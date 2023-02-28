package com.example.carpentryshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.carpentryshop.databinding.ActivityAddProjBinding
import com.example.carpentryshop.databinding.ActivityAddWorkersBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddWorkersActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivityAddWorkersBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fireBaseDataBase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_workers)
        binding = ActivityAddWorkersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users/$uid/workers")
        binding.buttonAddWork.setOnClickListener {
            val name = binding.editTextWorkerName.text.toString()
            val workerID = binding.editTextWorkerID.text.toString()
            val phone = binding.editTextWorkerPhone.text.toString()
            val age = binding.editTextWorkerAge.text.toString()
            val hourlySalary = binding.editTextWorkerSal.text.toString()
            val workers = Workers(name,workerID,phone,age,hourlySalary)
            databaseReference.child(name).setValue(workers).addOnSuccessListener {
                binding.editTextWorkerName.text.clear()
                binding.editTextWorkerID.text.clear()
                binding.editTextWorkerPhone.text.clear()
                binding.editTextWorkerAge.text.clear()
                binding.editTextWorkerSal.text.clear()
                Toast.makeText(this,"Worker Added Successfully", Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener {
                Toast.makeText(this,"Problem Occurred", Toast.LENGTH_SHORT)
                    .show()
            }


        }


        binding.buttonBackWork.setOnClickListener {
            val intent = Intent(this,WorkersActivity::class.java)
            startActivities(arrayOf(intent))
        }
    }
}