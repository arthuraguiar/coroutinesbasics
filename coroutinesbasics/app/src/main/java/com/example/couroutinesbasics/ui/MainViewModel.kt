package com.example.couroutinesbasics.ui

import androidx.lifecycle.*
import com.example.couroutinesbasics.data.TrumpQuotesDao
import com.example.couroutinesbasics.repository.TrumpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
     private val repository: TrumpRepository,
     private val dao: TrumpQuotesDao
) : ViewModel() {

    private val responses: MutableLiveData<ViewModelResponse> = MutableLiveData()
    val viewModelResponses: LiveData<ViewModelResponse> get() = responses
    val data = dao.getFirstQuote().asLiveData()

    init {
        getQuotes()
    }

    fun getQuotes() = viewModelScope.launch(Dispatchers.Main) {
        try {
            responses.value = ViewModelResponse.Loading(true)
            repository.getRandomQuote()
        }catch (e: Exception) {
            responses.value = ViewModelResponse.OnGetQuoteError
        }
        finally {
            responses.value = ViewModelResponse.Loading(false)
        }
    }

    sealed class ViewModelResponse{
        data class Loading(val isLoading: Boolean): ViewModelResponse()
        object OnGetQuoteError: ViewModelResponse()
    }
}