package com.example.planmymeetings.screens.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.planmymeetings.R
import com.example.planmymeetings.screens.register.RegisterActivity
import com.example.planmymeetings.databinding.ActivityLoginBinding
import com.example.planmymeetings.screens.appointments.AppointmentsActivity

class LoginActivity : AppCompatActivity() {
    private val LOG_TAG = RegisterActivity::class.java.name

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.loginViewModel = viewModel

        viewModel.toastMessage.observe(this, {
            if (!it.isNullOrEmpty()) {
                viewModel.resetToastMessage()
                Log.d(LOG_TAG, it)
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.loggedInEvent.observe(this, {
            if (it) {
                viewModel.resetLoggedInEvent()
                gotoAppointments()
            }
        })
    }

    private fun gotoAppointments() {
        val intent = Intent(this, AppointmentsActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun cancel(view: View) = finish()
}