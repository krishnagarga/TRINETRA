package com.example.trinetra

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        loginBtn = findViewById(R.id.loginBtn)

        auth = FirebaseAuth.getInstance()

        loginBtn.setOnClickListener {

            val userEmail = email.text.toString().trim()
            val userPass = password.text.toString().trim()

            if (userEmail.isEmpty() || userPass.isEmpty()) {
                Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 🔥 Firebase Login
            auth.signInWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)

                        // 🔥 IMPORTANT (no splash loop)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                        startActivity(intent)

                    } else {
                        Toast.makeText(
                            this,
                            "Login Failed.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}