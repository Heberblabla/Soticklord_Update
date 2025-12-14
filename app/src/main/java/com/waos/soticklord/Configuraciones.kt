package com.waos.soticklord

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class Configuraciones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_configuraciones)

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

        val editText = findViewById<EditText>(R.id.insertar_nombre)
        editText.setText("${GlobalData.Nombre}")
        val nombre = findViewById<TextView>(R.id.nombre)
        nombre.text = "Usuario: ${GlobalData.Nombre}"


        if (GlobalData.Nombre != "Default") {
            var valor = DataManager.verficar_si_existe(GlobalData.Nombre)
            if (valor == 1) {
                editText.isEnabled = false
                val boton = findViewById<ImageButton>(R.id.guardar_cambios)
                boton.isEnabled = false
                boton.visibility = View.GONE
            } else {
                Toast.makeText(this, "Nombre no encontrado en la base de datos", Toast.LENGTH_SHORT)
                    .show()
            }

        } else {
            //no pasa nada
        }

        val switchAnimaciones = findViewById<Switch>(R.id.Animaiciones)

        // 1. Mostrar el valor inicial en el switch
        switchAnimaciones.isChecked = GlobalData.Desea_nimaciones

        // 2. Escuchar cuando el usuario lo cambia
        switchAnimaciones.setOnCheckedChangeListener { _, isChecked ->
            GlobalData.Desea_nimaciones = isChecked
            DataManager.guardarDatos(this)
        }



    }


    fun reclamar_codigo(view: View){
        val editText = findViewById<EditText>(R.id.insertar_codigo)
        // 2. Obtener lo que el usuario escribió
        val nombreIngresado = editText.text.toString()

        var waos = DataManager.reclamar_codigo(nombreIngresado)
        Toast.makeText(this, "Obtuvistes + $waos monedas", Toast.LENGTH_SHORT).show()
        GlobalData.monedas += waos
        DataManager.guardarDatos(this)
    }

    // 1 exite
    // 0 no existe
    fun subir_nombre(view: View){
        // 1. Buscar el EditText
        val editText = findViewById<EditText>(R.id.insertar_nombre)
        // 2. Obtener lo que el usuario escribió
        val nombreIngresado = editText.text.toString()

        if(nombreIngresado != "Default" && nombreIngresado != "Name" && nombreIngresado != "default"){
            var valor = DataManager.verficar_si_existe(nombreIngresado)
            if(valor == 1){
                Toast.makeText(this, "Ya esta registrado", Toast.LENGTH_SHORT).show()
            }else{
                GlobalData.Nombre = nombreIngresado
                DataManager.subir_datos_nube()
                DataManager.guardarDatos(this)
                Toast.makeText(this, "Registro existoso", Toast.LENGTH_SHORT).show()
                editText.isEnabled = false
            }
        }else{
            Toast.makeText(this, "Nombre no valido", Toast.LENGTH_SHORT).show()
        }

    }

    fun atras(view: View) {
        val intent = Intent(this, Iniciar_Sesion::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

}