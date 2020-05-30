package org.senac.contatos.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import org.senac.contatos.R
import org.senac.contatos.databinding.FragmentContatoFormBinding
import org.senac.contatos.domain.Contato
import org.senac.contatos.model.ContatoViewModel


/**
 * A simple [Fragment] subclass.
 */
class ContatoForm : Fragment() {

    private val contatoView: ContatoViewModel by navGraphViewModels(R.id.nav_principal)

    // val contatoView = ViewModelProvider(requireActivity()).get<ContatoViewModel>(ContatoViewModel::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentContatoFormBinding>(
            inflater,
            R.layout.fragment_contato_form,
            container,
            false
        )

        binding.btSalvar.setOnClickListener {
            if (contatoView.selected.value == null) {
                Toast.makeText(context!!, "Contato não encontrado", Toast.LENGTH_LONG).show()
            }
            else {
                contatoView.selected.value.apply {
                    this?.nome = binding.tiNome.editText?.text.toString()
                    this?.email = binding.tiEmail.editText?.text.toString()
                }
                contatoView.save(contatoView.selected.value!!)
            }
            Navigation.findNavController(it).navigateUp()
        }

        binding.btCancelar.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        binding.contato = contatoView
        return binding.root
    }

}
