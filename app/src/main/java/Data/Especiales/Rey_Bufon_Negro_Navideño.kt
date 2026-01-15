package Data.Especiales

import Data.Tropa
import Data.Tropa.Companion.calcularAtaque
import Data.Tropa.Companion.calcularDañoCritico
import Data.Tropa.Companion.calcularProbCritico
import Data.Tropa.Companion.calcularVida
import Data.Tropa_Gigante
import android.widget.ImageView
import com.waos.soticklord.Animador
import com.waos.soticklord.GlobalData
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random

class Rey_Bufon_Negro_Navideño
    (
    Nivel:Int = 1 ,
    esDelJugador: Boolean
):
    Tropa(
        nombre = "Rey_Bufon_Negro_Navideño",
        nivel = Nivel,
        vida = calcularVida(1000,Nivel),
        ataque_base = calcularAtaque(100,Nivel),
        daño_critico = calcularDañoCritico(1.3,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.65,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.rey_bufon_negro_navideno,
        rutamuerta = R.drawable.tropa_muerta,
        turnoActivo = true,
        turnoDoble =  false,
        cantidad_espinas = 0.00,
        cantidad_escudos = 0.00,
        precision = 100
    ), Serializable {

    var ultimo_daño = 0;
    var Bando = esDelJugador

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


    fun Ataque_normal(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if (xd < this.precision) {
            //sigue realizando tu atque
        } else {
            return
        }
        val daño = daño()
        enemigos[posicion].Recivir_daño(this, daño)
    }

    fun Oscuridad_Negra(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if (xd < this.precision) {
            //sigue realizando tu atque
        } else {
            return
        }

        this.vida += (this.vida * 0.3).toInt()
        this.cantidad_escudos += 0.05

    }

    fun Risa_desgarradora(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if (xd < this.precision) {
            //sigue realizando tu atque
        } else {
            return
        }

        xd = Random.nextInt(100)

        enemigos[posicion].Recivir_daño(this, this.ataque_base)
        if (xd < 25) {
            enemigos[posicion].turnoActivo = false
        } else {
            return
        }

        this.vida += (this.vida * 0.1).toInt()

    }

    fun Contrataque_Negro(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if (xd < this.precision) {
            //sigue realizando tu atque
        } else {
            return
        }
        if (ultimo_daño == 0) {
            val daño = daño()
            enemigos[posicion].Recivir_daño(this, daño)
            return
        }
        ultimo_daño = ultimo_daño * 2
        enemigos[posicion].Recivir_daño(this, ultimo_daño)
    }

    fun Sombras_Traviesas(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision) {
            //sigue realizando tu atque
        }else {
            return
        }
        var Angel1 = Tropa_Gigante(this.nivel)
        Angel1.Ataque_normal(ArrayList(enemigos.filterNotNull()), posicion, Waos)
        var Angel2 = Tropa_Gigante(this.nivel)
        Angel2.Ataque_normal(ArrayList(enemigos.filterNotNull()), posicion, Waos)

        this.vida += (this.vida * 0.1).toInt()
        this.ataque_base += (this.ataque_base * 0.1).toInt()

    }

    fun Mascara_Maldicta(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision) {
            //sigue realizando tu atque
        }else {
            return
        }

        if(enemigos[posicion].cantidad_escudos < 0){
            enemigos[posicion].ataque_base -= (enemigos[posicion].ataque_base * 0.2).toInt()
            enemigos[posicion].precision -= (enemigos[posicion].precision * 0.2).toInt()
        }else {
            enemigos[posicion].ataque_base -= (enemigos[posicion].ataque_base * 0.5).toInt()
            enemigos[posicion].precision -= (enemigos[posicion].precision * 0.5).toInt()
        }

    }

    fun Capsula_de_Locura(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision) {
            //sigue realizando tu atque
        }else {
            return
        }
        var rey = Rey_Bufon_Negro_Navideño(this.nivel,true)
        var waos = rey.vida

        GlobalData.Atodos = true
        if(this.vida < (waos / 2)){
            val daño = daño()/2
            enemigos[0]!!.Recivir_daño(this,daño)
            enemigos[1]!!.Recivir_daño(this,daño)
            enemigos[2]!!.Recivir_daño(this,daño)
            enemigos[3]!!.Recivir_daño(this,daño)
            enemigos[4]!!.Recivir_daño(this,daño)
            enemigos[5]!!.Recivir_daño(this,daño)
        }else{
            val daño = daño()*2
            enemigos[0]!!.Recivir_daño(this,daño)
            enemigos[1]!!.Recivir_daño(this,daño)
            enemigos[2]!!.Recivir_daño(this,daño)
            enemigos[3]!!.Recivir_daño(this,daño)
            enemigos[4]!!.Recivir_daño(this,daño)
            enemigos[5]!!.Recivir_daño(this,daño)
        }



    }

    fun Invocacion_antes_de_Tiempo(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision) {
            //sigue realizando tu atque
        }else {
            return
        }
        var rey = Rey_Bufon_Negro_Navideño(this.nivel,true)
        var waos = rey.vida

        val daño = (daño()*1.5).toInt()
        enemigos[0]!!.Recivir_daño(this,daño)
        enemigos[1]!!.Recivir_daño(this,daño)
        enemigos[2]!!.Recivir_daño(this,daño)
        enemigos[3]!!.Recivir_daño(this,daño)
        enemigos[4]!!.Recivir_daño(this,daño)
        enemigos[5]!!.Recivir_daño(this,daño)

        var prueba = Tropa_Gigante(1)

        this.vida = 0
        Recivir_daño(prueba,0)
    }


    override fun Habilidad_Especial(Waos: Boolean) {

    }

    override fun clonar(): Tropa {
        val copia = Rey_Bufon_Negro_Navideño(this.nivel,this.Bando)
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
        //------------------------------------
        ultimo_daño = Ataque
        var devolvergolpe = Random.nextInt(100)
        if(devolvergolpe < 40){
            tropa.vida -= Ataque
        }else{
            //no devuelves nada
        }
        var recivirdaño = Random.nextInt(100)
        if(recivirdaño < 75){
            //continua
        }else{
            return
        }
        //-------------------------------------
        if (this.cantidad_espinas > 0) {
            tropa.vida -= (Ataque * cantidad_espinas).toInt()
        }

        if (this.cantidad_escudos > 0) {
            val escudo = cantidad_escudos.coerceAtMost(1.0)
            this.vida -= (Ataque - (Ataque * escudo)).toInt()
            if(this.vida <= 0) {
                this.rutaviva = R.drawable.bufon_caido
                if(this.Bando){
                    GlobalData.Jugador1[0] = Rey_Gigante_Bufon_Negro(this.nivel)

                    val actividad = GlobalData.batalla ?: return // si es null, salimos
                    GlobalData.invocacion(actividad)
                    GlobalData.reproducirInvocacion()

                    actividad.runOnUiThread {
                        val img = actividad.findViewById<ImageView>(R.id.Imagen_izquierda)
                        img.setImageResource(R.drawable.gigante_gufon)

                    }

                }else {
                    GlobalData.Jugador2[0] = Rey_Gigante_Bufon_Negro(this.nivel)
                    val actividad = GlobalData.batalla ?: return // si es null, salimos
                    GlobalData.invocacion(actividad)
                    GlobalData.reproducirInvocacion()
                    actividad.runOnUiThread {
                        val img = actividad.findViewById<ImageView>(R.id.imagen_derecha)
                        img.scaleX = -1f
                        img.setImageResource(R.drawable.gigante_gufon)

                    }
                }

            }
        }else{
            this.vida -= Ataque
            if(this.vida <= 0) {
                this.rutaviva = R.drawable.bufon_caido
                if(this.Bando){
                    println("se muriooooooooooooooo")
                    GlobalData.Jugador1[0] = Rey_Gigante_Bufon_Negro(this.nivel)

                    val actividad = GlobalData.batalla ?: return // si es null, salimos
                    GlobalData.invocacion(actividad)
                    GlobalData.reproducirInvocacion()


                    actividad.runOnUiThread {
                        val img = actividad.findViewById<ImageView>(R.id.Imagen_izquierda)
                        img.setImageResource(R.drawable.gigante_gufon)
                        Animador.animacionConTransicion(
                            GlobalData.batalla!!,
                            img,
                            "reposo_gigante",
                            "ataque_gigante"
                        )
                    }



                }else{
                    println("se muriooooooooooooooo")
                    GlobalData.Jugador2[0] = Rey_Gigante_Bufon_Negro(this.nivel)
                    val actividad = GlobalData.batalla ?: return // si es null, salimos
                    GlobalData.invocacion(actividad)
                    GlobalData.reproducirInvocacion()


                    actividad.runOnUiThread {
                        val img = actividad.findViewById<ImageView>(R.id.imagen_derecha)
                        img.scaleX = -1f
                        img.setImageResource(R.drawable.gigante_gufon)
                        Animador.animacionConTransicion(
                            GlobalData.batalla!!,
                            img,
                            "reposo_gigante",
                            "ataque_gigante"
                        )
                    }




                }

            }
        }

    }




}