package com.industri.transportqa

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val fareCalculator = FareCalculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNik = findViewById<EditText>(R.id.etNik)
        val etName = findViewById<EditText>(R.id.etName)
        val btnSubmitBooking = findViewById<Button>(R.id.btnSubmitBooking)
        val tvBookingStatus = findViewById<TextView>(R.id.tvBookingStatus)

        btnSubmitBooking.setOnClickListener {
            val nik = etNik.text.toString().trim()
            val name = etName.text.toString().trim()

            // 1 & 2. Validasi kolom NIK kosong
            if (nik.isEmpty()) {
                val errorMsg = "NIK tidak boleh kosong"
                etNik.error = errorMsg
                tvBookingStatus.text = errorMsg
                return@setOnClickListener
            }

            // 4. Validasi format NIK menggunakan FareCalculator
            if (!fareCalculator.isValidNik(nik)) {
                val errorMsg = "NIK harus terdiri dari 16 digit angka"
                etNik.error = errorMsg
                tvBookingStatus.text = errorMsg
                return@setOnClickListener
            }

            // Validasi nama penumpang
            if (name.isEmpty()) {
                val errorMsg = "Nama penumpang tidak boleh kosong"
                etName.error = errorMsg
                tvBookingStatus.text = errorMsg
                return@setOnClickListener
            }

            // 5. NIK valid dan nama terisi
            etNik.error = null
            etName.error = null
            tvBookingStatus.text = "Pemesanan Tiket Berhasil Diverifikasi"
        }
    }
}
