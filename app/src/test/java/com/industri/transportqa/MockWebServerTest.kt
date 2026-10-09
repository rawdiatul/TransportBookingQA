package com.industri.transportqa

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MockWebServerTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: FlightApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlightApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getFlightAvailabilities_returnsTwoRoutesAndFirstContainsCgkSub() = runBlocking {
        // 1. Memasukkan respons HTTP 200 ke MockWebServer
        val mockJson = """
            [
              "GA-102 (CGK - SUB)",
              "GA-204 (CGK - DPS)"
            ]
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockJson)
        )

        // 2. Memanggil endpoint melalui Retrofit
        val response = apiService.getFlightAvailabilities()

        // 3. Memastikan respons menghasilkan dua rute penerbangan
        assertThat(response).hasSize(2)

        // 4. Memastikan elemen pertama berisi "CGK - SUB"
        assertThat(response[0]).contains("CGK - SUB")
    }
}
