package com.example.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.Constants.Companion.ENTER_PASSWORD
import com.example.newsapp.Constants.Companion.LOGIN_SUCCESS
import com.example.newsapp.Constants.Companion.LOGIN_UNSUCCESSFUL
import com.example.newsapp.Constants.Companion.NON_VALID_EMAIL
import com.example.newsapp.Constants.Companion.NOT_MATCHING
import com.example.newsapp.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

/**
 * Provides functionality to the register fragment UI. Allows the user to sign up to the app
 * using their email. Registers the user using Firebase.
 */
class RegisterActivity : AppCompatActivity() {
    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get firebase instance.
        firebaseAuth = FirebaseAuth.getInstance()

        //Set up View binding for activity
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        // Set on click listener for register button.
        registerBinding.registerButton.setOnClickListener {
            // Get user entered fields.
            val userEmail = registerBinding.registerEmail.text.toString()
            val userPassword = registerBinding.registerPassword.text.toString()
            val confirmPass = registerBinding.registerConfirmPass.text.toString()
            val userName = registerBinding.registerName.text.toString()

            if (isValidInput(userEmail, userPassword, confirmPass)) {
                // Create new user in Firebase
                firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Snackbar.make(
                                registerBinding.root,
                                LOGIN_SUCCESS,
                                Snackbar.LENGTH_SHORT
                            ).show()

                            // Set the user name
                            val user = firebaseAuth.currentUser
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(userName)
                                .build()
                            user?.updateProfile(profileUpdates)
                            navigateToLogin()
                        } else {
                            Snackbar.make(
                                registerBinding.root,
                                LOGIN_UNSUCCESSFUL,
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
        // add on click listener to login button.
        registerBinding.backToLoginButton.setOnClickListener {
            navigateToLogin()
        }
    }

    /**
     * Checks Whether the email and password is in a valid format.
     * @param email The email address entered.
     * @param password The password entered by the user.
     * @param confirmPass The validation password entered by the user.
     * @return true if the email and password is valid, false otherwise.
     */
    private fun isValidInput(email: String, password: String, confirmPass: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()) {
            registerBinding.registerEmail.error = NON_VALID_EMAIL
            return false
        }
        if (password.isEmpty() || password.length < 6) {
            registerBinding.registerPassword.error = ENTER_PASSWORD
            return false
        }

        if (confirmPass.isEmpty() || confirmPass != password) {
            registerBinding.registerConfirmPass.error = NOT_MATCHING
            return false
        }
        return true
    }

    /**
     * Helper function - Navigates to the login activity.
     */
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}

