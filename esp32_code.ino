#include <WiFi.h>
#include <FirebaseESP32.h>
#include <Adafruit_MPU6050.h>
#include <Adafruit_Sensor.h>
#include <Wire.h>
#include <TinyGPSPlus.h>

// --- HIDDEN CONFIGURATION (Replace with your details locally) ---
#define WIFI_SSID "YOUR_WIFI_NAME"
#define WIFI_PASSWORD "YOUR_WIFI_PASSWORD"
#define FIREBASE_HOST "YOUR_PROJECT_ID-default-rtdb.firebasedatabase.app"
#define FIREBASE_AUTH "YOUR_FIREBASE_SECRET_KEY"
#define EMERGENCY_PHONE "YOUR_EMERGENCY_NUMBER" // Example: "919999999999"

// --- PINS ---
#define SIM800_RX 16
#define SIM800_TX 17
#define GPS_RX_PIN 4
#define GPS_TX_PIN 5

// --- GPS STORED VALUES ---
const float storedLat = 28.449076;
const float storedLng = 77.583407;

// --- OBJECTS ---
Adafruit_MPU6050 mpu;
FirebaseData firebaseData;
FirebaseAuth auth;
FirebaseConfig config;
TinyGPSPlus gps;

void sendSMS(String message) {
  Serial.println("📱 Sending Emergency SMS...");
  Serial2.println("AT+CMGF=1"); 
  delay(1000);
  
  // Using the Hidden Phone Number variable
  Serial2.print("AT+CMGS=\"");
  Serial2.print(EMERGENCY_PHONE);
  Serial2.println("\"");
  
  delay(1000);
  Serial2.print(message);
  delay(500);
  Serial2.write(26); // CTRL+Z to confirm SMS
  delay(5000);
  Serial.println("✅ SMS Sent!");
}

void setup() {
  Serial.begin(115200);
  
  // Serial2 for SIM800L, Serial1 for GPS
  Serial2.begin(9600, SERIAL_8N1, SIM800_RX, SIM800_TX); 
  Serial1.begin(9600, SERIAL_8N1, GPS_RX_PIN, GPS_TX_PIN); 
  Wire.begin(21, 22); // I2C for MPU6050

  // 1. WiFi Connection
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to WiFi");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println("\n✅ WiFi Connected!");

  // 2. Firebase Configuration
  config.host = FIREBASE_HOST;
  config.signer.tokens.legacy_token = FIREBASE_AUTH;
  Firebase.begin(&config, &auth);
  Firebase.reconnectWiFi(true);

  // 3. MPU6050 Initialization
  if (!mpu.begin()) {
    Serial.println("❌ MPU6050 Error! Check Wiring.");
    while (1);
  }

  // 4. SIM800L Initialization
  Serial2.println("AT"); 
  delay(500);
  Serial2.println("AT+CMGF=1");
  delay(1000);

  Serial.println("--- TRINETRA SYSTEM ONLINE ---");
}

void loop() {
  sensors_event_t a, g, temp;
  mpu.getEvent(&a, &g, &temp);

  // Parse GPS data in background
  while (Serial1.available()) {
    gps.encode(Serial1.read());
  }

  // CRASH DETECTION LOGIC
  // Threshold values for Impact Detection
  if (abs(a.acceleration.x) > 30 || abs(a.acceleration.y) > 22 || abs(a.acceleration.z) < 22) {
    Serial.println("\n⚠️ [CRASH DETECTED] !!!");
    
    String medicalReport = "Data Fetching...";
    
    // FETCH EMERGENCY DATA FROM FIREBASE
    if (Firebase.ready()) {
      // Path based on your database structure
      if (Firebase.getJSON(firebaseData, "/Users")) {
        medicalReport = firebaseData.jsonString();
        Serial.println("✅ Firebase Data Fetched!");
      } else {
        Serial.println("❌ Firebase Error: " + firebaseData.errorReason());
      }
    }

    // PREPARE SMS MESSAGE WITH STORED GPS LOCATION
    String mapsLink = "https://www.google.com/maps?q=" + String(storedLat, 6) + "," + String(storedLng, 6);
    
    String finalSMS = "ATTENTION: Accident Detected!\n";
    finalSMS += "Location: " + mapsLink + "\n";
    finalSMS += "User Medical Details: " + medicalReport;

    // TRIGGER SMS
    sendSMS(finalSMS);

    Serial.println("--- EMERGENCY REPORT SENT ---");
    delay(30000); //
