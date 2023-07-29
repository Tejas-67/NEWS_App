package com.example.news.NewsRepository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.news.API.RetrofitInstance
import com.example.news.DataModel.Article
import com.example.news.DataModel.NewsResponse
import com.example.news.Database.ArticleDao
import com.example.news.Database.ArticleDatabase
import retrofit2.Response

class NewsRepository(val db: ArticleDatabase){


    private val dao: ArticleDao = db.getDao()
    val readAllData: LiveData<List<Article>> = dao.getAllArticles()

    suspend fun addToLocal(article: Article){
        dao.insertArticle(article)
    }

    suspend fun deleteFromLocal(article: Article){
        dao.deleteArticle(article)
    }

    fun getAllSaved(): LiveData<List<Article>>{
        return dao.getAllArticles()
    }


    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse> {
        return RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
    }


    suspend fun searchNews(searchQuery: String, pageNumber: Int)=
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun getBusinessNews(code: String, pageNumber: Int)= RetrofitInstance.api.getBusinessNews(code, pageNumber = pageNumber)

    suspend fun getSportsNews(countryCode: String, pageNumber: Int)=RetrofitInstance.api.getSportsNews(countryCode = countryCode, pageNumber = pageNumber)

    suspend fun getEntertainmentNews(countryCode: String, pageNumber: Int)= RetrofitInstance.api.getEntertainmentNews(countryCode=countryCode, pageNumber = pageNumber)

    suspend fun getHealthNews(countryCode: String, pageNumber: Int)= RetrofitInstance.api.getHealthNews(countryCode = countryCode, pageNumber = pageNumber)

    suspend fun getScienceNews(countryCode: String, pageNumber: Int)= RetrofitInstance.api.getScienceNews(countryCode=countryCode, pageNumber = pageNumber)

    suspend fun getTechnologyNews(countryCode: String, pageNumber: Int)= RetrofitInstance.api.getTechnologyNews(countryCode=countryCode, pageNumber = pageNumber)


}