package com.pilotflyingj.codechallenge.network.models

import kotlinx.serialization.Serializable

@Serializable
data class SpaceAvailability(
    val Available: Int,
    val Booked: Int,
    val Description: String,
    val ItemID: Int,
    val LocationID: Int,
    val Price: Int,
    val Total: Int
)
