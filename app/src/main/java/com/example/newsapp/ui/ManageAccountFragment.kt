package com.example.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.newsapp.Constants
import com.example.newsapp.Constants.Companion.DIALOG_DELETE_ACC
import com.example.newsapp.Constants.Companion.NAME_SUCCESS
import com.example.newsapp.Constants.Companion.PASSWORD_SUCCESS
import com.example.newsapp.Constants.Companion.UNCHANGED_NAME
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentManageAccountBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

/**
 * Controls the UI elements of the manage_account fragment. Allows the user to update their
 * name and password, with the changes persisting across sessions through the use of Firebase.
 */
class ManageAccountFragment : Fragment(R.layout.fragment_manage_account) {

    private lateinit var manageAccountBinding: FragmentManageAccountBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentManageAccountBinding.inflate(inflater, container, false)
        manageAccountBinding = binding

        firebaseAuth = FirebaseAuth.getInstance()
        // Fill EditText boxes with current user's details.
        val user = firebaseAuth.currentUser
        val currentName = user?.displayName
        val email = user?.email
        manageAccountBinding.updateNameText.setText(currentName)
        manageAccountBinding.updateEmailText.setText(email)
        manageAccountBinding.updateEmailLayout.isEnabled = false

        // Set listener for "Update Name" button.
        manageAccountBinding.updateNameButton.setOnClickListener {
            val newName = manageAccountBinding.updateNameText.text.toString()
            if (newName != currentName) {
                updateName(newName)
            } else {
                Snackbar.make(
                    manageAccountBinding.root, UNCHANGED_NAME, Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        // Set listener for "Update Password" button.
        manageAccountBinding.updatePassButton.setOnClickListener {
            val newPass = manageAccountBinding.updatePasswordText.text.toString()
            val confirmPass = manageAccountBinding.updateConfirmPassText.text.toString()
            if (isValidInput(newPass, confirmPass)) {
                updatePassword(newPass)
            }
        }
        // Set listener for 'Delete Account' button.
        manageAccountBinding.delAccButton.setOnClickListener {
            confirmDelete()
        }
        return binding.root
    }

    /**
     * Provides a dialog box for the user to confirm the permanent deletion of their account.
     */
    private fun confirmDelete() {
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setTitle("Delete Account")
        builder.setMessage(DIALOG_DELETE_ACC)
        builder.setPositiveButton("Yes") { _, _ ->
            deleteAccount()
        }
        builder.setNegativeButton("No") { _, _ ->
        }
        val dialog = builder.create()
        dialog.show()
    }

    /**
     * Updates the password of the current user within Firebase.
     * @param newPassword The updated password of the current user.
     */
    private fun updatePassword(newPassword: String) {
        val user = firebaseAuth.currentUser
        user?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Snackbar.make(
                        manageAccountBinding.root,
                        PASSWORD_SUCCESS,
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    Log.i("Password", "Password update failed")
                }
            }
    }

    /**
     * Updates the current user's display name within Firebase
     * @param newName The updated name of the user.
     */
    private fun updateName(newName: String) {
        val user = firebaseAuth.currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(newName)  // Replace userName with the desired name
            .build()
        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Snackbar.make(
                        manageAccountBinding.root,
                        NAME_SUCCESS,
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    Log.i("User name", "User name failed")
                }
            }
    }

    /**
     * Deletes the user account from firebase permanently and returns to the login activity.
     */
    private fun deleteAccount() {
        firebaseAuth.currentUser?.delete()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this.context, LoginActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    Log.i("Delete Account", "Delete Account Failed")
                }
            }
    }

    /**
     * Checks whether the password is in a valid format.
     * @param password The password entered by the user.
     * @param confirmPass The validation password entered by the user.
     * @return true if the email and password is valid, false otherwise.
     */
    private fun isValidInput(password: String, confirmPass: String): Boolean {
        if (password.isEmpty() || password.length < 6) {
            manageAccountBinding.updatePasswordText.error = Constants.ENTER_PASSWORD
            return false
        }
        if (confirmPass != password) {
            manageAccountBinding.updateConfirmPassText.error = Constants.NOT_MATCHING
            return false
        }
        return true
    }

}