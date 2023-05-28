package com.dicoding.picodiploma.loginwithanimation.injection

import android.content.Context
import com.dicoding.picodiploma.loginwithanimation.MainRepository

import com.dicoding.picodiploma.loginwithanimation.db.StoryDatabase
import com.intermediate.substory.api.ApiConfig

object Injection {
    fun provideRepository(context: Context): MainRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return MainRepository(database, apiService)
    }
}