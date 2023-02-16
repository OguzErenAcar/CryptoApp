package com.example.crypto_news_app.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.crypto_news_app.R
import com.example.crypto_news_app.ui.login.Login_Activity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private   var isLogin :Boolean = false
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout =drawerlayout
        val navView: NavigationView = nav_view
        val navController = findNavController(R.id.fragmentContainerView)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mainFragment ,R.id.profile
            ), drawerLayout )

        setupActionBarWithNavController(navController, appBarConfiguration)



        navView.setupWithNavController(navController)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false);
        editToolbar()
        loginbutton()
        //logout yapıldığında bilgiler nasıl gidicek activity kendini nası yenilicek
         isLogin=!intent.getBooleanExtra("LoginButonEnabled",true)




    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        if(isLogin||auth.currentUser!=null){
            loginbutton.visibility= View.GONE
            val menuInflater = menuInflater
            menuInflater.inflate(R.menu.main_menu, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.logoutbutton){

            val intent = Intent(applicationContext, Login_Activity::class.java)
            intent.putExtra("logout",true)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
    fun loginbutton(){
        loginbutton.setOnClickListener {
            val intent = Intent(applicationContext, Login_Activity::class.java)
            startActivity(intent)

        }

    }




    override fun onStart() {
        super.onStart()

    }

    fun setAuthenticationButton (){
        //mainac daki değişkene göre buton değişicek
    }

    fun editToolbar() {

        val drawerLayout: DrawerLayout =drawerlayout

        val actionbarDrawebleToogle=ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        actionbarDrawebleToogle.drawerArrowDrawable.color= ContextCompat.getColor(applicationContext,R.color.white)

        actionbarDrawebleToogle.syncState()

    //    val drawable = toolbar.navigationIcon.colorFilter
       toolbar.overflowIcon?.setColorFilter(ContextCompat.getColor(applicationContext, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY); // White Tint
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp(appBarConfiguration)  //|| super.onSupportNavigateUp()
    }



}




