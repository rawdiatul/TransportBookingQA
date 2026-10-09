package com.industri.transportqa

import retrofit2.http.GET

interface FlightApiService {
    @GET("api/v1/flights/availabilities")
    suspend fun getFlightAvailabilities(): List<String>
}
