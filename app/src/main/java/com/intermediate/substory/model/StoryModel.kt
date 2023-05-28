package com.intermediate.substory.model

data class StoryModel(
    val name: String,
    val photoUrl: String,
    val id: String,
    val lat : Double? = null,
    val lon : Double? = null,
)

data class StoryResponse(
    val error: Boolean,
    val message: String,
    val listStory: List<StoryModel>?
)