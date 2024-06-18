package com.example.practica02_alvarez_martinezg

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val etEmailLogin: EditText = findViewById(R.id.etEmailLogin)
        val etPasswordLogin: EditText = findViewById(R.id.etPasswordLogin)
        val btLogin: Button = findViewById(R.id.btLogin)
        val btRegister: Button = findViewById(R.id.btRegister)
        val auth =  FirebaseAuth.getInstance()

        btRegister.setOnClickListener {
            startActivity(Intent(this, RegisterUserActivity::class.java))
        }

        btLogin.setOnClickListener {
            val email = etEmailLogin.text.toString()
            val password = etPasswordLogin.text.toString()

            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){task->
                    if(task.isSuccessful){
                        Snackbar.make(
                            findViewById(android.R.id.content)
                            , "Ingreso satisfactorio"
                            , Snackbar.LENGTH_LONG
                        ).show()
                        startActivity(
                            Intent(this
                            , MainActivity::class.java)
                        )
                    }else{
                        Snackbar.make(
                            findViewById(android.R.id.content)
                            , "Credenciales inválidas"
                            , Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}