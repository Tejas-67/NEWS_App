package com.example.news.Fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.API.Resource
import com.example.news.Activities.MainActivity
import com.example.news.Adapters.NewsAdapter
import com.example.news.DataModel.Article
import com.example.news.ItemClickListener
import com.example.news.R
import com.example.news.UI.NewsViewModel
import com.example.news.Utils.Constants
import com.example.news.databinding.FragmentBreakingNewsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class BreakingNewsFragment : Fragment(), ItemClickListener {


    private var _binding: FragmentBreakingNewsBinding?=null
    private val binding get()=_binding!!
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var viewModel: NewsViewModel
    private lateinit var listener: ItemClickListener
    var isLoading = false
    var isLastPage = false
    var isScrolling = false
    val scrollListener = object: RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState==AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) isScrolling=true
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager= recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount=layoutManager.childCount
            val totalItemCount=layoutManager.itemCount

            val isNotLoadingAndNotLastPage=!isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition+visibleItemCount>=totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition>=0
            val isTotalMoreThanVisible = totalItemCount>=Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if(shouldPaginate){
                viewModel.getBreakingNews()
                isScrolling=false

            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    checkConnection()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentBreakingNewsBinding.inflate(inflater, container, false)
        listener=this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        showProgressBar()
        setUpRecyclerView()
        binding.technology.setOnClickListener {
            viewModel.technologyNews.observe(viewLifecycleOwner, Observer {
                    response->
                when(response){
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let{
                                newsResponse ->
                            newsAdapter.differ.submitList(newsResponse.articles)
                        }
                    }
                    is Resource.Error->{
                        hideProgressBar()
                        response.message?.let{message->
                            Log.w("NEWS", "Error in fetching Technology News")
                            showToast(message)
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
                            showToast(message)
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
                            showToast(message)
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
                            showToast(message)
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
            viewModel.breakingNews.observe(viewLifecycleOwner, Observer {
                    response->
                when(response){
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let{newsResponse ->
                            newsAdapter.differ.submitList(newsResponse.articles)
                            val totalPages = newsResponse.totalResults / Constants.QUERY_PAGE_SIZE +2
                            isLastPage=viewModel.breakingNewsPage==totalPages
                        }
                    }
                    is Resource.Error->{
                        hideProgressBar()
                        response.message?.let{message->
                            Log.w("NEWS", "Error in fetching News")
                            showToast(message)
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
                            showToast(message)
                        }
                    }
                    is Resource.Loading-> {
                        showProgressBar()
                    }
                }
            })
        }

        binding.searchNewsIv.setOnClickListener {
            val action=BreakingNewsFragmentDirections.actionBreakingNewsFragmentToSearchNewsFragment()
            findNavController().navigate(action)
        }

        binding.viewSavedButton.setOnClickListener {
            val action=BreakingNewsFragmentDirections.actionBreakingNewsFragmentToSavedNewsFragment()
            findNavController().navigate(action)
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
                            Log.w("NEWS", message)
                            showToast(message)
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
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages=newsResponse.totalResults / (Constants.QUERY_PAGE_SIZE+2)
                        isLastPage=(viewModel.breakingNewsPage==totalPages)
                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let{message->
                        Log.w("NEWS",  message)
                        showToast(message)

                    }
                }
                is Resource.Loading-> {
                    showProgressBar()
                }

            }
        })

    }
    private fun showToast(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    private fun showProgressBar(){
        isLoading=true
        binding.paginationProgressBar.visibility=View.VISIBLE
    }
    private fun hideProgressBar(){
        isLoading=false
        binding.paginationProgressBar.visibility=View.INVISIBLE
    }

    private fun setUpRecyclerView(){
        newsAdapter= NewsAdapter(listener)
        binding.rvBreakingNews.apply {
            adapter=newsAdapter
            layoutManager=LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }

    private fun checkConnection(){
        if(!isNetworkAvailable(requireContext())){
            val builder= MaterialAlertDialogBuilder(requireContext())
            builder.setTitle("Something went wrong")
                .setMessage("We're having issues loading this page.")
                .setPositiveButton("Try Again"){_,_-> checkConnection()}
                .setNegativeButton("Exit"){_, _ -> activity?.finish()}
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    override fun onItemClick(view: View, article: Article) {
        val action=BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(article)
        findNavController().navigate(action)
    }

    override fun onSaveButtonClicked(view: View, article: Article) {
        viewModel.addToLocalDataBase(article)
        Toast.makeText(requireContext(), "News saved succesfully!", Toast.LENGTH_SHORT).show()
    }
}