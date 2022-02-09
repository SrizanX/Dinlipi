package com.srizan.dinlipi.ui.notes.edit

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.srizan.dinlipi.R
import com.srizan.dinlipi.databinding.AddEditNoteFragmentBinding
import com.srizan.dinlipi.model.Note
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditNoteFragment : Fragment() {
    private lateinit var binding: AddEditNoteFragmentBinding
    private val viewModel: AddEditNoteViewModel by viewModels()
    private val args: AddEditNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater,
            R.layout.add_edit_note_fragment, container, false)
        binding.viewModel = viewModel

        if (args.note!=null){
            viewModel.note.value = args.note
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.save_note -> {

                val note: Note
                val title = binding.titleEditText.text.toString()
                val text = binding.textEditText.text.toString()

                if (args.note!=null){
                    note = Note(id = args.note!!.id, title = title, text = text )
                    updateNote(note)
                    findNavController().popBackStack()

                } else{
                    note = Note(title = title,text=text)
                    saveNote(note)
                    findNavController().popBackStack()
                }
                true
            }
            R.id.delete_note -> {
                args.note?.let {
                    deleteNote(it.id)
                    findNavController().popBackStack()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteNote(id: Long) {
        viewModel.delete(Note(id = id, title = null, text = null))
    }

    private fun saveNote(note: Note) {
        viewModel.insert(note)
    }

    private fun updateNote(note: Note) {
        viewModel.update(note)
    }
}