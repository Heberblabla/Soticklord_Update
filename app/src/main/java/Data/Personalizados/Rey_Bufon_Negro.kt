package Data.Personalizados

import Data.Especiales.Rey_Cristian
import Data.Especiales.Rey_Gigante_Bufon_Negro
import Data.Tropa
import Data.Tropa.Companion.calcularAtaque
import Data.Tropa.Companion.calcularDañoCritico
import Data.Tropa.Companion.calcularProbCritico
import Data.Tropa.Companion.calcularVida
import android.provider.Settings
import android.widget.ImageView
import com.waos.soticklord.GlobalData
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random
import com.waos.soticklord.*

class Rey_Bufon_Negro (
    Nivel:Int = 1
):
    Tropa(
        nombre = "Rey_Bufon_Negro",
        nivel = Nivel,
        vida = calcularVida(1000,Nivel),
        ataque_base = calcularAtaque(100,Nivel),
        daño_critico = calcularDañoCritico(1.3,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.75,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.bufon_negro,
        rutamuerta = R.drawable.tropa_muerta,
        turnoActivo = true,
        turnoDoble =  false,
        cantidad_espinas = 0.00,
        cantidad_escudos = 0.00,
        precision = 100
    ), Serializable {

    var ultimo_daño = 0;

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

    fun Contrataque_Negro(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if (xd < this.precision) {
            //sigue realizando tu atque
        } else {
            return
        }
        if (ultimo_daño == 0){
            val daño = daño()
            enemigos[posicion].Recivir_daño(this, daño)
            return
        }
        ultimo_daño = ultimo_daño * 2
        enemigos[posicion].Recivir_daño(this, ultimo_daño)
    }



    override fun Habilidad_Especial(Waos: Boolean) {

    }

    override fun clonar(): Tropa {
        val copia = Rey_Bufon_Negro(this.nivel)
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
            this.vida -= (Ataque - (Ataque * cantidad_escudos)).toInt()
            if(this.vida <= 0){
                if(GlobalData.A_quien){
                    GlobalData.Jugador2[0]
                }else{
                    GlobalData.Jugador1[0]
                }

            }
        }else{
            this.vida -= Ataque
            if(this.vida <= 0){
                this.rutaviva = R.drawable.bufon_caido
                if(GlobalData.A_quien){

                    GlobalData.Jugador2[0] = Rey_Gigante_Bufon_Negro(this.nivel)

                    val actividad = GlobalData.batalla ?: return // si es null, salimos
                    GlobalData.invocacion(actividad)
                    GlobalData.reproducirInvocacion()


                    actividad.runOnUiThread {
                        val img = actividad.findViewById<ImageView>(R.id.imagen_derecha)
                        img.scaleX = -1f
                        img.setImageResource(R.drawable.gigante_gufon)
                    }

                }else{

                    GlobalData.Jugador1[0] = Rey_Gigante_Bufon_Negro(this.nivel)
                    val actividad = GlobalData.batalla ?: return // si es null, salimos
                    GlobalData.invocacion(actividad)
                    GlobalData.reproducirInvocacion()


                    actividad.runOnUiThread {
                        val img = actividad.findViewById<ImageView>(R.id.Imagen_izquierda)
                        img.setImageResource(R.drawable.gigante_gufon)
                    }
                }

            }
        }

    }

}