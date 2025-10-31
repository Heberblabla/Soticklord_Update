package Data

import com.waos.soticklord.GlobalData
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random


class Rey_de_los_Gigantes(Nivel:Int =1) : Tropa(
    nombre = "Rey_de_los_Gigantes",
    nivel = Nivel,
    vida = calcularVida(1500,Nivel),
    ataque_base = calcularAtaque(50,Nivel),
    daño_critico = calcularDañoCritico(1.5,Nivel),
    probabilidad_de_critico = calcularProbCritico(0.5,Nivel),
    aereo = false,
    estado_de_vida = true,
    rutaviva = R.drawable.rey_de_los_gigantes,  // tu imagen en drawable
    rutamuerta = R.drawable.tropa_muerta,       // tu imagen en drawable
    turnoActivo = true,
    turnoDoble =  false,
    cantidad_espinas = 0.00,
    cantidad_escudos = 0.00,
    precision = 100
)

    , Serializable {
    var invocacion : Boolean = true

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
    private fun Daño(): Int {
        val daño: Int
        val random = Random.Default
        val suerte = random.nextDouble()

        if (suerte < this.probabilidad_de_critico) {
            val x = this.ataque_base * this.daño_critico
            daño = ceil(x).toInt() // convertir a int redondeando hacia arriba
        } else {
            daño = this.ataque_base // golpe normal
        }

        return daño
    }

    //metodo principal para atcar
    fun Ataque_normal(enemigos: ArrayList<Tropa?>, posicion: Int,Waos : Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        val daño = Daño()
        enemigos[posicion]!!.Recivir_daño(this,daño)
    }
    fun Invocacion_de_Gigantes(enemigos: ArrayList<Tropa?>, posicion: Int, Waos : Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        if(Waos) {
            if (this.invocacion) {
                GlobalData.Jugador1[1] = Tropa_Gigante(this.nivel)
                GlobalData.Jugador1[2] = Tropa_Gigante(this.nivel)
                GlobalData.Jugador1[3] = Tropa_Gigante(this.nivel)
                GlobalData.Jugador1[4] = Tropa_Gigante(this.nivel)
                GlobalData.Jugador1[5] = Tropa_Gigante(this.nivel)
                this.invocacion = false
            }
        }
        if(!Waos) {
            if (this.invocacion) {
                GlobalData.Jugador2[1] = Tropa_Gigante(this.nivel)
                GlobalData.Jugador2[2] = Tropa_Gigante(this.nivel)
                GlobalData.Jugador2[3] = Tropa_Gigante(this.nivel)
                GlobalData.Jugador2[4] = Tropa_Gigante(this.nivel)
                GlobalData.Jugador2[5] = Tropa_Gigante(this.nivel)
                this.invocacion = false
            }
        }

    }


    override fun Habilidad_Especial(Waos: Boolean){

    }
    override fun clonar(): Tropa {
        val copia = Rey_de_los_Gigantes(this.nivel)
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
            this.vida -= (Ataque - (Ataque * cantidad_escudos)).toInt()
        }
        if(this.cantidad_espinas > 0){
            tropa.vida -= (Ataque * cantidad_espinas).toInt()
            return
        }

        this.vida -= Ataque
        return
    }

}
