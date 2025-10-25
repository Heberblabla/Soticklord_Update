package Data.Especiales

import Data.Tropa
import Data.Tropa_Gigante
import com.waos.soticklord.GlobalData
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

    fun Ataque_Normal(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        val daño = daño() // calcula el daño según crítico o no
        enemigos[posicion]!!.Recivir_daño(this,daño)

    }

    fun mejoramiento(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        this.vida += 500
        this.ataque_base += 500
        this.daño_critico += 5
        this.probabilidad_de_critico += 0.05
    }

    fun Compañia_cercana(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        if(Waos) {
                GlobalData.Jugador1[0]!!.vida += (GlobalData.Jugador1[0]!!.vida * 0.25).toInt()
                GlobalData.Jugador1[1]!!.vida += (GlobalData.Jugador1[1]!!.vida * 0.25).toInt()
                GlobalData.Jugador1[2]!!.vida += (GlobalData.Jugador1[2]!!.vida * 0.25).toInt()
                GlobalData.Jugador1[3]!!.vida += (GlobalData.Jugador1[3]!!.vida * 0.25).toInt()
                GlobalData.Jugador1[4]!!.vida += (GlobalData.Jugador1[4]!!.vida * 0.25).toInt()
                GlobalData.Jugador1[5]!!.vida += (GlobalData.Jugador1[5]!!.vida * 0.25).toInt()
        }
        if(!Waos) {
                GlobalData.Jugador2[0]!!.vida += (GlobalData.Jugador2[0]!!.vida * 0.25).toInt()
                GlobalData.Jugador2[1]!!.vida += (GlobalData.Jugador2[1]!!.vida * 0.25).toInt()
                GlobalData.Jugador2[2]!!.vida += (GlobalData.Jugador2[2]!!.vida * 0.25).toInt()
                GlobalData.Jugador2[3]!!.vida += (GlobalData.Jugador2[3]!!.vida * 0.25).toInt()
                GlobalData.Jugador2[4]!!.vida += (GlobalData.Jugador2[4]!!.vida * 0.25).toInt()
                GlobalData.Jugador2[5]!!.vida += (GlobalData.Jugador2[5]!!.vida * 0.25).toInt()
        }

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