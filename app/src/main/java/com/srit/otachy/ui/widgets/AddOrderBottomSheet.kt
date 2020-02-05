package com.srit.otachy.ui.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.srit.otachy.adapters.setFormattedPriceTxt
import com.srit.otachy.database.local.ShoppingCartRepository
import com.srit.otachy.database.models.CategoryItemModel
import com.srit.otachy.database.models.ServiceItemModel
import com.srit.otachy.database.models.ShoppingCartItemModel
import com.srit.otachy.databinding.BottomSheetAddOrderBinding
import com.orhanobut.logger.Logger

class OrderBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetAddOrderBinding
    private var categoryItemModel: CategoryItemModel? = null
    private var serviceItemModel: ServiceItemModel? = null
    private var numOfItems:Double= 1.0

    companion object Factory {
        private const val EXTRA_CATEGORY: String = "extraCategory"
        private const val EXTRA_SERVICE: String = "extraService"

        fun newInstance(
            categoryItemModel: CategoryItemModel,
            serviceItemModel: ServiceItemModel
        ): OrderBottomSheet {
            val bottomSheet =
                OrderBottomSheet()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_CATEGORY, categoryItemModel)
            bundle.putParcelable(EXTRA_SERVICE, serviceItemModel)

            bottomSheet.arguments = bundle
            return bottomSheet
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetAddOrderBinding.inflate(layoutInflater, container, false)

        categoryItemModel = arguments?.getParcelable(EXTRA_CATEGORY)
        serviceItemModel = arguments?.getParcelable(EXTRA_SERVICE)

        serviceItemModel?.let {
            binding.serviceItem= it
        }

        setListeners()

        Logger.e("Hello otachy")
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
        val service = serviceItemModel
        service?.let {
            binding.numOfItems.text = numOfItems.toString()
            setFormattedPriceTxt(binding.totalPrice, numOfItems * service.price)
        }
    }


    private fun insertItem(it: View) {
        val repo = ShoppingCartRepository(it.context)

        val category = categoryItemModel
        val service = serviceItemModel

        if (service != null && category != null) {
            repo.insertItems(
                ShoppingCartItemModel(
                    service.id.toString(),
                    service.name,
                    numOfItems * service.price,
                    category.categoryId,
                    category.title,
                    numOfItems
                )
            )
        }
    }

}