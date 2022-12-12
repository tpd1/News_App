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
import com.example.newsapp.R
import com.example.newsapp.model.APIHandler
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.model.Article
import com.squareup.picasso.Picasso
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsFragment : Fragment(R.layout.fragment_news) {
    lateinit var adapter: NewsAdapter
    lateinit var model: Article

    private var newsfeedBinding: FragmentNewsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentNewsBinding.inflate(inflater, container, false)
        newsfeedBinding = binding
        return binding.root
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