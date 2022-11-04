package com.example.newsapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NewsFeedTest: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newsfeed)


        val imageModelArrayList = populateList()

        val recyclerView = findViewById<View>(R.id.news_recycler_view) as RecyclerView // Bind to the recyclerview in the layout
        val layoutManager = LinearLayoutManager(this) // Get the layout manager
        recyclerView.layoutManager = layoutManager
        val mAdapter = NewsRecyclerAdapter(imageModelArrayList)
        recyclerView.adapter = mAdapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL)
        )


    }

    private fun populateList(): ArrayList<NewsArticle> {
        val list = ArrayList<NewsArticle>()
        val myImageList = arrayOf(R.drawable.profile_example, R.drawable.profile_example, R.drawable.profile_example, R.drawable.profile_example)
        val myImageNameList = arrayOf("Headline 1", "Headline 2", "Headline 3", "Headline 4")
        val newsContentList = arrayOf("Content 1", "Content 2", "Content 3", "Content 4")

        for (i in 0..3) {
            val imageModel = NewsArticle()
            imageModel.setHeadline(myImageNameList[i])
            imageModel.setContent(newsContentList[i])
            imageModel.setImages(myImageList[i])
            list.add(imageModel)
        }
        return list
    }

    }
