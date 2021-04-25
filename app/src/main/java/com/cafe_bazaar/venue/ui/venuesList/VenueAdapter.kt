package com.cafe_bazaar.venue.ui.venuesList

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cafe_bazaar.venue.data.models.venue.Items

class VenueAdapter (private val onItemClickListener: (Items, Int) -> Unit) :
    RecyclerView.Adapter<VenueViewHolder>() {

    private val items: ArrayList<Items> = arrayListOf()

    fun setItems(newItems: ArrayList<Items>) {
        items.clear()
        items.addAll(newItems)
        notifyItemRangeChanged(0, newItems.count())
    }

    fun addItems(newItems: ArrayList<Items>) {
        val lastItemsCount = items.count()
        items.addAll(newItems)
        notifyItemInserted(lastItemsCount)
    }

    fun clearAllItems() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
        return VenueViewHolder.create(parent, onItemClickListener)
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        holder.bind(
            currentItem = items[position],
            position = position,
        )
    }
}