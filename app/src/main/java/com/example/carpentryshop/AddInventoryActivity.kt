package com.example.carpentryshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.carpentryshop.databinding.ActivityAddInventoryBinding
import com.example.carpentryshop.databinding.ActivityInventoryBinding
import com.example.carpentryshop.databinding.ActivityViewInventoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddInventoryActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivityAddInventoryBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fireBaseDataBase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_inventory)
        binding = ActivityAddInventoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users/$uid/inventory")
        binding.buttonAddNewItem.setOnClickListener {
            val itemName = binding.editTextItemName.text.toString()
            val itemPrice = binding.editTextItemPrice.text.toString()
            val itemQuantity = binding.editTextItemQuantity.text.toString()
            val itemPurDate = binding.editTextItemDate.text.toString()
            val itemWarranty = binding.editTextItemWarranty.text.toString()
            val items = Inventory(itemName,itemPrice,itemQuantity,itemPurDate,itemWarranty)
            databaseReference.child(itemName).setValue(items).addOnSuccessListener {
                binding.editTextItemName.text.clear()
                binding.editTextItemPrice.text.clear()
                binding.editTextItemQuantity.text.clear()
                binding.editTextItemDate.text.clear()
                binding.editTextItemWarranty.text.clear()
                Toast.makeText(this,"Item Added Successfully", Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener {
                Toast.makeText(this,"Problem Occurred", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.buttonAddInvBack.setOnClickListener {
            val intent = Intent(this, InventoryActivity::class.java)
            startActivities(arrayOf(intent))

        }
    }
}