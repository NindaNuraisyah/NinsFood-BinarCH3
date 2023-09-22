package com.catnip.ninsfood_binarch3.presentation.fragmentdetail

import com.catnip.ninsfood_binarch3.R
import com.catnip.ninsfood_binarch3.databinding.FragmentDetailBinding
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import coil.load
import com.catnip.ninsfood_binarch3.model.Menu

class FragmentDetail : Fragment() {

    private fun Double.formatCurrency(currencySymbols: String): String {
        val formattedAmount = String.format("%, .0f", this).replace(",", ".")
        return "$currencySymbols $formattedAmount"
    }

    private lateinit var binding: FragmentDetailBinding

    private val menu: Menu? by lazy {
        FragmentDetailArgs.fromBundle(arguments as Bundle).menu
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
        showMenuData()
        calculateProductTotalPrice()
    }


    private fun setClickListener() {
        binding.llLocation.setOnClickListener {
            navigateToMaps()
        }

        binding.ibBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun navigateToMaps() {
        binding.llLocation.setOnClickListener {
            val location = menu?.location

            val gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(location))
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            if (mapIntent.resolveActivity(requireContext().packageManager) == null) {
                startActivity(mapIntent)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Google Maps tidak tersambung!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun showMenuData() {
        menu?.let { n ->
            binding.apply {
                ivMenu.load(n.imgUrl) {
                    crossfade(true)
                }
                tvTitleListMenu.text = n.name
                tvPriceMenu.text = n.price.formatCurrency("Rp.")
                tvDescShopLocation.text = n.location
                btnCart.text =
                    getString(R.string.text_cart, n.price.toInt())
                tvDesc.apply {
                    text = n.desc
                    movementMethod = ScrollingMovementMethod()
                }
            }
        }
    }
    private fun calculateProductTotalPrice(){
        var totalProduct : Int = 1
        var totalPrice : Double
        val minusImage = binding.ivMinus
        val plusImage = binding.ivPlus
        val textTotalProduct = binding.tvNumberAmountProduct
        val textTotalPrice = binding.btnCart
        plusImage.setOnClickListener{
            totalProduct += 1
            totalPrice = (totalProduct * (menu?.price?.toInt() ?: 0)).toDouble()
            textTotalProduct.text = totalProduct.toString()
            textTotalPrice.text = getString(R.string.text_cart, totalPrice.toInt())
        }
        minusImage.setOnClickListener{
            if (totalProduct <= 1){
                totalProduct = 1
            } else {
                totalProduct -= 1
                totalPrice = (totalProduct * (menu?.price?.toInt() ?: 0)).toDouble()
                textTotalProduct.text = totalProduct.toString()
                textTotalPrice.text = getString(R.string.text_cart, totalPrice.toInt())
            }
        }
    }
}
