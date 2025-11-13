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

class Rey_Jerald (
    Nivel:Int = 1
):
    Tropa(
        nombre = "Rey_Jerald",
        nivel = Nivel,
        vida = calcularVida(1000,Nivel),
        ataque_base = calcularAtaque(100,Nivel),
        daño_critico = calcularDañoCritico(2.0,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.30,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.rey_jerald,
        rutamuerta = R.drawable.tropa_muerta,
        turnoActivo = true,
        turnoDoble =  false,
        cantidad_espinas = 0.00,
        cantidad_escudos = 0.00,
        precision = 100
    ), Serializable {
        var perro = true
        var invocacion = true
        var fase= true
        var atropello = true

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

    fun Llamada_De_perro(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        if(Waos) {
            if (perro) {
                GlobalData.Jugador1[0]!!.vida = (500 + GlobalData.Jugador1[0]!!.vida) * 2
                GlobalData.Jugador1[1]!!.vida = (500 + GlobalData.Jugador1[1]!!.vida) * 2
                GlobalData.Jugador1[2]!!.vida = (500 + GlobalData.Jugador1[2]!!.vida) * 2
                GlobalData.Jugador1[3]!!.vida = (500 + GlobalData.Jugador1[3]!!.vida) * 2
                GlobalData.Jugador1[4]!!.vida = (500 + GlobalData.Jugador1[4]!!.vida) * 2
                GlobalData.Jugador1[5]!!.vida = (500 + GlobalData.Jugador1[5]!!.vida) * 2
                perro = false
            }
        }
        if(!Waos) {
            if (perro) {
                GlobalData.Jugador2[0]!!.vida = (500 + GlobalData.Jugador2[0]!!.vida) * 2
                GlobalData.Jugador2[1]!!.vida = (500 + GlobalData.Jugador2[1]!!.vida) * 2
                GlobalData.Jugador2[2]!!.vida = (500 + GlobalData.Jugador2[2]!!.vida) * 2
                GlobalData.Jugador2[3]!!.vida = (500 + GlobalData.Jugador2[3]!!.vida) * 2
                GlobalData.Jugador2[4]!!.vida = (500 + GlobalData.Jugador2[4]!!.vida) * 2
                GlobalData.Jugador2[5]!!.vida = (500 + GlobalData.Jugador2[5]!!.vida) * 2
                perro = false
            }
        }


        //una ves por partida
        //invocas un perro  q les dara invencible por 3 turnos
    }

    fun Modo_cautel(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        this.vida += (this.vida * 0.25).toInt()
        this.ataque_base += this.ataque_base

    }

    fun Salto_de_soga(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        if(Waos) {
            GestorEventos.agregar(
                Evento(
                    tipo = "Sangrado",
                    objetivoIndex = posicion,
                    quien = 2, // si el enemigo es Jugador2
                    turnosRestantes = 5,
                    efecto = { evento, batalla ->
                        val lista = if (evento.quien == 1) GlobalData.Jugador1 else GlobalData.Jugador2
                        val tropa = lista[evento.objetivoIndex]
                        if (tropa != null && tropa.estado_de_vida) {
                            tropa.vida -= 100
                        }
                    }
                )
            )
        }
        if(!Waos) {
            GestorEventos.agregar(
                Evento(
                    tipo = "Sangrado",
                    objetivoIndex = posicion,
                    quien = 1, // si el enemigo es Jugador2
                    turnosRestantes = 5,
                    efecto = { evento, batalla ->
                        val lista = if (evento.quien == 1) GlobalData.Jugador1 else GlobalData.Jugador2
                        val tropa = lista[evento.objetivoIndex]
                        if (tropa != null && tropa.estado_de_vida) {
                            tropa.vida -= 100

                        }
                    }
                )
            )
        }

        //Los proximos 5 turnos sufres un daño igual a 100
    }

    fun Ballesta(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        this.rutaviva = R.drawable.gato_ballesta
        GlobalData.Atodos = true
        for(tropa in enemigos){
            tropa.Recivir_daño(this,ataque_base)
        }

    }

    fun invocacion_de_gatos(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        if (Waos) {
            if(this.invocacion) {
                val claseGigante = GlobalData.Diccionario_Clases["Tropa_Gato_amigo1"]
                val claseCurandera = GlobalData.Diccionario_Clases["Tropa_Gato_amigo2"]

                val nivelGigante = this.nivel + 5
                val nivelCurandera = this.nivel + 8

                if (claseGigante != null && claseCurandera != null) {
                    GlobalData.Jugador1[1] =
                        claseCurandera.constructors.first().call(nivelCurandera)
                    GlobalData.Jugador1[2] =
                        claseCurandera.constructors.first().call(nivelCurandera)
                    GlobalData.Jugador1[3] = claseGigante.constructors.first().call(nivelGigante)
                    GlobalData.Jugador1[4] = claseGigante.constructors.first().call(nivelGigante)
                    GlobalData.Jugador1[5] = claseGigante.constructors.first().call(nivelGigante)

                    println("Tropas creadas para Jugador 1")
                } else {
                    println("No se encontró alguna clase en Diccionario_Clases")
                }
                this.invocacion = false
            }

        } else {
            if (this.invocacion) {
                val claseGigante = GlobalData.Diccionario_Clases["Tropa_Gato_amigo1"]
                val claseCurandera = GlobalData.Diccionario_Clases["Tropa_Gato_amigo2"]
                val nivelGigante = this.nivel + 5
                val nivelCurandera = this.nivel + 8
                if (claseGigante != null && claseCurandera != null) {
                    GlobalData.Jugador2[1] =
                        claseCurandera.constructors.first().call(nivelCurandera)
                    GlobalData.Jugador2[2] =
                        claseCurandera.constructors.first().call(nivelCurandera)
                    GlobalData.Jugador2[3] = claseGigante.constructors.first().call(nivelGigante)
                    GlobalData.Jugador2[4] = claseGigante.constructors.first().call(nivelGigante)
                    GlobalData.Jugador2[5] = claseGigante.constructors.first().call(nivelGigante)
                    println("ropas creadas para Jugador 2")
                } else {
                    println(" No se encontró alguna clase en Diccionario_Clases")
                }
                this.invocacion = false
            }
        }
    }

    fun Sube_de_fase(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }

        if(this.fase) {
            this.rutaviva = R.drawable.fato_fase
            this.ataque_base += (this.ataque_base) + (this.ataque_base * 1.5).toInt()

            GlobalData.Atodos = true
            for (tropa in enemigos) {
                tropa.vida -= (tropa.vida * 0.3).toInt()
            }
            this.fase = false
        }
        //UNA   VES POR PARTIDA
        //tu vida y tu ataque sube en un 200%
        //todos los enemigos reciven un daño igual al 50% de su vida restante
    }

    fun Atropello(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        GlobalData.Atodos = true
        if(this.atropello) {
            if (Waos) {
                GlobalData.Jugador2[1]!!.Recivir_daño(this, ataque_base)
                GlobalData.Jugador2[2]!!.Recivir_daño(this, ataque_base)
                GlobalData.Jugador2[3]!!.Recivir_daño(this, ataque_base)
                GlobalData.Jugador2[4]!!.Recivir_daño(this, ataque_base)
                GlobalData.Jugador2[5]!!.Recivir_daño(this, ataque_base)

                GlobalData.Jugador2[0]!!.Recivir_daño(this, ataque_base)
                GlobalData.Jugador2[0]!!.cantidad_escudos -= 70
                GlobalData.Jugador2[0]!!.turnoActivo = false
                var waos = (enemigos[0].vida * 0.25).toInt()
                GlobalData.Jugador2[0]!!.vida -= (enemigos[0].vida * 0.25).toInt()
                GlobalData.Jugador2[0]!!.ataque_base -= (enemigos[0].ataque_base * 0.25).toInt()
                this.atropello = false
            }
            if (!Waos) {
                GlobalData.Jugador1[1]!!.Recivir_daño(this, ataque_base)
                GlobalData.Jugador1[2]!!.Recivir_daño(this, ataque_base)
                GlobalData.Jugador1[3]!!.Recivir_daño(this, ataque_base)
                GlobalData.Jugador1[4]!!.Recivir_daño(this, ataque_base)
                GlobalData.Jugador1[5]!!.Recivir_daño(this, ataque_base)

                GlobalData.Jugador1[0]!!.Recivir_daño(this, ataque_base)
                GlobalData.Jugador1[0]!!.cantidad_escudos -= 70
                GlobalData.Jugador1[0]!!.turnoActivo = false
                GlobalData.Jugador1[0]!!.vida = (enemigos[0].vida * 0.65).toInt()
                GlobalData.Jugador1[0]!!.ataque_base -= (enemigos[0].ataque_base * 0.25).toInt()
                this.atropello = false
            }


        }


    }

    override fun Habilidad_Especial(Waos: Boolean){

    }

    override fun clonar(): Tropa {
        val copia = Rey_Jerald(this.nivel)
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
        if (this.cantidad_espinas > 0) {
            tropa.vida -= (Ataque * cantidad_espinas).toInt()
        }

        if (this.cantidad_escudos > 0) {
            this.vida -= (Ataque - (Ataque * cantidad_escudos)).toInt()
        }else{
            this.vida -= Ataque
        }
    }

}