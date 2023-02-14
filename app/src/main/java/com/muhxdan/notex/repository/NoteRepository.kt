package com.muhxdan.notex.repository

import android.provider.ContactsContract.CommonDataKinds.Note
import com.muhxdan.notex.database.NoteDao
import com.muhxdan.notex.database.NoteDatabase
import com.muhxdan.notex.model.NoteModel

class NoteRepository(private val database: NoteDatabase) {
    suspend fun upsertNote(note: NoteModel) = database.getNoteDao().upsertNote(note)
    suspend fun deletNote(note: NoteModel) = database.getNoteDao().deleteNote(note)
    fun getAllNotes() = database.getNoteDao().getAllNotes()
    fun searchNote(query: String?) = database.getNoteDao().searchNote(query)
}