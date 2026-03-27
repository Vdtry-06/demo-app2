package com.example.colorphone.ui.theatre

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.colorphone.databinding.FragmentTheatreBinding
import com.example.colorphone.ui.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TheatreFragment : BaseFragment<FragmentTheatreBinding>(FragmentTheatreBinding::inflate) {

    private val viewModel: TheatreViewModel by viewModels()
    private val args: TheatreFragmentArgs by navArgs()
    
    private val theatreAdapter: TheatreAdapter by lazy { 
        TheatreAdapter { theater ->
            val action = TheatreFragmentDirections.actionTheatreFragmentToShowtimeFragment(
                movieId = args.movieId,
                theaterId = theater.id
            )
            navController.navigate(action)
        } 
    }

    override fun setupView() {
        super.setupView()
        binding.rvTheatres.adapter = theatreAdapter
    }

    override fun setupObserver() {
        super.setupObserver()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.theatres.collectLatest { theatres ->
                    theatreAdapter.submitList(theatres)
                }
            }
        }
    }
}
