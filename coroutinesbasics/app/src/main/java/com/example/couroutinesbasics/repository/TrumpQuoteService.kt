package com.example.couroutinesbasics.repository

import com.example.couroutinesbasics.data.TrumpQuote
import retrofit2.http.GET

interface TrumpQuoteService {

    @GET("random")
    suspend fun getRandomQuote(): TrumpQuote


    companion object{
        const val BASE_URL = "https://api.whatdoestrumpthink.com/api/v1/quotes/"
    }
}