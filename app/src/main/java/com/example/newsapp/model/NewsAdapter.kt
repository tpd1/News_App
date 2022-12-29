package com.example.newsapp.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.RowNewsArticleBinding

/**
 * Class to provide a source for the paging of news articles in the recyclerview.
 * Implemented by following tutorials from Florian Walther and Android code-labs:
 * https://github.com/codinginflow/ImageSearchApp
 * @property articleClickListener - click listener applied to articles in the recyclerview
 */
class NewsAdapter(private val articleClickListener: OnArticleClickListener) :
    PagingDataAdapter<NewsArticle, NewsAdapter.ViewHolder>(NewsArticleDiffUtil()) {

    interface OnArticleClickListener {
        fun onArticleClick(article: NewsArticle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowNewsArticleBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    //Holds each row in recyclerview
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentArticle = getItem(position)
        if (currentArticle != null) {
            holder.bind(currentArticle)
        }
    }

    //Use view binding to row_news_article
    inner class ViewHolder(private val binding: RowNewsArticleBinding) :
        RecyclerView.ViewHolder(binding.root)
    {
        // Set up click listener here rather than in onBindViewHolder, is re-created less often.
        init {
            binding.root.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    val article = getItem(pos)
                    if (article != null) {
                        articleClickListener.onArticleClick(article)
                    }
                }
            }
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

    /**
     * Required for paging library. Checks whether items in the recyclerview are the same.
     */
    private class NewsArticleDiffUtil : DiffUtil.ItemCallback<NewsArticle>() {

        override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
            return oldItem.link == newItem.link
        }

        override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
            return oldItem == newItem
        }
    }
}

