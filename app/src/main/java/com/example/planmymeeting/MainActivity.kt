package com.example.planmymeetings

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val LOG_TAG = MainActivity::class.java.name
    private val PREF_KEY = MainActivity::class.java.`package`?.toString()
    private val SECRET_KEY = 123;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide();
        setContentView(R.layout.activity_main)

    }

    fun register(view: View) {
         val intent = Intent(this, RegisterActivity::class.java).apply {
             putExtra("SECRET_KEY", SECRET_KEY)
         }
        startActivity(intent)
    }
}