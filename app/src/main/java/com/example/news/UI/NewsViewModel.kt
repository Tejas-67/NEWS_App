package com.example.news.UI


import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Email.TYPE_MOBILE
import android.util.Log
import android.widget.ResourceCursorAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import com.example.news.API.Resource
import com.example.news.DataModel.Article
import com.example.news.Utils.Constants
import com.example.news.DataModel.NewsResponse
import com.example.news.Database.ArticleDao
import com.example.news.Database.ArticleDatabase
import com.example.news.NewsApplication
import com.example.news.NewsRepository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(
    val app: Application,
    val newsRepository: NewsRepository
) : AndroidViewModel(app) {

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
        try{
            if(hasInternetConnection()){
                val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
                breakingNews.postValue(handleBreakingNewsResponse(response))
            }
            else{
                breakingNews.postValue(Resource.Error(Constants.NO_INTERNET_CONNECTION))
            }
        }catch (t: Throwable){
            when(t){
                is IOException -> breakingNews.postValue(Resource.Error(Constants.NETWORK_FAILURE))
                else -> breakingNews.postValue(Resource.Error(Constants.CONVERSION_ERROR))
            }
        }

    }
    fun getEntertainmentNews() = viewModelScope.launch {
        entertainmentNews.postValue(Resource.Loading())
        try{
            if(hasInternetConnection()){
                val response = newsRepository.getEntertainmentNews(countryCode, entertainmentNewsPage)
                entertainmentNews.postValue(handleEntertainmentNewsResponse(response))
            }
            else{
                entertainmentNews.postValue(Resource.Error(Constants.NO_INTERNET_CONNECTION))
            }
        }catch (t: Throwable){
            when(t){
                is IOException -> entertainmentNews.postValue(Resource.Error(Constants.NETWORK_FAILURE))
                else -> entertainmentNews.postValue(Resource.Error(Constants.CONVERSION_ERROR))
            }
        }
    }

    fun getBusinessNews()= viewModelScope.launch{
        businessNews.postValue(Resource.Loading())
        try{
            if(hasInternetConnection()){
                val response = newsRepository.getBusinessNews(countryCode, businessNewsPage)
                businessNews.postValue(handleBusinessNewsResponse(response))
            }
            else{
                businessNews.postValue(Resource.Error(Constants.NO_INTERNET_CONNECTION))
            }
        }catch (t: Throwable){
            when(t){
                is IOException -> businessNews.postValue(Resource.Error(Constants.NETWORK_FAILURE))
                else -> businessNews.postValue(Resource.Error(Constants.CONVERSION_ERROR))
            }
        }
    }

    fun getSportsNews()= viewModelScope.launch{
        sportsNews.postValue(Resource.Loading())
        try{
            if(hasInternetConnection()){
                val response = newsRepository.getSportsNews(countryCode, sportsNewsPage)
                sportsNews.postValue(handleSportsNewsResponse(response))
            }
            else{
                sportsNews.postValue(Resource.Error(Constants.NO_INTERNET_CONNECTION))
            }
        }catch (t: Throwable){
            when(t){
                is IOException -> sportsNews.postValue(Resource.Error(Constants.NETWORK_FAILURE))
                else -> sportsNews.postValue(Resource.Error(Constants.CONVERSION_ERROR))
            }
        }
    }

    fun getHealthNews()= viewModelScope.launch{
        healthNews.postValue(Resource.Loading())
        try{
            if(hasInternetConnection()){
                val response = newsRepository.getHealthNews(countryCode, healthNewsPage)
                healthNews.postValue(handleHealthNewsResponse(response))
            }
            else{
                healthNews.postValue(Resource.Error(Constants.NO_INTERNET_CONNECTION))
            }
        }catch (t: Throwable){
            when(t){
                is IOException -> healthNews.postValue(Resource.Error(Constants.NETWORK_FAILURE))
                else -> healthNews.postValue(Resource.Error(Constants.CONVERSION_ERROR))
            }
        }
    }

    fun getTechnologyNews()= viewModelScope.launch{
        technologyNews.postValue(Resource.Loading())
        try{
            if(hasInternetConnection()){
                val response = newsRepository.getTechnologyNews(countryCode, technologyNewsPage)
                technologyNews.postValue(handleTechnologyNewsResponse(response))
            }
            else{
                technologyNews.postValue(Resource.Error(Constants.NO_INTERNET_CONNECTION))
            }
        }catch (t: Throwable){
            when(t){
                is IOException -> technologyNews.postValue(Resource.Error(Constants.NETWORK_FAILURE))
                else -> technologyNews.postValue(Resource.Error(Constants.CONVERSION_ERROR))
            }
        }
    }

    fun getScienceNews()= viewModelScope.launch{
        scienceNews.postValue(Resource.Loading())
        try{
            if(hasInternetConnection()){
                val response = newsRepository.getScienceNews(countryCode, scienceNewsPage)
                scienceNews.postValue(handleScienceNewsResponse(response))
            }
            else{
                scienceNews.postValue(Resource.Error(Constants.NO_INTERNET_CONNECTION))
            }
        }catch (t: Throwable){
            when(t){
                is IOException -> scienceNews.postValue(Resource.Error(Constants.NETWORK_FAILURE))
                else -> scienceNews.postValue(Resource.Error(Constants.CONVERSION_ERROR))
            }
        }
    }
    private fun hasInternetConnection(): Boolean{
        val connectivityManager = getApplication<NewsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork?:return false
            val capability = connectivityManager.getNetworkCapabilities(activeNetwork)?:return false
            return when{
                capability.hasTransport(TRANSPORT_WIFI) -> true
                capability.hasTransport(TRANSPORT_CELLULAR) -> true
                capability.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
        else{
            connectivityManager.activeNetworkInfo?.run{
                return when(type){
                    TYPE_WIFI-> true
                    TYPE_MOBILE ->  true
                    TYPE_ETHERNET ->  true
                    else -> false
                }
            }
        }
        return false
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
        try{
            if(hasInternetConnection()){
                val response = newsRepository.searchNews(searchQuery, searchNewsPage)
                searchNews.postValue(handleSearchNewsResponse(response))
            }
            else{
                searchNews.postValue(Resource.Error(Constants.NO_INTERNET_CONNECTION))
            }
        }catch (t: Throwable){
            when(t){
                is IOException -> searchNews.postValue(Resource.Error(Constants.NETWORK_FAILURE))
                else -> searchNews.postValue(Resource.Error(Constants.CONVERSION_ERROR))
            }
        }
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