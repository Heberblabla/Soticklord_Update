package Data.Personalizados

import Archivos_Extra.Evento
import Archivos_Extra.GestorEventos
import Data.Tropa
import Data.Tropa.Companion.calcularAtaque
import Data.Tropa.Companion.calcularDañoCritico
import Data.Tropa.Companion.calcularProbCritico
import Data.Tropa.Companion.calcularVida
import com.waos.soticklord.GlobalData
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Rey_Kratos (
    Nivel:Int = 1
):
    Tropa(
        nombre = "Rey_Kratos",
        nivel = Nivel,
        vida = calcularVida(1300,Nivel),
        ataque_base = calcularAtaque(85,Nivel),
        daño_critico = calcularDañoCritico(1.5,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.25,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.rey_kratos,
        rutamuerta = R.drawable.tropa_muerta,
        turnoActivo = true,
        turnoDoble =  false,
        cantidad_espinas = 0.00,
        cantidad_escudos = 0.00,
        precision = 100
    ), Serializable {
    var Revivir = true
    var juicio = true
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
        var random = Random.Default
        var suerte = random.nextDouble()


        return if (suerte < this.probabilidad_de_critico) {
            val x = this.ataque_base * this.daño_critico
            ceil(x).toInt() // redondea hacia arriba
        } else {
            this.ataque_base // golpe normal
        }
    }

    fun Golpe_Helado_del_Leviatán(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        val daño = (daño() * 1.3).toInt()
        enemigos[posicion].Recivir_daño(this,daño)

        var num = Random.nextInt(100)
        if(num < 10){
            enemigos[posicion].turnoActivo = false
            this.precision += 20
            this.ataque_base += 100
        }
    }

    fun Torbellino_de_Njord(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        GlobalData.Atodos = true
        for(tropa in enemigos){
            var daño = (daño() * 1.2).toInt()
            tropa.Recivir_daño(this,daño)
            tropa.cantidad_escudos -= 15
        }

        this.cantidad_escudos + 0.1
    }

    fun Filo_del_Caos(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        if(Waos){
            GestorEventos.agregar(
                Evento(
                    tipo = "Quemadura",
                    objetivoIndex = posicion,
                    quien = 2, // si el enemigo es Jugador2
                    turnosRestantes = 2,
                    efecto = { evento, batalla ->
                        val lista = if (evento.quien == 1) GlobalData.Jugador1 else GlobalData.Jugador2
                        val tropa = lista[evento.objetivoIndex]
                        if (tropa != null && tropa.estado_de_vida || evento.turnosRestantes > 0) {
                            tropa!!.vida -= (tropa!!.vida * 0.15).toInt()

                        }
                    }
                )
            )
        }
        if(!Waos){
            GestorEventos.agregar(
                Evento(
                    tipo = "Quemadura",
                    objetivoIndex = posicion,
                    quien = 1, // si el enemigo es Jugador2
                    turnosRestantes = 2,
                    efecto = { evento, batalla ->
                        val lista = if (evento.quien == 1) GlobalData.Jugador1 else GlobalData.Jugador2
                        val tropa = lista[evento.objetivoIndex]
                        if (tropa != null && tropa.estado_de_vida || evento.turnosRestantes > 0) {
                            tropa!!.vida -= (tropa!!.vida * 0.15).toInt()

                        }
                    }
                )
            )
        }
    }

    fun Aplastamiento_de_Nemea(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        if(this.vida > 800) {
            GlobalData.Atodos = true
            if (Waos) {
                for (tropa in enemigos) {
                    var daño = (daño() * 0.5).toInt()
                    tropa.Recivir_daño(this, daño)
                    tropa!!.cantidad_escudos -= 8
                }
                GestorEventos.agregar(
                    Evento(
                        tipo = "Quemadura_Pesada",
                        objetivoIndex = posicion,
                        quien = 2, // si el enemigo es Jugador2
                        turnosRestantes = 2,
                        efecto = { evento, batalla ->
                            val lista =
                                if (evento.quien == 1) GlobalData.Jugador1 else GlobalData.Jugador2
                            val tropa = lista[evento.objetivoIndex]
                            if (tropa != null && tropa.estado_de_vida || evento.turnosRestantes > 0) {
                                tropa!!.vida -= (tropa!!.vida * 0.25).toInt()


                            }
                        }
                    )
                )
            }
            if (!Waos) {
                for (tropa in enemigos) {
                    var daño = (daño() * 0.5).toInt()
                    tropa.Recivir_daño(this, daño)
                    tropa!!.cantidad_escudos -= 8
                }
                GestorEventos.agregar(
                    Evento(
                        tipo = "Quemadura_Pesada",
                        objetivoIndex = posicion,
                        quien = 1, // si el enemigo es Jugador2
                        turnosRestantes = 2,
                        efecto = { evento, batalla ->
                            val lista =
                                if (evento.quien == 1) GlobalData.Jugador1 else GlobalData.Jugador2
                            val tropa = lista[evento.objetivoIndex]
                            if (tropa != null && tropa.estado_de_vida || evento.turnosRestantes > 0) {
                                tropa!!.vida -= (tropa!!.vida * 0.25).toInt()

                            }
                        }
                    )
                )
            }
            this.vida -= 100
        }
    }

    fun Carga_de_raupnir(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if (xd < this.precision) {
            //sigue realizando tu atque
        } else {
            return
        }

        var daño = daño()
        daño = daño * 2
        enemigos[posicion].Recivir_daño(this, daño)

        var num = Random.nextInt(100)
        if (num < 18) {
            enemigos[posicion].turnoActivo = false
            this.precision += 20
        }

    }

    fun Artillería_de_los_Antiguos(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        GlobalData.Atodos = true
        for(tropa in enemigos){
            tropa!!.cantidad_escudos -= 30
            var daño = (daño() * 1.8).toInt()
            tropa.Recivir_daño(this,daño)
        }

    }

    fun Juicio_de_los_Dioses(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        if(this.juicio) {
            GlobalData.Atodos = true
            for (tropa in enemigos) {
                var daño = (daño() * 3).toInt()
                tropa.Recivir_daño(this, daño)

                var num = Random.nextInt(100)
                if (num < 25) {
                    enemigos[posicion].turnoActivo = false
                    this.vida += (this.vida * 0.25).toInt()
                }
            }
            this.juicio = false
        }

    }

    override fun Habilidad_Especial(Waos: Boolean){
        if(Waos){
            if(this.vida < 1 && this.Revivir) {
                this.vida = 250
                this.cantidad_escudos += 0.3
                this.ataque_base += 100
                this.Revivir = false
                for (i in 1..5) {
                    GlobalData.Jugador1[i]!!.vida += 100
                }

            }
        }
        if(!Waos){
            if(this.vida < 1 && this.Revivir) {
                this.vida = 250
                this.cantidad_escudos += 0.3
                this.ataque_base += 100
                this.Revivir = false
                for (i in 1..5) {
                    GlobalData.Jugador2[i]!!.vida += 100
                }
            }
        }

    }

    override fun clonar(): Tropa {
        val copia = Rey_Kratos(this.nivel)
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
            this.cantidad_escudos -= 0.5
        }
        if(this.cantidad_espinas > 0){
            tropa.vida -= (Ataque * cantidad_espinas).toInt()
            this.cantidad_espinas -= 0.5
            return
        }

        this.vida -= Ataque
        return
    }

}