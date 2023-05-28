package com.intermediate.substory

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.intermediate.substory.api.ApiConfig
import com.intermediate.substory.model.*


import okhttp3.MultipartBody
import okhttp3.RequestBody

class Repository {

    fun signUp(registerRequest: RegisterRequest): LiveData<ResultCustom<RegisterResponse>> =
        liveData {
            emit(ResultCustom.Loading)
            try {
                val response = ApiConfig.getApiService().register(registerRequest)
                if (response != null) {
                    emit(ResultCustom.Success(response))
                } else {
                    emit(ResultCustom.Error("Terjadi Kesalahan"))
                }
            } catch (exception: Exception) {
                emit(ResultCustom.Error(exception.message ?: "Unknown error"))
            }
        }

    fun login(loginRequest: LoginRequest): LiveData<ResultCustom<LoginResult>> = liveData {
        emit(ResultCustom.Loading)
        try {
            val response = ApiConfig.getApiService().login(loginRequest)
            if (response.loginResult != null) {
                emit(ResultCustom.Success(response.loginResult))
            } else {
                emit(ResultCustom.Error("Terjadi Kesalahan"))
            }
        } catch (exception: Exception) {
            emit(ResultCustom.Error(exception.message ?: "Unknown error"))
        }
    }

    fun getAllStories(token : String) : LiveData<ResultCustom<List<StoryModel>>> = liveData {
        emit(ResultCustom.Loading)
        try {
            val response = ApiConfig.getApiService().getAllStories(token)
            if (response.listStory != null) {
                emit(ResultCustom.Success(response.listStory))
            } else {
                emit(ResultCustom.Error("Terjadi Kesalahan"))
            }
        } catch (exception: Exception) {
            emit(ResultCustom.Error(exception.message ?: "Unknown error"))
        }
    }

    fun getStoryById(id : String, authorization : String ) : LiveData<ResultCustom<DetailStory>> = liveData {
        emit(ResultCustom.Loading)
        try {
            val response = ApiConfig.getApiService().getStoryById(id, authorization)
            if (response != null) {
                emit(ResultCustom.Success(response.story))
            } else {
                emit(ResultCustom.Error("Terjadi Kesalahan"))
            }
        } catch (exception: Exception) {
            emit(ResultCustom.Error(exception.message ?: "Unknown error"))
        }
    }

    fun uploadStory(description : RequestBody, photo : MultipartBody.Part, token : String) : LiveData<ResultCustom<UploadStory>> = liveData {
        emit(ResultCustom.Loading)
        try {
            val response = ApiConfig.getApiService().uploadStory(description = description ,photo = photo ,lat = null,lon = null, token = token)
            if (response != null){
                emit(ResultCustom.Success(response))
            } else {
                emit(ResultCustom.Error("Terjadi Kesalahan"))
            }
        } catch (exception: Exception) {
            emit(ResultCustom.Error(exception.message ?: "Unknown error"))
        }
    }

    fun getAllStoriesWithLocation(token: String) : LiveData<ResultCustom<List<StoryModel>>> = liveData {
        emit(ResultCustom.Loading)
        try {
            val response = ApiConfig.getApiService().getAllStories(token, size = 30, location = 1)
            if (response.listStory != null) {
                emit(ResultCustom.Success(response.listStory))
            } else {
                emit(ResultCustom.Error("Terjadi Kesalahan"))
            }
        } catch (exception: Exception) {
            emit(ResultCustom.Error(exception.message ?: "Unknown error"))
        }
    }

}