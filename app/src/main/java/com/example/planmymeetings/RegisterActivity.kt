package com.example.planmymeetings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private val LOG_TAG = RegisterActivity::class.java.name
    private val PREF_KEY = RegisterActivity::class.java.`package`?.toString()
    private val SECRET_KEY = 123;

    private lateinit var userNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordAgainEditText: EditText

    private lateinit var mAuth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide();
        setContentView(R.layout.activity_register)

        userNameEditText = findViewById(R.id.userNameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        passwordAgainEditText = findViewById(R.id.passwordAgainEditText)

        mAuth = FirebaseAuth.getInstance()
    }

    fun register(view: View) {
        if (userNameEditText.text.isEmpty() || emailEditText.text.isEmpty() ||
            passwordEditText.text.isEmpty() || passwordAgainEditText.text.isEmpty()
        ) {
            Toast.makeText(this, "You must fill all fields.", Toast.LENGTH_SHORT).show()
            return
        }
        val userName = userNameEditText.text.toString()
        val email = emailEditText.text.toString()
        val password: String = passwordEditText.text.toString()
        val passwordAgain: String = passwordAgainEditText.text.toString()

        if (!password.equals(passwordAgain)) {
            Log.e(LOG_TAG, "The passwords are not the same.")
            return
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val successText = "User created successfully."
                Log.d(LOG_TAG, successText)
                Toast.makeText(this, successText, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                val errorText = "User creation failed (${it.exception?.message})."
                Log.d(LOG_TAG, errorText)
                Toast.makeText(this, errorText, Toast.LENGTH_LONG).show()
            }
        }

        Log.i(LOG_TAG, "Sign Up: $userName $email")
    }

    fun cancel(view: View) = finish()
}