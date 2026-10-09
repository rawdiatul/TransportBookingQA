package com.industri.transportqa

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookingActivityEspressoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    /**
     * Skenario A: Pemesanan Berhasil (Happy Path)
     * Mengisi NIK valid dan nama, lalu memastikan status verifikasi berhasil tampil.
     */
    @Test
    fun testSuccessfulBooking() {
        // 2. Isi etNik dengan "3209123456780001"
        onView(withId(R.id.etNik))
            .perform(typeText("3209123456780001"), closeSoftKeyboard())

        // 3. Isi etName dengan "Ahmad Dahlan"
        onView(withId(R.id.etName))
            .perform(typeText("Ahmad Dahlan"), closeSoftKeyboard())

        // 4. Klik btnSubmitBooking
        onView(withId(R.id.btnSubmitBooking))
            .perform(click())

        // 5. Verifikasi tvBookingStatus tampil
        onView(withId(R.id.tvBookingStatus))
            .check(matches(isDisplayed()))

        // 6. Verifikasi teksnya sama persis dengan "Pemesanan Tiket Berhasil Diverifikasi"
        onView(withId(R.id.tvBookingStatus))
            .check(matches(withText("Pemesanan Tiket Berhasil Diverifikasi")))
    }

    /**
     * Skenario B: Negative Flow UI Automation (Tugas Mandiri)
     * Mengosongkan NIK dan langsung menekan tombol pesan.
     * Memverifikasi pesan error "NIK tidak boleh kosong" muncul dan pesan sukses tidak muncul.
     */
    @Test
    fun testNegativeFlow_emptyNik_showsErrorAndNoSuccess() {
        // 1 & 2. Form dalam kondisi kosong (kolom etNik tidak diisi)
        // 3. Langsung klik btnSubmitBooking
        onView(withId(R.id.btnSubmitBooking))
            .perform(click())

        // 4. Verifikasi pesan error "NIK tidak boleh kosong" muncul di layar
        onView(withId(R.id.tvBookingStatus))
            .check(matches(isDisplayed()))
            .check(matches(withText("NIK tidak boleh kosong")))

        // 5. Pastikan transaksi tidak diproses dan pesan sukses tidak muncul
        onView(withId(R.id.tvBookingStatus))
            .check(matches(not(withText("Pemesanan Tiket Berhasil Diverifikasi"))))
    }
}
