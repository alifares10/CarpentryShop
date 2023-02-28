package com.example.carpentryshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.carpentryshop.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.carpentryshop.RegisterActivity.Companion.globalVar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fireBaseDataBase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        fireBaseDataBase = FirebaseDatabase.getInstance()
        binding.buttonProjects.setOnClickListener {
            val intent = Intent(this,ProjectsActivity::class.java)
            startActivities(arrayOf(intent))
        }
        binding.buttonInventory.setOnClickListener {
            val intent = Intent(this,InventoryActivity::class.java)
            startActivities(arrayOf(intent))
        }
        binding.buttonSchedule.setOnClickListener {
            val intent = Intent(this,ScheduleActivity::class.java)
            startActivities(arrayOf(intent))
        }
        binding.buttonWorkers.setOnClickListener {
            val intent = Intent(this,WorkersActivity::class.java)
            startActivities(arrayOf(intent))
        }
        binding.buttonShopInfo.setOnClickListener {
            val intent = Intent(this,ShopInfoActivity::class.java)
            startActivities(arrayOf(intent))
        }
    }

}