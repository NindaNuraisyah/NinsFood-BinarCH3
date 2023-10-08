package com.catnip.ninsfood_binarch3.presentation.feature.home.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.catnip.ninsfood_binarch3.core.ViewHolderBinder
import com.catnip.ninsfood_binarch3.databinding.ItemGridMenuBinding
import com.catnip.ninsfood_binarch3.databinding.ItemLinearMenuBinding
import com.catnip.ninsfood_binarch3.model.Product
import com.catnip.ninsfood_binarch3.utils.toCurrencyFormat

class LinearMenuItemViewHolder(
    private val binding: ItemLinearMenuBinding,
    private val onClickListener: (Product) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Product> {
    override fun bind(item: Product) {
        binding.ivLinearMenu.load(item.imgUrl) {
            crossfade(true)
        }
        binding.tvTitleLinearMenu.text = item.name
        binding.tvPriceLinearMenu.text = item.price.toCurrencyFormat()
        binding.root.setOnClickListener {
            onClickListener.invoke(item)
        }
    }
}

class GridMenuItemViewHolder(
    private val binding: ItemGridMenuBinding,
    private val onClickListener: (Product) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Product> {
    override fun bind(item: Product) {
        binding.ivGridMenu.load(item.imgUrl) {
            crossfade(true)
        }
        binding.tvTitleGridMenu.text = item.name
        binding.tvPriceGridMenu.text = item.price.toCurrencyFormat()
        binding.root.setOnClickListener {
            onClickListener.invoke(item)
        }
    }
}
