package com.example.tasks

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.db.entity.Task

class TaskItemAdapter: RecyclerView.Adapter<TaskItemAdapter.TaskItemViewHolder>() {
    var data = listOf<Task>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    // Метод создающий (пока незаполненные) оболочки ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        return TaskItemViewHolder.inflateFrom(parent)
    }

    // Метод наполняющий (или обновляющий) оболочки ViewHolder посредством передачи определенного
    // объекта данных в метод bind() объекта класса ViewHolder
    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    // Класс оболочка над макетом, который умеет 1) надувать макет и хранить его внутри себя в виде
    // надутого View; 2) заполнять надутый view данными с помощью функции bind()
    class TaskItemViewHolder(private val rootView: TextView) : RecyclerView.ViewHolder(rootView) {

        fun bind(item: Task) {
            rootView.text = item.taskName
        }

        // Способ получения view может отличаться (в зависимости от того используем binding-классы
        // или нет)
        companion object {
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.task_item, parent, false) as TextView
                return TaskItemViewHolder(view)
            }
        }
    }
}