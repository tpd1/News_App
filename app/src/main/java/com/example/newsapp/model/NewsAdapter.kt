package com.example.newsapp.model

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.RowNewsArticleBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var articles = mutableListOf<NewsArticle>()

    fun setList(list: MutableList<NewsArticle>) {
        this.articles = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    //Holds each row in recyclerview
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentArticle = articles[position]
        holder.bind(currentArticle)
    }

    //Use view binding to row_news_article
    class ViewHolder(private val binding: RowNewsArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: NewsArticle) {
            binding.newsRowCardView.apply {
                Log.i("Url: ", article.imageUrl.toString())
                Glide.with(this)
                    .load(article.imageUrl)
                    .centerCrop()
                    .error(R.drawable.settings_icon)
                    .into(binding.newsImage)

            }
            binding.newsHeadline.text = article.title
            binding.publisherIcon.text = article.sourceID
            binding.datePosted.text = article.pubDate

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder { //parent is RecyclerView
                val inflater = LayoutInflater.from(parent.context)
                val binding = RowNewsArticleBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    override fun getItemCount(): Int {
        return articles.size
    }
}


//Diff Util Class
private class NewsArticleDiffUtil : DiffUtil.ItemCallback<NewsArticle>() {

    override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
        return oldItem == newItem
    }

}

