package com.example.colorphone.ui.showtime

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.colorphone.databinding.FragmentShowtimeBinding
import com.example.colorphone.ui.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.findNavController
import androidx.core.os.bundleOf
import com.example.colorphone.R
import com.example.colorphone.util.SessionManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ShowtimeFragment : BaseFragment<FragmentShowtimeBinding>(FragmentShowtimeBinding::inflate) {

    private val viewModel: ShowtimeViewModel by viewModels()
    @Inject lateinit var sessionManager: SessionManager

    private val showtimeAdapter: ShowtimeAdapter by lazy { 
        ShowtimeAdapter { showtimeId ->
            if (sessionManager.isLoggedIn()) {
                findNavController().navigate(
                    R.id.action_showtimeFragment_to_bookingFragment,
                    bundleOf("showtimeId" to showtimeId)
                )
            } else {
                findNavController().navigate(
                    R.id.action_showtimeFragment_to_loginFragment,
                    bundleOf("showtimeId" to showtimeId)
                )
            }
        }
    }

    override fun setupView() {
        super.setupView()
        binding.rvShowtimes.adapter = showtimeAdapter
    }

    override fun setupObserver() {
        super.setupObserver()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showtimes.collectLatest { showtimes ->
                    showtimeAdapter.submitList(showtimes)
                }
            }
        }
    }
}
