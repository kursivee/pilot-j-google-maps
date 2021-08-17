package com.pilotflyingj.codechallenge.network.models

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val Address: String,
    val AddressTwo: String?,
    val Area: String?,
    val City: String,
    val Country: String,
    val Interstate: String,
    val Latitude: Double,
    val Longitude: Double,
    val Phone: String,
    val SpaceAvailability: List<SpaceAvailability>,
    val State: String,
    val StoreFrontBrand: String?,
    val StoreName: String,
    val StoreNo: Int,
    val StoreType: String?,
    val ZipCode: String
)
