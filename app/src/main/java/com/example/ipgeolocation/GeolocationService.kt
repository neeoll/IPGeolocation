package com.example.ipgeolocation

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeolocationService {
    @GET("country,city?")
    fun getLocationData(@Query("apiKey") key: String, @Query("ipAddress") ip: String): Call<GeolocationResponse>
}