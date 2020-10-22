package ch.ost.rj.mge.bemerkt.model

import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): List<Note>

    @Query("SELECT * FROM note WHERE title LIKE :title")
    fun findByTitle(title: String): Note

    @Insert
    fun insert(vararg note: Note)

    @Delete
    fun delete(todo: Note)

    @Update
    fun update(vararg note: Note)
}