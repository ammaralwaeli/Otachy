package com.haytham.coder.otchy.adapters.recyclerAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.srit.otachy.database.models.CategotyModel
import com.srit.otachy.database.models.ServiceModel
import com.srit.otachy.database.models.UserCategories
import com.srit.otachy.databinding.ItemCatigoryBinding
import com.srit.otachy.databinding.ItemGovernmentBinding
import com.srit.otachy.databinding.ItemServiceBinding


class ServiceRecyclerAdapter(private val dataList:List<ServiceModel> ) :
    RecyclerView.Adapter<ServiceRecyclerAdapter.MyViewHolder>() {
    private var listener: ItemListener? = null

    class MyViewHolder(private val binding: ItemServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userModel: ServiceModel) {
            binding.serviceItemModel = userModel
            binding.hasPendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        fun onItemClick(itemModel: ServiceModel)
    }

    fun setItemListener(listener: ItemListener) {
        this.listener = listener
    }



}


