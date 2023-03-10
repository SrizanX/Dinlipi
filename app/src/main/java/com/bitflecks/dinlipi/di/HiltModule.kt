package com.bitflecks.dinlipi.di

import android.content.Context
import androidx.room.Room
import com.bitflecks.dinlipi.data.NoteDao
import com.bitflecks.dinlipi.data.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : NoteDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "notes.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    @Singleton
    @Provides
    fun provideDao (noteDatabase: NoteDatabase): NoteDao {
        return noteDatabase.noteDao()
    }
}