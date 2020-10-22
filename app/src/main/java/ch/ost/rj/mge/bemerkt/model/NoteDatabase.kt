package ch.ost.rj.mge.bemerkt.model

import androidx.room.Database

@Database(entities = [NoteDao::class], version = 1)
abstract class NoteDatabase {
    abstract fun noteDao(): NoteDao
}