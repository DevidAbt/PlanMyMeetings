package com.example.planmymeetings.screens.appointments

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.planmymeetings.Appointment
import com.example.planmymeetings.R
import com.example.planmymeetings.databinding.AppointmentItemBinding
import java.util.*
import kotlin.collections.ArrayList


class AppointmentItemAdapter(
    context: Context,
    itemsData: ArrayList<Appointment>,
    private val appointmentListener: AppointmentListener
) :
    RecyclerView.Adapter<AppointmentItemAdapter.ViewHolder>(), Filterable {
    private var mAppointmentsData: ArrayList<Appointment> = itemsData
    private var mAppointmentsDataAll: ArrayList<Appointment> = itemsData
    private var mContext: Context = context
    private var lastPosition: Int = -1

    class ViewHolder private constructor(val binding: AppointmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindTo(appointment: Appointment, appointmentListener: AppointmentListener) {
            binding.appointment = appointment
            binding.listener = appointmentListener
            binding.appointmentCardView.setOnClickListener {
                appointmentListener.clickListener(appointment.id)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AppointmentItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = mAppointmentsData[position]
        holder.bindTo(currentItem, appointmentListener)

        if (holder.adapterPosition > lastPosition) {
            val animation = AnimationUtils.loadAnimation(mContext, R.anim.right_to_left)
            holder.itemView.startAnimation(animation)
            lastPosition = holder.adapterPosition
        }
    }

    override fun getItemCount(): Int {
        return mAppointmentsData.size
    }

    fun updateData(appointments: ArrayList<Appointment>) {
        mAppointmentsData = appointments
        mAppointmentsDataAll = appointments
        lastPosition = -1
        notifyDataSetChanged()
    }

    private val appointmentFilter: Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence?): FilterResults {
            val filteredList: ArrayList<Appointment> = ArrayList()
            val results = FilterResults()

            if (charSequence.isNullOrEmpty()) {
                results.count = mAppointmentsDataAll.size
                results.values = mAppointmentsDataAll
            } else {
                val filterPattern = charSequence.toString().toLowerCase(Locale.ROOT).trim()

                mAppointmentsDataAll.forEach {
                    if (it.category.toLowerCase(Locale.ROOT)
                            .contains(filterPattern) || it.description.toLowerCase(Locale.ROOT)
                            .contains(filterPattern)
                    ) {
                        filteredList.add(it)
                    }
                }

                results.count = filteredList.size
                results.values = filteredList
            }

            return results
        }

        override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
            mAppointmentsData = filterResults!!.values as ArrayList<Appointment>
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        return appointmentFilter
    }
}

class AppointmentListener(
    val clickListener: (appointmentId: Int) -> Unit,
    val removeClickListener: (appointmentId: Int) -> Unit
) {
    fun onRemoveClick(appointmentId: Int) = removeClickListener(appointmentId)
}
