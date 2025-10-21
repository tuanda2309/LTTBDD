package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class Create : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.create)

        val back = findViewById<ImageView>(R.id.back)

        back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val password = findViewById<EditText>(R.id.password)
        val confirm =  findViewById<EditText>(R.id.confirm)
        val next = findViewById<Button>(R.id.next)

        next.setOnClickListener {
            val nhapPass = password.text.toString().trim()
            val nhapConfirm = confirm.text.toString().trim()

            if(nhapPass != nhapConfirm) Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show()
            else if(nhapPass.isEmpty() || nhapConfirm.isEmpty()) Toast.makeText(this, "Vui lòng nhập mật khẩu hợp lệ", Toast.LENGTH_SHORT).show()
            else
            {
                val email = intent.getStringExtra("email") ?: ""
                val verify = intent.getStringExtra("verify") ?: ""
                val trangTiepTheo = Intent(this, Confirm::class.java)
                trangTiepTheo.putExtra("email", email)
                trangTiepTheo.putExtra("verify", verify)
                trangTiepTheo.putExtra("password", nhapPass)
                startActivity(trangTiepTheo)
            }
        }

    }
}
