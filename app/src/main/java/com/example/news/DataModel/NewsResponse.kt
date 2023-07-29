package com.example.news.DataModel

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)