package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.newsapp.Constants
import com.example.newsapp.NewsAdapter
import androidx.databinding.DataBindingUtil
import com.example.newsapp.model.APIHandler
import com.example.newsapp.R
import com.example.newsapp.database.ArticleDB
import com.example.newsapp.databinding.RowNewsArticleBinding
import com.example.newsapp.model.Article
import com.squareup.picasso.Picasso
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsFragment : Fragment() {
    lateinit var adapter: NewsAdapter
    lateinit var model: Article

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        //Define data binding
        val binding: RowNewsArticleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_news_feed, container, false)

        return null

    }


    private fun initiateAPIRequest() {
        val api = Retrofit.Builder()
            .baseUrl(Constants.ROOT_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIHandler::class.java)

    }

    private fun initiateRecyclerView() {



    }

    companion object {
        @BindingAdapter("fetchImgFromUrl")
        fun fetchImgFromUrl(imgView: ImageView, url: String) {
            Picasso
                .get()
                .load(url)
                .into(imgView)
        }


    }
}