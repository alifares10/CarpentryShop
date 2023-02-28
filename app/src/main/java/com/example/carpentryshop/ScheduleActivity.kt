package com.example.carpentryshop

import android.content.Intent
import android.hardware.biometrics.BiometricManager.Strings
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import com.example.carpentryshop.databinding.ActivityInventoryBinding
import com.example.carpentryshop.databinding.ActivityScheduleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ScheduleActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivityScheduleBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var calendarView: CalendarView
    private lateinit var datesMap: MutableMap<String,String>
    var year: Int = 0
    var month: Int = 0
    var day: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)
        auth = FirebaseAuth.getInstance()
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var currentDate: String
        calendarView = findViewById(R.id.calendarView)
        datesMap = createDates()
        calendarView.setOnDateChangeListener { calendarView, i, i2, i3 ->
            year = i
            month = i2+1
            day = i3
            currentDate = "$day.$month.$year"
            for (x in datesMap){
                if (x.key == currentDate){
                    binding.textView40.text = "Project Name : ${x.value} "
                    break

                }else{
                    binding.textView40.text = "No Projects"
                }
            }
        }

        binding.buttonMainMenuShed.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivities(arrayOf(intent))
        }

    }

    private fun createDates(): MutableMap<String, String> {
        var projDate: String
        var projName: String
        datesMap = mutableMapOf<String,String>()
        val uid = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users/$uid/projects")
        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (projSnapshot in snapshot.children){
                        projDate = projSnapshot.child("Date").value.toString()
                        projName = projSnapshot.child("customerName").value.toString()
                        datesMap[projDate] = projName
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return datesMap
    }


}
