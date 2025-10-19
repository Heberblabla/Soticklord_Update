package com.waos.soticklord

import Data.Tropa
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.reflect.full.primaryConstructor
import java.lang.reflect.Modifier

fun main(){
    println("hola")
    val lista = Obtener_Array_String("Data.Rey_Arquero")

}
val supabaseUrl = "https://zropeiibzqefzjrkdzzp.supabase.co"
val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inpyb3BlaWlienFlZnpqcmtkenpwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTkwMTc1NDYsImV4cCI6MjA3NDU5MzU0Nn0.ZJWqkOAbTul-RwIQrirajUSVdyI1w9Kh3kjek0vFMw8"


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