package Data

import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random


class Tropa_Arquero (Nivel:Int = 1): Tropa(
    nombre = "Tropa_Arquero",
    nivel = Nivel,
    vida = calcularVida(300,Nivel),
    ataque_base = calcularAtaque(40,Nivel),
    daño_critico = calcularDañoCritico(2.0,Nivel),
    probabilidad_de_critico = calcularProbCritico(0.30,Nivel),
    aereo = true,
    estado_de_vida = true,
    rutaviva = R.drawable.arquero_tropa,
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
    fun Ataque_normal(enemigos: ArrayList<Tropa?>, posicion: Int,Waos: Boolean) {
        val daño = Daño()
        enemigos[posicion]!!.Recivir_daño(this,daño)
    }

    fun Flecha_de_Sangre(enemigos: ArrayList<Tropa?>, posicion: Int,Waos: Boolean) {
        this.vida -= (this.vida * 0.1).toInt()
        var daño = Daño()
        daño = daño * 3
        enemigos[posicion]!!.Recivir_daño(this,daño)
    }

    fun Flecha_penetrante(enemigos: ArrayList<Tropa?>, posicion: Int,Waos: Boolean) { //20% de probabilida de multiplicar tu daño x 5
        val daño: Int
        val random = Random.Default
        val suerte = random.nextDouble(0.0,1.0)

        if (suerte < 0.2) {
            val x = (this.ataque_base * 5).toDouble()
            daño = ceil(x).toInt() // convertir a int redondeando hacia arriba
        } else {
            daño = (this.ataque_base/2).toInt() // golpe normal
        }

        enemigos[posicion]!!.Recivir_daño(this,daño)
    }

    override fun clonar(): Tropa {
        val copia = Tropa_Arquero(this.nivel)
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
