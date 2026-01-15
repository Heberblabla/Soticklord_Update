package Pruebas

import androidx.lifecycle.lifecycleScope
import com.waos.soticklord.DataManager
import com.waos.soticklord.GlobalData
import com.waos.soticklord.GlobalData.Diccionario_Reyes
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.*
import Cuenta_Verficacion.Iniciar_Sesion_Cuenta
import android.content.Context
import Data.*
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import android.widget.Toast
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.security.MessageDigest
import Cuenta_Verficacion.*
import kotlinx.serialization.json.put



fun mains() = runBlocking {

    var nombre = "HeberWaos"

    val supabase = createSupabaseClient(
        supabaseUrl = "https://xhkmibrcsdtysvwlhxev.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inhoa21pYnJjc2R0eXN2d2xoeGV2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjU3NTgzNjksImV4cCI6MjA4MTMzNDM2OX0.3V5MLAgv8OydwZtMAGFhVK8DTF_git5dNsdhnQiUWw4"

    ) {
        install(Postgrest)
    }

    println("Probando UPSERT...")

    // No usar mapOf. Usar JsonObject SIEMPRE.
    val params = buildJsonObject {
        put("p_nombre_jugador", nombre)
    }

    val result = supabase.postgrest.rpc(
        function = "obtener_reyes_jugador",
        parameters = params
    )

    val waos = result.data.toString()

    val resultado = HashMap<String, Int>()

    val jsonArray = Json.parseToJsonElement(waos).jsonArray
    val jsonObject = jsonArray[0].jsonObject

    val reyes = jsonObject["reyes"]!!.jsonArray
    val niveles = jsonObject["niveles"]!!.jsonArray

    for (i in reyes.indices) {
        val nombre = reyes[i].jsonPrimitive.content
        val nivel = niveles[i].jsonPrimitive.int
        resultado[nombre] = nivel
    }


    resultado.forEach { (nombre, nivel) ->
        println("Rey: $nombre  Nivel: $nivel")
    }

    println("${resultado.get("rey_mago")}")

}

fun guardar_datos_cuenta() = runBlocking {


    val supabase = createSupabaseClient(
        supabaseUrl = "https://xhkmibrcsdtysvwlhxev.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inhoa21pYnJjc2R0eXN2d2xoeGV2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjU3NTgzNjksImV4cCI6MjA4MTMzNDM2OX0.3V5MLAgv8OydwZtMAGFhVK8DTF_git5dNsdhnQiUWw4"

    ) {
        install(Postgrest)
    }

    println("Probando UPSERT...")

    // No usar mapOf. Usar JsonObject SIEMPRE.
    val params = buildJsonObject {
        put("upsert_jugador ", GlobalData.Nombre)
        put("p_nivel ", GlobalData.nivel_De_cuenta)
        put("p_moneda_global ", GlobalData.Moneda_Global)
        put("p_monedas ", GlobalData.monedas)
        put("p_esencia ", GlobalData.ecencia_de_juego)
        put("p_experiencia ", GlobalData.experiencia_de_juego)
        put("p_contrasena_hash ", generarHash("Heber2006"))
        put("p_nivel_progresion ", GlobalData.nivel_de_progresion)

    }

    val result = supabase.postgrest.rpc(
        function = "upsert_jugador",
        parameters = params
    )

    println("Resultado:")
    println(result.data)
}


fun main(){
    //GlobalData.Nombre = "HeberWaos"
    //GlobalData.id_usuario = 1
    //puntaje()
    println("xdd")

}

fun mainn(){
    GlobalData.Nombre = "HeberWaos"
    GlobalData.id_usuario = 1
    GlobalData.puntaje = 50
    //subir_puntaje()
    puntaje()
    println("puntaje : ${GlobalData.puntaje}")

}
fun maidn(){
    DataManager.ver_evento()
    println(" Waossssssssssssssss: ${GlobalData.datos_evento}")
    var textoRecibido = GlobalData.datos_evento
    val data = textoRecibido.trim()

    val partes = data.split("#")

    val tituloLimpio = partes[0]
        .replace("\\n", "")
        .replace("\n", "")
        .replace("\\", "")
        .replace("\"", "")
        .replace("n\"", "")
        .trim()


    val descripcion = partes[1]
    val rey = partes[2]
    val tropa1 = partes[3]
    val tropa2 = partes[4]
    val tropa3 = partes[5]
    val tropa4 = partes[6]
    val tropa5 = partes[7]
    val finalizacion = partes[8]


    println(tituloLimpio)
    println(descripcion)
    println(rey)
    println(tropa1)
    println(tropa2)
    println(tropa3)
    println(tropa4)
    println(tropa5)
    println(finalizacion)
}



fun puntaje() = runBlocking {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://xhkmibrcsdtysvwlhxev.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inhoa21pYnJjc2R0eXN2d2xoeGV2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjU3NTgzNjksImV4cCI6MjA4MTMzNDM2OX0.3V5MLAgv8OydwZtMAGFhVK8DTF_git5dNsdhnQiUWw4"

    ) {
        install(Postgrest)
    }

    println("consutladno premio....")

    // No usar mapOf. Usar JsonObject SIEMPRE.
    val params = buildJsonObject {
        put("p_id_jugador", GlobalData.id_usuario)
        put("p_nombre", GlobalData.Nombre)
    }

    val result = supabase.postgrest.rpc(
        function = "consultar_resultados",
        parameters = params
    )

    var xd = result.data.replace("\"", "")

    val partes = xd.split("#")

    if (partes.size >= 5) {
        val mensaje = partes[0]
        val dato1 = partes[1].toIntOrNull() ?: 0
        val dato2 = partes[2].toIntOrNull() ?: 0
        val dato3 = partes[3].toIntOrNull() ?: 0
        val dato4 = partes[4].toIntOrNull() ?: 0
    }



    println(xd)

}

fun subir_puntaje() = runBlocking{
    val supabase = createSupabaseClient(
        supabaseUrl = "https://xhkmibrcsdtysvwlhxev.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inhoa21pYnJjc2R0eXN2d2xoeGV2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjU3NTgzNjksImV4cCI6MjA4MTMzNDM2OX0.3V5MLAgv8OydwZtMAGFhVK8DTF_git5dNsdhnQiUWw4"

    ) {
        install(Postgrest)
    }

    println("Guardando datos nube....")

    // No usar mapOf. Usar JsonObject SIEMPRE.
    val params = buildJsonObject {
        put("p_id_jugador", GlobalData.id_usuario)
        put("p_nombre", GlobalData.Nombre)
        put("p_puntaje", GlobalData.puntaje)

    }

    val result = supabase.postgrest.rpc(
        function = "fn_evento_puntaje",
        parameters = params
    )

    var xd = result.data
    println("completado.... : $xd")


}


fun generarHash(texto: String): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val hashBytes = digest.digest(texto.toByteArray(Charsets.UTF_8))

    return hashBytes.joinToString("") {
        "%02x".format(it)
    }
}