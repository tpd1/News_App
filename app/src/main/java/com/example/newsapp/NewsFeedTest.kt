package com.example.newsapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

    }

    private fun populateList(): ArrayList<NewsArticle> {
        val list = ArrayList<NewsArticle>()
        val myImageList = arrayOf(R.drawable.pinhead, R.drawable.profile_example, R.drawable.profile_example, R.drawable.profile_example)
        val publisherIcon = arrayOf(R.drawable.reuters, R.drawable.bbc_logo_big, R.drawable.profile_example, R.drawable.profile_example)
        val myImageNameList = arrayOf("TIMDB Rates Hellraiser 10/10. \"The greatest film of all time\"", "Headline 2", "Headline 3", "Headline 4")
//        val newsContentList = arrayOf("By Tim Deville", "Content 2", "Content 3", "Content 4")

        for (i in 0..3) {
            val imageModel = NewsArticle()
            imageModel.setHeadline(myImageNameList[i])
            imageModel.setPublisherIcon(publisherIcon[i])
            imageModel.setImages(myImageList[i])
            list.add(imageModel)
        }
        return list
    }

    }
