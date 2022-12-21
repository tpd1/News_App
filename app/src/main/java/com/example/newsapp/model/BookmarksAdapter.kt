package com.example.newsapp.model

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.local.SavedArticleEntity
import com.example.newsapp.databinding.RowBookmarksBinding


class BookmarksAdapter (private val bookmarkClickListener: OnSavedArticleClickListener) : RecyclerView.Adapter<BookmarksAdapter.ViewHolder>() {
        private var savedArticles = mutableListOf<SavedArticleEntity>()

        interface OnSavedArticleClickListener {
            fun onSavedArticleClick(article: NewsArticle)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setList(list: MutableList<SavedArticleEntity>) {
            this.savedArticles = list
            notifyDataSetChanged()
        }

        fun getItem(position: Int): SavedArticleEntity {
            return savedArticles[position]
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RowBookmarksBinding.inflate(inflater, parent, false)
            return ViewHolder(binding)
        }

        //Holds each row in recyclerview
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val currentArticle = savedArticles[position]
            holder.bind(currentArticle)
        }

        //Use view binding to row_news_article
        inner class ViewHolder(private val binding: RowBookmarksBinding) :
            RecyclerView.ViewHolder(binding.root), View.OnClickListener {

            // Set up click listener here rather than in onBindViewHolder, is re-created less often.
            init {
                binding.root.setOnClickListener {
                    val pos = bindingAdapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        val savedArticleEntity = getItem(pos)
                        bookmarkClickListener.onSavedArticleClick(savedArticleEntity.article)
                    }
                }
            }

            override fun onClick(v: View?) {
                val position:Int = absoluteAdapterPosition
                bookmarkClickListener.onSavedArticleClick(savedArticles[position].article)
            }

            fun bind(savedArticleEntity: SavedArticleEntity) {
                // Use Glide library to fetch the image at the provided URL
                binding.bookmarksRowCardView.apply {
                    Glide.with(this)
                        .load(savedArticleEntity.article.imageUrl)
                        .centerCrop()
                        .error(R.drawable.image_error)
                        .into(binding.bookmarkNewsImage)
                }
                // Set other text fields.
                binding.bookmarkTopicText.text = savedArticleEntity.article.category[0].replaceFirstChar { it.uppercase() }
                binding.bookmarkNewsHeadline.text = savedArticleEntity.article.title
                binding.bookmarkSourceText.text = savedArticleEntity.article.sourceID.replaceFirstChar { it.uppercase() }
                binding.bookmarkDatePosted.text = savedArticleEntity.article.pubDate

                binding.executePendingBindings()

            }
        }

        override fun getItemCount(): Int {
            return savedArticles.size
        }
    }


    //Diff Util Class
//    private class NewsArticleDiffUtil : DiffUtil.ItemCallback<NewsArticle>() {
//
//        override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
//            return oldItem.title == newItem.title
//        }
//
//        override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
//            return oldItem == newItem
//        }
//
//    }
