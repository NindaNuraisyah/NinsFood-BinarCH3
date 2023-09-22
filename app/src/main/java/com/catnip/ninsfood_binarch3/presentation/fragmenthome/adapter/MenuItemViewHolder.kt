package com.catnip.ninsfood_binarch3.presentation.fragmenthome.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.catnip.ninsfood_binarch3.core.ViewHolderBinder
import com.catnip.ninsfood_binarch3.databinding.ItemGridMenuBinding
import com.catnip.ninsfood_binarch3.databinding.ItemLinearMenuBinding
import com.catnip.ninsfood_binarch3.model.Menu

private fun Double.formatCurrency(currencySymbols: String): String {
    val formattedAmount = String.format("%, .0f", this).replace(",", ".")
    return "$currencySymbols $formattedAmount"
}

class LinearMenuItemViewHolder(
    private val binding: ItemLinearMenuBinding,
    private val onClickListener: (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        binding.ivLinearMenu.load(item.imgUrl) {
            crossfade(true)
        }
        binding.tvTitleLinearMenu.text = item.name
        binding.tvPriceLinearMenu.text = item.price.formatCurrency("Rp.")
        binding.root.setOnClickListener {
            onClickListener.invoke(item)
        }
    }
}

class GridMenuItemViewHolder(
    private val binding: ItemGridMenuBinding,
    private val onClickListener: (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        binding.ivGridMenu.load(item.imgUrl) {
            crossfade(true)
        }
        binding.tvTitleGridMenu.text = item.name
        binding.tvPriceGridMenu.text = item.price.formatCurrency("Rp.")
        binding.root.setOnClickListener {
            onClickListener.invoke(item)
        }
    }
}
