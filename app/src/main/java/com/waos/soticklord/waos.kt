package com.waos.soticklord

import java.sql.DriverManager
import Data.*

fun main() {
    // Creamos una lista mutable de tropas
    val ejercito: MutableList<Tropa> = mutableListOf()

    // Agregamos hijas de Tropa
    ejercito.add(Tropa_Gigante())
    ejercito.add(Tropa_Arquero())
    ejercito.add(Tropa_Espadachin())
    ejercito.add(Tropa_Lanzatonio())
    // ... y todas las demás

    // Recorremos el ejército
    for (tropa in ejercito) {
        println("Nombre: ${tropa.nombre}, Vida: ${tropa.vida}, Ataque: ${tropa.ataque_base}")
    }
}
