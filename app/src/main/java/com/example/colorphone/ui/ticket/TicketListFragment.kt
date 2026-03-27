package com.example.colorphone.ui.ticket

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.colorphone.databinding.FragmentTicketListBinding
import com.example.colorphone.ui.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TicketListFragment : BaseFragment<FragmentTicketListBinding>(FragmentTicketListBinding::inflate) {

    private val viewModel: TicketListViewModel by viewModels()
    private val ticketAdapter: TicketListAdapter by lazy { TicketListAdapter() }

    override fun setupView() {
        super.setupView()
        binding.rvTickets.adapter = ticketAdapter
    }

    override fun setupObserver() {
        super.setupObserver()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tickets.collectLatest { tickets ->
                    ticketAdapter.submitList(tickets)
                }
            }
        }
    }
}
