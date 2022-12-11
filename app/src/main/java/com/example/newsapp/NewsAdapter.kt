package com.example.newsapp

import android.util.Property
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.RowNewsArticleBinding
import com.example.newsapp.model.Article
import com.squareup.picasso.Picasso

class NewsAdapter : ListAdapter<Article, NewsAdapter.ViewHolder>(NewsArticleDiffUtil()) {

    // Holds each row in recyclerview
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentArticle = getItem(position)
        holder.bind(currentArticle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    //Use view binding to row_news_article
    class ViewHolder(val binding: RowNewsArticleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.article = article
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = RowNewsArticleBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
}



// Diff Util Class
private class NewsArticleDiffUtil : DiffUtil.ItemCallback<Article>() {

    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.articleID == newItem.articleID
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

}

