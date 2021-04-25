package com.cafe_bazaar.venue.ui.venueDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafe_bazaar.venue.data.models.ApiResponse
import com.cafe_bazaar.venue.data.models.DataState
import com.cafe_bazaar.venue.data.models.venue.GetVenueDetailsRes
import com.cafe_bazaar.venue.data.models.venue.Venue
import com.cafe_bazaar.venue.data.repository.VenueRepository
import com.cafe_bazaar.venue.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsFragmentVM @Inject constructor(private val repo: VenueRepository) : ViewModel() {

    val showLoading = MutableLiveData(true)
    val showMessage = MutableLiveData<Event<String>>()

    private val _venue = MutableLiveData<Venue?>()
    val venue: LiveData<Venue?> = _venue

    fun getVenueDetails(venueId: String) {
        viewModelScope.launch {
            repo.getVenueDetails(venueId = venueId).onEach { res ->
                when (res) {
                    is DataState.Result<ApiResponse<GetVenueDetailsRes>> -> {
                        if (res.result.success) _venue.value = res.result.data?.venue
                        else showMessage.value = Event(res.result.message)
                        showLoading.value = false
                    }
                    DataState.Loading -> {
                        showLoading.value = true
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}