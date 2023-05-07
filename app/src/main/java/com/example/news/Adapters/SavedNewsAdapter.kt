package com.example.news.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.DataModel.Article
import com.example.news.ItemClickListener
import com.example.news.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SavedNewsAdapter(val itemClickListener: ItemClickListener): RecyclerView.Adapter<SavedNewsAdapter.ArticleViewHolder>() {
    private var list : List<Article> = emptyList<Article>()


    fun setData(l: List<Article>){
        this.list=l
        notifyDataSetChanged()
    }

    class ArticleViewHolder(view: View): RecyclerView.ViewHolder(view){
        val source: TextView =view.findViewById(R.id.tvSource)
        val desc: TextView =view.findViewById(R.id.tvDescription)
        val title: TextView =view.findViewById(R.id.tvTitle)
        val image: ImageView =view.findViewById(R.id.ivArticleImage)
        val publishedAt: TextView =view.findViewById(R.id.tvPublishedAt)
        val savebtn: FloatingActionButton=view.findViewById(R.id.saveButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = list[position]
        holder.savebtn.setImageResource(R.drawable.ic_delete)
        Glide.with(holder.itemView.context).load(article.urlToImage).into(holder.image)
        if(article.source!=null)  holder.source.text = article.source.name
        holder.title.text = article.title
        holder.desc.text = article.description
        holder.publishedAt.text = article.publishedAt

        holder.savebtn.setOnClickListener {
            itemClickListener.onSaveButtonClicked(it, article)
        }
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(it, article)
        }
    }
}