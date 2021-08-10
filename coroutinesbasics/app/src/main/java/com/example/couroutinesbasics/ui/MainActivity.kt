package com.example.couroutinesbasics.ui


import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.couroutinesbasics.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow


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
        lifecycleScope.launchWhenStarted {
            viewModel.viewModelResponses.receiveAsFlow().collect { response ->
                when(response){
                    is MainViewModel.ViewModelResponse.Loading ->{
                        binding.progressBar.isVisible = response.isLoading
                    }
                    is MainViewModel.ViewModelResponse.OnGetQuoteError -> {
                        showErrorDialog(response.msg)
                    }
                    is MainViewModel.ViewModelResponse.OnGetQuoteSucess -> {
                        binding.quoteText.text = response.quote.message
                    }
                }
            }
        }
    }

    private fun initButtons(){
        binding.refreshQuoteButton.setOnClickListener {
            viewModel.getQuotes()
        }
    }

    private fun showErrorDialog(msg: String) {
        AlertDialog.Builder(this)
            .setTitle("Error While Getting Quote")
            .setMessage(msg)
            .setNegativeButton("Ok", null)
            .show()
    }
}