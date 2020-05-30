package org.senac.contatos.adapter

import android.view.*
import android.view.View.OnCreateContextMenuListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.senac.contatos.R
import org.senac.contatos.domain.Contato


class ContatoAdapter() : RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>() {

    var contatos = emptyList<Contato>()

    var onItemClick: ((Contato) -> Unit)? = null

    var onAlterar: ((Contato) -> Boolean)? = null

    var onExcluir: ((Contato) -> Boolean)? = null

    inner class ContatoViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), OnCreateContextMenuListener {
        var tvItemNome = itemView.findViewById<TextView>(R.id.tv_item_nome)
        var tvItemEmail = itemView.findViewById<TextView>(R.id.tv_item_email)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(contatos.get(adapterPosition))
            }
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.setHeaderTitle("Opções")
            val alterar = menu?.add("Alterar")
            val excluir = menu?.add("Excluir")
            alterar?.setOnMenuItemClickListener {
                onAlterar?.invoke(contatos.get(adapterPosition)) ?: false
            }
            excluir?.setOnMenuItemClickListener {
                onExcluir?.invoke(contatos.get(adapterPosition)) ?: false
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ContatoViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return contatos.size
    }

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        holder.tvItemNome.text = contatos.get(position).nome
        holder.tvItemEmail.text = contatos.get(position).email
    }

    internal fun setContatos(contatos: List<Contato>) {
        this.contatos = contatos
        notifyDataSetChanged()
    }
}