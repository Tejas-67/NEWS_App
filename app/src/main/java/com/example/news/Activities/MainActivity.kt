package com.example.news.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.news.Database.ArticleDatabase
import com.example.news.Fragments.BreakingNewsFragment
import com.example.news.Fragments.SavedNewsFragment
import com.example.news.Fragments.SearchNewsFragment
import com.example.news.NewsRepository.NewsRepository
import com.example.news.R
import com.example.news.UI.NewsViewModel
import com.example.news.UI.NewsViewModelProviderFactory
import com.example.news.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

//a7049628798b4fe2875ca95af30922d3 api key
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding?=null
    private val binding get()=_binding!!
    public lateinit var viewModel : NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w("TEJAS", "MainActivity")

        _binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bnf=BreakingNewsFragment()
        val snf=SearchNewsFragment()
        val sanf=SavedNewsFragment()
        setCurrentFragment(bnf)
        binding.bottomNavView.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.savedNewsFragment->setCurrentFragment(sanf)
                        R.id.searchNewsFragment-> setCurrentFragment(snf)
                    R.id.breakingNewsFragment->setCurrentFragment(bnf)

                }
            true
        }

        val repo = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(repo)
        viewModel=ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)


    }

    private fun setCurrentFragment(fragment: Fragment) =
        this.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.frameLayout, fragment)
                commit()
        }


}