package com.waos.soticklord

import Data.*
import okhttp3.OkHttpClient
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject

import java.io.IOException
import kotlin.reflect.full.primaryConstructor

var Diccionario_Reyes = HashMap<Int, Tropa>()
var Diccionario_Tropas = HashMap<Int, Tropa>()
val client = OkHttpClient()
val supabaseUrl = "https://zropeiibzqefzjrkdzzp.supabase.co"
val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inpyb3BlaWlienFlZnpqcmtkenpwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTkwMTc1NDYsImV4cCI6MjA3NDU5MzU0Nn0.ZJWqkOAbTul-RwIQrirajUSVdyI1w9Kh3kjek0vFMw8"
var id = 1
fun main() {
    cargar_Hash(1)



}

 fun cargar_Hash(idJugador: Int) {
    val ids = sacar_los_id_tropa(idJugador) // devuelve todas las PK de tropas_jugador
     println(ids)
    for (id_tropa in ids) {
        val id_tipo = sacar_id_Tipo(id_tropa)
        val nivel = sacar_nivel(id_tropa)
        val nombre = obtener_nombre_de_la_tropa(id_tipo)
        val claseCompleta = "Data.$nombre"   // paquete + nombre de la clase

        // cargar la clase en tiempo de ejecución
        val clazz = Class.forName(claseCompleta).kotlin
        val constructor = clazz.primaryConstructor!!

        // crear objeto pasando solo el parámetro nivel
        val objeto = constructor.callBy(
            mapOf(constructor.parameters.find { it.name == "nivel" }!! to nivel)
        ) as Tropa  // casteo a Tropa porque el HashMap lo espera

        // Guardar en el diccionario correspondiente
        if (nombre.startsWith("Rey_")) {
            Diccionario_Reyes[id_tropa] = objeto
            println("Rey guardado en Diccionario_Reyes con id=$id_tropa y nivel=$nivel")
        } else if (nombre.startsWith("Tropa_")) {
            Diccionario_Tropas[id_tropa] = objeto
            println("Tropa guardada en Diccionario_Tropas con id=$id_tropa y nivel=$nivel")
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

    var nombre  = ""

    try {
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val body = response.body?.string()
            if (body != null) {
                val jsonArray = JSONArray(body)
                if (jsonArray.length() > 0) {
                    val obj = jsonArray.getJSONObject(0)
                    nombre = obj.getString("nombre")
                }
            }
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
            if (body != null) {
                val jsonArray = JSONArray(body)
                if (jsonArray.length() > 0) {
                    val obj = jsonArray.getJSONObject(0)
                    nivel = obj.getInt("nivel")
                }
            }
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
            if (body != null) {
                val jsonArray = JSONArray(body)
                if (jsonArray.length() > 0) {
                    val obj = jsonArray.getJSONObject(0)
                    id_tipo = obj.getInt("id_tipo")
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return id_tipo
}

 fun sacar_los_id_tropa(idJugador: Int): List<Int> {
    val url = "$supabaseUrl/rest/v1/tropas_jugador?id_jugador=eq.$idJugador&select=id_tipo"
    println(url)
    val client = OkHttpClient()
     val request = Request.Builder().url(url).addHeader("apikey", apiKey).addHeader("Authorization", "Bearer $apiKey").build()
    println(request)
    val miListaMutable = mutableListOf<Int>()

    try {
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val body = response.body?.string()
            if (body != null) {
                // el resultado de Supabase es un JSON array
                val jsonArray = JSONArray(body)
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val idTropaJugador = obj.getInt("id_tropa")
                    miListaMutable.add(idTropaJugador)
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return miListaMutable
}


