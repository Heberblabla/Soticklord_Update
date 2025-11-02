package com.waos.soticklord

import android.app.Activity
import Data.*

object EntornoManager {
    var batalla: Activity? = null
    var entornoActual: Entorno? = null
    var invocador: Tropa? = null
    var enemigos: List<Tropa> = emptyList()
    var aliados: List<Tropa> = emptyList()

    fun cambiarEntorno(
        nuevo: Entorno,
        invocador: Tropa,
        enemigos: List<Tropa>,
        aliados: List<Tropa>
    ) {
        batalla?.let { act ->
            entornoActual = nuevo
            this.invocador = invocador
            this.enemigos = enemigos
            this.aliados = aliados
            nuevo.activarVisual(act)
        }
    }

    fun aplicarEfecto() {
        entornoActual?.aplicarEfecto(invocador ?: return, enemigos, aliados)
    }

    fun limpiar() {
        entornoActual = null
        invocador = null
    }
}
