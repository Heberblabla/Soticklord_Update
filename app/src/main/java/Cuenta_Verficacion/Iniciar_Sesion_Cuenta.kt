package Cuenta_Verficacion

import Pruebas.generarHash
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
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
import com.waos.soticklord.R
import kotlinx.coroutines.launch



class Iniciar_Sesion_Cuenta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iniciar_sesion_cuenta)

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


    }


    fun iniciar_sesion(view: View) {
        val boton = view as ImageButton
        boton.isEnabled = false   // bloquear botón para evitar spam

        val botonatras = findViewById<ImageButton>(R.id.boton_atras11)
        botonatras.isEnabled = false


        val nombre = findViewById<EditText>(R.id.editTextText).text.toString()
        val password = findViewById<EditText>(R.id.editTextTextPassword).text.toString()

        lifecycleScope.launch {
            try {

                when (DataManager.Iniciar_sesion_en_la_nube(nombre, password,this@Iniciar_Sesion_Cuenta)) {
                    "inicio exitoso" -> {
                        GlobalData.contraseña_hash = generarHash(password)//se guarda la contraseña
                        DataManager.Cargar_datos_Desde_la_nube(GlobalData.datosJugador)//se cargan datos de recuros

                        DataManager.Cargar_reyes_de_nube()//se cargan los reyes
                        DataManager.Cargar_tropas_de_nube()//se cargas las tropas
                        println("de la nube : ${GlobalData.experiencia_pasada}")

                        DataManager.guardarDatos(this@Iniciar_Sesion_Cuenta)//se guarda internamente
                        println("s guardo internamente : ${GlobalData.experiencia_pasada}")

                        DataManager.cargarDatos(this@Iniciar_Sesion_Cuenta)//se vuelvea cargar ,como si ubiera entrado a la aplicaicon
                        println("se volvio a cargar : ${GlobalData.experiencia_pasada}")

                        val imagen = findViewById<ImageView>(R.id.imageView8)
                        val recurso = GlobalData.Diccionario_Perfiles[GlobalData.Perfil_id]
                        if (recurso != null) {
                            imagen.setImageResource(recurso)
                        } else {
                            imagen.setImageResource(R.drawable.perfil_0)
                        }
                        startActivity(Intent(this@Iniciar_Sesion_Cuenta, Cuenta_Principal::class.java))
                    }
                    else -> {
                        //Toast.makeText(this@Iniciar_Sesion_Cuenta, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
                    }
                }



            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this@Iniciar_Sesion_Cuenta,
                    "Error de conexión o datos corruptos",
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                boton.isEnabled = true
                botonatras.isEnabled = true
            }
        }
    }




    fun atras(view: View){
        val intent = Intent(this, Iniciar_Sesion::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}