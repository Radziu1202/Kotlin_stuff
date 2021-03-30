package com.example.gralosujca.DB

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.brgame.message.message
import com.example.gralosujca.ui.login.LoginActivity

class DBHelper(context: LoginActivity):SQLiteOpenHelper(context,DATABASE_NAME,
    null, DATABASE_VER) {
    companion object {
        private val DATABASE_VER = 2
        private val DATABASE_NAME = "EDMTDB.db"
        //Table
        private val TABLE_NAME = "Message"
        private val LOGINS="Logins"
        private val COL_ID = "Id"
        private val COL_ID2 = "Id"
        private val COL_NICK = "Nick"
        private val COL_MAIL="Mail"
        private val COL_SCORE = "SCORE"
        private val COL_PASSWORD="Password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NICK TEXT, $COL_SCORE TEXT)")
        val CREATE_TABLE_QUERY2 = ("CREATE TABLE $LOGINS ($COL_ID2 INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NICK TEXT, $COL_MAIL TEXT, $COL_PASSWORD TEXT)")
        db!!.execSQL(CREATE_TABLE_QUERY)
        db!!.execSQL(CREATE_TABLE_QUERY2)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $LOGINS")
        onCreate(db!!)
    }

    //CRUD
    val allMessage:List<message>

        get(){

            val listMessage = ArrayList<message>()
           // val selectQuery = "SELECT  * FROM $TABLE_NAME ORDER BY $COL_SCORE asc  LIMIT 10"
            val db = this.writableDatabase
            val score = arrayOf<String>()
            val c = db.query(TABLE_NAME,score , null, null, null, null, COL_SCORE+" DESC");
           // val cursor =  db.rawQuery(selectQuery, null)
            if(c.moveToFirst()){
                do {
                    val message = message(c.getString(c.getColumnIndex(COL_NICK)),
                            c.getInt(c.getColumnIndex(COL_SCORE)),
                            c.getInt(c.getColumnIndex(COL_ID)))
                    listMessage.add(message)
                } while (c.moveToNext())
            }
            db.close()
            return listMessage
        }

    fun addMessage(message:message){


        val db= this.writableDatabase

         overwriteScore(message.nick,message.score)
        db.close()

    }

    fun register_user(nick:String, pass:String,mail:String){

        val db=this.writableDatabase

        val values=ContentValues()
        values.put(COL_NICK,nick)
        values.put(COL_PASSWORD,pass)
        values.put(COL_MAIL,mail)
        db.insert(LOGINS, null, values)
        db.close()
    }

    fun getRecord(nick:String): Int{
        var database = this.writableDatabase
        val selectQuery = "SELECT * FROM ${TABLE_NAME}"
        val cursor =  database.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()){
            do {
                if( cursor.getString(cursor.getColumnIndex(COL_NICK)).equals(nick)){
                    val score = cursor.getInt((cursor.getColumnIndex(COL_SCORE)))
                    database.close()
                    return score;
                }
            } while (cursor.moveToNext())
        }
        database.close()
        return 0;
    }

     fun checkLogin(nick:String,password:String): Boolean {
        var database = this.writableDatabase
        val selectQuery = "SELECT * FROM ${LOGINS}"
        val cursor =  database.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()){
            do {
                if( cursor.getString(cursor.getColumnIndex(COL_NICK)).equals(nick) &&
                    cursor.getString(cursor.getColumnIndex(COL_PASSWORD)).equals(password)){
                        database.close()
                        return true;
                }
            } while (cursor.moveToNext())
        }
        database.close()
        return false
    }



    fun overwriteScore(nick:String,score:Int): Boolean {
        val updateQuery = "Update $TABLE_NAME set $COL_SCORE =  $score WHERE $COL_NICK = '$nick'"
        val selectQuery="SELECT * FROM ${TABLE_NAME}"
        val db = this.writableDatabase
        val cursor =  db.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()){
            do {
                if( cursor.getString(cursor.getColumnIndex(COL_NICK)).equals(nick)){
                    db!!.execSQL(updateQuery)
                    db.close()
                    return true;
                }
            } while (cursor.moveToNext())
        }
        val values = ContentValues()
        values.put(COL_NICK, nick)
        values.put(COL_SCORE, score)
        db.insert(TABLE_NAME, null, values)



        return true
    }

}