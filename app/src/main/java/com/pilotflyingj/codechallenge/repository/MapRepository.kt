package com.pilotflyingj.codechallenge.repository

import com.pilotflyingj.codechallenge.network.LocationService
import com.pilotflyingj.codechallenge.repository.models.Site
import javax.inject.Inject

class MapRepository @Inject constructor(
    private val locationService: LocationService
) {
    // TODO: pull the service in, run any business logic through here so that the view model is
    // simply requesting data at this point
    suspend fun getLocations(): List<Site> {
        val response = locationService.getLocations()
        return if (response.isSuccessful) {
            response.body()?.map { location ->
                Site.create(location)
            } ?: emptyList()
        } else {
            emptyList()
        }
    }
}
