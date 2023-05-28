package com.intermediate.substory.view.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.intermediate.substory.Repository
import com.intermediate.substory.ResultCustom
import com.intermediate.substory.model.UploadStory
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel : ViewModel() {
    private val repository : Repository = Repository()

    fun uploadStory(desc : RequestBody, img : MultipartBody.Part, token : String) : LiveData<ResultCustom<UploadStory>> {
        return repository.uploadStory(desc, img, token)
    }
}