package com.example.uthapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uthapi.data.model.Task
import com.example.uthapi.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private val _taskList = MutableStateFlow<List<Task>>(emptyList())
    val taskList: StateFlow<List<Task>> = _taskList

    private val _taskDetail = MutableStateFlow<Task?>(null)
    val taskDetail: StateFlow<Task?> = _taskDetail

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // 🟢 Lấy danh sách task
    fun getTasks() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = RetrofitInstance.api.getTasks()
                if (response.isSuccessful) {
                    _taskList.value = response.body()?.data ?: emptyList()
                } else {
                    _errorMessage.value = "Không thể tải dữ liệu (${response.code()})"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = "Không thể kết nối tới server!"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // 🟢 Lấy chi tiết task
    fun getTaskDetail(id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getTaskDetail(id)
                if (response.isSuccessful) {
                    _taskDetail.value = response.body()?.data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 🔴 DELETE task
    fun deleteTask(id: Int, onDeleted: (() -> Unit)? = null) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.deleteTask(id)
                if (response.isSuccessful) {
                    getTasks()
                    onDeleted?.invoke()
                } else {
                    println("Delete failed: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
