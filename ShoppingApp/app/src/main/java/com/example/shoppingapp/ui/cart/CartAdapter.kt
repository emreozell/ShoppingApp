package com.example.shoppingapp.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.CartRecyclerviewItemBinding
import com.example.shoppingapp.domain.model.Item

class CartAdapter(val context: Context) :
    ListAdapter<Item, CartAdapter.FoodsViewHolder>(ItemListUtil()) {

    private var onItemClick: (item: Item) -> Unit =
        { item -> }

    fun onItemClick(item: (Item) -> Unit) {
        onItemClick = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodsViewHolder {
        val binding = CartRecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FoodsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }

    inner class FoodsViewHolder(
        private val binding: CartRecyclerviewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item, position: Int) {
            binding.apply {
                itemName.text = item.name
                itemPrice.text = item.price.toString()
                Glide.with(context)
                    .load(item.imageUrl)
                    .into(cartImageView)
                sizeTextview.text = item.totalOrder.toString()
                addButton.setOnClickListener {
                    if (item.stock == item.totalOrder) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.stock_not_enough),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        item.totalOrder += 1
                        sizeTextview.text = item.totalOrder.toString()
                        onItemClick.invoke(item)
                    }
                }
                minusButton.setOnClickListener {
                    item.totalOrder -= 1
                    sizeTextview.text = item.totalOrder.toString()
                    onItemClick.invoke(item)
                }

            }
        }
    }

    class ItemListUtil : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.stock == newItem.stock &&
                    oldItem.price == newItem.price &&
                    oldItem.currency == newItem.currency
        }
    }
}