package com.waos.soticklord

import Archivos_Extra.GestorEventos
import Cuenta_Verficacion.Iniciar_Sesion_Cuenta
import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import Data.*
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
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
import Data.Personalizados.Rey_Han_Kong
import Data.Personalizados.Rey_Vikingo
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.put


object DataManager {

    private const val PREFS_NAME = "SoticklordUserData"

    fun guardarDatos(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        //  Datos simples
        editor.putInt("ID", GlobalData.id_usuario)
        editor.putString("Nombre", GlobalData.Nombre)
        editor.putString("Contraseña", GlobalData.contraseña_hash)
        editor.putInt("nivel_progresion", GlobalData.nivel_de_progresion)
        editor.putInt("monedas", GlobalData.monedas)
        editor.putInt("esencia_juego", GlobalData.ecencia_de_juego)
        editor.putInt("nivel_cuenta", GlobalData.nivel_De_cuenta)
        editor.putInt("experiencia_juego", GlobalData.experiencia_de_juego)
        editor.putBoolean("primer_inicio", GlobalData.Primer_inicio)
        editor.putInt("Plata", GlobalData.Moneda_Global)
        editor.putBoolean("Se_paso_el_evento", GlobalData.Se_paso_el_evento)

        editor.putBoolean("animaciones", GlobalData.Desea_nimaciones)
        editor.putBoolean("pasar_experiencia", GlobalData.experiencia_pasada)

        editor.putString("perfil", GlobalData.Perfil_id)

        // 🔹 Guardar Diccionario de Reyes
        val reyesArray = JSONArray()
        for ((nombre, tropa) in GlobalData.Diccionario_Reyes) {
            val obj = JSONObject()
            obj.put("nombre", tropa.nombre)
            obj.put("nivel", tropa.nivel)
            reyesArray.put(obj)
        }

        // 🔹 Guardar Diccionario de Tropas
        val tropasArray = JSONArray()
        for ((nombre, tropa) in GlobalData.Diccionario_Tropas) {
            val obj = JSONObject()
            obj.put("nombre", tropa.nombre)
            obj.put("nivel", tropa.nivel)
            tropasArray.put(obj)
        }

        editor.putString("reyes", reyesArray.toString())
        editor.putString("tropas", tropasArray.toString())

        editor.apply()


    }

    fun cargarDatos(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        println("por defecto eexperiencia pasada : ${GlobalData.experiencia_pasada}")
        println("por defecto cantidad experineica : ${GlobalData.experiencia_de_juego}")
        // 🔸 Datos simples
        //false = no se cargo ahun datos inciales
        //si se cargo datos inciales ya
        //osea migrar
        GlobalData.experiencia_pasada = prefs.getBoolean("pasar_experiencia", false)
        println("por defecto eexperiencia pasada : ${GlobalData.experiencia_pasada}")
        println("por defecto cantidad experineica : ${GlobalData.experiencia_de_juego}")


        println("se cargo la experiencia pasada y es : ${GlobalData.experiencia_pasada}")
        GlobalData.id_usuario = prefs.getInt("ID", 0)
        GlobalData.monedas = prefs.getInt("monedas", 0)
        GlobalData.ecencia_de_juego = prefs.getInt("esencia_juego", 0)
        //GlobalData.nivel_De_cuenta = prefs.getInt("nivel_cuenta", 1)
        GlobalData.nivel_de_progresion = prefs.getInt("nivel_progresion", 0)
        GlobalData.experiencia_de_juego = prefs.getInt("experiencia_juego", 0)
        //GlobalData.nivel_De_cuenta = calcularNivel(GlobalData.experiencia_de_juego)

        GlobalData.Primer_inicio = prefs.getBoolean("primer_inicio", true)
        GlobalData.Moneda_Global = prefs.getInt("Plata", 0)
        GlobalData.Nombre = prefs.getString("Nombre", "default") ?: "default"
        GlobalData.contraseña_hash = prefs.getString("Contraseña", "xxx") ?: "xxx"
        GlobalData.Se_paso_el_evento = prefs.getBoolean("Se_paso_el_evento", false)
        GlobalData.Desea_nimaciones = prefs.getBoolean("animaciones", false)
        GlobalData.Perfil_id = prefs.getString("perfil", "perfil_0") ?: "perfil_0"

        println("supongo q se cargo la experineica : ${GlobalData.experiencia_de_juego}")

        // 🔹 Cargar Diccionario de Reyes
        val reyesJson = prefs.getString("reyes", "[]")
        val reyesArray = JSONArray(reyesJson)

        GlobalData.Diccionario_Reyes.clear()
        for (i in 0 until reyesArray.length()) {
            val obj = reyesArray.getJSONObject(i)
            var nombre = obj.getString("nombre")
            var nivel = obj.getInt("nivel")
            if (nivel > 125) {
                var sobra = nivel - 125
                GlobalData.monedas += sobra * 100
                nivel -= sobra
            }
            if(nombre == "Rey_Kratos"){
                nombre = "Rey_Vikingo"
            }

            val nuevaTropa = crearTropaPorNombre(nombre, nivel)
            println("Se creo el rey : ${nuevaTropa?.nombre}")
            if (nuevaTropa != null) {
                GlobalData.Diccionario_Reyes[i] = nuevaTropa
            }
        }

        // 🔹 Cargar Diccionario de Tropas
        val tropasJson = prefs.getString("tropas", "[]")
        val tropasArray = JSONArray(tropasJson)

        GlobalData.Diccionario_Tropas.clear()
        for (i in 0 until tropasArray.length()) {
            val obj = tropasArray.getJSONObject(i)
            val nombre = obj.getString("nombre")
            var nivel = obj.getInt("nivel")
            if (nivel > 125) {
                var sobra = nivel - 125
                GlobalData.monedas += sobra * 100
                nivel -= sobra
            }

            val nuevaTropa = crearTropaPorNombre(nombre, nivel)
            if (nuevaTropa != null)
                GlobalData.Diccionario_Tropas[i] = nuevaTropa
        }

        if (GlobalData.experiencia_pasada) {
            GlobalData.nivel_De_cuenta = calcularNivel(GlobalData.experiencia_de_juego)
            println("nivel de cuenta:: ${GlobalData.experiencia_pasada}")
        } else {
            migrarExperiencia()
            GlobalData.nivel_De_cuenta = calcularNivel(GlobalData.experiencia_de_juego)
            println("supongo: ( ${GlobalData.experiencia_pasada} ) es true")
            println("experiencia nueva : ${GlobalData.experiencia_de_juego}")
        }

    }


    fun migrarExperiencia() {
        var xpGanada = 0

        // Reyes
        for (rey in GlobalData.Diccionario_Reyes.values) {
            xpGanada += rey.nivel * 100
        }

        // Tropas
        for (tropa in GlobalData.Diccionario_Tropas.values) {
            xpGanada += tropa.nivel * 100
        }
        //nivel de progresion
        if (GlobalData.nivel_de_progresion > 6) {
            xpGanada += (GlobalData.nivel_de_progresion - 7) * 100
            GlobalData.nivel_de_progresion = 6
        }

        GlobalData.experiencia_de_juego += xpGanada

        GlobalData.experiencia_pasada = true

        GlobalData.nivel_De_cuenta = calcularNivel(GlobalData.experiencia_de_juego)
    }


    fun calcularNivel(xp: Int): Int {
        var nivel = 0
        var xpNecesaria = 100
        var xpRestante = xp

        while (xpRestante >= xpNecesaria) {
            xpRestante -= xpNecesaria
            nivel++
            xpNecesaria += 50
        }

        return nivel
    }


    fun borrarDatos(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
        GlobalData.Primer_inicio = true
    }

    fun crearTropaPorNombre(nombre: String, nivel: Int): Tropa? {
        val clase = GlobalData.Diccionario_Clases[nombre] ?: return null

        return try {
            val ctor = clase.constructors

            // 1) Constructor con (nivel, esDelJugador1)
            val ctor2 = ctor.firstOrNull { c ->
                c.parameters.size == 2 &&
                        c.parameters[0].type.classifier == Int::class &&
                        c.parameters[1].type.classifier == Boolean::class
            }
            if (ctor2 != null) {
                return ctor2.call(nivel, true) as? Tropa
            }

            // 2) Constructor con (nivel)
            val ctor1 = ctor.firstOrNull { c ->
                c.parameters.size == 1 &&
                        c.parameters[0].type.classifier == Int::class
            }
            if (ctor1 != null) {
                return ctor1.call(nivel) as? Tropa
            }

            // 3) Constructor sin parámetros → crear y asignar nivel
            val ctor0 = ctor.firstOrNull { c -> c.parameters.isEmpty() }
            val tropa = ctor0?.call() as? Tropa
            tropa?.nivel = nivel
            return tropa

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun crearTropaPorNombre_2(nombre: String, nivel: Int): Tropa? {
        val clase = GlobalData.Diccionario_Clases[nombre] ?: return null

        return try {
            val ctor = clase.constructors

            // 1) Constructor con (nivel, esDelJugador1)
            val ctor2 = ctor.firstOrNull { c ->
                c.parameters.size == 2 &&
                        c.parameters[0].type.classifier == Int::class &&
                        c.parameters[1].type.classifier == Boolean::class
            }
            if (ctor2 != null) {
                return ctor2.call(nivel, false) as? Tropa
            }

            // 2) Constructor con (nivel)
            val ctor1 = ctor.firstOrNull { c ->
                c.parameters.size == 1 &&
                        c.parameters[0].type.classifier == Int::class
            }
            if (ctor1 != null) {
                return ctor1.call(nivel) as? Tropa
            }

            // 3) Constructor sin parámetros → crear y asignar nivel
            val ctor0 = ctor.firstOrNull { c -> c.parameters.isEmpty() }
            val tropa = ctor0?.call() as? Tropa
            tropa?.nivel = nivel
            return tropa

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    //nuevas funciones nube

    fun mostrarPopupConImagen(
        context: Context,
        mensaje: String,
        onAceptar: (() -> Unit)? = null
    ) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.popup_personalizado)
        dialog.setCancelable(false)

        val txtMensaje = dialog.findViewById<TextView>(R.id.txtMensaje)
        val btnAceptar = dialog.findViewById<ImageButton>(R.id.btnAceptar)

        txtMensaje.text = mensaje

        btnAceptar.setOnClickListener {
            dialog.dismiss()
            onAceptar?.invoke()
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    fun hayConexion(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        val conectado =
            capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        if (!conectado) {
            mostrarPopupConImagen(
                context,
                "⚠️ Conexión perdida\n " +
                        "Se necesita Internet para continuar"
            ) {

            }
        }

        return conectado
    }

    fun general_guardar_progreso_nube(owner: LifecycleOwner, context: Context) {
        if (hayConexion(context)) {
            owner.lifecycleScope.launch {
                guardar_datos_a_la_nube()
                Iniciar_sesion_en_la_nube_respaldo_guardad(GlobalData.Nombre, GlobalData.contraseña_hash,context)
                Cargar_datos_Desde_la_nube(GlobalData.datosJugador)
                subir_reyes_a_la_nube()
                subir_tropas_a_la_nube()

            }
        } else {


        }
    }

    fun Cargar_datos_Desde_la_nube(data: String) {
        val partes = data.split(",")

        if (partes.size < 9) {
            println("Datos incompletos")
            return
        }

        val limpio = partes.map {
            it.replace("\"", "").trim()
        }

        GlobalData.id_usuario = limpio[0].toIntOrNull() ?: 0
        GlobalData.Nombre = limpio[1]
        GlobalData.Moneda_Global = limpio[3].toIntOrNull() ?: 0
        GlobalData.monedas = limpio[4].toIntOrNull() ?: 0
        GlobalData.ecencia_de_juego = limpio[5].toIntOrNull() ?: 0
        GlobalData.experiencia_de_juego = limpio[6].toIntOrNull() ?: 0
        GlobalData.nivel_de_progresion = limpio[7].toIntOrNull() ?: 0
        GlobalData.Perfil_id = limpio[8]

        GlobalData.nivel_De_cuenta = limpio[2].toIntOrNull() ?: 1
        GlobalData.nivel_De_cuenta = calcularNivel(GlobalData.experiencia_de_juego)

        GlobalData.experiencia_pasada = if (limpio[9] == "0") {
            false
        } else {
            true
        }

        GlobalData.nivel_De_cuenta = calcularNivel(GlobalData.experiencia_de_juego)
    }

    suspend fun Crear_cuenta(nombre: String, contraseña: String): String {
        GlobalData.Nombre = nombre
        GlobalData.contraseña_hash = generarHash(contraseña)

        var conversion = if (GlobalData.se_cargo_datos_iniciales) {
            "1" //se hizo la converdion correctamente
        } else {
            "0" //ahun falta conversion (indudable)
        }

        val supabase = createSupabaseClient(
            supabaseUrl = "https://xhkmibrcsdtysvwlhxev.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inhoa21pYnJjc2R0eXN2d2xoeGV2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjU3NTgzNjksImV4cCI6MjA4MTMzNDM2OX0.3V5MLAgv8OydwZtMAGFhVK8DTF_git5dNsdhnQiUWw4"

        ) {
            install(Postgrest)
        }

        println("Consultando Datos...")

        // No usar mapOf. Usar JsonObject SIEMPRE.
        val params = buildJsonObject {
            put("p_nombre", nombre)
            put("p_nivel", GlobalData.nivel_De_cuenta)
            put("p_moneda_global", GlobalData.Moneda_Global)
            put("p_monedas", GlobalData.monedas)
            put("p_esencia", GlobalData.ecencia_de_juego)
            put("p_experiencia", GlobalData.experiencia_de_juego)
            put("p_contrasena_hash", GlobalData.contraseña_hash)
            put("p_nivel_progresion", GlobalData.nivel_de_progresion)
            put("p_perfil_id", GlobalData.Perfil_id)
            put("p_conversionxd", conversion)
        }


        val result = supabase.postgrest.rpc(
            function = "registrar_jugador",
            parameters = params
        )

        var xd = result.data
        println("$xd")
        return xd.toString()

    }

    suspend fun guardar_datos_a_la_nube(): String {

        var conversion = if (GlobalData.experiencia_pasada) {
            "1"
        } else {
            "0"
        }

        val supabase = createSupabaseClient(
            supabaseUrl = "https://xhkmibrcsdtysvwlhxev.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inhoa21pYnJjc2R0eXN2d2xoeGV2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjU3NTgzNjksImV4cCI6MjA4MTMzNDM2OX0.3V5MLAgv8OydwZtMAGFhVK8DTF_git5dNsdhnQiUWw4"

        ) {
            install(Postgrest)
        }

        println("Guardando datos nube....")

        // No usar mapOf. Usar JsonObject SIEMPRE.
        val params = buildJsonObject {
            put("p_nombre", GlobalData.Nombre)
            put("p_nivel", GlobalData.nivel_De_cuenta)
            put("p_moneda_global", GlobalData.Moneda_Global)
            put("p_monedas", GlobalData.monedas)
            put("p_esencia", GlobalData.ecencia_de_juego)
            put("p_experiencia", GlobalData.experiencia_de_juego)
            put("p_contrasena_hash", GlobalData.contraseña_hash)
            put("p_nivel_progresion", GlobalData.nivel_de_progresion)
            put("p_perfil_id", GlobalData.Perfil_id)
            put("p_conversionxd", conversion)
        }

        val result = supabase.postgrest.rpc(
            function = "upsert_jugador",
            parameters = params
        )

        var xd = result.data

        println("completado....")
        return xd.toString()

    }

    suspend fun subir_puntaje(puntos: Int): String {
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
            put("p_puntaje", puntos)

        }

        val result = supabase.postgrest.rpc(
            function = "fn_evento_puntaje",
            parameters = params
        )

        var xd = result.data
        return xd
    }

    suspend fun Consultar_premio_evento(): String {
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
        GlobalData.datos_premio_evento = xd

        println(xd)

        return xd

    }

    fun ver_lo_q_gane(context: Context, onFinish: () -> Unit){
        val partes = GlobalData.datos_premio_evento.split("#")
        println("waosssss: ${GlobalData.datos_premio_evento}")
        var mensaje = ""
        var puesto = 0
        var premio1 = 0
        var premio2 = 0
        var premio3 = 0

        mensaje = partes[0]
        puesto = partes[1].toIntOrNull() ?: 0
        premio1 = partes[2].toIntOrNull() ?: 0
        premio2 = partes[3].toIntOrNull() ?: 0
        premio3 = partes[4].toIntOrNull() ?: 0



        mostrarPopupConImagen(
            context,
            "Felicidades!!\n" +
            "$mensaje \n" +
            "Puesto Alcanzado : $puesto \n" +
            "Premios:\n" +
            "Monedas : +$premio1\n" +
            "Monedas Globales : +$premio2\n" +
            "Ecencias : +$premio3"
        ) {
            GlobalData.monedas += premio1
            GlobalData.Moneda_Global += premio2
            GlobalData.ecencia_de_juego += premio3
            GlobalData.datos_premio_evento = "NO_RESULTADO#0#0#0#0"
            guardarDatos(context)
            onFinish()
        }
    }

    suspend fun puntaje() {
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

        }

        val result = supabase.postgrest.rpc(
            function = "consultar_puntaje",
            parameters = params
        )

        var xd = result.data.toInt()
        GlobalData.puntaje = xd

    }


    suspend fun canjear_codigo(codigo: String): String {

        val supabase = createSupabaseClient(
            supabaseUrl = "https://xhkmibrcsdtysvwlhxev.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inhoa21pYnJjc2R0eXN2d2xoeGV2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjU3NTgzNjksImV4cCI6MjA4MTMzNDM2OX0.3V5MLAgv8OydwZtMAGFhVK8DTF_git5dNsdhnQiUWw4"

        ) {
            install(Postgrest)
        }

        println("canjeando datos nube....")

        // No usar mapOf. Usar JsonObject SIEMPRE.
        val params = buildJsonObject {
            put("p_id_jugador", GlobalData.id_usuario)
            put("p_nombre_jugador", GlobalData.Nombre)
            put("p_codigo", codigo)

        }

        val result = supabase.postgrest.rpc(
            function = "canjear_codigo",
            parameters = params
        )

        var xd = result.data

        println("completado....")
        return xd.toString()

    }

    suspend fun Iniciar_sesion_en_la_nube(
        nombre: String,
        contraseña: String,
        contexts: Context
    ): String {
        //GlobalData.contraseña_hash = generarHash(contraseña)

        val supabase = createSupabaseClient(
            supabaseUrl = "https://xhkmibrcsdtysvwlhxev.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inhoa21pYnJjc2R0eXN2d2xoeGV2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjU3NTgzNjksImV4cCI6MjA4MTMzNDM2OX0.3V5MLAgv8OydwZtMAGFhVK8DTF_git5dNsdhnQiUWw4"

        ) {
            install(Postgrest)
        }

        println("Consultando Datos...")

        // No usar mapOf. Usar JsonObject SIEMPRE.
        val params = buildJsonObject {
            put("p_nombre", nombre)
            put("p_contrasena_hash", generarHash(contraseña))
        }

        val result = supabase.postgrest.rpc(
            function = "login_jugador",
            parameters = params
        )

        val respuesta = when (val data = result.data) {
            is JsonPrimitive -> data.content
            else -> data.toString().trim('"')
        }

        println("xd : $respuesta")
        when {
            respuesta.startsWith("ERROR|") -> {
                Toast.makeText(contexts, "$respuesta", Toast.LENGTH_SHORT).show()
                return respuesta
            }

            respuesta.startsWith("OK|") -> {
                GlobalData.datosJugador = respuesta.removePrefix("OK|")
                Toast.makeText(contexts, "Inicio Exitoso", Toast.LENGTH_SHORT).show()
                return "inicio exitoso"
            }

            else -> {
                return "datos corruptos"
            }
        }

    }

    suspend fun Iniciar_sesion_en_la_nube_respaldo_guardad(
        nombre: String,
        contraseña: String,
        contexts: Context
    ): String {
        //GlobalData.contraseña_hash = generarHash(contraseña)

        val supabase = createSupabaseClient(
            supabaseUrl = "https://xhkmibrcsdtysvwlhxev.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inhoa21pYnJjc2R0eXN2d2xoeGV2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjU3NTgzNjksImV4cCI6MjA4MTMzNDM2OX0.3V5MLAgv8OydwZtMAGFhVK8DTF_git5dNsdhnQiUWw4"

        ) {
            install(Postgrest)
        }

        println("Consultando Datos...")

        // No usar mapOf. Usar JsonObject SIEMPRE.
        val params = buildJsonObject {
            put("p_nombre", nombre)
            put("p_contrasena_hash", contraseña)
        }

        val result = supabase.postgrest.rpc(
            function = "login_jugador",
            parameters = params
        )

        val respuesta = when (val data = result.data) {
            is JsonPrimitive -> data.content
            else -> data.toString().trim('"')
        }

        println("xd : $respuesta")
        when {
            respuesta.startsWith("ERROR|") -> {
                //Toast.makeText(contexts, "$respuesta", Toast.LENGTH_SHORT).show()
                return respuesta
            }

            respuesta.startsWith("OK|") -> {
                GlobalData.datosJugador = respuesta.removePrefix("OK|")
                //Toast.makeText(contexts, "Inicio Exitoso", Toast.LENGTH_SHORT).show()
                return "inicio exitoso"
            }

            else -> {
                Toast.makeText(contexts, "Datos Corruptos", Toast.LENGTH_SHORT).show()
                return "datos corruptos"
            }
        }

    }

    suspend fun subir_reyes_a_la_nube(): String {

        val nombresReyes = GlobalData.Diccionario_Reyes.values.map { it.nombre }
        val nivelesReyes = GlobalData.Diccionario_Reyes.values.map { it.nivel }

        var nombre = "HeberWaos"
        val supabase = createSupabaseClient(
            supabaseUrl = "https://xhkmibrcsdtysvwlhxev.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inhoa21pYnJjc2R0eXN2d2xoeGV2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjU3NTgzNjksImV4cCI6MjA4MTMzNDM2OX0.3V5MLAgv8OydwZtMAGFhVK8DTF_git5dNsdhnQiUWw4"

        ) {
            install(Postgrest)
        }

        println("Probando insertar reyes...")


        val params = buildJsonObject {
            put("p_id_jugador", GlobalData.id_usuario)

            put(
                "p_reyes", JsonArray(
                    nombresReyes.map { JsonPrimitive(it) }
                ))

            put(
                "p_niveles", JsonArray(
                    nivelesReyes.map { JsonPrimitive(it) }
                ))
        }

        val result = supabase.postgrest.rpc(
            function = "insertar_o_actualizar_reyes_jugador",
            parameters = params
        )

        println("Mensaje ${result.data}")
        var xd = "Reyes subidos/actualizados Correctamente"
        return xd.toString()
    }

    suspend fun subir_tropas_a_la_nube(): String {

        val nombresReyes = GlobalData.Diccionario_Tropas.values.map { it.nombre }
        val nivelesReyes = GlobalData.Diccionario_Tropas.values.map { it.nivel }

        var nombre = "HeberWaos"
        val supabase = createSupabaseClient(
            supabaseUrl = "https://xhkmibrcsdtysvwlhxev.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inhoa21pYnJjc2R0eXN2d2xoeGV2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjU3NTgzNjksImV4cCI6MjA4MTMzNDM2OX0.3V5MLAgv8OydwZtMAGFhVK8DTF_git5dNsdhnQiUWw4"

        ) {
            install(Postgrest)
        }

        println("Probando insertar tropas...")


        val params = buildJsonObject {
            put("p_id_jugador", GlobalData.id_usuario)

            put(
                "p_tropas", JsonArray(
                    nombresReyes.map { JsonPrimitive(it) }
                ))

            put(
                "p_niveles", JsonArray(
                    nivelesReyes.map { JsonPrimitive(it) }
                ))
        }

        val result = supabase.postgrest.rpc(
            function = "insertar_o_actualizar_tropas_jugador",
            parameters = params
        )

        println("Mensaje ${result.data}")

        var xd = "Tropas subidos/actualizados Correctamente"
        return xd.toString()
    }

    suspend fun Cargar_reyes_de_nube(): String {

        var nombre = GlobalData.Nombre

        val supabase = createSupabaseClient(
            supabaseUrl = "https://xhkmibrcsdtysvwlhxev.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inhoa21pYnJjc2R0eXN2d2xoeGV2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjU3NTgzNjksImV4cCI6MjA4MTMzNDM2OX0.3V5MLAgv8OydwZtMAGFhVK8DTF_git5dNsdhnQiUWw4"

        ) {
            install(Postgrest)
        }

        println("Cargando reyes...")

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

        var index = 0

        for ((nombre, nivel) in resultado) {
            val tropa = crearTropaPorNombre(nombre, nivel)

            if (tropa != null) {
                GlobalData.Diccionario_Reyes[index] = tropa
                index++
            } else {
                println("No se pudo crear la tropa: $nombre")
            }
        }


        println("Reyes Cargados...")
        var mensaje = "Ok"
        return mensaje.toString()

    }

    suspend fun Cargar_tropas_de_nube(): String {

        val nombreJugador = GlobalData.Nombre

        val supabase = createSupabaseClient(
            supabaseUrl = "https://xhkmibrcsdtysvwlhxev.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inhoa21pYnJjc2R0eXN2d2xoeGV2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjU3NTgzNjksImV4cCI6MjA4MTMzNDM2OX0.3V5MLAgv8OydwZtMAGFhVK8DTF_git5dNsdhnQiUWw4"

        ) {
            install(Postgrest)
        }

        println("Cargando tropas...")

        val params = buildJsonObject {
            put("p_nombre_jugador", nombreJugador)
        }

        val result = supabase.postgrest.rpc(
            function = "obtener_tropas_jugador",
            parameters = params
        )

        if (result.data == null) {
            println("No hay tropas en la nube")
            return "Sin datos"
        }

        val jsonArray = Json.parseToJsonElement(result.data.toString()).jsonArray
        if (jsonArray.isEmpty()) {
            println("Lista vacía")
            return "Sin tropas"
        }

        val jsonObject = jsonArray[0].jsonObject

        val tropas = jsonObject["tropas"]!!.jsonArray
        val niveles = jsonObject["niveles"]!!.jsonArray

        val resultado = HashMap<String, Int>()

        for (i in tropas.indices) {
            val nombre = tropas[i].jsonPrimitive.content
            val nivel = niveles[i].jsonPrimitive.int
            resultado[nombre] = nivel
        }

        var index = 0
        for ((nombre, nivel) in resultado) {
            val tropa = crearTropaPorNombre(nombre, nivel)
            if (tropa != null) {
                GlobalData.Diccionario_Tropas[index] = tropa
                index++
            } else {
                println("No se pudo crear la tropa: $nombre")
            }
        }

        println("Tropas cargadas correctamente")
        return "Ok"
    }

    fun ver_evento() = runBlocking {

        val supabase = createSupabaseClient(
            supabaseUrl = "https://xhkmibrcsdtysvwlhxev.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inhoa21pYnJjc2R0eXN2d2xoeGV2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjU3NTgzNjksImV4cCI6MjA4MTMzNDM2OX0.3V5MLAgv8OydwZtMAGFhVK8DTF_git5dNsdhnQiUWw4"

        ) {
            install(Postgrest)
        }

        println("Consultando Datos de evento...")

        // No usar mapOf. Usar JsonObject SIEMPRE.
        val params = buildJsonObject {
        }

        val result = supabase.postgrest.rpc(
            function = "consultar_evento",
            parameters = params
        )

        val respuesta = when (val data = result.data) {
            is JsonPrimitive -> data.content
            else -> data.toString().trim('"')
        }

        println("xd : $respuesta")
        when {
            respuesta.startsWith("ERROR|") -> {
                //Toast.makeText(contexts, "$respuesta", Toast.LENGTH_SHORT).show()
                println("mmh : $respuesta")

            }

            respuesta.startsWith("OK|") -> {
                GlobalData.datos_evento = respuesta.removePrefix("OK|")
                //Toast.makeText(contexts, "Inicio Exitoso", Toast.LENGTH_SHORT).show()
                println("mmh : $respuesta")

            }

            else -> {

            }
        }

    }


    //funciones adicionales

    fun obtenerTipo(mensaje: String): String {
        return mensaje
            .split(":", "|")
            .first()
            .trim()
    }

    fun valorDespuesDeBarra(mensaje: String): String {
        return mensaje
            .substringAfter("|")
            .replace("\"", "")
            .trim()
    }


    fun Porcesar_codigo(mensaje: String): String {
        val mensajeLimpio = mensaje
            .trim()
            .removePrefix("\"")
            .removeSuffix("\"")

        val tipo = obtenerTipo(mensajeLimpio)

        when (tipo) {
            "error" -> {
                // Manejar error
                return "⚠ $mensaje"
            }

            //numericos
            "monedas" -> {
                val cantidad = valorDespuesDeBarra(mensaje).toIntOrNull() ?: 0
                GlobalData.monedas += cantidad
                return "💰 Monedas recibidas: $cantidad"
            }

            "Moneda_Global" -> {
                val cantidad = valorDespuesDeBarra(mensaje).toIntOrNull() ?: 0
                println("💰 Monedas recibidas: S/.$cantidad")
                GlobalData.Moneda_Global += cantidad
                return "💰 Monedas recibidas: S/.$cantidad"
            }

            "ecencia_de_juego" -> {
                val cantidad = valorDespuesDeBarra(mensaje).toIntOrNull() ?: 0
                println("💰 Ecencia recibidas: $cantidad")
                GlobalData.ecencia_de_juego += cantidad
                return "Ecencia recibidas: $cantidad"
            }

            //tropas
            "Tropa" -> {
                val nombreTropa = valorDespuesDeBarra(mensaje)

                var Tropa_nueva = crearTropaPorNombre(nombreTropa, 1)

                var waos = devolver_clave2("${Tropa_nueva!!.nombre}") //para verficar si existe
                if (waos == -1) {
                    var xd = obtenerUltimoIDTropa() + 1
                    GlobalData.Diccionario_Tropas[xd] = Tropa_nueva

                    return "Obtuvistes A una nueva Tropa : ${Tropa_nueva!!.nombre}"
                } else {
                    var nuevo_nivel = GlobalData.Diccionario_Tropas[waos]!!.nivel + 5
                    var Tropa_nuevaxd = crearTropaPorNombre(nombreTropa, nuevo_nivel)
                    GlobalData.Diccionario_Tropas[waos] = Tropa_nuevaxd!!
                    return "se aumento + 5 niveles a tu ${Tropa_nuevaxd!!.nombre}"
                }

            }


            "Rey" -> {
                val nombreTropa = valorDespuesDeBarra(mensaje)

                println(" tropa : $nombreTropa")
                var Tropa_nueva = crearTropaPorNombre(nombreTropa, 1)

                var waos = devolver_clave("${Tropa_nueva!!.nombre}") //para verficar si existe
                if (waos == -1) {
                    var xd = obtenerUltimoIDRey() + 1
                    GlobalData.Diccionario_Reyes[xd] = Tropa_nueva

                    return "Obtuvistes A un nuevo Rey : ${Tropa_nueva!!.nombre}"
                } else {
                    var nuevo_nivel = GlobalData.Diccionario_Reyes[waos]!!.nivel + 5
                    var Tropa_nuevaxd = crearTropaPorNombre(nombreTropa, nuevo_nivel)
                    GlobalData.Diccionario_Reyes[waos] = Tropa_nuevaxd!!
                    return "se aumento + 5 niveles a tu ${Tropa_nuevaxd!!.nombre}"
                }

            }

            "Rey_aspecto" -> {
                val nombreTropa = valorDespuesDeBarra(mensaje)

                println(" tropa : $nombreTropa")

                var Tropa_nueva = crearTropaPorNombre(nombreTropa, 1)
                var waos = devolver_clave("Rey_Bufon_Negro") //para verficar si existe

                if (waos == -1) {
                    var xd = obtenerUltimoIDRey() + 1
                    GlobalData.Diccionario_Reyes[xd] = Tropa_nueva!!
                    return "Obtuvistes A un nuevo Rey : ${Tropa_nueva!!.nombre}"
                } else {
                    var tropa_nivel = GlobalData.Diccionario_Reyes[waos]!!.nivel
                    var Tropa_nuevaxd = crearTropaPorNombre(nombreTropa, tropa_nivel)
                    GlobalData.Diccionario_Reyes[waos] = Tropa_nuevaxd!!
                    return "Obtuvistes un Nuevo rey con aspecto especial:\n${Tropa_nuevaxd!!.nombre}\n Feliz navidad!"
                }

            }

            else -> {
                return "⚠ Sucedio un error inesperado"

            }
        }
    }

    //calve para reyes
    fun devolver_clave(nombre: String): Int {
        val clave = GlobalData.Diccionario_Reyes.entries.find { it.value.nombre == nombre }?.key

        return clave ?: -1
    }

    //calve para tropas
    fun devolver_clave2(nombre: String): Int {
        val clave =
            GlobalData.Diccionario_Tropas.entries.find { it.value.nombre == nombre }?.key

        return clave ?: -1
    }

    //ya se sabe
    fun obtenerUltimoIDTropa(): Int {
        return if (GlobalData.Diccionario_Tropas.isEmpty()) {
            -1   // o 0, según lo que necesites
        } else {
            GlobalData.Diccionario_Tropas.keys.max()
        }
    }

    //ya se sabe
    fun obtenerUltimoIDRey(): Int {
        return if (GlobalData.Diccionario_Reyes.isEmpty()) {
            -1   // o 0, según lo que necesites
        } else {
            GlobalData.Diccionario_Reyes.keys.max()
        }
    }


    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun <T> ejecutarSiHayInternet(context: Context, accion: () -> T): T? {
        return if (isInternetAvailable(context)) {
            try {
                accion()
            } catch (e: Exception) {
                Toast.makeText(context, "Error al conectar con el servidor", Toast.LENGTH_LONG)
                    .show()
                null
            }
        } else {
            Toast.makeText(
                context,
                "No hay conexión a internet, inténtalo más tarde",
                Toast.LENGTH_LONG
            ).show()
            null
        }
    }

    fun generarHash(texto: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(texto.toByteArray(Charsets.UTF_8))

        return hashBytes.joinToString("") {
            "%02x".format(it)
        }
    }

}
