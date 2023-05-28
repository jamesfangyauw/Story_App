package com.dicoding.picodiploma.loginwithanimation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.intermediate.substory.Repository
import com.intermediate.substory.ResultCustom
import com.intermediate.substory.model.StoryModel

class MapsViewModel : ViewModel() {

    private val repository: Repository = Repository()

    fun getAllStoriesWithLocation(token : String): LiveData<ResultCustom<List<StoryModel>>> {
        return repository.getAllStoriesWithLocation(token)
    }
}