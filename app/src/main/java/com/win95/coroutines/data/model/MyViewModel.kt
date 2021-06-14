package com.win95.coroutines.data.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.win95.coroutines.data.network.GitHubClient
import kotlinx.coroutines.*
import retrofit2.Response

class MyViewModel : ViewModel() {


    val list = MutableLiveData<List<GitHubRepo>>()
    var invalid = MutableLiveData<Boolean>(true)

    fun getDataRepo(): List<GitHubRepo> {
        val mutableList = mutableListOf<GitHubRepo>()
        if (list.value != null) {
            for (i in list.value!!) mutableList.add(i)
        }
        return mutableList
    }

    fun getRepo(user: String) {
        viewModelScope.launch {
            val res = makeCall(user)
            if (res?.isSuccessful == false) {
                invalid.value =false
                list.value = emptyList()
            }else{
            list.value = res?.body() as List<GitHubRepo>?
            }
        }

    }

    suspend fun makeCall(user: String): Response<List<GitHubRepo?>?>? {
        return withContext(Dispatchers.IO) {
            GitHubClient.getInstance().getStarredRepos(user)
        }
    }
}
