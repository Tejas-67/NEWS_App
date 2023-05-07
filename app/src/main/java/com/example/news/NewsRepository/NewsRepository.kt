package com.example.news.NewsRepository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.news.API.RetrofitInstance
import com.example.news.DataModel.Article
import com.example.news.Database.ArticleDao
import com.example.news.Database.ArticleDatabase

class NewsRepository(val db: ArticleDatabase){


    private val dao: ArticleDao = db.getArticleDao()
    val readAllData: LiveData<List<Article>> = dao.getAllArticles()

    suspend fun addToLocal(article: Article){
        dao.insert(article)
    }

    suspend fun deleteFromLocal(article: Article){
        dao.deleteArticle(article)
    }

    fun getAllSaved(): LiveData<List<Article>>{
        return dao.getAllArticles()
    }


    suspend fun getBreakingNews(countryCode: String, pageNumber: Int)=
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int)=
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun getBusinessNews()= RetrofitInstance.api.getBusinessNews()

    suspend fun getSportsNews()=RetrofitInstance.api.getSportsNews()

    suspend fun getEntertainmentNews()= RetrofitInstance.api.getEntertainmentNews()

    suspend fun getHealthNews()= RetrofitInstance.api.getHealthNews()

    suspend fun getScienceNews()= RetrofitInstance.api.getScienceNews()

    suspend fun getTechnologyNews()= RetrofitInstance.api.getTechnologyNews()


}