package com.rviannaoliveira.vchatfirebase

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class SignInActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var signUp: Button
    private lateinit var signIn: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        email = findViewById(R.id.email) as EditText
        password = findViewById(R.id.password) as EditText
        signUp = findViewById(R.id.signup) as Button
        signIn = findViewById(R.id.signin) as Button
        auth = FirebaseAuth.getInstance()


        signUp.setOnClickListener({
            if(TextUtils.isEmpty(email.text.toString()) && TextUtils.isEmpty(password.text.toString())){
                Toast.makeText(this,"error",Toast.LENGTH_LONG).show()
            }else{
                auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                        .addOnCompleteListener(this, { task ->
                            if (task.isSuccessful) {
                                showMainActivity()
                            }else{
                                Toast.makeText(this,task.exception.toString(), Toast.LENGTH_LONG).show()
                            }
                        })
            }
        })

        signIn.setOnClickListener({
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this, { task ->
                        if (task.isSuccessful) {
                            showMainActivity()
                        }else{
                            Toast.makeText(this,task.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    })
        })
    }

    private fun showMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}
