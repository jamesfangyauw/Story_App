package com.dicoding.picodiploma.loginwithanimation.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "story_entity")
data class StoryEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "photoUrl")
    var photoUrl: String,


    @ColumnInfo(name = "lat")
    var lat: Double? = null,

    @field:SerializedName("lon")
    @ColumnInfo(name = "lon")
    var lon: Double? = null
) {
    constructor() : this("", "", "")
}