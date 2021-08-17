package com.pilotflyingj.codechallenge.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pilotflyingj.codechallenge.repository.MapRepository
import com.pilotflyingj.codechallenge.repository.models.Site
import kotlinx.coroutines.launch

class MapsViewModel @ViewModelInject constructor(
    private val mapRepository: MapRepository
) : ViewModel() {

    private val _locations = MutableLiveData<List<Site>>()
    val locations: LiveData<List<Site>> = _locations

    init {
        viewModelScope.launch {
            _locations.value = mapRepository.getLocations()
        }
    }
}
