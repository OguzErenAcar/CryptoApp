package com.example.crypto_news_app.VM

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.crypto_news_app.model.Coin
import com.example.crypto_news_app.model.ApiData
import com.example.crypto_news_app.model.CoinInfo
import com.example.crypto_news_app.services.RoomCoin.CoinDatabase
import com.example.crypto_news_app.util.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

//lifecycle ve livedata kütüphenelerini bil
class MainFragmentVM(application: Application):BaseVM(application){
    //view de buradan bir nesne oluşturup
    //observe live data ile bu listedeki datayı gözlemliyoruz

    val Coinler_Info =MutableLiveData<List<CoinInfo>>()
//    val CoinHatamesaji =MutableLiveData<List<Coin>>()
//    val CoinYukleniyor =MutableLiveData<List<Coin>>()

    private var guncellemeZamani=1.6*60*1000*1000*1000L


    private val OzelSharedPreferences=OzelSharedPreferences(getApplication())
    //ornek data
    fun refreshData(){

//        val kaydedilmeZamani=OzelSharedPreferences.zamaniAL()
//        System.out.println("fark"+(System.nanoTime()- kaydedilmeZamani!!))
//        if(kaydedilmeZamani!=null &&kaydedilmeZamani!=0L &&System.nanoTime()-kaydedilmeZamani<guncellemeZamani){
//            verileriSQLitetanAl()
//        }else{
            verileriInternettenAl()

       // }

    }
//    private fun verileriSQLitetanAl() {
//
//        launch {
//            val coinlistesi= CoinDatabase(getApplication()).CoinDao().getAllCoin()
//            CoinleriGoster(coinlistesi)
//            Toast.makeText(getApplication(),"coinler sqltan alındı",Toast.LENGTH_LONG).show()
//        }
//    }

    override fun verileriInternettenAl() {
      //System.out.println(CoinApiServis.getData())
        val CoinsNamestoArray =CoinsNametoArray(getApplication())
        val CoinNames = CoinsNameString(CoinsNamestoArray)
        disposable.add(
            CoinApiServis.getInfoData(CoinNames)
                .subscribeOn(Schedulers.newThread())//yeni threadde işlemler asenkron ve kullanıcı beklemesin programı diye
                .observeOn(AndroidSchedulers.mainThread())//mainde gözlemler
                .subscribeWith(object : DisposableSingleObserver<ApiData>(){
                    override fun onSuccess(t: ApiData) {
                        System.out.println("------runinfo-----")
                        //internetten çekilen verileri sqlite ta saklayıp
                        val list_ =t.toinfoCoinList(CoinsNamestoArray)
                        sqlitetaSakla(t.toinfoCoinList(CoinsNametoArray(getApplication())))
                        //basarılı
                        Toast.makeText(getApplication(),"coinler interenetten alındı",Toast.LENGTH_LONG).show()
                    }
                    override fun onError(e: Throwable) {
                        System.out.println("------hata-----")
                        System.out.println( e.printStackTrace().toString())
                    }
                })
        )

        //burası coin infoda olacak

    }
    //coinleri en son gösterdik
    private fun CoinleriGoster(CoinListesi: List<CoinInfo>){
        //bu live data
        Coinler_Info.value=CoinListesi
    }

    private fun sqlitetaSakla(CoinListesi: List<CoinInfo>){
        launch{
            //burdan yeni thread ile room oluşturup
            val infoDao = CoinDatabase(getApplication()).CoinInfoDao()
            infoDao.deleteAll()

            var i =0

            while(i<CoinListesi.size){
                CoinListesi[i].uuid=i
                i+=1
            }
            infoDao.insertAll(*CoinListesi.toTypedArray())
            CoinleriGoster(CoinListesi)
        }
        OzelSharedPreferences.zamaniKaydet(System.nanoTime())
    }





}