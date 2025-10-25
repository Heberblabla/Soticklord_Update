package Data.Personalizados

import Data.Tropa
import Data.Tropa_Gigante
import com.waos.soticklord.GlobalData
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Rey_Borrego (
    Nivel:Int = 1
):
    Tropa(
        nombre = "Rey_Borrego",
        nivel = Nivel,
        vida = calcularVida(1500,Nivel),
        ataque_base = calcularAtaque(200,Nivel),
        daño_critico = calcularDañoCritico(2.0,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.50,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.rey_borrego,
        rutamuerta = R.drawable.tropa_muerta,
        turnoActivo = true,
        turnoDoble =  false,
        cantidad_espinas = 0,
        cantidad_escudos = 0
    ), Serializable {

    var Resurreccion : Boolean = true
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
        val daño = daño()
        enemigos[posicion].Recivir_daño(this,daño)
    }
    fun Golpe_del_Reyno(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean){
        this.vida -= (this.vida * 0.5).toInt()
        val daño = ((enemigos[posicion]!!.vida * 0.5) + this.vida).toInt()
        enemigos[posicion].Recivir_daño(this,daño)
    }
    fun Bendicion_del_cetro(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean){
        if(Waos) {
                val daño = (this.ataque_base * 0.5).toInt()
                enemigos[0].Recivir_daño(this,daño)
                enemigos[1].Recivir_daño(this,daño)
                enemigos[2].Recivir_daño(this,daño)
                enemigos[3].Recivir_daño(this,daño)
                enemigos[4].Recivir_daño(this,daño)
                enemigos[5].Recivir_daño(this,daño)

                GlobalData.Jugador1[0]!!.vida += (daño * 0.3).toInt()
                GlobalData.Jugador1[1]!!.vida += (daño * 0.3).toInt()
                GlobalData.Jugador1[2]!!.vida += (daño * 0.3).toInt()
                GlobalData.Jugador1[3]!!.vida += (daño * 0.3).toInt()
                GlobalData.Jugador1[4]!!.vida += (daño * 0.3).toInt()
                GlobalData.Jugador1[5]!!.vida += (daño * 0.3).toInt()
        }
        if(!Waos) {
            val daño = (this.ataque_base * 0.5).toInt()
            enemigos[0].Recivir_daño(this,daño)
            enemigos[1].Recivir_daño(this,daño)
            enemigos[2].Recivir_daño(this,daño)
            enemigos[3].Recivir_daño(this,daño)
            enemigos[4].Recivir_daño(this,daño)
            enemigos[5].Recivir_daño(this,daño)

            GlobalData.Jugador1[0]!!.vida += (daño * 0.3).toInt()
            GlobalData.Jugador2[1]!!.vida += (daño * 0.3).toInt()
            GlobalData.Jugador2[2]!!.vida += (daño * 0.3).toInt()
            GlobalData.Jugador2[3]!!.vida += (daño * 0.3).toInt()
            GlobalData.Jugador2[4]!!.vida += (daño * 0.3).toInt()
            GlobalData.Jugador2[5]!!.vida += (daño * 0.3).toInt()
        }
    }

    fun Juicio_Celestial(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean){
        if(Waos) {
            if(Resurreccion) {
                for (i in 1..5) { // posiciones 1 al 5
                    val tropa = GlobalData.Jugador1[i]
                    if (tropa != null && tropa.vida <= 0) {
                        try {
                            // obtiene la clase desde el diccionario
                            val clase = GlobalData.Diccionario_Clases[tropa.nombre]
                            // crea nueva instancia pasando el nivel actual
                            val nuevaTropa =
                                clase?.constructors?.firstOrNull()?.call(tropa.nivel) as? Tropa
                            // reemplaza la tropa vieja por la nueva
                            if (nuevaTropa != null) {
                                GlobalData.Jugador1[i] = nuevaTropa
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                this.Resurreccion = false
            }
        }
        if(!Waos) {
            if(Resurreccion) {
                for (i in 1..5) { // posiciones 1 al 5
                    val tropa = GlobalData.Jugador2[i]
                    if (tropa != null && tropa.vida <= 0) {
                        try {
                            // obtiene la clase desde el diccionario
                            val clase = GlobalData.Diccionario_Clases[tropa.nombre]
                            // crea nueva instancia pasando el nivel actual
                            val nuevaTropa =
                                clase?.constructors?.firstOrNull()?.call(tropa.nivel) as? Tropa
                            // reemplaza la tropa vieja por la nueva
                            if (nuevaTropa != null) {
                                GlobalData.Jugador2[i] = nuevaTropa
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                this.Resurreccion = false
            }
        }

    }





    override fun clonar(): Tropa {
        val copia = Rey_Borrego(this.nivel)
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
        if(this.cantidad_escudos > 0){
            this.vida -= (Ataque * (Ataque * cantidad_escudos)).toInt()
        }
        if(this.cantidad_espinas > 0){
            tropa.vida -= (Ataque * cantidad_espinas).toInt()
            return
        }

        this.vida -= Ataque
        return
    }

}