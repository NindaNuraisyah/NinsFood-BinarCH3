package com.catnip.ninsfood_binarch3.presentation.feature.checkout

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.catnip.ninsfood_binarch3.R
import com.catnip.ninsfood_binarch3.data.datasource.local.database.AppDatabase
import com.catnip.ninsfood_binarch3.data.datasource.local.database.datasource.CartDataSource
import com.catnip.ninsfood_binarch3.data.datasource.local.database.datasource.CartDatabaseDataSource
import com.catnip.ninsfood_binarch3.data.network.api.datasource.NinsFoodApiDataSource
import com.catnip.ninsfood_binarch3.data.network.api.service.NinsFoodApiService
import com.catnip.ninsfood_binarch3.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.catnip.ninsfood_binarch3.data.repository.CartRepository
import com.catnip.ninsfood_binarch3.data.repository.CartRepositoryImpl
import com.catnip.ninsfood_binarch3.data.repository.UserRepository
import com.catnip.ninsfood_binarch3.data.repository.UserRepositoryImpl
import com.catnip.ninsfood_binarch3.databinding.ActivityCheckoutBinding
import com.catnip.ninsfood_binarch3.presentation.common.adapter.CartListAdapter
import com.catnip.ninsfood_binarch3.utils.GenericViewModelFactory
import com.catnip.ninsfood_binarch3.utils.proceedWhen
import com.catnip.ninsfood_binarch3.utils.toCurrencyFormat
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.firebase.auth.FirebaseAuth

class CheckoutActivity : AppCompatActivity() {

    private val viewModel: CheckoutViewModel by viewModels {
        val database = AppDatabase.getInstance(this)
        val cartDao = database.cartDao()
        val cartDataSource: CartDataSource = CartDatabaseDataSource(cartDao)
        val chuckerInterceptor = ChuckerInterceptor(this.applicationContext)
        val service = NinsFoodApiService.invoke(chuckerInterceptor)
        val apiDataSource = NinsFoodApiDataSource(service)
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseDataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
        val cartRepo: CartRepository = CartRepositoryImpl(cartDataSource, apiDataSource)
        val userRepo: UserRepository = UserRepositoryImpl(firebaseDataSource)
        GenericViewModelFactory.create(CheckoutViewModel(cartRepo, userRepo))
    }

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupList()
        observeData()
        setClickListener()
    }

    private fun setClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.layoutContent.btnCheckout.setOnClickListener {
            viewModel.createOrder()
        }
    }

    private fun setupList() {
        binding.layoutContent.rvCart.adapter = adapter
    }

    private fun deleteCartData() {
        viewModel.deleteAllCarts()
    }

    private fun observeData() {
        observeCartList()
        observeCheckoutResult()
    }

    private fun dialogCheckoutSuccess() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.layout_dialog_success)

        val btnOkDialog = dialog.findViewById<Button>(R.id.dialog_ok_button)
        btnOkDialog.setOnClickListener {
            dialog.dismiss()
            deleteCartData()
        }
        dialog.show()
    }

    private fun observeCartList() {
        viewModel.cartList.observe(this) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutContent.root.isVisible = true
                    binding.layoutContent.rvCart.isVisible = true
                    binding.layoutContent.cvSectionOrder.isVisible = true
                    result.payload?.let { (carts, totalPrice) ->
                        adapter.submitData(carts)
                        binding.layoutContent.tvTotalPrice.text = totalPrice.toCurrencyFormat()
                    }
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutContent.root.isVisible = false
                    binding.layoutContent.rvCart.isVisible = false
                    binding.layoutContent.cvSectionOrder.isVisible = false
                },
                doOnError = { err ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = err.exception?.message.orEmpty()
                    binding.layoutContent.root.isVisible = false
                    binding.layoutContent.rvCart.isVisible = false
                    binding.layoutContent.cvSectionOrder.isVisible = false
                },
                doOnEmpty = { data ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                    data.payload?.let { (_, totalPrice) ->
                        binding.layoutContent.tvTotalPrice.text = totalPrice.toCurrencyFormat()
                    }
                    binding.layoutContent.root.isVisible = false
                    binding.layoutContent.rvCart.isVisible = false
                    binding.layoutContent.cvSectionOrder.isVisible = false
                },
            )
        }
    }

    private fun observeCheckoutResult() {
        viewModel.checkoutResult.observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    dialogCheckoutSuccess()
                },
                doOnError = { err ->
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    Toast.makeText(this, err.exception?.message.orEmpty(), Toast.LENGTH_SHORT)
                        .show()
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                }
            )
        }
    }
}