package com.example.trinetra

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class HomeActivity : AppCompatActivity() {

    private lateinit var txtUser: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // 🔥 USER TEXT
        txtUser = findViewById(R.id.txtUser)

        // 🔥 FIREBASE
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user != null) {

            val uid = user.uid

            val db = FirebaseDatabase.getInstance("https://trinetradb-default-rtdb.asia-southeast1.firebasedatabase.app/")

            val nameRef = db.getReference("Users")
                .child(uid)
                .child("PersonalDetails")
                .child("fullName")

            nameRef.get().addOnSuccessListener {

                val name = it.getValue(String::class.java)

                if (!name.isNullOrEmpty()) {
                    txtUser.text = name
                } else {
                    txtUser.text = "User"
                }

            }.addOnFailureListener {
                txtUser.text = "User"
            }
        }

        // 🔥 PERSONAL
        findViewById<TextView>(R.id.cardPersonal).setOnClickListener {
            startActivity(Intent(this, PersonalActivity::class.java))
        }

        // 🔥 MEDICAL
        findViewById<TextView>(R.id.cardMedical).setOnClickListener {
            startActivity(Intent(this, MedicalActivity::class.java))
        }

        // 🔥 EMERGENCY
        findViewById<TextView>(R.id.cardEmergency).setOnClickListener {
            startActivity(Intent(this, EmergencyActivity::class.java))
        }

        // 🚨 SOS BUTTON
        findViewById<TextView>(R.id.btnSOS).setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("Emergency SOS")
                .setMessage("Are you sure you want to send an emergency alert?")
                .setCancelable(false)

                .setPositiveButton("Confirm") { _, _ ->

                    val uid = FirebaseAuth.getInstance().currentUser?.uid

                    if (uid != null) {

                        // 🔥 Firebase me SOS trigger ON
                        FirebaseDatabase.getInstance().reference
                            .child("Users")
                            .child(uid)
                            .child("sos")
                            .setValue(true)

                        Toast.makeText(
                            this,
                            "🚨 Alert has been sent!",
                            Toast.LENGTH_LONG
                        ).show()

                    } else {
                        Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
                    }
                }

                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }

                .show()
        }

        // LOG OUT BUTTON:----
        val logoutBtn = findViewById<TextView>(R.id.cardLogout)

        logoutBtn.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setCancelable(false)

                .setPositiveButton("Yes") { _, _ ->
                    // 🔥 Firebase logout
                    FirebaseAuth.getInstance().signOut()

                    // 🔥 Redirect to Splash
                    val intent = Intent(this, SplashActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }

                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }

                .show()
        }
    }
}