package com.example.news.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.Activities.MainActivity
import com.example.news.Adapters.NewsAdapter
import com.example.news.Adapters.SavedNewsAdapter
import com.example.news.DataModel.Article
import com.example.news.ItemClickListener
import com.example.news.R
import com.example.news.UI.NewsViewModel
import com.example.news.databinding.FragmentSavedNewsBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SavedNewsFragment : Fragment(), ItemClickListener {
    private lateinit var listener: ItemClickListener
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var adapter: SavedNewsAdapter
    private var _binding : FragmentSavedNewsBinding?=null
    private val  binding get()=_binding!!
    private lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            viewModel=(activity as MainActivity).viewModel
            listener=this
            _binding=FragmentSavedNewsBinding.inflate(inflater, container, false)
            return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        viewModel.allDataInLocal.observe(viewLifecycleOwner) {
            list-> adapter.setData(list)
        }
    }

    private fun setUpRecyclerView(){
        adapter= SavedNewsAdapter(listener)
        binding.rvSavedNews.adapter=adapter
        binding.rvSavedNews.layoutManager= LinearLayoutManager(requireContext())

    }



    override fun onItemClick(view: View, article: Article) {
        Log.w("TEJAS", "Click Received BreakingNews")
        Log.w("TEJAS", "action initialised savedNews")
        viewModel.setArticle(article)
        (activity as MainActivity).moveToArticleFragment()
    }
}
