package com.example.uthapi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.uthapi.ui.screens.TaskListScreen
import com.example.uthapi.ui.screens.TaskDetailScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "taskList") {
        composable("taskList") { TaskListScreen(navController) }
        composable("taskDetail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
            TaskDetailScreen(taskId = id, navController = navController)
        }
    }
}
