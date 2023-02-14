package com.muhxdan.notex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.muhxdan.notex.model.NoteModel
import com.muhxdan.notex.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(
    app: Application,
    private val noteRepository: NoteRepository
) : AndroidViewModel(app) {
    fun upsertNote(note: NoteModel) = viewModelScope.launch {
        noteRepository.upsertNote(note)
    }

    fun deleteNote(note: NoteModel) = viewModelScope.launch {
        noteRepository.deletNote(note)
    }

    fun getAllNotes() = noteRepository.getAllNotes()

    fun searchNote(query: String?) = noteRepository.searchNote(query)


}