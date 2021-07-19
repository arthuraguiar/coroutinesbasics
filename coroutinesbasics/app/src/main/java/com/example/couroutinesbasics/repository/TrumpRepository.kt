package com.example.couroutinesbasics.repository

import androidx.room.withTransaction
import com.example.couroutinesbasics.data.TrumpQuotesDao
import com.example.couroutinesbasics.data.TrumpQuotesDataBase
import javax.inject.Inject

class TrumpRepository @Inject constructor(
    private val service: TrumpQuoteService,
    private val db: TrumpQuotesDataBase,
    private val dao: TrumpQuotesDao
) {

    suspend fun getRandomQuote() {
        val randomQuote = service.getRandomQuote()
        db.withTransaction {
            dao.deleteAllQuotes()
            dao.insert(randomQuote)
        }
    }
}