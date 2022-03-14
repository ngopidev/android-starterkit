package com.ngopidev.project.starterkit.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngopidev.project.starterkit.helper.Resource
import com.ngopidev.project.starterkit.models.LoginResponse
import com.ngopidev.project.starterkit.repositories.AppsRepositories
import kotlinx.coroutines.launch
import java.lang.Exception


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 **/
class LoginViewModel(private val repositories: AppsRepositories) : ViewModel() {
    private lateinit var loginData : MutableLiveData<Resource<LoginResponse>>
    lateinit var email : String
    lateinit var pass : String
    lateinit var apiKey : String

    val doLogin : LiveData<Resource<LoginResponse>>
    get() {
        loginData = MutableLiveData()
        executeLogin()
        return loginData
    }

    private fun executeLogin() = viewModelScope.launch{
        loginData.value = Resource.Loading()
        try {
            loginData.value = Resource.Success(repositories.doLogin(apiKey, email, pass).body()!!)
        }catch (ex : Exception){
            loginData.value = Resource.Error(ex.message!!)
        }
    }

}