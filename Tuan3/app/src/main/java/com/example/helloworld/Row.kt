package com.example.bai3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.widget.*

class Row : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.row)

        val back = findViewById<ImageView>(R.id.back)

        back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }
}