package Data.Personalizados

import Data.Tropa
import Data.Tropa.Companion.calcularAtaque
import Data.Tropa.Companion.calcularDañoCritico
import Data.Tropa.Companion.calcularProbCritico
import Data.Tropa.Companion.calcularVida
import com.waos.soticklord.GlobalData
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Rey_El_Pro (
    Nivel:Int = 1
):
    Tropa(
        nombre = "Rey_El_Pro",
        nivel = Nivel,
        vida = calcularVida(1000,Nivel),
        ataque_base = calcularAtaque(100,Nivel),
        daño_critico = calcularDañoCritico(10.0,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.90,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.rey_el_pro,
        rutamuerta = R.drawable.tropa_muerta,
        turnoActivo = true,
        turnoDoble =  false,
        cantidad_espinas = 0.00,
        cantidad_escudos = 0.00
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

    fun Ataque_normal(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        val daño = daño()
        enemigos[posicion].Recivir_daño(this, daño)
    }

    fun Transformacion(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean){

        if(Waos){

            GlobalData.Jugador1[0] = GlobalData.Jugador2[0] //+10 nivels

        }

        if(!Waos){

            GlobalData.Jugador2[0] = GlobalData.Jugador1[0]

        }

    }


    override fun clonar(): Tropa {
        val copia = Rey_El_Pro(this.nivel)
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

    override fun Recivir_daño(tropa: Tropa, Ataque: Int) {
        if (this.cantidad_escudos > 0) {
            this.vida -= (Ataque * (Ataque * cantidad_escudos)).toInt()
        }
        if (this.cantidad_espinas > 0) {
            tropa.vida -= (Ataque * cantidad_espinas).toInt()
            return
        }

        this.vida -= Ataque
        return
    }

}
