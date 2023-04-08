package com.example.news

import android.view.View
import com.example.news.DataModel.Article

interface ItemClickListener {
    fun onItemClick(view: View, article: Article)
}