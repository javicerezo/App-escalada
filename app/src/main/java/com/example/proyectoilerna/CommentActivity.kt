package com.example.proyectoilerna

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import classApp.Comment
import com.example.proyectoilerna.MainActivity.Companion.globalRoute
import com.example.proyectoilerna.MainActivity.Companion.globalSchool
import com.example.proyectoilerna.MainActivity.Companion.globalSector
import com.example.proyectoilerna.MainActivity.Companion.globalUser
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date

class CommentActivity : AppCompatActivity() {
    private lateinit var etComment: EditText
    private lateinit var comment: Comment
    private lateinit var btnComment: TextView

    private lateinit var writerComment: String
    private var update: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_comment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initObject()
        btnComment.setOnClickListener {
            makeComment()
        }
    }
    override fun onResume() {
        super.onResume()
        loadComment()
    }
    override fun onPause() {
        super.onPause()

        comment.comment = ""
        comment.date = ""
        comment.difficultyOfRoute = ""
        comment.idEmailUser = ""
        comment.idNameRoute = ""
        comment.mettersOfRoute = 0
        comment.nameUser = ""
        comment.sectorOfRoute = ""
    }

    private fun initObject() {
        val tvName = findViewById<TextView>(R.id.tv_name)
        tvName.text = globalRoute.name.toString()

        val tvSectorAndSchool = findViewById<TextView>(R.id.tv_sector_school)
        tvSectorAndSchool.text = "${globalSector.name.toString()}, ${globalSchool.name.toString()}"

        val tvNumberComments = findViewById<TextView>(R.id.tv_number_comments)
        tvNumberComments.text = "${globalRoute.numbersComments.toString()} comentarios."

        val tvNumberMetters = findViewById<TextView>(R.id.tv_number_metters)
        tvNumberMetters.text = "${globalRoute.metters.toString()} metros."

        val tvDifficulty = findViewById<TextView>(R.id.tv_difficulty)
        tvDifficulty.text = globalRoute.difficulty.toString()

        btnComment = findViewById(R.id.btn_comment)

        writerComment = ""

        etComment = findViewById(R.id.et_comment)
        initComment()
    }
    private fun initComment() {
        comment = Comment()

        comment.comment = ""
        comment.date = ""
        comment.difficultyOfRoute = ""
        comment.idEmailUser = ""
        comment.idNameRoute = ""
        comment.mettersOfRoute = 0
        comment.nameUser = ""
        comment.sectorOfRoute = ""
    }
    private fun loadComment() {
        val dbQuery = FirebaseFirestore.getInstance()
        dbQuery.collection("comments").document("${globalUser.email}+${globalRoute.name}")
            .get()
            .addOnSuccessListener { document ->
                if(document.data?.size != null) {
                    comment = document.toObject(Comment::class.java)!!
                    etComment.setText(comment.comment.toString())
                    btnComment.text = "MODIFICAR"
                    update = true
                }
            }
            .addOnFailureListener { exception ->
                Log.d("ERROR loadUser", "get failed with", exception)
            }
    }

    /**
     * volver a activity Route
     */
    fun callRouteActivity (view: View) {
        routeActivity()
    }
    private fun routeActivity() {
        val intent = Intent(this, RouteActivity::class.java)
        startActivity(intent)
    }

    /**
     * Hace un comentario nuevo o modifica el que ya existe.
     * Sólo existe un comentario por usuario y por ruta de escalada.
     */
    private fun makeComment() {
        writerComment = etComment.text.toString()

        if(writerComment.trim() != ""){
            if(update){     // Existe comentario...MODIFICAR
                updateComment()

                routeActivity()
                Toast.makeText(this, "Comentario modificado con éxito", Toast.LENGTH_SHORT).show()

            } else {    // No existe comentario... CREAR UNO NUEVO
                newComment()
                updateCommentRoute()
                updateUser()

                routeActivity()
                Toast.makeText(this, "Comentario registrado con éxito", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Rellena el campo de descripción de comentario", Toast.LENGTH_SHORT).show()
        }
    }

    // Crea comentario nuevo
    private fun newComment() {
        val dateRegister = SimpleDateFormat("dd/MM/yyy").format(Date())
        val dbQuery = FirebaseFirestore.getInstance()
        dbQuery.collection("comments").document("${globalUser.email}+${globalRoute.name}")
            .set(hashMapOf(
                "comment" to writerComment,
                "date" to dateRegister.toString(),
                "difficultyOfRoute" to globalRoute.difficulty,
                "idEmailUser" to globalUser.email,
                "idNameRoute" to globalRoute.name,
                "mettersOfRoute" to globalRoute.metters,
                "nameUser" to globalUser.name,
                "sectorOfRoute" to globalSector.name
            ))
    }
    // Actualiza comentario ya existente
    private fun updateComment() {
        comment.comment = etComment.text.toString()
        val dateRegister = SimpleDateFormat("dd/MM/yyy").format(Date())

        val dbQuery = FirebaseFirestore.getInstance()
        dbQuery.collection("comments").document("${globalUser.email}+${globalRoute.name}")
            .update("comment", comment.comment)
        dbQuery.collection("comments").document("${globalUser.email}+${globalRoute.name}")
            .update("date", dateRegister)
    }
    // Actualiza datos usuario
    private fun updateUser() {
        if(globalRoute.difficulty!! > globalUser.difficultyMax.toString()) {
            globalUser.difficultyMax = globalRoute.difficulty
        }
        globalUser.totalRoutes = globalUser.totalRoutes!! + 1
        globalUser.metersMax = globalUser.metersMax!! + globalRoute.metters!!

        val dbQuery = FirebaseFirestore.getInstance()
        dbQuery.collection("users").document(globalUser.email.toString())
            .update("difficultyMax", globalUser.difficultyMax)
        dbQuery.collection("users").document(globalUser.email.toString())
            .update("totalRoutes", globalUser.totalRoutes)
        dbQuery.collection("users").document(globalUser.email.toString())
            .update("metersMax", globalUser.metersMax)
    }
    // Actualiza número de comentarios de esa ruta de escalada
    private fun updateCommentRoute() {
        globalRoute.numbersComments = globalRoute.numbersComments!! + 1

        val dbQuery = FirebaseFirestore.getInstance()
        dbQuery.collection("routes").document(globalRoute.name.toString())
            .update("numbersComments", globalRoute.numbersComments)
    }
}