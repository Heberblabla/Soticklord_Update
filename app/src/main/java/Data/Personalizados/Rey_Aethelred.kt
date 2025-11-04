package Data.Personalizados

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

class Rey_Aethelred (
    Nivel:Int = 1
):
    Tropa(
        nombre = "Rey_Aethelred",
        nivel = Nivel,
        vida = calcularVida(1000,Nivel),
        ataque_base = calcularAtaque(150,Nivel),
        daño_critico = calcularDañoCritico(2.5,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.35,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.rey_aethelred,
        rutamuerta = R.drawable.tropa_muerta,
        turnoActivo = true,
        turnoDoble =  true,
        cantidad_espinas = 0.00,
        cantidad_escudos = 0.00,
        precision = 100
    ), Serializable {

        var muralla = true
        var Fragmento = 0

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


    fun Muralla_de_Cuarzo(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if (xd < this.precision) {
            //sigue realizando tu atque
        } else {
            return
        }
        if(this.muralla) {
            if (Waos) {
                for (tropa in GlobalData.Jugador1) {
                    tropa!!.cantidad_escudos += 0.35
                }
                this.muralla = false
            }
            if (!Waos) {
                for (tropa in GlobalData.Jugador2) {
                    tropa!!.cantidad_escudos += 0.35
                }
                this.muralla = false
            }
        }
    }

    fun Geoda_Arcana(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        val xd = Random.nextInt(100)
        if (xd >= this.precision) return // falla el ataque

        // Función local para regenerar aliados
        fun regeneracion(amigos: ArrayList<Tropa?>) {
            for (tropa in amigos) {
                tropa!!.vida += 250 // ejemplo de regeneración
            }
        }

        // Función local para hacer daño en cadena a enemigos
        fun dañocadena(enemigos: ArrayList<Tropa>) {
            GlobalData.Atodos = true
            for (tropa in enemigos) {
                tropa.Recivir_daño(this,ataque_base) // ejemplo de daño
            }
        }

        // Función local para turno doble
        fun turnodoble() {
            this.turnoDoble = true
            enemigos[posicion].Recivir_daño(this,ataque_base)
            this.Fragmento += 1
        }

        val numero = Random.nextInt(1, 4)
        when (numero) {
            1 -> if (Waos) regeneracion(GlobalData.Jugador1) else regeneracion(GlobalData.Jugador2)
            2 -> if (Waos) dañocadena(enemigos) else dañocadena(enemigos)
            3 -> turnodoble()
        }


    }

    fun Ataque_Refracción_Temporal(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if (xd < this.precision) {
            //sigue realizando tu atque
        } else {
            return
        }

        enemigos[posicion].turnoActivo = false

        if(this.Fragmento > 0){
            var daño = daño()
            enemigos[posicion].Recivir_daño(this,daño)
            this.vida += daño
            this.Fragmento -=1
        }

    }

    fun Lanza_de_Cristal_Puro(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if (xd < this.precision) {
            //sigue realizando tu atque
        } else {
            return
        }

        if(Waos){
            GlobalData.Jugador2[posicion]!!.vida -= (ataque_base * 2.5).toInt()
        }
        if(!Waos){
            GlobalData.Jugador1[posicion]!!.vida -= (ataque_base * 2.5).toInt()
        }


    }

    fun Sincronización_Forzada(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if (xd < this.precision) {
            //sigue realizando tu atque
        } else {
            return
        }

        enemigos[posicion].vida -= (ataque_base * 2.5).toInt()
        enemigos[posicion].turnoActivo = false
        this.turnoDoble = true

    }

    fun Campo_de_Estasis(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if (xd < this.precision) {
            //sigue realizando tu atque
        } else {
            return
        }

        if(Waos) {
            EntornoManager.cambiarEntorno(
                nuevo = DataEntornos.Campo_de_estasis,
                invocador = this, // la tropa que lo activó
                enemigos = GlobalData.Jugador2.filterNotNull(),
                aliados = GlobalData.Jugador1.filterNotNull()
            )

        }
        if(!Waos) {
            EntornoManager.cambiarEntorno(
                nuevo = DataEntornos.Campo_de_estasis,
                invocador = this, // la tropa que lo activó
                enemigos = GlobalData.Jugador1.filterNotNull(),
                aliados = GlobalData.Jugador2.filterNotNull()
            )

        }

        EntornoManager.aplicarEfecto()

    }

    fun Égida_del_Eón(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if (xd < this.precision) {
            //sigue realizando tu atque
        } else {
            return
        }
        var almacen = this.vida
        GlobalData.Atodos = true
        if(Waos) {
            for (tropa in GlobalData.Jugador1) {
                almacen += tropa!!.vida
                tropa.vida -= tropa.vida
            }
            this.vida = almacen
            this.cantidad_escudos += 0.25
        }
        if(!Waos) {
            for (tropa in GlobalData.Jugador2) {
                almacen += tropa!!.vida
                tropa.vida -= tropa.vida
            }
            this.vida = almacen
            this.cantidad_escudos += 0.25
        }

    }

    override fun Habilidad_Especial(Waos: Boolean) {
        this.turnoActivo = true
        this.vida += 35
    }

    override fun clonar(): Tropa {
        val copia = Rey_Aethelred(this.nivel)
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

    override fun Recivir_daño(tropa: Tropa, Ataque: Int) {
        if (this.cantidad_escudos > 0) {
            this.vida -= (Ataque - (Ataque * cantidad_escudos)).toInt()
        }
        if (this.cantidad_espinas > 0) {
            tropa.vida -= (Ataque * cantidad_espinas).toInt()
            return
        }

        this.vida -= Ataque
        return
    }

}