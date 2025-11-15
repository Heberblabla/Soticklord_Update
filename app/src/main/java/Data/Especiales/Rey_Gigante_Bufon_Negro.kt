package Data.Especiales

import Data.Tropa
import Data.Tropa.Companion.calcularAtaque
import Data.Tropa.Companion.calcularDañoCritico
import Data.Tropa.Companion.calcularProbCritico
import Data.Tropa.Companion.calcularVida
import com.waos.soticklord.GlobalData
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Rey_Gigante_Bufon_Negro(
    Nivel:Int = 1
):
    Tropa(
        nombre = "Rey_Gigante_Bufon_Negro",
        nivel = Nivel,
        vida = calcularVida(3000,Nivel),
        ataque_base = calcularAtaque(500,Nivel),
        daño_critico = calcularDañoCritico(2.0,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.30,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.bufon_caido,
        rutamuerta = R.drawable.tropa_muerta,
        turnoActivo = true,
        turnoDoble =  false,
        cantidad_espinas = 0.00,
        cantidad_escudos = 0.00,
        precision = 100
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
        val random = Random.Default
        val suerte = random.nextDouble()

        return if (suerte < this.probabilidad_de_critico) {
            val x = this.ataque_base * this.daño_critico
            ceil(x).toInt() // redondea hacia arriba
        } else {
            this.ataque_base // golpe normal
        }
    }

    fun Ataque_normal(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        val daño = daño() // calcula el daño según crítico o no
        enemigos[posicion]!!.Recivir_daño(this,daño)

    }

    fun Terremoto(enemigos: ArrayList<Tropa?>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        GlobalData.Atodos = true
        val daño = calcularAtaque(150,this.nivel)
        enemigos[0]!!.Recivir_daño(this,daño)
        enemigos[1]!!.Recivir_daño(this,daño)
        enemigos[2]!!.Recivir_daño(this,daño)
        enemigos[3]!!.Recivir_daño(this,daño)
        enemigos[4]!!.Recivir_daño(this,daño)
        enemigos[5]!!.Recivir_daño(this,daño)
    }

    override fun Habilidad_Especial(Waos: Boolean){

    }

    override fun clonar(): Tropa {
        val copia = Rey_Gigante_Bufon_Negro(this.nivel)
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
        if (this.cantidad_espinas > 0) {
            tropa.vida -= (Ataque * cantidad_espinas).toInt()
        }

        if (this.cantidad_escudos > 0) {
            this.vida -= (Ataque - (Ataque * cantidad_escudos)).toInt()
        }else{
            this.vida -= Ataque
        }
    }

}