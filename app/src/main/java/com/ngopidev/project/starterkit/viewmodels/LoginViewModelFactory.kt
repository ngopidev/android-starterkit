package com.ngopidev.project.starterkit.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ngopidev.project.starterkit.repositories.AppsRepositories

class LoginViewModelFactory(private val repositories: AppsRepositories) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repositories) as T
    }
}