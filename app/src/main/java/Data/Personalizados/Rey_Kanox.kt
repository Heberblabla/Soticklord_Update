package Data.Personalizados

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

class Rey_Kanox (
    Nivel:Int = 1
):
    Tropa(
        nombre = "Rey_Kanox",
        nivel = Nivel,
        vida = calcularVida(1600,Nivel),
        ataque_base = calcularAtaque(300,Nivel),
        daño_critico = calcularDañoCritico(2.5,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.45,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.rey_kronox,
        rutamuerta = R.drawable.tropa_muerta,
        turnoActivo = true,
        turnoDoble =  false,
        cantidad_espinas = 0.00,
        cantidad_escudos = 0.00,
        precision = 100
    ), Serializable {
        var esquivacion = 0.0
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


    fun Aguja_Escarlata(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        if(enemigos[posicion].vida <= 500){
            var daño = enemigos[posicion].vida
            enemigos[posicion].Recivir_daño(this,daño)
        }else{
            efectuardaño(enemigos,posicion,Waos)
        }

    }

    fun Rompimiento_de_Fe(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        enemigos[posicion].cantidad_escudos -= 0.30
    }

    fun Ultra_Instinto(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        this.esquivacion += 0.50

    }

    fun CORTE_DE_ESCALIBUR(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        for (i in 1 until enemigos.size) {
            var daño = this.ataque_base
            enemigos[i]!!.Recivir_daño(this,daño)
        }
    }

    fun Hakai(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        val num = Random.nextInt(100)
        if(num < 75){
            enemigos[posicion].turnoActivo = false
        }
    }

    fun Puño_del_Dragón(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        val num = Random.nextInt(100)

        if(enemigos[posicion].cantidad_escudos < 0) {
            var daño = daño()
            daño = daño * 5

            enemigos[posicion]!!.Recivir_daño(this, daño)
        }
    }

    fun Destrucción_Divina(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        val num = Random.nextInt(100)
        if(Waos) {
            if (num < 25) {
                if (GlobalData.Jugador1[1]!!.estado_de_vida &&
                    GlobalData.Jugador1[2]!!.estado_de_vida &&
                    GlobalData.Jugador1[3]!!.estado_de_vida &&
                    GlobalData.Jugador1[4]!!.estado_de_vida &&
                    GlobalData.Jugador1[5]!!.estado_de_vida
                ) {
                    GlobalData.Jugador1[posicion]!!.vida -= (GlobalData.Jugador2[posicion]!!.vida * 0.7).toInt()
                    efectuardaño(enemigos,posicion,Waos)
                }

            }
        }
        if(!Waos) {
            if (num < 25) {
                if (GlobalData.Jugador2[1]!!.estado_de_vida &&
                    GlobalData.Jugador2[2]!!.estado_de_vida &&
                    GlobalData.Jugador2[3]!!.estado_de_vida &&
                    GlobalData.Jugador2[4]!!.estado_de_vida &&
                    GlobalData.Jugador2[5]!!.estado_de_vida
                ) {
                    GlobalData.Jugador2[posicion]!!.vida -= (GlobalData.Jugador2[posicion]!!.vida * 0.7).toInt()
                    efectuardaño(enemigos,posicion,Waos)
                }

            }
        }
    }

    override fun Habilidad_Especial(Waos: Boolean){
        var num = Random.nextInt(100)
        if(this.vida > 0) {
            if (num < 45) {
                this.ataque_base += (this.ataque_base * 0.10).toInt()
                this.vida += (this.vida * 0.10).toInt()
            }
        }
    }

    fun efectuardaño(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean){
        if(posicion == 0) {
            var daño = daño()
            daño = (daño * 2.5).toInt()
            enemigos[posicion].Recivir_daño(this, daño)
        }else{
            var daño = daño()
            enemigos[posicion].Recivir_daño(this,daño)
        }
    }


    override fun clonar(): Tropa {
        val copia = Rey_Kanox(this.nivel)
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

    override fun Recivir_daño(tropa: Tropa, Ataque: Int) {
        val num = Random.nextDouble()
        if(num < esquivacion) {
            this.esquivacion -= 0.5
            return
        }else{
            if (this.cantidad_escudos > 0) {
                this.vida -= (Ataque - (Ataque * cantidad_escudos)).toInt()
            }
            if (this.cantidad_espinas > 0) {
                tropa.vida -= (Ataque * cantidad_espinas).toInt()
                return
            }
            this.vida -= Ataque
            return
        }
    }
}
