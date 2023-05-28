package com.dicoding.picodiploma.loginwithanimation

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.dicoding.picodiploma.loginwithanimation.data.StoryRemoteMediator
import com.dicoding.picodiploma.loginwithanimation.db.StoryDatabase
import com.dicoding.picodiploma.loginwithanimation.db.StoryEntity
import com.intermediate.substory.api.ApiService

class MainRepository(private val storyDatabase: StoryDatabase, private val apiService: ApiService) {

    @OptIn(ExperimentalPagingApi::class)
    fun getAllStories(token : String) : LiveData<PagingData<StoryEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = StoryRemoteMediator(token, storyDatabase, apiService),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStories()
            }).liveData
    }

}