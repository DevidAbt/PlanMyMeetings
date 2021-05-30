package com.example.planmymeetings.screens.add_appointment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.planmymeetings.Appointment
import com.example.planmymeetings.AppointmentStateType
import com.example.planmymeetings.FirebaseService
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddAppointmentViewModel : ViewModel() {
    val id = MutableLiveData<String>()
    val validFor = MutableLiveData<String>()
    val category = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val relatedPlace = MutableLiveData<String>()

    private val _eventFinishActivity = MutableLiveData<Boolean>()
    val eventFinishActivity: LiveData<Boolean>
        get() = _eventFinishActivity

    private val dateFormat = SimpleDateFormat("yyyy.MM.dd. HH:mm");

    init {
        id.value = ""
        validFor.value = ""
        category.value = ""
        description.value = ""
        relatedPlace.value = ""
    }

    fun add() {
        var validForDate: Date
        try {
            validForDate = dateFormat.parse(validFor.value!!)
        } catch (ignored: Exception) {
            return
        }

        val appointment = Appointment(
            id.value!!.toInt(),
            category.value!!,
            Calendar.getInstance().time,
            description.value!!,
            Calendar.getInstance().time,
            AppointmentStateType.initialized,
            validForDate,
            ArrayList(),
            relatedPlace.value
        )

        FirebaseService.addAppointment(appointment)
        _eventFinishActivity.value = true
    }
}