package Data.Especiales

import Data.Tropa
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Rey_Cristian (
    Nivel:Int = 1
):
    Tropa(
        nombre = "Rey_Cristian",
        nivel = Nivel,
        vida = calcularVida(950,Nivel),
        ataque_base = calcularAtaque(50,Nivel),
        daño_critico = calcularDañoCritico(10.0,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.90,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.rey_cristian,
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
    fun ataqueNormal(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        val daño = daño()
        enemigos[posicion]!!.Recivir_daño(this,daño)
    }

    fun Baje_de_vida_estelar(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        for (i in enemigos.indices) {
            var daño = (enemigos[i].vida * 0.3).toInt()
            enemigos[posicion]!!.vida = enemigos[posicion]!!.vida - daño
        }
    }

    fun Empeoramiento_estelar(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        for (i in enemigos.indices) {
            enemigos[i].ataque_base -= (enemigos[1].ataque_base * 0.5).toInt()
        }
    }

    fun Se_te_acabo_el_tiempo(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        for (i in enemigos.indices) {
            enemigos[posicion]!!.Recivir_daño(this,this.ataque_base)
        }
    }


    override fun clonar(): Tropa {
        val copia = Rey_Cristian(this.nivel)
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