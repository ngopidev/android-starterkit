package com.ngopidev.project.starterkit.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ngopidev.project.starterkit.databinding.ActivityLoginBinding
import com.ngopidev.project.starterkit.helper.MethodHelper


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
            MethodHelper.showShortToast(this@LoginActivity, "email atau password tidak boleh kosong")
        }else{
            MethodHelper.showLongToast(this@LoginActivity, "hello $email , selamat datang")
            MethodHelper.goTo(this@LoginActivity, MainActivity::class.java)
        }
    }
}