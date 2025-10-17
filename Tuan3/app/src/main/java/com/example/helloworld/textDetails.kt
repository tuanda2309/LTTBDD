package com.example.bai3

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat

class textDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.text)

        val back = findViewById<ImageView>(R.id.back)

        back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val text = findViewById<TextView>(R.id.text)
        val textDetails = SpannableStringBuilder("The quick Brown\n" + "fox j u m p s over\n" + "the lazy dog.")

        textDetails.setSpan(ForegroundColorSpan(Color.BLACK), 0, textDetails.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textDetails.setSpan(StrikethroughSpan(), 4, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textDetails.setSpan(ForegroundColorSpan(Color.parseColor("#FFCD3D")), 10, 15,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textDetails.setSpan(StyleSpan(Typeface.BOLD), 30, 34, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textDetails.setSpan(UnderlineSpan(), 35, 38, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val lazy = ResourcesCompat.getFont(this, R.font.lazy)

        if(lazy != null){
            textDetails.setSpan(lazyFont(lazy), 39, 43, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        }

        textDetails.setSpan(StyleSpan(Typeface.ITALIC), 39, 43, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textDetails.setSpan(StyleSpan(Typeface.NORMAL), 39, 43, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textDetails.setSpan(RelativeSizeSpan(0.6f), 39, 43, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        text.text = textDetails



    }
}