package com.waos.soticklord

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import Data.*

object GlobalDataManager {

    private const val PREFS_NAME = "DatosGlobales"

    fun guardar(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()

        // Guarda las variables simples
        editor.putInt("decision", GlobalData.decision)
        editor.putInt("ecencia_de_juego", GlobalData.ecencia_de_juego)
        editor.putInt("monedas", GlobalData.monedas)
        editor.putInt("nivel_De_cuenta", GlobalData.nivel_De_cuenta)
        editor.putInt("experiencia_de_juego", GlobalData.experiencia_de_juego)
        editor.putInt("nivel_de_progresion", GlobalData.nivel_de_progresion)

        // Guarda los diccionarios (se convierten a JSON)
        val reyesJson = gson.toJson(GlobalData.Diccionario_Reyes)
        val tropasJson = gson.toJson(GlobalData.Diccionario_Tropas)
        editor.putString("Diccionario_Reyes", reyesJson)
        editor.putString("Diccionario_Tropas", tropasJson)

        editor.apply()
    }

    fun cargar(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()

        // Carga las variables simples
        GlobalData.decision = prefs.getInt("decision", 0)
        GlobalData.ecencia_de_juego = prefs.getInt("ecencia_de_juego", 0)
        GlobalData.monedas = prefs.getInt("monedas", 0)
        GlobalData.nivel_De_cuenta = prefs.getInt("nivel_De_cuenta", 0)
        GlobalData.experiencia_de_juego = prefs.getInt("experiencia_de_juego", 0)
        GlobalData.nivel_de_progresion = prefs.getInt("nivel_de_progresion", 0)

        // Carga los diccionarios (desde JSON)
        val reyesJson = prefs.getString("Diccionario_Reyes", null)
        val tropasJson = prefs.getString("Diccionario_Tropas", null)

        val type = object : TypeToken<HashMap<Int, Tropa>>() {}.type

        if (reyesJson != null) {
            GlobalData.Diccionario_Reyes = gson.fromJson(reyesJson, type)
        }
        if (tropasJson != null) {
            GlobalData.Diccionario_Tropas = gson.fromJson(tropasJson, type)
        }
    }
}
