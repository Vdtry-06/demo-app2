package com.example.colorphone.ui.showtime

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.colorphone.databinding.FragmentShowtimeBinding
import com.example.colorphone.ui.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowtimeFragment : BaseFragment<FragmentShowtimeBinding>(FragmentShowtimeBinding::inflate) {

    private val viewModel: ShowtimeViewModel by viewModels()
    private val showtimeAdapter: ShowtimeAdapter by lazy { ShowtimeAdapter() }

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
