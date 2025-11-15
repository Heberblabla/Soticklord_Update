package com.waos.soticklord

import Data.Personalizados.Rey_Bufon_Negro
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


class Tienda : AppCompatActivity() {
    private lateinit var videoView: VideoView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            ------------Rey Bufon Negro--------------
            Un soberano enigmático que combina astucia
            y caos: devuelve el daño recibido y esquiva
            ataques con sorprendente facilidad. Cuando 
            su vida llega a cero, un esqueleto gigante 
            —su padre retornado del más allá— 
            emerge para protegerlo, desatar su furia y
            continuar la batalla en su nombre.
        """.trimIndent()

        videoView = findViewById<VideoView>(R.id.Video_muestra)
        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.gigante_animation)
        videoView.setVideoURI(uri)
        // Opcional: controles
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setOnTouchListener { _, _ -> true }
        videoView.start()
        mostrar_datos_economicos()
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
            
            ------¿Deseas comprar al Rey Bufón Negro?------
            
            -                            Costo : 1000 🪙                              -
                           
            """.trimIndent()

        val dialogo = AlertDialog.Builder(this)
            .setView(vista)
            .setCancelable(false)
            .create()

        botonSi.setOnClickListener {
            if(Existe_el_rey("Rey_Bufon_Negro")){
                Toast.makeText(this, "Ya tienes este Rey en tu coleccion", Toast.LENGTH_SHORT).show()
                dialogo.dismiss()
            }else {
                if (GlobalData.monedas >= 1000) {
                    GlobalData.monedas -= 1000
                    mostrar_datos_economicos()
                    var numero = obtenerUltimoID() + 1
                    GlobalData.Diccionario_Reyes[numero] = Rey_Bufon_Negro(5)
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
            
            ------¿Deseas comprar a la Tropa Gigante Estelar?------
            
            -                            Costo : 500 🪙                              -
                           
            """.trimIndent()

        val dialogo = AlertDialog.Builder(this)
            .setView(vista)
            .setCancelable(false)
            .create()

        botonSi.setOnClickListener {
            if(Existe_la_tropa("Tropa_Gigante_estelar")){
                Toast.makeText(this, "Ya tienes esta Tropa en tu coleccion", Toast.LENGTH_SHORT).show()
                dialogo.dismiss()
            }else {
                if (GlobalData.monedas >= 500) {
                    GlobalData.monedas -= 500
                    mostrar_datos_economicos()
                    var numero = obtenerUltimoIDTropa() + 1
                    GlobalData.Diccionario_Tropas[numero] = Tropa_Gigante_estelar(3)
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
            
            ------¿Deseas Ver un anuncio por 200🪙?------
            
            -                                                                          -
                           
            """.trimIndent()

        val dialogo = AlertDialog.Builder(this)
            .setView(vista)
            .setCancelable(false)
            .create()

        botonSi.setOnClickListener {
            Toast.makeText(this, "Los Anuncios Ahun no estas operativos", Toast.LENGTH_SHORT).show()
            dialogo.dismiss()
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

    fun comprar_Cb(view: View) {

        val inflater = layoutInflater
        val vista = inflater.inflate(R.layout.popup_confirmar, null)

        val texto = vista.findViewById<TextView>(R.id.textoConfirmar)
        val botonSi = vista.findViewById<Button>(R.id.botonSi)
        val botonNo = vista.findViewById<Button>(R.id.botonNo)

        // Por si quieres cambiar el texto dinámicamente:
        texto.text = """
            
            ------¿Deseas comprar 1000🪙?------
            
            -                            Costo : 0 🪙                              -
                           
            """.trimIndent()

        val dialogo = AlertDialog.Builder(this)
            .setView(vista)
            .setCancelable(false)
            .create()

        botonSi.setOnClickListener {
            GlobalData.monedas += 1000
            DataManager.guardarDatos(this)
            Toast.makeText(this, "Compra realizada", Toast.LENGTH_SHORT).show()
            dialogo.dismiss()
        }

        botonNo.setOnClickListener {
            dialogo.dismiss()
        }

        dialogo.show()
    }

    fun atras(view: View){
        val intent = Intent(this, Iniciar_Sesion::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

}