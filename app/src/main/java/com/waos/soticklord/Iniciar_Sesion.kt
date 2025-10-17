package com.waos.soticklord

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.content.Intent
import android.os.Bundle
import android.view.View
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


class Iniciar_Sesion : AppCompatActivity() {
    private lateinit var edit_nombre: EditText
    private lateinit var edit_password: EditText
    private lateinit var mediaPlayer: MediaPlayer
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
        inciar_musica()

        btnimagen.setOnClickListener {
            usuario = editNombre.text.toString()
            val password = editPassword.text.toString()
            val contraseña = crearHash(password)
            validarUsuario(usuario, contraseña)
        }

        btnRegistrar.setOnClickListener {
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }


    }


    private fun crearHash(password: String): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray(Charsets.UTF_8))

        return bytes.joinToString("") { "%02x".format(it) }
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

                            val intent = Intent(this@Iniciar_Sesion, Pantalla_de_Carga::class.java)
                            GlobalData.id_usuario = idJugador
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
