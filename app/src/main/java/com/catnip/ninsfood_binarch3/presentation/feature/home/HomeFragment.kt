package com.catnip.ninsfood_binarch3.presentation.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.catnip.ninsfood_binarch3.R
import com.catnip.ninsfood_binarch3.databinding.FragmentHomeBinding
import com.catnip.ninsfood_binarch3.model.Product
import com.catnip.ninsfood_binarch3.presentation.feature.detailproduct.DetailProductActivity
import com.catnip.ninsfood_binarch3.presentation.feature.home.adapter.AdapterLayoutMode
import com.catnip.ninsfood_binarch3.presentation.feature.home.adapter.subadapter.CategoriesListAdapter
import com.catnip.ninsfood_binarch3.presentation.feature.home.adapter.subadapter.ProductListAdapter
import com.catnip.ninsfood_binarch3.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()

    private val categoryAdapter: CategoriesListAdapter by lazy {
        CategoriesListAdapter {
            viewModel.getProducts(it.name)
        }
    }

    private val productAdapter: ProductListAdapter by lazy {
        ProductListAdapter(AdapterLayoutMode.LINEAR) { product: Product ->
            navigateToDetail(product)
        }
    }

    private fun navigateToDetail(item: Product) {
        DetailProductActivity.startActivity(requireContext(), item)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        getData()
        setupList()
        setupSwitch()
        setSwitchAction()
        observeGridPref()
        observeData()
    }

    private fun getData() {
        viewModel.getCategories()
        viewModel.getProducts()
    }

    private fun observeData() {
        viewModel.categories.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = {
                binding.layoutStateCategory.root.isVisible = false
                binding.layoutStateCategory.pbLoading.isVisible = false
                binding.layoutStateCategory.tvError.isVisible = false
                binding.rvCategories.apply {
                    isVisible = true
                    adapter = categoryAdapter
                }
                it.payload?.let { data -> categoryAdapter.submitData(data) }
            }, doOnLoading = {
                    binding.layoutStateCategory.root.isVisible = true
                    binding.layoutStateCategory.pbLoading.isVisible = true
                    binding.layoutStateCategory.tvError.isVisible = false
                    binding.rvCategories.isVisible = false
                }, doOnError = {
                    binding.layoutStateCategory.root.isVisible = true
                    binding.layoutStateCategory.pbLoading.isVisible = false
                    binding.layoutStateCategory.tvError.isVisible = true
                    binding.layoutStateCategory.tvError.text = it.exception?.message.orEmpty()
                    binding.rvCategories.isVisible = false
                })
        }
        viewModel.products.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = {
                binding.layoutStateProduct.root.isVisible = false
                binding.layoutStateProduct.pbLoading.isVisible = false
                binding.layoutStateProduct.tvError.isVisible = false
                binding.rvProduct.apply {
                    isVisible = true
                    adapter = productAdapter
                }
                it.payload?.let { data -> productAdapter.submitData(data) }
            }, doOnLoading = {
                    binding.layoutStateProduct.root.isVisible = true
                    binding.layoutStateProduct.pbLoading.isVisible = true
                    binding.layoutStateProduct.tvError.isVisible = false
                    binding.rvProduct.isVisible = false
                }, doOnError = {
                    binding.layoutStateProduct.root.isVisible = true
                    binding.layoutStateProduct.pbLoading.isVisible = false
                    binding.layoutStateProduct.tvError.isVisible = true
                    binding.layoutStateProduct.tvError.text = it.exception?.message.orEmpty()
                    binding.rvProduct.isVisible = false
                }, doOnEmpty = {
                    binding.layoutStateProduct.root.isVisible = true
                    binding.layoutStateProduct.pbLoading.isVisible = false
                    binding.layoutStateProduct.tvError.isVisible = true
                    binding.layoutStateProduct.tvError.text = resources.getString(R.string.product_not_found)
                    binding.rvProduct.isVisible = false
                })
        }
    }
    private fun observeGridPref() {
        viewModel.usingGridLiveData.observe(viewLifecycleOwner) { isUsingGrid ->
            binding.switchListGrid.isChecked = isUsingGrid
            (binding.rvProduct.layoutManager as GridLayoutManager).spanCount =
                if (isUsingGrid) 2 else 1
            productAdapter.adapterLayoutMode =
                if (isUsingGrid) AdapterLayoutMode.GRID else AdapterLayoutMode.LINEAR
            productAdapter.refreshList()
        }
    }

    private fun setSwitchAction() {
        binding.switchListGrid.setOnCheckedChangeListener {
                _, isUsingGrid ->
            viewModel.setUsingGridPref(isUsingGrid)
        }
    }

    private fun setupList() {
        val span = if (productAdapter.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvProduct.apply {
            layoutManager = GridLayoutManager(requireContext(), span)
            adapter = this@HomeFragment.productAdapter
        }
    }
    private fun setupSwitch() {
        binding.switchListGrid.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setUsingGridPref(isChecked)
        }
    }
}
