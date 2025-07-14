package com.example.proyectoilerna

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.properties.Delegates

class SignupActivity : AppCompatActivity() {

    companion object {
        lateinit var userEmail: String
        lateinit var providerSession: String
    }

    private var nombre by Delegates.notNull<String>()
    private var email by Delegates.notNull<String>()
    private var password by Delegates.notNull<String>()
    private lateinit var etNombre: EditText
    private lateinit var etEmail:EditText
    private lateinit var etPassword:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etNombre = findViewById(R.id.etNombre)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
    }

    /***
     * Funciones para movernos entre activities
     */
    fun goTerm(v: View) {
        val intent = Intent(this, TermsActivity::class.java)
        startActivity(intent)
    }
    fun volverLogin(v: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    private fun goHome(email: String, provider: String) {
        userEmail = email
        providerSession = provider

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    /***
     * Función para ingresar un nuevo usuario
     */
    fun signup(v: View) {
        signupUser()
    }
    private fun signupUser() {
        nombre = etNombre.text.toString()
        email = etEmail.text.toString()
        password = etPassword.text.toString()

        if(nombre.trim() != "" && email.trim() != "" && password.trim() != "") {
            val cbAccept = findViewById<CheckBox>(R.id.cbAccept)
            if(cbAccept.isChecked) register()
            else Toast.makeText(this, "Acepta los términos y condiciones de uso por favor", Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(this, "Rellena todos los campos por favor", Toast.LENGTH_SHORT).show()
    }
    private fun register () {
        // Firebase se encarga de validar y registrar email + password en la pestaña de 'Authentication'.
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    val dbRegister = FirebaseFirestore.getInstance()
                    dbRegister.collection("users").document(email).set(hashMapOf(
                        "email" to email,
                        "name" to nombre,
                        "metersMax" to 0,
                        "difficultyMax" to "",
                        "totalRoutes" to 0
                    ))
                    goHome(email, "email")
                    Toast.makeText(this, "Registrado con éxito", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this, "Error en el registro, comprueba que el email sea correcto", Toast.LENGTH_SHORT).show()
                }
            }
    }


    /***
     * Signup con Google. Consultar "Apartado 10 - Vías futuras"
     */
    fun callSignInGoogle (v: View) {
        signInGoogle()
    }
    private fun signInGoogle () {
        // TODO
        /**
         * Aquí implementaríamos un código para crear nuevo usuario mediante Google
         * Consultar "Apartado 10 - Vías futuras".
         */
    }


    /***
     * Signup con Facebook. Consultar "Apartado 10 - Vías futuras"
     */
    fun callSignInFacebook(v: View) {
        signInFacebook()
    }
    private fun signInFacebook() {
        // TODO
        /***
         * Aquí implementaríamos un código para crear nuevo usuario mediante Facebook
         * Consultar "Apartado 10 - Vías futuras".
         */
    }
}