package com.example.couroutinesbasics.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.couroutinesbasics.data.TrumpQuote
import com.example.couroutinesbasics.repository.TrumpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
        private val repository: TrumpRepository
) : ViewModel() {

    private val responses: MutableLiveData<ViewModelResponse> = MutableLiveData()
    val viewModelResponses: LiveData<ViewModelResponse> get() = responses

    init {
        getQuotes()
    }

    fun getQuotes() = viewModelScope.launch(Dispatchers.Main) {
        try {
            responses.value = ViewModelResponse.Loading(true)
            responses.value = ViewModelResponse.OnGetQuoteSucess(repository.getRandomQuote())
        }catch (e: Exception){
            responses.value = ViewModelResponse.OnGetQuoteError
        }finally {
            responses.value = ViewModelResponse.Loading(false)
        }

    }

    sealed class ViewModelResponse{
        data class OnGetQuoteSucess(val quote: TrumpQuote): ViewModelResponse()
        data class Loading(val isLoading: Boolean): ViewModelResponse()
        object OnGetQuoteError: ViewModelResponse()
    }
}