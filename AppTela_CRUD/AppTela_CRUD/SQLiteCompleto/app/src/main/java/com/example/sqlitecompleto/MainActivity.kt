package com.example.sqlitecompleto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var edNome: EditText
    private lateinit var edEmail: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button


    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView:RecyclerView
    private var adapter: CustomAdapter? = null
    private var std: EstudanteModel? = null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerVew()
        sqLiteHelper = SQLiteHelper(this)

        //setOnClickListener dos Botões
        btnAdd.setOnClickListener{
            addStudent()
        }
        btnView.setOnClickListener {
            getStudents()
        }
        btnUpdate.setOnClickListener{
            updateStudent()
        }
        adapter?.setOnClickItem {
            Toast.makeText(this, it.nome, Toast.LENGTH_SHORT).show()
            edNome.setText(it.nome)
            edEmail.setText(it.email)
            std = it
        }
        adapter?.setOnClickDeleteItem {
            deleteStudent(it.id)
        }
    }

    //Função que vai executar a operação de update
    private fun updateStudent() {
        //Recebe os valores dos EditText
        val nome = edNome.text.toString()
        val email = edEmail.text.toString()
        if(std == null) return

        //Atribui ao objeto std os paramêtros
        val std = EstudanteModel(id = std!!.id, nome = nome, email = email)
        //Chama o método update da classe SQLiteHelper
        val status = sqLiteHelper.updateStudent(std)
        //Se deu certo
        if(status > -1){
            //Limpa o formulário e chama a função para exibir os dados
            clearEditText()
            getStudents()
        }else{
            //Mensagem de falha
            Toast.makeText(this,"Update Falhou", Toast.LENGTH_SHORT).show()
        }
    }

    //Função para receber os dados
    private fun getStudents() {
        //Atribui ao objeto std os dados
        val stdList = sqLiteHelper.getAllStudent()
        //Adiciona os dados à recyclerView
        adapter?.addItems(stdList)
    }

    //Função para adicionar os alunos
    private fun addStudent() {
        //Recebe os dados do formulário
        val nome = edNome.text.toString()
        val email = edEmail.text.toString()

        //Checa se os valores não estão vazios
        if(nome.isEmpty()|| email.isEmpty()){
            Toast.makeText(this, "Preecha com algum valor ",Toast.LENGTH_SHORT).show()
        }else{
            //Atribui ao objeto std os dados
            //Não precisa passar id pq o campo é autoincrement
            val std = EstudanteModel(nome = nome, email = email)
            val status = sqLiteHelper.insertStudent(std)

            if(status>-1){
                Toast.makeText(this, "Estudante Adicionado com sucesso", Toast.LENGTH_SHORT).show()
                clearEditText()
                getStudents()
            }else {
                Toast.makeText(this, "Algo deu errado", Toast.LENGTH_SHORT).show()
            }
        }


    }

    //Função para deletar
    private fun deleteStudent(id:Int){
        //Janela de diálogo para deletar ou não
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Tem certeza que quer deletar?")
        builder.setCancelable(true)
        builder.setPositiveButton("Sim"){
                dialog, _ ->
            //Se sim, vai chamar a função delete da classe SQLiteHelper
            sqLiteHelper.deleteStudentById(id)
            //Atualiza os dados
            getStudents()
            dialog.dismiss()
        }
        builder.setNegativeButton("Não"){
                dialog, _ -> dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()

    }

    //Função para limpar os dados dos formulários
    private fun clearEditText() {
        edNome.setText("")
        edEmail.setText("")
        edNome.requestFocus()
    }

    //Função para iniciar o recyclerView
    private fun initRecyclerVew(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CustomAdapter()
        recyclerView.adapter = adapter
    }

    //Função para dar bind nos componentes do formulário
    private fun initView() {
        edNome = findViewById(R.id.edNome)
        edEmail = findViewById(R.id.edEmail)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)

    }

}