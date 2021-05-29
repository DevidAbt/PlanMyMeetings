package com.example.planmymeetings

import java.text.SimpleDateFormat
import java.util.*

private val shortDateFormat: SimpleDateFormat = SimpleDateFormat("MM.dd. HH:mm")
fun dateToShortString(data: Date): String {
    return shortDateFormat.format(data)
}

private val longDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd. HH:mm")
fun dateToLongString(data: Date): String {
    return longDateFormat.format(data)
}
