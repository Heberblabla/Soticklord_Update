package Data

import com.waos.soticklord.R
import kotlin.math.ceil
import kotlin.random.Random

class Tropa_Gigante : Tropa(
    nombre = "Gigante",
    nivel = 1,
    vida = 800,
    ataque_base = 100,
    daño_critico = 5.0,
    probabilidad_de_critico = 0.10,
    aereo = false,
    estado_de_vida = true,
    rutaviva = R.drawable.gigante_tropa,
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

    fun terremoto(enemigos: ArrayList<Tropa>) {
        val daño = 50
        for (i in 0 until enemigos.size.coerceAtMost(6)) {
            enemigos[i].vida -= daño
        }
    }
}
