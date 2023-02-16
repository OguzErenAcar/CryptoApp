package com.example.crypto_news_app.view

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.crypto_news_app.R
import com.example.crypto_news_app.VM.Coin_detailsVM
import com.example.crypto_news_app.VM.Coin_detailsVMFactory
import com.example.crypto_news_app.adapter.NavigationAction
import com.example.crypto_news_app.ui.login.LoginViewModel
import com.example.crypto_news_app.ui.login.LoginViewModelFactory
import com.example.crypto_news_app.util.gorselIndir
import com.example.crypto_news_app.util.placeholderYap
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_coin_info.*

class CoinDetailsFragment : Fragment()  {

    private var id_=0
    private lateinit var viewModel:Coin_detailsVM
    private lateinit var Firestore: FirebaseFirestore
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context?.let { FirebaseApp.initializeApp(it) }
        auth = Firebase.auth


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coin_info, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let { FirebaseApp.initializeApp(it) }
        Firestore = FirebaseFirestore.getInstance()

        arguments?.let{
            id_ =CoinDetailsFragmentArgs.fromBundle(it).coinIdArguman

        }
        viewModel= ViewModelProvider(this, Coin_detailsVMFactory( Application(),Firestore,auth)).get(Coin_detailsVM::class.java)

         viewModel.roomVerisiniAl(id_).invokeOnCompletion {
             viewModel.verileriInternettenAl()
             observeLiveData()
         }

        if(auth.currentUser!=null){
            Coinaddfav()
        }


    }

    fun Coinaddfav(){
        var isFav=false
        viewModel.isFav(){ bool->
            if(bool){
                 star.setColorFilter(ContextCompat.getColor(requireContext(), R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY)
             }
            System.out.println("bool $bool")
            isFav=bool
        }
        //listenerın yukardaknden sonra eklendiğine emn olmak lazm
        star.setOnClickListener {
            System.out.println("tıklandı")
            isFav=!isFav
            setStar(isFav)

        }

    }

   fun  setStar(bool:Boolean){
       if(bool){

           star.setColorFilter(ContextCompat.getColor(requireContext(), R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY)
           viewModel.addFav()//burası asenkron davranırsa ne olcak
       }
       else{
           star.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY)
           viewModel.deleteFav()//burası asenkron davranırsa ne olcak
       }
    }

    @SuppressLint("SetTextI18n")
    fun observeLiveData(){

        viewModel.CoinLiveData.observe(viewLifecycleOwner, Observer { Coin->
            Coin?.let{

                CoinName.text=Coin.Coin_Info?.name
                val price =Coin.Coin_Quotes?.price?.split(".")
                Price.text=price?.get(0).toString()+"."+price?.get(1)?.substring(0,2)
                tanim.text=Coin.Coin_Info?.description
                text1.text= Coin.Coin_Quotes?.max_supply.toString()
                text2.text=Coin.Coin_Quotes?.percent_24h
                text3.text=Coin.Coin_Quotes?.circulating_supply.toString()
                text4.text=Coin.Coin_Quotes?.total_supply.toString()
                context?.let {
                    //data url dolayısıyla resim her zaman indiriliyor
                    coin_IV.gorselIndir(Coin.Coin_Info?.logo, placeholderYap(it))
                }
            }
        })


    }
//    fun loginButtonListener() {
//
//        loginbutton2.setOnClickListener {
//
//            val intent = Intent(context, LoginActivity::class.java)
//            startActivity(intent)
//
//        }
//    }

}
