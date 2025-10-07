package Data

import com.waos.soticklord.R
import kotlin.math.ceil
import kotlin.random.Random
import java.io.Serializable
class Tropa_Lanzatonio(Nivel:Int = 1) : Tropa(
    nombre = "Lanzatonio",
    nivel = Nivel,
    vida = calcularVida(550,Nivel),
    ataque_base = calcularAtaque(100,Nivel),
    daño_critico = calcularDañoCritico(1.5,Nivel),
    probabilidad_de_critico = calcularProbCritico(0.5,Nivel),
    aereo = false,
    estado_de_vida = true,
    rutaviva = R.drawable.lanzatonio_tropa,
    rutamuerta = R.drawable.tropa_muerta,
    turnoActivo = true,
    turnoDoble = false
),Serializable {
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

    private fun calcularDaño(): Int {
        val suerte = Random.nextDouble()
        return if (suerte < probabilidad_de_critico) {
            ceil(ataque_base * daño_critico).toInt()
        } else {
            ataque_base
        }
    }

    fun ataqueNormal(enemigos: ArrayList<Tropa>, posicion: Int) {
        val daño = calcularDaño()
        enemigos[posicion].vida -= daño
    }

    fun estocada(enemigos: ArrayList<Tropa>, posicion: Int) {
        val suerte = Random.nextDouble()
        val daño = if (suerte < 0.3) {
            ceil(ataque_base * 4.0).toInt()
        } else {
            ataque_base
        }
        enemigos[posicion].vida -= daño
    }

    fun bloqueo() {
        this.vida += 100
    }
}
