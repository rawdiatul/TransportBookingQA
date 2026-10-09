package com.industri.transportqa

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TicketDaoTest {

    private lateinit var db: TestAppDatabase
    private lateinit var ticketDao: TicketDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            TestAppDatabase::class.java
        ).allowMainThreadQueries().build()

        ticketDao = db.ticketDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetTicketByCode_returnsCorrectTicket() {
        // 1. Masukkan tiket dengan kode TKT-8801
        // 2. Nama penumpang Budi Santoso
        // 3. Tujuan Surabaya Gubeng
        val ticket = TicketEntity(
            ticketCode = "TKT-8801",
            passengerName = "Budi Santoso",
            destination = "Surabaya Gubeng"
        )
        ticketDao.insertTicket(ticket)

        // 4. Ambil kembali tiket menggunakan kode tersebut
        val loadedTicket = ticketDao.getTicketByCode("TKT-8801")

        // 5. Pastikan data tidak null
        assertThat(loadedTicket).isNotNull()

        // 6. Pastikan nama penumpang sama dengan "Budi Santoso"
        assertThat(loadedTicket?.passengerName).isEqualTo("Budi Santoso")
        assertThat(loadedTicket?.destination).isEqualTo("Surabaya Gubeng")
        assertThat(loadedTicket?.ticketCode).isEqualTo("TKT-8801")
    }
}
