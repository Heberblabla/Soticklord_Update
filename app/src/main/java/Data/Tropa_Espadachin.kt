package Data

import com.waos.soticklord.R
import kotlin.math.ceil
import kotlin.random.Random

class Tropa_Espadachin : Tropa(
    nombre = "Espadachin",
    nivel = 1,
    vida = 420,
    ataque_base = 50,
    daño_critico = 1.8,
    probabilidad_de_critico = 0.45,
    aereo = false,
    estado_de_vida = true,
    rutaviva = R.drawable.espadachin_tropa,
    rutamuerta = R.drawable.tropa_muerta,
    turnoActivo = true,
    turnoDoble = false
) {

    // 🔹 Dato único de esta tropa
    var vidavida: Int = 420

    private fun daño(): Int {
        val suerte = Random.nextDouble()
        return if (suerte < probabilidad_de_critico) {
            ceil(ataque_base * daño_critico).toInt()
        } else {
            ataque_base
        }
    }

    fun ataqueNormal(enemigos: ArrayList<Tropa>, posicion: Int) {
        val daño = daño()
        enemigos[posicion].vida -= daño
    }

    fun contraataque(enemigos: ArrayList<Tropa>, posicion: Int) {
        val daño = daño()
        val contraataque = ((vidavida - vida) / 2) + daño
        enemigos[posicion].vida -= contraataque
    }

    fun estocadaVeloz(enemigos: ArrayList<Tropa>, posicion: Int) {
        vida -= 60
        val dañoTotal = daño() * 2
        enemigos[posicion].vida -= dañoTotal
    }
}
