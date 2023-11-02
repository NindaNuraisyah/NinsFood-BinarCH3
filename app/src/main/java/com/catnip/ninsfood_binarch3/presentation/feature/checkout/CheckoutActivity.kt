package com.catnip.ninsfood_binarch3.presentation.feature.checkout

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.catnip.ninsfood_binarch3.R
import com.catnip.ninsfood_binarch3.databinding.ActivityCheckoutBinding
import com.catnip.ninsfood_binarch3.presentation.common.adapter.CartListAdapter
import com.catnip.ninsfood_binarch3.utils.proceedWhen
import com.catnip.ninsfood_binarch3.utils.toCurrencyFormat
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutActivity : AppCompatActivity() {

    private val viewModel: CheckoutViewModel by viewModel()

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
                }
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
