package com.ngopidev.project.starterkit.repositories

import com.ngopidev.project.starterkit.helper.APIServices

//here is all repositories for all apiservices in projects
class AppsRepositories(private val apiServices: APIServices) {
    suspend fun doLogin(apiKey : String, email : String, pass : String) = apiServices.doLogin(apikey = apiKey, email = email, password = pass)
}