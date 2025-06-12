package com.example.focus

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Screentwo : AppCompatActivity() {

    private lateinit var db: Database
    private lateinit var adapter: TaskAdapter
    private lateinit var taskList: MutableList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screentwo)

        db = Database(this)
        taskList = db.getAllTasks().toMutableList()

        val taskInput = findViewById<EditText>(R.id.taskInput)
        val addButton = findViewById<Button>(R.id.addButton)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        adapter = TaskAdapter(taskList, db)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        addButton.setOnClickListener {
            val title = taskInput.text.toString()
            if (title.isNotEmpty()) {
                db.addTask(title)
                taskInput.text.clear()
                refreshTasks()
            }
        }
    }

    private fun refreshTasks() {
        taskList.clear()
        taskList.addAll(db.getAllTasks())
        adapter.notifyDataSetChanged()
    }
}
