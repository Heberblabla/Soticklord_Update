package com.waos.soticklord

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class Mapa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mapa)
        // Ocultar barras del sistema
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    fun primer_nivel(view: View){
        val intent = Intent(this, Principal::class.java)
        startActivity(intent)
    }
    fun segundo_nivel(view: View){
        val intent = Intent(this, Principal::class.java)
        startActivity(intent)

    }fun tercer_nivel(view: View){
        val intent = Intent(this, Principal::class.java)
        startActivity(intent)

    }fun cuarto_nivel(view: View){
        val intent = Intent(this, Principal::class.java)
        startActivity(intent)

    }
    fun quinto_nivel(view: View){
        val intent = Intent(this, Principal::class.java)
        startActivity(intent)

    }
    fun sexto_nivel(view: View){
        val intent = Intent(this, Principal::class.java)
        startActivity(intent)

    }


}