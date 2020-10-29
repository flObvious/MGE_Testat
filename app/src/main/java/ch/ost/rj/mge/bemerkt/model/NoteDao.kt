package ch.ost.rj.mge.bemerkt.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface NoteDao {

    @Query("SELECT * FROM note ORDER BY id DESC")
    suspend fun getAllNotes(): List<Note>

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

}