package com.example.shoppingapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.shoppingapp.base.BaseFragment
import com.example.shoppingapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel : HomeViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getItems()
    }
}