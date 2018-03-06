package com.example.max.snaphat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import android.widget.Toast

import com.google.firebase.auth.FirebaseUser

import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import android.R.attr.password
import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.util.Log


class MainActivity : AppCompatActivity() {
    var emailEditText: EditText? = null
    var passwordEditText: EditText? = null
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        if (mAuth.currentUser != null){
            logIn()
        }
    }

    fun goClicked(view: View){
        //check if we an log in the user
        mAuth.createUserWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        logIn()
                    } else {
                        //sign up user
                        mAuth.createUserWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString())
                                .addOnCompleteListener(this) { task ->
                                    if (task.isSuccessful){
                                //Add to database
                                        logIn()
                                    } else {
                                        Toast.makeText(this, "Login failed. Try again.", Toast.LENGTH_SHORT).show()
                                    }
                                }

                    }

                    // ...
                }

    }

    fun logIn(){
        //move to next Activity
        val intent = Intent(this, SnapsActivity::class.java)
        startActivity(intent)
    }
}
