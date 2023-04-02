package com.example.roomdbunittestingdemo

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuoteDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var quoteDatabase: QuoteDatabase
    lateinit var quoteDao: QuoteDao

    @Before
    fun setUp() {
        quoteDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            QuoteDatabase::class.java
        ).allowMainThreadQueries().build()
        quoteDao = quoteDatabase.quoteDao()
    }

    @Test
    fun insertQuote_expectedSingleQuote() = runBlocking {
        val quote = Quote(0, "This is a test quote", "CheezyCode")
        quoteDao.insertQuote(quote)

        val result = quoteDao.getQuote().getOrAwaitValue()
        Assert.assertEquals(1, result.size)
        Assert.assertEquals("This is a test quote", result[0].text)
    }

    @Test
    fun deleteQuote_expectedNoResult() = runBlocking {
        val quote = Quote(0, "This is a test quote", "CheezyCode")
        quoteDao.insertQuote(quote)

        quoteDao.delete()

        val result = quoteDao.getQuote().getOrAwaitValue()

        Assert.assertEquals(0, result.size)


    }



    @After
    fun tearDown() {
        quoteDatabase.close()
    }
}