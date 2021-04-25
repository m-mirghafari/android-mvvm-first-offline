package com.cafe_bazaar.venue.ui.venuesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cafe_bazaar.venue.databinding.FragmentVenuesBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VenuesFragment : Fragment() {

    private val viewModel: VenuesFragmentVM by viewModels()
    private lateinit var binding: FragmentVenuesBinding
    private var initializeRootView: Boolean = true


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
        }
    }
}