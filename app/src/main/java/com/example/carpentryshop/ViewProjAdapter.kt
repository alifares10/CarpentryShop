package com.example.carpentryshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewProjAdapter(private val projList : ArrayList<Projects>) : RecyclerView.Adapter<ViewProjAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.projects_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return projList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = projList[position]
        holder.cusName.text = currentItem.customerName.toString()
        holder.cusPhone.text = currentItem.customerPhone.toString()
        holder.location.text = currentItem.Location.toString()
        holder.date.text = currentItem.Date.toString()
        holder.description.text = currentItem.ProjDesc.toString()

    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val cusName : TextView = itemView.findViewById(R.id.text_customerName)
        val cusPhone : TextView = itemView.findViewById(R.id.text_customerPhone)
        val location : TextView = itemView.findViewById(R.id.text_Location)
        val date : TextView = itemView.findViewById(R.id.text_Date)
        val description : TextView = itemView.findViewById(R.id.text_Description)
    }

}