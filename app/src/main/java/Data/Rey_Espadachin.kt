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
    turnoActivo = true,
    turnoDoble =  false
), Serializable {

    override fun toString(): String {
        return """
            Nombre: $nombre
            Nivel: $nivel
            Vida: $vida
            Ataque base: $ataque_base
            Daño crítico: $daño_critico
            Prob. crítico: $probabilidad_de_critico
            Aéreo: $aereo
            Estado vida: $estado_de_vida
            Turno activo: $turnoActivo
            Turno doble: $turnoDoble
        """.trimIndent()
    }
    private fun daño(): Int {
        val random = Random.Default
        val suerte = random.nextDouble()

        return if (suerte < this.probabilidad_de_critico) {
            val x = this.ataque_base * this.daño_critico
            ceil(x).toInt() // redondea hacia arriba
        } else {
            this.ataque_base // golpe normal
        }
    }

    fun ataqueNormal(enemigos: ArrayList<Tropa>, posicion: Int) {
        val daño = daño()
        val nuevaVida = enemigos[posicion].vida - daño
        enemigos[posicion].vida = nuevaVida
    }

    fun espadazoReal(enemigos: ArrayList<Tropa>, posicion: Int) {
        this.vida -= 50
        var daño = daño() * 2
        val nuevaVida = enemigos[posicion].vida - daño
        enemigos[posicion].vida = nuevaVida
    }

    fun enGuardia(enemigos: ArrayList<Tropa>, posicion: Int) {
        this.vida += 120
    }

    fun ataqueFinal(enemigos: ArrayList<Tropa>, posicion: Int) {
        this.vida -= 150
        var daño = daño() * 4
        val nuevaVida = enemigos[posicion].vida - daño
        enemigos[posicion].vida = nuevaVida
    }

}
