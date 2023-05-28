package com.intermediate.substory.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.intermediate.substory.Repository
import com.intermediate.substory.ResultCustom
import com.intermediate.substory.model.LoginRequest
import com.intermediate.substory.model.LoginResult
import com.intermediate.substory.model.UserModel
import com.intermediate.substory.model.UserPreference
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: UserPreference) : ViewModel() {
    private val repository : Repository = Repository()

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun login(loginRequest: LoginRequest) : LiveData<ResultCustom<LoginResult>> {
        return repository.login(loginRequest)
    }

    fun saveLogin(token : String){
        viewModelScope.launch {
            pref.login(token)
        }
    }
    fun saveUser(user: UserModel)  {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
}