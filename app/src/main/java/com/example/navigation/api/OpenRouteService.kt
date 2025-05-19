package com.example.navigation.api

import android.content.Context
import android.util.Log
import com.example.navigation.R
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

data class RouteResponse(
    val features: List<Feature>
)

data class Feature(
    val properties: Properties,
    val geometry: Geometry
)

data class Properties(
    val segments: List<Segment>
)

data class Segment(
    val distance: Double,
    val duration: Double,
    val steps: List<Step>
)

data class Step(
    val distance: Double,
    val duration: Double,
    val type: Int,
    val instruction: String,
    val name: String,
    val wayPoints: List<Int>
)

data class Geometry(
    val coordinates: List<List<Double>>
)

interface OpenRouteService {
    @GET("v2/directions/foot-walking")
    suspend fun getWalkingRoute(
        @Header("Authorization") apiKey: String,
        @Query("start") start: String,
        @Query("end") end: String
    ): RouteResponse
}

object OpenRouteServiceClient {
    private const val BASE_URL = "https://api.openrouteservice.org/"
    private var apiKey: String? = null
    private const val TAG = "OpenRouteService"

    fun initialize(context: Context) {
        apiKey = context.getString(R.string.openroute_api_key)
        Log.d(TAG, "API Key initialized: ${apiKey?.take(5)}...")
    }

    private val retrofit = retrofit2.Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
        .client(
            okhttp3.OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request()
                    Log.d(TAG, "Request URL: ${request.url}")
                    Log.d(TAG, "Request Headers: ${request.headers}")
                    try {
                        val response = chain.proceed(request)
                        Log.d(TAG, "Response Code: ${response.code}")
                        response
                    } catch (e: Exception) {
                        Log.e(TAG, "Request failed", e)
                        throw e
                    }
                }
                .addInterceptor(okhttp3.logging.HttpLoggingInterceptor().apply {
                    level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .build()

    val service: OpenRouteService = retrofit.create(OpenRouteService::class.java)

    fun getApiKey(): String {
        return apiKey ?: throw IllegalStateException("OpenRouteServiceClient is not initialized. Call initialize() first.")
    }
} 