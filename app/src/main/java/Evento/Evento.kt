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
import android.content.Intent
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
import com.waos.soticklord.R
import com.waos.soticklord.*

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

        var waos = DataManager.rellenar_campo_completo_evento()
        if(waos == 1){
            GlobalData.Se_paso_el_evento = true
        }else{
            if(waos == 0){
                GlobalData.Se_paso_el_evento = false
            }
        }
        DataManager.guardarDatos(this)
        cargar_imagenes()

        GlobalData.evento_activo = false
        mostrarPopupEvento()
    }

    fun verificar_si_gane(view: View){
        var premio = DataManager.reclamar_premio()
        if(premio == 0){
            Toast.makeText(this, "No llegastes a ser el Primero ", Toast.LENGTH_SHORT).show()
        }else{
            if (premio > 0){
                Toast.makeText(this, "Felicidades obtuvistes +S/.10", Toast.LENGTH_SHORT).show()
            }
        }
        GlobalData.Moneda_Global += premio
        DataManager.guardarDatos(this)
        DataManager.Actualizar_datos()
        invocador()

    }

    fun cargar_imagenes(){
        GlobalData.Jugador2[0] = Rey_Vago_de_Vagos(GlobalData.nivel_de_progresion + 70)
        GlobalData.Jugador2[1] = Tropa_Bufon(GlobalData.nivel_de_progresion + 45)
        GlobalData.Jugador2[2] = Tropa_Bufon(GlobalData.nivel_de_progresion + 45)
        GlobalData.Jugador2[3] = Tropa_Lanzatonio_Medieval(GlobalData.nivel_de_progresion + 30,1.00)
        GlobalData.Jugador2[4] = Tropa_Lanzatonio_Medieval(GlobalData.nivel_de_progresion + 30,1.00)
        GlobalData.Jugador2[5] = Tropa_Lanzatonio_Medieval(GlobalData.nivel_de_progresion + 30,1.00)

        val imagen0 = findViewById<ImageView>(R.id.imagen_Rey_waos)
        imagen0.setImageResource(GlobalData.Jugador2[0]!!.rutaviva)
        imagen0.scaleX  = -1f
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



        var texto = """
            Algo extraño avanza entre los
            fragmentos del multiverso. Tropas
            con defensa absoluta marchan sin
            detenerse, acompañadas por bufones
            capaces de devolver golpes con una
            suerte inquietante. En el centro de
            todo, una presencia inmóvil observa,
            protegida por una barrera que ningún
            ataque físico puede romper.
            Quien se atreva a entrar en este
            territorio deberá enfrentar a un
            ejército que no conoce el dolor ni
            el cansancio. Sólo los preparados
            sobrevivirán a esta muralla viviente.
        """.trimIndent()

        val info = findViewById<TextView>(R.id.Info_rey_avento)
        info.text = texto
        cargar_datos_evento(GlobalData.Jugador2[0]!!)

    }

    fun siguiente_waos(view: View){
        posicion += 1
        if(posicion == 1 || posicion == 2 || posicion == 3) {
            invocador()
        }else{
            posicion = 1
            invocador()
        }

    }

    fun anterior_waos(view: View){
        posicion += 1
        if(posicion == 1 || posicion == 2 || posicion == 3){
            invocador()
        }else{
            posicion = 3
            invocador()
        }
    }


    fun invocador(){
        if(posicion == 1){
            cargar_datos_evento(GlobalData.Jugador2[0]!!)
        }
        if(posicion == 2){
            cargar_datos_evento(GlobalData.Jugador2[2]!!)
        }
        if(posicion == 3){
            cargar_datos_evento(GlobalData.Jugador2[5]!!)
        }

    }


    fun cargar_datos_evento(tropa: Tropa){
        val plata = findViewById<TextView>(R.id.Monedas_Globales)
        plata.text = "S/. ${GlobalData.Moneda_Global}"
        val info = findViewById<TextView>(R.id.Info_rey_avento)

        val info2 = findViewById<TextView>(R.id.Atributos_rey_evento)
        info2.text = tropa.toString()

    }

    fun waos(view: View){

    }

    fun push(view: View){
        if(GlobalData.Se_paso_el_evento){
            val numero = DataManager.push(GlobalData.Nombre)
            if(numero == "0"){
                //jugador no registrado
                Toast.makeText(this, "Ahun no te registrastes , -> ve a configuraciones", Toast.LENGTH_SHORT).show()
            }
            if(numero == "01"){
                //ya pusheo
                Toast.makeText(this, "Ya Se subio tu resultado", Toast.LENGTH_SHORT).show()
            }
            if(numero == "1" ){
                //pusheo correcto
                Toast.makeText(this, "Resultado subido corretamente", Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(this, "Derrota al Jefe primero", Toast.LENGTH_SHORT).show()
        }
    }

    fun jugarxd(view: View){
        if(GlobalData.Se_paso_el_evento){
            Toast.makeText(this, "Ya Derrotastes al Jefe", Toast.LENGTH_SHORT).show()
        }else {
            var valor = DataManager.verficar_si_existe(GlobalData.Nombre)
            if(valor == 1){
                GlobalData.evento_activo = true
                val intent = Intent(this, Principal::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Guarda bien tu nombre en Configuraciones", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun atras(view: View) {
        val intent = Intent(this, Escoger_modo::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    fun mostrarPopupEvento() {
        val inflater = layoutInflater
        val vista = inflater.inflate(R.layout.popup_confirmar, null)

        val texto = vista.findViewById<TextView>(R.id.textoConfirmar)
        val botonSi = vista.findViewById<Button>(R.id.botonSi)
        val botonNo = vista.findViewById<Button>(R.id.botonNo)

        // Ajustar tamaño de la letra
        texto.textSize = 12f  // tamaño fijo en sp
        // O para que se ajuste automáticamente si el texto es largo:
        texto.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)

        // Texto del popup
        texto.text = """
        💥 ¡Desafío del Rey Semanal! 💥
        Derrota al Rey Semanal lo más rápido posible y tu hora de victoria 
        será enviada a la nube.  
        🏆 Si eres el primero, ganarás una recompensa de S/.10.  
        📈 ¡Alcanza los S/.100 y podrás reclamar tu premio!
                ¿Listo para demostrar quién es el mejor?            
    """.trimIndent()

        val dialogo = AlertDialog.Builder(this)
            .setView(vista)
            .setCancelable(false)
            .create()

        botonSi.setOnClickListener {
            Toast.makeText(
                this,
                "Presiona \"!\" para consultar el resultado y \"push\" para subir tu victoria.",
                Toast.LENGTH_LONG
            ).show()
            dialogo.dismiss()
        }

        botonNo.setOnClickListener {
            val intent = Intent(this, Escoger_modo::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
            dialogo.dismiss()
        }

        dialogo.show()
    }



}