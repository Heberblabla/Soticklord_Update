package Data.Especiales

import Data.Tropa
import com.waos.soticklord.DataEntornos
import com.waos.soticklord.EntornoManager
import com.waos.soticklord.GlobalData
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Reyna_Apio(Nivel:Int = 1): Tropa(
    nombre = "Reyna_Apio",
    nivel = Nivel,
    vida = calcularVida(850,Nivel),
    ataque_base = calcularAtaque(110,Nivel),
    daño_critico = calcularDañoCritico(1.8,Nivel),
    probabilidad_de_critico = calcularProbCritico(0.3,Nivel),
    aereo = false,
    estado_de_vida = true,
    rutaviva = R.drawable.reyna_apio,   // tu imagen en drawable
    rutamuerta = R.drawable.tropa_muerta,   // imagen al morir
    turnoActivo = true,
    turnoDoble =  false,
    cantidad_espinas = 0.00,
    cantidad_escudos = 0.00,
    precision = 100
), Serializable {

    var entorno = true
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

    fun Ataque_normal(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        val daño = daño()
        enemigos[posicion].Recivir_daño(this,daño)
    }

    fun Nota_Real(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        this.vida -= (this.vida * 0.1).toInt()
        var daño = daño() * 2
        enemigos[posicion].Recivir_daño(this,daño)
    }

    fun Voz_Calmante(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        this.vida += (this.vida * 0.15).toInt()
    }

    fun Aura_Real(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        this.vida += (this.vida * 0.15).toInt()
    }

    fun Golpe_de_Cetro(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        this.vida -= (this.vida * 0.25).toInt()
        var daño = daño() * 4
        enemigos[posicion].Recivir_daño(this,daño)
    }

    fun Resonancia_Apio(enemigos: ArrayList<Tropa?>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        GlobalData.Atodos = true
        val daño = calcularAtaque((this.ataque_base * 0.25).toInt(),this.nivel)
        enemigos[0]!!.Recivir_daño(this,daño)
        enemigos[1]!!.Recivir_daño(this,daño)
        enemigos[2]!!.Recivir_daño(this,daño)
        enemigos[3]!!.Recivir_daño(this,daño)
        enemigos[4]!!.Recivir_daño(this,daño)
        enemigos[5]!!.Recivir_daño(this,daño)
    }

    fun Trono_de_Hielo_Cantante(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        if(this.entorno) {
            if (Waos) {
                EntornoManager.cambiarEntorno(
                    nuevo = DataEntornos.Mar_rojo,
                    invocador = this, // la tropa que lo activó
                    enemigos = GlobalData.Jugador2.filterNotNull(),
                    aliados = GlobalData.Jugador1.filterNotNull()
                )
                var daño = daño()
                enemigos[posicion].Recivir_daño(this, daño)
                this.entorno = false
            }
            if (!Waos) {
                EntornoManager.cambiarEntorno(
                    nuevo = DataEntornos.Mar_rojo,
                    invocador = this, // la tropa que lo activó
                    enemigos = GlobalData.Jugador1.filterNotNull(),
                    aliados = GlobalData.Jugador2.filterNotNull()
                )
                var daño = daño()
                enemigos[posicion].Recivir_daño(this, daño)
                this.entorno = false
            }

            EntornoManager.aplicarEfecto()
        }
    }

    fun Acorde_Congelante(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        val daño = daño()
        enemigos[posicion].Recivir_daño(this,daño)
    }

    fun Mandato_Real(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        val daño = daño()
        enemigos[posicion].Recivir_daño(this,daño)
    }

    override fun Habilidad_Especial(Waos: Boolean){

    }

    override fun clonar(): Tropa {
        val copia = Reyna_Apio(this.nivel)
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
