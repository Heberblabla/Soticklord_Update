package Multijugador

import Archivos_Extra.GestorAcciones
import Archivos_Extra.GestorEventos
import Data.Tropa
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.waos.soticklord.Bot_Desiciones_aleatorio
import com.waos.soticklord.DataManager
import com.waos.soticklord.Mapa
import com.waos.soticklord.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.net.ServerSocket
import java.net.Socket
import kotlin.jvm.java
import com.waos.soticklord.GlobalData
import com.waos.soticklord.Entorno
import com.waos.soticklord.EntornoManager


class Versus : AppCompatActivity() {

    var turno = 1

    private lateinit var mediaPlayer: MediaPlayer
    var Enemigo_Seleccionado = 5
    var turno_activo = 5
    var ataqueSeleccionado: String = " "
    lateinit var imagenes: ArrayList<ImageView>
    lateinit var imagenes2: ArrayList<ImageView>
    var turno_enemigo = 5
    lateinit var botonPasarTurno: ImageButton
    private lateinit var bannerView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_versus)
        EntornoManager.batalla = this
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

        botonPasarTurno = findViewById<ImageButton>(R.id.Atacarr)

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
                cargarinfo(ataqueSeleccionado)

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Por si no selecciona nada , se asigan el ataque q todos tienen por defecto :v
                ataqueSeleccionado = "Ataque_normal"
                cargarinfo(ataqueSeleccionado)
            }

        }

        // 1 Inicializa el SDK de AdMob
        MobileAds.initialize(this) {}
        // 2 Conecta tu banner del XML
        bannerView = findViewById(R.id.bannerView)
        // 3 Crea una solicitud de anuncio
        val adRequest = AdRequest.Builder().build()
        // 4️ Carga el anuncio
        bannerView.loadAd(adRequest)

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
        GlobalData.batalla = this
        GlobalData.initSonidos(this)
        visualizar_posicion()
        actualizar_datos()
        bucle_principal()
        Inicializar()

    }


    //empezar musica
    fun Inicializar(){
        mediaPlayer = MediaPlayer.create(this, R.raw.batalla)
        mediaPlayer.isLooping = true  // Para que se repita
        mediaPlayer.start()           // Reproduce al abrir la ventana
    }

    //cuando se destruye el activty se va la musica
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release() // Libera memoria al cerrar la Activity
    }

    //informacion de ataques
    fun cargarinfo(nombre: String){
        val descripcion = GlobalData.Diccionario_Ataques[nombre] ?: "Descripción no encontrada"
        val Info_ataques = findViewById<TextView>(R.id.info)
        Info_ataques.text = descripcion
    }

    //Crear diseño del espinner
    fun cambiarFuenteConFondo(items: List<String>) {
        val spinner = findViewById<Spinner>(R.id.Ataques)

        val adapter = ArrayAdapter(this, R.layout.spinner_item_madera, R.id.texto_item, items)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_madera)
        spinner.setPopupBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        spinner.adapter = adapter
    }


    //ver de que tropa es turno
    fun visualizar_posicion() {
        for (img in imagenes) {
            img.visibility = View.INVISIBLE
        }
        imagenes[turno_activo].visibility = View.VISIBLE
    }


    fun Reiniciar_turno() {
        // 1. Verificar victoria antes de cambiar de turno
        if (verificarFinDePartida()) return

        // 2. Cambiar jugador
        turno = if (turno == 1) 2 else 1



        // 3. Resetear índice (asumiendo que vas de la tropa 5 a la 0)
        turno_activo = 5

        // 4. Reactivar tropas
        val jugadorActual = if (turno == 1) GlobalData.Jugador1 else GlobalData.Jugador2
        jugadorActual.forEach { tropa ->
            tropa?.turnoActivo = true
        }

        // 5. Procesar eventos (veneno, regeneración, etc.)
        GestorEventos.procesarTodos(GlobalData.batalla)

        // 6. Actualizar UI
        actualizar_datos()

        // 7. IMPORTANTE: No llamar recursivamente a bucle_principal() ciegamente.
        // Llamar solo para buscar la primera tropa disponible del nuevo jugador.
        buscar_siguiente_tropa_viva()
    }

    fun bucle_principal() {
        // Este método ahora solo debería encargarse de la lógica visual inicial o de entrada
        GestorAcciones.Procesar()
        EntornoManager.aplicarEfecto()
        actualizar_datos()
        buscar_siguiente_tropa_viva()
    }

    fun buscar_siguiente_tropa_viva() {
        // Obtiene el jugador actual
        val jugadorActual = if (turno == 1) GlobalData.Jugador1 else GlobalData.Jugador2

        // Buscamos una tropa viva desde la posición actual hacia abajo
        while (turno_activo >= 0) {
            val tropa = jugadorActual.getOrNull(turno_activo)

            if (tropa != null && tropa.estado_de_vida && tropa.turnoActivo) {
                // ¡Encontramos tropa!
                visualizar_posicion()
                Imagenes_claras() // Asumo que esto resalta la tropa
                actualizar_datos()
                cargar_Espinner(tropa.nombre ?: "Sin nombre")

                // Si es turno de la IA (ejemplo: Jugador 2), aquí deberías llamar a su lógica
                // if (turno == 2) ejecutarIA()

                return
            }
            // Si no sirve, pasamos a la siguiente
            turno_activo--

        }

        // Si salimos del while, significa que este jugador no tiene más tropas por mover
        Reiniciar_turno()
        intercambiarSiUnoTieneImagen()
    }

    fun Turno_jugador_atacar(view: View) {
        var waos = false
        val (atacante, defensor) =
            if (turno == 1){
                waos = true
                GlobalData.Jugador1 to GlobalData.Jugador2}
            else{
                waos = false
                GlobalData.Jugador2 to GlobalData.Jugador1}


        val tropaActual = atacante.getOrNull(turno_activo)

        if (tropaActual != null && tropaActual.estado_de_vida && tropaActual.turnoActivo) {

            // Ejecutar ataque
            Ejecutar_ataque(atacante, defensor, turno_activo, Enemigo_Seleccionado, ataqueSeleccionado, waos)

            if (!tropaActual.turnoDoble) {
                tropaActual.turnoActivo = false
                turno_activo-- // Preparamos para buscar la siguiente
            } else {
                tropaActual.turnoDoble = false
                // No bajamos el turno_activo, vuelve a atacar la misma
            }

            actualizar_datos()

            // Verificamos si alguien murió y ganó
            if (verificarFinDePartida()) return

            // Buscamos quién sigue
            buscar_siguiente_tropa_viva()
        }
    }



    //--------------------------

    fun verificarFinDePartida(): Boolean {
        val jugador1Vivo = GlobalData.Jugador1.any { it?.estado_de_vida == true }
        val jugador2Vivo = GlobalData.Jugador2.any { it?.estado_de_vida == true }

        when {
            !jugador1Vivo && !jugador2Vivo -> {
                // Empate (poco probable pero posible)
                mostrarResultado("¡Empate!")
                return true
            }
            !jugador1Vivo -> {
                // Jugador 2 gana
                mostrarResultado("¡Jugador 2 Gana!")
                return true
            }
            !jugador2Vivo -> {
                // Jugador 1 gana
                mostrarResultado("¡Jugador 1 Gana!")
                return true
            }
        }
        return false
    }

    fun mostrarResultado(mensaje: String) {
        // Detiene la música
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }

        // Muestra el resultado
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()

        // Opcional: Navegar a pantalla de resultados después de un delay
        lifecycleScope.launch {
            delay(2000)
            // val intent = Intent(this@Versus, PantallaResultados::class.java)
            // intent.putExtra("ganador", mensaje)
            // startActivity(intent)
            finish() // Cierra la actividad actual
        }
    }

    //--------------------------

    fun actualizar_datos(){
        cargar_datos()

        if(turno == 1){
            actualizarVidas()
        } else {

            actualizarVidas2()
        }


    }


    fun intercambiarSiUnoTieneImagen() {
        val imgIzq = findViewById<ImageView>(R.id.Imagen_izquierda)
        val imgDer = findViewById<ImageView>(R.id.imagen_derecha)

        val drawableIzq = imgIzq.drawable
        val drawableDer = imgDer.drawable

        val nada = ContextCompat.getDrawable(this, R.drawable.nada)

        val izqEsNada = drawableIzq?.constantState == nada?.constantState
        val derEsNada = drawableDer?.constantState == nada?.constantState

        // Si ambos son "nada" → no intercambiar
        if (izqEsNada && derEsNada) return

        // --- HACER EL INTERCAMBIO ---
        imgIzq.setImageDrawable(drawableDer)
        imgDer.setImageDrawable(drawableIzq)

        // --- VOLTEAR PARA CONFIRMAR VISUALMENTE ---
        imgIzq.scaleX = imgIzq.scaleX * +1
        imgDer.scaleX = imgDer.scaleX * -1
    }


    fun cargarDatos(jugador: ArrayList<Tropa?>, ids: List<Int>, invertir: Boolean) {
        for (i in ids.indices) {
            val imageView = findViewById<ImageView>(ids[i])

            val tropa = jugador[i]!!
            val imagen = if (tropa.vida >= 1 && tropa.estado_de_vida)
                tropa.rutaviva
            else
                tropa.rutamuerta

            imageView.setImageResource(imagen)

            // Voltear si es jugador que mira a la izquierda
            imageView.scaleX = if (invertir) -1f else 1f
        }
    }


    fun cargar_datos() {
        if (turno == 1) {
            cargarDatos(
                GlobalData.Jugador1,
                listOf(R.id.TropaAa, R.id.TropaBa, R.id.TropaBb, R.id.TropaCa, R.id.TropaCb, R.id.TropaCc),
                false
            )
            cargarDatos(
                GlobalData.Jugador2,
                listOf(R.id.TropaFa, R.id.TropaEb, R.id.TropaEa, R.id.TropaDc, R.id.TropaDb, R.id.TropaDa),
                true
            )
        } else {
            cargarDatos(
                GlobalData.Jugador2,
                listOf(R.id.TropaAa, R.id.TropaBa, R.id.TropaBb, R.id.TropaCa, R.id.TropaCb, R.id.TropaCc),
                false
            )
            cargarDatos(
                GlobalData.Jugador1,
                listOf(R.id.TropaFa, R.id.TropaEb, R.id.TropaEa, R.id.TropaDc, R.id.TropaDb, R.id.TropaDa),
                true
            )
        }
    }
    fun cargarVidas(jugador: ArrayList<Tropa?>, ids: List<Int>) {
        for (i in ids.indices) {
            val textView = findViewById<TextView>(ids[i])
            val tropa = jugador[i]!!

            if (tropa.vida >= 1 && tropa.estado_de_vida) {
                textView.text = "♡: ${tropa.vida}"
            } else {
                tropa.estado_de_vida = false
                textView.text = "♡: ---"
            }
        }
    }

    fun actualizarVidas() {
        cargarVidas(GlobalData.Jugador1, listOf(R.id.vidaAa, R.id.vidaBa, R.id.vidaBb, R.id.vidaCa, R.id.vidaCb, R.id.vidaCc))
        cargarVidas(GlobalData.Jugador2, listOf(R.id.vidaFa, R.id.vidaEb, R.id.vidaEa, R.id.vidaDc, R.id.vidaDb, R.id.vidaDa))
    }

    fun actualizarVidas2() {
        cargarVidas(GlobalData.Jugador2, listOf(R.id.vidaAa, R.id.vidaBa, R.id.vidaBb, R.id.vidaCa, R.id.vidaCb, R.id.vidaCc))
        cargarVidas(GlobalData.Jugador1, listOf(R.id.vidaFa, R.id.vidaEb, R.id.vidaEa, R.id.vidaDc, R.id.vidaDb, R.id.vidaDa))
    }

    fun cargar_Espinner(nombreClase: String) {
            // Buscar la clase directamente en el diccionario
            val lista = Obtener_Array_String(nombreClase)
            actualizarSpinnerAtaques(lista)
            cambiarFuenteConFondo(lista)

    }


    fun Obtener_Array_String(nombreClase: String): List<String> {
        return try {
            val claseKotlin = GlobalData.Diccionario_Clases[nombreClase]
            println(claseKotlin)
            if (claseKotlin != null) {
                claseKotlin.java.declaredMethods
                    .filter { Modifier.isPublic(it.modifiers) }
                    .filter { !it.name.contains("$") } //  quita los $r8$lambda
                    .onEach { println(" Método público encontrado: ${it.name}") }
                    .map { it.name }
                    .filter { !it.startsWith("get") && !it.startsWith("set") }
                    .filterNot {
                        it in listOf(
                            "toString", "equals", "hashCode",
                            "copyValueOf", "transform", "formatted", "intern",
                            "wait", "notify", "notifyAll", "getClass",
                            "clonar", "copyBase", "reproducirVideoAtaque",
                            "Ataque_normall", "Recivir_daño",
                            "component1", "component2","Habilidad_Especial","Recivir_daño","efectuardaño"
                        )
                    }
                    .onEach { println("Método válido agregado: $it") }
            } else {
                println("No se encontró la clase '$nombreClase' en el diccionario.")
                emptyList()
            }
        } catch (e: Exception) {
            println("Error al obtener métodos de '$nombreClase': ${e.message}")
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
        if(turno ==1) {
            Imagenes_claras()
            if (GlobalData.Jugador2[5]!!.estado_de_vida) {

                Enemigo_Seleccionado = 5
                val imagen = findViewById<ImageView>(R.id.TropaDa)
                imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente
            }
        }
        if(turno ==2) {
            Imagenes_claras()
            if (GlobalData.Jugador1[5]!!.estado_de_vida) {

                Enemigo_Seleccionado = 5
                val imagen = findViewById<ImageView>(R.id.TropaDa)
                imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente
            }
        }
    }
    fun seleccionar_tropaDb(view: View){
        if(turno ==1) {
            Imagenes_claras()
            if (GlobalData.Jugador2[4]!!.estado_de_vida) {

                Enemigo_Seleccionado = 4
                val imagen = findViewById<ImageView>(R.id.TropaDb)
                imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente
            }
        }
        if(turno ==2) {
            Imagenes_claras()
            if (GlobalData.Jugador1[4]!!.estado_de_vida) {

                Enemigo_Seleccionado = 4
                val imagen = findViewById<ImageView>(R.id.TropaDb)
                imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente
            }
        }
    }
    fun seleccionar_tropaDc(view: View){
        if(turno ==1) {
            Imagenes_claras()
            if (GlobalData.Jugador2[3]!!.estado_de_vida) {

                Enemigo_Seleccionado = 3
                val imagen = findViewById<ImageView>(R.id.TropaDc)
                imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente
            }
        }
        if(turno ==2) {
            Imagenes_claras()
            if (GlobalData.Jugador1[3]!!.estado_de_vida) {

                Enemigo_Seleccionado = 3
                val imagen = findViewById<ImageView>(R.id.TropaDc)
                imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente
            }
        }
    }
    fun seleccionar_tropaEa(view: View){
        if(turno == 1) {
            Imagenes_claras()
            if ((GlobalData.Jugador2[2]!!.estado_de_vida && GlobalData.Jugador1[turno_activo]!!.aereo) || (
                        !GlobalData.Jugador2[3]!!.estado_de_vida &&
                                !GlobalData.Jugador2[4]!!.estado_de_vida &&
                                !GlobalData.Jugador2[5]!!.estado_de_vida)
            ) {

                Enemigo_Seleccionado = 2
                val imagen = findViewById<ImageView>(R.id.TropaEa)
                imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente
            }
        }
        if(turno == 2) {
            Imagenes_claras()
            if ((GlobalData.Jugador1[2]!!.estado_de_vida && GlobalData.Jugador2[turno_activo]!!.aereo) || (
                        !GlobalData.Jugador1[3]!!.estado_de_vida &&
                                !GlobalData.Jugador1[4]!!.estado_de_vida &&
                                !GlobalData.Jugador1[5]!!.estado_de_vida)
            ) {

                Enemigo_Seleccionado = 2
                val imagen = findViewById<ImageView>(R.id.TropaEa)
                imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente
            }
        }
    }
    fun seleccionar_tropaEb(view: View){
        if(turno == 1) {
            Imagenes_claras()
            if ((GlobalData.Jugador2[1]!!.estado_de_vida && GlobalData.Jugador1[turno_activo]!!.aereo) || (
                        !GlobalData.Jugador2[3]!!.estado_de_vida &&
                                !GlobalData.Jugador2[4]!!.estado_de_vida &&
                                !GlobalData.Jugador2[5]!!.estado_de_vida)
            ) {

                Enemigo_Seleccionado = 1
                val imagen = findViewById<ImageView>(R.id.TropaEb)
                imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente
            }
        }
        if(turno == 2) {
            Imagenes_claras()
            if ((GlobalData.Jugador1[1]!!.estado_de_vida && GlobalData.Jugador2[turno_activo]!!.aereo) || (
                        !GlobalData.Jugador1[3]!!.estado_de_vida &&
                                !GlobalData.Jugador1[4]!!.estado_de_vida &&
                                !GlobalData.Jugador1[5]!!.estado_de_vida)
            ) {

                Enemigo_Seleccionado = 1
                val imagen = findViewById<ImageView>(R.id.TropaEb)
                imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente
            }
        }
    }
    fun seleccionar_tropaFa(view: View){
        if(turno == 1) {
            Imagenes_claras()
            if ((GlobalData.Jugador2[0]!!.estado_de_vida && GlobalData.Jugador1[turno_activo]!!.aereo) || (
                        !GlobalData.Jugador2[3]!!.estado_de_vida &&
                                !GlobalData.Jugador2[4]!!.estado_de_vida &&
                                !GlobalData.Jugador2[5]!!.estado_de_vida &&
                                !GlobalData.Jugador2[1]!!.estado_de_vida &&
                                !GlobalData.Jugador2[2]!!.estado_de_vida)
            ) {

                Enemigo_Seleccionado = 0
                val imagen = findViewById<ImageView>(R.id.TropaFa)
                imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente
            }
        }
        if(turno == 2) {
            Imagenes_claras()
            if ((GlobalData.Jugador1[0]!!.estado_de_vida && GlobalData.Jugador2[turno_activo]!!.aereo) || (
                        !GlobalData.Jugador1[3]!!.estado_de_vida &&
                                !GlobalData.Jugador1[4]!!.estado_de_vida &&
                                !GlobalData.Jugador1[5]!!.estado_de_vida &&
                                !GlobalData.Jugador1[1]!!.estado_de_vida &&
                                !GlobalData.Jugador1[2]!!.estado_de_vida)
            ) {

                Enemigo_Seleccionado = 0
                val imagen = findViewById<ImageView>(R.id.TropaFa)
                imagen.alpha = 0.5f  // 1.0 = totalmente visible, 0.0 = totalmente transparente
            }
        }
    }



    fun Ejecutar_ataque(
        jugador1: ArrayList<Tropa?>,
        jugador2: ArrayList<Tropa?>,
        posicion1: Int,
        posicion2: Int,
        nombreMetodo: String,
        tipo_jugador: Boolean
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


                metodo.invoke(tropaAtacante, jugador2, posicion2,tipo_jugador)


            } else {
                println("No se encontró el método '$nombreMetodo' en la clase $nombreClase")
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        GlobalData.tropa_seleccionada_posicion = posicion2
        GlobalData.A_quien = true
        animacion_ataque()

    }



    fun animacion_ataque(){
        val overlay = ContextCompat.getDrawable(this, R.drawable.efecto_golpe)

        if(!GlobalData.A_quien){
            if (GlobalData.Atodos) {
                // Aplica a todas las tropas del jugador 1
                for (i in 0 until GlobalData.Jugador1.size) {
                    val idView = when (i) {
                        0 -> R.id.TropaAa
                        1 -> R.id.TropaBa
                        2 -> R.id.TropaBb
                        3 -> R.id.TropaCa
                        4 -> R.id.TropaCb
                        5 -> R.id.TropaCc
                        else -> null
                    }
                    idView?.let {
                        val imagenCentral = findViewById<ImageView>(it)
                        val imagen = GlobalData.Jugador1[i]!!.rutaviva
                        val base =
                            ContextCompat.getDrawable(this, imagen)?.constantState?.newDrawable()
                                ?.mutate()
                        Haceranimacion(overlay, imagen, base, imagenCentral)
                    }
                }
                GlobalData.Atodos = false
                return
            }

            if(GlobalData.tropa_seleccionada_posicion == 0 ){
                val imagenCentral = findViewById<ImageView>(R.id.TropaAa)
                var imagen = GlobalData.Jugador1[0]!!.rutaviva
                val base = ContextCompat.getDrawable(this, imagen)?.constantState?.newDrawable()?.mutate()
                Haceranimacion(overlay,imagen,base,imagenCentral)
            }
            if(GlobalData.tropa_seleccionada_posicion == 1 ){
                val imagenCentral = findViewById<ImageView>(R.id.TropaBa)
                var imagen = GlobalData.Jugador1[1]!!.rutaviva
                val base = ContextCompat.getDrawable(this, imagen)?.constantState?.newDrawable()?.mutate()
                Haceranimacion(overlay,imagen,base,imagenCentral)
            }
            if(GlobalData.tropa_seleccionada_posicion == 2 ){
                val imagenCentral = findViewById<ImageView>(R.id.TropaBb)
                var imagen = GlobalData.Jugador1[2]!!.rutaviva
                val base = ContextCompat.getDrawable(this, imagen)?.constantState?.newDrawable()?.mutate()
                Haceranimacion(overlay,imagen,base,imagenCentral)
            }
            if(GlobalData.tropa_seleccionada_posicion == 3 ){
                val imagenCentral = findViewById<ImageView>(R.id.TropaCa)
                var imagen = GlobalData.Jugador1[3]!!.rutaviva
                val base = ContextCompat.getDrawable(this, imagen)?.constantState?.newDrawable()?.mutate()
                Haceranimacion(overlay,imagen,base,imagenCentral)
            }
            if(GlobalData.tropa_seleccionada_posicion == 4 ){
                val imagenCentral = findViewById<ImageView>(R.id.TropaCb)
                var imagen = GlobalData.Jugador1[4]!!.rutaviva
                val base = ContextCompat.getDrawable(this, imagen)?.constantState?.newDrawable()?.mutate()
                Haceranimacion(overlay,imagen,base,imagenCentral)
            }
            if(GlobalData.tropa_seleccionada_posicion == 5 ){
                val imagenCentral = findViewById<ImageView>(R.id.TropaCc)
                var imagen = GlobalData.Jugador1[5]!!.rutaviva
                val base = ContextCompat.getDrawable(this, imagen)?.constantState?.newDrawable()?.mutate()
                Haceranimacion(overlay,imagen,base,imagenCentral)
            }

        }
        if(GlobalData.A_quien){
            if (GlobalData.Atodos) {
                // Aplica a todas las tropas del jugador 1
                for (i in 0 until GlobalData.Jugador2.size) {
                    val idView = when (i) {
                        0 -> R.id.TropaFa
                        1 -> R.id.TropaEb
                        2 -> R.id.TropaEa
                        3 -> R.id.TropaDc
                        4 -> R.id.TropaDb
                        5 -> R.id.TropaDa
                        else -> null
                    }
                    idView?.let {
                        val imagenCentral = findViewById<ImageView>(it)
                        val imagen = GlobalData.Jugador2[i]!!.rutaviva
                        val base =
                            ContextCompat.getDrawable(this, imagen)?.constantState?.newDrawable()
                                ?.mutate()
                        Haceranimacion(overlay, imagen, base, imagenCentral)
                    }
                }
                GlobalData.Atodos = false
                return
            }
            if(GlobalData.tropa_seleccionada_posicion == 0 ){
                val imagenCentral = findViewById<ImageView>(R.id.TropaFa)
                imagenCentral.scaleX = -1f
                var imagen = GlobalData.Jugador2[0]!!.rutaviva
                val base = ContextCompat.getDrawable(this, imagen)?.constantState?.newDrawable()?.mutate()
                Haceranimacion(overlay,imagen,base,imagenCentral)
                return
            }
            if(GlobalData.tropa_seleccionada_posicion == 1 ){
                val imagenCentral = findViewById<ImageView>(R.id.TropaEb)

                var imagen = GlobalData.Jugador2[1]!!.rutaviva
                val base = ContextCompat.getDrawable(this, imagen)?.constantState?.newDrawable()?.mutate()
                Haceranimacion(overlay,imagen,base,imagenCentral)
                return
            }
            if(GlobalData.tropa_seleccionada_posicion == 2 ){
                val imagenCentral = findViewById<ImageView>(R.id.TropaEa)

                var imagen = GlobalData.Jugador2[2]!!.rutaviva
                val base = ContextCompat.getDrawable(this, imagen)?.constantState?.newDrawable()?.mutate()
                Haceranimacion(overlay,imagen,base,imagenCentral)
                return
            }
            if(GlobalData.tropa_seleccionada_posicion == 3 ){
                val imagenCentral = findViewById<ImageView>(R.id.TropaDc)

                var imagen = GlobalData.Jugador2[3]!!.rutaviva
                val base = ContextCompat.getDrawable(this, imagen)?.constantState?.newDrawable()?.mutate()
                Haceranimacion(overlay,imagen,base,imagenCentral)
                return
            }
            if(GlobalData.tropa_seleccionada_posicion == 4 ){
                val imagenCentral = findViewById<ImageView>(R.id.TropaDb)

                var imagen = GlobalData.Jugador2[4]!!.rutaviva
                val base = ContextCompat.getDrawable(this, imagen)?.constantState?.newDrawable()?.mutate()
                Haceranimacion(overlay,imagen,base,imagenCentral)
                return
            }
            if(GlobalData.tropa_seleccionada_posicion == 5 ){
                val imagenCentral = findViewById<ImageView>(R.id.TropaDa)

                var imagen = GlobalData.Jugador2[5]!!.rutaviva
                val base = ContextCompat.getDrawable(this, imagen)?.constantState?.newDrawable()?.mutate()
                Haceranimacion(overlay,imagen,base,imagenCentral)
                return
            }
        }

    }

    fun Haceranimacion(
        overlay: Drawable?,
        imagen: Int,
        base: Drawable?,
        imagenCentral: ImageView
    ) {
        var overlay = ContextCompat.getDrawable(this, R.drawable.efecto_golpe)
        if (overlay == null || base == null) return

        val layer = LayerDrawable(arrayOf( overlay, base))
        imagenCentral.setImageDrawable(layer)

        // Detectar si está volteada
        val direccion = if (imagenCentral.scaleX < 0) -1 else 1

        imagenCentral.animate()
            .scaleX(1.1f * direccion)
            .scaleY(1.1f)
            .setDuration(200)
            .withEndAction {
                imagenCentral.animate()
                    .alpha(0.7f)
                    .setDuration(400)
                    .withEndAction {
                        imagenCentral.postDelayed({
                            imagenCentral.setImageResource(imagen)
                            imagenCentral.alpha = 1f
                            imagenCentral.scaleX = direccion.toFloat()
                            imagenCentral.scaleY = 1f
                            // 🔽 recarga solo después del efecto
                            actualizar_datos()
                        }, 500) // <-- el efecto durará medio segundo visible
                    }
            }


    }

}