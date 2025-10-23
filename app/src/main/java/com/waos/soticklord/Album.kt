package com.waos.soticklord

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class Album : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_album)

        //pantalla completa :v
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


    //Aparatdo derecho superior

    fun mostrar_datos_economicos(){

    }
    fun ordenar_por_nivel(){

    }
    fun ordenar_por_vida(){

    }
    fun ordenar_por_ataque(){

    }
    fun cambiar_a_otro_album(){


    }

    //apartado derecho inferior

    fun seleccionar_rey_tropa(){
    }
    fun pasar_siguiente_hoja(){
    }
    fun pasar_anterior_hoja(){
    }
    fun subir_de_nivel(){

    }

    //aparato isquierdo superior

    fun mejorar_tropa_rey(){

    }
    fun mostar_datos(){

    }

    //aparatdo aparatdo isquierdo inferior

    fun siguiente_ataque(){

    }
    fun anterior_ataque(){

    }
    fun mostrar_informacion(){

    }

}