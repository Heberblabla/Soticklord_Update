package Data

import com.waos.soticklord.R
import kotlin.math.ceil
import kotlin.random.Random

class Rey_Lanzatonio : Tropa(
    nombre = "Rey_Lanzatonio",
    vida = 950,
    ataque_base = 150,
    daño_critico = 1.5,
    probabilidad_de_critico = 0.50,
    aereo = false,
    estado_de_vida = true,
    rutaviva = R.drawable.rey_lanzatonio,
    rutamuerta = R.drawable.tropa_muerta,
    turnoActivo = true,
    turnoDoble = false
) {

    private fun calcularDaño(): Int {
        val suerte = Random.nextDouble()
        return if (suerte < probabilidad_de_critico) {
            ceil(ataque_base * daño_critico).toInt()
        } else {
            ataque_base
        }
    }

    fun ataqueNormal(enemigos: ArrayList<Tropa>, posicion: Int) {
        val daño = calcularDaño()
        enemigos[posicion].vida -= daño
    }

    fun lanzaImperial(enemigos: ArrayList<Tropa>, posicion: Int) {
        val daño = calcularDaño() * 2
        enemigos[posicion].vida -= daño
    }

    fun bloqueoReal() {
        this.vida += 120
    }

    fun tormentaDeLanzas(enemigos: ArrayList<Tropa>, posicion: Int) {
        var dañoTotal = 0
        val ataqueOriginal = ataque_base
        repeat(25) {
            ataque_base = 10
            dañoTotal += calcularDaño()
        }
        // opcional: restaurar el ataque original (en Java original se quedaba en 10)
        ataque_base = ataqueOriginal

        enemigos[posicion].vida -= dañoTotal
    }
}
