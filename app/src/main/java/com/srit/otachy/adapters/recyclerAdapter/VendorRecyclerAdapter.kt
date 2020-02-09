package com.haytham.coder.otchy.adapters.recyclerAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.srit.otachy.database.models.UserModel
import com.srit.otachy.database.models.VendorModel
import com.srit.otachy.databinding.ItemVendorBinding


class VendorRecyclerAdapter(private val dataList:List<VendorModel> ) :
    RecyclerView.Adapter<VendorRecyclerAdapter.MyViewHolder>() {
    private var listener: ItemListener? = null


    class MyViewHolder(private val binding: ItemVendorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userModel: VendorModel) {
            binding.item = userModel
            binding.hasPendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemVendorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        fun onItemClick(itemModel: VendorModel)
    }

    fun setItemListener(listener: ItemListener) {
        this.listener = listener
    }

}


