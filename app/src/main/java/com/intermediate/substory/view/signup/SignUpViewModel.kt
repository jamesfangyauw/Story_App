package com.intermediate.substory.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intermediate.substory.Repository
import com.intermediate.substory.ResultCustom
import com.intermediate.substory.model.RegisterRequest
import com.intermediate.substory.model.RegisterResponse
import com.intermediate.substory.model.UserModel
import com.intermediate.substory.model.UserPreference
import kotlinx.coroutines.launch

class SignupViewModel(private val pref: UserPreference) : ViewModel() {
    private val repository : Repository = Repository()

    fun saveUser(user: UserModel)  {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

    fun signUp(request : RegisterRequest) : LiveData<ResultCustom<RegisterResponse>> {
        return repository.signUp(request)

    }
}