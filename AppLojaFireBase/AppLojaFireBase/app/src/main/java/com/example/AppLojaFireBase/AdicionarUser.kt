package com.example.aulafirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AdicionarUser : AppCompatActivity() {
    private lateinit var edUsuario: EditText
    private lateinit var edNomeProduto: EditText
    private lateinit var edDescricao: EditText
    private lateinit var edPreco: EditText
    private lateinit var edEstoque: EditText
    private lateinit var btnAdicionar : Button

    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_user)

        initView()

        btnAdicionar.setOnClickListener{
            addUser()
        }


    }
    private fun addUser() {
        val usuario = edUsuario.text.toString()
        val nomeProduto = edNomeProduto.text.toString()
        val descricao = edDescricao.text.toString()
        val preco = edPreco.text.toString()
        val estoque = edEstoque.text.toString()

        if (usuario.isEmpty() || nomeProduto.isEmpty() || descricao.isEmpty() || preco.isEmpty() || estoque.isEmpty()) {
            Toast.makeText(this, "Preencha os campos ", Toast.LENGTH_SHORT).show()
        } else {

            databaseReference =
                FirebaseDatabase.getInstance().getReference("Usuarios")//Nome da tabela

            val users =
                User(usuario = usuario, nomeProduto = nomeProduto, Descricao = descricao, preco = preco, estoque = estoque)
            val status = databaseReference.child(usuario).setValue(users).addOnSuccessListener {
                Toast.makeText(this, "Usuário Adicionado", Toast.LENGTH_SHORT).show()

            }
        }
        }
    private fun initView() {
        edUsuario = findViewById(R.id.edUsuario)
        edNomeProduto = findViewById(R.id.edNomeProduto)
        edDescricao = findViewById(R.id.edDescricao)
        edPreco = findViewById(R.id.edPreco)
        edEstoque = findViewById(R.id.edEstoque)
        btnAdicionar = findViewById(R.id.btnAdicionar)

    }

    }

