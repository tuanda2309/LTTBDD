package com.example.bai3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email = findViewById<EditText>(R.id.email)
        val kt = findViewById<Button>(R.id.kt)
        val email1 = findViewById<TextView>(R.id.email1)

        kt.setOnClickListener {
            val nhap = email.text.toString().trim()

            if(nhap.isEmpty()){
                email1.text = "Email không hợp lệ"
                email1.visibility = TextView.VISIBLE
            }

            else if(!nhap.contains("@")){
                email1.text = "Email không đúng định dạng"
                email1.visibility = TextView.VISIBLE
            }

            else{
                email1.text = "Email hợp lệ"
                email1.visibility = TextView.VISIBLE
            }

//            fun checkEmail(email: String): String {
//                if (email.isEmpty()) return "Email không hợp lệ"
//                if (!email.contains("@")) return "Email không đúng định dạng"
//                return "Bạn đã nhập email hợp lệ"
//            }
//            kt.setOnClickListener {
//                val nhap = email.text.toString().trim()
//                val result = checkEmail(nhap)
//                email1.text = result
//                email1.visibility = TextView.VISIBLE
//            }

        }
    }
}