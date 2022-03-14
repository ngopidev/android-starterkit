package com.ngopidev.project.starterkit.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ngopidev.project.starterkit.databinding.ActivityLoginBinding
import com.ngopidev.project.starterkit.helper.MethodHelper
import com.ngopidev.project.starterkit.helper.MethodHelper.goTo
import com.ngopidev.project.starterkit.helper.MethodHelper.longToast
import com.ngopidev.project.starterkit.helper.MethodHelper.shortToast
import com.ngopidev.project.starterkit.helper.Resource
import com.ngopidev.project.starterkit.viewmodels.LoginViewModel
import com.ngopidev.project.starterkit.viewmodels.LoginViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 *   digunakan untuk activity login
 **/
class LoginActivity : AppCompatActivity(), KodeinAware{
    override val kodein by kodein()
    private val loginViewModelFactory : LoginViewModelFactory by instance()
    private val loginViewModel by lazy {
        ViewModelProvider(this, loginViewModelFactory).get(LoginViewModel::class.java)
    }
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
            loginViewModel.apiKey = "ADD APIKEY IF AVAIL"
            loginViewModel.email = email
            loginViewModel.pass = password
            loginViewModel.doLogin.observe(this, Observer {
                when(it){
                    is Resource.Loading ->{
                        //can add loader here
                    }
                    is Resource.Success ->{
                        this@LoginActivity longToast "hello $email , selamat datang"
                        this@LoginActivity goTo MainActivity::class.java
                        //unhide these lines for using intent with parameter
                        //val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        //intent.putExtra("email", email)
                        //this@LoginActivity goTo intent
                    }
                    is Resource.Error ->{
                        this@LoginActivity shortToast it.message!!
                    }
                }
            })

        }
    }
}