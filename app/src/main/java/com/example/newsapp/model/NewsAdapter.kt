package com.example.newsapp.model

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.RowNewsArticleBinding
import java.util.*

class NewsAdapter(private val articleClickListener: OnArticleClickListener) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private var articles = mutableListOf<NewsArticle>()

    interface OnArticleClickListener {
        fun onArticleClick(article: NewsArticle)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: MutableList<NewsArticle>) {
        this.articles = list
        notifyDataSetChanged()
    }

    fun getItem(position: Int): NewsArticle {
        return articles[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowNewsArticleBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    //Holds each row in recyclerview
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentArticle = articles[position]
        holder.bind(currentArticle)
    }

    //Use view binding to row_news_article
    inner class ViewHolder(private val binding: RowNewsArticleBinding) :
        RecyclerView.ViewHolder(binding.root), OnClickListener {

        // Set up click listener here rather than in onBindViewHolder, is re-created less often.
        init {
            binding.root.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    val article = getItem(pos)
                    articleClickListener.onArticleClick(article)
                }
            }
        }

        override fun onClick(v: View?) {
            val position:Int = absoluteAdapterPosition
            articleClickListener.onArticleClick(articles[position])
        }

        fun bind(article: NewsArticle) {
            // Use Glide library to fetch the image at the provided URL
            binding.newsRowCardView.apply {
                Glide.with(this)
                    .load(article.imageUrl)
                    .centerCrop()
                    .error(R.drawable.settings_icon)
                    .into(binding.newsImage)
            }
            // Set other text fields.
            binding.topicText.text = article.category[0].replaceFirstChar { it.uppercase() }
            binding.newsHeadline.text = article.title
            binding.sourceText.text = article.sourceID.replaceFirstChar { it.uppercase() }
            binding.datePosted.text = article.pubDate

            binding.executePendingBindings()

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

