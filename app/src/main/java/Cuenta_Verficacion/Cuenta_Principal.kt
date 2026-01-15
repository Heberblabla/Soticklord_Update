package Cuenta_Verficacion

import Archivos_Extra.GestorEventos
import Cuenta_Verficacion.Crear_Cuenta
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.waos.soticklord.DataManager
import com.waos.soticklord.GlobalData
import com.waos.soticklord.Iniciar_Sesion
import com.waos.soticklord.Mapa
import com.waos.soticklord.Pantalla_inicio_carga
import com.waos.soticklord.R
import kotlinx.coroutines.launch

class Cuenta_Principal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cuenta_principal)

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

        val hashesInvalidos = setOf("xxx", "waza2", "waza")

        val usuarioValido =
            !GlobalData.Nombre.isNullOrEmpty() &&
                    GlobalData.Nombre != "default" &&
                    GlobalData.contraseña_hash !in hashesInvalidos

        actualizarInterfaz(usuarioValido)


        val switchAnimaciones = findViewById<Switch>(R.id.animaciones)

        // 1. Mostrar el valor inicial en el switch
        switchAnimaciones.isChecked = GlobalData.Desea_nimaciones

        // 2. Escuchar cuando el usuario lo cambia
        switchAnimaciones.setOnCheckedChangeListener { _, isChecked ->
            GlobalData.Desea_nimaciones = isChecked
            DataManager.guardarDatos(this)
        }



    }

    fun actualizarInterfaz(isLoggedIn: Boolean) {
        val btn1 = findViewById<ImageButton>(R.id.Cargar_datos)
        val btn2 = findViewById<ImageButton>(R.id.Guarda_datos)
        val btn22 = findViewById<ImageButton>(R.id.guarda_datos)
        val btn222 = findViewById<EditText>(R.id.codigo)

        val btn3 = findViewById<ImageButton>(R.id.Iniciar_sesion)
        val btn4 = findViewById<ImageButton>(R.id.crear_cuenta)
        val texto1 = findViewById<TextView>(R.id.primer_aviso)
        val texto2 = findViewById<TextView>(R.id.primer_aviso2)

        if (isLoggedIn) {
            btn1.visibility = View.VISIBLE
            btn2.visibility = View.VISIBLE
            btn22.visibility = View.VISIBLE
            btn222.visibility = View.VISIBLE
            btn3.visibility = View.GONE
            btn4.visibility = View.GONE
            texto1.visibility = View.GONE
            texto2.visibility = View.GONE
            Rellenar_Datos_principales()
        } else {
            btn1.visibility = View.GONE
            btn2.visibility = View.GONE
            btn22.visibility = View.GONE
            btn222.visibility = View.GONE
            btn3.visibility = View.VISIBLE
            btn4.visibility = View.VISIBLE
            texto1.visibility = View.VISIBLE
            texto2.visibility = View.VISIBLE
        }
    }



    fun Rellenar_Datos_principales(){
        val nombre = findViewById<TextView>(R.id.nombre_txt)
        nombre.text = "Nombre: ${GlobalData.Nombre}"
        val nivel = findViewById<TextView>(R.id.nivel_txt)
        nivel.text = "Nive: ${GlobalData.nivel_De_cuenta}"
        val imagen = findViewById<ImageView>(R.id.perfil)
        val recurso = GlobalData.Diccionario_Perfiles[GlobalData.Perfil_id]
        if (recurso != null) {
            imagen.setImageResource(recurso)
        } else {
            imagen.setImageResource(R.drawable.perfil_0)
        }



    }


    fun Cerrar_sesion(view: View){
        DataManager.borrarDatos(this)
        GlobalData.se_cargo_datos_iniciales = false
        val intent = Intent(this, Pantalla_inicio_carga::class.java)
        startActivity(intent)
    }

    fun Guardar_Datos(view: View){
        lifecycleScope.launch {
            DataManager.general_guardar_progreso_nube(this@Cuenta_Principal,this@Cuenta_Principal) //guardamos datos en la nube
            Toast.makeText(this@Cuenta_Principal, "Datos Guardados exitosamente", Toast.LENGTH_SHORT).show()
        }
    }

    fun iniciar_sesion(view: View){
        val intent = Intent(this, Iniciar_Sesion_Cuenta::class.java)
        startActivity(intent)

    }

    fun crear_cuenta(view: View){

        if (GlobalData.nivel_de_progresion >= 0) {
            val intent = Intent(this, Crear_Cuenta::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(this@Cuenta_Principal, "Pasa el primer nivel del modo aventura!", Toast.LENGTH_SHORT).show()
        }



    }

    fun reclamar_codigo(view: View) {
        val editText = findViewById<EditText>(R.id.codigo)
        val codigo_ingresado = editText.text.toString()

        lifecycleScope.launch {
            var waos = DataManager.canjear_codigo(codigo_ingresado)
            println("XDDDDDDDDDDDDDDDD : $waos")
            var mensaje = DataManager.Porcesar_codigo(waos)

            DataManager.guardarDatos(this@Cuenta_Principal)
            mostrarPopupConImagen(
                this@Cuenta_Principal,
                "Mensaje del codigo:\n" +
                "$mensaje"
            ) {
                DataManager.general_guardar_progreso_nube(this@Cuenta_Principal,this@Cuenta_Principal)
            }

        }

        //Toast.makeText(this, "Obtuvistes + $waos monedas", Toast.LENGTH_SHORT).show()
        //GlobalData.monedas += waos
        //DataManager.guardarDatos(this)
    }

    fun atras(view: View){
        val intent = Intent(this, Iniciar_Sesion::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
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