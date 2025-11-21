package com.example.bai3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Column : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.column)

        val back = findViewById<ImageView>(R.id.back)

        back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }
}