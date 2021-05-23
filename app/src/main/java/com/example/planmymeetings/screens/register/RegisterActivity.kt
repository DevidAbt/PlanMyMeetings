package com.example.planmymeetings.screens.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.planmymeetings.R
import com.example.planmymeetings.databinding.ActivityLoginBinding
import com.example.planmymeetings.databinding.ActivityRegisterBinding
import com.example.planmymeetings.screens.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private val LOG_TAG = RegisterActivity::class.java.name
    private val PREF_KEY = RegisterActivity::class.java.`package`?.toString()
    private val SECRET_KEY = 123;

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        binding.registerViewModel = viewModel

        viewModel.eventFinishActivity.observe(this, {
            if (it) {
                finish()
            }
        })

        viewModel.toastMessage.observe(this, {
            if (!it.isNullOrEmpty()) {
                viewModel.resetToastMessage()
                Log.d(LOG_TAG, it)
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun cancel(view: View) = finish()
}