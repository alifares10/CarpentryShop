package com.example.carpentryshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carpentryshop.databinding.ActivityInventoryBinding
import com.example.carpentryshop.databinding.ActivityProjectsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class InventoryActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivityInventoryBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)
        auth = FirebaseAuth.getInstance()
        binding = ActivityInventoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonViewInventory.setOnClickListener {
            val intent = Intent(this,ViewInventoryActivity::class.java)
            startActivities(arrayOf(intent))
        }
        binding.buttonAddItemsInv.setOnClickListener {
            val intent = Intent(this,AddInventoryActivity::class.java)
            startActivities(arrayOf(intent))
        }
        binding.buttonRemoveItemsInv.setOnClickListener {
            val intent = Intent(this,RemoveInventoryActivity::class.java)
            startActivities(arrayOf(intent))
        }
        binding.buttonMainMenuInv.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivities(arrayOf(intent))
        }


    }
}