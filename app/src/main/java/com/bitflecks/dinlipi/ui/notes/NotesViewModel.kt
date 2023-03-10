package com.bitflecks.dinlipi.ui.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitflecks.dinlipi.data.NoteRepository
import com.bitflecks.dinlipi.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {
    var notes: LiveData<List<Note>> = noteRepository.getAllNotes()
    fun delete(note: Note) = viewModelScope.launch {
        noteRepository.delete(note)
    }
}