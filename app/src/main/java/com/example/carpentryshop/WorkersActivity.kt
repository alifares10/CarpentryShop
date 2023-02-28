package com.example.carpentryshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carpentryshop.databinding.ActivityInventoryBinding
import com.example.carpentryshop.databinding.ActivityWorkersBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class WorkersActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivityWorkersBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workers)
        auth = FirebaseAuth.getInstance()
        binding = ActivityWorkersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonViewWorkers.setOnClickListener {
            val intent = Intent(this,ViewWorkersActivity::class.java)
            startActivities(arrayOf(intent))
        }
        binding.buttonAddWorkers.setOnClickListener {
            val intent = Intent(this,AddWorkersActivity::class.java)
            startActivities(arrayOf(intent))
        }
        binding.buttonRemoveWorker.setOnClickListener {
            val intent = Intent(this,RemoveWorkerActivity::class.java)
            startActivities(arrayOf(intent))
        }
        binding.buttonMainMenu.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivities(arrayOf(intent))
        }

    }
}