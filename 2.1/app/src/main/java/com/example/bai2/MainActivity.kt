package com.example.bai2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sl = findViewById<EditText>(R.id.sl)
        val tao = findViewById<Button>(R.id.tao)
        val ds = findViewById<LinearLayout>(R.id.ds)

        tao.setOnClickListener {
            val nhap = sl.text.toString()
            ds.removeAllViews()
            val soLuong = nhap.toIntOrNull()

            if(soLuong != null && soLuong > 0){
                for(i in 1..soLuong){

                    val oChua = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 150
                    )
                    oChua.setMargins(20, 20, 20, 0)

                    val dm = TextView(this)
                    dm.text = i.toString()
                    dm.setBackgroundResource(R.drawable.dm)
                    dm.gravity = Gravity.CENTER

                    dm.layoutParams = oChua

                    ds.addView(dm)
                }
            } else{
                Toast.makeText(this, "Dữ liệu bạn nhập không hợp lệ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
