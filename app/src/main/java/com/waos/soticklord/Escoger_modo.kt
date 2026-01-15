package com.waos.soticklord

import Data.Personalizados.Rey_Bufon_Negro
import Data.Tropa_Gigante
import Data.Tropas_personalizadas.Tropa_Curandera
import Evento.Evento
import Multijugador.Unirse_Partida
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.waos.soticklord.Iniciar_Sesion
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdView
import com.waos.soticklord.DataManager.mostrarPopupConImagen
import com.waos.soticklord.databinding.ActivityJugador1Binding

class Escoger_modo : AppCompatActivity() {

    private lateinit var fondoVideo: TextureView
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var bannerView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_escoger_modo)

        // pantalla completa
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



        // 1️ Inicializa el SDK de AdMob
        MobileAds.initialize(this) {}
        // 2️ Conecta tu banner del XML
        bannerView = findViewById(R.id.bannerView)
        // 3 Crea una solicitud de anuncio
        val adRequest = AdRequest.Builder().build()
        // 4️ Carga el anuncio
        bannerView.loadAd(adRequest)

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }


    fun primer_modo(view: View){
        val intent = Intent(this, Mapa::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
    fun segundo_modod(view: View){
        val intent = Intent(this, Unirse_Partida::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
    fun tercer_modo(view: View){

            if(GlobalData.nivel_De_cuenta >= 20){
                if(hayConexion(this)) {
                    GlobalData.Jugador2[0] = Rey_Bufon_Negro(20,false)
                    GlobalData.Jugador2[1] = Tropa_Curandera(20)
                    GlobalData.Jugador2[2] = Tropa_Curandera(20)
                    GlobalData.Jugador2[3] = Tropa_Gigante(20)
                    GlobalData.Jugador2[4] = Tropa_Gigante(20)
                    GlobalData.Jugador2[5] = Tropa_Gigante(20)
                    val intent = Intent(this, Evento::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    finish()
                }
            }else{
                Toast.makeText(this, "Necesitas ser minimo Nivel 20 para jugar Los eventos Semanales", Toast.LENGTH_SHORT).show()
            }



    }

    fun atras(view: View){
        val intent = Intent(this, Iniciar_Sesion::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }


    fun hayConexion(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        val conectado =
            capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        if (!conectado) {
            mostrarPopupConImagen(
                context,
                "⚠️ Conexión perdida\n " +
                        "Se necesita Internet para continuar"
            ) {

            }
        }

        return conectado
    }

}
