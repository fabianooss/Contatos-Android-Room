package org.senac.contatos.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.senac.contatos.domain.Contato

@Dao
interface ContatoDao {

    @Query("select * from contato order by nome asc")
    fun getContatos() : LiveData<List<Contato>>

    @Query("select * from contato where id = :id")
    fun getContatoById(id: Int) : LiveData<Contato>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contato: Contato)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun udpate(contato: Contato)

    @Query("delete from contato")
    suspend fun deleteTodos()

    @Delete()
    suspend fun delete(contato: Contato)

}