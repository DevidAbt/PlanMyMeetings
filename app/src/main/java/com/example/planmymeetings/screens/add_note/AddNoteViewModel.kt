package com.example.planmymeetings.screens.add_note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.planmymeetings.FirebaseService
import com.example.planmymeetings.Note
import com.google.firebase.auth.FirebaseUser
import java.util.*

class AddNoteViewModel : ViewModel() {
    lateinit var user: FirebaseUser
    var appointmentId: Int = -1

    val id = MutableLiveData<String>()
    val text = MutableLiveData<String>()

    private val _eventFinishActivity = MutableLiveData<Boolean>()
    val eventFinishActivity: LiveData<Boolean>
        get() = _eventFinishActivity

    init {
        id.value = ""
        text.value = ""
    }

    fun add() {
        val note = Note(
            id.value!!.toInt(),
            user.displayName ?: "",
            Calendar.getInstance().time,
            text.value.toString()
        )
        FirebaseService.addNoteToAppointment(appointmentId, note)
        _eventFinishActivity.value = true
    }
}