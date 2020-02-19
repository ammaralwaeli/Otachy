package com.srit.otachy.adapters.recyclerAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.srit.otachy.databinding.ItemGovernmentBinding


class GovernmentRecyclerAdapter(private val dataList:List<String> ) :
    RecyclerView.Adapter<GovernmentRecyclerAdapter.MyViewHolder>() {
    private var listener: ItemListener? = null

    class MyViewHolder(private val binding: ItemGovernmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userModel: String) {
            binding.gov = userModel
            binding.hasPendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemGovernmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        fun onItemClick(itemModel: String)
    }

    fun setItemListener(listener: ItemListener) {
        this.listener = listener
    }



}


