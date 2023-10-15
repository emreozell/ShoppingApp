package com.example.shoppingapp.ui.cart


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppingapp.R
import com.example.shoppingapp.base.BaseFragment
import com.example.shoppingapp.databinding.FragmentCartBinding
import com.example.shoppingapp.domain.model.Posts
import com.example.shoppingapp.data.remote.PostsOrder
import com.example.shoppingapp.ui.SharedViewModel
import com.example.shoppingapp.utils.CustomAdaptiveDecoration
import com.example.shoppingapp.utils.clickWithDebounce
import kotlinx.coroutines.launch


class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {
    private val viewModel: SharedViewModel by activityViewModels()
    private val postsList = mutableListOf<PostsOrder>()
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
        binding.sepetCloseTextView.clickWithDebounce {
            findNavController().popBackStack()
        }
        binding.cartDeleteTextview.clickWithDebounce {
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle(getString(R.string.cart_delete_title))
            alertDialog.setMessage(getString(R.string.cart_delete_message))

            alertDialog.setNegativeButton(getString(R.string.hayır_text)) { dialog, _ ->
                dialog.dismiss()
            }

            alertDialog.setPositiveButton(getString(R.string.evet_text)) { _, _ ->
                viewModel.deleteAllItems()
            }
            alertDialog.show()

        }
        observeData()
        observeState()
        viewModel.getLocalItems()
        viewModel.getTotalPrice()
        binding.cartMainButton.clickWithDebounce {
            viewModel.items.value.forEach {
                if (it.id != null && it.totalOrder > 0) {
                    val postProduct = PostsOrder(
                        id = it.id,
                        amount = it.totalOrder
                    )
                    postsList.add(postProduct)
                }
            }
            viewModel.postOrders(Posts(postsList))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect {
                    if (it.totalPrice != null) {
                        binding.totalPrice.text = "₺" + it.totalPrice.toString()
                    } else {
                        binding.totalPrice.text = getString(R.string.total_price_default)
                    }
                    if (it.postResponse != null) {

                        val alertDialog = AlertDialog.Builder(requireContext())

                        alertDialog.setTitle(getString(R.string.order_status_title))
                        alertDialog.setMessage(
                            "${getString(R.string.order_number_title)} ${it.postResponse.orderID}\n${
                                getString(
                                    R.string.order_status_title
                                )
                            }: ${it.postResponse.message}"
                        )
                        alertDialog.setPositiveButton(getString(R.string.tamam_text)) { dialog, which ->
                            dialog.dismiss()
                            viewModel.deleteAllItems()
                            viewModel.resetPostResponse()
                        }
                        alertDialog.show()
                    }
                }
            }
        }

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