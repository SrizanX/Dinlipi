package com.srizan.dinlipi.ui.notes.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srizan.dinlipi.data.NoteRepository
import com.srizan.dinlipi.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
): ViewModel() {

   var note = MutableLiveData<Note>()

    fun insert(note:Note) = viewModelScope.launch {
        noteRepository.insert(note)
    }
    fun update(note: Note) = viewModelScope.launch {
        noteRepository.update(note)
    }
    fun delete(note: Note) = viewModelScope.launch {
        noteRepository.delete(note)
    }
}