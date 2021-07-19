package com.example.couroutinesbasics.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TrumpQuote::class], version = 1)
abstract class TrumpQuotesDataBase : RoomDatabase(){
    abstract fun trumpDao(): TrumpQuotesDao
}