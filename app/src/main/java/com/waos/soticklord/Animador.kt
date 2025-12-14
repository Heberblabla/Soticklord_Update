package com.waos.soticklord

import Data.Tropa
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView

object Animador {

    // Hilos individuales por tropa
    private val hilosJugador1 = mutableMapOf<Int, Thread>()
    private val hilosJugador2 = mutableMapOf<Int, Thread>()

    // ---------- PUBLICO ----------

    fun empezar_animacion(activity: Activity) {
        // jugador 1
        iniciarAnimacion1(activity, 0, R.id.TropaAa)
        iniciarAnimacion1(activity, 1, R.id.TropaBa)
        iniciarAnimacion1(activity, 2, R.id.TropaBb)
        iniciarAnimacion1(activity, 3, R.id.TropaCa)
        iniciarAnimacion1(activity, 4, R.id.TropaCb)
        iniciarAnimacion1(activity, 5, R.id.TropaCc)

        // jugador 2
        iniciarAnimacion2(activity, 0, R.id.TropaFa)
        iniciarAnimacion2(activity, 1, R.id.TropaEb)
        iniciarAnimacion2(activity, 2, R.id.TropaEa)
        iniciarAnimacion2(activity, 3, R.id.TropaDc)
        iniciarAnimacion2(activity, 4, R.id.TropaDb)
        iniciarAnimacion2(activity, 5, R.id.TropaDa)
    }

    fun detenerTodo() {
        hilosJugador1.values.forEach {
            it.interrupt()
        }
        hilosJugador2.values.forEach {
            it.interrupt()
        }
        hilosJugador1.clear()
        hilosJugador2.clear()
    }

    // ---------- PRIVADO ----------

    private fun iniciarAnimacion1(activity: Activity, index: Int, id: Int) {
        iniciarAnimacionGeneral(
            activity,
            id,
            { GlobalData.Jugador1[index] },
            hilosJugador1,
            index
        )
    }

    private fun iniciarAnimacion2(activity: Activity, index: Int, id: Int) {
        iniciarAnimacionGeneral(
            activity,
            id,
            { GlobalData.Jugador2[index] },
            hilosJugador2,
            index
        )
    }

    private fun iniciarAnimacionGeneral(
        activity: Activity,
        idImageView: Int,
        tropaGetter: () -> Tropa?,
        mapaHilos: MutableMap<Int, Thread>,
        index: Int
    ) {
        val imageView = activity.findViewById<ImageView>(idImageView)

        // Detener hilo viejo
        mapaHilos[index]?.interrupt()

        val hilo = Thread {
            var frameIndex = 0
            var nombreActual = ""
            var frames = emptyList<Bitmap>()

            try {
                while (!Thread.interrupted()) {

                    val tropa = tropaGetter() ?: break
                    if (!tropa.estado_de_vida) break

                    // Detectar cambio de animación
                    if (tropa.nombre != nombreActual) {
                        nombreActual = tropa.nombre
                        frames = cargarFrames(activity, nombreActual)
                        frameIndex = 0
                    }

                    // Si sigue sin frames, esperar e intentar otra vez
                    if (frames.isEmpty()) {
                        Thread.sleep(100)
                        continue
                    }

                    activity.runOnUiThread {
                        imageView.setImageBitmap(frames[frameIndex])
                    }

                    frameIndex = (frameIndex + 1) % frames.size
                    Thread.sleep(100)
                }

            } catch (_: InterruptedException) {
                // El hilo terminó correctamente
            }
        }

        mapaHilos[index] = hilo
        hilo.start()
    }

    private fun cargarFrames(context: Context, carpeta: String): List<Bitmap> {
        val assetManager = context.assets
        val lista = ArrayList<Bitmap>()

        val archivos = assetManager.list(carpeta) ?: return emptyList()

        for (archivo in archivos.sorted()) {
            val input = assetManager.open("$carpeta/$archivo")
            lista.add(BitmapFactory.decodeStream(input))
            input.close()
        }

        return lista
    }
}

