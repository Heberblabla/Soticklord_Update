package com.waos.soticklord

import android.app.Activity
import android.graphics.Color
import android.view.View
import kotlin.random.Random
import Data.*

object DataEntornos {

    val Bosque_normal = Entorno(
        nombre = "Bosque Normal",
        imagenRes = R.drawable.campo_batalla1,
        descripcion = "Un bosque por defecto",
        efectoVisual = { act ->

        },
        efectoBatalla = { invocador, enemigos, aliados ->
            //no tiene efecto
        }
    )

    val Mar_rojo = Entorno(
        nombre = "Mar rojo",
        imagenRes = R.drawable.mar_rojo,
        descripcion = "Los jugadores sufre un daño igual al ataque base del invocador",

        efectoBatalla = { invocador, enemigos, aliados ->
            if(invocador.estado_de_vida) {
                invocador.vida += 50
            }
            for (enemigo in enemigos) {
                enemigo.vida = (enemigo.vida * 0.95).toInt()
            }
            for (enemigo in enemigos) {
                enemigo.precision -= 1 // -1% presicion
            }
        }
    )

    val Campo_de_estasis = Entorno(
        nombre = "Campo_de_estasis",
        imagenRes = R.drawable.entorno_cristial,
        descripcion = "Los jugadores sufre un daño igual al 10% de su atque",

        efectoBatalla = { invocador, enemigos, aliados ->
            for (enemigo in enemigos) {
                enemigo.vida -= (enemigo.ataque_base * 0.08).toInt()
            }

        }
    )

    val Mar_desordenado = Entorno(
        nombre = "Mar desordenado",
        imagenRes = R.drawable.mar_desordenado,
        descripcion = "Los jugadores desordenan su sitio :v",

        efectoBatalla = { invocador, enemigos, aliados ->
            val listaMutable = (enemigos as MutableList<Tropa>)

            if (listaMutable.size > 1) {
                val subLista = listaMutable.subList(1, listaMutable.size)
                subLista.shuffle(Random(System.currentTimeMillis()))
            }
            for (enemigo in enemigos) {
                enemigo.vida -= 25
                enemigo.ataque_base -= 25
                enemigo.daño_critico -= 0.10
            }
        }

    )


    val Reino_Paranormal = Entorno(
        nombre = "Reino Paranormal",
        imagenRes = R.drawable.entorno_paranormal,
        descripcion = "Los rivales sufren efectos de combate",
        efectoVisual = { act ->

        },
        efectoBatalla = { invocador, enemigos, aliados ->
            for (enemigo in enemigos) {
                enemigo.vida = (enemigo.vida * 0.9).toInt() // -10% vida
            }
            invocador.vida += 100
            invocador.precision += 20
            invocador.daño_critico += 0.4
            invocador.cantidad_espinas += 0.02

            for (enemigo in enemigos) {
                enemigo.ataque_base = (enemigo.ataque_base * 0.9).toInt() // -10% ataque
            }
            for (enemigo in enemigos) {
                enemigo.precision -= 3 // -3% presicion
            }

            for (aliados in aliados) {
                if(aliados.estado_de_vida) {
                    aliados.vida = (aliados.vida * 1.1).toInt()
                }
            }
        }
    )

}
