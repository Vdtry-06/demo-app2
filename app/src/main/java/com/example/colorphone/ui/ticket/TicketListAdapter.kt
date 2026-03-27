package com.example.colorphone.ui.ticket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.colorphone.databinding.ItemTicketBinding
import com.example.colorphone.domain.model.Ticket
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TicketListAdapter : ListAdapter<Ticket, TicketListAdapter.TicketViewHolder>(TicketDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val binding = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TicketViewHolder(private val binding: ItemTicketBinding) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        fun bind(ticket: Ticket) {
            binding.tvSeatNumber.text = "Seat: ${ticket.seatNumber}"
            binding.tvPrice.text = "Price: ${ticket.price}"
            binding.tvBookingTime.text = "Booked on: ${dateFormat.format(Date(ticket.bookingTime))}"
        }
    }

    private class TicketDiffCallback : DiffUtil.ItemCallback<Ticket>() {
        override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
            return oldItem == newItem
        }
    }
}
