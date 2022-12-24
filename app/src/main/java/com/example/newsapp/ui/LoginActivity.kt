package com.example.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.Constants.Companion.ENTER_PASSWORD
import com.example.newsapp.Constants.Companion.LOGIN_SUCCESS
import com.example.newsapp.Constants.Companion.LOGIN_UNSUCCESSFUL
import com.example.newsapp.Constants.Companion.NON_VALID_EMAIL
import com.example.newsapp.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var fireBase: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set up View binding for activity
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        fireBase = FirebaseAuth.getInstance()

        loginBinding.loginButton.setOnClickListener {
            val userEmail = loginBinding.loginEmail.text.toString()
            val userPassword = loginBinding.loginPassword.text.toString()

            if (isValidInput(userEmail, userPassword)) {
                fireBase.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Snackbar.make(loginBinding.root, LOGIN_SUCCESS, Snackbar.LENGTH_SHORT).show()
                        navigateToNews()
                        finish()
                    } else {
                        Snackbar.make(loginBinding.root, LOGIN_UNSUCCESSFUL, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Set on click listener for register button
        loginBinding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }


    private fun navigateToNews() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun isValidInput(email: String, password: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()) {
            loginBinding.loginEmail.error = NON_VALID_EMAIL
            return false
        }
        if (password.isEmpty() || password.length <= 6) {
            loginBinding.loginPassword.error = ENTER_PASSWORD
            return false
        }
        return true
    }


}

