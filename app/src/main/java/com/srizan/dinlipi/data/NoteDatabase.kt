package com.srizan.dinlipi.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.srizan.dinlipi.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun noteDao(): NoteDao
}