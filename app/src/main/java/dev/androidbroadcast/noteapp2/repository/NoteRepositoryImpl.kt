package dev.androidbroadcast.noteapp2.repository

import androidx.lifecycle.LiveData
import dev.androidbroadcast.noteapp2.data.Note
import dev.androidbroadcast.noteapp2.data.NoteDao

interface NoteRepository {
    fun getAllNotes(): LiveData<List<Note>>
    fun getNoteById(id: Int): LiveData<Note>
    suspend fun insert(note: Note)
    suspend fun update(note: Note)
    suspend fun delete(note: Note)
}

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {
    override fun getAllNotes(): LiveData<List<Note>> = noteDao.getAllNotes()
    override fun getNoteById(id: Int): LiveData<Note> = noteDao.getNoteById(id)
    override suspend fun insert(note: Note) = noteDao.insert(note)
    override suspend fun update(note: Note) = noteDao.update(note)
    override suspend fun delete(note: Note) = noteDao.delete(note)
}