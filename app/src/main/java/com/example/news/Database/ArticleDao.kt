package com.example.news.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.news.DataModel.Article

@Dao
interface ArticleDao {
    @Insert(onConflict=OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}