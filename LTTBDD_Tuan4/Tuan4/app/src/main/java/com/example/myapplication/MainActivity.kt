package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val email = findViewById<EditText>(R.id.email)
        val next = findViewById<Button>(R.id.next)
        val tt1 = findViewById<EditText>(R.id.tt1)
        val tt2 = findViewById<EditText>(R.id.tt2)
        val tt3 = findViewById<EditText>(R.id.tt3)

        val userEmail = intent.getStringExtra("email")
        val userVerify = intent.getStringExtra("verify")
        val userPassword = intent.getStringExtra("password")

        if (userEmail != null && userVerify != null && userPassword != null) {
            tt1.setText("Email: $userEmail")
            tt2.setText("Verify code: $userVerify")
            tt3.setText("Password: $userPassword")

            tt1.visibility = View.VISIBLE
            tt2.visibility = View.VISIBLE
            tt3.visibility = View.VISIBLE

            email.setText(userEmail)
        }

        next.setOnClickListener {
            val nhap = email.text.toString().trim()
            val mau = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"

            if (nhap.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show()
            } else if (!nhap.matches(mau.toRegex())) {
                Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show()
            } else {
                val trangTiepTheo = Intent(this, Verify::class.java)
                trangTiepTheo.putExtra("email", nhap)
                startActivity(trangTiepTheo)
            }
        }
    }
}
