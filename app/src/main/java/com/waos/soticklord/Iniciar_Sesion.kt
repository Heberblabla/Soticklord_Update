package com.waos.soticklord

import Data.Especiales.Rey_Cristian
import Data.Especiales.Rey_Fernando
import Data.Especiales.Rey_Heber
import Data.Especiales.Reyna_Darisce
import Data.Especiales.Reyna_Shantal
import Data.Personalizados.Rey_Aethelred
import Data.Personalizados.Rey_Borrego
import Data.Personalizados.Rey_Bufon_Negro
import Data.Personalizados.Rey_El_Pro
import Data.Personalizados.Rey_Han_Kong
import Data.Personalizados.Rey_Jerald
import Data.Personalizados.Rey_Kanox
import Data.Personalizados.Rey_Kratos
import Data.Personalizados.Rey_Lucas
import Data.Personalizados.Rey_Moises
import Data.Personalizados.Reyna_paranormal
import Data.Rey_Arquero
import Data.Rey_Espadachin
import Data.Rey_Lanzatonio
import Data.Rey_de_los_Gigantes
import Data.Tropa
import Data.Tropa_Arquero
import Data.Tropa_Espadachin
import Data.Tropa_Gigante
import Data.Tropa_Lanzatonio
import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import java.security.MessageDigest
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdView


class Iniciar_Sesion : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var bannerView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContentView(R.layout.activity_iniciar_sesion)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 1️⃣ Inicializa el SDK de AdMob
        MobileAds.initialize(this) {}
        // 2️⃣ Conecta tu banner del XML
        bannerView = findViewById(R.id.bannerView)
        // 3️⃣ Crea una solicitud de anuncio
        val adRequest = AdRequest.Builder().build()
        // 4️⃣ Carga el anuncio
        bannerView.loadAd(adRequest)


        //DataManager.borrarDatos(this)
        cargardatos()
    }

    fun cargardatos(){
        DataManager.cargarDatos(this)


        if(GlobalData.Primer_inicio){
            iniciar_como_invitado()
            GlobalData.Primer_inicio = false
            DataManager.guardarDatos(this)
        }
        inciar_musica()

    }

    fun inciar_musica(){

        mediaPlayer = MediaPlayer.create(this, R.raw.musica)
        mediaPlayer.isLooping = true  // Para que se repita
        mediaPlayer.start()           // Reproduce al abrir la ventana
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release() // Libera memoria al cerrar la Activity
    }

    fun iniciar_como_invitado(){
        GlobalData.experiencia_de_juego += 100000
        GlobalData.nivel_De_cuenta = 10
        GlobalData.nivel_de_progresion = 0
        GlobalData.monedas += 500
        GlobalData.ecencia_de_juego += 100

            GlobalData.Diccionario_Reyes[0] = Rey_de_los_Gigantes(1)
            GlobalData.Diccionario_Reyes[1] = Rey_Arquero(1)
            GlobalData.Diccionario_Reyes[2] = Rey_Lanzatonio(1)
            GlobalData.Diccionario_Reyes[3] = Rey_Espadachin(1)
            GlobalData.Diccionario_Reyes[4] = Rey_Borrego(1)
            GlobalData.Diccionario_Reyes[5] = Reyna_paranormal(1)
            GlobalData.Diccionario_Reyes[6] = Reyna_Darisce(1)
            GlobalData.Diccionario_Reyes[7] = Reyna_Shantal(1)
            GlobalData.Diccionario_Reyes[8] = Rey_Fernando(1)
            GlobalData.Diccionario_Reyes[9] = Rey_Heber(1)
            GlobalData.Diccionario_Reyes[11] = Rey_Cristian(1)
            GlobalData.Diccionario_Reyes[12] = Rey_Kanox(1)
            GlobalData.Diccionario_Reyes[13] = Rey_Moises(1)
            GlobalData.Diccionario_Reyes[14] = Rey_El_Pro(1)
            GlobalData.Diccionario_Reyes[15] = Rey_Kratos(1)
            GlobalData.Diccionario_Reyes[16] = Rey_Aethelred(1)
            GlobalData.Diccionario_Reyes[17] = Rey_Han_Kong(1)
            GlobalData.Diccionario_Reyes[18] = Rey_Jerald(1)

            GlobalData.Diccionario_Tropas[0] = Tropa_Gigante(1)
            GlobalData.Diccionario_Tropas[1] = Tropa_Arquero(1)
            GlobalData.Diccionario_Tropas[2] = Tropa_Lanzatonio(1)
            GlobalData.Diccionario_Tropas[3] = Tropa_Espadachin(1)

    }

    fun Jugar(view: View) {
        val intent = Intent(this@Iniciar_Sesion, Escoger_modo::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()

    }


    fun Album(view: View) {
        val intent = Intent(this@Iniciar_Sesion, Album::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
    fun Tienda(view: View) {
        Toast.makeText(this, "Proximamente", Toast.LENGTH_SHORT).show()
        if(GlobalData.ecencia_de_juego > 0){
            GlobalData.ecencia_de_juego -= 1
            GlobalData.monedas += 100
        }
        GlobalData.Diccionario_Reyes[19] = Rey_Bufon_Negro(1)
    }

}
