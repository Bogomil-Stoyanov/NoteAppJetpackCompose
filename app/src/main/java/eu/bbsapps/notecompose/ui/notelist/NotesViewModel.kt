package eu.bbsapps.notecompose.ui.notelist

import android.content.SharedPreferences
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.bbsapps.notecompose.data.local.entities.Note
import eu.bbsapps.notecompose.repositories.NoteRepository
import eu.bbsapps.notecompose.util.Constants.KEY_LOGGED_IN_EMAIL
import eu.bbsapps.notecompose.util.Constants.KEY_PASSWORD
import eu.bbsapps.notecompose.util.Constants.NO_EMAIL
import eu.bbsapps.notecompose.util.Constants.NO_PASSWORD
import eu.bbsapps.notecompose.util.Event
import eu.bbsapps.notecompose.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NoteRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _forceUpdate = MutableLiveData(false)

    private val _allNotes = _forceUpdate.switchMap {
        repository.getAllNotes().asLiveData(viewModelScope.coroutineContext)
    }.switchMap {
        MutableLiveData(Event(it))
    }

    val allNotes: LiveData<Event<Resource<List<Note>>>> = _allNotes

    fun syncAllNotes() = _forceUpdate.postValue(true)

    fun logout() {
        sharedPreferences.edit().putString(KEY_LOGGED_IN_EMAIL, NO_EMAIL).apply()
        sharedPreferences.edit().putString(KEY_PASSWORD, NO_PASSWORD).apply()
    }
}