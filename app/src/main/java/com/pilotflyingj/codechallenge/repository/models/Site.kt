package com.pilotflyingj.codechallenge.repository.models

import com.google.android.gms.maps.model.LatLng
import com.pilotflyingj.codechallenge.network.models.Location

data class Site(
    val name: String,
    val location: LatLng
) {
    companion object {
        fun create(location: Location): Site {
            return Site(
                name = location.StoreName,
                location = LatLng(location.Latitude, location.Longitude)
            )
        }
    }
}
