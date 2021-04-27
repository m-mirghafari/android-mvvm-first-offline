package com.cafe_bazaar.venue.ui.venuesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cafe_bazaar.venue.R
import com.cafe_bazaar.venue.data.models.venue.Items
import com.cafe_bazaar.venue.databinding.ItemVenueBinding

class VenueViewHolder (
    private val binding: ItemVenueBinding,
    private val onItemClickListener: ((Items, Int) -> Unit)) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(currentItem: Items, position: Int) {
        binding.txtTitle.text = currentItem.venue.name
        binding.txtCategory.text = currentItem.venue.categories.first().name
        binding.txtAddress.text = currentItem.venue.location.address

        binding.itemRoot.setOnClickListener {
            onItemClickListener(currentItem, position)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onItemClickListener: (Items, Int) -> Unit): VenueViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_venue, parent, false)
            val binding = ItemVenueBinding.bind(view)

            return VenueViewHolder(binding, onItemClickListener)
        }
    }
}
