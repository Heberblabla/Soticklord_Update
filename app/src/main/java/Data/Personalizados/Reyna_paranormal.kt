package Data.Personalizados

import Archivos_Extra.Evento
import Archivos_Extra.GestorEventos
import Data.Tropa
import Data.Tropa_Gigante
import com.waos.soticklord.R
import java.io.Serializable
import kotlin.math.ceil
import kotlin.random.Random
import android.app.Activity
import android.graphics.Color
import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.VideoView
import android.view.View
import android.view.Gravity
import com.waos.soticklord.*

class Reyna_paranormal (
    Nivel:Int = 1
):
    Tropa(
        nombre = "Reyna_paranormal",
        nivel = Nivel,
        vida = calcularVida(1300,Nivel),
        ataque_base = calcularAtaque(200,Nivel),
        daño_critico = calcularDañoCritico(2.0,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.60,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.reyna_paranormal,
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

    fun Ataque_normall(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if (xd < this.precision) {
            //sigue realizando tu atque
        } else {
            return
        }
        val daño = daño()
        enemigos[posicion].Recivir_daño(this, daño)
    }

    fun Porque_tan_solo(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {

        val batalla = GlobalData.batalla ?: return

        // Fondo oscuro semitransparente
        val contenedor = FrameLayout(batalla).apply {
            setBackgroundColor(Color.argb(220, 0, 0, 0)) // negro con transparencia
            alpha = 0f // empieza invisible
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        // Crear el VideoView centrado y ajustado
        val videoView = VideoView(batalla).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            ).apply {
                gravity = Gravity.CENTER
            }
        }

        contenedor.addView(videoView)
        batalla.addContentView(contenedor, contenedor.layoutParams)

        // Animación: fade in (aparecer suavemente)
        contenedor.animate()
            .alpha(1f)
            .setDuration(400)
            .start()

        // URI del video
        val uri = Uri.parse("android.resource://${batalla.packageName}/${R.raw.reyna_attack}")
        videoView.setVideoURI(uri)

        // Cuando el video está listo
        videoView.setOnPreparedListener { mp ->
            mp.setVolume(1f, 1f)
            mp.isLooping = false

            // Ajustar proporción del video (centrado sin recorte)
            val videoWidth = mp.videoWidth.toFloat()
            val videoHeight = mp.videoHeight.toFloat()
            val viewWidth = videoView.width.toFloat()
            val viewHeight = videoView.height.toFloat()

            if (videoWidth != 0f && videoHeight != 0f) {
                val scaleX = viewWidth / videoWidth
                val scaleY = viewHeight / videoHeight
                val scale = maxOf(scaleX, scaleY)
                videoView.scaleX = scale
                videoView.scaleY = scale
            }

            videoView.start()
        }

        // Cuando el video termina → ataque + fade out
        videoView.setOnCompletionListener {


            val daño = daño()
            enemigos[posicion].Recivir_daño(this, daño)


            // Animación de salida (fade out)
            contenedor.animate()
                .alpha(0f)
                .setDuration(400)
                .withEndAction {
                    (contenedor.parent as? ViewGroup)?.removeView(contenedor)
                }
                .start()
        }

        // Si hay error → igual aplica el ataque
        videoView.setOnErrorListener { _, _, _ ->
            val daño = daño()
            enemigos[posicion].Recivir_daño(this, daño)
            (contenedor.parent as? ViewGroup)?.removeView(contenedor)
            true
        }
    }

    fun Susto(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if (xd < this.precision) {
            //sigue realizando tu atque
        } else {
            return
        }
        GlobalData.Atodos = true
        for (tropa in enemigos) {
            tropa.precision -= 8
        }

    }

    fun Control_Sombra(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if (xd < this.precision) {
            //sigue realizando tu atque
        } else {
            return
        }
        if(Waos) {
            EntornoManager.cambiarEntorno(
                nuevo = DataEntornos.Reino_Paranormal,
                invocador = this, // la tropa que lo activó
                enemigos = GlobalData.Jugador2.filterNotNull(),
                aliados = GlobalData.Jugador1.filterNotNull()
            )
            var daño = daño()
            enemigos[posicion].Recivir_daño(this, daño)
        }
        if(!Waos) {
            EntornoManager.cambiarEntorno(
                nuevo = DataEntornos.Reino_Paranormal,
                invocador = this, // la tropa que lo activó
                enemigos = GlobalData.Jugador1.filterNotNull(),
                aliados = GlobalData.Jugador2.filterNotNull()
            )
            var daño = daño()
            enemigos[posicion].Recivir_daño(this, daño)
        }

        EntornoManager.aplicarEfecto()
    }
    //entorno de sombra al incio de cada turno pierden 5% de precicion ,
    // 150 de vida 25% de ataque abse :v
    //obtienes + 20% de prescion , +10% de escudo + 40% de daño critico
    //y + 20% de espinas

    fun Juguemos_un_juego(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        var xd = Random.nextInt(100)
        if(xd < this.precision) {
            //sigue realizando tu atque
            }else {
            return
            }

        var num = Random.nextInt(100)
        if(num < 85){
            var daño = (daño() * 2)
            enemigos[posicion]!!.vida -= daño
        }else{
            enemigos[posicion]!!.precision -= 10
        }

    }

    fun Reflejo_Maldito(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision) {
            //sigue realizando tu atque
        }else {
            return
        }

        this.vida = enemigos[posicion]!!.vida

    }

    fun Angeles_Demoniacos(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean){
        var xd = Random.nextInt(100)
        if(xd < this.precision) {
            //sigue realizando tu atque
        }else {
            return
        }
        var Angel1 = Tropa_Gigante(5)
        Angel1.Ataque_normal(ArrayList(enemigos.filterNotNull()), posicion, Waos)
        var Angel2 = Tropa_Gigante(5)
        Angel2.Ataque_normal(ArrayList(enemigos.filterNotNull()), posicion, Waos)

    }

    override fun Habilidad_Especial(Waos: Boolean){
        if(Waos) {
            if (this.vida > 0) {
                GestorEventos.agregar(
                    Evento(
                        tipo = "Almas Malignas",
                        objetivoIndex = GlobalData.Jugador1.indexOf(this), // o Jugador1 según el caso
                        quien = 1, // 1 = jugador, 2 = enemigo
                        turnosRestantes = 2, // revive en 3 turnos
                        efecto = { evento, batalla ->
                            if (evento.turnosRestantes == 1) {
                                GlobalData.Jugador1[0]!!.vida += 50
                                GlobalData.Jugador1[1]!!.vida += 50
                                GlobalData.Jugador1[2]!!.vida += 50
                                GlobalData.Jugador1[3]!!.vida += 50
                                GlobalData.Jugador1[4]!!.vida += 50
                                GlobalData.Jugador1[5]!!.vida += 50
                            }
                        }
                    )
                )
            }
        }
        if(!Waos) {
            if (this.vida > 0) {
                GestorEventos.agregar(
                    Evento(
                        tipo = "Almas Malignas",
                        objetivoIndex = GlobalData.Jugador2.indexOf(this), // o Jugador1 según el caso
                        quien = 1, // 1 = jugador, 2 = enemigo
                        turnosRestantes = 2, // revive en 3 turnos
                        efecto = { evento, batalla ->
                            if (evento.turnosRestantes == 1) {
                                GlobalData.Jugador2[0]!!.vida += 50
                                GlobalData.Jugador2[1]!!.vida += 50
                                GlobalData.Jugador2[2]!!.vida += 50
                                GlobalData.Jugador2[3]!!.vida += 50
                                GlobalData.Jugador2[4]!!.vida += 50
                                GlobalData.Jugador2[5]!!.vida += 50
                            }
                        }
                    )
                )
            }
        }
    }

    override fun clonar(): Tropa {
        val copia = Reyna_paranormal(this.nivel)
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
            this.cantidad_espinas -= 0.05
            return
        }

        this.vida -= Ataque
        return
    }

}