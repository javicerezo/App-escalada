package com.example.proyectoilerna

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classApp.Comment
import classApp.ListAdapterRoutesProfile
import com.example.proyectoilerna.MainActivity.Companion.globalUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ProfileActivity : AppCompatActivity() {
    private lateinit var rvListRoutesProfile: RecyclerView
    private lateinit var routesProfileArrayList: ArrayList<Comment>
    private lateinit var myAdapterRoutesProfile: ListAdapterRoutesProfile

    private lateinit var tvNoClimb: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initObjects()
        initRoutesProfileList()
    }
    override fun onResume() {
        super.onResume()
        loadRoutesProfileList()
    }
    override fun onPause() {
        super.onPause()
        routesProfileArrayList.clear()
    }

    private fun initObjects() {
        val nameUser = findViewById<TextView>(R.id.tv_name_user)
        nameUser.text = globalUser.name

        val emailUser = findViewById<TextView>(R.id.tv_email_user)
        emailUser.text = globalUser.email

        val mettersMax = findViewById<TextView>(R.id.tv_metters_max)
        mettersMax.text = "${globalUser.metersMax} metros"

        val difficultyMax = findViewById<TextView>(R.id.tv_difficulty_max)
        difficultyMax.text = globalUser.difficultyMax

        val totalRoutes = findViewById<TextView>(R.id.tv_total_routes)
        totalRoutes.text = "${globalUser.totalRoutes} vias"

        tvNoClimb = findViewById(R.id.tv_no_climb)
        tvNoClimb.visibility = View.GONE
    }
    private fun initRoutesProfileList() {
        rvListRoutesProfile = findViewById(R.id.rv_list_routes_profile)
        rvListRoutesProfile.layoutManager = LinearLayoutManager(this)
        rvListRoutesProfile.setHasFixedSize(true)

        routesProfileArrayList = arrayListOf()

        myAdapterRoutesProfile = ListAdapterRoutesProfile(routesProfileArrayList)
        rvListRoutesProfile.adapter = myAdapterRoutesProfile
    }
    private fun loadRoutesProfileList() {
        routesProfileArrayList.clear()

        val dbQuery = FirebaseFirestore.getInstance()
        dbQuery.collection("comments")
            .whereEqualTo("idEmailUser", globalUser.email.toString())
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { collection ->
                if(collection.size() != 0){
                    for (doc in collection) {
                        routesProfileArrayList.add(doc.toObject(Comment::class.java))
                    }
                    myAdapterRoutesProfile.notifyDataSetChanged()
                } else {
                    tvNoClimb.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting document: ", exception)
            }
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


    /**
     * a la activity_medal
     */
    fun callMedalActivity(view: View) {
        medalActivity()
    }
    private  fun medalActivity() {
        val intent = Intent(this, MedalActivity::class.java)
        startActivity(intent)
    }
}