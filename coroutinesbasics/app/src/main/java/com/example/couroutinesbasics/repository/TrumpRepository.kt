package com.example.couroutinesbasics.repository

import javax.inject.Inject

class TrumpRepository @Inject constructor(
    private val service: TrumpQuoteService
) {


    suspend fun getRandomQuote() = service.getRandomQuote()

}