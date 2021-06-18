package com.ngopidev.project.starterkit.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ngopidev.project.starterkit.databinding.ActivityLoginBinding
import com.ngopidev.project.starterkit.helper.MethodHelper
import com.ngopidev.project.starterkit.helper.MethodHelper.goTo
import com.ngopidev.project.starterkit.helper.MethodHelper.longToast
import com.ngopidev.project.starterkit.helper.MethodHelper.shortToast


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 *   digunakan untuk activity login
 **/
class LoginActivity : AppCompatActivity(){
    //binding activity_login menggunakan konsep viewbinding
    private val loginbinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(loginbinding.root)
        loginbinding.btnLogin.setOnClickListener{
            doLoginActivity()
        }
    }

    private fun doLoginActivity(){
        val email = loginbinding.etEmail.text.toString()
        val password = loginbinding.etPassword.text.toString()
        if(email.isEmpty() || password.isEmpty()){
            this@LoginActivity shortToast "email atau password tidak boleh kosong"
        }else{
            this@LoginActivity longToast "hello $email , selamat datang"
            this@LoginActivity goTo MainActivity::class.java
            //unhide these lines for using intent with parameter
            //val intent = Intent(this@LoginActivity, MainActivity::class.java)
            //intent.putExtra("email", email)
            //this@LoginActivity goTo intent
        }
    }
}