package com.example.crypto_news_app.services.RoomCoin

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.crypto_news_app.model.CoinInfo

@Dao
interface CoinInfoDao {

@Insert
suspend fun insertAll(vararg CoinInfo: CoinInfo):List<Long>

@Query("DELETE FROM CoinInfo")
suspend fun deleteAll()

@Query("SELECT * FROM CoinInfo")
suspend fun getAllCoinInfo():List<CoinInfo>

@Query("SELECT * FROM CoinInfo  WHERE uuid=:CoinInfoId")
suspend fun getCoinInfo(CoinInfoId:Int):CoinInfo

@Query("SELECT * FROM CoinInfo  WHERE symbol=:CoinInfoSymbol")
suspend fun getCoinInfoWithSymbol(CoinInfoSymbol:String):CoinInfo

@Query("SELECT uuid FROM CoinInfo")
suspend fun getAllIds(): List<Int>
}