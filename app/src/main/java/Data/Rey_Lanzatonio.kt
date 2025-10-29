package Data

import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random


class Rey_Lanzatonio(Nivel:Int = 1 ) : Tropa(
    nombre = "Rey_Lanzatonio",
    nivel = Nivel,
    vida = calcularVida(950,Nivel),
    ataque_base = calcularAtaque(150,Nivel),
    daño_critico = calcularDañoCritico(1.5,Nivel),
    probabilidad_de_critico = calcularProbCritico(0.5,Nivel),
    aereo = false,
    estado_de_vida = true,
    rutaviva = R.drawable.rey_lanzatonio,
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


    fun Ataque_normal(enemigos: ArrayList<Tropa?>, posicion: Int,Waos: Boolean) {
        val daño = Daño()
        enemigos[posicion]!!.Recivir_daño(this,daño)
    }

    fun Lanza_Imperial(enemigos: ArrayList<Tropa?>, posicion: Int,Waos: Boolean) {
        var daño = Daño()
        daño = daño * 2
        enemigos[posicion]!!.Recivir_daño(this,daño)
    }

    fun Bloqueo_real(enemigos: ArrayList<Tropa?>?, posicion: Int,Waos: Boolean) {
        this.vida += (this.vida * 0.25).toInt()
    }

    fun Tormenta_de_Lanzas(enemigos: ArrayList<Tropa?>, posicion: Int,Waos: Boolean) {
        var ataque1 = this.ataque_base
        this.ataque_base = 10
        for (i in 0..25) {
            var daño = Daño()
            enemigos[posicion]!!.Recivir_daño(this,daño)
        }
        this.ataque_base = ataque1
    }

    override fun clonar(): Tropa {
        val copia = Rey_Lanzatonio(this.nivel)
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
