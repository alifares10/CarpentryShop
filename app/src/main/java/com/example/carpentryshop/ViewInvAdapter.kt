package com.example.carpentryshop

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

 class ViewInvAdapter(private val itemsList : ArrayList<Inventory>) :
     RecyclerView.Adapter<ViewInvAdapter.MyViewInvHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewInvHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_inventory,parent,false)
        return MyViewInvHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: MyViewInvHolder, position: Int) {
        val currentItem = itemsList[position]
        holder.itemName.text = currentItem.itemName.toString()
        holder.itemPrice.text = currentItem.itemPrice.toString()
        holder.itemQuantity.text = currentItem.itemQuantity.toString()
        holder.itemDate.text = currentItem.itemPurDate.toString()
        holder.itemWarranty.text = currentItem.itemWarranty.toString()
    }

     class MyViewInvHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
         val itemName: TextView = itemView.findViewById(R.id.text_itemName)
         val itemPrice: TextView = itemView.findViewById(R.id.text_ItemPrice)
         val itemQuantity: TextView = itemView.findViewById(R.id.text_itemQuantity)
         val itemDate: TextView = itemView.findViewById(R.id.text_itemDate)
         val itemWarranty: TextView = itemView.findViewById(R.id.text_itemWarranty)
     }
}