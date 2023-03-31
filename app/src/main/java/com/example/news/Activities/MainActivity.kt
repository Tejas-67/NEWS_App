package com.example.news.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.news.Fragments.BreakingNewsFragment
import com.example.news.Fragments.SavedNewsFragment
import com.example.news.Fragments.SearchNewsFragment
import com.example.news.R
import com.example.news.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

//a7049628798b4fe2875ca95af30922d3 api key
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding?=null
    private val binding get()=_binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w("TEJAS", "MainActivity")

        _binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCurrentFragment(BreakingNewsFragment())

        binding.bottomNavView.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.savedNewsFragment->setCurrentFragment(SavedNewsFragment())
                        R.id.searchNewsFragment-> setCurrentFragment(SearchNewsFragment())
                    R.id.breakingNewsFragment->setCurrentFragment(BreakingNewsFragment())

                }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) =
        this.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.frameLayout, fragment)
                commit()
        }


}