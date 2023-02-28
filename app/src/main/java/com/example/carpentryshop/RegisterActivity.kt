package com.example.carpentryshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.carpentryshop.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    companion object {
        var globalVar: String = ""
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        val loginText: TextView = findViewById(R.id.textView_Login_Here)
        loginText.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivities(arrayOf(intent))

        }

        binding.buttonRegister.setOnClickListener {

            performSignUp()
        }

    }

    private fun performSignUp() {
        val user = findViewById<EditText>(R.id.editTextUserName_Register)
        val pass = findViewById<EditText>(R.id.editTextPassword_Register)
        if (user.text.isEmpty() || pass.text.isEmpty()){
            Toast.makeText(this,"Please Fill All Fields", Toast.LENGTH_SHORT)
                .show()
            return
        }
        val inputUser = user.text.toString()
        val inputPass = pass.text.toString()
        auth.createUserWithEmailAndPassword(inputUser,inputPass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, Add User To Database And Move to Main
                    val uid = auth.currentUser?.uid
                    val email = binding.editTextUserNameRegister.text.toString()
                    val name = binding.editTextTextPersonName.text.toString()
                    val users = Users(email,name)
                    if(uid != null){
                        databaseReference.child(uid).setValue(users).addOnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(this@RegisterActivity,"Saved To Database",Toast.LENGTH_SHORT).show()

                            }else{
                                Toast.makeText(this@RegisterActivity,"Failed to save info",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    val intent = Intent(this,MainActivity::class.java)
                    startActivities(arrayOf(intent))

                    Toast.makeText(baseContext, "Success.",
                        Toast.LENGTH_SHORT).show()

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this,"Error Occurred ${it.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
            }

    }
}