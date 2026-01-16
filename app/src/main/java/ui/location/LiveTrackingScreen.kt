package com.example.trekkingbuddy.ui.location

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.*
import com.google.maps.android.compose.*
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun LiveTrackingScreen() {

    // 📍 Trek Path
    var trekPath by remember { mutableStateOf<List<TrekLocationPoint>>(emptyList()) }

    // 📏 Distance (meters)
    var totalDistance by remember { mutableStateOf(0f) }

    // ⏱ Time
    var startTime by remember { mutableStateOf<Long?>(null) }
    var duration by remember { mutableStateOf(0L) } // milliseconds

    // 📍 Location state
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }
    var isTracking by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val fusedLocationClient =
        remember { LocationServices.getFusedLocationProviderClient(context) }

    val cameraPositionState = rememberCameraPositionState()


    // 🔁 Location request
    val locationRequest = remember {
        LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5000L // 5 seconds
        ).build()
    }

    // 📡 Location callback
    val locationCallback = remember {
        object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.lastLocation ?: return

                latitude = location.latitude
                longitude = location.longitude

                // 📏 Distance calculation
                if (trekPath.isNotEmpty()) {
                    val last = trekPath.last()
                    totalDistance += calculateDistance(
                        last.latitude,
                        last.longitude,
                        location.latitude,
                        location.longitude
                    )
                }

                // 📍 Save path point
                trekPath = trekPath + TrekLocationPoint(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    timestamp = System.currentTimeMillis()
                )

                // ⏱ Live duration update
                startTime?.let {
                    duration = System.currentTimeMillis() - it
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Live GPS Tracking",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = true
            ),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = true
            )
        ) {

            // 📍 Draw live route
            if (trekPath.size > 1) {
                Polyline(
                    points = trekPath.map {
                        LatLng(it.latitude, it.longitude)
                    },
                    color = androidx.compose.ui.graphics.Color.Red,
                    width = 8f
                )
            }

            // 🎯 Move camera to latest position
            trekPath.lastOrNull()?.let {
                LaunchedEffect(it) {
                    cameraPositionState.animate(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(it.latitude, it.longitude),
                            17f
                        )
                    )
                }
            }
        }


        // ▶ START TRACKING
        Button(
            onClick = {
                isTracking = true

                // Reset old data
                trekPath = emptyList()
                totalDistance = 0f
                duration = 0L

                // Save start time
                startTime = System.currentTimeMillis()

                // Start GPS updates
                startLocationUpdates(
                    fusedLocationClient,
                    locationRequest,
                    locationCallback
                )
            },
            enabled = !isTracking
        ) {
            Text("Start Tracking 📍")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ⛔ STOP TRACKING
        Button(
            onClick = {
                isTracking = false

                fusedLocationClient.removeLocationUpdates(locationCallback)

                // Final duration
                duration = System.currentTimeMillis() -
                        (startTime ?: System.currentTimeMillis())
            },
            enabled = isTracking
        ) {
            Text("Stop Tracking ⛔")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 📊 LIVE STATS
        if (latitude != null && longitude != null) {

            Text("Latitude: ${latitude}")
            Text("Longitude: ${longitude}")

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "📏 Distance: ${(totalDistance / 1000).format(2)} km",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "⏱ Duration: ${(duration / 60000)} minutes",
                style = MaterialTheme.typography.bodyLarge
            )

        } else {
            Text("Waiting for GPS signal...")
        }
    }
}

@SuppressLint("MissingPermission")
private fun startLocationUpdates(
    fusedLocationClient: FusedLocationProviderClient,
    locationRequest: LocationRequest,
    locationCallback: LocationCallback
) {
    fusedLocationClient.requestLocationUpdates(
        locationRequest,
        locationCallback,
        Looper.getMainLooper()
    )
}

// 📏 Distance calculation helper
fun calculateDistance(
    oldLat: Double,
    oldLng: Double,
    newLat: Double,
    newLng: Double
): Float {
    val result = FloatArray(1)
    Location.distanceBetween(
        oldLat, oldLng,
        newLat, newLng,
        result
    )
    return result[0] // meters
}

// 🔢 Format helper
fun Float.format(digits: Int): String {
    return "%.${digits}f".format(this)
}




