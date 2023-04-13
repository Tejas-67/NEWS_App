package com.example.news.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.news.Activities.MainActivity
import com.example.news.DataModel.Article
import com.example.news.R
import com.example.news.UI.NewsViewModel
import com.example.news.databinding.FragmentArticleBinding
private const val TAG="TEJAS"
class ArticleFragment : Fragment() {

    private lateinit var args: NavArgs


     private var _binding: FragmentArticleBinding?=null
     private val binding get()=_binding!!
     private lateinit var viewModel: NewsViewModel
     private var article: Article?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel=(activity as MainActivity).viewModel
        _binding=FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        article=viewModel.currentArticle
        binding.webView.loadUrl(article!!.url)

        binding.fab.setOnClickListener{
            if(article==null) Toast.makeText(requireContext(), "Couldn't Save News!!", Toast.LENGTH_SHORT).show()
            else {
                viewModel.addToLocalDataBase(article!!)
                Toast.makeText(requireContext(), "News Saved Succesfully", Toast.LENGTH_SHORT).show()
                Log.w(TAG, "Added Successfully to Room")
            }
        }
    }
}