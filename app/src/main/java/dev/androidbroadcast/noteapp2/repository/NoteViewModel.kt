package dev.androidbroadcast.noteapp2.repository


import android.app.Application
import androidx.lifecycle.*
import dev.androidbroadcast.noteapp2.data.Note
import dev.androidbroadcast.noteapp2.data.NoteDatabase
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = NoteRepositoryImpl(NoteDatabase.getDatabase(application).noteDao())
    val notes = repository.getAllNotes()

    fun getNoteById(id: Int) = repository.getNoteById(id)

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }
    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }
    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }
}