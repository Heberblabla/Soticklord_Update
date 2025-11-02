package com.waos.soticklord

import android.app.Activity
import android.widget.ImageView
import Data.*

class Entorno(
    val nombre: String,
    val imagenRes: Int,
    val descripcion: String = "",
    val efectoVisual: ((Activity) -> Unit)? = null,
    val efectoBatalla: ((invocador: Tropa, enemigos: List<Tropa>, aliados: List<Tropa>) -> Unit)? = null
) {
    fun activarVisual(activity: Activity) {
        val fondo = activity.findViewById<ImageView>(R.id.fondo_batalla)
        fondo.setImageResource(imagenRes)
        efectoVisual?.invoke(activity)
    }

    fun aplicarEfecto(invocador: Tropa, enemigos: List<Tropa>, aliados: List<Tropa>) {
        efectoBatalla?.invoke(invocador, enemigos, aliados)
    }
}
