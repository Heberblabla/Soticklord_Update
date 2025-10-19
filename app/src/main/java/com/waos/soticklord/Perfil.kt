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
    var posicion = 0
    var estado = true // true = Reyes, false = Tropas

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

        if (GlobalData.id_usuario != -1) {
            val numero = GlobalData.id_usuario.toInt()
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
        cambiarTropa(+1)
    }

    fun anterior_tropa(view: View) {
        cambiarTropa(-1)
    }

    private fun cambiarTropa(direccion: Int) {
        val imagenView = findViewById<ImageView>(R.id.Imagen)

        // Selecciona el diccionario y su lista según el estado
        val lista = if (estado) {
            GlobalData.Diccionario_Reyes.values.toList()
        } else {
            GlobalData.Diccionario_Tropas.values.toList()
        }

        // Si no hay datos
        if (lista.isEmpty()) {
            val tipo = if (estado) "reyes" else "tropas"
            Toast.makeText(this, "No hay $tipo disponibles", Toast.LENGTH_SHORT).show()
            return
        }

        // Ajustar posición en bucle circular (soporta avance y retroceso)
        posicion = (posicion + direccion + lista.size) % lista.size

        // Mostrar tropa/reino actual
        val actual = lista[posicion]
        imagenView.setImageResource(actual.rutaviva)
        mostrarDatos(actual)
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
        val intent = Intent(this, Principal::class.java)
        startActivity(intent)
    }

    fun mapear(view: View){
        val intent = Intent(this, Mapa::class.java)
        startActivity(intent)
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
