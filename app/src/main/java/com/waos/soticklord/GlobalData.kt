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
    var Diccionario_Reyes  = hashMapOf<Int, Tropa>()
    var Diccionario_Tropas  = hashMapOf<Int, Tropa>()
    var id_usuario  = 0
    var nivel_de_progresion = 0


    var ReySeleccionado: Tropa? = null
    var TropaSeleccionada: Tropa? = null
    var decision = 0
}
