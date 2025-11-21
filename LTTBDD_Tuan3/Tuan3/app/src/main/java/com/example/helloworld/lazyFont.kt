package com.example.bai3

import android.graphics.*
import android.text.TextPaint
import android.text.style.*


class lazyFont(private val kieuMoi : Typeface): TypefaceSpan(""){

    override fun updateDrawState(ds: TextPaint) {
        applyFont(ds, kieuMoi)
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyFont(paint, kieuMoi)
    }

    private fun applyFont(paint: Paint, font: Typeface){

        paint.typeface = font
    }
}