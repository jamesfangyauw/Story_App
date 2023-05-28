package com.intermediate.substory.model

data class Detail(
    val error : Boolean,
    val message : String,
    val story : DetailStory
)
data class DetailStory(
    var id: String,
    var name: String,
    var description: String,
    var photoUrl: String,
    var createdAt: String,
    var lat: Double,
    var lon: Double
)