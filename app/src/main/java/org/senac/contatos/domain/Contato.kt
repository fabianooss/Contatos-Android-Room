package org.senac.contatos.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contato")
data class Contato (var nome: String, var email: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}