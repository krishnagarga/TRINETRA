# 🚀 TRINETRA – Smart Accident Detection & Response System

TRINETRA is an IoT-based smart safety system designed to reduce fatalities caused by delayed medical assistance after road accidents. It automatically detects accidents and instantly notifies nearby authorities with critical user data, enabling faster response and treatment.

---

## 📌 Problem Statement

Many accident victims lose their lives not due to injuries, but because help does not arrive on time.

TRINETRA addresses this by:
- Detecting accidents in real-time  
- Automatically triggering emergency alerts  
- Providing medical and personal data instantly  

---

## 💡 Solution Overview

TRINETRA integrates Mobile Application, IoT hardware, and Cloud (Firebase) to create a real-time accident response system.

### 🔄 Workflow

1. User enters personal and medical details in the mobile app  
2. Sensors detect abnormal motion / accident  
3. ESP32 processes sensor data  
4. SIM800L sends emergency alert  
5. Firebase syncs user data  
6. Authorities receive information instantly  

---

## 🧠 My Contribution

**Krishna Garg (Project Lead & App Developer)**  
- Idea conceptualization  
- Mobile application development (UI/UX + Firebase integration)  
- System architecture design  
- Project planning and team leadership  

---

## 🧩 Team Contribution

- **Shreyansh Prasad (Backend Developer)**  
  Firebase Integration, Realtime Database, Data Management  

- **Pritish Pant (Embedded Systems Developer)**  
  Arduino Programming, Hardware–App Integration  

- **Nimisha Sonare (Hardware & Documentation Lead)**  
  Hardware Setup, GitHub & Documentation  

---

## 📱 Mobile Application Setup

### Requirements
- Android Studio  
- Java / XML  
- Firebase Account  

### Steps to Run

1. Clone the repository:
   ```bash
   git clone <your-repo-link>

2. Open the project in Android Studio  

3. Connect Firebase:
   - Add `google-services.json`  
   - Enable Realtime Database  

4. Build and run the application  

---

## 📦 APK Installation

APK file is included in this repository.

### Steps

- Download the APK file  
- Transfer it to your Android device  

- Enable installation from unknown sources:
  - Settings → Security  
  - Enable "Install Unknown Apps" / "Allow from this source"  

- Open the APK file  
- Tap Install  
- Launch the application  

---

## 🔌 Hardware Components

- ESP32  
- MPU6050 (Accelerometer + Gyroscope)  
- Neo-6M GPS Module  
- SIM800L GSM Module  
- Buzzer Module  

---

## ⚙️ Arduino IDE Setup

### Requirements

- Arduino IDE  
- ESP32 Board Package  

### Required Libraries

- WiFi.h  
- Firebase ESP Client (by Mobizt)  
- TinyGPS++  
- SoftwareSerial  
- Wire  

### Steps to Run ESP32 Code

- Install ESP32 board in Arduino IDE  
- Select the correct COM port  
- Install required libraries  

- Update credentials:
  - WiFi SSID and Password  
  - Firebase API Key and Database URL  

- Upload the code to ESP32  

---

## 📡 Firebase Setup

- Create a Firebase project  
- Enable Realtime Database  

- Set rules (for testing):

- Set rules (for testing):

json
{
  "rules": {
    ".read": true,
    ".write": true
  }
}

## 🚀 Features

- Real-time accident detection  
- Automatic emergency alert system  
- Medical data accessibility  
- IoT and Mobile integration  
- Scalable system design  

---

## ⚠️ Note

This project is a prototype inspired by modern vehicle safety systems. It demonstrates a scalable approach to improving emergency response using technology.

---

## ⭐ Support

If you find this project useful, consider giving it a star.

---

## 🤝 Acknowledgement

Thanks to the team for their collaboration and contributions in building this project.



   
