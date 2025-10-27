package Data.Personalizados

import Data.Tropa
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
        vida = calcularVida(950,Nivel),
        ataque_base = calcularAtaque(50,Nivel),
        daño_critico = calcularDañoCritico(10.0,Nivel),
        probabilidad_de_critico = calcularProbCritico(0.90,Nivel),
        aereo = true,
        estado_de_vida = true,
        rutaviva = R.drawable.reyna_paranormal,
        rutamuerta = R.drawable.tropa_muerta,
        turnoActivo = true,
        turnoDoble =  false,
        cantidad_espinas = 0,
        cantidad_escudos = 0
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

    fun Ataque_normal(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
        val daño = daño()
        enemigos[posicion].Recivir_daño(this, daño)
    }

    fun Ataque_Normal(enemigos: ArrayList<Tropa>, posicion: Int, Waos: Boolean) {
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