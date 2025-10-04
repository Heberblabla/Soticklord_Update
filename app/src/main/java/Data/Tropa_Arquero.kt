package Data

import com.waos.soticklord.R
import kotlin.math.ceil
import kotlin.random.Random

class Tropa_Arquero : Tropa(
    nombre = "Arquero",
    vida = 300,
    ataque_base = 40,
    daño_critico = 2.0,
    probabilidad_de_critico = 0.30,
    aereo = true,
    estado_de_vida = true,
    rutaviva = R.drawable.arquero_tropa,
    rutamuerta = R.drawable.tropa_muerta,
    turnoActivo = true,
    turnoDoble = false
) {

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

    fun flechaDeSangre(enemigos: ArrayList<Tropa>, posicion: Int) {
        vida -= 50
        val dañoTotal = daño() * 3
        enemigos[posicion].vida -= dañoTotal
    }

    fun flechaPenetrante(enemigos: ArrayList<Tropa>, posicion: Int) {
        val suerte = Random.nextDouble()
        val daño = if (suerte < 0.2) {
            ceil(ataque_base * 5.0).toInt()
        } else {
            ataque_base
        }
        enemigos[posicion].vida -= daño
    }
}
