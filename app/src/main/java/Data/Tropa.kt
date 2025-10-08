package Data

import com.waos.soticklord.R
import java.io.Serializable

abstract class Tropa(
    var nombre: String,
    var nivel: Int,
    var vida: Int,
    var ataque_base: Int,
    var daño_critico: Double,
    var probabilidad_de_critico: Double,
    var aereo: Boolean,
    var estado_de_vida: Boolean,
    var rutaviva: Int = R.drawable.tropa_default,
    var rutamuerta: Int = R.drawable.tropa_default,
    var turnoActivo: Boolean = true,
    var turnoDoble: Boolean = false
): Serializable  {
    companion object {

        // Aumenta 10% compuesto por nivel
        fun calcularVida(base: Int, nivel: Int): Int {
            if (nivel <= 1) return base
            val multiplicador = Math.pow(1.10, (nivel - 1).toDouble())
            return (base * multiplicador).toInt()
        }

        // Aumenta 15% compuesto por nivel
        fun calcularAtaque(base: Int, nivel: Int): Int {
            if (nivel <= 1) return base
            val multiplicador = Math.pow(1.15, (nivel - 1).toDouble())
            return (base * multiplicador).toInt()
        }

        // Aumenta 5% simple por nivel
        fun calcularDañoCritico(base: Double, nivel: Int): Double {
            if (nivel <= 1) return base
            return base + (0.05 * (nivel - 1))
        }

        // Aumenta 2% simple por nivel (en valor decimal)
        fun calcularProbCritico(base: Double, nivel: Int): Double {
            if (nivel <= 1) return base
            return base + (0.02 * (nivel - 1))
        }
    }

    fun imprimir() {
        println("nombre : $nombre")
        println("vida: $vida")
        println("ataque base: $ataque_base")
        println("daño critico: $daño_critico")
        println("probabilidad de critico: $probabilidad_de_critico")
        println("es aereo?: $aereo")
        println("su estado de vida es: $estado_de_vida")
        println("ruta de imagen viva: $rutaviva")
        println("ruta de imagen muerta: $rutamuerta")
    }
    open fun recibirDaño(cantidad: Int, atacante: Tropa) {}

}
