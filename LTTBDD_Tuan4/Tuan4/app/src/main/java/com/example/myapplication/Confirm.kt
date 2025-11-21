package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class Confirm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.confirm)

        val back = findViewById<ImageView>(R.id.back)

        back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val email = findViewById<EditText>(R.id.email)
        val verify = findViewById<EditText>(R.id.verify)
        val password = findViewById<EditText>(R.id.password)
        val summit = findViewById<Button>(R.id.summit)


        val userEmail = intent.getStringExtra("email") ?: ""
        val userVerify = intent.getStringExtra("verify") ?: ""
        val userPassword = intent.getStringExtra("password") ?: ""

        email.setText(userEmail)
        verify.setText(userVerify)
        password.setText(userPassword)

        password.transformationMethod = PasswordTransformationMethod.getInstance()

        email.isEnabled = false
        verify.isEnabled = false
        password.isEnabled = false

        summit.setOnClickListener {
            val trangTiepTheo = Intent(this, MainActivity::class.java)
            trangTiepTheo.putExtra("email", userEmail)
            trangTiepTheo.putExtra("verify", userVerify)
            trangTiepTheo.putExtra("password", userPassword)
            startActivity(trangTiepTheo)
        }
    }
}
