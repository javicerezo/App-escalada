package com.example.proyectoilerna

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classApp.LearnMountain
import classApp.ListAdapterLearnMountain
import classApp.ListAdapterRoutes
import classApp.ListAdapterSchools
import classApp.Route
import classApp.School
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class LearnMountainActivity : AppCompatActivity() {
    private lateinit var rvListLearn: RecyclerView
    private lateinit var learnArrayList: ArrayList<LearnMountain>
    private lateinit var myAdapterLearn: ListAdapterLearnMountain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_learn_mountain)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initLearnList()
    }
    override fun onResume() {
        super.onResume()
        loadLearnList()
    }
    override fun onPause() {
        super.onPause()
        learnArrayList.clear()
    }


    private fun initLearnList() {
        rvListLearn = findViewById(R.id.rv_list_learn)
        rvListLearn.layoutManager = LinearLayoutManager(this)
        rvListLearn.setHasFixedSize(true)

        learnArrayList = arrayListOf()

        myAdapterLearn = ListAdapterLearnMountain(learnArrayList)
        rvListLearn.adapter = myAdapterLearn
    }

    private fun loadLearnList() {
        learnArrayList.clear()

        val dbQuery = FirebaseFirestore.getInstance()
        dbQuery.collection("mountainItems")
            .get()
            .addOnSuccessListener { collection ->
                for (doc in collection) {
                    learnArrayList.add(doc.toObject(LearnMountain::class.java))
                }
                myAdapterLearn.notifyDataSetChanged()
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

}