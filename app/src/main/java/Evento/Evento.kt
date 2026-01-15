package Evento

import Data.Especiales.Rey_Vago_de_Vagos
import Data.Especiales.Reyna_Shantal
import Data.Personalizados.Rey_Bufon_Negro
import Data.Tropa
import Data.Tropa_Gigante
import Data.Tropas_personalizadas.Tropa_Bufon
import Data.Tropas_personalizadas.Tropa_Curandera
import Data.Tropas_personalizadas.Tropa_Gigante_estelar
import Data.Tropas_personalizadas.Tropa_Lanzatonio_Medieval
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.waos.soticklord.R
import com.waos.soticklord.*
import com.waos.soticklord.Iniciar_Sesion
import kotlinx.coroutines.launch

class Evento : AppCompatActivity() {

    var posicion = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_evento)

        //pantalla completa
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

        lifecycleScope.launch {
            DataManager.puntaje()
        }
        DataManager.guardarDatos(this)
        cargar_imagenes()
        GlobalData.evento_activo = false
        mostrarPopupEvento()



    }

    fun verificar_si_gane(view: View) {
        mostrarPopupEvento()
    }

    fun jugarxd(view: View) {
        GlobalData.evento_activo = true
        val intent = Intent(this, Principal::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    fun cargar_imagenes() {
        DataManager.ver_evento()
        var textoRecibido = GlobalData.datos_evento
        val data = textoRecibido.trim()
        val partes = data.split("#")

        val titulo =
            partes[0].replace("\\n", "").replace("\n", "").replace("\\", "").replace("\"", "")
                .replace("n\"", "").trim()
        val descripcion = partes[1]
        val rey = DataManager.crearTropaPorNombre_2(partes[2], 50)
        val tropa1 = DataManager.crearTropaPorNombre_2(partes[3], 50)
        val tropa2 = DataManager.crearTropaPorNombre_2(partes[4], 50)
        val tropa3 = DataManager.crearTropaPorNombre_2(partes[5], 50)
        val tropa4 = DataManager.crearTropaPorNombre_2(partes[6], 50)
        val tropa5 = DataManager.crearTropaPorNombre_2(partes[7], 50)
        val finalizacion = partes[8]

        lifecycleScope.launch {
            var xd = DataManager.Consultar_premio_evento()
            println("MMMHHH : $xd")
            if (xd == "NO_RESULTADO#0#0#0#0" || xd == "ERROR_SQL#0#0#0#0") {
                    var mensaje = findViewById<ImageButton>(R.id.boton_mensaje)
                    mensaje.visibility = View.GONE

            }
        }


        GlobalData.Jugador2[0] = rey
        GlobalData.Jugador2[1] = tropa1
        GlobalData.Jugador2[2] = tropa2
        GlobalData.Jugador2[3] = tropa3
        GlobalData.Jugador2[4] = tropa4
        GlobalData.Jugador2[5] = tropa5

        val imagen0 = findViewById<ImageView>(R.id.imagen_Rey_waos)
        imagen0.setImageResource(GlobalData.Jugador2[0]!!.rutaviva)
        imagen0.scaleX = -1f
        val imagen1 = findViewById<ImageView>(R.id.arriba_enmedio)
        imagen1.setImageResource(GlobalData.Jugador2[1]!!.rutaviva)
        imagen1.scaleX = -1f
        val imagen2 = findViewById<ImageView>(R.id.abajo_enmedio)
        imagen2.setImageResource(GlobalData.Jugador2[2]!!.rutaviva)
        imagen2.scaleX = -1f
        val imagen3 = findViewById<ImageView>(R.id.arriba_delante)
        imagen3.setImageResource(GlobalData.Jugador2[3]!!.rutaviva)
        imagen3.scaleX = -1f
        val imagen4 = findViewById<ImageView>(R.id.centro_adelante)
        imagen4.setImageResource(GlobalData.Jugador2[4]!!.rutaviva)
        imagen4.scaleX = -1f
        val imagen5 = findViewById<ImageView>(R.id.abajo_adelante)
        imagen5.setImageResource(GlobalData.Jugador2[5]!!.rutaviva)
        imagen5.scaleX = -1f


        val info = findViewById<TextView>(R.id.Info_rey_avento)
        info.text = descripcion

        val tituloxd = findViewById<TextView>(R.id.titulo)
        tituloxd.text = titulo

        cargar_datos_evento(GlobalData.Jugador2[0]!!)

    }

    fun reclamar_mensaje(view: View){
        DataManager.ver_lo_q_gane(this) {
            lifecycleScope.launch {
                val xd = DataManager.Consultar_premio_evento()
                if (xd == "NO_RESULTADO#0#0#0#0" || xd == "ERROR_SQL#0#0#0#0") {

                        var mensaje = findViewById<ImageButton>(R.id.boton_mensaje)
                        mensaje.visibility = View.GONE

                }
            }
        }
    }


    fun siguiente_waos(view: View) {
        posicion += 1
        if (posicion == 1 || posicion == 2 || posicion == 3) {
            invocador()
        } else {
            posicion = 1
            invocador()
        }

    }

    fun anterior_waos(view: View) {
        posicion += 1
        if (posicion == 1 || posicion == 2 || posicion == 3) {
            invocador()
        } else {
            posicion = 3
            invocador()
        }
    }


    fun invocador() {
        if (posicion == 1) {
            cargar_datos_evento(GlobalData.Jugador2[0]!!)
        }
        if (posicion == 2) {
            cargar_datos_evento(GlobalData.Jugador2[2]!!)
        }
        if (posicion == 3) {
            cargar_datos_evento(GlobalData.Jugador2[5]!!)
        }

    }


    fun cargar_datos_evento(tropa: Tropa) {
        val plata = findViewById<TextView>(R.id.Monedas_Globales)
        plata.text = "S/. ${GlobalData.Moneda_Global}"

        val puntaje = findViewById<TextView>(R.id.puntaje)
        puntaje.text = "Puntaje. ${GlobalData.puntaje}"

        val info = findViewById<TextView>(R.id.Info_rey_avento)

        val info2 = findViewById<TextView>(R.id.Atributos_rey_evento)
        info2.text = tropa.toString()

    }

    fun waos(view: View) {

    }


    fun atras(view: View) {
        val intent = Intent(this, Escoger_modo::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    fun mostrarPopupEvento() {
        mostrarPopupConImagen(
            this,
            "\uD83D\uDCA5 ¡Desafío del Rey Semanal! \uD83D\uDCA5\n" +
                    "    Enfréntate al Rey Semanal y consigue el mayor puntaje posible.\n" +
                    "    cada tropa y rey derrotada suman puntos.\n" +
                    "    \n" +
                    "    \uD83C\uDFC6 Los mejores puntajes obtendrán recompensas.\n" +
                    "   \n" +
                    "    \n" +
                    "    ¿Hasta dónde podrás llegar?"
        ) {
            Toast.makeText(this, "Buena Suerte", Toast.LENGTH_SHORT).show()
        }
    }

    fun mostrarPopupConImagen(
        context: Context,
        mensaje: String,
        onAceptar: (() -> Unit)? = null
    ) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.popup_personalizado)
        dialog.setCancelable(false)

        val txtMensaje = dialog.findViewById<TextView>(R.id.txtMensaje)
        val btnAceptar = dialog.findViewById<ImageButton>(R.id.btnAceptar)

        txtMensaje.text = mensaje

        btnAceptar.setOnClickListener {
            dialog.dismiss()
            onAceptar?.invoke()
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

}