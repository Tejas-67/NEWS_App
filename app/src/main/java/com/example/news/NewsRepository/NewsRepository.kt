package com.example.news.NewsRepository

import androidx.lifecycle.LiveData
import com.example.news.API.RetrofitInstance
import com.example.news.DataModel.Article
import com.example.news.Database.ArticleDao
import com.example.news.Database.ArticleDatabase

class NewsRepository(val db: ArticleDatabase){



    suspend fun getBreakingNews(countryCode: String, pageNumber: Int)=
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int)=
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

}