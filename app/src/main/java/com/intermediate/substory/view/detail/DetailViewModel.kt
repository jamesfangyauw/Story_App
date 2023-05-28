package com.intermediate.substory.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.intermediate.substory.Repository
import com.intermediate.substory.ResultCustom
import com.intermediate.substory.model.DetailStory

class DetailViewModel : ViewModel() {
    private val repository : Repository = Repository()

    fun getStoryById(id : String, authorization : String) : LiveData<ResultCustom<DetailStory>> {
        return repository.getStoryById(id,authorization)
    }
}