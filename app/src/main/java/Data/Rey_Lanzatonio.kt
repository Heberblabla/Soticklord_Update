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


    fun Ataque_normal(enemigos: ArrayList<Tropa?>, posicion: Int) {
        val daño = Daño()
        val nuevavida = enemigos.get(posicion)!!.vida - daño
        enemigos.get(posicion)!!.vida = nuevavida
    }

    fun Lanza_Imperial(enemigos: ArrayList<Tropa?>, posicion: Int) {
        var daño = Daño()
        daño = daño * 2
        val nuevavida = enemigos.get(posicion)!!.vida - daño
        enemigos.get(posicion)!!.vida = nuevavida
    }

    fun Bloqueo_real(enemigos: ArrayList<Tropa?>?, posicion: Int) {
        this.vida = this.vida + 120
    }

    fun Tormenta_de_Lanzas(enemigos: ArrayList<Tropa?>, posicion: Int) {
        val ataque = this.ataque_base
        var daño_total = 0
        for (i in 0..24) {
            this.ataque_base = 10
            daño_total = daño_total + Daño()
        }
        val nuevavida = enemigos.get(posicion)!!.vida - daño_total
        enemigos.get(posicion)!!.vida = nuevavida
    }

}
