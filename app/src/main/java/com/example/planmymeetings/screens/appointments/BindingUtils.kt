package com.example.planmymeetings.screens.appointments

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.planmymeetings.dateToString
import java.util.*

@BindingAdapter("formattedDate")
fun TextView.setFormattedDate(date: Date) {
    text = dateToString(date)
}