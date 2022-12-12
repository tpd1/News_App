package com.example.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentProfileBinding
import com.example.newsapp.databinding.FragmentTopicSelectBinding
import com.google.android.material.card.MaterialCardView

class TopicSelectFragment: Fragment(R.layout.fragment_topic_select) {
    private var topicBinding: FragmentTopicSelectBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentTopicSelectBinding.inflate(inflater, container, false)
        topicBinding = binding
        return binding.root
    }


}