package com.catnip.ninsfood_binarch3.presentation.feature.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.catnip.ninsfood_binarch3.data.datasource.dummy.DummyCategoriesDataSource
import com.catnip.ninsfood_binarch3.data.datasource.dummy.DummyCategoriesDataSourceImpl
import com.catnip.ninsfood_binarch3.data.datasource.dummy.DummyProductDataSource
import com.catnip.ninsfood_binarch3.data.datasource.dummy.DummyProductDataSourceImpl
import com.catnip.ninsfood_binarch3.data.datasource.local.database.AppDatabase
import com.catnip.ninsfood_binarch3.data.datasource.local.database.datasource.ProductDatabaseDataSource
import com.catnip.ninsfood_binarch3.data.datasource.local.datastore.UserPreferenceDataSource
import com.catnip.ninsfood_binarch3.data.datasource.local.datastore.UserPreferenceDataSourceImpl
import com.catnip.ninsfood_binarch3.data.datasource.local.datastore.appDataStore
import com.catnip.ninsfood_binarch3.data.repository.ProductRepository
import com.catnip.ninsfood_binarch3.data.repository.ProductRepositoryImpl
import com.catnip.ninsfood_binarch3.databinding.FragmentHomeBinding
import com.catnip.ninsfood_binarch3.model.Categories
import com.catnip.ninsfood_binarch3.model.Product
import com.catnip.ninsfood_binarch3.presentation.feature.home.adapter.AdapterLayoutMode
import com.catnip.ninsfood_binarch3.presentation.feature.home.adapter.CategoriesListAdapter
import com.catnip.ninsfood_binarch3.presentation.feature.home.adapter.ProductListAdapter
import com.catnip.ninsfood_binarch3.utils.GenericViewModelFactory
import com.catnip.ninsfood_binarch3.utils.PreferenceDataStoreHelperImpl
import com.catnip.ninsfood_binarch3.utils.proceedWhen


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val datasource: DummyProductDataSource by lazy {
        DummyProductDataSourceImpl()
    }

    private val adapter: ProductListAdapter by lazy {
        ProductListAdapter(AdapterLayoutMode.LINEAR) { product: Product ->
            navigateToDetail(product)
        }
    }

    private val viewModel : HomeViewModel by viewModels {
        GenericViewModelFactory.create(HomeViewModel(createProductRepo(), createPreferenceDataSource()))
    }

    private fun createProductRepo() : ProductRepository {
        val cds: DummyCategoriesDataSource = DummyCategoriesDataSourceImpl()
        val database = AppDatabase.getInstance(requireContext())
        val productDao = database.productDao()
        val productDataSource = ProductDatabaseDataSource(productDao)
        return ProductRepositoryImpl(productDataSource, cds)
    }

    private fun createPreferenceDataSource() : UserPreferenceDataSource {
        val dataStore = this.requireContext().appDataStore
        val dataStoreHelper = PreferenceDataStoreHelperImpl(dataStore)
        return UserPreferenceDataSourceImpl(dataStoreHelper)
    }

    private fun navigateToDetail(item: Product) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showListCategories(viewModel.getCategoriesData())
        setupList()
        setupSwitch()
        setSwitchAction()
        observeGridPref()
        observeProductData()
    }

    private fun observeProductData() {
        viewModel.productListLiveData.observe(viewLifecycleOwner){
            it.proceedWhen(doOnSuccess = {
                it.payload?.let { it1 -> adapter.submitData(it1) }
            })
        }
    }
    private fun observeGridPref() {
        viewModel.usingGridLiveData.observe(viewLifecycleOwner) { isUsingGrid ->
            binding.switchListGrid.isChecked = isUsingGrid
            (binding.rvMenu.layoutManager as GridLayoutManager).spanCount =
                if (isUsingGrid) 2 else 1
            adapter.adapterLayoutMode =
                if (isUsingGrid) AdapterLayoutMode.GRID else AdapterLayoutMode.LINEAR
            adapter.refreshList()
        }
    }

    private fun setSwitchAction() {
        binding.switchListGrid.setOnCheckedChangeListener{
                _, isUsingGrid -> viewModel.setUsingGridPref(isUsingGrid)
        }
    }

    private fun showListCategories(data : List<Categories>) {
        val categoryListAdapter = CategoriesListAdapter()
        binding.rvCategories.adapter = categoryListAdapter
        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false )

        categoryListAdapter.setData(data)
    }

    private fun setupList() {
        val span = if(adapter.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvMenu.apply {
            layoutManager = GridLayoutManager(requireContext(),span)
            adapter = this@HomeFragment.adapter
        }

    }
    private fun setupSwitch() {
        binding.switchListGrid.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setUsingGridPref(isChecked)
        }
    }

}