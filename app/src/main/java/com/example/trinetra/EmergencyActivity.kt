package com.example.trinetra

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EmergencyActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency) // XML ka naam match kar lena

        // 1. Firebase Init
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://trinetradb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")

        // 2. Bind Basic Views
        val backBtn = findViewById<ImageView>(R.id.backBtn)
        val saveBtn = findViewById<Button>(R.id.btnSaveContacts)

        // 🔙 Back button
        backBtn.setOnClickListener {
            finish()
        }

        // 💾 Save button click
        saveBtn.setOnClickListener {
            Log.d("TRINETRA_DEBUG", "Saving Emergency Contacts...")
            saveContactsToFirebase()
        }
    }

    private fun saveContactsToFirebase() {
        val userId = auth.currentUser?.uid ?: "TestUser123"
        val contactsList = mutableListOf<EmergencyContact>()

        // 📝 Tumhari XML mein jo IDs hain: contact1, contact2, contact3, contact4
        val contactLayoutIds = listOf(R.id.contact1, R.id.contact2, R.id.contact3, R.id.contact4)

        for (layoutId in contactLayoutIds) {
            // Pehle included layout ka view pakdo
            val contactView = findViewById<View>(layoutId)

            // Phir uske andar ke fields (IDs: etContactName, etContactPhone, etRelation)
            val nameEt = contactView.findViewById<EditText>(R.id.etName)
            val phoneEt = contactView.findViewById<EditText>(R.id.etPhone)
            val relationEt = contactView.findViewById<EditText>(R.id.etRelation)

            val name = nameEt.text.toString().trim()
            val phone = phoneEt.text.toString().trim()
            val relation = relationEt.text.toString().trim()

            // Agar Name aur Phone bhare hain, tabhi list mein add karo
            if (name.isNotEmpty() && phone.isNotEmpty()) {
                contactsList.add(EmergencyContact(
                    name = name,
                    phoneNumber = phone,
                    relation = relation
                ))
            }
        }

        if (contactsList.isEmpty()) {
            Toast.makeText(this, "Please add at least one contact", Toast.LENGTH_SHORT).show()
            return
        }

        // 🔥 Firebase mein save karo
        database.child(userId).child("EmergencyContacts").setValue(contactsList)
            .addOnSuccessListener {
                Log.d("TRINETRA_DEBUG", "Emergency Contacts saved!")
                Toast.makeText(this, "Contacts Saved Successfully ✅", Toast.LENGTH_SHORT).show()
                // finish() // Optional: Save ke baad piche jane ke liye
            }
            .addOnFailureListener { e ->
                Log.e("TRINETRA_DEBUG", "Error: ${e.message}")
                Toast.makeText(this, "Failed to save: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}