package Archivos_Extra

import com.waos.soticklord.Batalla

/**
 * Gestor global de eventos en la batalla.
 * Se encarga de ejecutar efectos persistentes como veneno, regeneración, resurrección, etc.
 */
object GestorEventos {

    // Lista de todos los eventos activos
    private val eventosActivos = mutableListOf<Evento>()

    /**
     * Agrega un nuevo evento al sistema
     */
    fun agregar(evento: Evento) {
        eventosActivos.add(evento)
        println(" Evento agregado: ${evento.tipo} (${evento.turnosRestantes} turnos restantes)")
    }


    fun procesarTodos(batalla: Any?) {
        if (eventosActivos.isEmpty()) return

        println("\n Procesando eventos (${eventosActivos.size}) activos...")

        val iterator = eventosActivos.iterator()
        while (iterator.hasNext()) {
            val evento = iterator.next()

            try {
                evento.efecto(evento, batalla)
            } catch (e: Exception) {
                println(" Error en evento ${evento.tipo}: ${e.message}")
            }

            evento.turnosRestantes--
            if (evento.turnosRestantes <= 0) {
                println(" Evento finalizado: ${evento.tipo}")
                iterator.remove()
            }
        }
    }


    /**
     * 🔍 Devuelve una copia de todos los eventos actuales (para depuración)
     */
    fun listarEventos(): List<Evento> = eventosActivos.toList()

    /**
     * 🧹 Limpia todos los eventos (por ejemplo, al terminar la batalla)
     */
    fun limpiar() {
        eventosActivos.clear()
        println("🧽 Todos los eventos han sido limpiados.")
    }
}
