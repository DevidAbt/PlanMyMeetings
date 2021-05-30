package com.example.planmymeetings

import android.util.Log
import com.example.planmymeetings.screens.appointment_details.NoteItemAdapter
import com.example.planmymeetings.screens.appointments.AppointmentItemAdapter
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

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

    fun updateAppointmentStateById(id: Int, stateString: String) {
        getAppointmentById(id).onSuccessTask {
            val updateMap = HashMap<String, Any>()
            updateMap["status"] = stateString
            updateMap["lastUpdate"] = Calendar.getInstance().time
            it?.documents?.first()?.reference!!.update(updateMap).addOnSuccessListener {
                Log.d(LOG_TAG, "appointment updated ($id, $stateString)")
            }
        }
    }

    fun removeNote(note: Note, items: ArrayList<Note>, adapter: NoteItemAdapter) {
        mAppointmentRefs.whereArrayContains("notes", note).get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    Log.d(
                        LOG_TAG,
                        "appointments with noteId: ${document.toObject(Appointment::class.java)!!.id}"
                    )
                    val appointment = document.toObject(Appointment::class.java)
                    val newNotes = appointment?.notes?.filter { it.id != note.id }
                    items.clear()
                    for (newNote in newNotes!!) {
                        items.add(newNote)
                        adapter.notifyDataSetChanged()
                    }
                    document.reference.update("notes", newNotes).addOnSuccessListener {
                    }
                }
            }
    }

    fun addAppointment(appointment: Appointment) {
        mAppointmentRefs.add(appointment).addOnSuccessListener {
            Log.d(
                LOG_TAG,
                "appointment added ($appointment.id)"
            )
        }
    }

    fun addNoteToAppointment(appointmentId: Int, note: Note) {
        getAppointmentById(appointmentId).addOnSuccessListener {
            var notes = it.toObjects(Appointment::class.java)[0].notes
            val mutableNotes = notes.toMutableList()
            mutableNotes.add(note)
            notes = mutableNotes
            it?.documents?.first()?.reference!!.update("notes", notes).addOnSuccessListener {
                Log.d(
                    LOG_TAG,
                    "note added ($note.id)"
                )
            }
        }
    }
}