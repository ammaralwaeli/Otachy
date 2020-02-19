package com.srit.otachy.adapters.recyclerAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView

import com.srit.otachy.database.models.UserModel
import com.srit.otachy.database.models.VendorModel
import com.srit.otachy.databinding.ItemVendorBinding


class VendorRecyclerAdapter(private val dataList:List<VendorModel> ) :
    RecyclerView.Adapter<VendorRecyclerAdapter.MyViewHolder>(), Filterable {
    private var listener: ItemListener? = null

    var filterListSchools:List<VendorModel>
    init {
        this.filterListSchools=dataList
    }
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

    override fun getItemCount() = filterListSchools.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val selectedItem = filterListSchools[position]


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

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val str=constraint.toString()
                com.orhanobut.logger.Logger.d("mySearch $str")
                if(str.isEmpty()){
                    filterListSchools=dataList
                }else{
                    val result=ArrayList<VendorModel>()
                    for(row in dataList){

                        if(row.district.contains(str)){

                            result.add(row)
                        }
                        filterListSchools=result
                    }
                }
                val filterResults=FilterResults()
                filterResults.values=filterListSchools
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

            }

        }
    }
}


