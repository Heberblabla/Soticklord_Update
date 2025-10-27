package Archivos_Extra

import com.waos.soticklord.*

/**
 * Representa un evento persistente en el juego, como veneno o resurrección.
 */
data class Evento(
    val tipo: String,                           // Tipo de evento ("veneno", "revivir", etc.)
    val objetivoIndex: Int,                     // Índice de la tropa afectada
    val quien: Int,                             // 1 = Jugador1, 2 = Jugador2
    var turnosRestantes: Int,                   // Cuántos turnos quedan
    val efecto: (Evento, Batalla) -> Unit       // Qué hace el evento cada turno
)
