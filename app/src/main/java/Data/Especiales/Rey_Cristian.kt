package Data.Especiales

import Data.Tropa
import android.provider.Settings
import com.waos.soticklord.GlobalData
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Rey_Cristian (
    Nivel:Int = 1
):
    Tropa(
        nombre = "Rey_Cristian",
        nivel = Nivel,
        vida = calcularVida(1000,Nivel),
        ataque_base = calcularAtaque(150,Nivel),
        daño_critico = calcularDañoCritico(10.0,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.90,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.rey_cristian,
        rutamuerta = R.drawable.tropa_muerta,
        turnoActivo = true,
        turnoDoble =  false,
        cantidad_espinas = 0.00,
        cantidad_escudos = 0.00

    ), Serializable {

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


    fun Back_on_de_bit(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        for (i in enemigos.indices) {
            var daño = (enemigos[i].vida * 0.15).toInt()
            enemigos[posicion]!!.vida = enemigos[posicion]!!.vida - daño
        }

        for (i in enemigos.indices) {
            enemigos[i].ataque_base -= (enemigos[1].ataque_base * 0.15).toInt()
        }

    }

    fun king_Crimson(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {

        if(Waos){
            var i = 0
            while(true){
                if(GlobalData.Diccionario_Tropas[i]!!.nombre == "Tropa_Gigante_estelar"){
                    break
                }
                i ++
            }
            var j = 0
            while(true){
                if(GlobalData.Diccionario_Tropas[j]!!.nombre == "Tropa_Gurandera"){
                    break
                }
                j ++
            }

            GlobalData.Jugador1[1] = GlobalData.Diccionario_Tropas[j]!!.clonar()
            GlobalData.Jugador1[2] = GlobalData.Diccionario_Tropas[j]!!.clonar()
            GlobalData.Jugador1[3] = GlobalData.Diccionario_Tropas[i]!!.clonar()
            GlobalData.Jugador1[4] = GlobalData.Diccionario_Tropas[i]!!.clonar()
            GlobalData.Jugador1[5] = GlobalData.Diccionario_Tropas[i]!!.clonar()
        }
        if(!Waos){
            var i = 0
            while(true){
                if(GlobalData.Diccionario_Tropas[i]!!.nombre == "Tropa_Gigante_estelar"){
                    break
                }
                i ++
            }
            var j = 0
            while(true){
                if(GlobalData.Diccionario_Tropas[j]!!.nombre == "Tropa_Gurandera"){
                    break
                }
                j ++
            }

            GlobalData.Jugador2[1] = GlobalData.Diccionario_Tropas[j]!!.clonar()
            GlobalData.Jugador2[2] = GlobalData.Diccionario_Tropas[j]!!.clonar()
            GlobalData.Jugador2[3] = GlobalData.Diccionario_Tropas[i]!!.clonar()
            GlobalData.Jugador2[4] = GlobalData.Diccionario_Tropas[i]!!.clonar()
            GlobalData.Jugador2[5] = GlobalData.Diccionario_Tropas[i]!!.clonar()
        }


        //invocas 2 curadneros atras 150pv 50atq
        //curan 100pv a todos expeto a ellosmismo

        //invocas 3 gigantes estelares, 500pv y 100ataque

        //solo si no tienes ninguna tropa viva y ni el rival
    }

    fun TUSK(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        if(Waos) {
            if (!GlobalData.Jugador1[1]!!.estado_de_vida &&
                !GlobalData.Jugador1[2]!!.estado_de_vida &&
                !GlobalData.Jugador1[3]!!.estado_de_vida &&
                !GlobalData.Jugador1[4]!!.estado_de_vida &&
                !GlobalData.Jugador1[5]!!.estado_de_vida
            ) {

                GlobalData.Jugador1[1] = GlobalData.Jugador2[1]!!.clonar()
                GlobalData.Jugador1[2] = GlobalData.Jugador2[2]!!.clonar()
                GlobalData.Jugador1[3] = GlobalData.Jugador2[3]!!.clonar()
                GlobalData.Jugador1[4] = GlobalData.Jugador2[4]!!.clonar()
                GlobalData.Jugador1[5] = GlobalData.Jugador2[5]!!.clonar()

                GlobalData.Jugador2[1]!!.vida -= GlobalData.Jugador2[1]!!.vida
                GlobalData.Jugador2[2]!!.vida -= GlobalData.Jugador2[2]!!.vida
                GlobalData.Jugador2[3]!!.vida -= GlobalData.Jugador2[3]!!.vida
                GlobalData.Jugador2[4]!!.vida -= GlobalData.Jugador2[4]!!.vida
                GlobalData.Jugador2[5]!!.vida -= GlobalData.Jugador2[5]!!.vida
            }
        }
        if(!Waos) {
            if (!GlobalData.Jugador2[1]!!.estado_de_vida &&
                !GlobalData.Jugador2[2]!!.estado_de_vida &&
                !GlobalData.Jugador2[3]!!.estado_de_vida &&
                !GlobalData.Jugador2[4]!!.estado_de_vida &&
                !GlobalData.Jugador2[5]!!.estado_de_vida
            ) {

                GlobalData.Jugador2[1] = GlobalData.Jugador1[1]!!.clonar()
                GlobalData.Jugador2[2] = GlobalData.Jugador1[2]!!.clonar()
                GlobalData.Jugador2[3] = GlobalData.Jugador1[3]!!.clonar()
                GlobalData.Jugador2[4] = GlobalData.Jugador1[4]!!.clonar()
                GlobalData.Jugador2[5] = GlobalData.Jugador1[5]!!.clonar()

                GlobalData.Jugador1[1]!!.vida -= GlobalData.Jugador1[1]!!.vida
                GlobalData.Jugador1[2]!!.vida -= GlobalData.Jugador1[2]!!.vida
                GlobalData.Jugador1[3]!!.vida -= GlobalData.Jugador1[3]!!.vida
                GlobalData.Jugador1[4]!!.vida -= GlobalData.Jugador1[4]!!.vida
                GlobalData.Jugador1[5]!!.vida -= GlobalData.Jugador1[5]!!.vida
            }
        }
        //si todas las tropas aliadas estan muertas , las tropas enemigas se pasan a tu bando

    }

    fun Diamond(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var num = Random.nextInt(100)
        if(num < 50){
            GlobalData.Jugador1[posicion]
        }
        //prrobabilidad de 50% de q reviva con un 50% de vida una tropa aleaotria muerta y 5% con 100&

        val daño = daño()
        enemigos[posicion]!!.Recivir_daño(this,daño)
        //q cada vez q muera aliada ,muera una tropa del irivql 50% probabilidad

    }

    fun Calamidad(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean){

           //bajar la tropa a nivel 1
    }

    fun Killer_Queen(){
           //seleccina a uno y elimina a una tropa aleatoria enemiga, ademas
            // le resta 20% de acertar un ataque y regeenra vida si es la vida esta baja
    }

    fun Golden(){

            //una ves por partida
           //obtienes imnuidad total
           // var = val
           //no recibes efecto

    }




    override fun clonar(): Tropa {
        val copia = Rey_Cristian(this.nivel)
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
        copia.cantidad_espinas = this.cantidad_espinas
        copia.cantidad_escudos = this.cantidad_escudos
        return copia
    }

    override fun Recivir_daño(tropa: Tropa,Ataque :Int) {
        if(this.cantidad_escudos > 0){
            this.vida -= (Ataque * (Ataque * cantidad_escudos)).toInt()
        }
        if(this.cantidad_espinas > 0){
            tropa.vida -= (Ataque * cantidad_espinas).toInt()
            return
        }

        this.vida -= Ataque
        return
    }

}