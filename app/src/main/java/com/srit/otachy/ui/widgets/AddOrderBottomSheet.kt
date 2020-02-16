package com.srit.otachy.ui.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.srit.otachy.adapters.setFormattedPriceTxt
import com.srit.otachy.database.local.ShoppingCartRepository
import com.srit.otachy.databinding.BottomSheetAddOrderBinding
import com.orhanobut.logger.Logger
import com.srit.otachy.database.local.VendorShopRepository
import com.srit.otachy.database.models.*

class OrderBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetAddOrderBinding
    private var numOfItems: Double = 1.0
    private var totalPrice: Double = 0.0

    companion object Factory {

        fun newInstance(
        ): OrderBottomSheet {
            return OrderBottomSheet()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetAddOrderBinding.inflate(layoutInflater, container, false)

        ServiceModel.getInstance()?.let {
            binding.serviceItem = it
        }

        setListeners()

        binding
            .addBtn
            .setOnClickListener {
                insertItem(it)
                dismiss()
            }
        return binding.root
    }

    private fun setListeners() {
        binding.increaseBtn.setOnClickListener {
            numOfItems += 1

            reCalculateTotalPrice()
        }

        binding.decreaseBtn.setOnClickListener {
            numOfItems -= 1
            if (numOfItems < 1) numOfItems = 1.0

            reCalculateTotalPrice()
        }


    }

    private fun reCalculateTotalPrice() {
        val service = ServiceModel.getInstance()
        service?.let {
            binding.numOfItems.text = numOfItems.toString()
            totalPrice = numOfItems * service.price
            setFormattedPriceTxt(binding.totalPrice, totalPrice)
        }
    }


    private fun insertItem(it: View) {
        val shoppingCartRepository = ShoppingCartRepository(it.context)
        val vendorShopRepository = VendorShopRepository(it.context)

        val category = CategotyModel.getInstance()
        val service = ServiceModel.getInstance()
        val vendor = VendorModel.getInstance()
        if (service != null && category != null) {
            vendorShopRepository.insertItems(
                VendorShopModel(
                    vendor.id.toInt(),
                    vendor.name,
                    vendor.district,
                    VendorModel.getInstance().user.id.toString()
                )
            )

            shoppingCartRepository.insertItems(
                ShoppingCartItemModel(
                    service.item.id.toString(),
                    VendorModel.getInstance().id,
                    service.item.name,
                    totalPrice, category.id.toString(), category.name, numOfItems
                )
            )
        }
    }

}