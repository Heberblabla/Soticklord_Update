package com.waos.soticklord

import Data.Tropa
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import okhttp3.OkHttpClient
import okhttp3.*
import java.io.IOException
import android.widget.Switch
import androidx.constraintlayout.widget.ConstraintLayout


class Perfil : AppCompatActivity() {
    private val client = OkHttpClient()
    private val supabaseUrl = "https://zropeiibzqefzjrkdzzp.supabase.co"
    private val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inpyb3BlaWlienFlZnpqcmtkenpwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTkwMTc1NDYsImV4cCI6MjA3NDU5MzU0Nn0.ZJWqkOAbTul-RwIQrirajUSVdyI1w9Kh3kjek0vFMw8" //  key pública
    var id = 0
    var misMonedas = 0
    var miExperiencia = 0
    var misMedallas = 0
    var Diccionario_Reyes = HashMap<Int, Tropa>()
    var Diccionario_Tropas = HashMap<Int, Tropa>()
    var cantidad_reyes = 0
    var cantidad_tropas = 0
    var posicion = 0
    var estado = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)
        val layoutPrincipal = findViewById<ConstraintLayout>(R.id.main)
        val switchCambio = findViewById<Switch>(R.id.Cambio)

        switchCambio.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // 🟢 Modo tropas
                layoutPrincipal.setBackgroundResource(R.drawable.perfil_tropa)
                posicion = 0
                estado = false
                val boton = findViewById<Button>(R.id.Boton_Avanzar)
                siguiente_tropa(boton)


            } else {
                // ⚪ Modo rey
                layoutPrincipal.setBackgroundResource(R.drawable.perfil_rey)
                posicion = 0
                estado = true
                val boton = findViewById<Button>(R.id.Boton_Avanzar)
                siguiente_tropa(boton)
            }
        }
        // Ocultar barras del sistema
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        // Ajuste de paddings por barras
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 🔑 Recibir el ID del jugador
        val idJugador = intent.getIntExtra("ID_JUGADOR", -1)
        val hash1 = intent.getSerializableExtra("miHash1") as? HashMap<Int, Tropa>
        val hash2 = intent.getSerializableExtra("miHash2") as? HashMap<Int, Tropa>
        if (hash1 != null) Diccionario_Reyes = hash1
        if (hash2 != null) Diccionario_Tropas = hash2
        cantidad_reyes = Diccionario_Reyes.size
        cantidad_tropas = Diccionario_Tropas.size

        id = idJugador
        if (idJugador != -1) {
            val numero = idJugador.toInt()

            // Llamar a obtenerDatosJugador y actualizar los TextViews
            obtenerDatosJugador(numero) { monedas, experiencia, medallas ->
                misMonedas = monedas
                miExperiencia = experiencia
                misMedallas = medallas
                asignar_datos_principales()


            }

        } else {

            Toast.makeText(
                this@Perfil,
                "No se recivio ningun id de jugador",
                Toast.LENGTH_SHORT
                //lanza un mensaje emergente
            ).show()
        }



    }





    fun siguiente_tropa(view: View) {
        val imagenView = findViewById<ImageView>(R.id.Imagen)

        if (estado) {
            if (Diccionario_Reyes.isEmpty()) {
                Toast.makeText(this, "No hay reyes disponibles", Toast.LENGTH_SHORT).show()
                return
            }

            val listaReyes = Diccionario_Reyes.values.toList()
            val tropaActual = listaReyes[posicion]

            // Cambia imagen
            imagenView.setImageResource(tropaActual.rutaviva)

            // 🟢 Muestra los datos
            mostrarDatos(tropaActual)

            // Avanza al siguiente
            posicion = (posicion + 1) % listaReyes.size

        } else {
            if (Diccionario_Tropas.isEmpty()) {
                Toast.makeText(this, "No hay tropas disponibles", Toast.LENGTH_SHORT).show()
                return
            }

            val listaTropas = Diccionario_Tropas.values.toList()
            val tropaActual = listaTropas[posicion]

            // Cambia imagen
            imagenView.setImageResource(tropaActual.rutaviva)

            // 🟢 Muestra los datos
            mostrarDatos(tropaActual)

            // Avanza al siguiente
            posicion = (posicion + 1) % listaTropas.size
        }
    }

    fun anterior_tropa(view: View) {
        val imagenView = findViewById<ImageView>(R.id.Imagen)

        if (estado) {
            // 👑 Si estás en modo REY
            if (Diccionario_Reyes.isEmpty()) {
                Toast.makeText(this, "No hay reyes disponibles", Toast.LENGTH_SHORT).show()
                return
            }

            val listaReyes = Diccionario_Reyes.values.toList()

            // Retroceder correctamente en bucle
            posicion = if (posicion - 1 < 0) listaReyes.size - 1 else posicion - 1

            val tropaActual = listaReyes[posicion]

            // Actualizar imagen y texto
            imagenView.setImageResource(tropaActual.rutaviva)
            mostrarDatos(tropaActual)

        } else {
            // ⚔️ Si estás en modo TROPA
            if (Diccionario_Tropas.isEmpty()) {
                Toast.makeText(this, "No hay tropas disponibles", Toast.LENGTH_SHORT).show()
                return
            }

            val listaTropas = Diccionario_Tropas.values.toList()

            // Retroceder correctamente en bucle
            posicion = if (posicion - 1 < 0) listaTropas.size - 1 else posicion - 1

            val tropaActual = listaTropas[posicion]

            // Actualizar imagen y texto
            imagenView.setImageResource(tropaActual.rutaviva)
            mostrarDatos(tropaActual)
        }
    }

    private fun mostrarDatos(tropa: Tropa) {
        val nombreView = findViewById<TextView>(R.id.Nombre)
        val nivelView = findViewById<TextView>(R.id.Nivel_Personaje)
        val vidaView = findViewById<TextView>(R.id.Vida)
        val ataqueBaseView = findViewById<TextView>(R.id.Ataque_Base)

        nombreView.text = tropa.nombre
        nivelView.text = "Nivel: ${tropa.nivel}"
        vidaView.text = "Vida: ${tropa.vida}"
        ataqueBaseView.text = "Ataque: ${tropa.ataque_base}"
    }

    //boton menu
    fun entrar(view: View) {
        finish()

    }

    fun mapear(view: View){
        val intent = Intent(this, Mapa::class.java)
        startActivity(intent)
        finish()
    }
    // sacar datos principales de cuenta
    private fun asignar_datos_principales(){
        val Nivel = findViewById<TextView>(R.id.Nivel_General)
        val nuevo =  calcularNivel(miExperiencia)
        Nivel.text = nuevo.toString()
        val monedas = findViewById<TextView>(R.id.Monedas)
        monedas.text = misMonedas.toString()
        val Medallas = findViewById<TextView>(R.id.Medallas)
        Medallas.text = misMedallas.toString()
    }

    private fun calcularNivel(experiencia: Int): Int {
        var expRestante = experiencia
        var nivel = 1
        var expNecesaria = 1000

        while (expRestante >= expNecesaria) {
            expRestante -= expNecesaria
            nivel++

            // a partir del nivel 2 se aumenta 25%
            expNecesaria = (expNecesaria * 1.25).toInt()
        }

        return nivel
    }

    private fun obtenerDatosJugador(idJugador: Int, callback: (Int, Int, Int) -> Unit) {
        val url = "$supabaseUrl/rest/v1/jugadores?id_jugador=eq.$idJugador"
        val request = Request.Builder()
            .url(url)
            .addHeader("apikey", apiKey)
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Accept", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) {
                        runOnUiThread {
                            Toast.makeText(
                                this@Perfil,
                                "Error al sacar datos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        return
                    }

                    val body = it.body?.string()
                    println("Respuesta Supabase (jugador): $body")

                    if (!body.isNullOrEmpty() && body != "[]") {
                        // Ejemplo de respuesta: [{"monedas":200,"experiencia":4500,"medallas":10}]
                        val textoLimpio = body.replace("[", "").replace("]", "").trim()

                        // Usa una expresión regular para sacar los valores
                        val monedas = Regex("\"monedas\":(\\d+)").find(textoLimpio)?.groupValues?.get(1)?.toIntOrNull() ?: 0
                        val experiencia = Regex("\"experiencia\":(\\d+)").find(textoLimpio)?.groupValues?.get(1)?.toIntOrNull() ?: 0
                        val medallas = Regex("\"medallas\":(\\d+)").find(textoLimpio)?.groupValues?.get(1)?.toIntOrNull() ?: 0

                        // Devuelve los datos al callback en el hilo principal
                        runOnUiThread {
                            callback(monedas, experiencia, medallas)
                        }
                    }
                }
            }
        })
    }


}
