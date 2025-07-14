package com.example.proyectoilerna

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import classApp.Comment
import classApp.Route
import com.example.proyectoilerna.MainActivity.Companion.globalUser
import com.example.proyectoilerna.SignupActivity.Companion.userEmail
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class SettingsActivity : AppCompatActivity() {
    private lateinit var commentsArrayList: ArrayList<Comment>
    private lateinit var deleteCommentsArrayList: ArrayList<Comment>

    private lateinit var etName: EditText
    private lateinit var newName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initObjects()
    }
    override fun onPause() {
        super.onPause()
        commentsArrayList.clear()
        deleteCommentsArrayList.clear()
    }

    /***
     * Inicializa los menús desplegables pero recogidos
     */
    private fun initObjects() {
        val lyProfile = findViewById<LinearLayout>(R.id.ly_profile)
        lyProfile.visibility = View.GONE

        val lyLenguage = findViewById<LinearLayout>(R.id.ly_lenguage)
        lyLenguage.visibility = View.GONE

        val lyContact = findViewById<LinearLayout>(R.id.ly_contact)
        lyContact.visibility = View.GONE

        etName = findViewById(R.id.et_name)

        commentsArrayList = arrayListOf()
        deleteCommentsArrayList = arrayListOf()
    }


    /***
     * Volver a la activity Main
     */
    fun callMainActivity(view: View) {
        mainActivity()
    }
    private fun mainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    /***
     * cambia la visibilidad al clicar
     */
    fun callChangeVisibilityLinearLayout(view: View) {
        changeVisibilityLinearLayout(view)
    }
    private fun changeVisibilityLinearLayout(view: View) {
        val lyLinear: LinearLayout
        val tvProfile = findViewById<TextView>(R.id.tv_profile)
        val tvlenguage = findViewById<TextView>(R.id.tv_lenguage)
        val tvcontact = findViewById<TextView>(R.id.tv_contact)

        when(view.getId()){
            tvProfile.getId() -> {
                lyLinear = findViewById(R.id.ly_profile)
                changeVisibilityAndIcon(lyLinear, tvProfile)
            }
            tvlenguage.getId() -> {
                lyLinear = findViewById(R.id.ly_lenguage)
                changeVisibilityAndIcon(lyLinear, tvlenguage)
            }
            tvcontact.getId() -> {
                lyLinear = findViewById(R.id.ly_contact)
                changeVisibilityAndIcon(lyLinear, tvcontact)
            }
        }
    }
    private fun changeVisibilityAndIcon(ly: LinearLayout, tv: TextView) {
        if(ly.visibility == View.VISIBLE) {
            ly.visibility = View.GONE
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_flecha_abajo, 0)
        }
        else  {
            ly.visibility = View.VISIBLE
            tv.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_flecha_arriba,0)
        }
    }


    /***
     * cambia el nombre del usuario en su perfil
     */
    fun callChangeName(view: View) {
        changeName()
    }
    private fun changeName() {
        newName = etName.text.toString()

        if(newName.trim() != "") {
            globalUser.name = newName
            updateUser()
            updateComments()

            etName.setText("")

            val lyLinear2 = findViewById<LinearLayout>(R.id.ly_profile)
            val tvProfile2 = findViewById<TextView>(R.id.tv_profile)
            changeVisibilityAndIcon(lyLinear2, tvProfile2)

            Toast.makeText(this, "Nombre modificado correctamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Debes rellenar el campo para el nombre", Toast.LENGTH_SHORT).show()
        }
    }
    // actualiza el nombre de usuario en Firebase
    private fun updateUser() {
        val dbQuery = FirebaseFirestore.getInstance()
        dbQuery.collection("users").document(globalUser.email.toString())
            .update("name", newName)
    }
    // modifica todos los comentarios del usuario para que aparezcan con el nuevo nombre
    private fun updateComments() {
        val dbQuery = FirebaseFirestore.getInstance()
        dbQuery.collection("comments")
            .whereEqualTo("idEmailUser", globalUser.email)
            .get()
            .addOnSuccessListener { collection ->
                for (doc in collection) {
                    commentsArrayList.add(doc.toObject(Comment::class.java))
                }

                val dbQuery2 = FirebaseFirestore.getInstance()
                for (comment in commentsArrayList) {
                    dbQuery2.collection("comments").document("${comment.idEmailUser.toString()}+${comment.idNameRoute.toString()}")
                        .update("nameUser", globalUser.name)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting document: ", exception)
            }
    }

    /**
     * navega a la activity para consultar las descargas. Consultar "Apartado 10 - Vías futuras".
     */
    fun callDownloadActivity(view: View) {
        downloadActivity()
    }
    private fun downloadActivity() {
        // TODO
        /**
         * Aquí implementaríamos un código para navegar hasta la activity que nos muestre las descargas
         * Consultar "Apartado 10 - Vías futuras".
         */
    }

    /**
     * cambia el idioma del usuario. Consultar "Apartado 10 - Vías futuras".
     */
    fun callChangeLenguage (view: View){
        changeLenguage()
    }
    private fun changeLenguage() {
        // TODO
        /**
         * Aquí implementaríamos un código para cambiar el idioma del usuario
         * Consultar "Apartado 10 - Vías futuras".
         */
    }


    /**
     * enviar mail al correo destinado para ello. Consultar "Apartado 10 - Vías futuras".
     */
    fun callSendMailContact (view: View){
        sendMailContact()
    }
    private fun sendMailContact() {
        // TODO
        /**
         * Aquí implementaríamos un código para enviar mail al correo destinado para ello
         * Consultar "Apartado 10 - Vías futuras".
         */
    }


    /***
     * Cierra sesión
     */
    fun callSignOut(view: View) {
        signOut()
    }
    private fun signOut () {
        userEmail = ""
        FirebaseAuth.getInstance().signOut()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


    /***
     * Borra la cuenta de usuario. IMPORTANTE, borrar también todoo registro relacionado con ese perfil (comentarios)
     */
    fun callDeleteAccount(view: View) {
        deleteAccount()
    }
    private fun deleteAccount(){
        val alert = AlertDialog.Builder(this)

        alert.setTitle("CUIDADO")
            .setMessage("¿Estás seguro que quieres borrar tu usuario y todos tus datos relacionados?")
            .setCancelable(false)
            .setPositiveButton("SÍ") { dialog, id ->
                deleteUserAuthentication()                      // Borramos el usuario de la Authentication de Firebase

                userEmail = ""                                  // Variable global con la que hacemos LoadUser desde el Main
                FirebaseAuth.getInstance().signOut()            // cerramos sesión de usuario

                deleteComments(globalUser.email.toString())     // Borramos TODOS los comentarios de la collection "comments"
                deleteUser(globalUser.email.toString())         // Borramos los datos del usuario de la collection "users"

                // Inicializamos por defecto el objeto globalUser
                globalUser.difficultyMax = ""
                globalUser.email = ""
                globalUser.metersMax = 0
                globalUser.name = ""
                globalUser.totalRoutes = 0

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton("NO") { dialog, id ->
                Toast.makeText(this, "Has cancelado", Toast.LENGTH_SHORT).show()
            }
        alert.show()
    }

    private fun deleteComments (email: String) {
        val dbQuery = FirebaseFirestore.getInstance()
        dbQuery.collection("comments").
            whereEqualTo("idEmailUser", globalUser.email)
            .get()
            .addOnSuccessListener { collection ->
                for (doc in collection) {
                    deleteCommentsArrayList.add(doc.toObject(Comment::class.java))
                }
                if(deleteCommentsArrayList.size > 0) {
                    updateRoutes()
                    deleteAllComments()


                }
            }
            .addOnFailureListener { exception ->
            Log.w(ContentValues.TAG, "Error getting document: ", exception)
        }
    }
    // -1 al número total de comentarios de cada via
    private fun updateRoutes() {
        val dbQuery = FirebaseFirestore.getInstance()
        for (route in deleteCommentsArrayList) {
            dbQuery.collection("routes").document("${route.idNameRoute}")
                .get()
                .addOnSuccessListener { document ->
                    val newRoute = document.toObject(Route::class.java)!!   // obtengo los datos de esa ruta para saber cuandos comentarios tiene
                    // Actualizo en -1 su número de comentarios
                    val dbQuery2 = FirebaseFirestore.getInstance()
                    dbQuery2.collection("routes").document("${route.idNameRoute}").
                    update("numbersComments", (newRoute.numbersComments!! - 1))
                }
                .addOnFailureListener { exception ->
                    Log.d("ERROR loadUser", "get failed with", exception)
                }
        }
    }
    // borro todos los comentarios
    private fun deleteAllComments() {
        val dbQuery = FirebaseFirestore.getInstance()
        for (comment in deleteCommentsArrayList) {
            dbQuery.collection("comments").document("${comment.idEmailUser.toString()}+${comment.idNameRoute.toString()}")
                .delete()
        }
    }
    // Borra el usuario de la colección users Firebase
    private fun deleteUser (email: String) {
        val dbQuery = FirebaseFirestore.getInstance()
        dbQuery.collection("users").document(email)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Usuario borrado con éxito", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting document: ", exception)
            }
    }
    // Borra el usuario de Authentication de Firebase
    private fun deleteUserAuthentication () {
        val user = Firebase.auth.currentUser!!

        user.delete()
            .addOnCompleteListener{ task ->
                if( task.isSuccessful) {
                    Toast.makeText(this, "Cuenta borrada con éxito", Toast.LENGTH_SHORT).show()
                }
            }
    }
}