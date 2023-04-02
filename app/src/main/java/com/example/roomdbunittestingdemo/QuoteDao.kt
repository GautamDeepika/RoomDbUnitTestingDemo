package com.example.roomdbunittestingdemo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuoteDao {

    @Insert
    suspend fun insertQuote(quote: Quote)

    @Update
    suspend fun updateQuote(quote: Quote)

    @Query("DELETE FROM quote")
    suspend fun delete()

    @Query("SELECT * FROM quote")
    fun getQuote(): LiveData<List<Quote>>

    @Query("SELECT * FROM quote where id = :quoteId")
    suspend fun getQuoteById(quoteId: Int): Quote
}