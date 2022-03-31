package br.edu.infnet.caderno_anotacoes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.infnet.caderno_anotacoes.R
import br.edu.infnet.caderno_anotacoes.model.Anotacao
import java.text.SimpleDateFormat

class AnotacoesAdapter(val anotacoes: List<Anotacao>,
                       val deletaAnotacao: (Int) -> Unit,
                       val detalhaAnotacao: (String) -> Unit) :
    RecyclerView.Adapter<AnotacoesAdapter.AnotacoesAdapterViewHolder>() {

    class AnotacoesAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lblData = itemView.findViewById<TextView>(R.id.recycler_anotacoes_item_lbl_data)
        val lblTitulo = itemView.findViewById<TextView>(R.id.recycler_anotacoes_item_lbl_titulo)
        val btnDelete = itemView.findViewById<ImageButton>(R.id.recycler_anotacoes_item_btn_delete)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : AnotacoesAdapterViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recycler_anotacoes_item, parent, false)
        return AnotacoesAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnotacoesAdapterViewHolder, position: Int) {
        val anotacao = anotacoes[position]
        val dataString = "${SimpleDateFormat("dd/MM/yy").format(anotacao.data.toDate())}"
        holder.lblData.text = dataString
        holder.lblTitulo.text = anotacao.titulo
        holder.itemView.setOnClickListener {
            if(!anotacao.id.isNullOrBlank())
                detalhaAnotacao(anotacao.id)
        }
        holder.btnDelete.setOnClickListener {
            deletaAnotacao(position)
        }
    }

    override fun getItemCount(): Int = anotacoes.size
}