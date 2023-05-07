package com.example.news.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.API.Resource
import com.example.news.Activities.MainActivity
import com.example.news.Adapters.NewsAdapter
import com.example.news.DataModel.Article
import com.example.news.ItemClickListener
import com.example.news.R
import com.example.news.UI.NewsViewModel
import com.example.news.databinding.FragmentSearchNewsBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG="TEJAS"

class SearchNewsFragment : Fragment(), ItemClickListener {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var  newsAdapter: NewsAdapter
    private var _binding: FragmentSearchNewsBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel : NewsViewModel
    private lateinit var listener: ItemClickListener

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
        _binding=FragmentSearchNewsBinding.inflate(inflater, container, false)
        listener=this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //newsAdapter=NewsAdapter()
      //  showProgressBar()
        setUpRecyclerView()

        var job: Job?=null
        binding.etSearch.addTextChangedListener { editable->
            job?.cancel()
            job= MainScope().launch{
                //delay(500L)
                editable?.let{
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }



        viewModel.searchNews.observe(viewLifecycleOwner, Observer {
                response->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let{newsResponse ->
                        Log.w(TAG, "searched")
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let{message->
                        Log.w("NEWS", "Error in fetching data")
                    }
                }
                is Resource.Loading-> {
                    Log.w(TAG, "Loading!!")
                    showProgressBar()
                }

            }
        })
    }
    private fun showProgressBar(){
        binding.paginationProgressBarSearch.visibility=View.VISIBLE
    }
    private fun hideProgressBar(){
        binding.paginationProgressBarSearch.visibility=View.INVISIBLE
    }
    private fun setUpRecyclerView(){
        newsAdapter= NewsAdapter(listener)
        binding.rvSearchNews.apply {
            adapter=newsAdapter
            layoutManager= LinearLayoutManager(activity)
        }
    }

    override fun onItemClick(view: View, article: Article) {
        viewModel.setArticle(article)
    }

    override fun onSaveButtonClicked(view: View, article: Article) {
    }
}