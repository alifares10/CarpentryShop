package com.example.carpentryshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.carpentryshop.databinding.ActivityInventoryBinding
import com.example.carpentryshop.databinding.ActivityRemoveInventoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RemoveInventoryActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivityRemoveInventoryBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fireBaseDataBase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remove_inventory)
        binding = ActivityRemoveInventoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.buttonRemoveItems.setOnClickListener {
            val itemName = binding.editTextRemoveItemName.text.toString()
            removeItem(itemName)
        }
        binding.buttonRemoveItemBack.setOnClickListener {
            val intent = Intent(this,InventoryActivity::class.java)
            startActivities(arrayOf(intent))
        }
        binding.buttonUpdateInv.setOnClickListener {
            val itemQuantUpdate = binding.editTextRemoveItemName.text.toString()
            val uid = auth.currentUser?.uid
            val databaseReference = FirebaseDatabase.getInstance().getReference("Users/$uid/inventory")
            databaseReference.child(itemQuantUpdate).get().addOnSuccessListener {
                if(it.exists()){
                    openUpdateDialog(itemQuantUpdate)
                }else{
                    Toast.makeText(this,"Item Not Found", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun openUpdateDialog(itemQuantUpdate: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_inv,null)
        mDialog.setView(mDialogView)
        val quantUpdate = mDialogView.findViewById<EditText>(R.id.editTextUpdateItemQuant)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.buttonUpdateInvOk)
        val btnCancelUpdate = mDialogView.findViewById<Button>(R.id.buttonUpdateInvCancel)
        mDialog.setTitle("Updating Item Quantity : $itemQuantUpdate")
        val alertDialog = mDialog.create()
        alertDialog.show()
        btnUpdateData.setOnClickListener{
            updateItemData(itemQuantUpdate,quantUpdate.text.toString())
            alertDialog.dismiss()
        }
        btnCancelUpdate.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    private fun updateItemData(itemQuantUpdate: String, quantUpdate: String) {
        val uid = auth.currentUser?.uid
        val databaseReference = FirebaseDatabase.getInstance().getReference("Users/$uid/inventory").child(itemQuantUpdate)
        databaseReference.child("itemQuantity").setValue(quantUpdate).addOnSuccessListener {
            Toast.makeText(this,"Item Quantity Updated", Toast.LENGTH_SHORT)
                .show()
        }.addOnFailureListener {
            Toast.makeText(this,"Update Problem Occurred", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private fun removeItem(itemName: String) {
        val uid = auth.currentUser?.uid
        val databaseReference = FirebaseDatabase.getInstance().getReference("Users/$uid/inventory")
        databaseReference.child(itemName).get().addOnSuccessListener {
            if(it.exists()){
                databaseReference.child(itemName).removeValue()
                Toast.makeText(this,"Item Removed From Inventory", Toast.LENGTH_SHORT)
                    .show()
            }else{
                Toast.makeText(this,"Item Not Found In Inventory", Toast.LENGTH_SHORT)
                    .show()
            }
        }.addOnFailureListener {
            Toast.makeText(this,"Problem Occurred", Toast.LENGTH_SHORT)
                .show()
        }

    }
}