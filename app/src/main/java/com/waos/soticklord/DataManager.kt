package com.waos.soticklord

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
import android.widget.Toast


object DataManager {

    private const val PREFS_NAME = "SoticklordUserData"

    fun guardarDatos(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()

        //  Datos simples
        editor.putInt("monedas", GlobalData.monedas)
        editor.putInt("esencia_juego", GlobalData.ecencia_de_juego)
        editor.putInt("nivel_cuenta", GlobalData.nivel_De_cuenta)
        editor.putInt("nivel_progresion", GlobalData.nivel_de_progresion)
        editor.putInt("experiencia_juego", GlobalData.experiencia_de_juego)
        editor.putBoolean("primer_inicio", GlobalData.Primer_inicio)
        editor.putInt("Plata", GlobalData.Moneda_Global)
        editor.putString("Nombre", GlobalData.Nombre)
        editor.putBoolean("Se_paso_el_evento", GlobalData.Se_paso_el_evento)
        editor.putBoolean("animaciones", GlobalData.Desea_nimaciones)

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

        // 🔸 Datos simples
        GlobalData.monedas = prefs.getInt("monedas", 0)
        GlobalData.ecencia_de_juego = prefs.getInt("esencia_juego", 0)
        GlobalData.nivel_De_cuenta = prefs.getInt("nivel_cuenta", 1)
        GlobalData.nivel_de_progresion = prefs.getInt("nivel_progresion", 0)
        GlobalData.experiencia_de_juego = prefs.getInt("experiencia_juego", 0)
        GlobalData.Primer_inicio = prefs.getBoolean("primer_inicio", true)
        GlobalData.Moneda_Global = prefs.getInt("Plata",0)
        GlobalData.Nombre = prefs.getString("Nombre", "default") ?: "default"
        GlobalData.Se_paso_el_evento = prefs.getBoolean("Se_paso_el_evento",false)
        GlobalData.Desea_nimaciones = prefs.getBoolean("animaciones",false)


        // 🔹 Cargar Diccionario de Reyes
        val reyesJson = prefs.getString("reyes", "[]")
        val reyesArray = JSONArray(reyesJson)

        GlobalData.Diccionario_Reyes.clear()
        for (i in 0 until reyesArray.length()) {
            val obj = reyesArray.getJSONObject(i)
            val nombre = obj.getString("nombre")
            var nivel = obj.getInt("nivel")
            if(nivel > 125){
                var sobra = nivel - 125
                GlobalData.monedas += sobra *100
                nivel -= sobra
            }

            val nuevaTropa = crearTropaPorNombre(nombre, nivel)
            println("Se creo el rey : ${nuevaTropa?.nombre}")
            if (nuevaTropa != null){
                GlobalData.Diccionario_Reyes[i] = nuevaTropa}
        }

        // 🔹 Cargar Diccionario de Tropas
        val tropasJson = prefs.getString("tropas", "[]")
        val tropasArray = JSONArray(tropasJson)

        GlobalData.Diccionario_Tropas.clear()
        for (i in 0 until tropasArray.length()) {
            val obj = tropasArray.getJSONObject(i)
            val nombre = obj.getString("nombre")
            var nivel = obj.getInt("nivel")
            if(nivel > 125){
                var sobra = nivel - 125
                GlobalData.monedas += sobra *100
                nivel -= sobra
            }

            val nuevaTropa = crearTropaPorNombre(nombre, nivel)
            if (nuevaTropa != null)
                GlobalData.Diccionario_Tropas[i] = nuevaTropa
        }
    }

    fun borrarDatos(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
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

    fun reclamar_codigo(Codigo:String):Int = runBlocking{
        val supabase = createSupabaseClient(
            supabaseUrl = "https://bkrvukzsfyzbyoatpysw.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJrcnZ1a3pzZnl6YnlvYXRweXN3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjQwMDgyMzQsImV4cCI6MjA3OTU4NDIzNH0.It2XZzxNzPk0wYv-xW52mVkOOP3Wqpqacoefszu36ic"

        ) {
            install(Postgrest)
        }

        println("Probando UPSERT...")

        //  No usar mapOf. Usar JsonObject SIEMPRE.
        val params = buildJsonObject {
            put("nombre_p", Codigo)
        }

        val result = supabase.postgrest.rpc(
            function = "obtener_premio",
            parameters = params
        )

        val valor = result.decodeAs<Int>()

        return@runBlocking valor
    }


    fun Actualizar_datos():Int = runBlocking  {

        val supabase = createSupabaseClient(
            supabaseUrl = "https://bkrvukzsfyzbyoatpysw.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJrcnZ1a3pzZnl6YnlvYXRweXN3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjQwMDgyMzQsImV4cCI6MjA3OTU4NDIzNH0.It2XZzxNzPk0wYv-xW52mVkOOP3Wqpqacoefszu36ic"

        ) {
            install(Postgrest)
        }

        println("Probando UPSERT...")

        //  No usar mapOf. Usar JsonObject SIEMPRE.
        val params = buildJsonObject {
            put("nombre_j", GlobalData.Nombre)
            put("money_j", GlobalData.Moneda_Global)
            put("nivel_j", GlobalData.nivel_de_progresion)
        }

        val result = supabase.postgrest.rpc(
            function = "actualizar_jugador",
            parameters = params
        )

        val valor = result.decodeAs<Int>()

        return@runBlocking valor
    }

    fun reclamar_premio():Int = runBlocking  {

        val supabase = createSupabaseClient(
            supabaseUrl = "https://bkrvukzsfyzbyoatpysw.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJrcnZ1a3pzZnl6YnlvYXRweXN3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjQwMDgyMzQsImV4cCI6MjA3OTU4NDIzNH0.It2XZzxNzPk0wYv-xW52mVkOOP3Wqpqacoefszu36ic"

        ) {
            install(Postgrest)
        }

        println("Probando UPSERT...")

        //  No usar mapOf. Usar JsonObject SIEMPRE.
        val params = buildJsonObject {
            put("nombre_g", GlobalData.Nombre)
        }

        val result = supabase.postgrest.rpc(
            function = "obtener_y_eliminar_ganador",
            parameters = params
        )

        val valor = result.decodeAs<Int>()

        return@runBlocking valor
    }

    fun rellenar_campo_completo_evento():Int = runBlocking  {

        val supabase = createSupabaseClient(
            supabaseUrl = "https://bkrvukzsfyzbyoatpysw.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJrcnZ1a3pzZnl6YnlvYXRweXN3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjQwMDgyMzQsImV4cCI6MjA3OTU4NDIzNH0.It2XZzxNzPk0wYv-xW52mVkOOP3Wqpqacoefszu36ic"

        ) {
            install(Postgrest)
        }

        println("Probando UPSERT...")

        //  No usar mapOf. Usar JsonObject SIEMPRE.
        val params = buildJsonObject {
            put("p_nombre_jugador", GlobalData.Nombre)
        }

        val result = supabase.postgrest.rpc(
            function = "verificar_evento",
            parameters = params
        )

        val valor = result.decodeAs<Int>()

        return@runBlocking valor
    }

    fun verficar_si_existe(waos: String):Int = runBlocking  {

        val supabase = createSupabaseClient(
            supabaseUrl = "https://bkrvukzsfyzbyoatpysw.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJrcnZ1a3pzZnl6YnlvYXRweXN3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjQwMDgyMzQsImV4cCI6MjA3OTU4NDIzNH0.It2XZzxNzPk0wYv-xW52mVkOOP3Wqpqacoefszu36ic"

        ) {
            install(Postgrest)
        }

        println("Probando UPSERT...")

        //  No usar mapOf. Usar JsonObject SIEMPRE.
        val params = buildJsonObject {
            put("p_nombre", waos)
        }

        val result = supabase.postgrest.rpc(
            function = "existe_jugador",
            parameters = params
        )

        val valor = result.decodeAs<Int>()

        return@runBlocking valor
    }

    fun subir_datos_nube() = runBlocking {

        val supabase = createSupabaseClient(
            supabaseUrl = "https://bkrvukzsfyzbyoatpysw.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJrcnZ1a3pzZnl6YnlvYXRweXN3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjQwMDgyMzQsImV4cCI6MjA3OTU4NDIzNH0.It2XZzxNzPk0wYv-xW52mVkOOP3Wqpqacoefszu36ic"

        ) {
            install(Postgrest)
        }

        println("Probando UPSERT...")

        //  No usar mapOf. Usar JsonObject SIEMPRE.
        val params = buildJsonObject {
            put("p_nombre", GlobalData.Nombre)
            put("p_money", GlobalData.Moneda_Global)
            put("p_nivel", GlobalData.nivel_de_progresion)
        }

        val result = supabase.postgrest.rpc(
            function = "upsert_jugador",
            parameters = params
        )

        println("Resultado:")
        println(result.data)
    }

    fun push(waos: String):String = runBlocking  {

        val supabase = createSupabaseClient(
            supabaseUrl = "https://bkrvukzsfyzbyoatpysw.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJrcnZ1a3pzZnl6YnlvYXRweXN3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjQwMDgyMzQsImV4cCI6MjA3OTU4NDIzNH0.It2XZzxNzPk0wYv-xW52mVkOOP3Wqpqacoefszu36ic"

        ) {
            install(Postgrest)
        }

        println("Probando UPSERT...")

        //  No usar mapOf. Usar JsonObject SIEMPRE.
        val params = buildJsonObject {
            put("p_nombre_jugador", waos)
        }

        val result = supabase.postgrest.rpc(
            function = "verificar_evento",
            parameters = params
        )

        val valor = result.decodeAs<String>()

        return@runBlocking valor
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
                Toast.makeText(context, "Error al conectar con el servidor", Toast.LENGTH_LONG).show()
                null
            }
        } else {
            Toast.makeText(context, "No hay conexión a internet, inténtalo más tarde", Toast.LENGTH_LONG).show()
            null
        }
    }

}
