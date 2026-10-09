package com.industri.transportqa

import com.google.common.truth.Truth.assertThat
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class FareCalculatorTest {

    private lateinit var fareCalculator: FareCalculator

    @Before
    fun setUp() {
        fareCalculator = FareCalculator()
    }

    // --- Pengujian Validasi NIK ---

    @Test
    fun isValidNik_with16Digits_returnsTrue() {
        // NIK dengan 16 digit angka harus valid
        val validNik = "3209123456780001"
        val result = fareCalculator.isValidNik(validNik)
        assertThat(result).isTrue()
    }

    @Test
    fun isValidNik_withLessThan16Digits_returnsFalse() {
        // NIK kurang dari 16 digit harus tidak valid
        val shortNik = "123456789"
        val result = fareCalculator.isValidNik(shortNik)
        assertThat(result).isFalse()
    }

    @Test
    fun isValidNik_containingLetters_returnsFalse() {
        // NIK yang mengandung huruf harus tidak valid
        val nikWithLetters = "320912345678000A"
        val result = fareCalculator.isValidNik(nikWithLetters)
        assertThat(result).isFalse()
    }

    // --- Pengujian Perhitungan Tarif ---

    @Test
    fun calculateTotalFare_seniorPassenger_receives20PercentDiscount() {
        // Penumpang lansia (>= 60 tahun) mendapatkan diskon 20%
        // Tarif Rp500.000, usia 65 tahun, bagasi 15 kg = Rp400.000
        val baseFare = 500000.0
        val passengerAge = 65
        val baggageWeightKg = 15

        val totalFare = fareCalculator.calculateTotalFare(baseFare, passengerAge, baggageWeightKg)
        assertThat(totalFare).isEqualTo(400000.0)
    }

    @Test
    fun calculateTotalFare_baggage25Kg_incursCorrectExcessFee() {
        // Bagasi 25 kg melebihi kuota 20 kg sebanyak 5 kg -> 5 * 25.000 = Rp125.000
        // Tarif Rp500.000, usia 30 tahun, bagasi 25 kg = Rp625.000
        val baseFare = 500000.0
        val passengerAge = 30
        val baggageWeightKg = 25

        val totalFare = fareCalculator.calculateTotalFare(baseFare, passengerAge, baggageWeightKg)
        assertThat(totalFare).isEqualTo(625000.0)
    }

    // --- Tugas Mandiri 1A: Tarif Negatif melempar IllegalArgumentException ---

    @Test
    fun calculateTotalFare_negativeBaseFare_throwsIllegalArgumentException() {
        // Tugas 1A: Panggil calculateTotalFare dengan baseFare = -10000.0
        // Pastikan fungsi melempar IllegalArgumentException dengan pesan yang tepat
        val exception = assertThrows(IllegalArgumentException::class.java) {
            fareCalculator.calculateTotalFare(
                baseFare = -10000.0,
                passengerAge = 25,
                baggageWeightKg = 10
            )
        }
        assertThat(exception).hasMessageThat().isEqualTo("Tarif dasar tidak boleh minus")
    }

    // --- Tugas Mandiri 1B: Tarif Balita (Diskon 100%) ---

    @Test
    fun calculateTotalFare_toddlerPassenger_returnsZeroFare() {
        // Tugas 1B: Panggil calculateTotalFare dengan baseFare = 500000.0, passengerAge = 2, baggageWeightKg = 15
        // Penumpang berusia di bawah 3 tahun mendapatkan diskon 100% (Rp0)
        val baseFare = 500000.0
        val passengerAge = 2
        val baggageWeightKg = 15

        val totalFare = fareCalculator.calculateTotalFare(baseFare, passengerAge, baggageWeightKg)
        assertThat(totalFare).isEqualTo(0.0)
    }
}
