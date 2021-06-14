package com.win95.coroutines.data.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.win95.coroutines.data.model.GitHubRepo
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GitHubClient {
    private val GITHUB_BASE_URL = "https://api.github.com/"

    companion object {
        lateinit var globalInstance: GitHubClient
        fun getInstance(): GitHubClient {
            if (!::globalInstance.isInitialized) {
                globalInstance = GitHubClient()
            }
            return globalInstance
        }

    }

    private var gitHubService: GitHubService? = null

    init {
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(GITHUB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        gitHubService = retrofit.create(GitHubService::class.java)
    }


    suspend fun getStarredRepos(userName: String): Response<List<GitHubRepo?>?>? {
        return gitHubService!!.getStarredRepositories(userName)
    }
}