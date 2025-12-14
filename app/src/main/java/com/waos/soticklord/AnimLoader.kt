package com.waos.soticklord

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

object AnimLoader {

    fun cargarFrames(context: Context , nombreCarpeta: String): List<Bitmap> {
        val assetManager = context.assets
        val lista = ArrayList<Bitmap>()

        try {
            val archivos = assetManager.list(nombreCarpeta) ?: return emptyList()

            for (archivo in archivos.sorted()) {
                val input = assetManager.open("$nombreCarpeta/$archivo")
                val bmp = BitmapFactory.decodeStream(input)
                lista.add(bmp)
                input.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return lista
    }
}
