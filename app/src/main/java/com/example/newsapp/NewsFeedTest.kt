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
        val myImageList = arrayOf(R.drawable.twitter_article_image, R.drawable.bbc_article_image, R.drawable.shark_article_image)
        val publisherIcon = arrayOf(R.drawable.reuters, R.drawable.bbc_logo_big, R.drawable.guardian_logo)
        val myCategoryList = arrayOf("Technology", "General", "Science")
        val myDateList = arrayOf("Yesterday", "8:15am", "2 days ago")
        val myImageNameList = arrayOf(
            "Twitter says 50% of staff laid off, moves to reassure on content moderation",
            "Train strikes: Passengers warned of disruption after walkouts suspended",
            "Scientists discover ‘world’s largest’ seagrass forest – by strapping cameras to sharks")

        for (i in 0..2) {
            val imageModel = NewsArticle()
            imageModel.setHeadline(myImageNameList[i])
            imageModel.setCategory(myCategoryList[i])
            imageModel.setArticleDate(myDateList[i])

            imageModel.setPublisherIcon(publisherIcon[i])
            imageModel.setImages(myImageList[i])
            list.add(imageModel)
        }
        return list
    }

    }
