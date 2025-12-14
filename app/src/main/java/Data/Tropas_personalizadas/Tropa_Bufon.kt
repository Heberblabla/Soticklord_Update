package Data.Tropas_personalizadas

import Data.Tropa
import Data.Tropa.Companion.calcularAtaque
import Data.Tropa.Companion.calcularDañoCritico
import Data.Tropa.Companion.calcularProbCritico
import Data.Tropa.Companion.calcularVida
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Tropa_Bufon(Nivel:Int = 1) : Tropa(
    nombre = "Tropa_Bufon",
    nivel = Nivel,
    vida = calcularVida(750,Nivel),
    ataque_base = calcularAtaque(50,Nivel),
    daño_critico = calcularDañoCritico(1.5,Nivel),
    probabilidad_de_critico = calcularProbCritico(0.2,Nivel),
    aereo = false,
    estado_de_vida = true,
    rutaviva = R.drawable.bufon,
    rutamuerta = R.drawable.tropa_muerta,
    turnoActivo = true,
    turnoDoble =  false,
    cantidad_espinas = 0.00,
    cantidad_escudos = 0.30,
    precision = 100
), Serializable {
    var ultimo_daño = 0;
    override fun toString(): String {
        val criticoPorcentaje = String.format("%.2f", probabilidad_de_critico * 100)
        val dañoCriticoPorcentaje = String.format("%.2f", daño_critico * 100)

        return """
        • Nombre: $nombre
        • Nivel: $nivel  
        • Vida: $vida
        • Ataque Base: $ataque_base
        • Daño Crítico: $dañoCriticoPorcentaje %
        • Probabilidad de Crítico: $criticoPorcentaje %
        • Aéreo: $aereo
        • Turno Doble: $turnoDoble
        • Defensa : ${cantidad_escudos * 100}%
    """.trimIndent()
    }

    private fun daño(): Int {
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
    fun Ataque_normal(enemigos: ArrayList<Tropa?>, posicion: Int, Waos: Boolean) {

        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        val daño = daño()
        enemigos[posicion]!!.Recivir_daño(this,daño)
    }

    fun Contrataque(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if (xd < this.precision) {
            //sigue realizando tu atque
        } else {
            return
        }
        if (ultimo_daño == 0) {
            val daño = daño()
            enemigos[posicion].Recivir_daño(this, daño)
            return
        }

        var daño = daño()
        enemigos[posicion].Recivir_daño(this, daño)
    }

    override fun Habilidad_Especial(Waos: Boolean){

    }

    override fun clonar(): Tropa {
        val copia = Tropa_Bufon(this.nivel)
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

    override fun Recivir_daño(tropa: Tropa, Ataque :Int) {

        ultimo_daño = Ataque;
        this.ataque_base = ultimo_daño
        var devolvergolpe = Random.nextInt(100)
        if(devolvergolpe < 50 && this.estado_de_vida){
            tropa.vida -= Ataque
            return
        }else{
            //no devuelves nada
        }

        if (this.cantidad_espinas > 0) {
            tropa.vida -= (Ataque * cantidad_espinas).toInt()
        }

        if (this.cantidad_escudos > 0) {
            val escudo = cantidad_escudos.coerceAtMost(1.0)
            this.vida -= (Ataque - (Ataque * escudo)).toInt()
        }else{
            this.vida -= Ataque

        }
    }

}
