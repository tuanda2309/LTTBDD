package com.example.bai3

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ten = findViewById<EditText>(R.id.ten)
        val tuoi = findViewById<EditText>(R.id.tuoi)
        val kt = findViewById<Button>(R.id.kt)
        val treTrau = findViewById<TextView>(R.id.treTrau)

        kt.setOnClickListener {
            val nhapTen = ten.text.toString().trim()
            val nhap = tuoi.text.toString().trim()
            val age = nhap.toIntOrNull()

            if(age == null){
                treTrau.text = "Nhập tuổi đi dm"
                treTrau.visibility = TextView.VISIBLE
            }

            else if(age >= 65){
                treTrau.text = "Người già"
                treTrau.visibility = TextView.VISIBLE

            }

            else if(age > 6 && age < 65){
                treTrau.text = "Người lớn"
                treTrau.visibility = TextView.VISIBLE
            }
            else if(age > 2 && age < 6){
                treTrau.text = "Trẻ em"
                treTrau.visibility = TextView.VISIBLE

            }

            else if(age <= 2){
                treTrau.text = "Em bé"
                treTrau.visibility = TextView.VISIBLE

            }

        }

    }
}
