package com.example.news.API

import retrofit2.http.Query
import com.example.news.DataModel.NewsResponse
import retrofit2.Response
import retrofit2.http.GET


interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String="us",
        @Query("page")
        pageNumber: Int=1,
        @Query("apiKey")
        apiKey:String="21bee3506500443ea55b3e9ffb88dbee"
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int=1,
        @Query("apiKey")
        apiKey:String="21bee3506500443ea55b3e9ffb88dbee"
    ): Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun getBusinessNews(
        @Query("country")
        countryCode: String="us",
        @Query("category")
        category: String="business",
        @Query("page")
        pageNumber: Int=1,
        @Query("apiKey")
        apiKey:String="21bee3506500443ea55b3e9ffb88dbee"
    ): Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun getSportsNews(
        @Query("country")
        countryCode: String="us",
        @Query("category")
        category: String="sports",
        @Query("page")
        pageNumber: Int=1,
        @Query("apiKey")
        apiKey:String="21bee3506500443ea55b3e9ffb88dbee"
    ): Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun getEntertainmentNews(
        @Query("country")
        countryCode: String="us",
        @Query("category")
        category: String="entertainment",
        @Query("page")
        pageNumber: Int=1,
        @Query("apiKey")
        apiKey:String="21bee3506500443ea55b3e9ffb88dbee"
    ): Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun getHealthNews(
        @Query("country")
        countryCode: String="us",
        @Query("category")
        category: String="health",
        @Query("page")
        pageNumber: Int=1,
        @Query("apiKey")
        apiKey:String="21bee3506500443ea55b3e9ffb88dbee"
    ): Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun getScienceNews(
        @Query("country")
        countryCode: String="us",
        @Query("category")
        category: String="science",
        @Query("page")
        pageNumber: Int=1,
        @Query("apiKey")
        apiKey:String="21bee3506500443ea55b3e9ffb88dbee"
    ): Response<NewsResponse>


    @GET("v2/top-headlines")
    suspend fun getTechnologyNews(
        @Query("country")
        countryCode: String="us",
        @Query("category")
        category: String="technology",
        @Query("page")
        pageNumber: Int=1,
        @Query("apiKey")
        apiKey:String="21bee3506500443ea55b3e9ffb88dbee"
    ): Response<NewsResponse>




}