package com.waos.soticklord

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import Data.*

object DataManager {

    private const val PREFS_NAME = "SoticklordUserData"

    fun guardarDatos(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()

        // 🔸 Datos simples
        editor.putInt("monedas", GlobalData.monedas)
        editor.putInt("esencia_juego", GlobalData.ecencia_de_juego)
        editor.putInt("nivel_cuenta", GlobalData.nivel_De_cuenta)
        editor.putInt("nivel_progresion", GlobalData.nivel_de_progresion)
        editor.putInt("experiencia_juego", GlobalData.experiencia_de_juego)
        editor.putBoolean("primer_inicio", GlobalData.Primer_inicio)

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

        // 🔹 Cargar Diccionario de Reyes
        val reyesJson = prefs.getString("reyes", "[]")
        val reyesArray = JSONArray(reyesJson)

        GlobalData.Diccionario_Reyes.clear()
        for (i in 0 until reyesArray.length()) {
            val obj = reyesArray.getJSONObject(i)
            val nombre = obj.getString("nombre")
            val nivel = obj.getInt("nivel")

            val nuevaTropa = crearTropaPorNombre(nombre, nivel)
            if (nuevaTropa != null)
                GlobalData.Diccionario_Reyes[i] = nuevaTropa
        }

        // 🔹 Cargar Diccionario de Tropas
        val tropasJson = prefs.getString("tropas", "[]")
        val tropasArray = JSONArray(tropasJson)

        GlobalData.Diccionario_Tropas.clear()
        for (i in 0 until tropasArray.length()) {
            val obj = tropasArray.getJSONObject(i)
            val nombre = obj.getString("nombre")
            val nivel = obj.getInt("nivel")

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
            // Constructor que recibe un Int (nivel)
            val constructor = clase.constructors.firstOrNull { ctor ->
                ctor.parameters.size == 1 && ctor.parameters[0].type.classifier == Int::class
            }

            // Si tiene constructor con nivel → úsalo
            val tropa = constructor?.call(nivel) as? Tropa

            // Si no lo tiene → crear sin parámetros y asignar el nivel
            tropa ?: (clase.constructors.firstOrNull()?.call() as? Tropa)?.apply {
                this.nivel = nivel
            }

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
