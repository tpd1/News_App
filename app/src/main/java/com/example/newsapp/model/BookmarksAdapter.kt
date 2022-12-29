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

/**
 * RecyclerView adapter class for displaying bookmarked articles to the user.
 * @property bookmarkClickListener Allows the user to click on a single article in the list.
 */
class BookmarksAdapter(private val bookmarkClickListener: OnSavedArticleClickListener) :
    RecyclerView.Adapter<BookmarksAdapter.ViewHolder>() {
    private var savedArticles = mutableListOf<SavedArticleEntity>()

    interface OnSavedArticleClickListener {
        fun onSavedArticleClick(article: NewsArticle)
    }

    /**
     * Assigns the list of saved articles.
     * @param list The list to be assigned.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: MutableList<SavedArticleEntity>) {
        this.savedArticles = list
        notifyDataSetChanged()
    }

    /**
     * Returns a single item from the list.
     * @param position The index of the item to be returned.
     * @return The saved article at the specified position.
     */
    fun getItem(position: Int): SavedArticleEntity {
        return savedArticles[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowBookmarksBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentArticle = savedArticles[position]
        holder.bind(currentArticle)
    }

    /**
     * Describes the view and it's place in the RecyclerView.
     */
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

        /**
         * Processes onClick event
         * @param v view associated with click.
         */
        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            bookmarkClickListener.onSavedArticleClick(savedArticles[position].article)
        }

        /**
         * Applies the UI changes to a single news row layout.
         * @param savedArticleEntity The single article to be used to update UI.
         */
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
            binding.bookmarkTopicText.text =
                savedArticleEntity.article.category[0].replaceFirstChar { it.uppercase() }
            binding.bookmarkNewsHeadline.text = savedArticleEntity.article.title
            binding.bookmarkSourceText.text =
                savedArticleEntity.article.sourceID.replaceFirstChar { it.uppercase() }
            binding.bookmarkDatePosted.text = savedArticleEntity.article.pubDate

            binding.executePendingBindings()

        }
    }

    /**
     * Returns the number of items in the saved article list.
     * @return The number of saved articles.
     */
    override fun getItemCount(): Int {
        return savedArticles.size
    }
}

