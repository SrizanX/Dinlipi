package com.srizan.dinlipi.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.srizan.dinlipi.R
import com.srizan.dinlipi.adapters.NoteAdapter
import com.srizan.dinlipi.databinding.NotesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotesFragment : Fragment() {
   private val viewModel: NotesViewModel by viewModels()

    private lateinit var binding: NotesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.notes_fragment, container, false)
        val adapter = NoteAdapter()
        binding.recyclerviewNotes.adapter = adapter
        viewModel.notes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        return binding.root
    }
}