package com.example.focus

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context) : SQLiteOpenHelper(context, "tasks.db", null, 1) {

    //called to create the database table
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE tasks (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title TEXT, " +
                    "isDone INTEGER)"
        )
    }
//if it already exits override it
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS tasks")
        onCreate(db)
    }
//when user inputs -adds new task to database
    fun addTask(title: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("isDone", 0)//0= false 1=true 0=default
        }
    //inserts values into tasks table
        db.insert("tasks", null, values)
        db.close()
    }
//if task is deleted by the user-that task id is deleted from database
    fun deleteTask(id: Int) {
        val db = writableDatabase
        db.delete("tasks", "id=?", arrayOf(id.toString()))
        db.close()
    }
//if checkBox checked makes value 1 meaning done
    fun markTaskAsDone(id: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("isDone", 1)
        }
        db.update("tasks", values, "id=?", arrayOf(id.toString()))
        db.close()
    }
//this method reads all tasks from db
    fun getAllTasks(): List<Task> {
        val taskList = mutableListOf<Task>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM tasks", null)
        if (cursor.moveToFirst()) {
            do {
                val task = Task(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    title = cursor.getString(cursor.getColumnIndexOrThrow("title")),
                    isDone = cursor.getInt(cursor.getColumnIndexOrThrow("isDone")) == 1
                )
                taskList.add(task)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
    //returns to user id,title,isdone status
        return taskList
    }
}
