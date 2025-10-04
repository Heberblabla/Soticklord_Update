package Data

import com.waos.soticklord.R
import kotlin.math.ceil
import kotlin.random.Random

class Rey_Arquero : Tropa(
    nombre = "Rey_Arquero",
    vida = 700,
    ataque_base = 90,
    daño_critico = 1.9,
    probabilidad_de_critico = 0.40,
    aereo = true,
    estado_de_vida = true,
    rutaviva = R.drawable.rey_arquero,
    rutamuerta = R.drawable.tropa_muerta,
    turnoActivo = true,
    turnoDoble = false
) {

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
}
