package com.waos.soticklord

import Data.Tropa

object GlobalData {
    val Diccionario_Reyes  = hashMapOf<Int, Tropa>()
    val Diccionario_Tropas  = hashMapOf<Int, Tropa>()
    var id_usuario  = 0
    var nivel_de_progresion = 0
    val Jugador2 = ArrayList<Tropa?>().apply {
        add(null) // posición 0 (rey)
        add(null) // posición 1 (tropa)
        add(null) // posición 2 (tropa)
        add(null) // posición 3 (tropa)
        add(null) // posición 4 (tropa)
        add(null) // posición 5 (tropa)
    }
    val Jugador1 = ArrayList<Tropa?>().apply {
        add(null) // posición 0 (rey)
        add(null) // posición 1 (tropa)
        add(null) // posición 2 (tropa)
        add(null) // posición 3 (tropa)
        add(null) // posición 4 (tropa)
        add(null) // posición 5 (tropa)
    }

    var ReySeleccionado: Tropa? = null
    var TropaSeleccionada: Tropa? = null
}
