package com.cafe_bazaar.venue.ui.venuesList

import android.Manifest
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cafe_bazaar.venue.databinding.FragmentVenuesBinding
import com.cafe_bazaar.venue.utils.Extensions.Companion.navigateSafe
import com.cafe_bazaar.venue.utils.LocationUtils
import com.cafe_bazaar.venue.utils.LocationUtilsListener
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class VenuesFragment : Fragment() {

    @Inject
    lateinit var locationUtils: LocationUtils

    private val viewModel: VenuesFragmentVM by viewModels()
    private var initializeRootView: Boolean = true
    private lateinit var binding: FragmentVenuesBinding
    private lateinit var adapter: VenueAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (::binding.isInitialized) {
            // Do not inflate the layout again.
            (binding.root.parent as? ViewGroup)?.removeView(binding.root)

        } else {
            // Inflate the fragment's layout
            binding = FragmentVenuesBinding.inflate(inflater, container, false)
            binding.lifecycleOwner = this
            binding.viewModel = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (initializeRootView) {
            initializeRootView = false
            configAdapter()
        }
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        checkLocationPermission()
    }

    private fun initObservers() {
        viewModel.venues.observe(viewLifecycleOwner, Observer {
            it?.let {
                CoroutineScope(Main).launch {
                    adapter.addItems(it.getContentIfNotHandled() ?: arrayListOf())
                }
            }
        })

        viewModel.showMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.hasBeenHandled) {
                    Toast.makeText(requireContext(), it.getContentIfNotHandled(), Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun configAdapter() {
        adapter = VenueAdapter(onItemClickListener = { item, position ->
            showVenueDetails(item.venue.id)
        })
        binding.venuesRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )
        binding.venuesRecyclerView.adapter = adapter
        binding.venuesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (viewModel.showLoading.value == true)
                    return

                val visibleItemCount: Int = (binding.venuesRecyclerView.layoutManager as LinearLayoutManager).childCount
                val totalItemCount: Int = (binding.venuesRecyclerView.layoutManager as LinearLayoutManager).itemCount
                val pastVisibleItems: Int = (binding.venuesRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    viewModel.getVenues()
                }
            }
        })
    }


    private fun showVenueDetails(venueId: String) {
        val action = VenuesFragmentDirections.actionVenuesFragmentToDetailsFragment(venueId)
        findNavController().navigateSafe(action)
    }

    private fun checkLocationPermission() {
        Permissions.check(activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            null,
            null,
            object : PermissionHandler() {
                override fun onGranted() {
                    letsGetUserLocation()
                }

                override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
                    Toast.makeText(requireContext(), "we need location permission to get venues around your location!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        )
    }

    private fun letsGetUserLocation() {
        if (locationUtils.isGpsEnables(requireActivity())) {
            locationUtils.getCurrentLocation(activity = requireActivity(),
                getLocationUpdates = true,
                listener = object : LocationUtilsListener {
                    override fun onLocationChanged(location: Location) {
                        onUserLocationUpdated(location)
                    }

                    override fun onProviderDisabled(provider: String) {
                        locationUtils.stopUpdateLocation()
                    }
                })

        } else {
            locationUtils.requestToTurnOnGps(requireActivity())
        }
    }

    private fun onUserLocationUpdated(location: Location) {
        val lastLocation = viewModel.currentLocation
        Toast.makeText(requireContext(), "new location detected", Toast.LENGTH_SHORT).show()
        Toast.makeText(requireContext(), "distance is ${lastLocation?.distanceTo(location)}", Toast.LENGTH_LONG).show()
        if (lastLocation != null && lastLocation.distanceTo(location) < 100.0f)
            return
        viewModel.currentLocation = location
        viewModel.getVenues()
    }

}