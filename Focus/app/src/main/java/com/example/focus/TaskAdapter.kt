package com.example.focus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val tasks: MutableList<Task>, private val db: Database) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskTitle: TextView = view.findViewById(R.id.taskTitle)
        val doneCheckbox: CheckBox = view.findViewById(R.id.doneCheckbox)
        val deleteButton: Button = view.findViewById(R.id.deleteButton)
    }
//called only when new view holder is needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_task_adapter, parent, false)
        return TaskViewHolder(view)
    }
//called for each visible task-title and the status displayed to user
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskTitle.text = task.title
        holder.doneCheckbox.isChecked = task.isDone

        //handles the status of the task
        holder.doneCheckbox.setOnCheckedChangeListener { _, isChecked ->
            db.markTaskAsDone(task.id)
            task.isDone = isChecked
        }

        holder.deleteButton.setOnClickListener {
            //deletes the task from the database
            db.deleteTask(task.id)
            //removes the task from the list
            tasks.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, tasks.size)
        }
    }

    //returns how many tasks ,for the recyclerview to display
    override fun getItemCount(): Int = tasks.size
}
