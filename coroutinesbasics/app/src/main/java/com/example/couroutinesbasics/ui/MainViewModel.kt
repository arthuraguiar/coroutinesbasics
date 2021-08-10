package com.example.couroutinesbasics.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.couroutinesbasics.data.TrumpQuote
import com.example.couroutinesbasics.repository.TrumpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TrumpRepository
) : ViewModel() {

    private val responses =  Channel<ViewModelResponse>()
    val viewModelResponses get() = responses

    init {
        getQuotes()
    }

    fun getQuotes() = viewModelScope.launch {
        repository.getRandomQuoteFlow()
            .flowOn(Dispatchers.IO)
            .onStart {
                responses.send(ViewModelResponse.Loading(true))
            }
            .catch { t ->
                responses.send(ViewModelResponse.OnGetQuoteError(t.message.toString()))
            }
            .onCompletion {
                responses.send(ViewModelResponse.Loading(false))
            }
            .collect { quote ->
                responses.send(ViewModelResponse.OnGetQuoteSucess(quote))
            }
    }

    sealed class ViewModelResponse {
        data class OnGetQuoteSucess(val quote: TrumpQuote) : ViewModelResponse()
        data class Loading(val isLoading: Boolean) : ViewModelResponse()
        data class OnGetQuoteError(val msg: String) : ViewModelResponse()
    }
}