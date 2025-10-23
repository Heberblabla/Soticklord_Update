package Data

import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Reyna_Darisce (
    Nivel:Int = 1
):
    Tropa(
        nombre = "Reyna_Darisce",
        nivel = Nivel,
        vida = calcularVida(1000,Nivel),
        ataque_base = calcularAtaque(200,Nivel),
        daño_critico = calcularDañoCritico(5.0,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.80,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.reyna_darisce,
        rutamuerta = R.drawable.tropa_muerta,
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
        val daño = daño() // calcula el daño según crítico o no
        val nuevaVida = enemigos[posicion].vida - daño
        enemigos[posicion].vida = nuevaVida
    }

    fun mejoramiento(enemigos: ArrayList<Tropa>, posicion: Int) {
        this.vida += 500
        this.ataque_base += 500
        this.daño_critico += 5
        this.probabilidad_de_critico += 0.05
    }


    override fun clonar(): Tropa {
        val copia = Reyna_Darisce(this.nivel)
        copia.nombre = this.nombre
        copia.vida = this.vida
        copia.ataque_base = this.ataque_base
        copia.daño_critico = this.daño_critico
        copia.probabilidad_de_critico = this.probabilidad_de_critico
        copia.aereo = this.aereo
        copia.estado_de_vida = this.estado_de_vida
        copia.rutaviva = this.rutaviva
        copia.rutamuerta = this.rutamuerta
        copia.turnoActivo = this.turnoActivo
        copia.turnoDoble = this.turnoDoble
        return copia
    }


}