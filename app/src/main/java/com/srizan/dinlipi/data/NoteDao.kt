package com.srizan.dinlipi.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.srizan.dinlipi.model.Note

@Dao
interface NoteDao {
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM NOTES_TABLE WHERE id = :noteId")
    suspend fun getNoteById(noteId: Long): Note
}