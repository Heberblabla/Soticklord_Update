package Data

import com.waos.soticklord.R
import kotlin.math.ceil
import kotlin.random.Random

class Tropa_Lanzatonio : Tropa(
    nombre = "Lanzatonio",
    nivel = 1,
    vida = 550,
    ataque_base = 100,
    daño_critico = 1.5,
    probabilidad_de_critico = 0.40,
    aereo = false,
    estado_de_vida = true,
    rutaviva = R.drawable.lanzatonio_tropa,
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

    fun estocada(enemigos: ArrayList<Tropa>, posicion: Int) {
        val suerte = Random.nextDouble()
        val daño = if (suerte < 0.3) {
            ceil(ataque_base * 4.0).toInt()
        } else {
            ataque_base
        }
        enemigos[posicion].vida -= daño
    }

    fun bloqueo() {
        this.vida += 100
    }
}
