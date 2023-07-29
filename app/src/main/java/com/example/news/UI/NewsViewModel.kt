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

    var allDataInLocal: LiveData<List<Article>> = newsRepository.readAllData
    var countryCode = "in"
    fun setArticle(article: Article) {
        _currentArticle=article
    }
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null

    val businessNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var businessNewsPage=1
    var businessNewsResponse : NewsResponse? = null

    val entertainmentNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var entertainmentNewsPage = 1
    var entertainmentNewsResponse : NewsResponse? = null

    val sportsNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var sportsNewsPage=1
    var sportsNewsResponse : NewsResponse? = null

    val healthNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var healthNewsPage=1
    var healthNewsResponse: NewsResponse? = null

    val scienceNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var scienceNewsPage=1
    var scienceNewsResponse: NewsResponse? = null

    val technologyNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var technologyNewsPage=1
    var technologyNewsResponse: NewsResponse? = null

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null

    init {
        getBreakingNews()
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

    fun getBreakingNews() = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }
    fun getEntertainmentNews() = viewModelScope.launch {
        entertainmentNews.postValue(Resource.Loading())
        val response = newsRepository.getEntertainmentNews(countryCode, entertainmentNewsPage)
        entertainmentNews.postValue(handleEntertainmentNewsResponse(response))
    }

    fun getBusinessNews()= viewModelScope.launch{
        businessNews.postValue(Resource.Loading())
        val response = newsRepository.getBusinessNews(countryCode, businessNewsPage)
        businessNews.postValue(handleBusinessNewsResponse(response))
    }

    fun getSportsNews()= viewModelScope.launch{
        sportsNews.postValue(Resource.Loading())
        val response=newsRepository.getSportsNews(countryCode, sportsNewsPage)
        sportsNews.postValue(handleSportsNewsResponse(response))
    }

    fun getHealthNews()= viewModelScope.launch{
        healthNews.postValue(Resource.Loading())
        val response=newsRepository.getHealthNews(countryCode, healthNewsPage)
        healthNews.postValue(handleHealthNewsResponse(response))
    }

    fun getTechnologyNews()= viewModelScope.launch{
        technologyNews.postValue(Resource.Loading())
        val response=newsRepository.getTechnologyNews(countryCode, technologyNewsPage)
        technologyNews.postValue(handleTechnologyNewsResponse(response))
    }

    fun getScienceNews()= viewModelScope.launch{
        scienceNews.postValue(Resource.Loading())
        val response=newsRepository.getScienceNews(countryCode, scienceNewsPage)
        scienceNews.postValue(handleScienceNewsResponse(response))
    }
    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewsPage++
                if(breakingNewsResponse==null) breakingNewsResponse=resultResponse
                else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)

                }
                return Resource.Success(breakingNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleEntertainmentNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                entertainmentNewsPage++
                if(entertainmentNewsResponse==null) entertainmentNewsResponse=resultResponse
                else {
                    val oldArticles = entertainmentNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)

                }
                return Resource.Success(entertainmentNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleSportsNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                sportsNewsPage++
                if(sportsNewsResponse==null) sportsNewsResponse=resultResponse
                else {
                    val oldArticles = sportsNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)

                }
                return Resource.Success(sportsNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleTechnologyNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                technologyNewsPage++
                if(technologyNewsResponse==null) technologyNewsResponse=resultResponse
                else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)

                }
                return Resource.Success(technologyNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleBusinessNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                businessNewsPage++
                if(businessNewsResponse==null) businessNewsResponse=resultResponse
                else {
                    val oldArticles = businessNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)

                }
                return Resource.Success(businessNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleScienceNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                scienceNewsPage++
                if(scienceNewsResponse==null) scienceNewsResponse=resultResponse
                else {
                    val oldArticles = scienceNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)

                }
                return Resource.Success(scienceNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleHealthNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let{
                healthNewsPage++
                if(healthNewsResponse==null) healthNewsResponse=it
                else{
                    val oldArticles = scienceNewsResponse?.articles
                    val newArticles= it.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(healthNewsResponse?:it)
            }
        }
        return Resource.Error(response.message())
    }


    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }
    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let{
                searchNewsPage++
                if(searchNewsResponse==null) searchNewsResponse=it
                else{
                    val oldArticles=searchNewsResponse?.articles
                    val newArticles=it.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse?:it)
            }
        }
        return Resource.Error(response.message())
    }


}