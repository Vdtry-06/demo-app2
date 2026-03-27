package com.example.colorphone.ui.booking.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.colorphone.databinding.ItemSeatBinding
import com.example.colorphone.ui.booking.model.SeatStatus
import com.example.colorphone.ui.booking.model.SeatUiModel

class SeatAdapter(private val onSeatClicked: (SeatUiModel) -> Unit) :
    ListAdapter<SeatUiModel, SeatAdapter.SeatViewHolder>(SeatDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewHolder {
        val binding = ItemSeatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeatViewHolder(binding, onSeatClicked)
    }

    override fun onBindViewHolder(holder: SeatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SeatViewHolder(
        private val binding: ItemSeatBinding,
        private val onSeatClicked: (SeatUiModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(seat: SeatUiModel) {
            binding.tvSeatNumber.text = seat.seatNumber
            
            val bgColor = when (seat.status) {
                SeatStatus.AVAILABLE -> Color.parseColor("#4CAF50") // Green
                SeatStatus.BOOKED -> Color.parseColor("#F44336")    // Red
                SeatStatus.SELECTED -> Color.parseColor("#FF9800")  // Orange
            }
            binding.tvSeatNumber.setBackgroundColor(bgColor)

            binding.root.setOnClickListener {
                if (seat.status != SeatStatus.BOOKED) {
                    onSeatClicked(seat)
                }
            }
        }
    }

    private class SeatDiffCallback : DiffUtil.ItemCallback<SeatUiModel>() {
        override fun areItemsTheSame(oldItem: SeatUiModel, newItem: SeatUiModel): Boolean {
            return oldItem.seatNumber == newItem.seatNumber
        }

        override fun areContentsTheSame(oldItem: SeatUiModel, newItem: SeatUiModel): Boolean {
            return oldItem == newItem
        }
    }
}
