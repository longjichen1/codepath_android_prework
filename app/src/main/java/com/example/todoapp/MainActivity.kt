package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    var listOfCards = mutableListOf<TaskCard>()
    lateinit var adapter2: TaskCardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        loadItems()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener2 = object : TaskCardAdapter.OnLongClickListener {
            override fun onCardLongClicked(position: Int) {
                // Edit card
                saveItems()
            }
        }

        val onDeleteListener = object : TaskCardAdapter.OnDeleteListener {
            override fun onCardLongClicked(position: Int) {
                // 1. Remove item from list
                listOfCards.removeAt(position)

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
        val inputMonth = findViewById<EditText>(R.id.editTextMonth)
        val inputDay= findViewById<EditText>(R.id.editTextDate)
        val inputYear = findViewById<EditText>(R.id.editTextYear)

        // Clear All Tasks
        findViewById<Button>(R.id.clearTasks).setOnClickListener{
//            listOfTasks.clear()
//            adapter.notifyDataSetChanged()
            listOfCards.clear()
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
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
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
}