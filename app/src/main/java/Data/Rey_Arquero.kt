package Data

import android.provider.Settings
import com.waos.soticklord.GlobalData
import com.waos.soticklord.R
import kotlinx.coroutines.awaitCancellation
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Rey_Arquero (
    Nivel:Int = 1
):
    Tropa(
        nombre = "Rey_Arquero",
        nivel = Nivel,
        vida = calcularVida(700,Nivel),
        ataque_base = calcularAtaque(90,Nivel),
        daño_critico = calcularDañoCritico(1.9,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.40,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.rey_arquero,
        rutamuerta = R.drawable.tropa_muerta,
        turnoActivo = true,
        turnoDoble =  false,
        cantidad_escudos = 0,
        cantidad_espinas =  0
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
        val daño = daño()
        enemigos[posicion].Recivir_daño(this,daño)
    }

    fun Disparo_Real(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        val random = Random.Default
        val suerte = random.nextDouble()
        val probabilidad = this.probabilidad_de_critico / 2

        val daño = if (suerte < probabilidad) {
            (this.ataque_base * 5).toInt()
        } else {
            this.ataque_base
        }
        enemigos[posicion].Recivir_daño(this,daño)
    }

    fun Flecha_Explosiva(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        val num = Random.nextInt(100)

        if (num < 21) { // 20% de probabilidad
            this.vida -= this.ataque_base
        } else {
            var daño = daño() * 4
            enemigos[posicion].Recivir_daño(this,daño)
        }
    }

    fun Furia_Del_Rey(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        this.vida += (this.vida * 0.2).toInt()
        this.ataque_base += (this.ataque_base * 0.15).toInt()
        this.probabilidad_de_critico += 0.1
        this.daño_critico += 0.15
    }

    override fun clonar(): Tropa {
        val copia = Rey_Arquero(this.nivel)
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
        copia.cantidad_escudos = this.cantidad_escudos
        copia.cantidad_espinas = this.cantidad_espinas

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
