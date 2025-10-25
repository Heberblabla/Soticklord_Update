package Data

import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random


class Tropa_Espadachin(Nivel:Int = 1 ) : Tropa(
    nombre = "Tropa_Espadachin",
    nivel = Nivel,
    vida = calcularVida(420,Nivel),
    ataque_base = calcularAtaque(50,Nivel),
    daño_critico = calcularDañoCritico(1.8,Nivel),
    probabilidad_de_critico = calcularProbCritico(0.45,Nivel),
    aereo = false,
    estado_de_vida = true,
    rutaviva = R.drawable.espadachin_tropa,
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


    fun Estocada_Veloz(enemigos: ArrayList<Tropa?>, posicion: Int,Waos: Boolean) {
        this.vida -= (this.vida * 0.10).toInt()
        var daño = Daño()
        daño = daño * 2
        val nuevavida = enemigos.get(posicion)!!.vida - daño
        enemigos.get(posicion)!!.vida = nuevavida
    }

    override fun clonar(): Tropa {
        val copia = Tropa_Espadachin(this.nivel)
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
