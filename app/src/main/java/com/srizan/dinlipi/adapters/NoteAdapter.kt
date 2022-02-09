package com.srizan.dinlipi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.srizan.dinlipi.databinding.ItemNotesBinding
import com.srizan.dinlipi.model.Note
import com.srizan.dinlipi.ui.notes.NotesFragmentDirections

class NoteAdapter : ListAdapter<Note, RecyclerView.ViewHolder>(NoteDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NoteViewHolder(
            ItemNotesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val note = getItem(position)
        (holder as NoteViewHolder).bind(note)
    }

    class NoteViewHolder(private val binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setNoteClickListener { view->
                binding.note?.let { note ->
                    navigateToNoteDetails(note,view)
                }
            }
        }

        private fun navigateToNoteDetails(note: Note, view: View?) {
            val action = NotesFragmentDirections
                .actionNotesFragmentToAddEditNoteFragment(note)
            view?.findNavController()?.navigate(action)
        }

        fun bind(newNote : Note){
            binding.apply {
                note = newNote
            }
        }
    }
}

private class NoteDiffCallback : DiffUtil.ItemCallback<Note> () {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}
