package com.pilotflyingj.codechallenge.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.maps.model.LatLng
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pilotflyingj.codechallenge.network.LocationService
import com.pilotflyingj.codechallenge.repository.MapRepository
import com.pilotflyingj.codechallenge.repository.models.Site
import com.pilotflyingj.codechallenge.testutil.MainCoroutineRule
import com.pilotflyingj.codechallenge.testutil.enqueueResponse
import com.pilotflyingj.codechallenge.testutil.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit

@ExperimentalCoroutinesApi
@ExperimentalSerializationApi
class MapsViewModelTest {

    private val server = MockWebServer()
    private lateinit var mapRepository: MapRepository
    private lateinit var sut: MapsViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        mapRepository = MapRepository(
            createLocationService(
                server.url("/")
            )
        )
        sut = MapsViewModel(mapRepository)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `when initializing then load all the locations`() {
        server.enqueueResponse("sample_response.json")
        val site = Site(name = "Pilot Travel Center", location = LatLng(35.99996, -83.777618))
        runBlocking {
            assertEquals(listOf(site), sut.locations.getOrAwaitValue())
        }
    }

    @Test
    fun `when initializing and no locations then load no locations`() {
        server.enqueueResponse("empty_response.json")
        runBlocking {
            assertEquals(listOf<Site>(), sut.locations.getOrAwaitValue())
        }
    }

    @Test
    fun `when initializing and error from server then load no locations`() {
        server.enqueue(MockResponse().setResponseCode(500))
        runBlocking {
            assertEquals(listOf<Site>(), sut.locations.getOrAwaitValue())
        }
    }

    private fun createLocationService(baseUrl: HttpUrl): LocationService {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
            .create(LocationService::class.java)
    }
}
