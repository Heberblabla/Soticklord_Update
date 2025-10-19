package Data

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
        turnoDoble =  false
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

    fun ataqueNormal(enemigos: ArrayList<Tropa>, posicion: Int) {
        val daño = daño()
        val nuevaVida = enemigos[posicion].vida - daño
        enemigos[posicion].vida = nuevaVida
    }

    fun disparoReal(enemigos: ArrayList<Tropa>, posicion: Int) {
        val random = Random.Default
        val suerte = random.nextDouble()
        val probabilidad = this.probabilidad_de_critico / 2

        val daño = if (suerte < probabilidad) {
            (this.ataque_base * 5).toInt()
        } else {
            this.ataque_base
        }

        val nuevaVida = (enemigos[posicion].vida - daño)
        enemigos[posicion].vida = nuevaVida
    }

    fun flechaExplosiva(enemigos: ArrayList<Tropa>, posicion: Int) {
        val num = Random.nextInt(100)

        if (num < 15) { // 15% de probabilidad
            this.vida -= 200
        } else {
            var daño = daño() * 4
            val nuevaVida = enemigos[posicion].vida - daño
            enemigos[posicion].vida = nuevaVida
        }
    }

    fun furiaDelRey(enemigos: ArrayList<Tropa>, posicion: Int) {
        this.vida += 50
        this.ataque_base += 50
        this.probabilidad_de_critico += 0.1
        this.daño_critico += 0.1
    }


}
