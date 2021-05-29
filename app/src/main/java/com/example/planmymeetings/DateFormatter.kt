package com.example.planmymeetings

import java.text.SimpleDateFormat
import java.util.*

private val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("MM.dd. HH:mm")
fun dateToString(data: Date): String {
    val formattedDate = simpleDateFormat.format(data)
    return formattedDate
}
