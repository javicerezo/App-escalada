package com.example.proyectoilerna

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import classApp.School
import classApp.Sector
import com.example.proyectoilerna.MainActivity.Companion.globalSchool
import com.example.proyectoilerna.MainActivity.Companion.globalSector
import com.google.firebase.firestore.FirebaseFirestore

class SearchViewActivity : AppCompatActivity() {
    private lateinit var svBuscador: SearchView
    private lateinit var lvResultados: ListView

    private lateinit var arrayList: ArrayList<String>
    private lateinit var schoolList: ArrayList<School>
    private lateinit var sectorList: ArrayList<Sector>

    private lateinit var adapterList: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initObjects()
        loadSchoolList()


        svBuscador.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                svBuscador.clearFocus()
                if (arrayList.contains(query)) adapterList.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                adapterList.filter.filter(query)

                return false
            }

        })

        lvResultados.setOnItemClickListener { parent, view, position, id ->
            val textItem = lvResultados.getItemAtPosition(position)

            // comprobamos si es una escuela de escalada
            for (school in schoolList) {
                if(school.name == textItem) {
                    globalSchool = school
                    schoolActivity()
                }
            }

            // si no es escuela, es sector de escalada
            for(sector in sectorList) {
                if(sector.name == textItem){
                    globalSector = sector

                    // También tenemos que saber los datos de la escuela a la que pertenece
                    for(school in schoolList) {
                        if(school.name == sector.idSchool) {
                            globalSchool = school
                            sectorActivity()
                        }
                    }
                }
            }
        }
    }


    private fun initObjects() {
        arrayList = arrayListOf()
        schoolList = arrayListOf()
        sectorList = arrayListOf()

        svBuscador = findViewById(R.id.sv_buscador)
        lvResultados = findViewById(R.id.lv_resultados)

        adapterList = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList)
        lvResultados.adapter = adapterList
    }

    private fun loadSchoolList() {
        val dbQuery = FirebaseFirestore.getInstance()
        dbQuery.collection("schools")
            .get()
            .addOnSuccessListener { collection ->
                for(doc in collection) {
                    schoolList.add(doc.toObject(School::class.java))
                }
                for (school in schoolList) {
                    arrayList.add(school.name.toString())
                }
                loadSectorList()
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting document: ", exception)
            }
    }
    private fun loadSectorList() {
        val dbQuery = FirebaseFirestore.getInstance()
        dbQuery.collection("sectors")
            .get()
            .addOnSuccessListener { collection ->
                for(doc in collection) {
                    sectorList.add(doc.toObject(Sector::class.java))
                }
                for (sector in sectorList) {
                    arrayList.add(sector.name.toString())
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
        globalSchool.carGPSLatitude = 0.0
        globalSchool.carGPSLongitude = 0.0
        globalSchool.description = ""
        globalSchool.image = ""
        globalSchool.name = ""
        globalSchool.numbersRoutes = 0
        globalSchool.numbersSectors = 0

        globalSector.idSchool = ""
        globalSector.name = ""
        globalSector.numbersRoutes = 0
        globalSector.orientation = ""
        globalSector.sketch = ""
        globalSector.style = ""
        globalSector.walkingTime = 0
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun schoolActivity() {
        val intent = Intent(this, SchoolActivity::class.java)
        startActivity(intent)
    }
    private fun sectorActivity() {
        val intent = Intent(this, SectorActivity::class.java)
        startActivity(intent)
    }
}