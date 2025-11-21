package com.example.uthapi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uthapi.data.model.Task
import com.example.uthapi.ui.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(navController: NavController, viewModel: TaskViewModel = viewModel()) {
    val tasks by viewModel.taskList.collectAsState(initial = emptyList<Task>())
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) { viewModel.getTasks() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("List", style = MaterialTheme.typography.titleLarge) },
                actions = {
                    IconButton(onClick = { viewModel.getTasks() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { padding ->
        when {
            isLoading -> LoadingView()
            !errorMessage.isNullOrEmpty() -> EmptyView("Không thể tải dữ liệu", "😴")
            tasks.isEmpty() -> EmptyView("No Tasks Yet\nStay productive — add something to do", "💤")
            else -> TaskListContent(tasks, navController, padding)
        }
    }
}

@Composable
fun TaskListContent(tasks: List<Task>, navController: NavController, padding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(8.dp)
    ) {
        items(tasks) { task ->
            val bgColor = when (task.status.lowercase()) {
                "in progress" -> Color(0xFFFFCDD2) // Đỏ nhạt
                "pending" -> Color(0xFFBBDEFB) // Xanh nhạt
                "completed" -> Color(0xFFC8E6C9) // Xanh lá nhạt
                else -> Color(0xFFF5F5F5)
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { navController.navigate("taskDetail/${task.id}") },
                colors = CardDefaults.cardColors(containerColor = bgColor),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(task.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(4.dp))
                    Text(task.description ?: "", style = MaterialTheme.typography.bodySmall)
                    Spacer(Modifier.height(8.dp))
                    Text("Status: ${task.status}", fontWeight = FontWeight.Medium)
                    Text("Priority: ${task.priority}")
                    Text("Due: ${task.dueDate?.substringBefore('T') ?: "N/A"}")
                }
            }
        }
    }
}

@Composable
fun EmptyView(message: String, emoji: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(emoji, style = MaterialTheme.typography.displayLarge)
            Spacer(Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
