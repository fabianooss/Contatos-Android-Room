package org.senac.contatos.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.senac.contatos.bd.ContatoDatabase
import org.senac.contatos.dao.ContatoDao
import org.senac.contatos.domain.Contato

class ContatoViewModel(application: Application) : AndroidViewModel(application) {

    private val contatoDao : ContatoDao
    val todosContatos : LiveData<List<Contato>>

    val selected = MutableLiveData<Contato>()

    init {
        contatoDao = ContatoDatabase.getDatabase(application).contatoDao()
        todosContatos = contatoDao.getContatos()
    }
    fun save(contato: Contato) {
        viewModelScope.launch(Dispatchers.IO) {
            if (contato.id == 0) {
                contatoDao.insert(contato)
            }
            else {
                contatoDao.udpate(contato)
            }
        }
    }
    fun select(contato: Contato) {
        this.selected.value = contato
    }
    fun delete(contato: Contato) {
        viewModelScope.launch ( Dispatchers.IO ) {
            contatoDao.delete(contato)
        }
    }
}