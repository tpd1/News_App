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

/**
 * Provides functionality to the user profile fragment UI. Allows the user to navigate to
 * the various sections of the app from the profile screen.
 */
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

        // Get the firebase instance and set the user name in the UI.
        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser
        val name = user?.displayName
        val nameText = "Welcome \n$name"
        profileBinding.nameText.text = nameText

        // Set up on-click listeners for topic selection navigation button
        profileBinding.chooseTopicsCard.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToTopicSelectFragment()
            findNavController().navigate(action)
        }

        // Set up on-click listeners for account management navigation button
        profileBinding.manageAccountCard.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToManageAccountFragment()
            findNavController().navigate(action)
        }

        // Set up on-click listeners for app settings navigation button
        profileBinding.appSettingsCard.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToAppSettingsFragment()
            findNavController().navigate(action)
        }

        // Set up on-click listeners for bookmarks navigation button
        profileBinding.bookmarksCard.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToBookmarksFragment()
            findNavController().navigate(action)
        }

        // Set up on-click listeners for sign out button.
        profileBinding.signOutButton.setOnClickListener {
            val intent = Intent(this.context, LoginActivity::class.java)
            // Sign user out of Firebase and close Main Activity.
            firebaseAuth.signOut()
            startActivity(intent)
            requireActivity().finish()
        }
        return binding.root
    }

}
