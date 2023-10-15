package com.example.shoppingapp.ui.cart


import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppingapp.base.BaseFragment
import com.example.shoppingapp.databinding.FragmentCartBinding
import com.example.shoppingapp.ui.SharedViewModel
import com.example.shoppingapp.utils.CustomAdaptiveDecoration
import kotlinx.coroutines.launch


class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {
    private val viewModel: SharedViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cartRecyclerview.apply {
            layoutManager = GridLayoutManager(requireContext(), 1)
            addItemDecoration(
                CustomAdaptiveDecoration(
                    context = requireContext(),
                    spanCount = 1,
                    spacingVertical = 0,
                    spacingHorizontal = 0
                )
            )
        }
        binding.sepetCloseTextView.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.cartDeleteTextview.setOnClickListener {
            viewModel.deleteAllItems()
        }
        observeData()
        viewModel.getLocalItems()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.items.collect { items ->
                val cartAdapter = CartAdapter(requireContext())
                if (items.isNotEmpty()) {
                    cartAdapter.submitList(items)
                    binding.cartRecyclerview.adapter = cartAdapter
                } else {
                    cartAdapter.submitList(emptyList())
                    binding.cartRecyclerview.adapter = cartAdapter
                }
                cartAdapter.onItemClick { item ->
                    viewModel.updateDataBase(item)
                }
            }
        }
    }
}