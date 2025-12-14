package Data.Tropas_personalizadas

import Data.Tropa
import com.waos.soticklord.GlobalData
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random
import kotlin.random.nextInt


class Tropa_Lanzatonio_Medieval(Nivel:Int = 1,Defensaxd:Double) : Tropa(
    nombre = "Tropa_Lanzatonio_Medieval",
    nivel = Nivel,
    vida = calcularVida(750,Nivel),
    ataque_base = calcularAtaque(100,Nivel),
    daño_critico = calcularDañoCritico(1.5,Nivel),
    probabilidad_de_critico = calcularProbCritico(0.5,Nivel),
    aereo = false,
    estado_de_vida = true,
    rutaviva = R.drawable.tropa_escolta,
    rutamuerta = R.drawable.tropa_muerta,
    turnoActivo = true,
    turnoDoble =  false,
    cantidad_espinas = 0.00,
    cantidad_escudos = Defensaxd,
    precision = 100
),Serializable {
    var defensa = Defensaxd

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
    fun Ataque_normal(enemigos: ArrayList<Tropa?>, posicion: Int, Waos: Boolean) {

        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        val daño = Daño()
        enemigos[posicion]!!.Recivir_daño(this,daño)
    }

    fun Estocada(enemigos: ArrayList<Tropa?>, posicion: Int, Waos: Boolean) { //30%de probabilida de multiplicar tu daño x 4

        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        val daño: Int
        val random = Random.Default
        val suerte = random.nextDouble()

        if (suerte < 0.5) {
            val x = (this.ataque_base * 4).toDouble()
            daño = ceil(x).toInt() // convertir a int redondeando hacia arriba
        } else {
            daño = this.ataque_base // golpe normal
        }

        enemigos[posicion]!!.Recivir_daño(this,daño)
    }

    fun Bloqueo(enemigos: ArrayList<Tropa?>?, posicion: Int, Waos: Boolean) { //aumenta + 100 puntos de vida

        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        this.vida += (this.vida * 0.15).toInt()
        this.cantidad_escudos += 0.05
    }

    override fun Habilidad_Especial(Waos: Boolean){

    }

    override fun clonar(): Tropa {
        val copia = Tropa_Lanzatonio_Medieval(this.nivel,this.defensa)
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
