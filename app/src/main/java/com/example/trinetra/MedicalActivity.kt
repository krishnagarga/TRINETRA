package com.example.trinetra

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MedicalActivity : AppCompatActivity() {

    // 1. Declare Views (Exact match with your XML)
    private lateinit var bloodSpinner: Spinner
    private lateinit var weightEt: EditText
    private lateinit var allergyEt: EditText
    private lateinit var conditionEt: EditText
    private lateinit var medicationEt: EditText
    private lateinit var surgeryEt: EditText
    private lateinit var doctorEt: EditText
    private lateinit var saveBtn: Button

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medical)

        // 2. Initialize Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://trinetradb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")

        // 🔗 Bind Views (Using your XML IDs)
        val backBtn = findViewById<ImageView>(R.id.backBtn)
        bloodSpinner = findViewById(R.id.spBlood)
        weightEt = findViewById(R.id.etWeight)
        allergyEt = findViewById(R.id.etAllergy)
        conditionEt = findViewById(R.id.etCondition)
        medicationEt = findViewById(R.id.etMedication)
        surgeryEt = findViewById(R.id.etSurgery)
        doctorEt = findViewById(R.id.etDoctor)
        saveBtn = findViewById(R.id.btnSaveMedical)

        // 🔙 Back Button
        backBtn.setOnClickListener {
            finish()
        }

        // 💾 Save Button Click
        saveBtn.setOnClickListener {
            Log.d("TRINETRA_DEBUG", "Medical Save Clicked!")
            saveMedicalData()
        }
    }

    private fun saveMedicalData() {
        // Data Fetching
        val bloodGroup = bloodSpinner.selectedItem.toString()
        val weight = weightEt.text.toString().trim()
        val allergy = allergyEt.text.toString().trim()
        val condition = conditionEt.text.toString().trim()
        val medication = medicationEt.text.toString().trim()
        val surgery = surgeryEt.text.toString().trim()
        val doctor = doctorEt.text.toString().trim()

        // Simple Validation
        if (weight.isEmpty() || doctor.isEmpty()) {
            Toast.makeText(this, "Weight and Doctor Contact are required", Toast.LENGTH_SHORT).show()
            return
        }

        // Use current UID or Test ID
        val userId = auth.currentUser?.uid ?: "TestUser123"

        // Create MedicalDetails Object
        val medicalDetails = MedicalDetails(
            bloodGroup = bloodGroup,
            weight = weight,
            allergies = allergy,
            chronicConditions = condition,
            currentMedications = medication,
            pastSurgeries = surgery,
            doctorContact = doctor
        )

        // 🔥 FIREBASE PUSH
        database.child(userId).child("MedicalDetails").setValue(medicalDetails)
            .addOnSuccessListener {
                Log.d("TRINETRA_DEBUG", "Medical Data Saved Successfully")
                Toast.makeText(this, "Medical Information Stored! ✅", Toast.LENGTH_SHORT).show()

                // Optional: Save hone ke baad wapas dashboard ya home pe bhejna
                // finish()
            }
            .addOnFailureListener { e ->
                Log.e("TRINETRA_DEBUG", "Error: ${e.message}")
                Toast.makeText(this, "Failed to save: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}