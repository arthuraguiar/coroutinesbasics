package com.example.couroutinesbasics.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TrumpQuotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trumpQuote: TrumpQuote)

    @Query("DELETE FROM trump_quote")
    suspend fun deleteAllQuotes()

    @Query("SELECT * FROM trump_quote LIMIT 1")
    fun getFirstQuote(): Flow<TrumpQuote?>
}