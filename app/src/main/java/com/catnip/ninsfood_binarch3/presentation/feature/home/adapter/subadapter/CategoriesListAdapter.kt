package com.catnip.ninsfood_binarch3.presentation.feature.home.adapter.subadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.catnip.ninsfood_binarch3.databinding.ItemCategoriesMenuBinding
import com.catnip.ninsfood_binarch3.model.Categories

class CategoriesListAdapter(private val itemClick: (Categories) -> Unit) :
    RecyclerView.Adapter<CategoriesListAdapter.ItemCategoryViewHolder>() {

    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Categories>() {
                override fun areItemsTheSame(
                    oldItem: Categories,
                    newItem: Categories
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Categories,
                    newItem: Categories
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            }
        )

    fun submitData(data: List<Categories>) {
        dataDiffer.submitList(data)
    }

    fun setData(data: List<Categories>) {
        dataDiffer.submitList(data)
        notifyItemChanged(0, data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCategoryViewHolder {
        val binding =
            ItemCategoriesMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemCategoryViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: ItemCategoryViewHolder, position: Int) {
        holder.bindView(dataDiffer.currentList[position])
    }

    class ItemCategoryViewHolder(
        private val binding: ItemCategoriesMenuBinding,
        val itemClick: (Categories) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Categories) {
            with(item) {
                binding.ivCategory.load(item.imageUrl) {
                    crossfade(true)
                }
                binding.tvTitleCategory.text = item.name
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
