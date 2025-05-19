package com.example.navigation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.navigation.ui.components.OpenStreetMapView
import com.example.navigation.ui.components.addMarker
import com.example.navigation.ui.components.drawRoute
import com.example.navigation.ui.theme.NavigationTheme
import com.example.navigation.utils.LocationUtils
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

class MainActivity : ComponentActivity() {
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            // 位置情報の取得を開始
        } else if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
            // 粗い位置情報の取得を開始
        } else {
            // 権限が拒否された場合の処理
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // OpenStreetMapの設定
        Configuration.getInstance().userAgentValue = packageName

        // 位置情報の権限をリクエスト
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        setContent {
            NavigationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationScreen()
                }
            }
        }
    }
}

@Composable
fun NavigationScreen() {
    val context = LocalContext.current
    val locationUtils = remember { LocationUtils(context) }
    var currentLocation by remember { mutableStateOf<GeoPoint?>(null) }
    var destinationLocation by remember { mutableStateOf<GeoPoint?>(null) }
    var mapView by remember { mutableStateOf<MapView?>(null) }

    LaunchedEffect(Unit) {
        currentLocation = locationUtils.getCurrentLocation()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        OpenStreetMapView(
            modifier = Modifier.fillMaxSize(),
            onMapReady = { view ->
                mapView = view
                currentLocation?.let { location ->
                    view.controller.setCenter(location)
                    view.addMarker(location, "現在地")
                }
            }
        )

        // 画面中央の十字マーカー
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.foundation.Image(
                painter = painterResource(id = R.drawable.crosshair),
                contentDescription = "目的地設定位置",
                modifier = Modifier.size(48.dp)
            )
        }

        // ボタンを画面の上部に配置
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 目的地設定ボタン（左上）
            Button(
                onClick = {
                    // 過去の目的地とルートを削除
                    mapView?.overlays?.clear()
                    // 現在地のマーカーを再表示
                    currentLocation?.let { location ->
                        mapView?.addMarker(location, "現在地")
                    }
                    
                    // 新しい目的地を設定
                    val center = mapView?.mapCenter as? GeoPoint
                    center?.let {
                        destinationLocation = it
                        mapView?.addMarker(it, "目的地")
                        // ルートを描画（実際のルーティングAPIを使用する必要があります）
                        currentLocation?.let { start ->
                            mapView?.drawRoute(listOf(start, it))
                        }
                    }
                }
            ) {
                Text("目的地設定")
            }

            // 現在地ボタン（右上）
            Button(
                onClick = {
                    // 現在地に戻る
                    currentLocation?.let { location ->
                        mapView?.controller?.setCenter(location)
                    }
                }
            ) {
                Text("現在地")
            }
        }
    }
}