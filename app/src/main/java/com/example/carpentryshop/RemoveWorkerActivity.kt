package com.example.carpentryshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.carpentryshop.databinding.ActivityRemoveInventoryBinding
import com.example.carpentryshop.databinding.ActivityRemoveWorkerBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RemoveWorkerActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivityRemoveWorkerBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fireBaseDataBase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remove_worker)
        binding = ActivityRemoveWorkerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.buttonRemoveWorker.setOnClickListener {
            val workerName = binding.editTextRemoveWorkerName.text.toString()
            removeWorker(workerName)
        }
        binding.buttonRemoveWorkerBack.setOnClickListener {
            val intent = Intent(this,WorkersActivity::class.java)
            startActivities(arrayOf(intent))
        }
        binding.buttonUpdateWorker.setOnClickListener {
            val workerUpdate = binding.editTextRemoveWorkerName.text.toString()
            val uid = auth.currentUser?.uid
            val databaseReference = FirebaseDatabase.getInstance().getReference("Users/$uid/workers")
            databaseReference.child(workerUpdate).get().addOnSuccessListener {
                if(it.exists()){
                    openUpdateDialog(workerUpdate)
                }else{
                    Toast.makeText(this,"Worker Not Found", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }

    private fun openUpdateDialog(workerUpdate: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_workers,null)
        mDialog.setView(mDialogView)
        val workerNewSal = mDialogView.findViewById<EditText>(R.id.editTextUpdateWorkSal)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.buttonUpdateWorkOk)
        val btnCancelUpdate = mDialogView.findViewById<Button>(R.id.buttonUpdateSalCancel)
        mDialog.setTitle("Updating $workerUpdate Salary ")
        val alertDialog = mDialog.create()
        alertDialog.show()
        btnUpdateData.setOnClickListener{
            updateItemData(workerUpdate,workerNewSal.text.toString())
            alertDialog.dismiss()
        }
        btnCancelUpdate.setOnClickListener {
            alertDialog.dismiss()
        }

    }

    private fun updateItemData(workerUpdate: String, newSal: String) {
        val uid = auth.currentUser?.uid
        val databaseReference = FirebaseDatabase.getInstance().getReference("Users/$uid/workers").child(workerUpdate)
        databaseReference.child("hourlySalary").setValue(newSal).addOnSuccessListener {
            Toast.makeText(this,"Worker Salary Updated", Toast.LENGTH_SHORT)
                .show()
        }.addOnFailureListener {
            Toast.makeText(this,"Update Problem Occurred", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun removeWorker(workerName: String) {
        val uid = auth.currentUser?.uid
        val databaseReference = FirebaseDatabase.getInstance().getReference("Users/$uid/workers")
        databaseReference.child(workerName).get().addOnSuccessListener {
            if(it.exists()){
                databaseReference.child(workerName).removeValue()
                Toast.makeText(this,"Worker Removed", Toast.LENGTH_SHORT)
                    .show()
            }else{
                Toast.makeText(this,"Worker Not Found", Toast.LENGTH_SHORT)
                    .show()
            }
        }.addOnFailureListener {
            Toast.makeText(this,"Problem Occurred", Toast.LENGTH_SHORT)
                .show()
        }

    }
}