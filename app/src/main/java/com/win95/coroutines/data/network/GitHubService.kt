package com.win95.coroutines.data.network

import com.win95.coroutines.data.model.GitHubRepo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("users/{user}/repos")
    suspend fun getStarredRepositories(@Path("user") userName: String?): Response<List<GitHubRepo?>?>?
}