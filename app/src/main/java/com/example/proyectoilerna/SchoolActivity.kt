package com.example.proyectoilerna

import android.content.ContentValues
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classApp.ListAdapterSectors
import classApp.Sector
import classApp.UserApp
import com.example.proyectoilerna.MainActivity.Companion.globalSchool
import com.example.proyectoilerna.Utility.downloadSector
import com.google.firebase.firestore.FirebaseFirestore

class SchoolActivity : AppCompatActivity() {
    private lateinit var rvListSector: RecyclerView
    private lateinit var sectorArrayList: ArrayList<Sector>
    private lateinit var myAdapterSector: ListAdapterSectors

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_school)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initObjects()
        initSectorList()
    }
    override fun onResume() {
        super.onResume()
        loadSectorList()
    }
    override fun onPause() {
        super.onPause()
        sectorArrayList.clear()
    }


    /***
     * Inicializa los las pestañas sectores e info visible e invisible
     */
    private fun initObjects() {
        val llSchoolSector = findViewById<LinearLayout>(R.id.ll_school_sector)
        llSchoolSector.visibility = View.VISIBLE

        val llSchoolInformation = findViewById<LinearLayout>(R.id.ll_school_information)
        llSchoolInformation.visibility = View.GONE

        val header = findViewById<TextView>(R.id.tv_header)
        header.text = "Escuela de ${globalSchool.name.toString()}"

        val image = findViewById<ImageView>(R.id.iv_school_image)
        when (globalSchool.image) {
            "general_image_caimari" -> image.setImageResource(R.drawable.general_image_caimari)
            "general_image_calvia" -> image.setImageResource(R.drawable.general_image_calvia)
            "general_image_penyalgrau" -> image.setImageResource(R.drawable.general_image_penyalgrau)
            "general_image_puigstmarti" -> image.setImageResource(R.drawable.general_image_puigstmarti)
            "general_image_sagubia" -> image.setImageResource(R.drawable.general_image_sagubia)
            "general_image_valldemossa" -> image.setImageResource(R.drawable.general_image_valldemossa)
        }

        val description = findViewById<TextView>(R.id.tv_description)
        description.text = globalSchool.description.toString()

        val name = findViewById<TextView>(R.id.tv_name)
        name.text = "Escuela de ${globalSchool.name.toString()}"

        val numberRoutes = findViewById<TextView>(R.id.tv_number_routes)
        numberRoutes.text = "${globalSchool.numbersRoutes} vías de escalada"

        val numberSectors = findViewById<TextView>(R.id.tv_number_sectors)
        numberSectors.text = "${globalSchool.numbersSectors.toString()} sectores"

        val gps = findViewById<TextView>(R.id.tv_GPS)
        gps.setOnClickListener {
            callGPSGo()
        }
    }

    private fun initSectorList() {
        rvListSector = findViewById(R.id.rv_list_sector)
        rvListSector.layoutManager = LinearLayoutManager(this)
        rvListSector.setHasFixedSize(true)

        sectorArrayList = arrayListOf()

        myAdapterSector = ListAdapterSectors(sectorArrayList)
        rvListSector.adapter = myAdapterSector
    }

    private fun loadSectorList() {
        sectorArrayList.clear()

        val dbQuery = FirebaseFirestore.getInstance()
        dbQuery.collection("sectors")
            .whereEqualTo("idSchool", globalSchool.name.toString())
            .get()
            .addOnSuccessListener { collection ->
                for (doc in collection) {
                    sectorArrayList.add(doc.toObject(Sector::class.java))
                }
                myAdapterSector.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting document: ", exception)
            }
    }

    /**
     * Volver al Main Activity
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

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    /**
     * Descarga la información del sector para su consulta OffLine. Consultar "Apartado 10 - Vías futuras".
     * La función se importa desde utility porque hay otras activities que usarán esta función.
     */
    fun callDownloadSector(view: View) {
        downloadSector()
    }

    /**
     * cambia la visivilidad de la pestaña sectores e info
     */
    fun callChangeVisibilityLinearLayout(view: View) {
        changeVisibilityLinearLayout(view)
    }

    private fun changeVisibilityLinearLayout(view: View) {
        val llSchoolSector = findViewById<LinearLayout>(R.id.ll_school_sector)
        val llSchoolInformation = findViewById<LinearLayout>(R.id.ll_school_information)
        val tvSchoolSector = findViewById<TextView>(R.id.tv_school_sector)
        val tvSchoolInformation = findViewById<TextView>(R.id.tv_school_information)

        when (view.getId()) {
            tvSchoolSector.getId() -> {
                llSchoolSector.visibility = View.VISIBLE
                llSchoolInformation.visibility = View.GONE
                tvSchoolSector.setTypeface(Typeface.DEFAULT, Typeface.BOLD)
                tvSchoolInformation.setTypeface(Typeface.DEFAULT, Typeface.NORMAL)
            }

            tvSchoolInformation.getId() -> {
                llSchoolSector.visibility = View.GONE
                llSchoolInformation.visibility = View.VISIBLE
                tvSchoolSector.setTypeface(Typeface.DEFAULT, Typeface.NORMAL)
                tvSchoolInformation.setTypeface(Typeface.DEFAULT, Typeface.BOLD)
            }
        }
    }


    /**
     * a la activity map
     */
    fun callMapActivity(view: View) {
        mapActivity()
    }
    private fun mapActivity() {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }

    /**
     * va a la activity map pero activa directamente el direccionamiento para ir al parking de esa escuela
     */
    private fun callGPSGo () {
        // TODO

        Toast.makeText(this, "ACTIVANDO GPS...", Toast.LENGTH_SHORT).show()
    }
}