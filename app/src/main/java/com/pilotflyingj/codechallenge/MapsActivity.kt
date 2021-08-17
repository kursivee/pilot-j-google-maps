package com.pilotflyingj.codechallenge

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.pilotflyingj.codechallenge.viewmodel.MapsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LifecycleOwner {

    companion object {
        val US_LAT_LNG = LatLng(39.5, -98.35)
        const val MIN_ZOOM = 0f
        const val MAX_ZOOM = 19f
        const val DEFAULT_ZOOM = 3f
    }

    private val viewModel: MapsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        (supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment)?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        with(googleMap) {
            viewModel.locations.observe(this@MapsActivity) { sites ->
                sites.forEach { site ->
                    val marker = MarkerOptions()
                        .position(site.location)
                        .title(site.name)
                    googleMap.addMarker(marker)
                }
            }
            moveCamera(CameraUpdateFactory.newLatLngZoom(US_LAT_LNG, DEFAULT_ZOOM))
            setMinZoomPreference(MIN_ZOOM)
            setMaxZoomPreference(MAX_ZOOM)
        }
    }
}
