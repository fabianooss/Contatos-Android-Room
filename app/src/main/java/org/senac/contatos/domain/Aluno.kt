package org.senac.contatos.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Aluno (var nome: String, var nota : Double){

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}