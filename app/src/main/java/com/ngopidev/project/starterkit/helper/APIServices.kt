package com.ngopidev.project.starterkit.helper

import com.ngopidev.project.starterkit.models.LoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 *   add all
 **/
interface APIServices {
    /**
     * login
     * @queryparam apikey
     * @queryparam email
     * @queryparam password
     */
    @GET("add url here")
    suspend fun doLogin(
        @Query("apikey") apikey : String, // add apikey if needed
        @Query("email") email : String,
        @Query("password") password : String,
    ) : Response<LoginResponse>
}