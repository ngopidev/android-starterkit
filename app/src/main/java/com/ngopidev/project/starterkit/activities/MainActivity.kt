package com.ngopidev.project.starterkit.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ngopidev.project.starterkit.R
import com.ngopidev.project.starterkit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val mainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)
    }
}