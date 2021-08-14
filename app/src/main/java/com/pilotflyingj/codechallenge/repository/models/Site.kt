package com.pilotflyingj.codechallenge.repository.models

import com.google.android.gms.maps.model.LatLng

data class Site(
    val name: String,
    val location: LatLng
)