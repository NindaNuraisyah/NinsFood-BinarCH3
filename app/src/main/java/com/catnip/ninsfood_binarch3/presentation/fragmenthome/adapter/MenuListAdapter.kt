package com.catnip.ninsfood_binarch3.presentation.fragmenthome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.catnip.ninsfood_binarch3.core.ViewHolderBinder
import com.catnip.ninsfood_binarch3.databinding.ItemGridMenuBinding
import com.catnip.ninsfood_binarch3.databinding.ItemLinearMenuBinding
import com.catnip.ninsfood_binarch3.model.Menu

class MenuListAdapter(
    var adapterLayoutMode: AdapterLayoutMode,
    private val onClickListener: (Menu) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Menu>() {
        override fun areItemsTheSame(oldItem: Menu, newItem: Menu): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.desc == newItem.desc &&
                    oldItem.price == newItem.price &&
                    oldItem.imgUrl == newItem.imgUrl
        }

        override fun areContentsTheSame(oldItem: Menu, newItem: Menu): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            AdapterLayoutMode.GRID.ordinal -> {
                GridMenuItemViewHolder(
                    binding = ItemGridMenuBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ), onClickListener
                )
            }

            else -> {
                LinearMenuItemViewHolder(
                    binding = ItemLinearMenuBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ), onClickListener
                )
            }
        }
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Menu>).bind(dataDiffer.currentList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return adapterLayoutMode.ordinal // 0 GRID, 1 Linear
    }

    fun submitData(data: List<Menu>) {
        dataDiffer.submitList(data)
    }

    fun refreshList() {
        notifyItemRangeChanged(0, dataDiffer.currentList.size)
    }
}