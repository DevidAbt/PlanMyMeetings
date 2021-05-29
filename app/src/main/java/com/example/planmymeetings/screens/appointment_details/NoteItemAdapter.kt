package com.example.planmymeetings.screens.appointment_details

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.planmymeetings.Note
import com.example.planmymeetings.databinding.NoteItemBinding

class NoteItemAdapter(
    context: Context,
    itemsData: ArrayList<Note>
) : RecyclerView.Adapter<NoteItemAdapter.ViewHolder>() {
    private var mNoteItemData: ArrayList<Note> = itemsData
    private var mContext: Context = context
    private var lastPosition: Int = -1

    class ViewHolder private constructor(val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindTo(note: Note) {
            binding.note = note
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NoteItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = mNoteItemData[position]
        holder.bindTo(currentItem)

        // TODO: animation
//        if (holder.)
    }

    override fun getItemCount(): Int {
        return mNoteItemData.size
    }
}