package com.example.news.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.news.DataModel.Article

@Dao
interface ArticleDao {
    @Insert(onConflict=OnConflictStrategy.ABORT)
    fun insert(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    fun deleteArticle(article: Article)
}