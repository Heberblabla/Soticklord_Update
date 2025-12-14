package Archivos_Extra
import Multijugador.Versus
import com.waos.soticklord.Batalla

data class Evento(
    val tipo: String,
    val objetivoIndex: Int,
    val quien: Int,
    var turnosRestantes: Int,
    val efecto: (Evento, Any?) -> Unit
)
