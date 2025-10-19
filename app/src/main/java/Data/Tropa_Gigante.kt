package Data

import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random


class Tropa_Gigante(Nivel:Int = 1) : Tropa(
    nombre = "Tropa_Gigante",
    nivel = Nivel,
    vida = calcularVida(800,Nivel),
    ataque_base = calcularAtaque(100,Nivel),
    daño_critico = calcularDañoCritico(5.0,Nivel),
    probabilidad_de_critico = calcularProbCritico(0.10,Nivel),
    aereo = false,
    estado_de_vida = true,
    rutaviva = R.drawable.gigante_tropa,
    rutamuerta = R.drawable.tropa_muerta,
    turnoActivo = true,
    turnoDoble = false
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
    fun Ataque_normal(enemigos: ArrayList<Tropa?>, posicion: Int) {
        val daño: Int = Daño()
        val nuevavida = enemigos.get(posicion)!!.vida - daño
        enemigos.get(posicion)!!.vida = nuevavida
    }

    fun Terremoto(enemigos: ArrayList<Tropa?>, posicion: Int) {
        val daño = 50
        enemigos.get(0)!!.vida = enemigos.get(0)!!.vida - daño
        enemigos.get(1)!!.vida = enemigos.get(1)!!.vida - daño
        enemigos.get(2)!!.vida = enemigos.get(2)!!.vida - daño
        enemigos.get(3)!!.vida = enemigos.get(3)!!.vida - daño
        enemigos.get(4)!!.vida = enemigos.get(4)!!.vida - daño
        enemigos.get(5)!!.vida = enemigos.get(5)!!.vida - daño
    }

}
