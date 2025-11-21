package com.example.uthapi.data.remote

import com.example.uthapi.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("researchUTH/tasks")
    suspend fun getTasks(): Response<ApiResponse<List<Task>>>

    @GET("researchUTH/task/{id}")
    suspend fun getTaskDetail(@Path("id") id: Int): Response<ApiResponse<Task>>

    @DELETE("researchUTH/task/{id}")
    suspend fun deleteTask(@Path("id") id: Int): Response<Unit>
}
