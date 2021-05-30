package com.example.planmymeetings.screens.add_note

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.planmymeetings.R
import com.example.planmymeetings.databinding.ActivityAddAppointmentBinding
import com.example.planmymeetings.databinding.ActivityRegisterBinding
import com.example.planmymeetings.screens.register.RegisterActivity
import com.example.planmymeetings.screens.register.RegisterViewModel

class AddAppointmentActivity : AppCompatActivity() {
    private val LOG_TAG = AddAppointmentActivity::class.java.name

    private lateinit var binding: ActivityAddAppointmentBinding
    private lateinit var viewModel: AddAppointmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_appointment)

        viewModel = ViewModelProvider(this).get(AddAppointmentViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.eventFinishActivity.observe(this, {
            if (it) {
                finish()
            }
        })
    }
}