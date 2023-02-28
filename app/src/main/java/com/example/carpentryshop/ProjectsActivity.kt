package com.example.carpentryshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carpentryshop.databinding.ActivityMainBinding
import com.example.carpentryshop.databinding.ActivityProjectsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProjectsActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivityProjectsBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projects)
        binding = ActivityProjectsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        binding.buttonViewProj.setOnClickListener {
            val intent = Intent(this,ViewProjectsActivity::class.java)
            startActivities(arrayOf(intent))
        }
        binding.buttonAddProj.setOnClickListener {
            val intent = Intent(this,AddProjActivity::class.java)
            startActivities(arrayOf(intent))
        }
        binding.buttonRemoveProj.setOnClickListener {
            val intent = Intent(this,FinishedProjActivity::class.java)
            startActivities(arrayOf(intent))
        }

        binding.buttonMainMenu.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivities(arrayOf(intent))
        }


    }
}