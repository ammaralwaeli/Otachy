package com.haytham.coder.otchy.adapters.recyclerAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView

import com.srit.otachy.database.models.UserModel
import com.srit.otachy.database.models.VendorModel
import com.srit.otachy.database.models.VendorShopModel
import com.srit.otachy.databinding.ItemVendorBinding
import com.srit.otachy.databinding.ItemVendorShopBinding


class VendorShopRecyclerAdapter(private val dataList:List<VendorShopModel> ) :
    RecyclerView.Adapter<VendorShopRecyclerAdapter.MyViewHolder>() {
    private var listener: ItemListener? = null

    class MyViewHolder(private val binding: ItemVendorShopBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(userModel: VendorShopModel) {
            binding.vendorShopModel = userModel
            binding.hasPendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemVendorShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(
            binding
        )
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val selectedItem = dataList[position]


        holder.bind(selectedItem)
        holder.itemView.setOnClickListener {
            listener?.onItemClick(selectedItem)
        }
    }


    interface ItemListener {
        fun onItemClick(itemModel: VendorShopModel)
    }

    fun setItemListener(listener: ItemListener) {
        this.listener = listener
    }

}


