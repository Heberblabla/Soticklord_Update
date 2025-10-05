package Data

import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Rey_de_los_Gigantes(Nivel:Int =1) : Tropa(
    nombre = "Rey_de_los_Gigantes",
    nivel = Nivel,
    vida = 1500,
    ataque_base = 50,
    daño_critico = 1.5,
    probabilidad_de_critico = 0.50,
    aereo = false,
    estado_de_vida = true,
    rutaviva = R.drawable.rey_de_los_gigantes,  // tu imagen en drawable
    rutamuerta = R.drawable.tropa_muerta,       // tu imagen en drawable
    turnoActivo = true,
    turnoDoble = false
), Serializable {
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
        val enemigo = enemigos[posicion]
        enemigo.vida -= daño
    }
}
