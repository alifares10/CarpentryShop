package com.example.carpentryshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carpentryshop.databinding.ActivityProjectsBinding
import com.example.carpentryshop.databinding.ActivityViewProjectsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import javax.security.auth.login.LoginException

class ViewProjectsActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivityViewProjectsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fireBaseDataBase: FirebaseDatabase
    private lateinit var projRecyclerView: RecyclerView
    private lateinit var projArrayList: ArrayList<Projects>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_projects)
        binding = ActivityViewProjectsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        projRecyclerView = findViewById(R.id.userList)
        projRecyclerView.layoutManager = LinearLayoutManager(this)
        projRecyclerView.setHasFixedSize(true)
        projArrayList = arrayListOf<Projects>()
        getProjData()
        binding.buttonBackToProj.setOnClickListener {
            val intent = Intent(this,ProjectsActivity::class.java)
            startActivities(arrayOf(intent))
        }
    }


    private fun getProjData() {
        val uid = auth.currentUser?.uid.toString()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users/$uid/projects")
        databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (projSnapshot in snapshot.children){
                        val proj = projSnapshot.getValue(Projects::class.java)
                        projArrayList.add(proj!!)
                    }
                    projRecyclerView.adapter = ViewProjAdapter(projArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
}