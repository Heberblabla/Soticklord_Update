package com.waos.soticklord

import Data.Tropa
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.reflect.full.primaryConstructor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Pantalla_de_Carga : AppCompatActivity() {
    val supabaseUrl = "https://zropeiibzqefzjrkdzzp.supabase.co"
    val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inpyb3BlaWlienFlZnpqcmtkenpwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTkwMTc1NDYsImV4cCI6MjA3NDU5MzU0Nn0.ZJWqkOAbTul-RwIQrirajUSVdyI1w9Kh3kjek0vFMw8"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla_de_carga)

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

        // Configurar video de fondo
        val videoView = findViewById<VideoView>(R.id.videoSplash)
        val videoUri = Uri.parse("android.resource://$packageName/${R.raw.intro}")
        videoView.setVideoURI(videoUri)

        // Repetir el video en bucle
        videoView.setOnPreparedListener { mp ->
            mp.isLooping = true
            mp.start()
        }


        // Cargar datos en segundo plano mientras el video sigue corriendo
        CoroutineScope(Dispatchers.IO).launch {
            cargar_Hash(GlobalData.id_usuario) // tu función que trae los datos del servidor

            // Cuando termina la carga, volvemos al hilo principal
            withContext(Dispatchers.Main) {
                val intent = Intent(this@Pantalla_de_Carga, Perfil::class.java)
                startActivity(intent)
                videoView.stopPlayback()
                finish()
            }

        }
    }

    fun cargar_Hash(idJugador: Int) {
        val ids = sacar_los_id_tropa(idJugador) // devuelve todas las PK de tropas_jugador
        println("🟢 Tropas del jugador: $ids")

        for (id_tropa in ids) {
            val id_tipo = sacar_id_Tipo(id_tropa)
            println("🔹 id_tropa=$id_tropa → id_tipo=$id_tipo")

            val nivel = sacar_nivel(id_tropa)
            println("🔹 Nivel de tropa ($id_tropa): $nivel")

            val nombre = obtener_nombre_de_la_tropa(id_tipo)
            println("🔹 Nombre de tropa ($id_tipo): $nombre")

            val claseCompleta = "Data.$nombre"
            println("🧩 Clase completa: $claseCompleta")
            // cargar la clase en tiempo de ejecución
            var clazz = Class.forName(claseCompleta).kotlin
            var constructor = clazz.primaryConstructor!!

            // Buscar el parámetro "nivel" en el constructor
            val parametroNivel = constructor.parameters.find { it.name == "Nivel" }

            if (parametroNivel != null) {
                // Crear objeto con el nivel si existe ese parámetro
                var objeto = constructor.callBy(mapOf(parametroNivel to nivel)) as Tropa
                if (nombre.startsWith("Rey_")) {
                    GlobalData.Diccionario_Reyes[id_tropa] = objeto
                    println("Rey guardado en Diccionario_Reyes con id=$id_tropa y Nivel=$nivel")
                } else if (nombre.startsWith("Tropa_")) {
                    GlobalData.Diccionario_Tropas[id_tropa] = objeto
                    println("Tropa guardada en Diccionario_Tropas con id=$id_tropa y Nivel=$nivel")
                }
            } else {
                println("️ El constructor de ${nombre} no tiene parámetro 'Nivel'. Se omitió la creación.")
            }

        }
    }

    fun obtener_nombre_de_la_tropa(idTipo: Int): String {
        val url = "$supabaseUrl/rest/v1/tipos_tropa?id_tipo=eq.$idTipo&select=nombre"

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .addHeader("apikey", apiKey)
            .addHeader("Authorization", "Bearer $apiKey")
            .build()

        var nombre = ""

        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val body = response.body?.string()


                if (!body.isNullOrEmpty() && body != "[]") {
                    // Supabase devuelve algo como: [{"nombre":"Rey de los Gigantes"}]
                    val textoLimpio = body.replace("[", "").replace("]", "").trim()
                    val partes = textoLimpio.split(":")
                    if (partes.size > 1) {
                        // quita comillas, llaves y espacios
                        nombre = partes[1]
                            .replace("}", "")
                            .replace("\"", "")
                            .trim()
                    }
                }
            } else {
                println("Error en la respuesta: ${response.code}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return nombre
    }


    fun sacar_nivel(idTropa: Int): Int {
        val url = "$supabaseUrl/rest/v1/tropas_jugador?id_tropa=eq.$idTropa&select=nivel"

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .addHeader("apikey", apiKey)
            .addHeader("Authorization", "Bearer $apiKey")
            .build()

        var nivel = 0

        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val body = response.body?.string()


                if (!body.isNullOrEmpty() && body != "[]") {
                    // Supabase devuelve algo como: [{"nivel":5}]
                    val textoLimpio = body.replace("[", "").replace("]", "")
                    val partes = textoLimpio.split(":")
                    if (partes.size > 1) {
                        nivel = partes[1].replace("}", "").trim().toInt()
                    }
                }
            } else {
                println("Error en la respuesta: ${response.code}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return nivel
    }


    fun sacar_id_Tipo(idTropa: Int): Int {
        val url = "$supabaseUrl/rest/v1/tropas_jugador?id_tropa=eq.$idTropa&select=id_tipo"
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .addHeader("apikey", apiKey)
            .addHeader("Authorization", "Bearer $apiKey")
            .build()

        var id_tipo = 0

        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val body = response.body?.string()


                if (!body.isNullOrEmpty() && body != "[]") {
                    // Supabase devuelve algo como: [{"id_tipo":3}]
                    val textoLimpio = body.replace("[", "").replace("]", "")
                    val partes = textoLimpio.split(":")
                    if (partes.size > 1) {
                        id_tipo = partes[1].replace("}", "").trim().toInt()
                    }
                }
            } else {
                println("Error en la respuesta: ${response.code}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return id_tipo
    }


    fun sacar_los_id_tropa(idJugador: Int): List<Int> {
        val url = "$supabaseUrl/rest/v1/tropas_jugador?id_jugador=eq.$idJugador&select=id_tropa"


        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .addHeader("apikey", apiKey)
            .addHeader("Authorization", "Bearer $apiKey")
            .build()

        val miListaMutable = mutableListOf<Int>()

        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val body = response.body?.string() ?: ""


                // Busca todos los números después de "id_tipo":
                val regex = Regex("\"id_tropa\"\\s*:\\s*(\\d+)")
                val matches = regex.findAll(body)

                for (m in matches) {
                    val idTipo = m.groupValues[1].toInt()
                    miListaMutable.add(idTipo)
                }
            } else {
                println("Error HTTP: ${response.code}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return miListaMutable
    }


}
