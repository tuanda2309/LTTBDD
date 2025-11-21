package com.example.bai3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class images : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.images)

        val back = findViewById<ImageView>(R.id.back)

        back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        val t2 = findViewById<TextView>(R.id.t2)
        val text = SpannableString("Xem bài viết trên Wikipedia")
        val url = "https://vi.wikipedia.org/wiki/Tr%C6%B0%E1%BB%9Dng_%C4%90%E1%BA%A1i_h%E1%BB%8Dc_Giao_th%C3%B4ng_v%E1%BA%ADn_t%E1%BA%A3i_Th%C3%A0nh_ph%E1%BB%91_H%E1%BB%93_Ch%C3%AD_Minh"

        val click = object : ClickableSpan() {
            override fun onClick(p0: View) {
                val moLienKet = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(moLienKet)
            }
        }

        text.setSpan(click, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        t2.text = text
        t2.movementMethod = LinkMovementMethod.getInstance()
        t2.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
    }
}
