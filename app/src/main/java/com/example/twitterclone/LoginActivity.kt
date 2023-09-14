package com.example.twitterclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        val user = firebaseAuth.currentUser?.uid
        user?.let {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    // called when button is clicked
    fun onLogin(v: View){
        var proceed = true
        if(emailET.text.isNullOrEmpty()){
            emailTIL.error = "Email is required"
            emailTIL.isErrorEnabled = true
            proceed = false
        }
        if(passwordET.text.isNullOrEmpty()) {
            passwordTIL.error = "Password is required"
            passwordTIL.isErrorEnabled = true
            proceed = false
        }
        if(proceed) {
            loginProgressLayout.visibility = View.VISIBLE
            firebaseAuth.signInWithEmailAndPassword(
                emailET.text.toString(),
                passwordET.text.toString()
            )
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        loginProgressLayout.visibility = View.GONE
                        Toast.makeText(
                            this@LoginActivity,
                            "Login error: ${task.exception?.localizedMessage}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                    loginProgressLayout.visibility = View.GONE
                }
            }
        }

    fun goToSignup(v: View) {

    }
}