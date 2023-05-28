package com.dicoding.picodiploma.loginwithanimation.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao {
    @Query("SELECT * FROM story_entity")
    fun getAllStories(): PagingSource<Int, StoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(stories: List<StoryEntity>)

    @Query("DELETE FROM story_entity")
    fun deleteAll()
}