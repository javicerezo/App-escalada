package com.example.proyectoilerna

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classApp.School
import classApp.ListAdapterSchools
import com.example.proyectoilerna.Utility.downloadSector
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class SchoolListActivity : AppCompatActivity() {
    private lateinit var rvListSchool: RecyclerView // RecyclerView para mostrar las escuelas de schoolArrayList
    private lateinit var schoolArrayList: ArrayList<School>
    private lateinit var myAdapter: ListAdapterSchools

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_school_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initSchoolList()
    }
    override fun onResume() {
        super.onResume()
        loadSchoolList()
    }
    override fun onPause() {
        super.onPause()
        schoolArrayList.clear()
    }


    private fun initSchoolList() {
        rvListSchool = findViewById(R.id.rv_list_school)
        rvListSchool.layoutManager = LinearLayoutManager(this)
        rvListSchool.setHasFixedSize(true)

        schoolArrayList = arrayListOf()
        myAdapter = ListAdapterSchools(schoolArrayList)
        rvListSchool.adapter = myAdapter
    }
    private fun loadSchoolList() {
        schoolArrayList.clear()

        val dbQuery = FirebaseFirestore.getInstance()
        dbQuery.collection("schools")
            .orderBy("name", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { collection ->
                for(doc in collection) {
                    //cargamos cada escuela dentro del array de escuelas
                    schoolArrayList.add(doc.toObject(School::class.java))
                }
                myAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting document: ", exception)
            }
    }

    /**
     * Descarga la información del sector para su consulta OffLine. Consultar "Apartado 10 - Vías futuras".
     * La función se importa desde utility porque hay otras activities que usarán esta función.
     */
    fun callDownloadSector(view: View) {
        downloadSector()
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