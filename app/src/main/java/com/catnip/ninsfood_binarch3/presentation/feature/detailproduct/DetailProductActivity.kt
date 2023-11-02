package com.catnip.ninsfood_binarch3.presentation.feature.detailproduct

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import coil.load
import com.catnip.ninsfood_binarch3.R
import com.catnip.ninsfood_binarch3.databinding.ActivityDetailProductBinding
import com.catnip.ninsfood_binarch3.model.Product
import com.catnip.ninsfood_binarch3.utils.proceedWhen
import com.catnip.ninsfood_binarch3.utils.toCurrencyFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailProductActivity : AppCompatActivity() {

    private val binding: ActivityDetailProductBinding by lazy {
        ActivityDetailProductBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailProductViewModel by viewModel {
        parametersOf(intent.extras ?: bundleOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindProduct(viewModel.product)
        observeData()
        setClickListener()
    }

    private fun setClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.ivMinus.setOnClickListener {
            viewModel.minus()
        }
        binding.ivPlus.setOnClickListener {
            viewModel.add()
        }
        binding.tvDescShopLocation.setOnClickListener {
            viewModel.onLocationClicked()
        }
        binding.btnCart.setOnClickListener {
            viewModel.addToCart()
        }
    }

    private fun observeData() {

        viewModel.productCountLiveData.observe(this) {
            binding.tvNumberAmountProduct.text = it.toString()
        }

        viewModel.priceLiveData.observe(this) {
            binding.tvDetailTotalPrice.text = it.toCurrencyFormat()
        }

        viewModel.navigateToMapsLiveData.observe(this) { location ->
            location?.let {
                navigateToMaps(location)
            }
        }

        viewModel.addToCartResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_add_to_cart_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                },
                doOnError = {
                    Toast.makeText(this, it.exception?.message.orEmpty(), Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun bindProduct(product: Product?) {
        product?.let { item ->
            binding.ivMenu.load(item.imageUrl) {
                crossfade(true)
            }
            binding.tvTitleListMenu.text = item.name
            binding.tvDesc.text = item.desc
            binding.tvPriceMenu.text = item.price.toCurrencyFormat()
            binding.tvDescShopLocation.text = item.location
        }
    }

    private fun navigateToMaps(location: String?) {
        val gmmIntentUri = Uri.parse("http://maps.google.com/?q=$location")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

        startActivity(mapIntent)

    }

    companion object {
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
        fun startActivity(context: Context, product: Product) {
            val intent = Intent(context, DetailProductActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT, product)
            context.startActivity(intent)
        }
    }
}