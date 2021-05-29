package com.example.planmymeetings.screens.appointments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.planmymeetings.Appointment
import com.example.planmymeetings.AppointmentStateType
import com.example.planmymeetings.R
import com.example.planmymeetings.databinding.ActivityAppointmentsBinding
import com.example.planmymeetings.screens.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.*
import kotlin.collections.ArrayList

class AppointmentsActivity : AppCompatActivity() {
    private val LOG_TAG = AppointmentsActivity::class.java.name

    private lateinit var binding: ActivityAppointmentsBinding
    private lateinit var viewModel: AppointmentsViewModel

    private lateinit var user: FirebaseUser
    private lateinit var mAuth: FirebaseAuth

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mItemList: ArrayList<Appointment>
    private lateinit var mAdapter: AppointmentItemAdapter

    private val gridNumber: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_appointments)

        viewModel = ViewModelProvider(this).get(AppointmentsViewModel::class.java)
        binding.viewModel = viewModel

        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            user = mAuth.currentUser!!
            Log.d(LOG_TAG, "Authenticated user")
        } else {
            Log.d(LOG_TAG, "Unauthenticated user")
            finish()
        }

        mRecyclerView = binding.appointmentsRecycleView
        mRecyclerView.layoutManager = GridLayoutManager(this, gridNumber)
        mItemList = ArrayList()

        mAdapter = AppointmentItemAdapter(this, mItemList, AppointmentListener {
            Log.d(LOG_TAG, it)
        })
        mRecyclerView.adapter = mAdapter

        initializeData()
    }

    private fun initializeData() {
        mItemList.clear()

        for (i in 1..10) {
            mItemList.add(
                Appointment(
                    i.toString(),
                    "meeting",
                    Date(2021),
                    "description$i",
                    Date(7483),
                    AppointmentStateType.initialized,
                    Date(8120),
                    ArrayList(),
                    ArrayList(),
                    "place$i"
                )
            )
        }

        mAdapter.notifyDataSetChanged()
    }
}