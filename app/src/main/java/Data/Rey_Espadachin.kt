package Data

import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Rey_Espadachin(Nivel:Int = 1): Tropa(
    nombre = "Rey_Espadachin",
    nivel = Nivel,
    vida = calcularVida(850,Nivel),
    ataque_base = calcularAtaque(110,Nivel),
    daño_critico = calcularDañoCritico(1.8,Nivel),
    probabilidad_de_critico = calcularProbCritico(0.3,Nivel),
    aereo = false,
    estado_de_vida = true,
    rutaviva = R.drawable.rey_espadachin,   // tu imagen en drawable
    rutamuerta = R.drawable.tropa_muerta,   // imagen al morir
), Serializable {

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
