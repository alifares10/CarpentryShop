package com.example.carpentryshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.carpentryshop.databinding.ActivityAddProjBinding
import com.example.carpentryshop.databinding.ActivityViewProjectsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Date

class AddProjActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivityAddProjBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fireBaseDataBase: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_proj)
        auth = FirebaseAuth.getInstance()
        binding = ActivityAddProjBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val uid = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users/$uid/projects")
        binding.buttonAddProject.setOnClickListener {
            val customerName = binding.editTextAddProjName.text.toString()
            val customerPhone = binding.editTextAddProjPhone.text.toString()
            val location = binding.editTextAddProjLocation.text.toString()
            val date = binding.editTextAddProjDate.text.toString()
            val projDescription = binding.editTextAddProjDesc.text.toString()
            val projects = Projects(customerName,customerPhone,location,date,projDescription)
            databaseReference.child(customerName).setValue(projects).addOnSuccessListener {
                binding.editTextAddProjName.text.clear()
                binding.editTextAddProjPhone.text.clear()
                binding.editTextAddProjLocation.text.clear()
                binding.editTextAddProjDate.text.clear()
                binding.editTextAddProjDesc.text.clear()
                Toast.makeText(this,"Project Added Successfully", Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener {
                Toast.makeText(this,"Problem Occurred", Toast.LENGTH_SHORT)
                    .show()

            }
        }
        binding.buttonBackToProjects.setOnClickListener {
            val intent = Intent(this,ProjectsActivity::class.java)
            startActivities(arrayOf(intent))
        }

    }
}