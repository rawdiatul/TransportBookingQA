package com.industri.transportqa

class FareCalculator {

    /**
     * Memvalidasi Nomor Induk Kependudukan (NIK).
     * Syarat: Harus tepat 16 digit dan seluruhnya berupa angka.
     */
    fun isValidNik(nik: String): Boolean {
        if (nik.length != 16) {
            return false
        }
        return nik.all { it.isDigit() }
    }

    /**
     * Menghitung total tarif perjalanan berdasarkan tarif dasar, usia penumpang, dan berat bagasi.
     *
     * Aturan diskon usia:
     * - Usia < 3 tahun: diskon 100% (tarif dasar menjadi 0)
     * - Usia >= 60 tahun: diskon 20%
     * - Usia 3..59 tahun: tanpa diskon
     *
     * Aturan bagasi:
     * - Gratis hingga 20 kg
     * - Kelebihan bagasi dikenakan biaya Rp25.000 per kg
     */
    fun calculateTotalFare(
        baseFare: Double,
        passengerAge: Int,
        baggageWeightKg: Int
    ): Double {
        if (baseFare < 0.0) {
            throw IllegalArgumentException("Tarif dasar tidak boleh minus")
        }
        if (passengerAge < 0) {
            throw IllegalArgumentException("Usia tidak valid")
        }

        val fareAfterDiscount = when {
            passengerAge < 3 -> 0.0
            passengerAge >= 60 -> baseFare * 0.8
            else -> baseFare
        }

        val excessBaggageKg = if (baggageWeightKg > 20) baggageWeightKg - 20 else 0
        val excessBaggageFee = excessBaggageKg * 25000.0

        return fareAfterDiscount + excessBaggageFee
    }
}
