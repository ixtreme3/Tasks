package com.example.tasks

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tasks.db.dao.TaskDao
import kotlinx.coroutines.launch

class EditTaskViewModel(taskId: Long, private val taskDao: TaskDao) : ViewModel() {
    val task = taskDao.get(taskId)

    private val _navigateToList = MutableLiveData<Boolean>()
    val navigateToList get() = _navigateToList

    fun updateTask() {
        viewModelScope.launch {
            Log.e("TEST", "${task.value?.taskDone}")
            taskDao.update(task.value!!)
            _navigateToList.value = true
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            taskDao.delete(task.value!!)
            _navigateToList.value = true
        }
    }

    fun onNavigatedToList() {
        _navigateToList.value = false
    }
}

class EditTaskViewModelFactory(private val taskId: Long, private val taskDao: TaskDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditTaskViewModel::class.java)) {
            return EditTaskViewModel(taskId, taskDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
