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
import classApp.ListAdapterRoutes
import classApp.Route
import com.example.proyectoilerna.MainActivity.Companion.globalSector
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class SectorActivity : AppCompatActivity() {
    private lateinit var rvListRoute: RecyclerView
    private lateinit var routeArrayList: ArrayList<Route>
    private lateinit var myAdapterRoute: ListAdapterRoutes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sector)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initObjects()
        initRouteList()
    }
    override fun onResume() {
        super.onResume()
        loadRouteList()
    }
    override fun onPause() {
        super.onPause()
        routeArrayList.clear()
    }

    private fun initObjects() {
        val header = findViewById<TextView>(R.id.tv_header)
        header.text = "Sector ${globalSector.name.toString()}"

        val image = findViewById<ImageView>(R.id.iv_sector_image)
        when (globalSector.sketch) {
            "sketch_aboveroad" -> image.setImageResource(R.drawable.sketch_aboveroad)
            "sketch_calvia" -> image.setImageResource(R.drawable.sketch_calvia)
            "sketch_caraoeste" -> image.setImageResource(R.drawable.sketch_caraoeste)
            "sketch_comunacaimari" -> image.setImageResource(R.drawable.sketch_comunacaimari)
            "sketch_maincrag" -> image.setImageResource(R.drawable.sketch_maincrag)
            "sketch_murocaimari" -> image.setImageResource(R.drawable.sketch_murocaimari)
            "sketch_northbuttress" -> image.setImageResource(R.drawable.sketch_northbuttress)
            "sketch_sectoralbahida" -> image.setImageResource(R.drawable.sketch_sectoralbahida)
            "sketch_sectorpotajeespanol" -> image.setImageResource(R.drawable.sketch_sectorpotajeespanol)
            "sketch_sectorprincesa" -> image.setImageResource(R.drawable.sketch_sectorprincesa)
        }
    }

    private fun initRouteList() {
        rvListRoute = findViewById(R.id.rv_list_route)
        rvListRoute.layoutManager = LinearLayoutManager(this)
        rvListRoute.setHasFixedSize(true)

        routeArrayList = arrayListOf()

        myAdapterRoute = ListAdapterRoutes(routeArrayList)
        rvListRoute.adapter = myAdapterRoute
    }

    private fun loadRouteList() {
        routeArrayList.clear()

        val dbQuery = FirebaseFirestore.getInstance()
        dbQuery.collection("routes")
            .whereEqualTo("idSector", globalSector.name.toString())
            .orderBy("numberOfRoute", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { collection ->
                for (doc in collection) {
                    routeArrayList.add(doc.toObject(Route::class.java))
                }
                myAdapterRoute.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting document: ", exception)
            }
    }

    /***
     * volver a actitity_school
     */
    fun callSchoolActivity(view: View) {
        schoolActivity()
    }
    private fun schoolActivity() {
        globalSector.idSchool = ""
        globalSector.name = ""
        globalSector.numbersRoutes = 0
        globalSector.orientation = ""
        globalSector.sketch = ""
        globalSector.style = ""
        globalSector.walkingTime = 0

        val intent = Intent(this, SchoolActivity::class.java)
        startActivity(intent)
    }

}