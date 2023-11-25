package com.example.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.tasks.db.dao.TaskDao
import com.example.tasks.db.entity.Task
import kotlinx.coroutines.launch

class TaskViewModel(private val dao: TaskDao): ViewModel() {
    var newTaskName = ""

    val tasks = dao.getAll()
    val taskString = tasks.map { tasks -> formatTasks(tasks) }

    private fun formatTasks(tasks: List<Task>): String {
        return tasks.fold("") {
            str, item -> str + '\n' + formatTask(item)
        }
    }

    private fun formatTask(task: Task): String {
        var str = "ID: ${task.taskId}"
        str += '\n' + "Name: ${task.taskName}"
        str += '\n' + "Complete: ${task.taskDone}"
        return str
    }

    fun addTask() {
        viewModelScope.launch {
            val task = Task()
            task.taskName = newTaskName
            dao.insert(task)
        }
    }
}

class TaskViewModelFactory(private val dao: TaskDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}