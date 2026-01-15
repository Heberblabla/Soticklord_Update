package com.waos.soticklord

import Data.Tropa
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import java.io.File
import kotlin.collections.get
import com.google.android.play.core.assetpacks.AssetPackManagerFactory


object Animador {

    // Hilos individuales por tropa
    private val hilosJugador1 = mutableMapOf<Int, Thread>()
    private val hilosJugador2 = mutableMapOf<Int, Thread>()


    private var hiloExtraDerecha: Thread? = null
    private var hiloAnimacion: Thread? = null

    //aempezar aniamciones de tropas y reyes

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


    //empesar animacion del gignate esquelto ,imagnes derecha o izquierda
    fun empezar_animacion_extra2(activity: Activity, carpeta: String, id :Int) {
        android.util.Log.e("ANIMADOR", "➡️ iniciar animacion extra2")

        val img = activity.findViewById<ImageView>(id)


        if (img == null) {
            android.util.Log.e("ANIMADOR", "❌ imagen_derecha es NULL")
            return
        } else {
            android.util.Log.e("ANIMADOR", "✅ imagen_derecha encontrada")
        }

        hiloExtraDerecha?.interrupt()

        hiloExtraDerecha = Thread {
            android.util.Log.e("ANIMADOR", "🧵 hilo iniciado")

            val archivos = activity.assets.list(carpeta)
            if (archivos == null) {
                android.util.Log.e("ANIMADOR", "❌ assets.list devolvió null: $carpeta")
                return@Thread
            }

            android.util.Log.e(
                "ANIMADOR",
                "📂 archivos encontrados: ${archivos.joinToString()}"
            )

            var frameIndex = 0

            try {
                while (!Thread.currentThread().isInterrupted) {
                    android.util.Log.e("ANIMADOR", "▶ frame $frameIndex")

                    val bitmap = activity.assets
                        .open("$carpeta/${archivos[frameIndex]}")
                        .use { BitmapFactory.decodeStream(it) }

                    activity.runOnUiThread {
                        img.setImageBitmap(bitmap)
                    }

                    frameIndex = (frameIndex + 1) % archivos.size
                    Thread.sleep(200)
                }
            } catch (e: Exception) {
                android.util.Log.e("ANIMADOR", "💥 EXCEPCIÓN", e)
            }
        }

        hiloExtraDerecha?.start()
    }

    //complemento con animacion con trnasicion
    enum class Estado {
        REPOSO,
        ACCION_IDA,
        ACCION_VUELTA
    }
    fun animacionConTransicion(
        activity: Activity,
        img: ImageView,
        carpetaReposo: String,
        carpetaAccion: String
    ) {
        Thread {
            val reposo = cargarFrames(activity,carpetaReposo)
            val accion = cargarFrames(activity,carpetaAccion)

            var estado = Estado.REPOSO
            var index = 0
            var direccion = 1

            while (!Thread.currentThread().isInterrupted) {

                val frames = when (estado) {
                    Estado.REPOSO -> reposo
                    Estado.ACCION_IDA,
                    Estado.ACCION_VUELTA -> accion
                }

                activity.runOnUiThread {
                    img.setImageBitmap(frames[index])
                }

                Thread.sleep(120)

                when (estado) {
                    Estado.REPOSO -> {
                        index++
                        if (index >= reposo.size) {
                            index = 0
                            if (GlobalData.disparador) {
                                GlobalData.disparador = false
                                estado = Estado.ACCION_IDA
                                index = 0
                                direccion = 1
                            }
                        }
                    }

                    Estado.ACCION_IDA -> {
                        index += direccion
                        if (index >= accion.size - 1) {
                            estado = Estado.ACCION_VUELTA
                            direccion = -1
                        }
                    }

                    Estado.ACCION_VUELTA -> {
                        index += direccion
                        if (index <= 0) {
                            estado = Estado.REPOSO
                            index = 0
                            direccion = 1
                        }
                    }
                }
            }
        }.start()
    }



    //animar los albumes

    fun Animacion_album_rey(activity: Activity){
        iniciarAnimacion1(activity,0,R.id.Imagen_grande)
    }
    fun Animacion_album_tropa(activity: Activity){
        iniciarAnimacion1(activity,5,R.id.Imagen_grande)
    }


    //detener todo
    fun detenerTodo() {
        hilosJugador1.values.forEach {
            it.interrupt()
        }
        hilosJugador2.values.forEach {
            it.interrupt()
        }
        hilosJugador1.clear()
        hilosJugador2.clear()

        hiloAnimacion?.interrupt()
        hiloAnimacion = null
    }

    // ---------- PRIVADO ----------
    //inicia animaciones con los hilos de los jugadores tropas y reyes
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

    //funcioens para animar con asset interno normal

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
            var direccion = 1 // 1 = adelante, -1 = atrás
            var nombreActual = ""
            var frames = emptyList<Bitmap>()

            try {
                while (!Thread.interrupted() && GlobalData.Desea_nimaciones) {

                    val tropa = tropaGetter() ?: break
                    if (!tropa.estado_de_vida) break

                    // Detectar cambio de animación
                    if (tropa.nombre != nombreActual) {
                        nombreActual = tropa.nombre
                        frames = cargarFrames(activity,nombreActual)
                        frameIndex = 0
                        direccion = 1
                    }

                    if (frames.isEmpty()) {
                        Thread.sleep(100)
                        continue
                    }

                    activity.runOnUiThread {
                        imageView.setImageBitmap(frames[frameIndex])
                    }

                    // 🔁 LÓGICA IDA Y VUELTA
                    frameIndex += direccion

                    if (frameIndex >= frames.size - 1) {
                        direccion = -1
                    } else if (frameIndex <= 0) {
                        direccion = 1
                    }

                    Thread.sleep(100)
                }

            } catch (_: InterruptedException) {
                // hilo terminado correctamente
            }
        }

        mapaHilos[index] = hilo
        hilo.start()
    }


    private fun cargarFrames(context: Context, carpeta: String): List<Bitmap> {
        val assetManager = context.assets
        val lista = ArrayList<Bitmap>()

        Log.d("ANIM", "Buscando en carpeta de assets: '$carpeta'")

        val archivos: Array<String>?
        try {
            archivos = assetManager.list(carpeta)
        } catch (e: Exception) {
            Log.e("ANIM", "Error al listar la carpeta '$carpeta'. ¿La ruta es correcta?", e)
            return emptyList()
        }

        if (archivos == null || archivos.isEmpty()) {
            Log.w("ANIM", "No se encontraron archivos en '$carpeta' o la carpeta no existe.")
            return emptyList()
        }

        Log.d("ANIM", "Archivos encontrados: ${archivos.toList()}")

        for (archivo in archivos.sorted()) {
            // Ignora subdirectorios si los hubiera (list() a veces los devuelve)
            if (!archivo.contains(".")) continue

            try {
                val rutaCompleta = "$carpeta/$archivo"
                val inputStream = assetManager.open(rutaCompleta)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream.close()

                if (bitmap != null) {
                    lista.add(bitmap)
                } else {
                    Log.w("ANIM", "No se pudo decodificar el bitmap para el archivo: $rutaCompleta")
                }
            } catch (e: Exception) {
                Log.e("ANIM", "Error al abrir o decodificar el archivo '$archivo' en '$carpeta'", e)
            }
        }

        if (lista.isEmpty()) {
            Log.w("ANIM", "Se encontraron archivos, pero no se pudo cargar ningún bitmap de la carpeta: $carpeta")
        }

        return lista
    }


    // funciones para animar con asets externo
    private fun iniciarAnimacionGeneral2(
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
            var direccion = 1 // 1 = adelante, -1 = atrás
            var nombreActual = ""
            var frames = emptyList<Bitmap>()

            try {
                while (!Thread.interrupted() && GlobalData.Desea_nimaciones) {

                    val tropa = tropaGetter() ?: break
                    if (!tropa.estado_de_vida) break

                    // Detectar cambio de animación
                    if (tropa.nombre != nombreActual) {
                        nombreActual = tropa.nombre
                        frames = cargarFrames2(activity, "images_pack", nombreActual)
                        frameIndex = 0
                        direccion = 1
                    }


                    if (frames.isEmpty()) {
                        Thread.sleep(100)
                        continue
                    }

                    activity.runOnUiThread {
                        imageView.setImageBitmap(frames[frameIndex])
                    }

                    // 🔁 LÓGICA IDA Y VUELTA
                    frameIndex += direccion

                    if (frameIndex >= frames.size - 1) {
                        direccion = -1
                    } else if (frameIndex <= 0) {
                        direccion = 1
                    }

                    Thread.sleep(100)
                }

            } catch (_: InterruptedException) {
                // hilo terminado correctamente
            }
        }

        mapaHilos[index] = hilo
        hilo.start()
    }


    private fun cargarFrames2(
        context: Context,
        packName: String,
        carpeta: String
    ): List<Bitmap> {

        val frames = ArrayList<Bitmap>()

        val assetPackManager = AssetPackManagerFactory.getInstance(context)
        val location = assetPackManager.getPackLocation(packName)

        if (location == null) {
            Log.w("ANIM", "Asset pack '$packName' no está disponible aún")
            return emptyList()
        }

        val basePath = location.assetsPath()
        val carpetaFrames = File(basePath, carpeta)

        if (!carpetaFrames.exists() || !carpetaFrames.isDirectory) {
            Log.w("ANIM", "Carpeta '$carpeta' no existe en el asset pack")
            return emptyList()
        }

        val archivos = carpetaFrames.listFiles()
            ?.filter { it.isFile && it.name.endsWith(".png") }
            ?.sortedBy { it.name }
            ?: return emptyList()

        for (archivo in archivos) {
            val bmp = BitmapFactory.decodeFile(archivo.absolutePath)
            if (bmp != null) {
                frames.add(bmp)
            }
        }

        return frames
    }


}

