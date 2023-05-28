package com.intermediate.substory.model

import com.google.gson.annotations.SerializedName

data class UploadStory(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String
)