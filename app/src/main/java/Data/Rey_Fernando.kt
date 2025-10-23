package Data

import com.waos.soticklord.GlobalData
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Rey_Fernando (
    Nivel:Int = 1
):
    Tropa(
        nombre = "Rey_Fernando",
        nivel = Nivel,
        vida = calcularVida(1500,Nivel),
        ataque_base = calcularAtaque(100,Nivel),
        daño_critico = calcularDañoCritico(4.0,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.90,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.rey_fernando,
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

    fun Estaca_Feroz(enemigos: ArrayList<Tropa>, posicion: Int) {
        enemigos[posicion].vida -= 500
    }

    fun Clavada_de_pico(enemigos: ArrayList<Tropa>, posicion: Int) {
        for (i in enemigos.indices) {
            enemigos[i].vida -= this.ataque_base
        }
    }

    fun la_muerte_no_es_una_opcion(enemigos: ArrayList<Tropa>, posicion: Int) {
        this.vida += 500
    }



    override fun clonar(): Tropa {
        val copia = Rey_Fernando(this.nivel)
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