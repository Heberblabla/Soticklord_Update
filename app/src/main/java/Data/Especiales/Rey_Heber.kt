package Data.Especiales

import Data.Tropa
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Rey_Heber (
    Nivel:Int = 1
):
    Tropa(
        nombre = "Rey_Heber",
        nivel = Nivel,
        vida = calcularVida(500,Nivel),
        ataque_base = calcularAtaque(200,Nivel),
        daño_critico = calcularDañoCritico(5.0,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.80,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.rey_heber,
        rutamuerta = R.drawable.tropa_muerta,
        turnoActivo = true,
        turnoDoble =  false,
        cantidad_espinas = 0,
        cantidad_escudos = 0
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

    fun Donde_corress(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        enemigos[posicion].vida = 1
    }

    fun Soy_inevitable(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        this.vida += 10000
    }

    fun Jugamos_jaja(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        enemigos[posicion].ataque_base = 10
        enemigos[posicion].vida = 10

    }



    override fun clonar(): Tropa {
        val copia = Rey_Heber(this.nivel)
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
        copia.cantidad_espinas = this.cantidad_espinas
        copia.cantidad_escudos = this.cantidad_escudos
        return copia
    }
    override fun Recivir_daño(tropa: Tropa,Ataque :Int) {
        if(this.cantidad_escudos > 0){
            this.vida -= (Ataque * (Ataque * cantidad_escudos)).toInt()
        }
        if(this.cantidad_espinas > 0){
            tropa.vida -= (Ataque * cantidad_espinas).toInt()
            return
        }

        this.vida -= Ataque
        return
    }

}