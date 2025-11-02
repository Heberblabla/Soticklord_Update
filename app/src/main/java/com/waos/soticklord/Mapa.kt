package com.waos.soticklord

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import Data.*
import Data.Especiales.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdView

class Mapa : AppCompatActivity() {
    var nivel_uno = true
    var nivel_dos = false
    var nivel_tres = false
    var nivel_cuatro = false
    var nivel_cinco = false
    var nivel_seis = false
    var nivel_secreto = false
    private lateinit var bannerView: AdView
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

        // 1️⃣ Inicializa el SDK de AdMob
        MobileAds.initialize(this) {}
        // 2️⃣ Conecta tu banner del XML
        bannerView = findViewById(R.id.bannerView)
        // 3️⃣ Crea una solicitud de anuncio
        val adRequest = AdRequest.Builder().build()
        // 4️⃣ Carga el anuncio
        bannerView.loadAd(adRequest)

        imagenes = arrayListOf(
            findViewById(R.id.Estrella_nivel1),
            findViewById(R.id.Estrella_nivel2),
            findViewById(R.id.Estrella_nivel3),
            findViewById(R.id.Estrella_nivel4),
            findViewById(R.id.Estrella_nivel5),
            findViewById(R.id.Estrella_nivel6)
        )

        actualizar_estados()
    }

    fun atras(view: View) {
        val intent = Intent(this, Escoger_modo::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun actualizar_estados() {
        val progreso = GlobalData.nivel_de_progresion

        // reiniciar
        nivel_uno = false
        nivel_dos = false
        nivel_tres = false
        nivel_cuatro = false
        nivel_cinco = false
        nivel_seis = false
        nivel_secreto = false

        for (i in imagenes.indices) {
            val img = imagenes[i]

            when {
                //  completado
                progreso > i -> {
                    img.setImageResource(R.drawable.estrella2)
                    img.isEnabled = true
                    img.alpha = 1f
                }
                // disponible (aún no completado)
                progreso == i -> {
                    img.setImageResource(R.drawable.boton_atras)
                    img.rotation = -90f   // 🔁 gira -90 grados en eje Z
                    img.isEnabled = true
                    img.alpha = 1f
                }
                // bloqueado
                else -> {
                    img.setImageResource(R.drawable.candado)
                    img.isEnabled = false
                    img.alpha = 0.5f
                }
            }
        }

        // actualizar variables booleanas
        if (progreso >= 0) nivel_uno = true
        if (progreso >= 1) nivel_dos = true
        if (progreso >= 2) nivel_tres = true
        if (progreso >= 3) nivel_cuatro = true
        if (progreso >= 4) nivel_cinco = true
        if (progreso >= 5) nivel_seis = true
        if (progreso >= 6) nivel_secreto = true
    }

    // ===========================
    //      NIVELES NORMALES
    // ===========================

    fun primer_nivel(view: View) {
        if (nivel_uno) {
            GlobalData.nivel_actico = 0
            GlobalData.Jugador2[0] = Rey_Espadachin(5)
            GlobalData.Jugador2[1] = Tropa_Arquero(2)
            GlobalData.Jugador2[2] = Tropa_Arquero(2)
            GlobalData.Jugador2[3] = Tropa_Espadachin(3)
            GlobalData.Jugador2[4] = Tropa_Espadachin(3)
            GlobalData.Jugador2[5] = Tropa_Espadachin(3)
            GlobalData.decision = 0
            irABatalla()
        } else nivelBloqueado()
    }

    fun segundo_nivel(view: View) {
        if (nivel_dos) {
            GlobalData.nivel_actico = 1
            GlobalData.Jugador2[0] = Rey_Lanzatonio(7)
            GlobalData.Jugador2[1] = Tropa_Arquero(4)
            GlobalData.Jugador2[2] = Tropa_Arquero(4)
            GlobalData.Jugador2[3] = Tropa_Lanzatonio(3)
            GlobalData.Jugador2[4] = Tropa_Espadachin(5)
            GlobalData.Jugador2[5] = Tropa_Lanzatonio(3)
            GlobalData.decision = 0
            irABatalla()
        } else nivelBloqueado()
    }

    fun tercer_nivel(view: View) {
        if (nivel_tres) {
            GlobalData.nivel_actico = 2
            GlobalData.Jugador2[0] = Rey_Arquero(10)
            GlobalData.Jugador2[1] = Tropa_Arquero(5)
            GlobalData.Jugador2[2] = Tropa_Arquero(5)
            GlobalData.Jugador2[3] = Tropa_Lanzatonio(3)
            GlobalData.Jugador2[4] = Tropa_Lanzatonio(5)
            GlobalData.Jugador2[5] = Tropa_Lanzatonio(3)
            GlobalData.decision = 0
            irABatalla()
        } else nivelBloqueado()
    }

    fun cuarto_nivel(view: View) {
        if (nivel_cuatro) {
            GlobalData.nivel_actico = 3
            GlobalData.Jugador2[0] = Rey_Arquero(15)
            GlobalData.Jugador2[1] = Tropa_Arquero(4)
            GlobalData.Jugador2[2] = Tropa_Arquero(4)
            GlobalData.Jugador2[3] = Tropa_Gigante(3)
            GlobalData.Jugador2[4] = Tropa_Gigante(5)
            GlobalData.Jugador2[5] = Tropa_Gigante(3)
            GlobalData.decision = 0
            irABatalla()
        } else nivelBloqueado()
    }

    fun quinto_nivel(view: View) {
        if (nivel_cinco) {
            GlobalData.nivel_actico = 4
            GlobalData.Jugador2[0] = Rey_Arquero(15)
            GlobalData.Jugador2[1] = Tropa_Gigante(4)
            GlobalData.Jugador2[2] = Tropa_Gigante(4)
            GlobalData.Jugador2[3] = Tropa_Gigante(3)
            GlobalData.Jugador2[4] = Tropa_Lanzatonio(10)
            GlobalData.Jugador2[5] = Tropa_Gigante(3)
            GlobalData.decision = 0
            irABatalla()
        } else nivelBloqueado()
    }

    fun sexto_nivel(view: View) {
        if (nivel_seis) {
            GlobalData.nivel_actico = 5
            GlobalData.Jugador2[0] = Rey_de_los_Gigantes(20)
            GlobalData.Jugador2[1] = Tropa_Gigante(5)
            GlobalData.Jugador2[2] = Tropa_Gigante(5)
            GlobalData.Jugador2[3] = Tropa_Lanzatonio(7)
            GlobalData.Jugador2[4] = Tropa_Arquero(20)
            GlobalData.Jugador2[5] = Tropa_Lanzatonio(7)
            GlobalData.decision = 0
            irABatalla()
        } else nivelBloqueado()
    }

    fun nivel_secreto(view: View) {
        if (nivel_secreto) {
            GlobalData.nivel_actico = 6
            GlobalData.Jugador2[0] = Rey_Heber(GlobalData.nivel_de_progresion + 10)
            GlobalData.Jugador2[1] = Reyna_Darisce(GlobalData.nivel_de_progresion + 10)
            GlobalData.Jugador2[2] = Reyna_Shantal(GlobalData.nivel_de_progresion + 10)
            GlobalData.Jugador2[3] = Rey_Fernando(GlobalData.nivel_de_progresion + 10)
            GlobalData.Jugador2[4] = Tropa_Gigante(GlobalData.nivel_de_progresion + 20)
            GlobalData.Jugador2[5] = Rey_Cristian(GlobalData.nivel_de_progresion + 10)
            GlobalData.decision = 1
            irABatalla()
        } else nivelBloqueado()
    }

    // ===========================
    //      FUNCIONES EXTRA
    // ===========================

    private fun irABatalla() {
        val intent = Intent(this, Principal::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    private fun nivelBloqueado() {
        Toast.makeText(this, "Nivel bloqueado", Toast.LENGTH_SHORT).show()
    }

    fun practicar(view: View){
        val intent = Intent(this, Enemigo_personalizacion::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
}
