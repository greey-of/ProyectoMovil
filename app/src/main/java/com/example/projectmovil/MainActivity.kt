package com.example.projectmovil

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val heroImage: ImageView = findViewById(R.id.background_image)
        val titleText: TextView = findViewById(R.id.title_text)
        val subtitleText: TextView = findViewById(R.id.subtitle_text)
        val glassContainer: LinearLayout = findViewById(R.id.bottom_container)

        val btnSignup: Button = findViewById(R.id.btn_signup)
        val btnLogin: Button = findViewById(R.id.btn_login)

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val fadeTitle = AnimationUtils.loadAnimation(this, R.anim.fade_title)
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        val scaleBtn = AnimationUtils.loadAnimation(this, R.anim.scale_btn)
        val pressAnim = AnimationUtils.loadAnimation(this, R.anim.btn_press)

        heroImage.startAnimation(fadeIn)
        titleText.startAnimation(fadeTitle)
        subtitleText.startAnimation(fadeTitle)
        glassContainer.startAnimation(slideUp)
        btnSignup.startAnimation(scaleBtn)
        btnLogin.startAnimation(scaleBtn)

        btnSignup.setOnClickListener {
            it.startAnimation(pressAnim)
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        btnLogin.setOnClickListener {
            it.startAnimation(pressAnim)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}