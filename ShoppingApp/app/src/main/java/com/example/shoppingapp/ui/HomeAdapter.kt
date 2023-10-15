package com.example.shoppingapp.ui

import android.content.Context
import com.example.shoppingapp.models.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapp.databinding.HomeRecyclerviewItemBinding


class HomeAdapter(
    private val itemList: List<Item>,
    private val context: Context,
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var onItemClick: (item: Item) -> Unit =
        { item -> }

    fun onItemClick(item: (Item) -> Unit) {
        onItemClick = item
    }


    class ViewHolder(val binding: HomeRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HomeRecyclerviewItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            foodsTitleTextView.text = itemList[position].name
            foodsIngredientsTextView.text =
                "${itemList[position].currency + itemList[position].price.toString()}"
            sizeTextview.text = itemList[position].totalOrder.toString()
            Glide.with(context)
                .load(itemList[position].imageUrl)
                .into(foodsImageView)
            addButton.setOnClickListener {
                if (  itemList[position].stock==itemList[position].totalOrder){
                    Toast.makeText(context,"Stockta yeterli ürün bulunmamaktadır.",Toast.LENGTH_SHORT).show()
                }else{
                    itemList[position].totalOrder += 1
                    if (itemList[position].totalOrder > 0) {
                        minusButton.visibility = View.VISIBLE
                        sizeTextview.visibility = View.VISIBLE
                    }
                    sizeTextview.text = itemList[position].totalOrder.toString()
                    onItemClick.invoke(itemList[position])
                }

            }
            minusButton.setOnClickListener {
                itemList[position].totalOrder -= 1
                if (itemList[position].totalOrder == 0) {
                    minusButton.visibility = View.GONE
                    sizeTextview.visibility = View.GONE
                }
                sizeTextview.text = itemList[position].totalOrder.toString()
                onItemClick.invoke(itemList[position])
            }
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}