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
    var turnoActivo: Boolean,
    var turnoDoble: Boolean,
    var cantidad_escudos: Int,
    var cantidad_espinas: Int
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
    open fun clonar(): Tropa {
        try {
            // Intenta obtener el constructor primario de la subclase
            val constructor = this::class.constructors.firstOrNull()
            val copia = constructor?.call(this.nivel) as? Tropa ?: return this

            // Copiar todos los valores actuales
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
        } catch (e: Exception) {
            e.printStackTrace()
            return this // fallback
        }
    }


    abstract fun Recivir_daño(tropa: Tropa,Ataque :Int)


}
