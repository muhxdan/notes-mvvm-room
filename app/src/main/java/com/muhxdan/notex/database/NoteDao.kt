package com.muhxdan.notex.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.muhxdan.notex.model.NoteModel

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNote(note: NoteModel)

    @Delete
    suspend fun deleteNote(note: NoteModel)

    @Query("SELECT * FROM notex ORDER BY id DESC")
    fun getAllNotes() : LiveData<List<NoteModel>>

    @Query("SELECT * FROM notex WHERE noteTitle LIKE :query OR noteDesc LIKE :query ORDER BY id DESC")
    fun searchNote(query: String?) : LiveData<List<NoteModel>>
}