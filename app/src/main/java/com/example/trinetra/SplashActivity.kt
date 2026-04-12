package com.example.trinetra

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<ImageView>(R.id.logo)
        val title = findViewById<TextView>(R.id.title)
        val subtitle = findViewById<TextView>(R.id.subtitle)
        val desc = findViewById<TextView>(R.id.desc)
        val btn = findViewById<Button>(R.id.startBtn)
        val createAccount = findViewById<TextView>(R.id.createAccount)

        // 🔥 Animations (same as before)
        logo.scaleX = 0f
        logo.scaleY = 0f
        logo.animate().scaleX(1f).scaleY(1f).setDuration(800).start()

        title.translationY = 100f
        title.alpha = 0f
        title.animate().translationY(0f).alpha(1f).setDuration(700).setStartDelay(300).start()

        subtitle.alpha = 0f
        desc.alpha = 0f
        subtitle.animate().alpha(1f).setDuration(600).setStartDelay(600).start()
        desc.animate().alpha(1f).setDuration(600).setStartDelay(800).start()

        btn.scaleX = 0f
        btn.scaleY = 0f
        btn.animate().scaleX(1f).scaleY(1f).setDuration(600).setStartDelay(1000).start()

        // 🚀 LOGIN BUTTON
        btn.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)

            // 🔥 IMPORTANT FIX (no back to splash)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish() // 🔥 VERY IMPORTANT

            overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
        }

        // 🚀 REGISTER BUTTON
        createAccount.setOnClickListener {

            val intent = Intent(this, RegisterActivity::class.java)

            startActivity(intent)

            overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
        }
    }
}