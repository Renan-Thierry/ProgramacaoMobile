package com.example.sqlitecompleto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class TelaCadastro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_cadastro)
    }
    fun BotaoCadastro(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    fun BotaoLogin(view: View) {
        val intent = Intent(this, TelaLogin::class.java)
        startActivity(intent)
    }
}