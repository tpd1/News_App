package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topics)

        val imageModelArrayList = populateList()

        val recyclerView = findViewById<View>(R.id.topic_recycler_view) as RecyclerView // Bind to the recyclerview in the layout
        val layoutManager = LinearLayoutManager(this) // Get the layout manager
        recyclerView.layoutManager = layoutManager
        val mAdapter = TopicRecyclerAdapter(imageModelArrayList)
        recyclerView.adapter = mAdapter
    }

    private fun populateList(): ArrayList<NewsTopic> {
        val list = ArrayList<NewsTopic>()
        val myImageList = arrayOf(R.drawable.business, R.drawable.entertainment, R.drawable.general, R.drawable.health,R.drawable.science ,R.drawable.sports, R.drawable.technology)
        val myImageNameList = arrayOf(R.string.category_business, R.string.category_entertainment, R.string.category_general,
        R.string.category_health, R.string.category_science, R.string.category_sports, R.string.category_technology)


        for (i in 0..6) {
            val imageModel = NewsTopic()
            imageModel.setNames(getString(myImageNameList[i]))
            imageModel.setImages(myImageList[i])
            list.add(imageModel)
        }
        return list
    }
}