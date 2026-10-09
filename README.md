# TransportBookingQA

Project Android Studio Tugas Praktikum Pemrograman Mobile Dasar - Pertemuan 13
Topik: **PENGUJIAN APLIKASI MOBILE & PENJAMINAN MUTU (QA)**

---

## 1. Identitas Project

* **Nama Project**: TransportBookingQA
* **Package Name**: `com.industri.transportqa`
* **Bahasa**: Kotlin
* **UI**: Android Native XML Views (Empty Views Activity)
* **Gradle**: Gradle Kotlin DSL (`build.gradle.kts`) dengan Version Catalog (`libs.versions.toml`)
* **JDK**: Java 17 / 21

---

## 2. Struktur Direktori Project

```
TransportBookingQA/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── local.properties
├── gradlew
├── gradlew.bat
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
└── app/
    ├── build.gradle.kts
    └── src/
        ├── main/
        │   ├── AndroidManifest.xml
        │   ├── java/com/industri/transportqa/
        │   │   ├── MainActivity.kt
        │   │   ├── FareCalculator.kt
        │   │   ├── FlightApiService.kt
        │   │   ├── TicketEntity.kt
        │   │   ├── TicketDao.kt
        │   │   └── TestAppDatabase.kt
        │   └── res/
        │       ├── layout/
        │       │   └── activity_main.xml
        │       └── values/
        │           ├── colors.xml
        │           ├── strings.xml
        │           └── themes.xml
        ├── test/
        │   └── java/com/industri/transportqa/
        │       ├── FareCalculatorTest.kt
        │       └── MockWebServerTest.kt
        └── androidTest/
            └── java/com/industri/transportqa/
                ├── TicketDaoTest.kt
                └── BookingActivityEspressoTest.kt
```

---

## 3. Komponen dan Pengujian yang Diterapkan

### A. Logika Bisnis (`FareCalculator.kt`)
1. **`isValidNik(nik: String): Boolean`**
   * Tepat 16 digit angka. Kurang/lebih atau mengandung karakter selain angka menghasilkan `false`.
2. **`calculateTotalFare(baseFare: Double, passengerAge: Int, baggageWeightKg: Int): Double`**
   * Melempar `IllegalArgumentException("Tarif dasar tidak boleh minus")` jika tarif dasar < 0.
   * Melempar `IllegalArgumentException("Usia tidak valid")` jika usia < 0.
   * Balita (< 3 tahun): Diskon 100% (tarif dasar = Rp0).
   * Lansia (>= 60 tahun): Diskon 20%.
   * Dewasa (3–59 tahun): Tanpa diskon.
   * Bagasi gratis hingga 20 kg. Kelebihan dikenakan biaya Rp25.000 per kg.

### B. Unit Testing (`FareCalculatorTest.kt`)
* Framework: **JUnit 4** & **Google Truth**.
* Skenario Pengujian:
  1. NIK 16 digit valid -> `isTrue()`.
  2. NIK < 16 digit -> `isFalse()`.
  3. NIK mengandung huruf -> `isFalse()`.
  4. Diskon lansia 20% (Rp500.000, 65 tahun, 15 kg -> Rp400.000).
  5. Kelebihan bagasi (Rp500.000, 30 tahun, 25 kg -> Rp625.000).
  6. **Tugas Mandiri 1A**: Verifikasi exception tarif negatif (-10000.0) melempar `IllegalArgumentException("Tarif dasar tidak boleh minus")`.
  7. **Tugas Mandiri 1B**: Verifikasi tarif balita (500000.0, 2 tahun, 15 kg -> Rp0.0).

### C. MockWebServer Integration Test (`MockWebServerTest.kt`)
* Framework: **MockWebServer**, **Retrofit**, **Gson**, **Coroutines**, **Google Truth**.
* Server simulasi lokal dijalankan pada `@Before` dan ditutup pada `@After`.
* Menguji endpoint `GET api/v1/flights/availabilities` tanpa memerlukan internet.
* Memverifikasi ukuran daftar penerbangan (2 rute) dan rute pertama mengandung `"CGK - SUB"`.

### D. In-Memory Room Database Integration Test (`TicketDaoTest.kt`)
* Framework: **Room Database** (`Room.inMemoryDatabaseBuilder`), **Truth**, **AndroidJUnit4**.
* Entitas `tickets` (`TicketEntity`), DAO (`TicketDao`), Database (`TestAppDatabase`).
* Pengujian instrumented: insert tiket `TKT-8801` ("Budi Santoso", "Surabaya Gubeng"), query kembali berdasarkan kode, dan validasi data.

### E. Espresso UI Automation Test (`BookingActivityEspressoTest.kt`)
* Framework: **AndroidX Espresso**, **ActivityScenarioRule**, **AndroidJUnit4**.
* Form interaktif pada `MainActivity` (`etNik`, `etName`, `btnSubmitBooking`, `tvBookingStatus`).
* **Skenario A (Happy Path)**: Mengisi NIK valid dan nama -> klik tombol -> verifikasi status tampil `"Pemesanan Tiket Berhasil Diverifikasi"`.
* **Skenario B (Tugas Mandiri - Negative Flow)**: Form NIK kosong -> klik tombol -> verifikasi error `"NIK tidak boleh kosong"` tampil dan transaksi tidak diproses.

---

## 4. Cara Menjalankan di Android Studio

1. Buka Android Studio.
2. Pilih **File -> Open...**, lalu arahkan ke folder:
   `TransportBookingQA`
3. Tunggu hingga proses **Gradle Sync** selesai.
4. **Menjalankan Unit Test (Lokal/Offline JVM)**:
   * Buka folder `app/src/test/java/com/industri/transportqa/`.
   * Klik kanan pada `FareCalculatorTest.kt` atau `MockWebServerTest.kt`, pilih **Run '...Test'**.
   * Atau jalankan via terminal:
     ```bash
     .\gradlew testDebugUnitTest
     ```
5. **Menjalankan Instrumented Test (Database & Espresso UI Test)**:
   * Hubungkan perangkat fisik (USB Debugging aktif) atau nyalakan Emulator Android Studio.
   * Buka folder `app/src/androidTest/java/com/industri/transportqa/`.
   * Klik kanan pada `TicketDaoTest.kt` atau `BookingActivityEspressoTest.kt`, pilih **Run '...Test'**.
   * Atau jalankan via terminal:
     ```bash
     .\gradlew connectedDebugAndroidTest
     ```
6. **Menjalankan Aplikasi Utama**:
   * Pilih konfigurasi run `app` dan klik icon **Run** (segitiga hijau) pada toolbar.
