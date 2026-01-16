package Data.Especiales

import Data.Tropa
import com.waos.soticklord.DataEntornos
import com.waos.soticklord.EntornoManager
import com.waos.soticklord.GlobalData
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Reyna_Apio(Nivel:Int = 1): Tropa(
    nombre = "Reyna_Apio",
    nivel = Nivel,
    vida = calcularVida(850,Nivel),
    ataque_base = calcularAtaque(110,Nivel),
    daño_critico = calcularDañoCritico(1.8,Nivel),
    probabilidad_de_critico = calcularProbCritico(0.3,Nivel),
    aereo = false,
    estado_de_vida = true,
    rutaviva = R.drawable.reyna_apio,   // tu imagen en drawable
    rutamuerta = R.drawable.tropa_muerta,   // imagen al morir
    turnoActivo = true,
    turnoDoble =  false,
    cantidad_espinas = 0.00,
    cantidad_escudos = 0.00,
    precision = 100
), Serializable {

    var entorno = true
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
    //ataque normal q siempre alguien tiene
    fun Ataque_normal(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) { //1
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        val daño = daño()
        enemigos[posicion].Recivir_daño(this,daño)
    }
    
    fun Nota_Real(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) { //2
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        //los enemigos pierden 15% de su vida restante
        //y el rey pierde 10% mas, ademas recives un 5% 
        //de vida extra deacuerdo a tu salud restante
        
        enemigos[0]!!.vida -= (enemigos[0]!!.vida * 0.25).toInt()
        enemigos[1]!!.vida -= (enemigos[1]!!.vida * 0.15).toInt()
        enemigos[2]!!.vida -= (enemigos[2]!!.vida * 0.15).toInt()
        enemigos[3]!!.vida -= (enemigos[3]!!.vida * 0.15).toInt()
        enemigos[4]!!.vida -= (enemigos[4]!!.vida * 0.15).toInt()
        enemigos[5]!!.vida -= (enemigos[5]!!.vida * 0.15).toInt()
        
        this.vida += (this.vida * 0.05).toInt()
    
    }

    fun Voz_Calmante(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) { //3
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        //todos los efectos de combate se limpiaran,
        //ademas tus tropas reciven +8% de vida 
        //respecto a su salud restante y tu recives
        //+5% de vida respecto a tu salud restante
        this.vida += (this.vida * 0.05).toInt()
        GestorEventos.limpiar()
        if(Waos){
            GlobalData.jugador1[1]!!.vida += (GlobalData.jugador1[1]!!.vida * 0.08).toInt()
            GlobalData.jugador1[2]!!.vida += (GlobalData.jugador1[2]!!.vida * 0.08).toInt()
            GlobalData.jugador1[3]!!.vida += (GlobalData.jugador1[3]!!.vida * 0.08).toInt()
            GlobalData.jugador1[4]!!.vida += (GlobalData.jugador1[4]!!.vida * 0.08).toInt()
            GlobalData.jugador1[5]!!.vida += (GlobalData.jugador1[5]!!.vida * 0.08).toInt()
        }else{
            GlobalData.jugador2[1]!!.vida += (GlobalData.jugador2[1]!!.vida * 0.08).toInt()
            GlobalData.jugador2[2]!!.vida += (GlobalData.jugador2[2]!!.vida * 0.08).toInt()
            GlobalData.jugador2[3]!!.vida += (GlobalData.jugador2[3]!!.vida * 0.08).toInt()
            GlobalData.jugador2[4]!!.vida += (GlobalData.jugador2[4]!!.vida * 0.08).toInt()
            GlobalData.jugador2[5]!!.vida += (GlobalData.jugador2[5]!!.vida * 0.08).toInt()
        }
        
    }

    fun Aura_Real(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) { //4
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        
        
    }

    fun Golpe_de_Cetro(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) { //5
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        //todos los aliados reciven +12% de defensa 
        this.cantidad_escudos += 0.12
        if(Waos){
            GlobalData.jugador1[1]!!.cantidad_escudos += 0.12
            GlobalData.jugador1[2]!!.cantidad_escudos += 0.12
            GlobalData.jugador1[3]!!.cantidad_escudos += 0.12
            GlobalData.jugador1[4]!!.cantidad_escudos += 0.12
            GlobalData.jugador1[5]!!.cantidad_escudos += 0.12
        }else{
            GlobalData.jugador2[1]!!.cantidad_escudos += 0.12
            GlobalData.jugador2[2]!!.cantidad_escudos += 0.12
            GlobalData.jugador2[3]!!.cantidad_escudos += 0.12
            GlobalData.jugador2[4]!!.cantidad_escudos += 0.12
            GlobalData.jugador2[5]!!.cantidad_escudos += 0.12
        }
        
    }

    fun Resonancia_Apio(enemigos: ArrayList<Tropa?>, posicion: Int,Waos: Boolean) { //6
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        //todas las tropas enemigas bajan su defensa en un -10%
        if(Waos){
            GlobalData.jugador2[1]!!.cantidad_escudos -= 0.10
            GlobalData.jugador2[2]!!.cantidad_escudos -= 0.10
            GlobalData.jugador2[3]!!.cantidad_escudos -= 0.10
            GlobalData.jugador2[4]!!.cantidad_escudos -= 0.10
            GlobalData.jugador2[5]!!.cantidad_escudos -= 0.10
        }else{
            GlobalData.jugador1[1]!!.cantidad_escudos -= 0.10
            GlobalData.jugador1[2]!!.cantidad_escudos -= 0.10
            GlobalData.jugador1[3]!!.cantidad_escudos -= 0.10
            GlobalData.jugador1[4]!!.cantidad_escudos -= 0.10
            GlobalData.jugador1[5]!!.cantidad_escudos -= 0.10
        }
    }

    fun Trono_de_Hielo_Cantante(enemigos: ArrayList<Tropa>, posicion: Int,Waos: Boolean) { //6
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        //una ves por partida invocas un entorno 
        //el cual hara q los enemigos tengan una probabilidad del 10% de q pierdan turno por congelamiento
        cuando alguien pierda turno obtienes +10% de ataque
        
    }

    fun Acorde_Congelante(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) { //7
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
        
        //una ves por partida 
        
        
    }

    fun Mandato_Real(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) { //8
        var xd = Random.nextInt(100)
        if(xd < this.precision){
            //sigue realizando tu atque
        }else{
            return
        }
       
    }

    override fun Habilidad_Especial(Waos: Boolean){

    }

    override fun clonar(): Tropa {
        val copia = Reyna_Apio(this.nivel)
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



    override fun Recivir_daño(tropa: Tropa, Ataque :Int) {
        if (this.cantidad_espinas > 0) {
            tropa.vida -= (Ataque * cantidad_espinas).toInt()
        }

        if (this.cantidad_escudos > 0) {
            val escudo = cantidad_escudos.coerceAtMost(1.0)
            this.vida -= (Ataque - (Ataque * escudo)).toInt()

        }else{
            this.vida -= Ataque

        }
    }

}
