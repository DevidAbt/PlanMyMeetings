package com.example.planmymeetings.screens.appointment_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.planmymeetings.Appointment
import com.example.planmymeetings.AppointmentStateType
import com.example.planmymeetings.Note
import com.example.planmymeetings.R
import com.example.planmymeetings.databinding.ActivityAppointmentDetailsBinding
import java.util.*
import kotlin.collections.ArrayList

class AppointmentDetailsActivity : AppCompatActivity() {
    private val LOG_TAG = AppointmentDetailsActivity::class.java.name

    private lateinit var binding: ActivityAppointmentDetailsBinding
    private lateinit var viewModel: AppointmentDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appointmentId: String = intent.getStringExtra("appointmentId")!!

        val note = Note("1", "Alice", Date(32445), "note text")
        val notes = arrayListOf<Note>(note)
        val appointment = Appointment(
            "4",
            "meeting",
            Date(2021),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            Date(7483),
            AppointmentStateType.initialized,
            Date(20378),
            notes,
            "Szeged, Széchenyi square"
        )

        binding = DataBindingUtil.setContentView(this, R.layout.activity_appointment_details)

        viewModel = ViewModelProvider(this).get(AppointmentDetailsViewModel::class.java)
        viewModel.setAppointment(appointment)
        binding.viewModel = viewModel

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.state_types, R.layout.state_spinner_item)
        adapter.setDropDownViewResource(R.layout.state_spinner_dropdown_item)
        binding.statusSpinner.adapter = adapter

        Log.d(LOG_TAG, "appointmentId: $appointmentId")
    }
}