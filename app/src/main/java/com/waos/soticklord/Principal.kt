package com.waos.soticklord

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import Data.Tropa
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat


class Principal : AppCompatActivity() {
    //atributos
    var indice_waos = 0
    var indice_reyes = 1
    var indice_tropas = 1

    var Diccionario_Reyes = HashMap<Int, Tropa>()
    var Diccionario_Tropas = HashMap<Int, Tropa>()

    // Lista de imágenes (la llenamos en onCreate)
    lateinit var imagenes: ArrayList<ImageView>

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
        val mapa1 = intent.getSerializableExtra("mapa1") as? HashMap<Int, Tropa>
        val mapa2 = intent.getSerializableExtra("mapa2") as? HashMap<Int, Tropa>
        if (mapa1 != null) Diccionario_Reyes = mapa1
        if (mapa2 != null) Diccionario_Tropas = mapa2

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



    fun Ocultar_imagenes() {
        for (img in imagenes) {
            img.visibility = View.INVISIBLE
        }
        imagenes[indice_waos].visibility = View.VISIBLE
    }

    // Boton para q aparesca la siquiente imagen respectiva en el view grande
    fun Boton_siguiente_Mostrar(view: View) {
        val imagenCentral = findViewById<ImageView>(R.id.Imagen_Central)

        if (indice_waos >= 1 && indice_waos <= 5) {

            val seleccionada = Diccionario_Tropas[indice_tropas]
            //imagenCentral.setImageResource(seleccionada?.imagen ?: R.drawable.tropa_default)
            indice_tropas = indice_tropas + 1
            if (indice_tropas > Diccionario_Tropas.size)
                indice_tropas = 1
        }

        if (indice_waos == 0) {
            val seleccionada = Diccionario_Reyes[indice_reyes]
            //imagenCentral.setImageResource(seleccionada?.imagen ?: R.drawable.tropa_default)
            indice_reyes = indice_reyes + 1
            if (indice_reyes > Diccionario_Reyes.size)
                indice_reyes = 1

        }

    }
    // Boton para q aparesca la siquiente imagen respectiva en el view grande
    fun Boton_Anterior_Mostrar(view: View) {
        val imagenCentral = findViewById<ImageView>(R.id.Imagen_Central)

        if (indice_waos >= 1 && indice_waos <= 5) {

            val seleccionada = Diccionario_Tropas[indice_tropas]
            //imagenCentral.setImageResource(seleccionada?.imagen ?: R.drawable.tropa_default)
            indice_tropas = indice_tropas - 1
            if (indice_tropas < 0)
                indice_tropas = 5
        }

        if (indice_waos == 0) {
            val seleccionada = Diccionario_Reyes[indice_reyes]
            //imagenCentral.setImageResource(seleccionada?.imagen ?: R.drawable.tropa_default)
            indice_reyes = indice_reyes - 1
            if (indice_reyes < 0)
                indice_reyes = 5
        }
    }


    //indice de la tropa para asignarle un personaje / avanzar
    fun Boton_siguiente_tropa(view: View) {
            if (indice_waos == 5) {
                imagenes[indice_waos].visibility = View.INVISIBLE
                indice_waos = 4
                imagenes[indice_waos].visibility = View.VISIBLE
                return
            }
            if (indice_waos == 4) {
                imagenes[indice_waos].visibility = View.INVISIBLE
                indice_waos = 3
                imagenes[indice_waos].visibility = View.VISIBLE
                return
            }
            if (indice_waos == 3) {
                imagenes[indice_waos].visibility = View.INVISIBLE
                indice_waos = 2
                imagenes[indice_waos].visibility = View.VISIBLE
                return
            }
            if (indice_waos == 2) {
                imagenes[indice_waos].visibility = View.INVISIBLE
                indice_waos = 1
                imagenes[indice_waos].visibility = View.VISIBLE
                return
            }
            if (indice_waos == 1) {
                imagenes[indice_waos].visibility = View.INVISIBLE
                indice_waos = 0
                imagenes[indice_waos].visibility = View.VISIBLE
                return
            }
            if (indice_waos == 0) {
                imagenes[indice_waos].visibility = View.INVISIBLE
                indice_waos = 5
                imagenes[indice_waos].visibility = View.VISIBLE
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

    }



