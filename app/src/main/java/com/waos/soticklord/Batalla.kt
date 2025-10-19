package com.waos.soticklord

import Data.Rey_Arquero
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import android.widget.TextView
import android.widget.ArrayAdapter
import android.widget.Spinner
import java.lang.reflect.Modifier
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.KVisibility
import java.lang.reflect.Method
import Data.*




class Batalla : AppCompatActivity() {

    var Enemigo_Seleccionado = 0
    var turno_activo = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_batalla)

        // Oculta las barras del sistema
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

        cargar_datos1()
        cargar_datos2()
        cargar_vida1()
        cargar_vida2()
        cargar()

    }


    fun atacar_pasarturno(){
        var turno_activo = 1
    }


    fun cargar_datos1() {
        // Lista de los IDs de los ImageView en el mismo orden que los índices
        val idsImagenes = listOf(
            R.id.TropaAa,
            R.id.TropaBa,
            R.id.TropaBb,
            R.id.TropaCa,
            R.id.TropaCb,
            R.id.TropaCc
        )

        // Recorremos los índices del 0 al 5
        for (i in idsImagenes.indices) {
            val imageView = findViewById<ImageView>(idsImagenes[i])
            val tropa = GlobalData.Jugador1.getOrNull(i)

            if (tropa != null && tropa.estado_de_vida) {
                // Si existe tropa en esa posición, se carga su imagen viva
                imageView.setImageResource(tropa.rutaviva)
            } else {
                // Si no hay tropa, se pone imagen por defecto
                imageView.setImageResource(R.drawable.tropa_default)
            }
        }
    }

    fun cargar_datos2() {
        // IDs de las imágenes del Jugador2 en orden según el índice del array
        val idsImagenes = listOf(
            R.id.TropaFa, // índice 0
            R.id.TropaEa, // índice 1
            R.id.TropaEb, // índice 2
            R.id.TropaDa, // índice 3
            R.id.TropaDb, // índice 4
            R.id.TropaDc, // índice 5
        )

        for (i in idsImagenes.indices) {
            val imageView = findViewById<ImageView>(idsImagenes[i])
            val tropa = GlobalData.Jugador2.getOrNull(i)

            if (tropa != null && tropa.estado_de_vida) {
                imageView.setImageResource(tropa.rutaviva)
            } else {
                imageView.setImageResource(R.drawable.tropa_default)
            }

            // Voltear horizontalmente para que mire hacia la izquierda
            imageView.scaleX = -1f
        }


    }

    //sirven para cargar como para actualizar vidas xd
    fun cargar_vida1() {
        // IDs de los TextView correspondientes
        val idsTextos = listOf(
            R.id.vidaAa, // 0
            R.id.vidaBa, // 1
            R.id.vidaBb, // 2
            R.id.vidaCa, // 3
            R.id.vidaCb, // 4
            R.id.vidaCc  // 5
        )

        // Recorremos los índices
        for (i in idsTextos.indices) {
            val textView = findViewById<TextView>(idsTextos[i])
            val tropa = GlobalData.Jugador1.getOrNull(i)

            if (tropa != null) {
                textView.text = "♡: ${tropa.vida}"
            } else {
                textView.text = "♡: --"
            }
        }
    }

    fun cargar_vida2() {
        // IDs de los TextView correspondientes
        val idsTextos = listOf(
            R.id.vidaFa, // 0
            R.id.vidaEb, // 1
            R.id.vidaEa, // 2
            R.id.vidaDc, // 3
            R.id.vidaDb, // 4
            R.id.vidaDa  // 5
        )

        // Recorremos los índices
        for (i in idsTextos.indices) {
            val textView = findViewById<TextView>(idsTextos[i])
            val tropa = GlobalData.Jugador2.getOrNull(i)

            if (tropa != null) {
                textView.text = "♡: ${tropa.vida}"
            } else {
                textView.text = "♡: --"
            }
        }
    }

    fun cargar(){
        val lista = Obtener_Array_String("Data.Rey_Arquero")
        actualizarSpinnerAtaques(lista)

    }

    fun Obtener_Array_String(nombreClase: String): List<String> {
        return try {
            val clase = Class.forName(nombreClase)
            clase.declaredMethods // solo los definidos en la clase
                .filter { Modifier.isPublic(it.modifiers) } // solo públicos
                .map { it.name }
                .filter { it != "toString" } // ignora toString aunque exista en la hija
        } catch (e: ClassNotFoundException) {
            println("No se encontró la clase: $nombreClase")
            emptyList()
        }
    }




    fun actualizarSpinnerAtaques(lista: List<String>) {
        val spinner = findViewById<Spinner>(R.id.Ataques)

        val adaptador = ArrayAdapter(
            spinner.context,
            android.R.layout.simple_spinner_item,
            lista
        )
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adaptador
    }
    fun Imagenes_claras(){
        val imagen1 = findViewById<ImageView>(R.id.TropaDa)
        val imagen2 = findViewById<ImageView>(R.id.TropaDb)
        val imagen3 = findViewById<ImageView>(R.id.TropaDc)
        val imagen4 = findViewById<ImageView>(R.id.TropaEa)
        val imagen5 = findViewById<ImageView>(R.id.TropaEb)
        val imagen6 = findViewById<ImageView>(R.id.TropaFa)
        imagen1.alpha = 1.0f
        imagen2.alpha = 1.0f
        imagen3.alpha = 1.0f
        imagen4.alpha = 1.0f
        imagen5.alpha = 1.0f
        imagen6.alpha = 1.0f

    }
    fun seleccionar_tropaDa(view: View){
        Imagenes_claras()
        Enemigo_Seleccionado = 5
        val imagen = findViewById<ImageView>(R.id.TropaDa)
        imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente

    }
    fun seleccionar_tropaDb(view: View){
        Imagenes_claras()
        Enemigo_Seleccionado = 4
        val imagen = findViewById<ImageView>(R.id.TropaDb)
        imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente

    }
    fun seleccionar_tropaDc(view: View){
        Imagenes_claras()
        Enemigo_Seleccionado = 3
        val imagen = findViewById<ImageView>(R.id.TropaDc)
        imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente

    }
    fun seleccionar_tropaEa(view: View){
        Imagenes_claras()
        Enemigo_Seleccionado = 2
        val imagen = findViewById<ImageView>(R.id.TropaEa)
        imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente

    }
    fun seleccionar_tropaEb(view: View){
        Imagenes_claras()
        Enemigo_Seleccionado = 1
        val imagen = findViewById<ImageView>(R.id.TropaEb)
        imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente

    }
    fun seleccionar_tropaFa(view: View){
        Imagenes_claras()
        Enemigo_Seleccionado = 0
        val imagen = findViewById<ImageView>(R.id.TropaFa)
        imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente

    }



    fun ejecutarAtaquePorReflexion(
        jugador1: ArrayList<Tropa?>,
        jugador2: ArrayList<Tropa?>,
        posicion1: Int,
        posicion2: Int,
        nombreMetodo: String
    ) {
        try {
            // Obtener la tropa atacante y la clase de esa tropa
            val tropaAtacante = jugador1[posicion1] ?: return
            val clase = tropaAtacante::class.java
            val nombreClase = clase.simpleName

            println("Clase atacante: $nombreClase")

            // Buscar el método con el nombre exacto
            val metodo: Method? = clase.declaredMethods.find { m ->
                m.name == nombreMetodo
            }

            if (metodo != null) {
                println("Método encontrado: ${metodo.name}")

                // Invocar el método sobre la instancia de la tropa atacante
                // Le pasamos jugador2 y la posición del enemigo
                metodo.invoke(tropaAtacante, jugador2, posicion2)
            } else {
                println("No se encontró el método '$nombreMetodo' en la clase $nombreClase")
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }




}