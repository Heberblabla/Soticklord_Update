package com.waos.soticklord

import Data.*
import okhttp3.OkHttpClient
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import kotlin.reflect.full.primaryConstructor

var Diccionario_Reyes = HashMap<Int, Tropa>()
var Diccionario_Tropas = HashMap<Int, Tropa>()
val supabaseUrl = "https://zropeiibzqefzjrkdzzp.supabase.co"
val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inpyb3BlaWlienFlZnpqcmtkenpwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTkwMTc1NDYsImV4cCI6MjA3NDU5MzU0Nn0.ZJWqkOAbTul-RwIQrirajUSVdyI1w9Kh3kjek0vFMw8"
var id = 1
val nuevo = "xd"
fun main() {
    editar_nombre_de_la_tropa(1,nuevo)


}


fun waos(){
    cargar_Hash(1)
    println("=== REYES ===")
    for ((id, rey) in Diccionario_Reyes) {
        println("ID: $id")
        println(rey)
        println("--------------")
    }

    println("=== TROPAS ===")
    for ((id, tropa) in Diccionario_Tropas) {
        println("ID: $id")
        println(tropa)
        println("--------------")
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
                Diccionario_Reyes[id_tropa] = objeto
                println("Rey guardado en Diccionario_Reyes con id=$id_tropa y Nivel=$nivel")
            } else if (nombre.startsWith("Tropa_")) {
                Diccionario_Tropas[id_tropa] = objeto
                println("Tropa guardada en Diccionario_Tropas con id=$id_tropa y Nivel=$nivel")
            }
        } else {
            println("⚠️ El constructor de ${nombre} no tiene parámetro 'Nivel'. Se omitió la creación.")
        }

    }
}

fun editar_nombre_de_la_tropa(idTipo: Int, nuevoNombre: String): Boolean {
    val url = "$supabaseUrl/rest/v1/tipos_tropa?id_tipo=eq.$idTipo"

    val client = OkHttpClient()

    // el cuerpo del JSON con los nuevos datos
    val json = """
        {
            "nombre": "$nuevoNombre"
        }
    """.trimIndent()

    val requestBody = RequestBody.create(
        "application/json".toMediaTypeOrNull(), json
    )

    // se usa PATCH para actualizar parcialmente los datos
    val request = Request.Builder()
        .url(url)
        .patch(requestBody)
        .addHeader("apikey", apiKey)
        .addHeader("Authorization", "Bearer $apiKey")
        .addHeader("Content-Type", "application/json")
        .build()

    return try {
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            println("✅ Nombre actualizado correctamente")
            true
        } else {
            println("❌ Error al actualizar: ${response.code}")
            false
        }
    } catch (e: Exception) {
        e.printStackTrace()
        false
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



