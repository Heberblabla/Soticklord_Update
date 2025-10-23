package com.waos.soticklord

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import android.widget.TextView
import android.widget.ArrayAdapter
import android.widget.Spinner
import java.lang.reflect.Modifier
import java.lang.reflect.Method
import Data.*
import android.widget.Toast
import kotlinx.coroutines.*
import android.widget.AdapterView
import androidx.lifecycle.lifecycleScope
import android.graphics.Color
import android.graphics.drawable.ColorDrawable


class Batalla_oculta : AppCompatActivity() {
    var Enemigo_Seleccionado = 5
    var turno_activo = 5
    var ataqueSeleccionado: String = " "
    lateinit var imagenes: ArrayList<ImageView>
    lateinit var imagenes2: ArrayList<ImageView>
    var turno_enemigo = 5
    var es_mi_turno = true
    var es_turno_del_enemigo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_batalla_oculta)
        // Oculta las barras del sistema
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
        // Referencia al Spinner
        val spinnerAtaques: Spinner = findViewById(R.id.Ataques)
        // Listener del Spinner
        spinnerAtaques.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Guardamos el texto seleccionado en la variable
                ataqueSeleccionado = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Por si no selecciona nada , se asigan el ataque q todos tienen por defecto :v
                ataqueSeleccionado = "Ataque_normal"
            }
        }
        imagenes = arrayListOf(
            findViewById(R.id.Turno_TropaAa),
            findViewById(R.id.Turno_TropaBa),
            findViewById(R.id.Turno_TropaBb),
            findViewById(R.id.Turno_TropaCa),
            findViewById(R.id.Turno_TropaCb),
            findViewById(R.id.Turno_TropaCc)
        )
        imagenes2= arrayListOf(
            findViewById(R.id.enemigoFa),
            findViewById(R.id.enemigoEb),
            findViewById(R.id.enemigoEa),
            findViewById(R.id.enemigoDc),
            findViewById(R.id.enemigoDb),
            findViewById(R.id.enemigoDa)

        )
        visualizar_posicion()
        visualizar_posicion_enemiga(5)
        actualizar_datos()
        bucle_principal()
    }


    fun cambiarFuenteConFondo(items: List<String>) {
        val spinner = findViewById<Spinner>(R.id.Ataques)

        val adapter = ArrayAdapter(this, R.layout.spinner_item_madera, R.id.texto_item, items)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_madera)
        spinner.setPopupBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        spinner.adapter = adapter
    }



    fun pasar_turno_al_enemigo() {
        turno_activo = 5
        es_mi_turno = false
        lifecycleScope.launch { turno_del_enemigo() }
    }


    fun visualizar_posicion() {
        for (img in imagenes) {
            img.visibility = View.INVISIBLE
        }
        if (es_mi_turno){
            imagenes[turno_activo].visibility = View.VISIBLE}

    }
    fun visualizar_posicion_enemiga(posicion:Int) {
        for (img in imagenes2) {
            img.visibility = View.INVISIBLE
        }
        if(es_turno_del_enemigo){
            imagenes2[posicion].visibility = View.VISIBLE}

    }

    fun bucle_principal() {
        if (turno_activo !in 0..5) {
            pasar_turno_al_enemigo()
            return
        }

        val tropa = GlobalData.Jugador1.getOrNull(turno_activo)

        if (tropa?.estado_de_vida == true) {
            visualizar_posicion()
            Imagenes_claras()
            actualizar_datos()
            cargar_Espinner(tropa.nombre ?: "Sin nombre")
        } else {
            turno_activo--
            bucle_principal() // llamada recursiva segura
        }
    }


    suspend fun turno_del_enemigo() {
        actualizar_datos()
        if(GlobalData.Jugador1[0]!!.estado_de_vida ||
            GlobalData.Jugador1[1]!!.estado_de_vida ||
            GlobalData.Jugador1[2]!!.estado_de_vida ||
            GlobalData.Jugador1[3]!!.estado_de_vida ||
            GlobalData.Jugador1[4]!!.estado_de_vida ||
            GlobalData.Jugador1[5]!!.estado_de_vida
            ) {
            turno_activo = 5
            es_turno_del_enemigo = true
            visualizar_posicion()

            for (i in 5 downTo 0) {
                val tropa = GlobalData.Jugador2.getOrNull(i) // evita crash por índice inválido
                if (tropa != null && tropa.estado_de_vida) {
                    visualizar_posicion_enemiga(i)
                    delay(5000) // Espera sin bloquear la UI

                    val bot = Bot_Desiciones_aleatorio(this)
                    bot.Empezar_Analisis(i)
                    actualizar_datos()
                }
            }

            // Verificar si todas las tropas enemigas murieron
            val enemigos_vivos = GlobalData.Jugador2.filter { it?.estado_de_vida == true }
            if (enemigos_vivos.isEmpty()) {
                Toast.makeText(this, "¡Ganaste!", Toast.LENGTH_LONG).show()
                finish() // termina la Activity actual
                return
            }

            es_turno_del_enemigo = false
            es_mi_turno = true
            turno_activo = 5
            bucle_principal()
            visualizar_posicion_enemiga(5)
        }else{
            Toast.makeText(this, "Perdisdes", Toast.LENGTH_LONG).show()
            finish() // termina la Activity actual
            return
        }
    }

    fun atacar_pasarturno(view: View) {

        // Solo actuamos si es tu turno
        if (es_mi_turno) {
            if (turno_activo < 0 || turno_activo >= 6) {
                pasar_turno_al_enemigo()
                }
            }
            // Verificamos que la tropa actual esté viva
            val tropaActual = GlobalData.Jugador1.getOrNull(turno_activo)

            if (tropaActual != null && tropaActual.estado_de_vida) {
                Ejecutar_ataque(
                    GlobalData.Jugador1,
                    GlobalData.Jugador2,
                    turno_activo,
                    Enemigo_Seleccionado,
                    ataqueSeleccionado
                )
            }
            actualizar_datos()
            // Pasamos al siguiente turno
            turno_activo--

            // Si ya terminó el turno (índice fuera de rango), pasamos al enemigo
            if (turno_activo < 0 || turno_activo >= 6) {
                pasar_turno_al_enemigo()
                return // ❌ evita ejecutar bucle_principal con turno -1
            }

            // Si todavía quedan tropas, continúa el bucle normal
            bucle_principal()
        }


    //--------------------------

    //--------------------------

    fun actualizar_datos(){
        cargar_vida1()
        cargar_vida2()
        cargar_datos1()
        cargar_datos2()
    }
    fun cargar_datos1() {
        // Lista de los IDs de los ImageView en el mismo orden que los índices
        val idsImagenes = listOf(
            R.id.TropaAa,
            R.id.TropaBa,
            R.id.TropaBb,
            R.id.TropaCa,
            R.id.TropaCb,
            R.id.TropaCc
        )

        // Recorremos los índices del 0 al 5
        for (i in idsImagenes.indices) {
            val imageView = findViewById<ImageView>(idsImagenes[i])
            val tropa = GlobalData.Jugador1[i]!!.rutaviva
            if (GlobalData.Jugador1[i]!!.estado_de_vida) {
                // Si existe tropa en esa posición, se carga su imagen viva
                imageView.setImageResource(tropa)
            } else {
                // Si esta muerto se pone su muerte
                imageView.setImageResource(GlobalData.Jugador1[i]!!.rutamuerta)
            }
        }
    }
    fun cargar_datos2() {
        // IDs de las imágenes del Jugador2 en orden según el índice del array
        val idsImagenes = listOf(
            R.id.TropaFa, // índice 0
            R.id.TropaEb, // índice 1
            R.id.TropaEa, // índice 2
            R.id.TropaDc, // índice 3
            R.id.TropaDb, // índice 4
            R.id.TropaDa, // índice 5
        )

        for (i in idsImagenes.indices) {
            val imageView = findViewById<ImageView>(idsImagenes[i])
            val tropa = GlobalData.Jugador2[i]!!.rutaviva
            if (GlobalData.Jugador2[i]!!.estado_de_vida) {
                // Si existe tropa en esa posición, se carga su imagen viva
                imageView.setImageResource(tropa)
            } else {
                // Si esta muerto se pone su muerte
                imageView.setImageResource(GlobalData.Jugador2[i]!!.rutamuerta)
            }
            // Voltear horizontalmente para que mire hacia la izquierda
            imageView.scaleX = -1f
        }
    }
    //-------------------------
    //sirven para cargar como para actualizar vidas xd
    fun cargar_vida1() {
        // IDs de los TextView correspondientes
        val idsTextos = listOf(
            R.id.vidaAa, // 0
            R.id.vidaBa, // 1
            R.id.vidaBb, // 2
            R.id.vidaCa, // 3
            R.id.vidaCb, // 4
            R.id.vidaCc  // 5
        )

        // Recorremos los índices
        for (i in idsTextos.indices) {
            val textView = findViewById<TextView>(idsTextos[i])
            val vida = GlobalData.Jugador1[i]!!.vida
            if(vida >= 1){
                textView.text = "♡: $vida"
            }
            else{
                GlobalData.Jugador1[i]!!.estado_de_vida = false
                textView.text = "♡: ---"
            }
        }
    }
    fun cargar_vida2() {
        // IDs de los TextView correspondientes
        val idsTextos = listOf(
            R.id.vidaFa, // 0
            R.id.vidaEb, // 1
            R.id.vidaEa, // 2
            R.id.vidaDc, // 3
            R.id.vidaDb, // 4
            R.id.vidaDa  // 5
        )

        // Recorremos los índices
        // Recorremos los índices
        for (i in idsTextos.indices) {

            val textView = findViewById<TextView>(idsTextos[i])
            val vida = GlobalData.Jugador2[i]!!.vida
            if(vida >= 1){
                GlobalData.Jugador2[i]!!.estado_de_vida = true
                textView.text = "♡: $vida"
            }
            else{
                GlobalData.Jugador2[i]!!.estado_de_vida = false
                textView.text = "♡: ---"
            }


        }
    }
    fun cargar_Espinner(Nombre: String){
        if(es_mi_turno) {
            val lista = Obtener_Array_String("Data.$Nombre")
            actualizarSpinnerAtaques(lista)
            cambiarFuenteConFondo(lista)
        }
    }
    fun Obtener_Array_String(nombreClase: String): List<String> {
        return try {
            val clase = Class.forName(nombreClase)
            clase.declaredMethods
                .filter { Modifier.isPublic(it.modifiers) }
                .map { it.name }
                .filter { !it.startsWith("get") && !it.startsWith("set") }
                .filterNot {
                    it in listOf(
                        "toString", "equals", "hashCode",
                        "copyValueOf", "transform", "formatted", "intern",
                        "wait", "notify", "notifyAll", "getClass",
                        "clonar", "copyBase", "component1", "component2"
                    )
                }
        } catch (e: ClassNotFoundException) {
            println("No se encontró la clase: $nombreClase")
            emptyList()
        }
    }
    fun actualizarSpinnerAtaques(lista: List<String>) {
        val spinner = findViewById<Spinner>(R.id.Ataques)

        val adaptador = ArrayAdapter(
            spinner.context,
            android.R.layout.simple_spinner_item,
            lista
        )
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adaptador
    }
    fun Imagenes_claras(){
        val imagen1 = findViewById<ImageView>(R.id.TropaDa)
        val imagen2 = findViewById<ImageView>(R.id.TropaDb)
        val imagen3 = findViewById<ImageView>(R.id.TropaDc)
        val imagen4 = findViewById<ImageView>(R.id.TropaEa)
        val imagen5 = findViewById<ImageView>(R.id.TropaEb)
        val imagen6 = findViewById<ImageView>(R.id.TropaFa)
        imagen1.alpha = 1.0f
        imagen2.alpha = 1.0f
        imagen3.alpha = 1.0f
        imagen4.alpha = 1.0f
        imagen5.alpha = 1.0f
        imagen6.alpha = 1.0f
        Enemigo_Seleccionado = 5
    }
    fun seleccionar_tropaDa(view: View){
        Imagenes_claras()
        if(GlobalData.Jugador2[5]!!.estado_de_vida) {

            Enemigo_Seleccionado = 5
            val imagen = findViewById<ImageView>(R.id.TropaDa)
            imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente
        }
    }
    fun seleccionar_tropaDb(view: View){
        Imagenes_claras()
        if(GlobalData.Jugador2[4]!!.estado_de_vida) {

            Enemigo_Seleccionado = 4
            val imagen = findViewById<ImageView>(R.id.TropaDb)
            imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente
        }
    }
    fun seleccionar_tropaDc(view: View){
        Imagenes_claras()
        if(GlobalData.Jugador2[3]!!.estado_de_vida) {

            Enemigo_Seleccionado = 3
            val imagen = findViewById<ImageView>(R.id.TropaDc)
            imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente
        }
    }
    fun seleccionar_tropaEa(view: View){
        Imagenes_claras()
        if((GlobalData.Jugador2[2]!!.estado_de_vida && GlobalData.Jugador1[turno_activo]!!.aereo) ||(
                    !GlobalData.Jugador2[3]!!.estado_de_vida &&
                            !GlobalData.Jugador2[4]!!.estado_de_vida &&
                            !GlobalData.Jugador2[5]!!.estado_de_vida)) {

            Enemigo_Seleccionado = 2
            val imagen = findViewById<ImageView>(R.id.TropaEa)
            imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente
        }
    }
    fun seleccionar_tropaEb(view: View){
        Imagenes_claras()
        if((GlobalData.Jugador2[1]!!.estado_de_vida && GlobalData.Jugador1[turno_activo]!!.aereo) ||(
                    !GlobalData.Jugador2[3]!!.estado_de_vida &&
                            !GlobalData.Jugador2[4]!!.estado_de_vida &&
                            !GlobalData.Jugador2[5]!!.estado_de_vida)) {

            Enemigo_Seleccionado = 1
            val imagen = findViewById<ImageView>(R.id.TropaEb)
            imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente
        }
    }
    fun seleccionar_tropaFa(view: View){
        Imagenes_claras()
        if((GlobalData.Jugador2[0]!!.estado_de_vida && GlobalData.Jugador1[turno_activo]!!.aereo) ||(
                    !GlobalData.Jugador2[3]!!.estado_de_vida &&
                            !GlobalData.Jugador2[4]!!.estado_de_vida &&
                            !GlobalData.Jugador2[5]!!.estado_de_vida &&
                            !GlobalData.Jugador2[1]!!.estado_de_vida &&
                            !GlobalData.Jugador2[2]!!.estado_de_vida))
        {

            Enemigo_Seleccionado = 0
            val imagen = findViewById<ImageView>(R.id.TropaFa)
            imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente
        }
    }
    fun Ejecutar_ataque(
        jugador1: ArrayList<Tropa?>,
        jugador2: ArrayList<Tropa?>,
        posicion1: Int,
        posicion2: Int,
        nombreMetodo: String
    ) {
        try {
            // Obtener la tropa atacante y la clase de esa tropa
            val tropaAtacante = jugador1[posicion1] ?: return
            val clase = tropaAtacante::class.java
            val nombreClase = clase.simpleName

            println("Clase atacante: $nombreClase")

            // Buscar el método con el nombre exacto
            val metodo: Method? = clase.declaredMethods.find { m ->
                m.name == nombreMetodo
            }

            if (metodo != null) {
                println("Método encontrado: ${metodo.name}")

                // Invocar el método sobre la instancia de la tropa atacante
                // Le pasamos jugador2 y la posición del enemigo
                metodo.invoke(tropaAtacante, jugador2, posicion2)
            } else {
                println("No se encontró el método '$nombreMetodo' en la clase $nombreClase")
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }




}