package Data

import com.waos.soticklord.GlobalData
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Reyna_Shantal (
    Nivel:Int = 1
):
    Tropa(
        nombre = "Reyna_Shantal",
        nivel = Nivel,
        vida = calcularVida(1000,Nivel),
        ataque_base = calcularAtaque(100,Nivel),
        daño_critico = calcularDañoCritico(3.0,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.50,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.reyna_shantal,
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
        val daño = daño()
        val nuevaVida = enemigos[posicion].vida - daño
        enemigos[posicion].vida = nuevaVida
    }

    fun tiro_perfecto(enemigos: ArrayList<Tropa>, posicion: Int) {
        enemigos[posicion].vida -= 10000
    }

    fun lluvia_de_flechas(enemigos: ArrayList<Tropa>, posicion: Int) {
        for (i in enemigos.indices) {
            enemigos[i].vida -= 50
        }
    }


    override fun clonar(): Tropa {
        val copia = Reyna_Shantal(this.nivel)
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