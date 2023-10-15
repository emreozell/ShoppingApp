package com.example.shoppingapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppingapp.base.BaseFragment
import com.example.shoppingapp.databinding.FragmentHomeBinding
import com.example.shoppingapp.utils.CustomAdaptiveDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var homeAdapter: HomeAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.localListSİzeLiveData.observe(viewLifecycleOwner) { localListSize ->
            binding.homeCartCount.text=localListSize.toString()
        }
        viewModel.getItems()
        observeData()
    }


    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { viewState ->
                    if (viewState.errorMessage != null) {


                    } else {
                        viewState.isLoading?.let {
                            binding.homeProgressBar.isVisible = it
                        }
                        if (viewState.itemList != null) {
                            homeAdapter = HomeAdapter(viewState.itemList, requireContext())
                            binding.homeRecyclerview.apply {
                                adapter = homeAdapter
                                layoutManager = GridLayoutManager(requireContext(), 3)
                                addItemDecoration(
                                    CustomAdaptiveDecoration(
                                        context = requireContext(),
                                        spanCount = 3,
                                        spacingHorizontal = 10,
                                        spacingVertical = 10
                                    )
                                )
                            }
                            homeAdapter.onItemClick { item ->
                                viewModel.updateDataBase(item)
                            }
                        }
                    }
                }
            }
        }
    }
}