package com.example.crypto_news_app.services.RoomCoin

import android.content.Context
import androidx.room.*
import com.example.crypto_news_app.model.CoinInfo
import com.example.crypto_news_app.model.CoinQuotes
import com.example.crypto_news_app.util.Converter
 //room
//ayırmak gerekecek gibi
@Database(entities=arrayOf(CoinQuotes::class,CoinInfo::class),version = 2, exportSchema = true )
@TypeConverters(Converter::class)
    abstract class CoinDatabase : RoomDatabase(){

        abstract  fun CoinInfoDao(): CoinInfoDao
        abstract  fun CoinQuotesDao(): CoinQuotesDao
        //Singleton
        companion object{

      @Volatile
      private var instance: CoinDatabase? =null

      private val lock =Any()
            //daha önce oluşturulmuşsa onu kulan yoksa yeni olustur
      operator fun invoke(context:Context)= instance ?: synchronized(lock){

          instance ?: databaseOlustur(context).also{
              instance =it
          }
      }

        private fun databaseOlustur(context:Context)= Room.databaseBuilder(
            context.applicationContext, CoinDatabase::class.java,"coindatabase").
            fallbackToDestructiveMigration().build()
        }
    }