package com.example.uthapi.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.example.uthapi.ui.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: Int,
    navController: NavController,
    viewModel: TaskViewModel = viewModel()
) {
    val task by viewModel.taskDetail.collectAsState()
    var showConfirmDelete by remember { mutableStateOf(false) }

    LaunchedEffect(taskId) { viewModel.getTaskDetail(taskId) }

    if (showConfirmDelete) {
        AlertDialog(
            onDismissRequest = { showConfirmDelete = false },
            title = { Text("Xác nhận xóa") },
            text = { Text("Bạn có chắc muốn xóa công việc này không?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteTask(taskId) { navController.popBackStack() }
                    showConfirmDelete = false
                }) { Text("Xóa") }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDelete = false }) { Text("Hủy") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showConfirmDelete = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )
        }
    ) { padding ->
        if (task == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                item {
                    AsyncImage(
                        model = task?.desImageURL,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(task!!.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                    Text(task!!.description, style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        InfoChip("Category", task!!.category)
                        InfoChip("Status", task!!.status)
                        InfoChip("Priority", task!!.priority)
                    }

                    Spacer(Modifier.height(16.dp))
                    Divider()
                }

                item { Text("Subtasks", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 12.dp)) }
                items(task!!.subtasks ?: emptyList()) { sub ->
                    Text("- ${sub.title} ${if (sub.isCompleted) "✅" else "❌"}")
                }

                item { Text("Attachments", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 12.dp)) }
                items(task!!.attachments ?: emptyList()) { att ->
                    Text("📎 ${att.fileName}")
                }

                item { Text("Reminders", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 12.dp)) }
                items(task!!.reminders ?: emptyList()) { rem ->
                    Text("⏰ ${rem.type} at ${rem.time}")
                }
            }
        }
    }
}

@Composable
fun InfoChip(title: String, value: String) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = "$title: $value",
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodySmall
        )
    }
}
