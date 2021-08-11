package eu.bbsapps.notecompose.ui.addeditnote

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.bbsapps.notecompose.data.local.entities.Note
import eu.bbsapps.notecompose.repositories.NoteRepository
import eu.bbsapps.notecompose.util.Constants.KEY_LOGGED_IN_EMAIL
import eu.bbsapps.notecompose.util.Constants.NO_EMAIL
import eu.bbsapps.notecompose.util.Event
import eu.bbsapps.notecompose.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val sharedPref: SharedPreferences
) : ViewModel() {

    private val _noteColorPosition = mutableStateOf(0)
    val noteColorPosition: State<Int> = _noteColorPosition

    private val _noteTitle = mutableStateOf("Title")
    val noteTitle: State<String> = _noteTitle

    private val _noteContent = mutableStateOf("Write here...")
    val noteContent: State<String> = _noteContent

    fun setNoteColorPosition(colorPos: Int) {
        _noteColorPosition.value = colorPos
    }

    fun setNoteTitle(title: String) {
        _noteTitle.value = title
    }

    fun setNoteContent(content: String) {
        _noteContent.value = content
    }

    fun save() {
        val authEmail = sharedPref.getString(KEY_LOGGED_IN_EMAIL, "") ?: NO_EMAIL
        val date = System.currentTimeMillis()
        val id = note.value?.peekContent()?.data?.id ?: UUID.randomUUID().toString()
        val note = Note(
            title = noteTitle.value,
            content = noteContent.value,
            id = id,
            date = date,
            color = noteColorPosition.value.toString(),
            owners = note.value?.peekContent()?.data?.owners ?: listOf(authEmail)
        )

        insertNote(note)
    }

    private val _note = MutableLiveData<Event<Resource<Note>>>()
    val note: LiveData<Event<Resource<Note>>> = _note

    private fun insertNote(note: Note) = GlobalScope.launch(Dispatchers.IO) {
        noteRepository.insertNote(note)
    }

    fun getNoteById(noteId: String) = viewModelScope.launch {
        _note.postValue(Event(Resource.loading(null)))
        val note = noteRepository.getNoteById(noteId)

        note?.let {
            _note.postValue(Event(Resource.success(it)))
            setNoteColorPosition(it.color.toInt())
            setNoteTitle(it.title)
            setNoteContent(it.content)
        } ?: _note.postValue(Event(Resource.error("Note not found", null)))

    }

    fun hasNoteChanged():Boolean {
        val note = note.value?.peekContent()?.data
        note?.let {
            if (noteTitle.value != it.title || noteContent.value != it.content || noteColorPosition.value.toString() != it.color) {
                return true
            }
        } ?: if (noteTitle.value != "Title" || noteContent.value != "Write here..." || noteColorPosition.value != 0) {
                return true
            }
        return false
    }

    fun deleteNote() = viewModelScope.launch {
        if(note.value?.peekContent()?.data?.id!=null)
        noteRepository.deleteNote(note.value?.peekContent()?.data?.id?:"")
    }


}