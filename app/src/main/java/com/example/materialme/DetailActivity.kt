package com.example.materialme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        findViewById<TextView>(R.id.detailTitle).apply {
            text = intent.getStringExtra("title")
        }

        findViewById<ImageView>(R.id.detailImage).also {
            Glide.with(this).load(intent.getIntExtra("image_resource", 0)).into(it)
        }
    }
}