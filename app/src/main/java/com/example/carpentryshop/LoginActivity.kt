package com.example.carpentryshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth


        val registerText: TextView = findViewById(R.id.textView_Register_Here)
        registerText.setOnClickListener{
            val intent = Intent(this,RegisterActivity::class.java)
            startActivities(arrayOf(intent))
        }

        val loginButton: Button = findViewById(R.id.button_login)
        loginButton.setOnClickListener {
            performLogin()
        }

    }

    private fun performLogin() {
        val email: EditText = findViewById(R.id.editTextUserName_Login)
        val pass: EditText = findViewById(R.id.editTextPassword_Login)
        if (email.text.isEmpty() || pass.text.isEmpty()){
            Toast.makeText(this,"Please Fill All Fields", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val emailInput = email.text.toString()
        val passInput = pass.text.toString()

        auth.signInWithEmailAndPassword(emailInput, passInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, Move to Main
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