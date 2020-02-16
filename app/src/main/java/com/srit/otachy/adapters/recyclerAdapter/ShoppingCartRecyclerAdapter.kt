package com.haytham.coder.otchy.adapters.recyclerAdapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.srit.otachy.database.models.ShoppingCartItemModel
import com.srit.otachy.databinding.ItemShoppingCartBinding


class ShoppingCartRecyclerAdapter:
    RecyclerView.Adapter<ShoppingCartRecyclerAdapter.MyViewHolder>() {
    var dataList:List<ShoppingCartItemModel> = ArrayList()
    private var listener: ItemListener? = null

    class MyViewHolder(val binding: ItemShoppingCartBinding) :
        RecyclerView.ViewHolder(binding.root){


        fun bind(shoppingCartModel: ShoppingCartItemModel){
            binding.shoppingCartModel= shoppingCartModel
            binding.hasPendingBindings()
        }
    }


    fun updateData(data:List<ShoppingCartItemModel>){
        dataList= data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding= ItemShoppingCartBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)


        return MyViewHolder(
            binding
        )
    }

    override fun getItemCount()= dataList.size



    fun getItemsCount(): Int{
        var count=0
        for(item in dataList){
            count += item.numberOfItems.toInt()
        }
        return count
    }

    fun getTotalPrice():Double{
        var count=0.0
        for(item in dataList){
            count += item.totalPrice
        }
        return count
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int){
        val item= dataList[position]
        holder.bind(item)

        holder.binding.deleteBtn.setOnClickListener {
            listener?.let {
                it.onItemDelete(item)
                dataList.drop(position)
                notifyItemRemoved(position)
                notifyItemChanged(position)
            }
        }

        holder.binding.orderBtn.setOnClickListener {
            listener?.let {
                it.onItemOrder(item)
            }
        }
    }

    interface ItemListener {
        fun onItemDelete(itemModel: ShoppingCartItemModel)
        fun onItemOrder(itemModel: ShoppingCartItemModel)
    }

    fun setItemListener(listener: ItemListener) {
        this.listener = listener
    }

}
