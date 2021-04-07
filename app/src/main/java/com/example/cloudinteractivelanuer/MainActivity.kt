package com.example.cloudinteractivelanuer

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.IntentCompat

class MainActivity : AppCompatActivity() {
    private lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById()
        setListener()
        init()
    }

    fun findViewById() {
        btn = findViewById(R.id.btn)
    }

    fun setListener() {
        btn.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }

    fun init() {

    }
}