package com.example.aulafirebase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var recyclerbtn : Button
    lateinit var userAddBtn : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerbtn = findViewById(R.id.recyclerviewbtn)
        userAddBtn = findViewById(R.id.addUserBtn)

        recyclerbtn.setOnClickListener {

            var i = Intent(this, UserlistActivity::class.java)
            startActivity(i)
            finish()


        }
        userAddBtn.setOnClickListener {

            var i = Intent(this, AdicionarUser::class.java)
            startActivity(i)
            finish()


        }
    }
}