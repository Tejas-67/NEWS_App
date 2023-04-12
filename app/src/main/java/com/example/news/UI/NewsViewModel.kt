package com.example.news.UI


import android.util.Log
import android.widget.ResourceCursorAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import com.example.news.API.Resource
import com.example.news.DataModel.Article
import com.example.news.DataModel.NewsResponse
import com.example.news.Database.ArticleDao
import com.example.news.Database.ArticleDatabase
import com.example.news.NewsRepository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    private var _currentArticle: Article?=null
    val currentArticle get()=_currentArticle!!

    var allDataInLocal: LiveData<List<Article>> = newsRepository.readAllData


    fun setArticle(article: Article) {
        _currentArticle=article
    }
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1

    val businessNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val entertainmentNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val sportsNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val healthNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val scienceNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val technologyNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1

    init {
        getBreakingNews("in")
        getEntertainmentNews()
        getBusinessNews()
        getScienceNews()
        getHealthNews()
        getSportsNews()
        getTechnologyNews()
    }

    fun getSavedArticles(): LiveData<List<Article>>{
        return allDataInLocal
    }
    fun deleteFromDatabase(article: Article){
        viewModelScope.launch {
            newsRepository.deleteFromLocal(article)
        }
    }

    fun addToLocalDataBase(article: Article){
        Log.w("TEJAS", "viewModel Initiating Insert")
        viewModelScope.launch{
            newsRepository.addToLocal(article)
        }
        Log.w("TEJAS", "viewModel Insert Done")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleNewsResponse(response))
    }
    fun getEntertainmentNews() = viewModelScope.launch {
        entertainmentNews.postValue(Resource.Loading())
        val response = newsRepository.getEntertainmentNews()
        entertainmentNews.postValue(handleNewsResponse(response))
    }

    fun getBusinessNews()= viewModelScope.launch{
        businessNews.postValue(Resource.Loading())
        val response = newsRepository.getBusinessNews()
        businessNews.postValue(handleNewsResponse(response))
    }

    fun getSportsNews()= viewModelScope.launch{
        sportsNews.postValue(Resource.Loading())
        val response=newsRepository.getSportsNews()
        sportsNews.postValue(handleNewsResponse(response))
    }

    fun getHealthNews()= viewModelScope.launch{
        healthNews.postValue(Resource.Loading())
        val response=newsRepository.getHealthNews()
        healthNews.postValue(handleNewsResponse(response))
    }

    fun getTechnologyNews()= viewModelScope.launch{
        technologyNews.postValue(Resource.Loading())
        val response=newsRepository.getTechnologyNews()
        technologyNews.postValue(handleNewsResponse(response))
    }

    fun getScienceNews()= viewModelScope.launch{
        scienceNews.postValue(Resource.Loading())
        val response=newsRepository.getScienceNews()
        scienceNews.postValue(handleNewsResponse(response))
    }
    private fun handleNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleNewsResponse(response))
    }


}