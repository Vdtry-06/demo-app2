package com.example.colorphone.ui.showtime

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.colorphone.databinding.ItemShowtimeBinding
import com.example.colorphone.domain.model.Showtime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ShowtimeAdapter : ListAdapter<Showtime, ShowtimeAdapter.ShowtimeViewHolder>(ShowtimeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowtimeViewHolder {
        val binding = ItemShowtimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowtimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowtimeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ShowtimeViewHolder(private val binding: ItemShowtimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        fun bind(showtime: Showtime) {
            binding.tvRoomName.text = showtime.roomName
            val start = timeFormat.format(Date(showtime.startTime))
            val end = timeFormat.format(Date(showtime.endTime))
            binding.tvTime.text = "$start - $end"
        }
    }

    private class ShowtimeDiffCallback : DiffUtil.ItemCallback<Showtime>() {
        override fun areItemsTheSame(oldItem: Showtime, newItem: Showtime): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Showtime, newItem: Showtime): Boolean {
            return oldItem == newItem
        }
    }
}
