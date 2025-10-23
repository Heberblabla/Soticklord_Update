package com.waos.soticklord

import Data.Tropa

object GlobalData {
    var Jugador2 = ArrayList<Tropa?>().apply {
        add(null) // posición 0 (rey)
        add(null) // posición 1 (tropa)
        add(null) // posición 2 (tropa)
        add(null) // posición 3 (tropa)
        add(null) // posición 4 (tropa)
        add(null) // posición 5 (tropa)
    }
    var Jugador1 = ArrayList<Tropa?>().apply {
        add(null) // posición 0 (rey)
        add(null) // posición 1 (tropa)
        add(null) // posición 2 (tropa)
        add(null) // posición 3 (tropa)
        add(null) // posición 4 (tropa)
        add(null) // posición 5 (tropa)
    }
    var Diccionario_Reyes = hashMapOf<Int, Tropa>()
    var Diccionario_Tropas = hashMapOf<Int, Tropa>()
    var id_usuario = 0 //id del usuario
    var nivel_de_progresion = 0 //progresion del mapa
    var experiencia_de_juego = 0 //su experincia del nivel
    var nivel_De_cuenta = 0 //depdned de experiencia
    var monedas = 0 // monedas q tiene el jugador
    var ecencia_de_juego = 0//ecencia para mejorar personajes



    var ReySeleccionado: Tropa? = null
    var TropaSeleccionada: Tropa? = null
    var decision = 0
}
