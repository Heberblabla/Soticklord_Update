package Cuenta_Verficacion

import Tutorial.Dialogos
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
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

class Crear_Cuenta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_cuenta)

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

    fun crear_cuenta(view: View) {
        if (GlobalData.nivel_de_progresion >= 1) {
            val boton = view as ImageButton
            boton.isEnabled = false   // bloquear botón para evitar spam
            val botonatras = findViewById<ImageButton>(R.id.boton_atras12)
            botonatras.isEnabled = false


            val nombre = findViewById<EditText>(R.id.editTextText2).text.toString()
            val password = findViewById<EditText>(R.id.editTextTextPassword2).text.toString()

            lifecycleScope.launch {
                try {
                    //verficar si el nombre existe
                    var xd = DataManager.Crear_cuenta(nombre, password)
                    //verificamos
                    if (xd == "Jugador ya existe") {
                        Toast.makeText(
                            this@Crear_Cuenta,
                            "Nombe ya esta en Uso",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        GlobalData.monedas += 1000//damos recompensa
                        DataManager.general_guardar_progreso_nube(
                            this@Crear_Cuenta,
                            this@Crear_Cuenta
                        ) //guardamos datos en la nube
                        DataManager.guardarDatos(this@Crear_Cuenta)//guardamos datos internamente


                        val intent = Intent(this@Crear_Cuenta, Cuenta_Principal::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        this@Crear_Cuenta,
                        "Error de conexión o datos corruptos",
                        Toast.LENGTH_LONG
                    ).show()
                } finally {
                    boton.isEnabled = true
                    botonatras.isEnabled = true
                }
            }

        }else{
            Toast.makeText(
                this@Crear_Cuenta,
                "Pasa el primer nivel del modo aventura!!",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    fun perfil_1(view: View) {
        // 1️⃣ Actualizamos el ID del perfil seleccionado
        GlobalData.Perfil_id = "perfil_1"

        // 2️⃣ Obtenemos el ImageView del layout
        val imagen = findViewById<ImageView>(R.id.perfil2)

        // 3️⃣ Verificamos que exista el ImageView
        if (imagen == null) {

            return
        }

        // 4️⃣ Verificamos que el diccionario de perfiles exista e incluya el perfil
        val diccionario = GlobalData.Diccionario_Perfiles
        val recurso = diccionario?.get(GlobalData.Perfil_id)

        if (recurso != null) {
            // 5️⃣ Si encontramos el recurso en el diccionario, lo aplicamos
            imagen.setImageResource(recurso)
        } else {
            // 6️⃣ Si no existe, usamos un recurso por defecto

            imagen.setImageResource(R.drawable.perfil_0)
        }
    }


    fun perfil_2(view: View) {
        // 1️⃣ Actualizamos el ID del perfil seleccionado
        GlobalData.Perfil_id = "perfil_2"

        // 2️⃣ Obtenemos el ImageView del layout
        val imagen = findViewById<ImageView>(R.id.perfil2)

        // 3️⃣ Verificamos que exista el ImageView
        if (imagen == null) {

            return
        }

        // 4️⃣ Verificamos que el diccionario de perfiles exista e incluya el perfil
        val diccionario = GlobalData.Diccionario_Perfiles
        val recurso = diccionario?.get(GlobalData.Perfil_id)

        if (recurso != null) {
            // 5️⃣ Si encontramos el recurso en el diccionario, lo aplicamos
            imagen.setImageResource(recurso)
        } else {
            // 6️⃣ Si no existe, usamos un recurso por defecto

            imagen.setImageResource(R.drawable.perfil_0)
        }
    }


    fun perfil_3(view: View) {
        // 1️⃣ Actualizamos el ID del perfil seleccionado
        GlobalData.Perfil_id = "perfil_3"

        // 2️⃣ Obtenemos el ImageView del layout
        val imagen = findViewById<ImageView>(R.id.perfil2)

        // 3️⃣ Verificamos que exista el ImageView
        if (imagen == null) {

            return
        }

        // 4️⃣ Verificamos que el diccionario de perfiles exista e incluya el perfil
        val diccionario = GlobalData.Diccionario_Perfiles
        val recurso = diccionario?.get(GlobalData.Perfil_id)

        if (recurso != null) {
            // 5️⃣ Si encontramos el recurso en el diccionario, lo aplicamos
            imagen.setImageResource(recurso)
        } else {
            // 6️⃣ Si no existe, usamos un recurso por defecto

            imagen.setImageResource(R.drawable.perfil_0)
        }
    }


    fun atras(view: View) {
        val intent = Intent(this, Iniciar_Sesion::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }


}