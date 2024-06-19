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

class RegisterUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etRmail: EditText = findViewById(R.id.etRmail)
        val etRpass: EditText = findViewById(R.id.etRpass)
        val btnRegisterU: Button = findViewById(R.id.btnRegisterU)
        val btVolver: Button = findViewById(R.id.btVolver)

        btVolver.setOnClickListener {
            //Volver a LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
        }

        //Registrar usuario, con email y password en firebase
        btnRegisterU.setOnClickListener {
            val email = etRmail.text.toString()
            val password = etRpass.text.toString()

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Snackbar.make(
                            findViewById(android.R.id.content)
                            , "Usuario registrado satisfactoriamente"
                            , Snackbar.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                    } else {
                        Snackbar.make(
                            findViewById(android.R.id.content)
                            , "Ha ocurrido un error: ${task.exception?.message}"
                            , Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
        }

    }
}