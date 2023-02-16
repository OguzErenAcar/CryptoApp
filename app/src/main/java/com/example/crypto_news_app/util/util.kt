package com.example.crypto_news_app.util

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.constraintlayout.helper.widget.MotionPlaceholder
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.crypto_news_app.R
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener

fun ImageView.gorselIndir(url:String?,placeholder:CircularProgressDrawable) {
    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round)
    if (!url!!.substring(url.length - 3, url.length).equals("svg")) {
        Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
    } else {
        val uri = Uri.parse(url)

    GlideToVectorYou.init().with(context).load(uri,this)

    }
}

fun placeholderYap(context:Context):CircularProgressDrawable{

    return  CircularProgressDrawable(context).apply {

     strokeWidth=8f
     centerRadius=400f
     start()
    }
}