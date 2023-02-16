package com.example.crypto_news_app.view

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.crypto_news_app.R
import com.example.crypto_news_app.VM.ProfilVM
import com.example.crypto_news_app.VM.ProfilVMFactory
import com.example.crypto_news_app.adapter.CoinGridAdapter
import com.example.crypto_news_app.adapter.NavigationAction
import com.example.crypto_news_app.model.Coin
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment(),NavigationAction {
    private var Coin_adapter = CoinGridAdapter(arrayListOf(),this)
    private lateinit var viewmodel :ProfilVM
    private lateinit var Firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let { FirebaseApp.initializeApp(it) }
        auth = Firebase.auth
        Firestore=FirebaseFirestore.getInstance()

        profil_recyler_view.layoutManager=object :GridLayoutManager(context,2){
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        profil_recyler_view.adapter=Coin_adapter

        viewmodel= ViewModelProviders.of(this,ProfilVMFactory(Application(),FirebaseFirestore.getInstance(),auth)).get(ProfilVM::class.java)

        viewmodel.FavCoinleriRoomdanAl()

        observeLiveData()


    }

    private fun observeLiveData() {
        viewmodel.Coinler_Info.observe(viewLifecycleOwner) {

            Coin_adapter.coinListesiniGuncelle(it)

        }
    }

    override fun ActionNavigate(uuid: Int, view: View) {
        val action = ProfileFragmentDirections.actionProfileToCoinInfoFragment(uuid)
        Navigation.findNavController(view).navigate(action)
    }



}