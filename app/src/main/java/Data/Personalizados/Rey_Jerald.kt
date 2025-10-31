package Data.Personalizados

import Data.Tropa
import Data.Tropa.Companion.calcularAtaque
import Data.Tropa.Companion.calcularDañoCritico
import Data.Tropa.Companion.calcularProbCritico
import Data.Tropa.Companion.calcularVida
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
        vida = calcularVida(950,Nivel),
        ataque_base = calcularAtaque(50,Nivel),
        daño_critico = calcularDañoCritico(10.0,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.90,Nivel),
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
        //Tu vida aumentara en un 25% y tu dañño se duplicara durante 2 turnos
    }
    fun Salto_de_soga(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
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
        //se coloca una ballesta q inflijira un daño iguala  tu aatque base
        //a todos
    }
    fun invocacion_de_gato(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        //invocas a un gato aliado ,q mientras este vivo tus aatques inflijiran x3 de daño
        //junto con el
    }
    fun Sube_de_fase(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        //UNA VES POR PARTIDA
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
        //una ves por partida
        //invocas un bus de arequipa q inflije daño a todos de atqqeu base ,el rey sufre
        //el doble , su defensa baja en un 70% , pierde turno y su ataque y vida se reduce
        //en un 25%
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