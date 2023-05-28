package com.intermediate.substory.view.main

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.picodiploma.loginwithanimation.MainRepository
import com.dicoding.picodiploma.loginwithanimation.db.StoryEntity
import com.intermediate.substory.Repository
import com.intermediate.substory.ResultCustom
import com.intermediate.substory.model.StoryModel
import com.intermediate.substory.model.UserModel
import com.intermediate.substory.model.UserPreference
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserPreference, private val repository: MainRepository) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

    fun getAllStories(token : String): LiveData<PagingData<StoryEntity>> {
        return repository.getAllStories(token).cachedIn(viewModelScope)
    }

}
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainViewModelFactory(private val pref: UserPreference, private val repository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(pref, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}