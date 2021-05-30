package com.example.planmymeetings.screens.appointments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.planmymeetings.*
import com.example.planmymeetings.databinding.ActivityAppointmentsBinding
import com.example.planmymeetings.screens.appointment_details.AppointmentDetailsActivity
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

        mAdapter = AppointmentItemAdapter(this, mItemList, AppointmentListener({
            Log.d(LOG_TAG, "clicked on item: $it")
            val intent = Intent(this, AppointmentDetailsActivity::class.java)
            intent.putExtra("appointmentId", it)
            startActivity(intent)
        }, { appointmentId ->
            FirebaseService.getAppointmentById(appointmentId).onSuccessTask { querySnapshot ->
                querySnapshot?.documents?.first()?.reference!!.delete().addOnSuccessListener {
                    FirebaseService.queryData(mAdapter)
                    Log.d(LOG_TAG, "appointment removed ($appointmentId)")
                }
            }
        }))
        mRecyclerView.adapter = mAdapter

        FirebaseService.queryData(mAdapter)
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