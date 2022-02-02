package com.example.todoapp

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController

import org.w3c.dom.Text

class MainActivity2 : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = getIntent()

        val position = intent.getIntExtra("HI", 0)
        val ds = helper.writableDatabase

        Log.e("OPS", position.toString())
        val args = arrayOf("TASKCARDID", "TASK", "DAY", "MONTH", "YEAR")
        val k = ds.query("TASKCARDS", args, "TASKCARDID='${position}'", null, null, null, null)

        Log.e("adf", k.columnNames.size.toString())
        k.moveToFirst()
//        Log.i("INTENT EXTRA", k.getString(0))
        //delete at position, insert new at position
//        val card = listOfCards[position]
        setContentView(R.layout.dialog_layout)
        val viewButton = findViewById<Button>(R.id.editContent)
        val textField:EditText = findViewById(R.id.editTaskField)
        val dayField: EditText = findViewById(R.id.editTextDate)
        val monthField: EditText = findViewById(R.id.editTextMonth)
        val yearField: EditText = findViewById(R.id.editTextYear)

        textField.setText(k.getString(1))
        if (k.getInt(2) > 0 && k.getInt(2)< 31) dayField.setText(k.getInt(2).toString())
        if (k.getInt(3) > 0 && k.getInt(3) <= 12) monthField.setText(k.getInt(3).toString())
        if (k.getInt(4) > 2000 && k.getInt(4) < 3000) yearField.setText(k.getInt(4).toString())
        Log.e("adf", k.columnNames.size.toString())

        viewButton.setOnClickListener{
                if (textField.text.isNotEmpty()){
                    val card = TaskCard(textField.text.toString(), dayField.text.toString().toIntOrNull()?:-1, monthField.text.toString().toIntOrNull()?:-1, yearField.text.toString().toIntOrNull()?:-1)
                    var cv = ContentValues()

                    cv.put("TASK", card.task)
                    cv.put("DAY", card.day)
                    cv.put("MONTH", card.month)
                    cv.put("YEAR", card.year)

                    val db = MyDBHelper(applicationContext)
                    val dbs = db.writableDatabase
                    ds.delete("TASKCARDS", "TASKCARDID='$position'", null)

                    dbs.insert("TASKCARDS", null, cv)
                    setResult(2, intent)
                    finish()
                }
        }
    }
}