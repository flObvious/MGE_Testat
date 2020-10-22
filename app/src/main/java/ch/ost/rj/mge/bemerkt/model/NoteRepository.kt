package ch.ost.rj.mge.bemerkt.model

class NoteRepository(private val dao: NoteDao) {

    val notes = dao.getAll()

    suspend fun insert(note: Note){
        dao.insert(note)
    }

    suspend fun update(note: Note){
        dao.update(note)
    }

    suspend fun delete(note: Note){
        dao.delete(note)
    }
}