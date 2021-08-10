package com.example.couroutinesbasics.repository

import com.example.couroutinesbasics.data.TrumpQuote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TrumpRepository @Inject constructor(
    private val service: TrumpQuoteService
) {

    suspend fun getRandomQuoteFlow(): Flow<TrumpQuote> = flow {
        emit(service.getRandomQuote())
    }

}