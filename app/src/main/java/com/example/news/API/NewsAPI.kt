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
        apiKey:String="a7049628798b4fe2875ca95af30922d3"
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int=1,
        @Query("apiKey")
        apiKey:String="a7049628798b4fe2875ca95af30922d3"
    ): Response<NewsResponse>
}