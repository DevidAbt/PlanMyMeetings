package com.example.planmymeetings

import java.util.*
import kotlin.collections.ArrayList

enum class AppointmentStateType {
    initialized,
    confirmed,
    cancelled,
    completed,
    failed
}

data class Appointment(
    val id: Int,
    val category: String,
    val creationDate: Date,
    val description: String,
    val lastUpdate: Date,
    val status: AppointmentStateType,
    val validFor: Date,

    val notes: List<Note>,
    val relatedPlace: String?,
) {
    // needed for firebase
    constructor(): this(-1, defaultString, defaultDate, defaultString, defaultDate, AppointmentStateType.initialized, defaultDate, defaultNotes, null)
}

data class Note(
    val id: Int,
    val author: String,
    val date: Date,
    val text: String,
) {
    // needed for firebase
    constructor() : this(-1, defaultString, defaultDate, defaultString)
}

private const val defaultString = ""
private val defaultDate = Date()
private val defaultNotes = ArrayList<Note>()
