package com.example.navigation.utils

import android.util.Log
import com.example.navigation.api.OpenRouteServiceClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Polyline

private const val TAG = "RouteUtils"

suspend fun getWalkingRoute(start: GeoPoint, end: GeoPoint): List<GeoPoint> {
    return withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Getting route from $start to $end")
            val response = OpenRouteServiceClient.service.getWalkingRoute(
                apiKey = OpenRouteServiceClient.getApiKey(),
                start = "${start.longitude},${start.latitude}",
                end = "${end.longitude},${end.latitude}"
            )

            val points = response.features.firstOrNull()?.geometry?.coordinates?.map { coord ->
                GeoPoint(coord[1], coord[0]) // OpenRouteServiceは[longitude, latitude]の順で返す
            } ?: emptyList()

            Log.d(TAG, "Route points count: ${points.size}")
            points
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get route", e)
            emptyList()
        }
    }
}

fun createRoutePolyline(points: List<GeoPoint>): Polyline {
    Log.d(TAG, "Creating polyline with ${points.size} points")
    return Polyline().apply {
        setPoints(points)
        outlinePaint.color = android.graphics.Color.BLUE
        outlinePaint.strokeWidth = 5f
    }
} 