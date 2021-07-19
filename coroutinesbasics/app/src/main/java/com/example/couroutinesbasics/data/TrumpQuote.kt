package com.example.couroutinesbasics.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trump_quote")
data class TrumpQuote(
    @PrimaryKey(autoGenerate = false)
    val message: String
)
