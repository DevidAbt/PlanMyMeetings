package com.example.planmymeetings

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.planmymeetings.screens.appointments.AppointmentItemAdapter
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

object FirebaseService {
    private val LOG_TAG = FirebaseService::class.java.name
    private val mFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val mAppointmentRefs: CollectionReference

    init {
        mAppointmentRefs = mFireStore.collection("Appointments")
    }

    private fun uploadTestData() {
        for (i in 1..30) {
            mAppointmentRefs.add(
                Appointment(
                    i.toString(),
                    "meeting",
                    Date(2021),
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                    Date(7483),
                    AppointmentStateType.initialized,
                    Date(8120),
                    ArrayList(),
                    "place$i"
                )
            )
        }
    }

    fun queryData(adapter: AppointmentItemAdapter) {
        val appointments = ArrayList<Appointment>()
        mAppointmentRefs.orderBy("validFor").limit(50).get().addOnSuccessListener {
            for (document in it) {
                val appointment = document.toObject(Appointment::class.java)
                appointments.add(appointment)
            }

            if (appointments.size == 0) {
                uploadTestData()
                queryData(adapter)
            }
            adapter.updateData(appointments)
        }.addOnFailureListener {
            Log.e(LOG_TAG, it.toString())
        }
    }
}