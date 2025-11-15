package Data.Tropas_personalizadas

import Data.Tropa
import com.waos.soticklord.GlobalData
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Tropa_Curandera  (Nivel:Int = 1) : Tropa(
    nombre = "Tropa_Curandera",
    nivel = Nivel,
    vida = calcularVida(150,Nivel),
    ataque_base = calcularAtaque(50,Nivel),
    daño_critico = calcularDañoCritico(2.5,Nivel),
    probabilidad_de_critico = calcularProbCritico(0.50,Nivel),
    aereo = false,
    estado_de_vida = true,
    rutaviva = R.drawable.curandera,
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
    fun Ataque_normal_curativo(enemigos: ArrayList<Tropa?>, posicion: Int,Waos: Boolean) {

        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        val daño: Int = Daño()
        enemigos[posicion]!!.Recivir_daño(this,daño)
        if(Waos){
            GlobalData.Jugador1[0]!!.vida += 130
            GlobalData.Jugador1[1]!!.vida += 130
            GlobalData.Jugador1[2]!!.vida += 130
            GlobalData.Jugador1[3]!!.vida += 130
            GlobalData.Jugador1[4]!!.vida += 130
            GlobalData.Jugador1[5]!!.vida += 130
        }
        if(!Waos){
            GlobalData.Jugador2[0]!!.vida += 130
            GlobalData.Jugador2[1]!!.vida += 130
            GlobalData.Jugador2[2]!!.vida += 130
            GlobalData.Jugador2[3]!!.vida += 130
            GlobalData.Jugador2[4]!!.vida += 130
            GlobalData.Jugador2[5]!!.vida += 130
        }
    }

    override fun Habilidad_Especial(Waos: Boolean){

    }

    override fun clonar(): Tropa {
        val copia = Tropa_Curandera(this.nivel)
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
