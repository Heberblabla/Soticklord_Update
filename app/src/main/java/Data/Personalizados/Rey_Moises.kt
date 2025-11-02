package Data.Personalizados

import Archivos_Extra.Evento
import Archivos_Extra.GestorEventos
import Data.Tropa
import Data.Tropa.Companion.calcularAtaque
import Data.Tropa.Companion.calcularDañoCritico
import Data.Tropa.Companion.calcularProbCritico
import Data.Tropa.Companion.calcularVida
import com.waos.soticklord.DataEntornos
import com.waos.soticklord.EntornoManager
import com.waos.soticklord.GlobalData
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Rey_Moises (
    Nivel:Int = 1
):
    Tropa(
        nombre = "Rey_Moises",
        nivel = Nivel,
        vida = calcularVida(1200,Nivel),
        ataque_base = calcularAtaque(150,Nivel),
        daño_critico = calcularDañoCritico(2.1,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.45,Nivel),
        aereo = false,
        estado_de_vida = true,
        rutaviva = R.drawable.rey_moises,
        rutamuerta = R.drawable.tropa_muerta,
        turnoActivo = true,
        turnoDoble =  false,
        cantidad_espinas = 0.00,
        cantidad_escudos = 0.00,
        precision = 100
    ), Serializable {

        var contrataque = ataque_base
        var mar_rojo = true
        var mar_desordenados = true
        var caos = true
        var ultimo = true

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

    fun Mar_Rojo_del_Fin(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        if(this.mar_rojo) {
            if (Waos) {
                EntornoManager.cambiarEntorno(
                    nuevo = DataEntornos.Mar_rojo,
                    invocador = this, // la tropa que lo activó
                    enemigos = GlobalData.Jugador2.filterNotNull(),
                    aliados = GlobalData.Jugador1.filterNotNull()
                )
                var daño = daño()
                enemigos[posicion].Recivir_daño(this, daño)
                this.mar_rojo = false
            }
            if (!Waos) {
                EntornoManager.cambiarEntorno(
                    nuevo = DataEntornos.Mar_rojo,
                    invocador = this, // la tropa que lo activó
                    enemigos = GlobalData.Jugador1.filterNotNull(),
                    aliados = GlobalData.Jugador2.filterNotNull()
                )
                var daño = daño()
                enemigos[posicion].Recivir_daño(this, daño)
                this.mar_rojo = false
            }

            EntornoManager.aplicarEfecto()
        }
    }

    fun Mandamiento_del_Silencio(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        enemigos[posicion].turnoActivo = false
        enemigos[posicion].ataque_base = (enemigos[posicion].ataque_base * 0.75).toInt()
        enemigos[posicion].cantidad_escudos -= 0.2

    }


    fun Separación_del_Mar(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        if(this.mar_desordenados) {
            if (Waos) {
                EntornoManager.cambiarEntorno(
                    nuevo = DataEntornos.Mar_desordenado,
                    invocador = this,
                    enemigos = GlobalData.Jugador2.filterNotNull().toMutableList(),
                    aliados = GlobalData.Jugador1.filterNotNull().toMutableList()
                )
                this.mar_desordenados = false
            } else {
                EntornoManager.cambiarEntorno(
                    nuevo = DataEntornos.Mar_desordenado,
                    invocador = this,
                    enemigos = GlobalData.Jugador1.filterNotNull().toMutableList(),
                    aliados = GlobalData.Jugador2.filterNotNull().toMutableList()
                )
                this.mar_desordenados = false
            }
        }



    }


    fun Plaga_de_Oscuridad(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {

        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        if(Waos) {
            GestorEventos.agregar(
                Evento(
                    tipo = "Plaga",
                    objetivoIndex = posicion,
                    quien = 2, // si el enemigo es Jugador2
                    turnosRestantes = 4,
                    efecto = { evento, batalla ->
                        val lista =
                            if (evento.quien == 1) GlobalData.Jugador1 else GlobalData.Jugador2
                        val tropa = lista[evento.objetivoIndex]
                        if (tropa != null && tropa.estado_de_vida) {
                            tropa.vida -= 150

                        }
                    }
                )
            )
        }
        if(!Waos) {
            GestorEventos.agregar(
                Evento(
                    tipo = "Plaga",
                    objetivoIndex = posicion,
                    quien = 1, // si el enemigo es Jugador2
                    turnosRestantes = 4,
                    efecto = { evento, batalla ->
                        val lista =
                            if (evento.quien == 1) GlobalData.Jugador1 else GlobalData.Jugador2
                        val tropa = lista[evento.objetivoIndex]
                        if (tropa != null && tropa.estado_de_vida) {
                            tropa.vida -= 150

                        }
                    }
                )
            )
        }
    }


    fun Mandamiento_del_Caos(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        if(this.caos) {
            for (tropa in enemigos) {
                tropa.vida -= 150
                tropa.precision -= 10
            }
        }
    }


    fun Caminar_sobre_el_Vacío(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        this.aereo = true
        this.vida += 400
        enemigos[posicion].Recivir_daño(this,this.contrataque)
        enemigos[posicion].aereo = false

    }


    fun El_Último_Mandamiento(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        if(this.ultimo) {
            if (Waos) {
                if (posicion == 0) {
                    GlobalData.Jugador2[posicion]!!.vida =
                        (GlobalData.Jugador2[posicion]!!.vida * 0.35).toInt()
                    GlobalData.Jugador2[posicion]!!.turnoActivo = false
                }
                GlobalData.Jugador2[posicion]!!.vida = 0
                this.ultimo = false
            }
            if (!Waos) {
                if (posicion == 0) {
                    GlobalData.Jugador1[posicion]!!.vida =
                        (GlobalData.Jugador1[posicion]!!.vida * 0.35).toInt()
                    GlobalData.Jugador1[posicion]!!.turnoActivo = false
                }
                GlobalData.Jugador1[posicion]!!.vida = 0
                this.ultimo = false
            }
        }
    }



    override fun Habilidad_Especial(Waos: Boolean){

    }

    override fun clonar(): Tropa {
        val copia = Rey_Moises(this.nivel)
        copia.nombre = this.nombre
        copia.vida = this.vida
        copia.ataque_base = this.ataque_base
        copia.daño_critico = this.daño_critico
        copia.probabilidad_de_critico = this.probabilidad_de_critico
        copia.aereo = this.aereo
        copia.estado_de_vida = this.estado_de_vida
        copia.rutaviva = this.rutaviva
        copia.rutamuerta = this.rutamuerta
        copia.turnoActivo = this.turnoActivo
        copia.turnoDoble = this.turnoDoble
        return copia
    }

    override fun Recivir_daño(tropa: Tropa,Ataque :Int) {
        if(this.cantidad_escudos > 0){
            this.vida -= (Ataque - (Ataque * cantidad_escudos)).toInt()
        }
        if(this.cantidad_espinas > 0){
            tropa.vida -= (Ataque * cantidad_espinas).toInt()
            this.contrataque = Ataque
            return
        }
        this.contrataque = Ataque
        this.vida -= Ataque
        return
    }

}