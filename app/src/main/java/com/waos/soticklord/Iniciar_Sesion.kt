package com.waos.soticklord

import android.annotation.SuppressLint
// -> Sirve para usar la anotación @SuppressLint en el código.
//    Esta anotación le dice al compilador/IDE que ignore ciertas advertencias
//    específicas de "Lint" (el sistema de análisis de Android).
//    Ej: @SuppressLint("MissingInflatedId")

import android.content.Intent
// -> Permite crear "intenciones" (Intents), que son las que se usan
//    para abrir nuevas actividades, enviar datos entre pantallas o abrir apps externas.

import android.os.Bundle
// -> Se usa para manejar el "paquete" de datos que Android pasa al crear
//    o restaurar una Activity.
//    En onCreate(Bundle savedInstanceState) siempre está presente.

import android.view.View
// -> Representa cualquier vista de Android (botón, texto, layout, etc.).
//    Se usa como tipo genérico cuando no importa qué vista específica es.

import android.widget.Button
// -> Clase que representa un botón normal (con texto) en la interfaz de Android.

import android.widget.EditText
// -> Clase que representa una caja de texto donde el usuario puede escribir.

import android.widget.ImageButton
// -> Botón que en lugar de texto muestra una imagen (ícono, PNG, SVG, etc.).

import android.widget.Toast
// -> Permite mostrar mensajes emergentes cortos en pantalla
//    (ej: "Usuario incorrecto") sin necesidad de un AlertDialog.

import androidx.activity.enableEdgeToEdge
// -> Función para activar que la app use la pantalla completa
//    aprovechando el área debajo de la barra de estado y navegación.

import androidx.appcompat.app.AppCompatActivity
// -> Clase base para todas las actividades modernas de Android.
//    Te da compatibilidad hacia atrás y soporte para funciones nuevas.

import androidx.core.view.ViewCompat
// -> Utilidades para trabajar con vistas de forma más compatible con distintas versiones de Android.

import androidx.core.view.WindowCompat
// -> Sirve para controlar configuraciones de la ventana (fullscreen, ocultar barras del sistema, etc.).

import androidx.core.view.WindowInsetsCompat
// -> Permite manejar "insets", que son las áreas que ocupa el notch,
//    la barra de estado, la barra de navegación, etc.

import androidx.core.view.WindowInsetsControllerCompat
// -> Controlador que permite ocultar/mostrar las barras del sistema (status bar, nav bar) en forma compatible.

import okhttp3.*
// ⚡ ESTA ES LA DE BASE DE DATOS (red).
// -> OkHttp es la librería que usas para hacer peticiones HTTP/HTTPS.
//    En tu caso, es la que conecta tu app con **Supabase** (que maneja la base de datos).
//    A través de OkHttp puedes hacer consultas, inserciones, actualizaciones, etc.

import org.json.JSONArray
// -> Clase que representa un arreglo JSON (ej: [{"id":1,"nombre":"Heber"}]).
//    Sirve para leer y parsear la respuesta que devuelve Supabase.

import java.io.IOException
// -> Excepción que se lanza cuando ocurre un error de entrada/salida (I/O),
//    como fallos de conexión al servidor (cuando usas OkHttp).


class Iniciar_Sesion : AppCompatActivity() {
    private lateinit var edit_nombre: EditText
    private lateinit var edit_password: EditText

    var usuario: String = "zzz"
    var password: String = "123"
    private val client = OkHttpClient()
    private val supabaseUrl = "https://zropeiibzqefzjrkdzzp.supabase.co"
    private val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inpyb3BlaWlienFlZnpqcmtkenpwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTkwMTc1NDYsImV4cCI6MjA3NDU5MzU0Nn0.ZJWqkOAbTul-RwIQrirajUSVdyI1w9Kh3kjek0vFMw8" //  key pública

    @SuppressLint("MissingInflatedId")
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

        val editNombre = findViewById<EditText>(R.id.edit_nombre)
        val editPassword = findViewById<EditText>(R.id.edit_password)
        val btnimagen = findViewById<ImageButton>(R.id.Iniciarwaza)
        val btnRegistrar = findViewById<Button>(R.id.Registrarse)


        btnimagen.setOnClickListener {
            usuario = editNombre.text.toString()
            val password = editPassword.text.toString()
            validarUsuario(usuario, password)
        }

        btnRegistrar.setOnClickListener {
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }



    }


    private fun validarUsuario(nombre: String, password: String) {
        // primero me fijo si el user dejó vacío algún campo
        if (nombre.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa los campos", Toast.LENGTH_SHORT).show() // le digo q ponga sus datos
            return // y ya no sigo porque no tiene sentido :v
        }

        // armo la URL para consultar a la Base de Datos (supabase) con el user y pass q puse arriba
        val url = "$supabaseUrl/rest/v1/jugadores?nombre_usuario=eq.$nombre&contrasena=eq.$password"

        // preparo la petición HTTP con headers
        val request = Request.Builder()
            .url(url)
            .addHeader("apikey", apiKey)
            .addHeader("Authorization", "Bearer $apiKey")
            .build()

        // lanzo la petición
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // si falla el request, o sea, no llega ni a supabase
                runOnUiThread {
                    Toast.makeText(
                        this@Iniciar_Sesion,
                        "Error de conexión: ${e.message}", // lanzo un mensaje q dice error de conexion
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                runOnUiThread {
                    //si stodo salió piola y me devolvió algo con nombre usuario
                    if (response.isSuccessful && body != null) {
                        val jsonArray = JSONArray(body)        // el body es un array JSON
                        if (jsonArray.length() > 0) {
                            val obj = jsonArray.getJSONObject(0)  // primer jugador
                            val idJugador = obj.getInt("id_jugador")

                            val intent = Intent(this@Iniciar_Sesion, Perfil::class.java)
                            intent.putExtra("ID_JUGADOR", idJugador) // paso solo el ID
                            startActivity(intent)
                            finish()
                        }
                    }
                    else {
                        // si no encontró nada → usuario o pass mal
                        Toast.makeText(
                            this@Iniciar_Sesion,
                            "Usuario o contraseña incorrectos",
                            Toast.LENGTH_SHORT
                            //lanza un mensaje emergente
                        ).show()
                    }
                }
            }
        })
    }


    fun cargar_datos(view: View){
        usuario = edit_nombre.text.toString()
        password = edit_password.text.toString()
        validarUsuario(usuario, password)
    }

}
