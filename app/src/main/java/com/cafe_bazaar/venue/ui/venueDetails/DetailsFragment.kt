package com.cafe_bazaar.venue.ui.venueDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cafe_bazaar.venue.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsFragmentVM by viewModels()
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.viewModel= viewModel
        binding.lifecycleOwner = this

        initView()
        initObservers()
        viewModel.getVenueDetails(venueId = args.venueId)

        return binding.root
    }

    private fun initView() {
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.buttonNavigate.setOnClickListener {
            val uri = "geo: ${viewModel.venue.value?.location?.lat},${viewModel.venue.value?.location?.lng} ?q= ${viewModel.venue.value?.location?.lat},${viewModel.venue.value?.location?.lng}"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
        }
    }

    private fun initObservers() {
        viewModel.showMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.hasBeenHandled) {
                    Toast.makeText(requireContext(), it.getContentIfNotHandled(), Toast.LENGTH_LONG)
                        .show()
                }
            }
        })

        viewModel.venue.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.txtTitle.text = it.name
                binding.txtCategory.text = it.categories.first().name
                binding.txtCountryCity.text =
                    "${it.location.country ?: ""} - ${it.location.city ?: ""}"
                binding.txtAddress.text =
                    "${it.location.address ?: ""} ${it.location.crossStreet ?: ""}"
                binding.txtDistance.text = "${it.location.distance ?: ""} KM"
            }
        })
    }
}
