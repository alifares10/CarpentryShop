package com.example.carpentryshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carpentryshop.databinding.ActivityViewProjectsBinding
import com.example.carpentryshop.databinding.ActivityViewWorkersBinding
import com.example.carpentryshop.databinding.ActivityWorkersBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ViewWorkersActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivityViewWorkersBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fireBaseDataBase: FirebaseDatabase
    private lateinit var workRecyclerView: RecyclerView
    private lateinit var workArrayList: ArrayList<Workers>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_workers)
        binding = ActivityViewWorkersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        workRecyclerView = findViewById(R.id.workersList)
        workRecyclerView.layoutManager = LinearLayoutManager(this)
        workRecyclerView.setHasFixedSize(true)
        workArrayList = arrayListOf<Workers>()
        getWorkData()


        binding.buttonBackToWorkers.setOnClickListener {
            val intent = Intent(this,WorkersActivity::class.java)
            startActivities(arrayOf(intent))
        }

    }

    private fun getWorkData() {
        val uid = auth.currentUser?.uid.toString()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users/$uid/workers")
        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (workSnapshot in snapshot.children){
                        val work = workSnapshot.getValue(Workers::class.java)
                        workArrayList.add(work!!)
                    }
                    workRecyclerView.adapter = ViewWorkersAdapter(workArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
}