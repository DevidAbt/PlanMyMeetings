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
    val id: String,
//    val href: String,
    val category: String,
    val creationDate: Date,
    val description: String,
//    val externalId: String,
    val lastUpdate: Date,
    val status: AppointmentStateType,
    val validFor: Date,

//    val calendarEvent: CalendarEventRef?,
    val notes: List<Note>,
//    val relatedEntity: List<RelatedEntity>,
//    val attachment: List<AttachmentRefOrValue>,
//    val contactMedium: List<ContactMedium>,
//    val relatedParty: List<RelatedParty>,
    val relatedPlace: String?,
) {
    constructor(): this("", "", defaultDate, "", defaultDate, AppointmentStateType.initialized, defaultDate, defaultNotes, null)
}

//data class CalendarEventRef(
//    val id: String,
//    val href: String,
//    val name: String,
//)

data class Note(
    val id: String,
    val author: String,
    val date: Date,
    val text: String,
)

//data class RelatedEntity(
//    val id: String,
//    val href: String,
//    val name: String,
//    val role: String,
//)

//data class AttachmentRefOrValue(
//    val id: String,
//    val href: String,
//    val attachmentType: String,
//    val description: String,
//    val isRef: Boolean,
//    val mimeType: String,
//    val name: String,
//    val url: String,
//    val size: Quantity,
//    val validFor: Date,
//)
//
//data class Quantity(
//    val amount: Float,
//    val units: String,
//)

//data class ContactMedium(
//    val mediumType: String,
//    val preferred: Boolean,
//    val validFor: Date,
//
//    val characteristic: MediumCharacteristic?,
//)

//data class MediumCharacteristic(
//    val city: String,
//    val country: String,
//    val emailAddress: String,
//    val faxNumber: String,
//    val phoneNumber: String,
//    val postCode: String,
//    val socialNetworkId: String,
//    val stateOrProvince: String,
//    val street1: String,
//    val street2: String,
//    val type: String,
//)

//data class RelatedParty(
//    val id: String,
//    val href: String,
//    val name: String,
//    val role: String,
//)

//data class RelatedPlaceRefOrValue(
//    val id: String,
//    val href: String,
//    val isRef: Boolean,
//    val name: String,
//    val role: String,
//)


data class SearchTimeSlot(
    val id: String,
    val href: String,
    val searchDate: Date,
    val searchResult: String,
    val status: String,

    val relatedPlace: String?,
//    val relatedEntity: List<RelatedEntity>,
//    val relatedParty: RelatedParty?,
    val requestedTimeSlot: List<TimeSlot>,
    val availableTimeSlot: List<TimeSlot>,
)

data class TimeSlot(
    val id: String,
    val href: String,
    val validFor: Date,

//    val relatedParty: RelatedParty?,
)

private val defaultDate = Date()
private val defaultNotes = ArrayList<Note>()
