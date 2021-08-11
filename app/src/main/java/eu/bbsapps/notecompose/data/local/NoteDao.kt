package eu.bbsapps.notecompose.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.bbsapps.notecompose.data.local.entities.LocallyDeletedNoteId
import eu.bbsapps.notecompose.data.local.entities.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Query("DELETE FROM notes WHERE id = :id ")
    suspend fun deleteNoteById(id: String)

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()

    @Query("DELETE FROM notes WHERE isSynced = 1")
    suspend fun deleteAllSyncedNotes()

    //this is not a suspend fun because it returns a LiveData
    @Query("SELECT * FROM notes WHERE id = :id")
    fun observeNoteById(id:String):LiveData<Note>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id:String): Note?

    @Query("SELECT * FROM notes ORDER BY date DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE isSynced = 0")
    suspend fun getAllUnsyncedNotes():List<Note>

    @Query("SELECT * FROM locally_deleted_note_ids")
    suspend fun getAllLocallyDeletedNoteIds():List<LocallyDeletedNoteId>

    @Query("DELETE FROM locally_deleted_note_ids WHERE deletedNoteId = :deletedNoteId")
    suspend fun deleteLocallyDeletedNoteId(deletedNoteId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocallyDeletedNoteId(locallyDeletedNoteId: LocallyDeletedNoteId)


}