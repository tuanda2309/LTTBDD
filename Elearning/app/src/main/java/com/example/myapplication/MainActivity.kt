package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val text = findViewById<TextView>(R.id.text)
        val button_1 = findViewById<Button>(R.id.button_1)
        val button_2 = findViewById<Button>(R.id.button_2)
        val logo = findViewById<ImageView>(R.id.logo)
        val image = findViewById<ImageView>(R.id.image)

        Log.d("MainActivity", "onCreate() được gọi - Giao diện đã sẵn sàng")

        button_1.setOnClickListener {
            text.text = "Text đã được thay đổi!"
            Log.i("MainActivity", "Button 1 được nhấn - TextView đã thay đổi nội dung")
        }

        button_2.setOnClickListener {
            Log.d("MainActivity", "Button 2 được nhấn - Chuẩn bị hiển thị Toast")

            val inflater: LayoutInflater = layoutInflater
            val layout = inflater.inflate(R.layout.toast, null)

            val toastText = layout.findViewById<TextView>(R.id.toast_text)
            toastText.text = "Bạn vừa bấm nút!"

            val toast = Toast(applicationContext)
            toast.duration = Toast.LENGTH_SHORT
            toast.view = layout
            toast.show()

            Log.d("MainActivity", "Toast hiển thị thành công 🎉")
        }

        logo.setOnClickListener {
            val intent = Intent(this, GifActivity::class.java)
            startActivity(intent)
        }

        image.setOnClickListener {
            val intent = Intent(this, Animated::class.java)
            startActivity(intent)
        }

    }
}
