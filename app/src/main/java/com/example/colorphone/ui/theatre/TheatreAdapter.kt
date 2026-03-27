package com.example.colorphone.ui.theatre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.colorphone.databinding.ItemTheatreBinding
import com.example.colorphone.domain.model.Theater

class TheatreAdapter : ListAdapter<Theater, TheatreAdapter.TheatreViewHolder>(TheatreDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheatreViewHolder {
        val binding = ItemTheatreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TheatreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TheatreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TheatreViewHolder(private val binding: ItemTheatreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(theater: Theater) {
            binding.tvName.text = theater.name
            binding.tvAddress.text = theater.address
        }
    }

    private class TheatreDiffCallback : DiffUtil.ItemCallback<Theater>() {
        override fun areItemsTheSame(oldItem: Theater, newItem: Theater): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Theater, newItem: Theater): Boolean {
            return oldItem == newItem
        }
    }
}
