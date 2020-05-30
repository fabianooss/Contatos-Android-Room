package org.senac.contatos.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.senac.contatos.R
import org.senac.contatos.adapter.ContatoAdapter
import org.senac.contatos.databinding.FragmentContatosListBinding
import org.senac.contatos.domain.Contato
import org.senac.contatos.model.ContatoViewModel

class ContatosList : Fragment() {

    private val contatoView: ContatoViewModel by navGraphViewModels(R.id.nav_principal)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentContatosListBinding>(
            inflater,
            R.layout.fragment_contatos_list,
            container,
            false
        )

        bindListaContatos(binding)

        binding.contato = contatoView
        binding.setLifecycleOwner (this)

        return binding.root

    }

    private fun bindListaContatos(binding: FragmentContatosListBinding?) {
        val adapter = ContatoAdapter()

        binding?.let {
            it.rvContatos?.adapter = adapter
            it.rvContatos?.layoutManager = LinearLayoutManager(context!!)

            contatoView.todosContatos.observe(this.viewLifecycleOwner, Observer { contatos ->
                contatos?.let {
                    adapter.setContatos(it)
                }
            })

            it.fbNovo?.setOnClickListener{
                contatoView.select(Contato("",""))
                Navigation.findNavController(it).navigate(R.id.action_contatosList_to_contatoForm)
            }

            adapter.onAlterar = { contato ->
                contatoView.select(contato)
                Navigation.findNavController(it.rvContatos).navigate(R.id.action_contatosList_to_contatoForm)
                true
            }

            adapter.onExcluir = {
                AlertDialog.Builder(this.activity)
                    .setMessage("Deseja excluir o cotanto ${it.nome}?")
                    .setPositiveButton("Sim", DialogInterface.OnClickListener { _, _ ->
                        contatoView.delete(it)
                        true
                    })
                    .setNegativeButton("Não", DialogInterface.OnClickListener { _, _ ->
                        false
                    })
                    .create().show()
                true
            }
        }
    }


}
