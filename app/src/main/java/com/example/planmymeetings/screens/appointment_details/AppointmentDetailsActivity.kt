package com.example.planmymeetings.screens.appointment_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mItemList: ArrayList<Note>
    private lateinit var mAdapter: NoteItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_appointment_details)

        viewModel = ViewModelProvider(this).get(AppointmentDetailsViewModel::class.java)
        binding.viewModel = viewModel

        val layoutManager = LinearLayoutManager(this,  LinearLayoutManager.HORIZONTAL, false)

        mRecyclerView = binding.notesRecyclerView
        mRecyclerView.layoutManager = layoutManager
        mItemList = ArrayList()
        mAdapter = NoteItemAdapter(this, mItemList)
        mRecyclerView.adapter = mAdapter

        val adapter: ArrayAdapter<CharSequence> =
            ArrayAdapter.createFromResource(this, R.array.state_types, R.layout.state_spinner_item)
        adapter.setDropDownViewResource(R.layout.state_spinner_dropdown_item)
        binding.statusSpinner.adapter = adapter

        initializeData()
    }

    private fun initializeData() {
        val appointmentId: String = intent.getStringExtra("appointmentId")!!


        val notes = ArrayList<Note>()
        for (i in 1..5) {
            notes.add(
                Note(
                    i.toString(),
                    "Alice$i",
                    Date(54323),
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
                )
            )
        }
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
        viewModel.setAppointment(appointment)

        mItemList.clear()
        for (note in appointment.notes) {
            mItemList.add(note)
        }
        mAdapter.notifyDataSetChanged()
    }
}