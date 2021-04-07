package com.example.planmymeetings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private val LOG_TAG = RegisterActivity::class.java.name

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    private lateinit var mAuth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide();
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        mAuth = FirebaseAuth.getInstance()
    }

    fun login(view: View) {
        if (emailEditText.text.isEmpty() || passwordEditText.text.isEmpty()) {
            Toast.makeText(this, "You must fill all fields.", Toast.LENGTH_SHORT).show()
            return
        }

        val userName = emailEditText.text.toString()
        val password: String = passwordEditText.text.toString()

        mAuth.signInWithEmailAndPassword(userName, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val successText = "Logged in successfully."
                Log.d(LOG_TAG, successText)
                Toast.makeText(this, successText, Toast.LENGTH_SHORT).show()
            } else {
                val errorText = "Login failed (${it.exception?.message})."
                Log.d(LOG_TAG, errorText)
                Toast.makeText(this, errorText, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun cancel(view: View) = finish()
}