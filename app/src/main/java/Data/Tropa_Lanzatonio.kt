package Data

import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random


class Tropa_Lanzatonio(Nivel:Int = 1) : Tropa(
    nombre = "Tropa_Lanzatonio",
    nivel = Nivel,
    vida = calcularVida(550,Nivel),
    ataque_base = calcularAtaque(100,Nivel),
    daño_critico = calcularDañoCritico(1.5,Nivel),
    probabilidad_de_critico = calcularProbCritico(0.5,Nivel),
    aereo = false,
    estado_de_vida = true,
    rutaviva = R.drawable.lanzatonio_tropa,
    rutamuerta = R.drawable.tropa_muerta,
    turnoActivo = true,
    turnoDoble = false
),Serializable {
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

    private fun Daño(): Int {
        val daño: Int
        val random = Random.Default
        val suerte = random.nextDouble()

        if (suerte < this.probabilidad_de_critico) {
            val x = this.ataque_base * this.daño_critico
            daño = ceil(x).toInt() // convertir a int redondeando hacia arriba
        } else {
            daño = this.ataque_base // golpe normal
        }

        return daño
    }

    //metodo principal para atcar
    fun Ataque_normal(enemigos: ArrayList<Tropa?>, posicion: Int) {
        val daño = Daño()
        val nuevavida = enemigos.get(posicion)!!.vida - daño
        enemigos.get(posicion)!!.vida = nuevavida
    }

    fun Estocada(enemigos: ArrayList<Tropa?>, posicion: Int) { //30%de probabilida de multiplicar tu daño x 4
        val daño: Int
        val random = Random.Default
        val suerte = random.nextDouble()

        if (suerte < 0.3) {
            val x = (this.ataque_base * 4).toDouble()
            daño = ceil(x).toInt() // convertir a int redondeando hacia arriba
        } else {
            daño = this.ataque_base // golpe normal
        }

        val nuevavida = enemigos.get(posicion)!!.vida - daño
        enemigos.get(posicion)!!.vida = nuevavida
    }

    fun Bloqueo(enemigos: ArrayList<Tropa?>?, posicion: Int) { //aumenta + 100 puntos de vida
        this.vida += 100
    }

    override fun clonar(): Tropa {
        val copia = Tropa_Lanzatonio(this.nivel)
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
