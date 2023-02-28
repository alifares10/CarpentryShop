package com.example.carpentryshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carpentryshop.databinding.ActivityInventoryBinding
import com.example.carpentryshop.databinding.ActivityViewInventoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ViewInventoryActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivityViewInventoryBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fireBaseDataBase: FirebaseDatabase
    private lateinit var InvRecyclerView: RecyclerView
    private lateinit var itemArrayList: ArrayList<Inventory>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_inventory)
        binding = ActivityViewInventoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        InvRecyclerView = findViewById(R.id.inventoryList)
        InvRecyclerView.layoutManager = LinearLayoutManager(this)
        InvRecyclerView.setHasFixedSize(true)
        itemArrayList = arrayListOf<Inventory>()
        getInvData()

        binding.buttonViewInvBack.setOnClickListener {
            val intent = Intent(this,InventoryActivity::class.java)
            startActivities(arrayOf(intent))
        }
    }

    private fun getInvData() {
        val uid = auth.currentUser?.uid.toString()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users/$uid/inventory")
        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (itemsSnapshot in snapshot.children){
                        val items = itemsSnapshot.getValue(Inventory::class.java)
                        itemArrayList.add(items!!)
                    }
                    InvRecyclerView.adapter = ViewInvAdapter(itemArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
}