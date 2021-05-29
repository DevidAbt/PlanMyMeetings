package com.example.planmymeetings.screens.appointment_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.planmymeetings.Appointment

class AppointmentDetailsViewModel : ViewModel() {
    private val _appointment = MutableLiveData<Appointment>()
    val appointment: LiveData<Appointment>
        get() = _appointment

    fun setAppointment(appointment: Appointment) {
        _appointment.value = appointment
    }
}