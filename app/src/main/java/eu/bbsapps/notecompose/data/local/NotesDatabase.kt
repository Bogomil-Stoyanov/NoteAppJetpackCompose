package eu.bbsapps.notecompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import eu.bbsapps.notecompose.data.local.Converters
import eu.bbsapps.notecompose.data.local.NoteDao
import eu.bbsapps.notecompose.data.local.entities.LocallyDeletedNoteId
import eu.bbsapps.notecompose.data.local.entities.Note

@Database(entities = [Note::class, LocallyDeletedNoteId::class], version = 1)
@TypeConverters(Converters::class)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
}