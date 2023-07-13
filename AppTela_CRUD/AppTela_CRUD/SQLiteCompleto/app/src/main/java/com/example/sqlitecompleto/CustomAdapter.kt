package com.example.sqlitecompleto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//Classe Adapter, vai associar os dados à sua View

class CustomAdapter : RecyclerView.Adapter<CustomAdapter.ViewHolderEstudante>(){

    //Array que vai popular nossa RecyclerView
    private var stdList: ArrayList<EstudanteModel> = ArrayList()
    //Variáveis que serão usadas nas funções onClick
    private var onClickItem: ((EstudanteModel) -> Unit)? = null
    private var onClickDeleteItem: ((EstudanteModel) -> Unit)? = null

    //Função que adiciona os dados na nossa RecyclerView
    fun addItems(items: ArrayList<EstudanteModel>){
        //stdList recebe os items enviados
        this.stdList = items
        //Função para "atualizar" a RecyclerView quando uma mudança nos dados ocorrer
        notifyDataSetChanged()
    }

    //Funções onClick
    fun setOnClickItem(callback: (EstudanteModel)->Unit){
        this.onClickItem = callback
    }
    fun setOnClickDeleteItem(callback: (EstudanteModel)->Unit){
        this.onClickDeleteItem = callback
    }

    //Função obrigatória, RecyclerView chama esse método sempre que precisa criar um novo ViewHolder
    //Associa a View à ViewHolder mas não a preenche com dados
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolderEstudante(
        LayoutInflater.from(parent.context).inflate(R.layout.items_estudantes, parent, false)
    )

    //RecyclerView chama esse método para associar um ViewHolder aos dados
    //Esse método vai preecher o layout associado com os dados apropriados
    override fun onBindViewHolder(holder: ViewHolderEstudante, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(std) }
        holder.btnDelete.setOnClickListener{ onClickDeleteItem?.invoke(std) }
    }

    //RecyclerView chama esse método para ver o tamanho do conjunto de dados
    override fun getItemCount(): Int {
        return stdList.size
    }

    //Classe ViewHolder, "mapeia" os textViews e os botões
    class ViewHolderEstudante(var view: View): RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var nome = view.findViewById<TextView>(R.id.tvNome)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        //Função que da bind nos componentes da ViewHolder
        fun bindView(std:EstudanteModel){
            id.text = std.id.toString()
            nome.text = std.nome
            email.text = std.email
        }
    }
}
