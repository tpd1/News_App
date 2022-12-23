package com.example.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var profileBinding: FragmentProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        profileBinding = binding

        firebaseAuth = FirebaseAuth.getInstance()
        // Set up on-click listeners for buttons
        profileBinding.chooseTopicsCard.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToTopicSelectFragment()
            findNavController().navigate(action)
        }

        profileBinding.manageAccountCard.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToManageAccountFragment()
            findNavController().navigate(action)
        }

        profileBinding.appSettingsCard.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToAppSettingsFragment()
            findNavController().navigate(action)
        }

        profileBinding.bookmarksCard.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToBookmarksFragment()
            findNavController().navigate(action)
        }

        profileBinding.signOutButton.setOnClickListener {
            val intent = Intent(this.context, LoginActivity::class.java)
            firebaseAuth.signOut()
            startActivity(intent)
            requireActivity().finish()
        }


        return binding.root
    }

}
