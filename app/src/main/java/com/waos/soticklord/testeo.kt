package com.waos.soticklord

import Data.Rey_Espadachin
import Data.Rey_Lanzatonio
import Data.Tropa_Arquero
import Data.Tropa_Espadachin
import Data.Tropa_Gigante
import Data.Tropa_Lanzatonio


fun main(){
    GlobalData.Diccionario_Tropas[1] = Tropa_Arquero(1)
    val listaTropas = GlobalData.Diccionario_Tropas.values
    println("hola")
    println("Vida antes: ${GlobalData.Diccionario_Tropas[1]!!.vida}")
    var nivel = listaTropas.first().nivel + 1
    GlobalData.Diccionario_Tropas[1] = Tropa_Arquero(nivel)
    println("Vida después: ${GlobalData.Diccionario_Tropas[1]!!.vida}")
}

fun prueba02(){
    GlobalData.Jugador2[0] = Rey_Espadachin(1)
    GlobalData.Jugador2[1] = Tropa_Lanzatonio(1)
    GlobalData.Jugador2[2] = Tropa_Lanzatonio(1)
    GlobalData.Jugador2[3] = Tropa_Espadachin(1)
    GlobalData.Jugador2[4] = Tropa_Espadachin(1)
    GlobalData.Jugador2[5] = Tropa_Espadachin(1)

    GlobalData.Jugador1[0] = Rey_Lanzatonio(1)
    GlobalData.Jugador1[1] = Tropa_Arquero(1)
    GlobalData.Jugador1[2] = Tropa_Arquero(1)
    GlobalData.Jugador1[3] = Tropa_Gigante(1)
    GlobalData.Jugador1[4] = Tropa_Gigante(1)
    GlobalData.Jugador1[5] = Tropa_Gigante(1)
}

fun imprimir(){
    println("-----jugador1----")
    for (tropa in GlobalData.Jugador1 ){
        println(tropa!!.nombre)
        println(tropa!!.vida)
        println("----------------")
    }
    println("-----jugador2----")
    for (tropa in GlobalData.Jugador2 ){
        println(tropa!!.nombre)
        println(tropa!!.vida)
        println("----------------")
    }
}

fun simularataque(){
    println("empezando turno")
    //val bot = Bot_Desiciones_aleatorio(this)
    //bot.Empezar_Analisis(5)
}

fun turno_del_enemigo(){
        var turno_enemigo = 5
        for(tropa in GlobalData.Jugador1 ){
            println("-turno de la triopa $turno_enemigo ")
            Thread.sleep(2000)
            if(GlobalData.Jugador1[turno_enemigo]!!.estado_de_vida){
                //val bot = Bot_Desiciones()
               // bot.Empezar_Analisis(turno_enemigo)
            }
            println("Se ejecuto el atque sin problema")
            turno_enemigo -= 1
        }
    }


val supabaseUrl = "https://zropeiibzqefzjrkdzzp.supabase.co"
val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inpyb3BlaWlienFlZnpqcmtkenpwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTkwMTc1NDYsImV4cCI6MjA3NDU5MzU0Nn0.ZJWqkOAbTul-RwIQrirajUSVdyI1w9Kh3kjek0vFMw8"




