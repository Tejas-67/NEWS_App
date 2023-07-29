package com.example.news.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.news.DataModel.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Insert
    fun insertArticle(article: Article)

    @Delete
    fun deleteArticle(article: Article)

    @Update
    fun updateArticle(article: Article)
}