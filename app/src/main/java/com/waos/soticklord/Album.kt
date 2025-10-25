package com.waos.soticklord

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import Data.*
import android.widget.ImageButton
import android.widget.ImageView
import java.lang.reflect.Modifier


class Album : AppCompatActivity() {
    val ataques = HashMap<Int, String>()
    var posicionAtaque = 0
    var rey_o_tropa = 0 //1 = Rey , 0 = Tropa
    var aumento = 0 //parra pasar a la siguiente pagina :v
    var listaTropas = GlobalData.Diccionario_Tropas.values.toList()
    var listaReyes = GlobalData.Diccionario_Reyes.values.toList()
    var listaNombres = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_album)

        // Pantalla completa
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
        cambiar_a_otro_album()
        mostrar_datos_economicos()
    }

    //Aparatdo derecho superior
    fun mostrar_datos_economicos(){
        val Nivel = findViewById<TextView>(R.id.Nivel_de_cuentawaos)
        Nivel.text = "N : ${GlobalData.nivel_De_cuenta} "
        val monedas = findViewById<TextView>(R.id.Monedass)
        monedas.text = "\uD83E\uDE99 : ${GlobalData.monedas}"
        val ecencia = findViewById<TextView>(R.id.Ecencia)
        ecencia.text = "✮⋆˙ : ${GlobalData.ecencia_de_juego}"
    }
    fun apretar_para_cambiar_a_otro_album(view: View){
        if(rey_o_tropa == 1){
            rey_o_tropa = 0
            val miBoton = findViewById<ImageButton>(R.id.Cambiar_de_rey_a_tropa)
            miBoton.setImageResource(R.drawable.icono_tropa)
            aumento = 0
            cambiar_a_otro_album()
            return
        }
        if(rey_o_tropa == 0){
            rey_o_tropa = 1
            val miBoton = findViewById<ImageButton>(R.id.Cambiar_de_rey_a_tropa)
            miBoton.setImageResource(R.drawable.icono_rey)
            aumento = 0
            cambiar_a_otro_album()
            return
        }


    }
    fun cambiar_a_otro_album(){
        //figuras
        val botonesIds = listOf(
            R.id.Personaje0, R.id.Personaje1, R.id.Personaje2, R.id.Personaje3, R.id.Personaje4,
            R.id.Personaje5, R.id.Personaje6, R.id.Personaje7, R.id.Personaje8, R.id.Personaje9,
            R.id.Personaje10, R.id.Personaje11, R.id.Personaje12, R.id.Personaje13, R.id.Personaje14
        )

        if(rey_o_tropa == 1){
            val Reyes = GlobalData.Diccionario_Reyes.values.toList()
            val botones = botonesIds.map { findViewById<ImageButton>(it) }
            cargar_botones_imagen(botones, Reyes)
        }
        if(rey_o_tropa == 0){
            val tropas = GlobalData.Diccionario_Tropas.values.toList()
            val botones = botonesIds.map { findViewById<ImageButton>(it) }
            cargar_botones_imagen(botones, tropas)
        }


    }
    //apartado derecho inferior
    fun cargar_botones_imagen(botones: List<ImageButton>, tropas: List<Tropa>) {
        for (i in botones.indices) {
            val index = i + aumento
            if (index < tropas.size) {
                botones[i].setImageResource(tropas[index].rutaviva)
                botones[i].visibility = View.VISIBLE
            } else {
                botones[i].visibility = View.GONE
            }
        }

    }

    fun mostrarInfoPersonaje(view: View) {
        val botonesIds = listOf(
            R.id.Personaje0, R.id.Personaje1, R.id.Personaje2, R.id.Personaje3, R.id.Personaje4,
            R.id.Personaje5, R.id.Personaje6, R.id.Personaje7, R.id.Personaje8, R.id.Personaje9,
            R.id.Personaje10, R.id.Personaje11, R.id.Personaje12, R.id.Personaje13, R.id.Personaje14
        )

        // Obtener índice del botón presionado
        val index = botonesIds.indexOf(view.id)
        if (index == -1) return

        val posicionReal = index + aumento

        if (rey_o_tropa == 0) {
            // 🟢 Si estamos viendo tropas
            val listaTropas = GlobalData.Diccionario_Tropas.values.toList()
            if (posicionReal < listaTropas.size) {
                val tropa = listaTropas[posicionReal]
                mostrarDatosDeTropa(tropa)
            }
        } else {
            // 🔵 Si estamos viendo reyes
            val listaReyes = GlobalData.Diccionario_Reyes.values.toList()
            if (posicionReal < listaReyes.size) {
                val rey = listaReyes[posicionReal]
                mostrarDatosDeRey(rey)
            }
        }
    }

    fun mostrarDatosDeTropa(tropa: Tropa) {

        val Nombre_rey_tropa = findViewById<TextView>(R.id.Nombre_rey_tropa)
        Nombre_rey_tropa.text = "${tropa.nombre}"
        val imagen = findViewById<ImageView>(R.id.Imagen_grande)
        imagen.setImageResource(tropa.rutaviva)
        val Nivel_rey_tropa = findViewById<TextView>(R.id.Nivel_rey_tropa)
        Nivel_rey_tropa.text = "N : ${tropa.nivel}"
        val Vida_rey_tropa = findViewById<TextView>(R.id.Vida_rey_tropa)
        Vida_rey_tropa.text = "♡  : ${tropa.vida}"
        val Ataque_rey_tropa = findViewById<TextView>(R.id.Ataque_rey_tropa)
        Ataque_rey_tropa.text = "⚔ : ${tropa.ataque_base}"
        val Ataques_disponibles = findViewById<TextView>(R.id.Ataques_disponibles)
        var ataquess =obtener_Ataques(tropa)
        Ataques_disponibles.text = ataquess
        listaNombres = Obtener_Array_String(tropa.nombre).toMutableList()
        actualizarAtaques(listaNombres)


    }
    fun mostrarDatosDeRey(tropa: Tropa) {
        val Nombre_rey_tropa = findViewById<TextView>(R.id.Nombre_rey_tropa)
        Nombre_rey_tropa.text = "${tropa.nombre}"
        val imagen = findViewById<ImageView>(R.id.Imagen_grande)
        imagen.setImageResource(tropa.rutaviva)
        val Nivel_rey_tropa = findViewById<TextView>(R.id.Nivel_rey_tropa)
        Nivel_rey_tropa.text = "N :${tropa.nivel}"
        val Vida_rey_tropa = findViewById<TextView>(R.id.Vida_rey_tropa)
        Vida_rey_tropa.text = "♡  : ${tropa.vida}"
        val Ataque_rey_tropa = findViewById<TextView>(R.id.Ataque_rey_tropa)
        Ataque_rey_tropa.text = "⚔ : ${tropa.ataque_base}"
        val Ataques_disponibles = findViewById<TextView>(R.id.Ataques_disponibles)
        var ataquess =obtener_Ataques(tropa)
        Ataques_disponibles.text = ataquess
        listaNombres = Obtener_Array_String(tropa.nombre).toMutableList()
        actualizarAtaques(listaNombres)

    }

    fun pasar_siguiente_hoja(view: View) {
        val botonesIds = listOf(
            R.id.Personaje0, R.id.Personaje1, R.id.Personaje2, R.id.Personaje3, R.id.Personaje4,
            R.id.Personaje5, R.id.Personaje6, R.id.Personaje7, R.id.Personaje8, R.id.Personaje9,
            R.id.Personaje10, R.id.Personaje11, R.id.Personaje12, R.id.Personaje13, R.id.Personaje14
        )
        val botones = botonesIds.map { findViewById<ImageButton>(it) }
        val maxPorPagina = 15

        if (rey_o_tropa == 0) {
            val total = GlobalData.Diccionario_Tropas.size
            if (aumento + maxPorPagina < total) {
                aumento += maxPorPagina
                cargar_botones_imagen(botones, listaTropas)
            }
        }

        if (rey_o_tropa == 1) {
            val total = GlobalData.Diccionario_Reyes.size
            if (aumento + maxPorPagina < total) {
                aumento += maxPorPagina
                cargar_botones_imagen(botones, listaReyes)
            }
        }
    }
    fun pasar_hoja_anterior(view: View) {
        val botonesIds = listOf(
            R.id.Personaje0, R.id.Personaje1, R.id.Personaje2, R.id.Personaje3, R.id.Personaje4,
            R.id.Personaje5, R.id.Personaje6, R.id.Personaje7, R.id.Personaje8, R.id.Personaje9,
            R.id.Personaje10, R.id.Personaje11, R.id.Personaje12, R.id.Personaje13, R.id.Personaje14
        )
        val botones = botonesIds.map { findViewById<ImageButton>(it) }
        val maxPorPagina = 15

        // Evita que baje de 0
        if (aumento - maxPorPagina >= 0) {
            aumento -= maxPorPagina
        } else {
            aumento = 0
        }

        // Carga según el tipo de álbum actual
        if (rey_o_tropa == 0) {
            val listaTropas = GlobalData.Diccionario_Tropas.values.toList()
            cargar_botones_imagen(botones, listaTropas)
        }

        if (rey_o_tropa == 1) {
            val listaReyes = GlobalData.Diccionario_Reyes.values.toList()
            cargar_botones_imagen(botones, listaReyes)
        }
    }

    fun pasarSiguienteAtaque(view: View) {
        if (ataques.isEmpty()) return

        // Obtener nombre del ataque actual
        val nombreAtaque = ataques[posicionAtaque]
        val descripcion = GlobalData.Diccionario_Ataques[nombreAtaque] ?: "Descripción no encontrada"

        val Nombre_de_Ataque = findViewById<TextView>(R.id.Nombre_de_Ataque)
        Nombre_de_Ataque.text = nombreAtaque
        val Info_ataques = findViewById<TextView>(R.id.Info_ataques)
        Info_ataques.text = descripcion

        // Pasar al siguiente
        posicionAtaque++
        if (posicionAtaque >= ataques.size) {
            posicionAtaque = 0 // vuelve al inicio
        }
    }

    fun pasarAtaqueAnterior(view: View) {
        if (ataques.isEmpty()) return

        // Retroceder
        posicionAtaque--
        if (posicionAtaque < 0) {
            posicionAtaque = ataques.size - 1 // vuelve al último
        }

        val nombreAtaque = ataques[posicionAtaque]
        val descripcion = GlobalData.Diccionario_Ataques[nombreAtaque] ?: "Descripción no encontrada"

        val Nombre_de_Ataque = findViewById<TextView>(R.id.Nombre_de_Ataque)
        Nombre_de_Ataque.text = nombreAtaque
        val Info_ataques = findViewById<TextView>(R.id.Info_ataques)
        Info_ataques.text = descripcion
    }


    fun actualizarAtaques(nuevosAtaques: List<String>) {
        ataques.clear() // Borra todo
        for ((index, ataque) in nuevosAtaques.withIndex()) {
            ataques[index] = ataque
        }
    }


    fun obtener_Ataques(obj: Any): String {
        val metodos_excluidos = listOf(
            "toString", "equals", "hashCode",
            "copyValueOf", "transform", "formatted", "intern",
            "wait", "notify", "notifyAll", "getClass",
            "clonar", "copyBase", "component1", "component2"
        )

        return obj::class.java.methods
            .map { it.name }
            .filter { it !in metodos_excluidos }
            .filter { !it.startsWith("get") && !it.startsWith("set") } // 🚫 sin getters/setters
            .distinct()
            .sorted() // opcional: orden alfabético
            .joinToString("\n") { "-$it" } // salida formateada
    } //para impirmir

    fun Obtener_Array_String(nombreClase: String): List<String> {
        return try {
            // Buscar la clase directamente en el diccionario
            val claseKotlin = GlobalData.Diccionario_Clases[nombreClase]
            if (claseKotlin != null) {
                claseKotlin.java.declaredMethods
                    .filter { Modifier.isPublic(it.modifiers) }
                    .map { it.name }
                    .filter { !it.startsWith("get") && !it.startsWith("set") }
                    .filterNot {
                        it in listOf(
                            "toString", "equals", "hashCode",
                            "copyValueOf", "transform", "formatted", "intern",
                            "wait", "notify", "notifyAll", "getClass",
                            "clonar", "copyBase", "component1", "component2"
                        )
                    }
            } else {
                println("No se encontró la clase '$nombreClase' en el diccionario.")
                emptyList()
            }
        } catch (e: Exception) {
            println("Error al obtener métodos de '$nombreClase': ${e.message}")
            emptyList()
        }
    } //para recorrer

    fun ordenar_por_nivel(){

    }
    fun ordenar_por_vida(){

    }
    fun ordenar_por_ataque() {
    }

    fun subir_de_nivel(){

    }






}