package Data.Personalizados

import Data.Tropa
import Data.Tropa.Companion.calcularAtaque
import Data.Tropa.Companion.calcularDañoCritico
import Data.Tropa.Companion.calcularProbCritico
import Data.Tropa.Companion.calcularVida
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Rey_Han_Kong (
    Nivel:Int = 1
):
    Tropa(
        nombre = "Rey_Han_Kong",
        nivel = Nivel,
        vida = calcularVida(1300,Nivel),
        ataque_base = calcularAtaque(125,Nivel),
        daño_critico = calcularDañoCritico(1.3,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.45,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.rey_mono,
        rutamuerta = R.drawable.tropa_muerta,
        turnoActivo = true,
        turnoDoble =  false,
        cantidad_espinas = 0.00,
        cantidad_escudos = 0.00,
        precision = 100
    ), Serializable {

        var clon = 100
        var recivir = false
        var supraise = true

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

    fun Bastón_del_Cielo_Expandido(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        for(tropa in enemigos){
            var daño = daño()
            tropa.Recivir_daño(this,daño)
            var random = Random.nextInt(100)
            if(random < 10){
                tropa.turnoActivo = false
            }
        }


    }

    fun Nube_Voladora(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        val daño = daño() * 2
        enemigos[posicion].vida -= daño
    }

    fun Grito_del_Dragón_Dorado(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        val daño = daño()
        enemigos[posicion].vida -= daño
        enemigos[posicion].ataque_base = (enemigos[posicion].ataque_base * 0.8).toInt()
        enemigos[posicion].cantidad_escudos -= 0.2

    }

    fun Clones_de_Sombrasque(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        this.clon -= 55

    }

    fun Golpe_del_Sol_Primordial(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        this.ataque_base = (this.ataque_base * 1.3).toInt()
        val daño = daño()
        enemigos[posicion].Recivir_daño(this,daño)
        this.vida += (daño * 0.6).toInt()

    }

    fun Risa_del_Caos_Eterno(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        this.vida = (this.vida * 1.3).toInt()
        val daño = daño()
        enemigos[posicion].Recivir_daño(this,daño)
        this.recivir = true


    }


    fun Ascensión_del_Rey_Inmortal(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        if(this.supraise) {
            val daño = (daño() * 1.5).toInt()
            enemigos[posicion].Recivir_daño(this, daño)
            if(enemigos[posicion].vida < 0){
                this.ataque_base = (this.ataque_base * 0.5).toInt()
                this.supraise = true
                return
            }else{
                this.supraise = false
            }

        }

    }



    override fun Habilidad_Especial(Waos: Boolean){

    }

    override fun clonar(): Tropa {
        val copia = Rey_Han_Kong(this.nivel)
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
        var num = Random.nextInt(100)
        if(num < this.clon){

        }else {
            if (this.clon < 1) {
                this.clon += 20
                if(this.clon > 1){
                    this.clon = 1
                }
                return
            }
        }

        if(this.cantidad_escudos > 0){
            this.vida -= (Ataque - (Ataque * cantidad_escudos)).toInt()
        }
        if(this.cantidad_espinas > 0){
            tropa.vida -= (Ataque * cantidad_espinas).toInt()
            if(this.recivir){
                ataque_base += Ataque
                this.recivir = false
            }
            return
        }

        if(this.recivir){
            ataque_base += Ataque
            this.recivir = false
        }
        this.vida -= Ataque
        return
    }

}