package com.example.sqlitecompleto

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/*Classe contendo as funções do SQLite que permitirão nossas operações CRUD */
@Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "estudante.db"
        private const val TBL_ESTUDANTE = "tbl_estudante"
        private const val ID = "id"
        private const val NOME = "nome"
        private const val EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        /*Criação da tabela que será utilizada*/
        val createTblEstudante = ("CREATE TABLE "+ TBL_ESTUDANTE + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +NOME + " TEXT, "
                +EMAIL + " TEXT"+")")

        db?.execSQL(createTblEstudante)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_ESTUDANTE")
        onCreate(db)
    }
    //Função para inserir os alunos na tabela
    fun insertStudent(std:EstudanteModel):Long{
        val db= this.writableDatabase
        val contentValues = ContentValues()
        /*contentValues.put(ID, std.id)*/     //Já que a coluna ID é autoincrement, não precisa mandar o valor de ID
        contentValues.put(NOME, std.nome)
        contentValues.put(EMAIL, std.email)

        //Função do SQLite para inserir os dados
        val sucess = db.insert(TBL_ESTUDANTE, null, contentValues)
        db.close()
        //Se der certo vai retornar um valor positivo
        return sucess
    }

    //Função para receber os valores que estão na tabela - select
    @SuppressLint("Range")
    fun getAllStudent():ArrayList<EstudanteModel> {
        val stdList : ArrayList<EstudanteModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_ESTUDANTE"
        val db = this.writableDatabase

        val cursor: Cursor?

        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var nome: String
        var email: String


        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex("id"))
                nome = cursor.getString(cursor.getColumnIndex("nome"))
                email = cursor.getString(cursor.getColumnIndex("email"))
                val std = EstudanteModel(id = id, nome = nome, email = email)
                stdList.add(std)

            }while (cursor.moveToNext())
        }
        return stdList
    }

    //Função para atualizar os dados, recebe como argumento um objeto vindo da recyclerView
    fun updateStudent(std: EstudanteModel):Int{
        val db= this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(NOME, std.nome)
        contentValues.put(EMAIL, std.email)

        //Função do SQLite para update, recebe como argumentos: A tabela, os valores das colunas
        //a condição do where e os argumentos do where (caso necessário)
        val sucess = db.update(TBL_ESTUDANTE, contentValues, "id="+std.id,null)
        db.close()
        return sucess
    }

    //Função para deletar linhas da tabela
    //Recebe como parâmetro uma id de uma das linhas do RecyclerView
    fun deleteStudentById(id:Int):Int{
        val db= this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, id)


        //Função do SQLite para delete, recebe como argumentos: A tabela, os valores das colunas
        //a condição do where e os argumentos do where (caso necessário)
        val sucess = db.delete(TBL_ESTUDANTE, "id=$id", null)
        db.close()
        return sucess
    }
}