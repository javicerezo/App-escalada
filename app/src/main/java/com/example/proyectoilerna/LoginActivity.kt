package com.example.proyectoilerna

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoilerna.SignupActivity.Companion.providerSession
import com.example.proyectoilerna.SignupActivity.Companion.userEmail
import com.google.firebase.auth.FirebaseAuth
import kotlin.properties.Delegates

class LoginActivity : AppCompatActivity() {
    private var email by Delegates.notNull<String>()
    private var password by Delegates.notNull<String>()
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        mAuth = FirebaseAuth.getInstance()
    }


    /***
     * Funciones para movernos entre activities
     */
    fun userNew(v: View) {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }

    /***
     * Comprueba si existe usuario logeado en memoria del dispositivo
     */
    public override fun onStart () {
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) goHome(currentUser.email.toString(), currentUser.providerId)
    }
    private fun goHome(email: String, provider: String) {
        userEmail = email
        providerSession = provider

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    /***
     * Impide que dando a botón atras del terminal desde login que vaya a Main
     * sin pasar por verificar un login, lo que hacemos es redirigirlo login.
     */
    @Deprecated("This method has been deprecated in favor of using the\n      " +
            "{@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      " +
            "The OnBackPressedDispatcher controls how back button events are dispatched\n      " +
            "to one or more {@link OnBackPressedCallback} objects.")
    // He intentado usar OnBackPressedDispatcher pero NO me compila
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
    }


    /***
     * Función para autenticar usuario
     */
    fun login(v: View) {
        loginUser()
    }
    private fun loginUser () {
        email = etEmail.text.toString()
        password = etPassword.text.toString()

        if(email.trim() != "" && password.trim() != "") {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) goHome(email, "email")
                    else Toast.makeText(this, "Error de usuario o contraseña", Toast.LENGTH_SHORT).show()
                }
        }
        else Toast.makeText(this, "Rellena los campos para logearte", Toast.LENGTH_SHORT).show()
    }


    /***
     *  Restablecer password
     */
    fun forgotPassword(v: View) {
        resetPassword()
    }
    private fun resetPassword () {
        // Firebase se encargará de restablecer password
        val e = etEmail.text.toString()
        if (!TextUtils.isEmpty(e)) {
            mAuth.sendPasswordResetEmail(e)
                .addOnCompleteListener {
                    if (it.isSuccessful) Toast.makeText(this, "Email enviado a $e", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(this, "No se encontró el usuario con este correo", Toast.LENGTH_SHORT).show()
            }
        }
        else Toast.makeText(this, "Indica un email válido", Toast.LENGTH_SHORT).show()
    }


    /***
     * Logeo mediante Google. Consultar "Apartado 10 - Vías futuras"
     */
    fun callLogInGoogle (v: View) {
        signInGoogle()
    }
    private fun signInGoogle () {
        // TODO
        /**
         * Aquí implementaríamos un código para logearse mediante Google
         * Consultar "Apartado 10 - Vías futuras".
         */
    }


    /***
     * Logeo con Facebook. Consultar "Apartado 10 - Vías futuras"
     */
    fun callLogInFacebook(v: View) {
        logInFacebook()
    }
    private fun logInFacebook() {
        // TODO
        /***
         * Aquí implementaríamos un código para logearse mediante Facebook
         * Consultar "Apartado 10 - Vías futuras".
         */
    }
}