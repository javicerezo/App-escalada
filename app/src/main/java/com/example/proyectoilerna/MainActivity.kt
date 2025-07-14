package com.example.proyectoilerna

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classApp.LevelDifficulty
import classApp.LevelMetter
import classApp.ListAdapterSchools
import classApp.Route
import classApp.School
import classApp.Sector
import classApp.UserApp
import com.example.proyectoilerna.SignupActivity.Companion.userEmail
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout // La activity_main está en una etiqueta DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var rvListSchool: RecyclerView // RecyclerView para mostrar las escuelas de schoolArrayList
    private lateinit var schoolArrayList: ArrayList<School>
    private lateinit var myAdapter: ListAdapterSchools

    companion object {
        lateinit var globalUser: UserApp
        lateinit var globalSchool: School
        lateinit var globalSector: Sector
        lateinit var globalRoute: Route

        lateinit var levelDifficultyArrayList: ArrayList<LevelDifficulty>
        lateinit var levelMetterArrayList: ArrayList<LevelMetter>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initToolBar()
        initNavigationView()
        initObjects()
        loadUser()
        loadLevel()
    }
    /**
     * onCreate() hace la consulta a Firebase y cargará todos los objetos en el array,
     *  onResume carga esos objetos en el rvListSchool: RecyclerView
     */
    override fun onResume() {
        super.onResume()
        loadSchoolList()
    }
    /**
     * usuario cambia de ventana, mejor eliminar los datos y cuando vuelva, se vuelven a cargar.
     */
    override fun onPause() {
        super.onPause()
        schoolArrayList.clear()
    }

    /**
     * Iniciamos el punto inicial de todos los objetos de la aplicación
     */
    private fun initObjects() {
        initUserApp()
        initSchool()
        initSector()
        initRoute()
        initLevels()
        initSchoolList()
    }
    private fun initUserApp() {
        globalUser = UserApp()

        globalUser.difficultyMax = ""
        globalUser.email = ""
        globalUser.metersMax = 0
        globalUser.name = ""
        globalUser.totalRoutes = 0
    }
    private fun initSchool() {
        globalSchool = School()

        globalSchool.carGPSLatitude = 0.0
        globalSchool.carGPSLongitude = 0.0
        globalSchool.description = ""
        globalSchool.image = ""
        globalSchool.name = ""
        globalSchool.numbersRoutes = 0
        globalSchool.numbersSectors = 0
    }
    private fun initSector() {
        globalSector = Sector()

        globalSector.idSchool = ""
        globalSector.name = ""
        globalSector.numbersRoutes = 0
        globalSector.orientation = ""
        globalSector.sketch = ""
        globalSector.style = ""
        globalSector.walkingTime = 0
    }
    private fun initRoute() {
        globalRoute = Route()

        globalRoute.difficulty = ""
        globalRoute.idSector = ""
        globalRoute.name = ""
        globalRoute.numberOfRoute = 0
        globalRoute.numbersComments = 0
    }
    private fun initLevels() {
        levelDifficultyArrayList = arrayListOf()
        levelDifficultyArrayList.clear()

        levelMetterArrayList = arrayListOf()
        levelMetterArrayList.clear()
    }
    private fun initSchoolList() {
        rvListSchool = findViewById(R.id.rv_list_school)
        rvListSchool.layoutManager = LinearLayoutManager(this)
        rvListSchool.setHasFixedSize(true)

        schoolArrayList = arrayListOf()
        myAdapter = ListAdapterSchools(schoolArrayList)
        rvListSchool.adapter = myAdapter
    }


    /**
     * cargamos los datos del usuario desde Firebase dentro de user (variable global)
     */
    private fun loadUser() {
        val dbQuery = FirebaseFirestore.getInstance()
        dbQuery.collection("users").document(userEmail)
            .get()
            .addOnSuccessListener { document ->
                globalUser = document.toObject(UserApp::class.java)!!
            }
            .addOnFailureListener { exception ->
                Log.d("ERROR loadUser", "get failed with", exception)
            }
    }

    private fun loadLevel() {
        loadLevelTarget("Meters")
        loadLevelTarget("Difficulty")
    }
    private fun loadLevelTarget(target: String) {
        levelMetterArrayList.clear()
        levelDifficultyArrayList.clear()

        val dbQuery = FirebaseFirestore.getInstance()
        dbQuery.collection("levels$target")
            .get()
            .addOnSuccessListener { collection ->
                for (doc in collection) {
                    when (target) {
                        "Meters" -> levelMetterArrayList.add(doc.toObject(LevelMetter::class.java))
                        "Difficulty" -> levelDifficultyArrayList.add(doc.toObject(LevelDifficulty::class.java))
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting document: ", exception)
            }
    }


    /***
     * carga el listado de escuelas de escalada desde la Firebase
     */
    private fun loadSchoolList() {
        schoolArrayList.clear()

        val dbQuery = FirebaseFirestore.getInstance()
        dbQuery.collection("schools")
            .orderBy("carGPSLongitude", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { collection ->
                for(doc in collection) {
                    schoolArrayList.add(doc.toObject(School::class.java))
                    if(schoolArrayList.size == 4) break
                }
                myAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting document: ", exception)
            }
    }


    /***
     * Integramos el menú desplegable en el activity_main
     */
    private fun initToolBar() {
        // administramos la toolbar
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.main)

        // es esta activity vamos a tener este layout (drawer), con esta toolbar con esos dos string de apertura y cierre
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name)
        drawer.addDrawerListener(toggle)

        toggle.syncState()
    }
    /***
     * Administramos cada elemento del menú
     */
    private fun initNavigationView() {
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }
    /***
     * capturamos que elemento del menú se ha pulsado
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_item_profile -> callProfileActivity()
            R.id.nav_item_download -> callDownload()
            R.id.nav_item_learn_mountain -> callLearnMountainActivity()
            R.id.nav_item_main_settings -> callSettingsActivity()
            R.id.nav_item_main_close_session -> callSignOut()
        }
        drawer.closeDrawers()

        return true
    }
    /***
     * Funciones para los elementos del menú
     */
    private fun callProfileActivity() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }
    private fun callDownload() {
        // TODO
        /**
         * Aquí implementaríamos un código para navegar hasta la activity que nos muestre las descargas
         * Consultar "Apartado 10 - Vías futuras".
         */
    }
    private fun callLearnMountainActivity() {
        val intent = Intent(this, LearnMountainActivity::class.java)
        startActivity(intent)
    }
    private fun callSettingsActivity() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
    private fun callSignOut () {
        userEmail = ""
        FirebaseAuth.getInstance().signOut()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    /***
     * ir al activity_school_list
     */
    fun callSchoolListActivity (view: View) {
        schoolListActivity()
    }
    private fun schoolListActivity() {
        val intent = Intent(this, SchoolListActivity::class.java)
        startActivity(intent)
    }

    /**
     * ir a la activity_searchView
     */
    fun callSearchViewActivity(view: View) {
        searchViewActivity()
    }
    private fun searchViewActivity() {
        val intent = Intent(this, SearchViewActivity::class.java)
        startActivity(intent)
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
}