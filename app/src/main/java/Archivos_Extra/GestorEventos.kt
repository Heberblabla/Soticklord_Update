package Archivos_Extra

import com.waos.soticklord.*

/**
 * Gestor de eventos global que maneja efectos persistentes en batalla
 * (por ejemplo, veneno, curaciones por turno, resurrecciones, etc.)
 */
object GestorEventos {

    // Lista que guarda todos los eventos activos en la batalla
    private val eventosActivos = mutableListOf<Evento>()

    /**
     * Agrega un nuevo evento a la lista
     */
    fun agregar(evento: Evento) {
        eventosActivos.add(evento)
        println("🌀 Evento agregado: ${evento.tipo} (${evento.turnosRestantes} turnos restantes)")
    }

    /**
     * Procesa todos los eventos activos.
     * Se debe llamar una vez por turno (al final del turno del jugador y del enemigo).
     */
    fun procesarTodos(batalla: Batalla) {
        val iterator = eventosActivos.iterator()
        while (iterator.hasNext()) {
            val evento = iterator.next()

            // Ejecuta el efecto del evento
            try {
                evento.efecto(evento, batalla)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Reduce el contador de turnos
            evento.turnosRestantes--

            // Si ya terminó, lo quitamos
            if (evento.turnosRestantes <= 0) {
                println(" Evento finalizado: ${evento.tipo}")
                iterator.remove()
            }
        }
    }

    /**
     * Muestra todos los eventos actuales (útil para depuración)
     */
    fun listarEventos(): List<Evento> = eventosActivos.toList()

    /**
     * Limpia todos los eventos activos (por si termina la batalla)
     */
    fun limpiar() {
        eventosActivos.clear()
    }
}
