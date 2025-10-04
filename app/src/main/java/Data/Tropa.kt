package Data

import com.waos.soticklord.R

abstract class Tropa(
    var nombre: String,
    var vida: Int,
    var ataque_base: Int,
    var daño_critico: Double,
    var probabilidad_de_critico: Double,
    var aereo: Boolean,
    var estado_de_vida: Boolean,
    var rutaviva: Int = R.drawable.tropa_default,
    var rutamuerta: Int = R.drawable.tropa_default,
    var turnoActivo: Boolean = true,
    var turnoDoble: Boolean = false
) {
    fun imprimir() {
        println("nombre : $nombre")
        println("vida: $vida")
        println("ataque base: $ataque_base")
        println("daño critico: $daño_critico")
        println("probabilidad de critico: $probabilidad_de_critico")
        println("es aereo?: $aereo")
        println("su estado de vida es: $estado_de_vida")
        println("ruta de imagen viva: $rutaviva")
        println("ruta de imagen muerta: $rutamuerta")
    }
}
