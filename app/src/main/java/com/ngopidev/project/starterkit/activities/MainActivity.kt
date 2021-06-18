package com.ngopidev.project.starterkit.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ngopidev.project.starterkit.R
import com.ngopidev.project.starterkit.databinding.ActivityMainBinding
import com.ngopidev.project.starterkit.helper.MethodHelper.shortToast

class MainActivity : AppCompatActivity() {
    private val mainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)
        //unhide these lines if loginactivity send parameter with intent
        //val email = intent.getStringExtra("email")
        //this@MainActivity shortToast "welcome $email"
    }
}