package com.waos.soticklord

import Data.Especiales.Rey_Vago_de_Vagos
import Data.Personalizados.Rey_Bufon_Negro
import Data.Tropas_personalizadas.Tropa_Bufon
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import android.net.Uri
import android.view.View
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import android.widget.Button
import android.widget.Toast
import Data.Tropas_personalizadas.Tropa_Curandera
import Data.Tropas_personalizadas.Tropa_Gato_amigo2
import Data.Tropas_personalizadas.Tropa_Gigante_estelar
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback


class Tienda : AppCompatActivity() {
    private var rewardedAd: RewardedAd? = null


    private lateinit var videoView: VideoView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this) {}
        cargarRewardedAd()

        enableEdgeToEdge()
        setContentView(R.layout.activity_tienda)

        // Pantalla completa
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


        val info = findViewById<TextView>(R.id.Informacion)

        info.text = """
            ------------Rey Vago de Vagos------------
            
            Un rey tan perezoso como poderoso: inicia
            con defensa total y deja que sus Lanzatonios
            Medievales peleen por él. Cada ataque invoca
            a uno nuevo, formando un ejército que lucha
            mientras el monarca descansa sin preocupación
            alguna.
        """.trimIndent()

        videoView = findViewById<VideoView>(R.id.Video_muestra)
        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.rey_vago)
        videoView.setVideoURI(uri)
        // Opcional: controles
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setOnTouchListener { _, _ -> true }
        videoView.start()
        mostrar_datos_economicos()
    }

    private fun cargarRewardedAd() {
        RewardedAd.load(
            this,
            //ca-app-pub-3940256099942544/5224354917 // prueba
            //ca-app-pub-4214042047166873/5869616806 // real
            "ca-app-pub-4214042047166873/5869616806", //
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    rewardedAd = null
                }
            }
        )
    }

    private fun mostrarRewarded() {
        if (rewardedAd != null) {
            rewardedAd?.show(this) { rewardItem ->
                // AQUI DAS LA RECOMPENSA
                val amount = rewardItem.amount
                val type = rewardItem.type

                // Por ejemplo, sumar monedas:
                sumarMonedas(350)
                Toast.makeText(this, "¡Recibiste 350 monedas!", Toast.LENGTH_SHORT).show()

                // Cargar otro anuncio para la próxima vez
                cargarRewardedAd()
            }
        } else {
            Toast.makeText(this, "Cargando anuncio...", Toast.LENGTH_SHORT).show()
            cargarRewardedAd()
        }
    }

    private fun sumarMonedas(cantidad: Int) {
        GlobalData.monedas += cantidad
        DataManager.guardarDatos(this)
        mostrar_datos_economicos()
        // guarda en SharedPreferences si quieres
    }




    fun mostrar_datos_economicos(){
        val monedas = findViewById<TextView>(R.id.monedass)
        monedas.text = "\uD83E\uDE99 : ${GlobalData.monedas}"
        val ecencia = findViewById<TextView>(R.id.ecencia)
        ecencia.text = "✮⋆˙ : ${GlobalData.ecencia_de_juego}"
    }

    fun comprar_rey_bufon(view: View) {

        val inflater = layoutInflater
        val vista = inflater.inflate(R.layout.popup_confirmar, null)

        val texto = vista.findViewById<TextView>(R.id.textoConfirmar)
        val botonSi = vista.findViewById<Button>(R.id.botonSi)
        val botonNo = vista.findViewById<Button>(R.id.botonNo)

        // Por si quieres cambiar el texto dinámicamente:
        texto.text = """
            
            ------¿Deseas comprar al Rey Vago de Vagos?------
            
            -                            Costo : 1500 🪙                              -
                           
            """.trimIndent()

        val dialogo = AlertDialog.Builder(this)
            .setView(vista)
            .setCancelable(false)
            .create()

        botonSi.setOnClickListener {
            if(Existe_el_rey("Rey_Vago_de_Vagos")){
                Toast.makeText(this, "Ya tienes este Rey en tu coleccion", Toast.LENGTH_SHORT).show()
                dialogo.dismiss()
            }else {
                if (GlobalData.monedas >= 1500) {
                    GlobalData.monedas -= 1500
                    mostrar_datos_economicos()
                    var numero = obtenerUltimoID() + 1
                    GlobalData.Diccionario_Reyes[numero] = Rey_Vago_de_Vagos(5)
                    Toast.makeText(this, "Compra realizada", Toast.LENGTH_SHORT).show()
                    DataManager.guardarDatos(this)
                    dialogo.dismiss()
                } else {
                    Toast.makeText(this, "No tiene suficiente Monedas", Toast.LENGTH_SHORT).show()
                    dialogo.dismiss()
                }
            }
        }

        botonNo.setOnClickListener {
            dialogo.dismiss()
        }

        dialogo.show()
    }

    fun Existe_el_rey(nombreBuscado: String): Boolean {
        //true = existe
        //false no existe
        return GlobalData.Diccionario_Reyes.values.any { it.nombre == nombreBuscado }
    }
    fun Existe_la_tropa(nombreBuscado: String): Boolean {
        //true = existe
        //false no existe
        return GlobalData.Diccionario_Tropas.values.any { it.nombre == nombreBuscado }
    }


    fun obtenerUltimoIDTropa(): Int {
        return if (GlobalData.Diccionario_Tropas.isEmpty()) {
            -1   // o 0, según lo que necesites
        } else {
            GlobalData.Diccionario_Tropas.keys.max()
        }
    }

    fun obtenerUltimoID(): Int {
        return if (GlobalData.Diccionario_Reyes.isEmpty()) {
            -1   // o 0, según lo que necesites
        } else {
            GlobalData.Diccionario_Reyes.keys.max()
        }
    }


    fun comprar_Aa(view: View) {

        val inflater = layoutInflater
        val vista = inflater.inflate(R.layout.popup_confirmar, null)

        val texto = vista.findViewById<TextView>(R.id.textoConfirmar)
        val botonSi = vista.findViewById<Button>(R.id.botonSi)
        val botonNo = vista.findViewById<Button>(R.id.botonNo)

        // Por si quieres cambiar el texto dinámicamente:
        texto.text = """
            
            ------¿Deseas comprar a la Tropa Bufon?----
            
            -                            Costo : 1000 🪙                              -
                           
            """.trimIndent()

        val dialogo = AlertDialog.Builder(this)
            .setView(vista)
            .setCancelable(false)
            .create()

        botonSi.setOnClickListener {
            if(Existe_la_tropa("Tropa_Bufon")){
                Toast.makeText(this, "Ya tienes esta Tropa en tu coleccion", Toast.LENGTH_SHORT).show()
                dialogo.dismiss()
            }else {
                if (GlobalData.monedas >= 1000) {
                    GlobalData.monedas -= 1000
                    mostrar_datos_economicos()
                    var numero = obtenerUltimoIDTropa() + 1
                    GlobalData.Diccionario_Tropas[numero] = Tropa_Bufon(5)
                    Toast.makeText(this, "Compra realizada", Toast.LENGTH_SHORT).show()
                    DataManager.guardarDatos(this)
                    dialogo.dismiss()
                } else {
                    Toast.makeText(this, "No tiene suficiente Monedas", Toast.LENGTH_SHORT).show()
                    dialogo.dismiss()
                }
            }
        }

        botonNo.setOnClickListener {
            dialogo.dismiss()
        }

        dialogo.show()
    }

    fun comprar_Ab(view: View) {

        val inflater = layoutInflater
        val vista = inflater.inflate(R.layout.popup_confirmar, null)

        val texto = vista.findViewById<TextView>(R.id.textoConfirmar)
        val botonSi = vista.findViewById<Button>(R.id.botonSi)
        val botonNo = vista.findViewById<Button>(R.id.botonNo)

        // Por si quieres cambiar el texto dinámicamente:
        texto.text = """
            
            ----------¿Deseas comprar x100🪙?---------------
            
            -                            Costo : x3 ✮⋆˙                                 -
                           
            """.trimIndent()

        val dialogo = AlertDialog.Builder(this)
            .setView(vista)
            .setCancelable(false)
            .create()

        botonSi.setOnClickListener {
            if(GlobalData.ecencia_de_juego >= 3){
                GlobalData.ecencia_de_juego -= 3
                GlobalData.monedas += 100
                mostrar_datos_economicos()
                Toast.makeText(this, "Compra realizada", Toast.LENGTH_SHORT).show()
                DataManager.guardarDatos(this)
                dialogo.dismiss()
            }else{
                Toast.makeText(this, "No tiene suficiente Esencia", Toast.LENGTH_SHORT).show()
                dialogo.dismiss()
            }

        }

        botonNo.setOnClickListener {
            dialogo.dismiss()
        }

        dialogo.show()
    }

    fun comprar_Ba(view: View) {

        val inflater = layoutInflater
        val vista = inflater.inflate(R.layout.popup_confirmar, null)

        val texto = vista.findViewById<TextView>(R.id.textoConfirmar)
        val botonSi = vista.findViewById<Button>(R.id.botonSi)
        val botonNo = vista.findViewById<Button>(R.id.botonNo)

        // Por si quieres cambiar el texto dinámicamente:
        texto.text = """
            
            ------¿Deseas comprar a la Tropa Curandera?------
            
            -                            Costo : 750 🪙                              -
                           
            """.trimIndent()

        val dialogo = AlertDialog.Builder(this)
            .setView(vista)
            .setCancelable(false)
            .create()

        botonSi.setOnClickListener {
            if(Existe_la_tropa("Tropa_Curandera")){
                Toast.makeText(this, "Ya tienes esta Tropa en tu coleccion", Toast.LENGTH_SHORT).show()
                dialogo.dismiss()
            }else {
                if (GlobalData.monedas >= 750) {
                    GlobalData.monedas -= 750
                    mostrar_datos_economicos()
                    var numero = obtenerUltimoIDTropa() + 1
                    println("numero = $numero")
                    GlobalData.Diccionario_Tropas[numero] = Tropa_Curandera(2)
                    Toast.makeText(this, "Compra realizada", Toast.LENGTH_SHORT).show()
                    DataManager.guardarDatos(this)
                    dialogo.dismiss()
                } else {
                    Toast.makeText(this, "No tiene suficiente Monedas", Toast.LENGTH_SHORT).show()
                    dialogo.dismiss()
                }
            }
        }
        botonNo.setOnClickListener {
            dialogo.dismiss()
        }
        dialogo.show()
    }

    fun comprar_Bb(view: View) {

        val inflater = layoutInflater
        val vista = inflater.inflate(R.layout.popup_confirmar, null)

        val texto = vista.findViewById<TextView>(R.id.textoConfirmar)
        val botonSi = vista.findViewById<Button>(R.id.botonSi)
        val botonNo = vista.findViewById<Button>(R.id.botonNo)

        // Por si quieres cambiar el texto dinámicamente:
        texto.text = """
            
            ------¿Deseas Ver un anuncio por 350🪙?------
            
            -                                                                          -
                           
            """.trimIndent()

        val dialogo = AlertDialog.Builder(this)
            .setView(vista)
            .setCancelable(false)
            .create()

        botonSi.setOnClickListener {
            dialogo.dismiss()
            mostrarRewarded()
        }

        botonNo.setOnClickListener {
            dialogo.dismiss()
        }

        dialogo.show()
    }

    fun comprar_Ca(view: View) {

        val inflater = layoutInflater
        val vista = inflater.inflate(R.layout.popup_confirmar, null)

        val texto = vista.findViewById<TextView>(R.id.textoConfirmar)
        val botonSi = vista.findViewById<Button>(R.id.botonSi)
        val botonNo = vista.findViewById<Button>(R.id.botonNo)

        // Por si quieres cambiar el texto dinámicamente:
        texto.text = """
            
            ------¿Deseas comprar a la Tropa Gato Amigo 2?------
            
            -                            Costo : 600 🪙                              -
                           
            """.trimIndent()

        val dialogo = AlertDialog.Builder(this)
            .setView(vista)
            .setCancelable(false)
            .create()

        botonSi.setOnClickListener {
            if(Existe_la_tropa("Tropa_Gato_amigo2")){
                Toast.makeText(this, "Ya tienes esta Tropa en tu coleccion", Toast.LENGTH_SHORT).show()
                dialogo.dismiss()
            }else {
                if (GlobalData.monedas >= 600) {
                    GlobalData.monedas -= 600
                    mostrar_datos_economicos()
                    var numero = obtenerUltimoIDTropa() + 1
                    GlobalData.Diccionario_Tropas[numero] = Tropa_Gato_amigo2(3)
                    Toast.makeText(this, "Compra realizada", Toast.LENGTH_SHORT).show()
                    DataManager.guardarDatos(this)
                    dialogo.dismiss()
                } else {
                    Toast.makeText(this, "No tiene suficiente Monedas", Toast.LENGTH_SHORT).show()
                    dialogo.dismiss()
                }
            }
        }

        botonNo.setOnClickListener {
            dialogo.dismiss()
        }

        dialogo.show()
    }

    fun ruletas(view: View){
        val intent = Intent(this, Tienda_Ruleta::class.java)
        startActivity(intent)
    }

    fun atras(view: View){
        val intent = Intent(this, Iniciar_Sesion::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

}