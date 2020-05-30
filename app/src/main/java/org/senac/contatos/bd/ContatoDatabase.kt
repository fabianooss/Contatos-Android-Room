package org.senac.contatos.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.senac.contatos.dao.ContatoDao
import org.senac.contatos.domain.Aluno
import org.senac.contatos.domain.Contato

@Database(entities = arrayOf(Contato::class), version = 1, exportSchema = false)
abstract class ContatoDatabase: RoomDatabase() {

    abstract fun contatoDao() : ContatoDao

    companion object {
        // não usar cache, sempre ler a variável da mémoria
        @Volatile
        private var INSTANCE: ContatoDatabase? = null

        fun getDatabase(context: Context): ContatoDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContatoDatabase::class.java,
                    "contato_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}