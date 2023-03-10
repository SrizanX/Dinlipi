package com.bitflecks.dinlipi.data

import com.bitflecks.dinlipi.model.Note
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) {
    fun getAllNotes() = noteDao.getAllNotes()
    suspend fun insert(note: Note){
        noteDao.insert(note)
    }
    suspend fun update(note: Note){
        noteDao.update(note)
    }
    suspend fun delete(note: Note){
        noteDao.delete(note)
    }
}