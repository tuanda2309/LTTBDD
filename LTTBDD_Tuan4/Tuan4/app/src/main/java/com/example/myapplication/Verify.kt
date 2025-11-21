package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Verify : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.verify)

        val back = findViewById<ImageView>(R.id.back)

        back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val e1 = findViewById<EditText>(R.id.e1)
        val e2 = findViewById<EditText>(R.id.e2)
        val e3 = findViewById<EditText>(R.id.e3)
        val e4 = findViewById<EditText>(R.id.e4)
        val e5 = findViewById<EditText>(R.id.e5)
        val next = findViewById<Button>(R.id.next)

        val ds = listOf(e1, e2, e3, e4, e5)

        next.setOnClickListener {
            val verify = ds.joinToString("") { it.text.toString().trim() }

            if (verify.length != 5) {
                Toast.makeText(this, "Vui lòng nhập đủ 5 số!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!verify.all { it.isDigit() }) {
                Toast.makeText(this, "Mã chỉ được chứa các số (0-9)!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Mã bạn nhập là: $verify", Toast.LENGTH_SHORT).show()

            val email = intent.getStringExtra("email") ?: ""
            val trangTiepTheo = Intent(this, Create::class.java)
            trangTiepTheo.putExtra("email", email)
            trangTiepTheo.putExtra("verify", verify)
            startActivity(trangTiepTheo)
        }
    }
}
