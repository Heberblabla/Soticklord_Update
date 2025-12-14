package Data.Especiales

import Data.Rey_Arquero
import Data.Rey_Espadachin
import Data.Tropa
import Data.Tropa.Companion.calcularAtaque
import Data.Tropa.Companion.calcularDañoCritico
import Data.Tropa.Companion.calcularProbCritico
import Data.Tropa.Companion.calcularVida
import Data.Tropas_personalizadas.Tropa_Lanzatonio_Medieval
import android.opengl.GLSurfaceView
import com.waos.soticklord.GlobalData
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.random.Random

class Rey_Vago_de_Vagos  (
    Nivel:Int = 1
):
    Tropa(
        nombre = "Rey_Vago_de_Vagos",
        nivel = Nivel,
        vida = calcularVida(950,Nivel),
        ataque_base = calcularAtaque(50,Nivel),
        daño_critico = calcularDañoCritico(10.0,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.90,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.rey_vago_de_vaos,
        rutamuerta = R.drawable.tropa_muerta,
        turnoActivo = true,
        turnoDoble =  false,
        cantidad_espinas = 0.00,
        cantidad_escudos = 1.00,
        precision = 100
    ), Serializable {

        var miturno = false
        var cantidad = 0
        var invocacion = true
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
        this.cantidad +=1
        this.miturno = true
        Habilidad_Especial(Waos)

        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        val daño = daño()
        enemigos[posicion].Recivir_daño(this,daño)
    }

    fun Invocacion_de_guardia(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean){
        this.cantidad +=1
        this.miturno = true
        Habilidad_Especial(Waos)

        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        this.cantidad_escudos += 0.05
        this.miturno = true

        var tropa = mutableListOf<Int>(1)
        if(Waos){
            for(i in 1..5 ){
                if(!GlobalData.Jugador1[i]!!.estado_de_vida){
                    tropa.add(i)
                }
            }
            var waos = tropa.random()
            GlobalData.Jugador1[waos] = Tropa_Lanzatonio_Medieval(this.nivel + 2,0.6)
            this.miturno = false
        }else{
            for(i in 1..5 ){
                if(! GlobalData.Jugador2[i]!!.estado_de_vida){
                    tropa.add(i)
                }
            }
            var waos = tropa.random()
            GlobalData.Jugador2[waos] = Tropa_Lanzatonio_Medieval(this.nivel + 2,0.6)
            this.miturno = false
        }
        Habilidad_Especial(Waos)
    }

    fun Carga_real(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        this.cantidad +=1
        this.miturno = true
        Habilidad_Especial(Waos)

        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        this.cantidad_escudos += 0.15
        if(this.cantidad_escudos > 1.5){
            if(Waos){
                GlobalData.Jugador1[1] = Tropa_Lanzatonio_Medieval(this.nivel + 2,0.5)
                GlobalData.Jugador1[2] = Tropa_Lanzatonio_Medieval(this.nivel + 2,0.5)
                GlobalData.Jugador1[3] = Tropa_Lanzatonio_Medieval(this.nivel + 2,0.5)
                GlobalData.Jugador1[4] = Tropa_Lanzatonio_Medieval(this.nivel + 2,0.5)
                GlobalData.Jugador1[5] = Tropa_Lanzatonio_Medieval(this.nivel + 2,0.5)
            }else{
                GlobalData.Jugador2[1] = Tropa_Lanzatonio_Medieval(this.nivel + 2,0.5)
                GlobalData.Jugador2[2] = Tropa_Lanzatonio_Medieval(this.nivel + 2,0.5)
                GlobalData.Jugador2[3] = Tropa_Lanzatonio_Medieval(this.nivel + 2,0.5)
                GlobalData.Jugador2[4] = Tropa_Lanzatonio_Medieval(this.nivel + 2,0.5)
                GlobalData.Jugador2[5] = Tropa_Lanzatonio_Medieval(this.nivel + 2,0.5)
            }

        }
        this.cantidad_escudos = 0.5

    }

    fun Mandato_del_Castillo(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        this.cantidad +=1
        this.miturno = true
        Habilidad_Especial(Waos)

        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        if(Waos){
            for(i in 1..5){
                GlobalData.Jugador1[i]!!.cantidad_escudos += 0.15
                GlobalData.Jugador1[i]!!.vida += (GlobalData.Jugador1[1]!!.vida *0.1).toInt()
            }
        }else{
            for(i in 1..5){
                GlobalData.Jugador2[i]!!.cantidad_escudos += 0.15
                GlobalData.Jugador2[i]!!.vida += (GlobalData.Jugador1[1]!!.vida *0.1).toInt()
            }
        }


    }

    fun Trompeta_del_Reino(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        this.cantidad +=1
        this.miturno = true
        Habilidad_Especial(Waos)

        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        if(Waos){
            for(i in 0..5){
                GlobalData.Jugador1[i]!!.cantidad_escudos += 0.20
                GlobalData.Jugador1[i]!!.vida += (GlobalData.Jugador1[1]!!.vida *0.1).toInt()
            }
        }else{
            for(i in 0..5){
                GlobalData.Jugador2[i]!!.cantidad_escudos += 0.20
                GlobalData.Jugador2[i]!!.vida += (GlobalData.Jugador1[1]!!.vida *0.1).toInt()
            }
        }


    }

    fun Ultima_Invocacion(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        this.cantidad +=1
        this.miturno = true
        Habilidad_Especial(Waos)

        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        if(this.invocacion && this.cantidad >= 10){
            if(Waos){
                GlobalData.Jugador1[1] = Rey_Arquero(this.nivel + 5 )
                GlobalData.Jugador1[2] = Rey_Arquero(this.nivel + 5 )
                GlobalData.Jugador1[3] = Rey_Espadachin(this.nivel + 5)
                GlobalData.Jugador1[4] = Rey_Espadachin(this.nivel + 5)
                GlobalData.Jugador1[5] = Rey_Espadachin(this.nivel + 5)
                this.invocacion = false
            }else{
                GlobalData.Jugador2[1] = Rey_Arquero(this.nivel + 5)
                GlobalData.Jugador2[2] = Rey_Arquero(this.nivel + 5)
                GlobalData.Jugador2[3] = Rey_Espadachin(this.nivel + 5)
                GlobalData.Jugador2[4] = Rey_Espadachin(this.nivel + 5)
                GlobalData.Jugador2[5] = Rey_Espadachin(this.nivel + 5)
                this.invocacion = false
            }

        }



    }


    override fun Habilidad_Especial(Waos: Boolean){
        if(this.miturno){
            var tropa = mutableListOf<Int>(1)
            if(Waos){
                for(i in 1..5 ){
                    if(!GlobalData.Jugador1[i]!!.estado_de_vida){
                        tropa.add(i)
                    }
                }
                var waos = tropa.random()
                GlobalData.Jugador1[waos] = Tropa_Lanzatonio_Medieval(this.nivel + 2,0.6)
                this.miturno = false
            }else{
                for(i in 1..5 ){
                    if(!GlobalData.Jugador2[i]!!.estado_de_vida){
                        tropa.add(i)
                    }
                }
                var waos = tropa.random()
                GlobalData.Jugador2[waos] = Tropa_Lanzatonio_Medieval(this.nivel + 2,0.6)
                this.miturno = false
            }
        }

    }

    override fun clonar(): Tropa {
        val copia = Rey_Vago_de_Vagos(this.nivel)
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
        return copia
    }

    override fun Recivir_daño(tropa: Tropa,Ataque :Int) {
        abs(Ataque)
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