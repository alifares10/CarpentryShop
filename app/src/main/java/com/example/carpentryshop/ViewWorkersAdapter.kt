package com.example.carpentryshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewWorkersAdapter(private val workersList : ArrayList<Workers>) : RecyclerView.Adapter<ViewWorkersAdapter.MyViewHolder>() {


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val workerName : TextView = itemView.findViewById(R.id.text_WorkerName)
        val workerID : TextView = itemView.findViewById(R.id.text_WorkerID)
        val workerPhone : TextView = itemView.findViewById(R.id.text_WorkerPhone)
        val workerDate : TextView = itemView.findViewById(R.id.text_WorkerAge)
        val workerSal : TextView = itemView.findViewById(R.id.text_WorkerSal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_workers,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return workersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = workersList[position]
        holder.workerName.text = currentItem.name.toString()
        holder.workerID.text = currentItem.workerID.toString()
        holder.workerPhone.text = currentItem.phone.toString()
        holder.workerDate.text = currentItem.age.toString()
        holder.workerSal.text = currentItem.hourlySalary.toString()
    }
}