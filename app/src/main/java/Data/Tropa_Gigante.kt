package Data

import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Tropa_Gigante(Nivel:Int = 1) : Tropa(
    nombre = "Gigante",
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

    private fun daño(): Int {
        val suerte = Random.nextDouble()
        return if (suerte < probabilidad_de_critico) {
            ceil(ataque_base * daño_critico).toInt()
        } else {
            ataque_base
        }
    }

    fun ataqueNormal(enemigos: ArrayList<Tropa>, posicion: Int) {
        val daño = daño()
        enemigos[posicion].vida -= daño
    }

    fun terremoto(enemigos: ArrayList<Tropa>) {
        val daño = 50
        for (i in 0 until enemigos.size.coerceAtMost(6)) {
            enemigos[i].vida -= daño
        }
    }
}
