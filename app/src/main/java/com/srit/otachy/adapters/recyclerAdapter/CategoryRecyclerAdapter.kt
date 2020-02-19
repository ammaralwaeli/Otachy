package com.srit.otachy.adapters.recyclerAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.srit.otachy.database.models.CategotyModel
import com.srit.otachy.database.models.UserCategories
import com.srit.otachy.databinding.ItemCatigoryBinding
import com.srit.otachy.databinding.ItemGovernmentBinding


class CategoryRecyclerAdapter(private val dataList:List<UserCategories> ) :
    RecyclerView.Adapter<CategoryRecyclerAdapter.MyViewHolder>() {
    private var listener: ItemListener? = null

    class MyViewHolder(private val binding: ItemCatigoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userModel: UserCategories) {
            binding.categoryItemModel = userModel
            binding.hasPendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemCatigoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        fun onItemClick(itemModel: UserCategories)
    }

    fun setItemListener(listener: ItemListener) {
        this.listener = listener
    }



}


