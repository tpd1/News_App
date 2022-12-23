package com.example.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.Constants.Companion.ENTER_PASSWORD
import com.example.newsapp.Constants.Companion.LOGIN_SUCCESS
import com.example.newsapp.Constants.Companion.LOGIN_UNSUCCESSFUL
import com.example.newsapp.Constants.Companion.NON_VALID_EMAIL
import com.example.newsapp.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

        //Set up View binding for activity
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        registerBinding.registerButton.setOnClickListener {
            val userEmail = registerBinding.registerEmail.text.toString()
            val userPassword = registerBinding.registerPassword.text.toString()

            if (isValidInput(userEmail, userPassword)) {
                firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Snackbar.make(
                                registerBinding.root,
                                LOGIN_SUCCESS,
                                Snackbar.LENGTH_SHORT
                            ).show()
                            navigateToLogin()
                            finish()
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

        registerBinding.backToLoginButton.setOnClickListener {
            navigateToLogin()
        }

    }

    private fun isValidInput(email: String, password: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()) {
            registerBinding.registerEmail.error = NON_VALID_EMAIL
            return false
        }
        if (password.isEmpty() || password.length < 6) {
            registerBinding.registerPassword.error = ENTER_PASSWORD
            return false
        }
        return true
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}

