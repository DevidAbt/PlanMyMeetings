package com.example.planmymeetings.screens.add_note

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.planmymeetings.R
import com.example.planmymeetings.databinding.ActivityAddAppointmentBinding
import com.example.planmymeetings.databinding.ActivityAddNoteBinding
import com.example.planmymeetings.screens.add_appointment.AddAppointmentActivity
import com.example.planmymeetings.screens.add_appointment.AddAppointmentViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AddNoteActivity : AppCompatActivity() {
    private val LOG_TAG = AddNoteActivity::class.java.name

    private lateinit var user: FirebaseUser
    private lateinit var mAuth: FirebaseAuth

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var viewModel: AddNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide();

        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            user = mAuth.currentUser!!
            Log.d(LOG_TAG, "Authenticated user")
        } else {
            Log.d(LOG_TAG, "Unauthenticated user")
            finish()
        }

        val appointmentId = intent.getIntExtra("appointmentId", -1)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)

        viewModel = ViewModelProvider(this).get(AddNoteViewModel::class.java)
        viewModel.user = user
        viewModel.appointmentId = appointmentId
            binding.viewModel = viewModel

        viewModel.eventFinishActivity.observe(this, {
            if (it) {
                finish()
            }
        })
    }
}