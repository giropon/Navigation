package com.example.navigation.ui.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import org.osmdroid.views.overlay.mylocation.SimpleLocationOverlay

@Composable
fun OpenStreetMapView(
    modifier: Modifier = Modifier,
    onMapReady: (MapView) -> Unit = {},
    initialLocation: GeoPoint = GeoPoint(35.6812, 139.7671), // 東京駅の座標
    initialZoom: Double = 15.0
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val mapView = remember {
        MapView(context).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            controller.setZoom(initialZoom)
            controller.setCenter(initialLocation)
            
            // パフォーマンス最適化
            setLayerType(android.view.View.LAYER_TYPE_HARDWARE, null)
            
            // タイルキャッシュの設定
            Configuration.getInstance().tileFileSystemCacheMaxBytes = 1024L * 1024L * 50L // 50MB
            Configuration.getInstance().tileFileSystemThreads = 8
            Configuration.getInstance().tileDownloadThreads = 8
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    AndroidView(
        factory = { mapView },
        modifier = modifier,
        update = { view ->
            onMapReady(view)
        }
    )
}

fun MapView.addMarker(location: GeoPoint, title: String? = null) {
    val marker = Marker(this).apply {
        position = location
        title?.let { setTitle(it) }
    }
    overlays.add(marker)
    invalidate()
}

fun MapView.drawRoute(points: List<GeoPoint>) {
    val polyline = Polyline().apply {
        setPoints(points)
        outlinePaint.color = android.graphics.Color.BLUE
        outlinePaint.strokeWidth = 5f
    }
    overlays.add(polyline)
    invalidate()
} 