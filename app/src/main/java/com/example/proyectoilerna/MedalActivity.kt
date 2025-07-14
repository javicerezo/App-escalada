package com.example.proyectoilerna

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoilerna.MainActivity.Companion.globalUser
import com.example.proyectoilerna.MainActivity.Companion.levelDifficultyArrayList
import com.example.proyectoilerna.MainActivity.Companion.levelMetterArrayList

class MedalActivity : AppCompatActivity() {
    private lateinit var icon: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_medal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setDifficultyLevel()
        setMetterLevel()
    }

    private fun setDifficultyLevel() {
        for (level in levelDifficultyArrayList) { // array ordenado por dificultad
            if(globalUser.difficultyMax!! >= level.difficultyTarget!!) {
                when(level.name) {
                    "icon_difficulty_5a" -> {
                        icon = findViewById(R.id.icon_difficulty_5a)
                        icon.setBackgroundResource(R.drawable.ic_trofeo_2)
                    }
                    "icon_difficulty_6a" -> {
                        icon = findViewById(R.id.icon_difficulty_6a)
                        icon.setBackgroundResource(R.drawable.ic_trofeo_2)
                    }
                    "icon_difficulty_6c" -> {
                        icon = findViewById(R.id.icon_difficulty_6c)
                        icon.setBackgroundResource(R.drawable.ic_trofeo)
                    }
                    "icon_difficulty_7a" -> {
                        icon = findViewById(R.id.icon_difficulty_7a)
                        icon.setBackgroundResource(R.drawable.ic_trofeo)
                    }
                    "icon_difficulty_7b" -> {
                        icon = findViewById(R.id.icon_difficulty_7b)
                        icon.setBackgroundResource(R.drawable.ic_trofeo)
                    }
                    "icon_difficulty_8a" -> {
                        icon = findViewById(R.id.icon_difficulty_8a)
                        icon.setBackgroundResource(R.drawable.ic_trofeo_3)
                    }
                    "icon_difficulty_8b" -> {
                        icon = findViewById(R.id.icon_difficulty_8b)
                        icon.setBackgroundResource(R.drawable.ic_trofeo_3)
                    }
                    "icon_difficulty_8c" -> {
                        icon = findViewById(R.id.icon_difficulty_8c)
                        icon.setBackgroundResource(R.drawable.ic_trofeo_3)
                    }
                }
            }
            else break
        }
    }

    private fun setMetterLevel() {
        for (level in levelMetterArrayList) {
            if((globalUser.metersMax!! >= level.distanceTarget!!) && (globalUser.totalRoutes!! >= level.numRoutes!!)) {
                when(level.name) {
                    "icon_metters_100" -> {
                        icon = findViewById(R.id.icon_metters_100)
                        icon.setBackgroundResource(R.drawable.ic_trofeo_2)
                    }
                    "icon_metters_500" -> {
                        icon = findViewById(R.id.icon_metters_500)
                        icon.setBackgroundResource(R.drawable.ic_trofeo_2)
                    }
                    "icon_metters_1000" -> {
                        icon = findViewById(R.id.icon_metters_1000)
                        icon.setBackgroundResource(R.drawable.ic_trofeo)
                    }
                    "icon_metters_2000" -> {
                        icon = findViewById(R.id.icon_metters_2000)
                        icon.setBackgroundResource(R.drawable.ic_trofeo)
                    }
                    "icon_metters_3000" -> {
                        icon = findViewById(R.id.icon_metters_3000)
                        icon.setBackgroundResource(R.drawable.ic_trofeo)
                    }
                    "icon_metters_5000" -> {
                        icon = findViewById(R.id.icon_metters_5000)
                        icon.setBackgroundResource(R.drawable.ic_trofeo_3)
                    }
                    "icon_metters_8000" -> {
                        icon = findViewById(R.id.icon_metters_8000)
                        icon.setBackgroundResource(R.drawable.ic_trofeo_3)
                    }
                    "icon_metters_12000" -> {
                        icon = findViewById(R.id.icon_metters_12000)
                        icon.setBackgroundResource(R.drawable.ic_trofeo_3)
                    }
                }
            }
            else break
        }
    }

    /***
     * Volver a la activity_profile
     */
    fun callProfileActivity(view: View) {
        profileActivity()
    }
    private fun profileActivity() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }
}