package com.example.bai3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.v1).setOnClickListener {
            val intent = Intent(this, textDetails::class.java)
            startActivity(intent)
        }

        findViewById<View>(R.id.v2).setOnClickListener {
            val intent = Intent(this, images::class.java)
            startActivity(intent)
        }

        findViewById<View>(R.id.v3).setOnClickListener {
            val intent = Intent(this, textField::class.java)
            startActivity(intent)
        }

        findViewById<View>(R.id.v5).setOnClickListener {
            val intent = Intent(this, Column::class.java)
            startActivity(intent)
        }

        findViewById<View>(R.id.v6).setOnClickListener {
            val intent = Intent(this, Row::class.java)
            startActivity(intent)
        }

    }
}