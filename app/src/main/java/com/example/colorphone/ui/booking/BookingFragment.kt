package com.example.colorphone.ui.booking

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.colorphone.databinding.FragmentBookingBinding
import com.example.colorphone.ui.booking.adapter.SeatAdapter
import com.example.colorphone.ui.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import androidx.navigation.fragment.findNavController
import com.example.colorphone.R
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookingFragment : BaseFragment<FragmentBookingBinding>(FragmentBookingBinding::inflate) {

    private val viewModel: BookingViewModel by viewModels()
    private val seatAdapter = SeatAdapter { seat ->
        viewModel.onSeatClicked(seat)
    }

    override fun setupView() {
        super.setupView()
        binding.rvSeats.apply {
            layoutManager = GridLayoutManager(requireContext(), 10)
            adapter = seatAdapter
        }
    }

    override fun setUpListener() {
        binding.btnBookSelected.setOnClickListener {
            viewModel.bookSelected()
        }
    }

    override fun setupObserver() {
        super.setupObserver()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.seats.collectLatest { seats ->
                        seatAdapter.submitList(seats)
                    }
                }
                launch {
                    viewModel.bookingSuccess.collectLatest {
                        findNavController().navigate(R.id.action_bookingFragment_to_ticketListFragment)
                    }
                }
            }
        }
    }
}
