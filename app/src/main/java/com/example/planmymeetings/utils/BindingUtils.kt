package com.example.planmymeetings.screens.appointments

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.planmymeetings.dateToLongString
import com.example.planmymeetings.dateToShortString
import java.util.*

@BindingAdapter("formattedShortDate")
fun TextView.setFormattedShortDate(date: Date) {
    text = dateToShortString(date)
}

@BindingAdapter("formattedLongDate")
fun TextView.setFormattedLongDate(date: Date) {
    text = dateToLongString(date)
}
