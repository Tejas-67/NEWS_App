package com.example.news.Adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.DataModel.Article
import com.example.news.ItemClickListener
import com.example.news.R

class NewsAdapter(val itemClickListener: ItemClickListener): RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {


    class ArticleViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val source: TextView=view.findViewById(R.id.tvSource)
        val title: TextView=view.findViewById(R.id.tvTitle)
        val image: ImageView=view.findViewById(R.id.ivArticleImage)
        val savebtn: ImageView =view.findViewById(R.id.saveButton)
    }


    private val differCallback=object: DiffUtil.ItemCallback<Article>(){
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url==newItem.url
        }

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }
    }

    val differ= AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article: Article = differ.currentList[position]

        Glide.with(holder.itemView.context).load(article.urlToImage).into(holder.image)
        if(article.author!=null) holder.source.text = "By ${article.author}."
        else holder.source.text= "By unknown."
        holder.title.text = article.title

        holder.savebtn.setOnClickListener {
            itemClickListener.onSaveButtonClicked(it, article)
        }
        holder.itemView.setOnClickListener {
            Log.w("TEJAS", "Item Clicked")
            itemClickListener.onItemClick(it, article)
        }

    }
}