package Data

import com.waos.soticklord.R
import kotlin.math.ceil
import kotlin.random.Random

class Rey_Espadachin : Tropa(
    nombre = "Rey_Espadachin",
    nivel = 1,
    vida = 850,
    ataque_base = 110,
    daño_critico = 1.8,
    probabilidad_de_critico = 0.30,
    aereo = false,
    estado_de_vida = true,
    rutaviva = R.drawable.rey_espadachin,   // tu imagen en drawable
    rutamuerta = R.drawable.tropa_muerta,   // imagen al morir
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
        val enemigo = enemigos[posicion]
        enemigo.vida -= daño
    }

    fun espadazoReal(enemigos: ArrayList<Tropa>, posicion: Int) {
        this.vida -= 50
        var daño = calcularDaño() * 2
        val enemigo = enemigos[posicion]
        enemigo.vida -= daño
    }

    fun enGuardia() {
        this.vida += 120
    }

    fun ataqueFinal(enemigos: ArrayList<Tropa>, posicion: Int) {
        this.vida -= 150
        var daño = calcularDaño() * 4
        val enemigo = enemigos[posicion]
        enemigo.vida -= daño
    }
}
