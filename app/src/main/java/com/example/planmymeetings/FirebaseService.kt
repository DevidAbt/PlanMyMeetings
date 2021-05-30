package com.example.planmymeetings

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.planmymeetings.screens.appointments.AppointmentItemAdapter
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.collections.ArrayList

object FirebaseService {
    private val LOG_TAG = FirebaseService::class.java.name
    private val mFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val mAppointmentRefs: CollectionReference

    init {
        mAppointmentRefs = mFireStore.collection("Appointments")
    }

    private val random = Random()

    private fun uploadTestData() {
        for (i in 1..30) {
            val notes = ArrayList<Note>()
            for (j in 1..5) {
                notes.add(
                    Note(
                        j,
                        "Alice$j",
                        Calendar.getInstance().time,
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
                    )
                )
            }
            mAppointmentRefs.add(
                Appointment(
                    i,
                    "meeting$i",
                    Calendar.getInstance().time,
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                    Calendar.getInstance().time,
                    AppointmentStateType.values()[random.nextInt(5)],
                    Calendar.getInstance().time,
                    notes,
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

    fun getAppointmentById(id: Int): Task<QuerySnapshot> {
        return mAppointmentRefs.whereEqualTo("id", id).limit(1).get()
    }

    fun removeAppointmentById(id: Int, adapter: AppointmentItemAdapter) {
        getAppointmentById(id).onSuccessTask { querySnapshot ->
            querySnapshot?.documents?.first()?.reference!!.delete().addOnSuccessListener {
                queryData(adapter)
                Log.d(LOG_TAG, "appointment removed ($id)")
            }
        }
    }

    fun upadteAppointmentStateById(id: Int, stateString: String) {
        getAppointmentById(id).onSuccessTask {
            it?.documents?.first()?.reference!!.update("status", stateString).addOnSuccessListener {
                Log.d(LOG_TAG, "appointment updated ($id, $stateString)")
            }
        }
    }
}