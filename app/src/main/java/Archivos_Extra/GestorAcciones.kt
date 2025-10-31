package Archivos_Extra

import com.waos.soticklord.GlobalData
import com.waos.soticklord.Batalla

object GestorAcciones {

    fun Procesar() {
        GlobalData.Jugador1[0]!!.Habilidad_Especial(true)
        GlobalData.Jugador2[0]!!.Habilidad_Especial(false)
    }

}
