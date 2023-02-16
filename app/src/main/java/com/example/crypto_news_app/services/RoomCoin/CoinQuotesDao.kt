package com.example.crypto_news_app.services.RoomCoin

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.crypto_news_app.model.CoinQuotes

@Dao
interface CoinQuotesDao {

    @Insert
    suspend fun insertAll(vararg CoinQuotes: CoinQuotes):List<Long>

    @Insert
    suspend fun insert(CoinQuotes: CoinQuotes):Long

    @Query("DELETE FROM CoinQuotes")
    suspend fun deleteAll()

    @Query("SELECT * FROM CoinQuotes")
    suspend fun getAllCoinQuotes():List<CoinQuotes>

    @Query("SELECT * FROM CoinQuotes  WHERE uuid=:CoinQuotesId")
    suspend fun getCoinQuotes(CoinQuotesId:Int): CoinQuotes

    @Query("SELECT uuid FROM CoinQuotes  WHERE uuid=:CoinQuotesId")
    suspend fun getuuid(CoinQuotesId:Int): Int?

    @Query("SELECT uuid FROM CoinQuotes")
    suspend fun getAllIds(): List<Int>


}