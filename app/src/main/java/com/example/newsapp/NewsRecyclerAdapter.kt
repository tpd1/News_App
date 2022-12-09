package com.example.newsapp
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsRecyclerAdapter(private val imageModelArrayList: MutableList<NewsArticle>):
    RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var imgView = itemView.findViewById<View>(R.id.news_image) as ImageView
        var publisherIcon = itemView.findViewById<View>(R.id.publisher_icon) as ImageView

        var newsHeadline = itemView.findViewById<View>(R.id.news_headline) as TextView
        var category = itemView.findViewById<View>(R.id.topic_text) as TextView
        var datePosted = itemView.findViewById<View>(R.id.date_posted) as TextView

    }

    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.row_news_article, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = imageModelArrayList[position]
        holder.imgView.setImageResource(info.modelImage)
        holder.publisherIcon.setImageResource(info.publisherIcon)

        holder.newsHeadline.text = info.headline
        holder.category.text = info.category
        holder.datePosted.text = info.articleDate

    }


}