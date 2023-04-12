package com.example.news.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.news.databinding.FragmentBreakingNewsBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class BreakingNewsFragment : Fragment(), ItemClickListener {


    private var _binding: FragmentBreakingNewsBinding?=null
    private val binding get()=_binding!!
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var viewModel: NewsViewModel
    private lateinit var listener: ItemClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.w("TEJAS", "Breaking News Fragment")
        _binding=FragmentBreakingNewsBinding.inflate(inflater, container, false)
        Log.w("TEJAS", "vm")

        Log.w("TEJAS", "1")
        listener=this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.w("TEJAS", "2")
        viewModel=(activity as MainActivity).viewModel
        showProgressBar()
        Log.w("TEJAS", "3")
        setUpRecyclerView()
        Log.w("TEJAS", "4")

        //TECHNOLOGY
        binding.technology.setOnClickListener {
//            binding.sports.setChipBackgroundColorResource(R.color.faded_blue)
            viewModel.technologyNews.observe(viewLifecycleOwner, Observer {
                    response->
                when(response){
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let{newsResponse ->
                            newsAdapter.differ.submitList(newsResponse.articles)
                        }
                    }
                    is Resource.Error->{
                        hideProgressBar()
                        response.message?.let{message->
                            Log.w("NEWS", "Error in fetching Technology News")
                        }
                    }
                    is Resource.Loading-> {
                        showProgressBar()
                    }
                }
            })
        }

        //SCIENCE
        binding.science.setOnClickListener {
//            binding.sports.setChipBackgroundColorResource(R.color.faded_blue)

            viewModel.scienceNews.observe(viewLifecycleOwner, Observer {
                    response->
                when(response){
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let{newsResponse ->
                            newsAdapter.differ.submitList(newsResponse.articles)
                        }
                    }
                    is Resource.Error->{
                        hideProgressBar()
                        response.message?.let{message->
                            Log.w("NEWS", "Error in fetching Science News")
                        }
                    }
                    is Resource.Loading-> {
                        showProgressBar()
                    }
                }
            })
        }

        //HEALTH
        binding.health.setOnClickListener {
//            binding.sports.setChipBackgroundColorResource(R.color.faded_blue)
            viewModel.healthNews.observe(viewLifecycleOwner, Observer {
                    response->
                when(response){
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let{newsResponse ->
                            newsAdapter.differ.submitList(newsResponse.articles)
                        }
                    }
                    is Resource.Error->{
                        hideProgressBar()
                        response.message?.let{message->
                            Log.w("NEWS", "Error in fetching Health News")
                        }
                    }
                    is Resource.Loading-> {
                        showProgressBar()
                    }
                }
            })

        }

        //BUSINESS
        binding.business.setOnClickListener {
//            binding.sports.setChipBackgroundColorResource(R.color.faded_blue)
            viewModel.businessNews.observe(viewLifecycleOwner, Observer {
                    response->
                when(response){
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let{newsResponse ->
                            newsAdapter.differ.submitList(newsResponse.articles)
                        }
                    }
                    is Resource.Error->{
                        hideProgressBar()
                        response.message?.let{message->
                            Log.w("NEWS", "Error in fetching Business News")
                        }
                    }
                    is Resource.Loading-> {
                        showProgressBar()
                    }
                }
            })
        }

        //ALL
        binding.all.setOnClickListener {
//            binding.sports.setChipBackgroundColorResource(R.color.faded_blue)
            viewModel.breakingNews.observe(viewLifecycleOwner, Observer {
                    response->
                when(response){
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let{newsResponse ->
                            newsAdapter.differ.submitList(newsResponse.articles)
                        }
                    }
                    is Resource.Error->{
                        hideProgressBar()
                        response.message?.let{message->
                            Log.w("NEWS", "Error in fetching News")
                        }
                    }
                    is Resource.Loading-> {
                        showProgressBar()
                    }
                }
            })
        }

        //SPORTS
        binding.sports.setOnClickListener {
//            binding.sports.setChipBackgroundColorResource(R.color.faded_blue)
            viewModel.sportsNews.observe(viewLifecycleOwner, Observer {
                    response->
                when(response){
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let{newsResponse ->
                            newsAdapter.differ.submitList(newsResponse.articles)
                        }
                    }
                    is Resource.Error->{
                        hideProgressBar()
                        response.message?.let{message->
                            Log.w("NEWS", "Error in fetching Sports News")
                        }
                    }
                    is Resource.Loading-> {
                        showProgressBar()
                    }
                }
            })
        }

        //ENTERTAINMENT
        binding.entertainment.setOnClickListener {
            //binding.entertainment.setChipBackgroundColorResource(R.color.faded_blue)
            viewModel.entertainmentNews.observe(viewLifecycleOwner, Observer {
                response->
                when(response){
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let{newsResponse ->
                            newsAdapter.differ.submitList(newsResponse.articles)
                        }
                    }
                    is Resource.Error->{
                        hideProgressBar()
                        response.message?.let{message->
                            Log.w("NEWS", "Error in fetching Entertainment News")
                        }
                    }
                    is Resource.Loading-> {
                        showProgressBar()
                    }
                }
            })
        }

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {
            response->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let{newsResponse ->
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
                    showProgressBar()
                }

            }
        })

    }
    private fun showProgressBar(){
        binding.paginationProgressBar.visibility=View.VISIBLE
    }
    private fun hideProgressBar(){
        binding.paginationProgressBar.visibility=View.INVISIBLE
    }

    private fun setUpRecyclerView(){
        newsAdapter= NewsAdapter(listener)
        binding.rvBreakingNews.apply {
            adapter=newsAdapter
            layoutManager=LinearLayoutManager(activity)
        }
    }



    override fun onItemClick(view: View, article: Article) {

        Log.w("TEJAS", "Click Received BreakingNews")
//        val action=BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment()
        Log.w("TEJAS", "action initialised")
        viewModel.setArticle(article)
        (activity as MainActivity).moveToArticleFragment()
    }
}