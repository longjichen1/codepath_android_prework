package com.example.todoapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper(context:Context) : SQLiteOpenHelper(context, "USERDB", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE TASKCARDS(TASKCARDID INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT, DAY TEXT, MONTH TEXT, YEAR TEXT)")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}