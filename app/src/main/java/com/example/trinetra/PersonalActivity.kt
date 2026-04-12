package com.example.trinetra

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PersonalActivity : AppCompatActivity() {

    private lateinit var backBtn: ImageView
    private lateinit var fullName: EditText
    private lateinit var age: EditText
    private lateinit var gender: Spinner
    private lateinit var vehicle: EditText

    private lateinit var mobileNumber: EditText
    private lateinit var address: EditText
    private lateinit var city: EditText
    private lateinit var state: EditText
    private lateinit var saveBtn: Button

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal)

        // Firebase Initialize
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://trinetradb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")

        // 🔗 Bind Views
        backBtn = findViewById(R.id.backBtn)
        fullName = findViewById(R.id.etFullName)
        age = findViewById(R.id.etAge)
        gender = findViewById(R.id.spinnerGender)
        vehicle = findViewById(R.id.etVehicle)
        mobileNumber = findViewById(R.id.etMobileNumber)
        address = findViewById(R.id.etAddress)
        city = findViewById(R.id.etCity)
        state = findViewById(R.id.etState)
        saveBtn = findViewById(R.id.btnSaveDetails)

        Log.d("TRINETRA_DEBUG", "Activity Started & Views Bound")

        // 🔙 Back Button
        backBtn.setOnClickListener {
            finish()
        }

        // 💾 Save Button
        saveBtn.setOnClickListener {
            Log.d("TRINETRA_DEBUG", "Save Button Clicked!")
            // Turant confirm karne ke liye ek Toast
            Toast.makeText(this, "Processing...", Toast.LENGTH_SHORT).show()
            saveDataToFirebase()
        }
    }

    private fun saveDataToFirebase() {
        val name = fullName.text.toString().trim()
        val userAge = age.text.toString().trim()
        val userGender = gender.selectedItem.toString()
        val vehicleNo = vehicle.text.toString().trim()
        val phone = mobileNumber.text.toString().trim()
        val userAddress = address.text.toString().trim()
        val userCity = city.text.toString().trim()
        val userState = state.text.toString().trim()

        // ✅ Validation
        if (name.isEmpty() || userAge.isEmpty() || userAddress.isEmpty() || vehicleNo.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            Log.e("TRINETRA_DEBUG", "Validation Failed: Empty Fields")
            return
        }

        // 🛠️ CRITICAL FIX: Agar login nahi hai toh Testing ID use karega
        val userId = auth.currentUser?.uid ?: "TestUser123"
        Log.d("TRINETRA_DEBUG", "Using ID: $userId")

        // Data Pack
        val personalInfo = PersonalDetails(
            fullName = name,
            mobileNumber = phone,
            age = userAge,
            gender = userGender,
            vehicleNumber = vehicleNo,
            address = userAddress,
            city = userCity,
            state = userState
        )

        // 🔥 FIREBASE PUSH
        database.child(userId).child("PersonalDetails").setValue(personalInfo)
            .addOnSuccessListener {
                Log.d("TRINETRA_DEBUG", "Data saved successfully!")
                Toast.makeText(this, "Information Stored Successfully! ✅", Toast.LENGTH_LONG).show()

                // Agli screen (Medical) par bhejna ho toh yahan code dalo
            }
            .addOnFailureListener { error ->
                Log.e("TRINETRA_DEBUG", "Firebase Error: ${error.message}")
                Toast.makeText(this, "Failed to Save: ${error.message}", Toast.LENGTH_SHORT).show()
            }
    }
}