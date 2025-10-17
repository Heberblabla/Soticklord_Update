package Data

import com.waos.soticklord.R
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

    private fun calcularDaño(): Int {
        val suerte = Random.nextDouble()
        return if (suerte < probabilidad_de_critico) {
            ceil(ataque_base * daño_critico).toInt()
        } else {
            ataque_base
        }
    }

    fun ataqueNormal(enemigos: ArrayList<Tropa>, posicion: Int) {
        val daño = calcularDaño()
        enemigos[posicion].recibirDaño(daño,this) //cuando se usa this, es comodecir , yo te estoy atacnado, sta clase
        enemigos[posicion].vida -= daño
    }

    fun disparoReal(enemigos: ArrayList<Tropa>, posicion: Int) {
        val suerte = Random.nextDouble()
        val probabilidad = probabilidad_de_critico / 2
        val daño = if (suerte < probabilidad) {
            ceil(ataque_base * 5.0).toInt()
        } else {
            ataque_base
        }
        enemigos[posicion].vida -= daño
    }

    fun flechaExplosiva(enemigos: ArrayList<Tropa>, posicion: Int) {
        val num = Random.nextInt(100)
        if (num < 15) {
            this.vida -= 200
        } else {
            val daño = calcularDaño() * 4
            enemigos[posicion].vida -= daño
        }
    }
    fun furiaDelRey() {
        this.vida += 50
        this.ataque_base += 50
        this.probabilidad_de_critico += 0.1
        this.daño_critico += 0.1
    }
    fun activar_Escudo(){

    }




}
