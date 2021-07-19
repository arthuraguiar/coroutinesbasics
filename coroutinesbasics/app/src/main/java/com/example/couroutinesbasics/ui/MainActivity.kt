package com.example.couroutinesbasics.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.couroutinesbasics.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObserver()
        initButtons()
    }

    private fun initObserver(){
        viewModel.viewModelResponses.observe(this, { response ->
            when(response){
                is MainViewModel.ViewModelResponse.Loading ->{
                    binding.progressBar.isVisible = response.isLoading
                }
                MainViewModel.ViewModelResponse.OnGetQuoteError -> {
                    showErrorDialog()
                }
            }
        })

        viewModel.data.observe(this ,{ quote ->
            binding.quoteText.text = quote?.message?:"veio nulo"
        })
    }

    private fun initButtons(){
        binding.refreshQuoteButton.setOnClickListener {
            viewModel.getQuotes()
        }
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage("Error While Getting Quote")
            .setNegativeButton("Ok", null)
            .show()
    }
}