package com.example.Test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val i3 = findViewById<Button>(R.id.i3)
        val i2 = findViewById<TextView>(R.id.i2)

        i3.setOnClickListener {
            i2.text = "Đoàn Anh Tuấn"
            i2.visibility = View.VISIBLE
        }
    }
}
