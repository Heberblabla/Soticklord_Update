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
import Data.*
import Data.Rey_Cristian
import Data.Rey_Fernando
import Data.Rey_Heber
import Data.Reyna_Darisce
import Data.Reyna_Shantal
import android.widget.ImageView

class Mapa : AppCompatActivity() {
    var nivel_uno = true
    var nivel_dos = false
    var nivel_tres = false
    var nivel_cuatro = false
    var nivel_cinco = false
    var nivel_seis = false
    var nivel_secreto = false
    lateinit var imagenes: ArrayList<ImageView>


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
        imagenes = arrayListOf(
            findViewById(R.id.Estrella_nivel1),
            findViewById(R.id.Estrella_nivel2),
            findViewById(R.id.Estrella_nivel3),
            findViewById(R.id.Estrella_nivel4),
            findViewById(R.id.Estrella_nivel5),
            findViewById(R.id.Estrella_nivel6)
        )
        Ocultar_imagenes()

        mostrar_estrellas()


    }


    fun Ocultar_imagenes() {
        for (img in imagenes) {
            img.visibility = View.INVISIBLE
        }
    }

    fun mostrar_estrellas(){
        if(GlobalData.nivel_de_progresion > 0){
            nivel_uno = true

        }
        if(GlobalData.nivel_de_progresion > 1){
            nivel_dos = true
            imagenes[0].visibility = View.VISIBLE
        }
        if(GlobalData.nivel_de_progresion > 2){
            nivel_tres = true
            imagenes[1].visibility = View.VISIBLE
        }
        if(GlobalData.nivel_de_progresion > 3){
            nivel_cuatro = true
            imagenes[2].visibility = View.VISIBLE
        }
        if(GlobalData.nivel_de_progresion > 4){
            nivel_cinco = true
            imagenes[3].visibility = View.VISIBLE
        }
        if(GlobalData.nivel_de_progresion > 5){
            nivel_seis = true
            imagenes[4].visibility = View.VISIBLE
        }
        if(GlobalData.nivel_de_progresion >= 6){
            nivel_secreto = true
            imagenes[5].visibility = View.VISIBLE
        }
    }

    fun primer_nivel(view: View){
        if(nivel_uno) {
            GlobalData.Jugador2[0] = Rey_Espadachin(5)
            GlobalData.Jugador2[1] = Tropa_Arquero(2)
            GlobalData.Jugador2[2] = Tropa_Arquero(2)
            GlobalData.Jugador2[3] = Tropa_Espadachin(3)
            GlobalData.Jugador2[4] = Tropa_Espadachin(3)
            GlobalData.Jugador2[5] = Tropa_Espadachin(3)

            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }
    }
    fun segundo_nivel(view: View){
        if(nivel_dos) {
            GlobalData.Jugador2[0] = Rey_Lanzatonio(7)
            GlobalData.Jugador2[1] = Tropa_Arquero(4)
            GlobalData.Jugador2[2] = Tropa_Arquero(4)
            GlobalData.Jugador2[3] = Tropa_Lanzatonio(3)
            GlobalData.Jugador2[4] = Tropa_Espadachin(5)
            GlobalData.Jugador2[5] = Tropa_Lanzatonio(3)
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }
    }
    fun tercer_nivel(view: View){
        if(nivel_tres) {
            GlobalData.Jugador2[0] = Rey_Arquero(10)
            GlobalData.Jugador2[1] = Tropa_Arquero(5)
            GlobalData.Jugador2[2] = Tropa_Arquero(5)
            GlobalData.Jugador2[3] = Tropa_Lanzatonio(3)
            GlobalData.Jugador2[4] = Tropa_Lanzatonio(5)
            GlobalData.Jugador2[5] = Tropa_Lanzatonio(3)
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }
    }
    fun cuarto_nivel(view: View){
        if(nivel_cuatro) {
            GlobalData.Jugador2[0] = Rey_Arquero(15)
            GlobalData.Jugador2[1] = Tropa_Arquero(4)
            GlobalData.Jugador2[2] = Tropa_Arquero(4)
            GlobalData.Jugador2[3] = Tropa_Gigante(3)
            GlobalData.Jugador2[4] = Tropa_Gigante(5)
            GlobalData.Jugador2[5] = Tropa_Gigante(3)
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }
    }
    fun quinto_nivel(view: View){
        if(nivel_cinco) {
            GlobalData.Jugador2[0] = Rey_Arquero(15)
            GlobalData.Jugador2[1] = Tropa_Gigante(4)
            GlobalData.Jugador2[2] = Tropa_Gigante(4)
            GlobalData.Jugador2[3] = Tropa_Gigante(3)
            GlobalData.Jugador2[4] = Tropa_Lanzatonio(10)
            GlobalData.Jugador2[5] = Tropa_Gigante(3)
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }
    }
    fun sexto_nivel(view: View){
        if(nivel_seis) {
            GlobalData.Jugador2[0] = Rey_de_los_Gigantes(20)
            GlobalData.Jugador2[1] = Tropa_Gigante(5)
            GlobalData.Jugador2[2] = Tropa_Gigante(5)
            GlobalData.Jugador2[3] = Tropa_Lanzatonio(7)
            GlobalData.Jugador2[4] = Tropa_Arquero(20)
            GlobalData.Jugador2[5] = Tropa_Lanzatonio(7)
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }
    }
    fun nivel_secreto(view: View){
        if(nivel_secreto){
            GlobalData.Jugador2[0] = Rey_Heber(1)
            GlobalData.Jugador2[1] = Reyna_Darisce(1)
            GlobalData.Jugador2[2] = Reyna_Shantal(1)
            GlobalData.Jugador2[3] = Rey_Fernando(1)
            GlobalData.Jugador2[4] = Tropa_Gigante(10)
            GlobalData.Jugador2[5] = Rey_Cristian(1)
            GlobalData.decision = 1
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }

    }

}