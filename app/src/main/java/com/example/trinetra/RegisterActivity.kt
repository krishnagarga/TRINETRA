package com.example.trinetra

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val registerBtn = findViewById<Button>(R.id.registerBtn)

        registerBtn.setOnClickListener {

            val userEmail = email.text.toString().trim()
            val userPass = password.text.toString().trim()

            // ✅ Empty check
            if (userEmail.isEmpty() || userPass.isEmpty()) {
                Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ✅ Password length check
            if (userPass.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 🔥 Firebase Register
            auth.createUserWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        Toast.makeText(this, "Account Created Successfully ✅", Toast.LENGTH_SHORT).show()

                        // 👉 Clear fields
                        email.text.clear()
                        password.text.clear()

                        // 🚀 OPTIONAL: Go to Login screen
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(
                            this,
                            "Error: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}