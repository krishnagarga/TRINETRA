package com.example.trinetra

data class PersonalDetails(
    val fullName: String? = null,
    val mobileNumber: String? = null,
    val age: String? = null,
    val gender: String? = null,
    val vehicleNumber: String? = null,
    val address: String? = null,
    val city: String? = null,
    val state: String? = null
)


data class MedicalDetails(
    val bloodGroup: String? = null,
    val weight: String? = null,
    val allergies: String? = null,
    val chronicConditions: String? = null,
    val currentMedications: String? = null,
    val pastSurgeries: String? = null,
    val doctorContact: String? = null
)

data class EmergencyContact(
    val name: String? = null,
    val phoneNumber: String? = null,
    val relation: String? = null // Nayi field add ki
)