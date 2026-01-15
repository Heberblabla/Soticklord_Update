package com.waos.soticklord

import Data.Rey_Arquero
import Data.Rey_Espadachin
import Data.Rey_Lanzatonio
import Data.Rey_de_los_Gigantes
import Data.Tropa_Arquero
import Data.Tropa_Espadachin
import Data.Tropa_Gigante
import Data.Tropa_Lanzatonio
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class Pantalla_inicio_carga : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_inicio_carga)

        // FULLSCREEN REAL
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        val videoView = findViewById<VideoView>(R.id.Waos)

        val videoPath = "android.resource://" + packageName + "/" + R.raw.carga
        videoView.setVideoURI(Uri.parse(videoPath))

        videoView.setOnPreparedListener { mp ->
            videoView.post {

                val viewWidth = videoView.width.toFloat()
                val viewHeight = videoView.height.toFloat()

                val videoWidth = mp.videoWidth.toFloat()
                val videoHeight = mp.videoHeight.toFloat()

                // 🔥 Escala TOTAL (deforma si es necesario)
                videoView.scaleX = viewWidth / videoWidth
                videoView.scaleY = viewHeight / videoHeight

                mp.isLooping = false
                mp.start()
            }
        }


        videoView.setOnCompletionListener {
            cargardatos()
            irAlMenu()
        }
    }


    private fun irAlMenu() {
        val intent = Intent(this, Iniciar_Sesion::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish() // cierra la pantalla de carga
    }

    fun cargardatos() {
        DataManager.cargarDatos(this)
        DataManager.guardarDatos(this)

        if (GlobalData.Primer_inicio) {
            GlobalData.Primer_inicio = false
            iniciar_como_invitado()
            DataManager.guardarDatos(this)
        }
        //DataManager.guardarDatos(this)

    }

    fun iniciar_como_invitado() {
        GlobalData.experiencia_de_juego += 0
        GlobalData.nivel_De_cuenta = 1
        GlobalData.nivel_de_progresion = 0
        GlobalData.monedas += 500
        GlobalData.ecencia_de_juego += 20

        GlobalData.Diccionario_Reyes[0] = Rey_de_los_Gigantes(1)
        GlobalData.Diccionario_Reyes[1] = Rey_Arquero(1)
        GlobalData.Diccionario_Reyes[2] = Rey_Lanzatonio(1)
        GlobalData.Diccionario_Reyes[3] = Rey_Espadachin(1)

        //GlobalData.Diccionario_Reyes[4] = Rey_Borrego(1)
        //GlobalData.Diccionario_Reyes[5] = Reyna_paranormal(1)
        //GlobalData.Diccionario_Reyes[6] = Reyna_Darisce(1)
        //GlobalData.Diccionario_Reyes[7] = Reyna_Shantal(1)
        //GlobalData.Diccionario_Reyes[8] = Rey_Fernando(1)
        //GlobalData.Diccionario_Reyes[9] = Rey_Heber(1)
        //GlobalData.Diccionario_Reyes[11] = Rey_Cristian(1)
        //GlobalData.Diccionario_Reyes[12] = Rey_Kanox(1)
        //GlobalData.Diccionario_Reyes[13] = Rey_Moises(1)
        //GlobalData.Diccionario_Reyes[14] = Rey_El_Pro(1)
        //Diccionario_Reyes[15] = Rey_Kratos(1)
        //GlobalData.Diccionario_Reyes[16] = Rey_Aethelred(1)
        //GlobalData.Diccionario_Reyes[17] = Rey_Han_Kong(1)
        //GlobalData.Diccionario_Reyes[18] = Rey_Jerald(1)

        GlobalData.Diccionario_Tropas[0] = Tropa_Gigante(1)
        GlobalData.Diccionario_Tropas[1] = Tropa_Arquero(1)
        GlobalData.Diccionario_Tropas[2] = Tropa_Lanzatonio(1)
        GlobalData.Diccionario_Tropas[3] = Tropa_Espadachin(1)

    }

}
