package com.example.twitterclone

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }
    fun onSignup(v: View){

    }
    // hook up signup activity to login
    fun goToLogin(v: View){
        startActivity(LoginActivity.newIntent(this))
        finish()
    }
    companion object {
        fun newIntent(context: Context) = Intent(context, SignupActivity::class.java)
    }
}