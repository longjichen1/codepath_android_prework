package com.example.todoapp

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException

var listOfTasks = mutableListOf<String>()
var listOfCards = mutableListOf<TaskCard>()
var pos:Int = 0
lateinit var helper: MyDBHelper
class MainActivity : AppCompatActivity() {


    lateinit var adapter: TaskItemAdapter

    var listOfCards = mutableListOf<TaskCard>()
    lateinit var adapter2: TaskCardAdapter





    override fun onCreate(savedInstanceState: Bundle?) {
        helper = MyDBHelper(applicationContext)
        var db = helper.readableDatabase

        loadItems()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fun newIntent(position:Int){
            val clickedCard = listOfCards[position]

            val dialog = AlertDialog.Builder(applicationContext)
            dialog.setTitle("Update ToDo")
            val view = layoutInflater.inflate(R.layout.dialog_layout, null)
            val taskField = view.findViewById<EditText>(R.id.editTaskField)
            taskField.setText(clickedCard.task)
            val dayField = view.findViewById<EditText>(R.id.editTextDate)
            dayField.setText(clickedCard.task)
            val monthField = view.findViewById<EditText>(R.id.editTextMonth)
            monthField.setText(clickedCard.task)
            val yearField = view.findViewById<EditText>(R.id.editTextYear)
            yearField.setText(clickedCard.task)
            dialog.setView(view)
            dialog.setPositiveButton("Update") { _: DialogInterface, _: Int ->
                if (taskField.text.isNotEmpty()) {
                    val tk: TaskCard = TaskCard(taskField.text.toString(), dayField.text.toString().toIntOrNull()?:-1, monthField.text.toString().toIntOrNull()?:-1, yearField.text.toString().toIntOrNull()?:-1)
                    val wdb = helper.writableDatabase
                    val cv = ContentValues()
                }
            }
            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

            }
            dialog.show()
        }
        val d = this
        val onLongClickListener2 = object : TaskCardAdapter.OnLongClickListener {
            override fun onCardLongClicked(position: Int) {
                val intent = Intent(d, MainActivity2::class.java)
                val card = listOfCards[position]
                val selectQuery = "SELECT * FROM TASKCARDS WHERE TASK='${card.task}'"
                val ds = helper.writableDatabase
                val cursor = ds.rawQuery(selectQuery, null)

                cursor.moveToFirst()

                intent.putExtra("HI", cursor.getInt(0))
                Log.i("SIZE", listOfCards.size.toString())
                startActivityForResult(intent, 2)
                Log.e("intent", "NOT")
                loadItems()

                adapter2.notifyDataSetChanged()
            }
        }

        val onDeleteListener = object : TaskCardAdapter.OnDeleteListener {
            override fun onCardLongClicked(position: Int) {
                // 1. Remove item from list
                val wdb = helper.writableDatabase
                val currentCard = listOfCards[position]
                val tasks: String = currentCard.task
                val days = currentCard.day
                val months = currentCard.month
                val years = currentCard.year
                wdb.delete("TASKCARDS", "TASK='$tasks' and DAY='$days' and MONTH='$months' and YEAR='$years'", null)
                loadItems()
                Log.i("Hi", tasks)

//                wdb.delete("TASKCARDS", "TASK=?", arrayOf(task))
                // 2. Notify adapter
                adapter2.notifyDataSetChanged()

                // 3. Save File
                saveItems()
            }
        }

        // Look up recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter2 = TaskCardAdapter(listOfCards, onLongClickListener2, onDeleteListener)
        recyclerView.adapter = adapter2
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up input field
        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        val inputMonth = findViewById<EditText>(R.id.addTextMonth)
        val inputDay= findViewById<EditText>(R.id.addTextDate)
        val inputYear = findViewById<EditText>(R.id.addTextYear)

        // Clear All Tasks
        findViewById<Button>(R.id.clearTasks).setOnClickListener{
//            listOfTasks.clear()
//            adapter.notifyDataSetChanged()
            listOfCards.clear()
            db.execSQL("delete from "+ "TASKCARDS");
            adapter2.notifyDataSetChanged()
        }

        // Adds Data
        findViewById<Button>(R.id.addContent).setOnClickListener {
            // 1. Grab text inputted into @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()
            if (userInputtedTask.isNotEmpty() && (inputMonth.text.toString().toIntOrNull()?:-1 != -1
                        || inputMonth.text.toString().isEmpty())){
                val card = TaskCard(userInputtedTask, inputDay.text.toString().toIntOrNull()?:-1,
                    inputMonth.text.toString().toIntOrNull()?:-1,
                    inputYear.text.toString().toIntOrNull()?:-1);
                // 2. Add the string to our list of tasks: listofTasks
                listOfTasks.add(userInputtedTask)
                listOfCards.add(card)
                var cv = ContentValues()

                cv.put("TASK", card.task)
                cv.put("DAY", card.day)
                cv.put("MONTH", card.month)
                cv.put("YEAR", card.year)
                db.insert("TASKCARDS", null, cv)
                // Notify Adapter
//            adapter.notifyItemInserted(listOfTasks.size-1)
                adapter2.notifyItemInserted(listOfCards.size-1)
                // 3. Reset text field
                inputTextField.setText("")
                inputDay.setText("")
                inputMonth.setText("")
                inputYear.setText("")
                // 4. Save File
                saveItems()
            }
        }
    }
    // Save data that user inputted
    // Save data by writing and reading from a file

    // Create a method to get the file we need
    fun getDataFile() : File {

        // Every lien is going to represent a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }

    // Load the items by reading every line in the data file
    fun loadItems() {
        try{
            listOfCards.clear()
            val selectQuery = "SELECT * FROM TASKCARDS"
            val ds = helper.writableDatabase
            val cursor = ds.rawQuery(selectQuery, null)
            Log.e("init", cursor.columnNames.size.toString())
            if (cursor.moveToFirst()){

                do{
                    Log.e("USERID", cursor.getInt(0).toString())
                    listOfCards.add(TaskCard(cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4)))
                }while(cursor.moveToNext())

            }

//            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
            getDataFile().forEachLine{println(it)}
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }

    // Save items by writing them into our data file
    fun saveItems() {
        try{

            FileUtils.writeLines(getDataFile(), listOfCards)
            getDataFile().forEachLine{println(it)}
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {

            loadItems()
            adapter2.notifyDataSetChanged()

        }
    }
}