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
import com.example.todoapp.databinding.ActivityMain2Binding
import org.w3c.dom.Text

class MainActivity2 : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = getIntent()

        val position = intent.getIntExtra("HI", 0)
        val ds = helper.readableDatabase
        Log.e("OPS", position.toString())

        val k = ds.rawQuery("SELECT TASK FROM TASKCARDS WHERE TASKCARDID='$position' AND TASK='asdf' AND DAY!='-1'", null)

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
        val origTask = textField.text.toString()
//        textField.setText(k.getString(0))

        Log.e("adf", k.columnNames.size.toString())

        viewButton.setOnClickListener{
//
//
                val card = TaskCard(textField.text.toString(), 0, 0, 0)
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
//
//            Log.i("hi", "RUN")
        }
    }
}