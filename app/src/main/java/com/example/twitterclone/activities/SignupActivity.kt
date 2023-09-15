package com.example.twitterclone.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.twitterclone.activities.LoginActivity
import com.example.twitterclone.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.emailTIL
import kotlinx.android.synthetic.main.activity_signup.passwordET
import kotlinx.android.synthetic.main.activity_signup.passwordTIL
import com.example.twitterclone.utils.DATA_USERS
import com.example.twitterclone.utils.User

class SignupActivity : AppCompatActivity() {

    private val firebaseDB = FirebaseFirestore.getInstance()

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        val user = firebaseAuth.currentUser?.uid
        user?.let {
            startActivity(HomeActivity.newIntent(this))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        setTextChangeListener(emailET, emailTIL)
        setTextChangeListener(passwordET, passwordTIL)

        signupProgressLayout.setOnTouchListener { v, event -> true }
    }

    fun setTextChangeListener(et: EditText, til: TextInputLayout) {
        et.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                til.isErrorEnabled = false
            }

        })

    }
    fun onSignup(v: View){

        var proceed = true
        if(usernameET.text.isNullOrEmpty()){
            usernameTIL.error = "Username is required"
            usernameTIL.isErrorEnabled = true
            proceed = false
        }
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
            signupProgressLayout.visibility = View.VISIBLE
            firebaseAuth.createUserWithEmailAndPassword(
                emailET.text.toString(),
                passwordET.text.toString()
            )
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Toast.makeText(
                            this@SignupActivity,
                            "Signup error: ${task.exception?.localizedMessage}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else {
                        val email = emailET.text.toString()
                        val name = usernameET.text.toString()
                        val user = User(email, name, "", arrayListOf(), arrayListOf())
                        firebaseDB.collection(DATA_USERS).document(firebaseAuth.uid!!).set(user)
                    }

                    signupProgressLayout.visibility = View.GONE
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                    signupProgressLayout.visibility = View.GONE
                }
        }

    }
    // hook up signup activity to login
    fun goToLogin(v: View){
        startActivity(LoginActivity.newIntent(this))
        finish()
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener (firebaseAuthListener)
    }
    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(firebaseAuthListener)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, SignupActivity::class.java)
    }
}