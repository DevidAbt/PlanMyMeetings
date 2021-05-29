package com.example.planmymeetings.screens.appointments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.core.view.MenuItemCompat
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

        for (i in 1..30) {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.appointments_menu, menu)
        val menuItem = menu?.findItem(R.id.search_bar)
        if (menuItem != null) {
            val searchView = MenuItemCompat.getActionView(menuItem) as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(s: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(s: String?): Boolean {
                    mAdapter.filter.filter(s)
                    return false
                }
            })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.log_out -> {
                FirebaseAuth.getInstance().signOut()
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}