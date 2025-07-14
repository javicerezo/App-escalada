package com.example.proyectoilerna

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap

    /*
    private var activatedGPS: Boolean = true
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val PERMISSION_ID = 42
    val REQUIRED_PERMISSIONS_GPS =
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_map)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //initPermissionGPS()
        crateMapFragment()
    }


    /**
     * override obligatorio en documentación para manejar Maps (consultar documentación oficial)
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.mapType = GoogleMap.MAP_TYPE_SATELLITE

        val sydney = LatLng(-34.0, 151.0)
        map.addMarker(
            MarkerOptions()
            .position(sydney)
            .title("Marker in Sydney"))
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    /**
     * Creación del fragment para Maps
     */
    private fun crateMapFragment() {
        // obtain the SupportMapFragment and get notified when the map is ready to be used.
        // Script obligatorio para poder usar Maps (consultar documentación oficial)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }



    /**
     * VA A LA ACTIVITY GPS PARA ENCONTRAR TODOS LOS SECTORES CON EL MAPA
     */
    /*
    fun callGPSActivity(view: View) {
        gPSActivity()
    }
    private fun gPSActivity() {
        if(isLocationsEnabled() == false) {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.alertActivationGPSTitle))
                .setMessage(getString(R.string.alertActivationGPSDescription))
                .setPositiveButton(R.string.aceptActivation) { dialog, id ->
                    activationLocation()
                }
                .setNegativeButton(R.string.ignoreActivation) { dialog, id ->
                    Toast.makeText(this, "Debes activar la localización GPS", Toast.LENGTH_SHORT).show()
                    activatedGPS = false
                }
                .setCancelable(true)
                .show()
        }
        else {
            // ir a la activity location
        }
    }

    /**
     * Comprueba si todos los permisos están activados
     */
    private fun initPermissionGPS() {
        if(allPermissionsGrantedGPS()) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        }
        else {
            requestPermissionLocation()
        }
    }
    private fun allPermissionsGrantedGPS() = REQUIRED_PERMISSIONS_GPS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissionLocation() {
        ActivityCompat.requestPermissions(this, arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID
        )
    }
    /**
     * Comprueba si está activado el GPS del teléfono
     */
    private fun isLocationsEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
    /**
     * Abre la activity propia de android para los ajustes del sistema y poder activar el GPS
     */
    private fun activationLocation() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

     */

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