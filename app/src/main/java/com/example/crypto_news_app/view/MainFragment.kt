package com.example.crypto_news_app.view

//import androidx.navigation.Navigation
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.crypto_news_app.R
import com.example.crypto_news_app.VM.MainFragmentVM
import com.example.crypto_news_app.adapter.CoinGridAdapter
import com.example.crypto_news_app.adapter.NavigationAction
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(R.layout.fragment_main) ,NavigationAction {
  //  view model ile adapter oluşturduk ve burda tanımladık
    private lateinit var viewmodel:MainFragmentVM
    private var Coin_adapter=CoinGridAdapter(arrayListOf(),this )


    override fun onCreate(savedInstanceState: Bundle?) {
     super.onCreate(savedInstanceState)
        //nav buton için
//        val navView: NavigationView = findNavController(R.layout.)
//        val drawerLayout:DrawerLayout= navView.drawerlayout
//        val actionbarDrawebleToogle= ActionBarDrawerToggle(activity,drawerLayout,R.string.open,R.string.close)
//        actionbarDrawebleToogle.drawerArrowDrawable.color= context?.let { ContextCompat.getColor(it,R.color.white) }!!
//
//        actionbarDrawebleToogle.syncState()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //  ViewModelProviders--->fragment ile vm yyi bağlar

        viewmodel=ViewModelProviders.of(this).get(MainFragmentVM::class.java)
        viewmodel.refreshData()
        recyclerview.layoutManager= object :GridLayoutManager(context,3){
            override fun canScrollVertically(): Boolean {
                return false
            } }
//        recyclerview.setNestedScrollingEnabled(false);
//        recyclerview.setHasFixedSize(true)
        recyclerview.adapter= Coin_adapter
        observeLiveData()


    }

    fun observeLiveData(){

        viewmodel.Coinler_Info.observe(viewLifecycleOwner, Observer {
            it?.let{//nullable ile çalışılrsa kullanılması gerekir
                //System.out.println(it.get(5).uuid)
                recyclerview.visibility=View.VISIBLE
                Coin_adapter.coinListesiniGuncelle(it)
               }
        })
    }

    override fun ActionNavigate(uuid: Int, view: View) {
        val action = MainFragmentDirections.actionMainFragmentToCoinInfoFragment(uuid)
        Navigation.findNavController(view).navigate(action)
    }


//    override fun setVisible(position: Int) {
//        viewmodel.Coinler_Info.observe(viewLifecycleOwner) {
//            it?.let {
//                if (it.size - 1 == position)
//                    recyclerview.visibility = View.VISIBLE
//            }
//        }
//    }

//    fun menuButton(){
//        menubutton.setOnClickListener {
//            drawerLayout.openDrawer(GravityCompat.START)
//        }

//            val action = MainFragmentDirections.actionMainFragmentToProfile()
//            Navigation.findNavController(view).navigate(action)
//        }


}


fun loginButtonListener(){

//    loginbutton.setOnClickListener {
//
//        val intent = Intent(context, LoginActivity::class.java)
//        startActivity(intent)
//
//    }

  }
