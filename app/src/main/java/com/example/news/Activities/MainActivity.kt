package com.example.news.Activities

import android.app.Activity
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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationBarView
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding?=null
    private val binding get()=_binding!!
    lateinit var viewModel : NewsViewModel

    private lateinit var navController: NavController
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
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController=navHostFragment.navController
        setupActionBarWithNavController(navController)
    }







}