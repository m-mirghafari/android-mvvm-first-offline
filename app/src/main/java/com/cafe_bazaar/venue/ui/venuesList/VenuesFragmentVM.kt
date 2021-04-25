package com.cafe_bazaar.venue.ui.venuesList

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafe_bazaar.venue.app.Constants
import com.cafe_bazaar.venue.data.models.ApiResponse
import com.cafe_bazaar.venue.data.models.DataState
import com.cafe_bazaar.venue.data.models.venue.GetVenueListRes
import com.cafe_bazaar.venue.data.models.venue.Items
import com.cafe_bazaar.venue.data.repository.VenueRepository
import com.cafe_bazaar.venue.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VenuesFragmentVM @Inject constructor(private val repo: VenueRepository) : ViewModel() {

    val showLoading = MutableLiveData(true)
    val showMessage = MutableLiveData<Event<String>>()
    var currentLocation: Location? = null

    private var currentPage: Int = 0
    private var canLoadMoreItems = true

    private val _venues = MutableLiveData<Event<ArrayList<Items>>>()
    val venues: LiveData<Event<ArrayList<Items>>> = _venues

    fun getVenues() {
        if (currentLocation == null || !canLoadMoreItems) return

        viewModelScope.launch {
            repo.getPlaces(
                offset = currentPage * Constants.VENUES_LIST_LIMIT,
                latitude = currentLocation?.latitude ?: 0.0,
                longitude = currentLocation?.longitude ?: 0.0
            ).onEach { res ->
                when (res) {
                    is DataState.Result<ApiResponse<GetVenueListRes>> -> {
                        if (res.result.success) {
                            val newItems = res.result.data?.groups?.first()?.items
                            if (newItems?.count() ?: 0 < Constants.VENUES_LIST_LIMIT) {
                                canLoadMoreItems = false
                            }

                            if (!newItems.isNullOrEmpty()) {
                                _venues.value = Event(newItems)
                            }

                        } else {
                            showMessage.value = Event(res.result.message)
                        }

                        showLoading.value = false
                        currentPage++
                    }
                    DataState.Loading -> {
                        showLoading.value = true
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}