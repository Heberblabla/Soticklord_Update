package com.waos.soticklord

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import Data.Tropa
import Data.Tropa_Espadachin
import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlin.collections.get
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdView


class Principal : AppCompatActivity() {
    //atributos
    var indice_tropas = 0
    var indice_reyes = 0
    var indice_waos = 0
    // Lista de imágenes (la llenamos en onCreate)
    lateinit var imagenes: ArrayList<ImageView>
    private lateinit var bannerView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_principal)
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

        // AHORA ya puedes hacer findViewById
        imagenes = arrayListOf(
            findViewById(R.id.Cero),
            findViewById(R.id.Uno),
            findViewById(R.id.Dos),
            findViewById(R.id.Tres),
            findViewById(R.id.Cuatro),
            findViewById(R.id.Cinco)
        )
        Ocultar_imagenes()
    }

    //metodos

    fun batalla(view: View){
        if(GlobalData.Jugador1[0] != null &&
            GlobalData.Jugador1[1] != null &&
            GlobalData.Jugador1[2] != null &&
            GlobalData.Jugador1[3] != null &&
            GlobalData.Jugador1[4] != null &&
            GlobalData.Jugador1[5] != null) {
            if (GlobalData.decision == 1) {
                val intent = Intent(this, Batalla_oculta::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, Batalla::class.java)
                startActivity(intent)
                finish()
            }
        }else{
            Toast.makeText(this, "Selecciona bien tus tropas", Toast.LENGTH_SHORT).show()
        }
    }

    fun Ocultar_imagenes() {
        for (img in imagenes) {
            img.visibility = View.INVISIBLE
        }
        imagenes[indice_waos].visibility = View.VISIBLE
    }

    // Boton para q aparesca la siquiente imagen respectiva en el view grande
    fun Boton_siguiente_Mostrar(view: View) {
        mostrarImagen(1) // 1 = siguiente

    }
    // Boton para q aparesca la anterior imagen respectiva en el view grande
    fun Boton_Anterior_Mostrar(view: View) {
        mostrarImagen(-1) // -1 = anterior
    }


    private fun mostrarImagen(direccion: Int) {
        val imagenCentral = findViewById<ImageView>(R.id.Imagen_Central)

        if (indice_waos == 0) { //  Mostrar reyes
            val claves = GlobalData.Diccionario_Reyes.keys.toList()

            if (claves.isNotEmpty()) {
                indice_reyes = (indice_reyes + direccion + claves.size) % claves.size
                val claveActual = claves[indice_reyes]
                val seleccionada = GlobalData.Diccionario_Reyes[claveActual]
                GlobalData.ReySeleccionado = seleccionada?.clonar()
                imagenCentral.setImageResource(seleccionada?.rutaviva ?: R.drawable.tropa_default)
                seleccionada?.let { mostrar_datos(it) }

            }

        } else if (indice_waos in 1..5) { //  Mostrar tropas
            val claves = GlobalData.Diccionario_Tropas.keys.toList()

            if (claves.isNotEmpty()) {
                indice_tropas = (indice_tropas + direccion + claves.size) % claves.size
                val claveActual = claves[indice_tropas]
                val seleccionada = GlobalData.Diccionario_Tropas[claveActual]
                GlobalData.TropaSeleccionada = seleccionada?.clonar()
                imagenCentral.setImageResource(seleccionada?.rutaviva ?: R.drawable.tropa_default)
                seleccionada?.let { mostrar_datos(it) }
            }
        }
    }


    fun mostrar_datos(tropa: Tropa){
        findViewById<TextView>(R.id.Nombre_Waos)?.text = tropa.nombre
        findViewById<TextView>(R.id.nivel_rey_tropa)?.text = "N : ${tropa.nivel}"
        findViewById<TextView>(R.id.ataque_rey_tropa)?.text = "⚔ : ${tropa.ataque_base}"
        findViewById<TextView>(R.id.probabilidad_tropa_rey)?.text = "✢ : ${(tropa.probabilidad_de_critico * 100).toInt()}%"
        findViewById<TextView>(R.id.dano_critico_tropa_rey)?.text = "☠︎︎ : +${(tropa.daño_critico * 100).toInt()}%"
    }


    //indice de la tropa para asignarle un personaje / avanzar
    fun Boton_siguiente_tropa(view: View) {
            if (indice_waos == 5) {
                imagenes[indice_waos].visibility = View.INVISIBLE
                indice_waos = 4
                imagenes[indice_waos].visibility = View.VISIBLE
                mostrarImagen(1)
                return
            }
            if (indice_waos == 4) {
                imagenes[indice_waos].visibility = View.INVISIBLE
                indice_waos = 3
                imagenes[indice_waos].visibility = View.VISIBLE
                mostrarImagen(1)
                return
            }
            if (indice_waos == 3) {
                imagenes[indice_waos].visibility = View.INVISIBLE
                indice_waos = 2
                imagenes[indice_waos].visibility = View.VISIBLE
                mostrarImagen(1)
                return
            }
            if (indice_waos == 2) {
                imagenes[indice_waos].visibility = View.INVISIBLE
                indice_waos = 1
                imagenes[indice_waos].visibility = View.VISIBLE
                mostrarImagen(1)
                return
            }
            if (indice_waos == 1) {
                imagenes[indice_waos].visibility = View.INVISIBLE
                indice_waos = 0
                imagenes[indice_waos].visibility = View.VISIBLE
                mostrarImagen(1)
                return
            }
            if (indice_waos == 0) {
                imagenes[indice_waos].visibility = View.INVISIBLE
                indice_waos = 5
                imagenes[indice_waos].visibility = View.VISIBLE
                mostrarImagen(1)
                return
            }

        }

    //indice de la tropa para asignarle un personaje /retroceder
    fun Boton_Anterior_tropa(view: View) {
            if (indice_waos == 4) {
                imagenes[indice_waos].visibility = View.INVISIBLE
                indice_waos = 5
                imagenes[indice_waos].visibility = View.VISIBLE
                return
            }
            if (indice_waos == 3) {
                imagenes[indice_waos].visibility = View.INVISIBLE
                indice_waos = 4
                imagenes[indice_waos].visibility = View.VISIBLE
                return
            }
            if (indice_waos == 2) {
                imagenes[indice_waos].visibility = View.INVISIBLE
                indice_waos = 3
                imagenes[indice_waos].visibility = View.VISIBLE
                return
            }
            if (indice_waos == 1) {
                imagenes[indice_waos].visibility = View.INVISIBLE
                indice_waos = 2
                imagenes[indice_waos].visibility = View.VISIBLE
                return
            }
            if (indice_waos == 0) {
                imagenes[indice_waos].visibility = View.INVISIBLE
                indice_waos = 1
                imagenes[indice_waos].visibility = View.VISIBLE
                return
            }
            if (indice_waos == 5) {
                imagenes[indice_waos].visibility = View.INVISIBLE
                indice_waos = 0
                imagenes[indice_waos].visibility = View.VISIBLE
                return
            }

        }


    fun seleccionar(view: View){
        if (indice_waos == 5) {
            val imagenCentral = findViewById<ImageView>(R.id.Imagen_Central)
            val Imagen_Tropa_Frontal1 = findViewById<ImageView>(R.id.Imagen_Tropa_Frontal1)
            // Obtener el drawable actual de la imagen central
            val drawableActual = imagenCentral.drawable
            // Asignarlo al ImageView del Rey
            Imagen_Tropa_Frontal1.setImageDrawable(drawableActual)
            GlobalData.Jugador1[5] = GlobalData.TropaSeleccionada?.clonar()

            var vida = GlobalData.Jugador1[5]!!.vida
            val Vida_rey_tropa = findViewById<TextView>(R.id.Vida_Tropa_Frontal1)
            Vida_rey_tropa.text = "♡  : $vida"

            return
        }
        if (indice_waos == 4) {
            val imagenCentral = findViewById<ImageView>(R.id.Imagen_Central)
            val Imagen_Tropa_Frontal2 = findViewById<ImageView>(R.id.Imagen_Tropa_Frontal2)
            // Obtener el drawable actual de la imagen central
            val drawableActual = imagenCentral.drawable
            // Asignarlo al ImageView del Rey
            Imagen_Tropa_Frontal2.setImageDrawable(drawableActual)
            GlobalData.Jugador1[4] = GlobalData.TropaSeleccionada?.clonar()

            var vida = GlobalData.Jugador1[4]!!.vida
            val Vida_rey_tropa = findViewById<TextView>(R.id.Vida_Tropa_Frontal2)
            Vida_rey_tropa.text = "♡  : $vida"

            return
        }
        if (indice_waos == 3) {
            val imagenCentral = findViewById<ImageView>(R.id.Imagen_Central)
            val Imagen_Tropa_Frontal3 = findViewById<ImageView>(R.id.Imagen_Tropa_Frontal3)
            // Obtener el drawable actual de la imagen central
            val drawableActual = imagenCentral.drawable
            // Asignarlo al ImageView de Imagen_Tropa_Frontal3
            Imagen_Tropa_Frontal3.setImageDrawable(drawableActual)
            GlobalData.Jugador1[3] = GlobalData.TropaSeleccionada?.clonar()

            var vida = GlobalData.Jugador1[3]!!.vida
            val Vida_rey_tropa = findViewById<TextView>(R.id.Vida_Tropa_Frontal3)
            Vida_rey_tropa.text = "♡  : $vida"

            return
        }
        if (indice_waos == 2) {
            val imagenCentral = findViewById<ImageView>(R.id.Imagen_Central)
            val Imagen_Tropa_Segunda1 = findViewById<ImageView>(R.id.Imagen_Tropa_Segunda2)
            // Obtener el drawable actual de la imagen central
            val drawableActual = imagenCentral.drawable
            // Asignarlo al ImageView del Imagen_Tropa_Segunda2
            Imagen_Tropa_Segunda1.setImageDrawable(drawableActual)
            GlobalData.Jugador1[2] = GlobalData.TropaSeleccionada?.clonar()

            var vida = GlobalData.Jugador1[2]!!.vida
            val Vida_rey_tropa = findViewById<TextView>(R.id.Vida_Tropa_Segunda2)
            Vida_rey_tropa.text = "♡  : $vida"

            return
        }
        if (indice_waos == 1) {
            val imagenCentral = findViewById<ImageView>(R.id.Imagen_Central)
            val Imagen_Tropa_Segunda2 = findViewById<ImageView>(R.id.Imagen_Tropa_Segunda1)
            // Obtener el drawable actual de la imagen central
            val drawableActual = imagenCentral.drawable
            // Asignarlo al ImageView del Imagen_Tropa_Segunda1
            Imagen_Tropa_Segunda2.setImageDrawable(drawableActual)
            GlobalData.Jugador1[1] = GlobalData.TropaSeleccionada?.clonar()

            var vida = GlobalData.Jugador1[1]!!.vida
            val Vida_rey_tropa = findViewById<TextView>(R.id.Vida_Tropa_Segunda1)
            Vida_rey_tropa.text = "♡  : $vida"

            return
        }
        if (indice_waos == 0) {
            val imagenCentral = findViewById<ImageView>(R.id.Imagen_Central)
            val Imagen_Rey = findViewById<ImageView>(R.id.Imagen_Rey)
            // Obtener el drawable actual de la imagen central
            val drawableActual = imagenCentral.drawable
            // Asignarlo al ImageView del Rey
            Imagen_Rey.setImageDrawable(drawableActual)
            GlobalData.Jugador1[0] = GlobalData.ReySeleccionado?.clonar()

            var vida = GlobalData.Jugador1[0]!!.vida
            val Vida_rey_tropa = findViewById<TextView>(R.id.Vida_Rey)
            Vida_rey_tropa.text = "♡  : $vida"

            return
        }


    }

    fun atras(view: View){
            val intent = Intent(this, Mapa::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }



}





