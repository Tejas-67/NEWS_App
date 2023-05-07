package com.example.news.Activities

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.news.Database.ArticleDatabase
import com.example.news.Fragments.ArticleFragment
import com.example.news.Fragments.BreakingNewsFragment
import com.example.news.Fragments.SavedNewsFragment
import com.example.news.Fragments.SearchNewsFragment
import com.example.news.NewsRepository.NewsRepository
import com.example.news.R
import com.example.news.UI.NewsViewModel
import com.example.news.UI.NewsViewModelProviderFactory
import com.example.news.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding?=null
    private val binding get()=_binding!!
    lateinit var viewModel : NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //REMEMBER!
        //Always initialise viewModel before inflating layout, because as soon as mainactivity layout is inflated the home fragment breakingnewsfragment will try to access the
            //viewmodel from mainactivity!
        val repo = NewsRepository(ArticleDatabase.getDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(repo)
        viewModel=ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
        Log.w("TEJAS", "MainActivity")
        supportActionBar?.hide()

        _binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val navController = this.findNavController(R.id.nav_host_fragment)
        // Find reference to bottom navigation view
        val navView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        // Hook your navigation controller to bottom navigation view
        navView.setupWithNavController(navController)
    }

//    fun checkConnection(){
//
//        if(!isOnline(this)){
//            val dialog= Dialog(this)
//            dialog.setContentView(R.layout.dialog_layout)
//            dialog.findViewById<Button>(R.id.refresh).setOnClickListener {
//                checkConnection()
//            }
//            dialog.findViewById<Button>(R.id.exit).setOnClickListener {
//                exitProcess(-1)
//            }
//            dialog.show()
//        }
//    }

//    private fun isOnline(context: Context): Boolean {
//        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            val n = cm.activeNetwork
//            if (n != null) {
//                val nc = cm.getNetworkCapabilities(n)
//                //It will check for both wifi and cellular network
//                return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
//            }
//            return false
//        } else {
//            val netInfo = cm.activeNetworkInfo
//            return netInfo != null && netInfo.isConnectedOrConnecting
//        }
//    }


}