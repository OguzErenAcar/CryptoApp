package com.example.crypto_news_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.crypto_news_app.R
import com.example.crypto_news_app.model.Coin
import com.example.crypto_news_app.model.CoinInfo
import com.example.crypto_news_app.util.gorselIndir
import com.example.crypto_news_app.util.placeholderYap
import com.example.crypto_news_app.view.MainFragment
import com.example.crypto_news_app.view.MainFragmentDirections
import kotlinx.android.synthetic.main.coin_item.view.*
import kotlinx.android.synthetic.main.fragment_main.*

// on below line we are creating an
// adapter class for our grid view.
//adapter liste ile coin_itemi birleştirir

//https://www.geeksforgeeks.org/android-gridview-in-kotlin/
internal class CoinGridAdapter(
    private val CoinModelList: ArrayList<CoinInfo>,
    private  var navigationAction: NavigationAction
):RecyclerView.Adapter<CoinGridAdapter.CoinViewHolder>()  {

    //burası alttakilerden oluşan array

    //burası ve 3 fonk bir nesne için işlemler
    private var guncelmi=false
    class CoinViewHolder(var itemView:View):RecyclerView.ViewHolder(itemView) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.coin_item,parent,false)
        return CoinViewHolder(view)
    }

    //her item için geçerli işlemlerin yapıldığı yer
    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {

        holder.itemView.CoinName_tv.text=CoinModelList.get(position).name
        //resimleri bitmap olarak saklayablrmiyiz ?
        holder.itemView.imageView.gorselIndir(CoinModelList.get(position).logo,placeholderYap(holder.itemView.context))
       // ListStatusListener.setVisible(position)
       // System.out.println(CoinModelList.get(position).uuid)
        holder.itemView.setOnClickListener {
            navigationAction.ActionNavigate(CoinModelList.get(position).uuid,it)

        }

    }

    override fun getItemCount(): Int {
       return CoinModelList.size
    }

    fun coinListesiniGuncelle(yeniCoinListesi:List<CoinInfo>){
        CoinModelList.clear()
        CoinModelList.addAll(yeniCoinListesi)

        notifyDataSetChanged()
    }


    //fun guncellendimi():Boolean { return guncelmi }

}



//internal class CoinGridAdapter(
//    // on below line we are creating two
//    // variables for course list and context
//    private val CoinModelList: ArrayList<Coin>,
//    private val context: Context
//) : BaseAdapter() {
//    // in base adapter class we are creating variables
//    // for layout inflater, course image view and course text view.
//    private var layoutInflater: LayoutInflater? = null
//    private lateinit var CoinName: TextView
//    private lateinit var CoinImage: ImageView
//
//    // below method is use to return the count of course list
//    override fun getCount(): Int {
//        return CoinModelList.size
//    }
//
//    // below function is use to return the item of grid view.
//    override fun getItem(position: Int): Any? {
//        return null
//    }
//    // below function is use to return item id of grid view.
//    override fun getItemId(position: Int): Long {
//        return 0
//    }
//    // in below function we are getting(return) individual item of grid view.
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        var convertView= convertView
//        // on blow line we are checking if layout inflater
//        // is null, if it is null we are initializing it.
//        if (layoutInflater == null) {
//            layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        }
//        // on the below line we are checking if convert view is null.
//        // If it is null we are initializing it.
//        if (convertView == null) {
//            // on below line we are passing the layout file
//            // which we have to inflate for each item of grid view.
//            convertView = layoutInflater!!.inflate(R.layout.coin_item, null)
//        }
//        // on below line we are initializing our course image view
//        // and course text view with their ids.
//        //   CoinImage = convertView!!.findViewById(R.id.idTVCourse)
//        // on below line we are setting image for our course image view.
//        //CoinImage.setImageResource(CoinModelList.get(position).courseImg)
//        // on below line we are setting text in our course text view.
//        CoinName=convertView!!.findViewById(R.id.CoinName_tv)
//        CoinName.setText(CoinModelList.get(position).coinName)
//        // at last we are returning our convert view.
//        return convertView
//    }