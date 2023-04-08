package com.example.news.Adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.DataModel.Article
import com.example.news.Fragments.BreakingNewsFragmentDirections
import com.example.news.Fragments.SavedNewsFragmentDirections
import com.example.news.Fragments.SearchNewsFragmentDirections
import com.example.news.ItemClickListener
import com.example.news.R

class NewsAdapter(val itemClickListener: ItemClickListener): RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {


    class ArticleViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val source: TextView=view.findViewById(R.id.tvSource)
        val desc: TextView=view.findViewById(R.id.tvDescription)
        val title: TextView=view.findViewById(R.id.tvTitle)
        val image: ImageView=view.findViewById(R.id.ivArticleImage)
        val publishedAt: TextView=view.findViewById(R.id.tvPublishedAt)
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

//    private var onItemClickListener: ((Article)-> Unit)? = null
//
//    fun setOnItemClickListener(listener : (Article)-> Unit){
//        onItemClickListener=listener
//    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article: Article = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(holder.itemView.context).load(article.urlToImage).into(holder.image)
            holder.source.text = article.source.name
            holder.title.text = article.title
            holder.desc.text = article.description
            holder.publishedAt.text = article.publishedAt
        }

        holder.itemView.setOnClickListener {
            Log.w("TEJAS", "Item Clicked")
            itemClickListener.onItemClick(it, article)
        }
    }



}