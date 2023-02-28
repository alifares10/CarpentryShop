package com.example.carpentryshop

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ReturnThis
import androidx.appcompat.app.AlertDialog
import com.example.carpentryshop.databinding.ActivityFinishedProjBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class FinishedProjActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivityFinishedProjBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fireBaseDataBase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finished_proj)
        auth = FirebaseAuth.getInstance()
        binding = ActivityFinishedProjBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonBackToFinshed.setOnClickListener {
            val intent = Intent(this,ProjectsActivity::class.java)
            startActivities(arrayOf(intent))
        }
        binding.buttonRemoveFinProj.setOnClickListener {
            val username = binding.editTextFiniProjName.text.toString()
            deleteRecord(username)
        }
        binding.buttonUpdateProj.setOnClickListener {
            val usernameUpdate = binding.editTextFiniProjName.text.toString()
            val uid = auth.currentUser?.uid
            val databaseReference = FirebaseDatabase.getInstance().getReference("Users/$uid/projects")
            databaseReference.child(usernameUpdate).get().addOnSuccessListener {
                if(it.exists()){
                    openUpdateDialog(usernameUpdate)
                }else{
                    Toast.makeText(this,"Project Not Found", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun openUpdateDialog(usernameUpdate: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_proj,null)
        mDialog.setView(mDialogView)
        val cusname = mDialogView.findViewById<EditText>(R.id.editTextUpdateProjName)
        val cusphone = mDialogView.findViewById<EditText>(R.id.editTextUpdateProjPhone)
        val projlocation = mDialogView.findViewById<EditText>(R.id.editTextUpdateProjLoc)
        val projdate = mDialogView.findViewById<EditText>(R.id.editTextUpdateProjDate)
        val projdesc = mDialogView.findViewById<EditText>(R.id.editTextUpdateProjDesc)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.buttonUpdateOk)
        val btnCancelUpdate = mDialogView.findViewById<Button>(R.id.buttonCancelUpdate)
        mDialog.setTitle("Updating Project : $usernameUpdate")
        val alertDialog = mDialog.create()
        alertDialog.show()
        btnUpdateData.setOnClickListener{
            updateProData(usernameUpdate,cusname.text.toString(),cusphone.text.toString(),projlocation.text.toString(),projdate.text.toString(),projdesc.text.toString())
            alertDialog.dismiss()
        }
        btnCancelUpdate.setOnClickListener {
            alertDialog.dismiss()
        }


    }

    private fun updateProData(usernameUpdate: String, name: String, phone: String, location: String, date: String, desc: String) {
        val uid = auth.currentUser?.uid
        val databaseReference = FirebaseDatabase.getInstance().getReference("Users/$uid/projects")
        val projects = mapOf<String,String>(
            "customerName" to name,
            "customerPhone" to phone,
            "Location" to location,
            "Date" to date,
            "ProjDesc" to desc
        )
        databaseReference.child(usernameUpdate).removeValue()
        databaseReference.child(name).updateChildren(projects)
        Toast.makeText(this,"Project Updated", Toast.LENGTH_SHORT)
            .show()

    }

    private fun deleteRecord(id: String) {

        val uid = auth.currentUser?.uid
        val databaseReference = FirebaseDatabase.getInstance().getReference("Users/$uid/projects")
        databaseReference.child(id).get().addOnSuccessListener {
            if (it.exists()){
                databaseReference.child(id).removeValue()
                Toast.makeText(this,"Project Deleted Successfully", Toast.LENGTH_SHORT)
                    .show()
            }else{
                Toast.makeText(this,"Project Not Found", Toast.LENGTH_SHORT)
                    .show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Problem Occurred", Toast.LENGTH_SHORT)
                .show()
        }

    }
}