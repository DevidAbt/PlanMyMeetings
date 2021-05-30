package com.example.planmymeetings.screens.appointment_details

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.planmymeetings.*
import com.example.planmymeetings.databinding.ActivityAppointmentDetailsBinding
import java.util.*
import kotlin.collections.ArrayList

class AppointmentDetailsActivity : AppCompatActivity() {
    private val LOG_TAG = AppointmentDetailsActivity::class.java.name

    private lateinit var binding: ActivityAppointmentDetailsBinding

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mItemList: ArrayList<Note>
    private lateinit var mAdapter: NoteItemAdapter

    private lateinit var mSpinnerAdapter: ArrayAdapter<CharSequence>
    private val appointmentStateValues = AppointmentStateType.values()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appointmentId: Int = intent.getIntExtra("appointmentId", -1)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_appointment_details)
        binding.executePendingBindings()

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        mRecyclerView = binding.notesRecyclerView
        mRecyclerView.layoutManager = layoutManager
        mItemList = ArrayList()
        mAdapter = NoteItemAdapter(this, mItemList, NoteListener {
            FirebaseService.removeNote(it, mItemList, mAdapter)
        })
        mRecyclerView.adapter = mAdapter

        mSpinnerAdapter =
            ArrayAdapter.createFromResource(this, R.array.state_types, R.layout.state_spinner_item)
        mSpinnerAdapter.setDropDownViewResource(R.layout.state_spinner_dropdown_item)
        binding.statusSpinner.adapter = mSpinnerAdapter
        binding.statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                FirebaseService.upadteAppointmentStateById(
                    appointmentId,
                    appointmentStateValues[position].toString()
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // ignored
            }

        }

        fetchData(appointmentId)
    }

    private fun fetchData(appointmentId: Int) {
        FirebaseService.getAppointmentById(appointmentId).addOnSuccessListener {
            val appointment = it.toObjects(Appointment::class.java)[0]
            binding.validForText.text = dateToLongString(appointment.validFor)
            binding.relatedPlaceText.text = appointment.relatedPlace
            binding.categoryText.text = appointment.category
            binding.descriptionText.text = appointment.description
            binding.creationDateText.text = dateToLongString(appointment.creationDate)
            binding.lastUpdateText.text = dateToLongString(appointment.lastUpdate)
            setSpinner(appointment.status)

            mItemList.clear()
            for (note in appointment.notes) {
                mItemList.add(note)
            }
            mAdapter.notifyDataSetChanged()
        }
    }

    private fun setSpinner(state: AppointmentStateType) {
        val spinnerPosition = mSpinnerAdapter.getPosition(state.toString())
        binding.statusSpinner.setSelection(spinnerPosition)
    }
}