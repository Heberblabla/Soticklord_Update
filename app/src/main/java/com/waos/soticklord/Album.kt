package com.waos.soticklord
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import Data.*
import android.content.Intent
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import java.lang.reflect.Modifier
import android.widget.Toast
import com.waos.soticklord.Iniciar_Sesion

class Album : AppCompatActivity() {
    val ataques = HashMap<Int, String>()
    var posicionAtaque = 0
    var rey_o_tropa = 1 //1 = Rey , 0 = Tropa
    var aumento = 0 //parra pasar a la siguiente pagina :v
    var listaTropas = GlobalData.Diccionario_Tropas.values.toList()
    var listaReyes = GlobalData.Diccionario_Reyes.values.toList()
    var listaNombres = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_album)

        // Pantalla completa
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
        cambiar_a_otro_album()
        mostrar_datos_economicos()
    }

    //Aparatdo derecho superior
    fun mostrar_datos_economicos(){
        val Nivel = findViewById<TextView>(R.id.Nivel_de_cuentawaos)
        Nivel.text = "N : ${GlobalData.nivel_de_progresion} "
        val monedas = findViewById<TextView>(R.id.Monedass)
        monedas.text = "\uD83E\uDE99 : ${GlobalData.monedas}"
        val ecencia = findViewById<TextView>(R.id.Ecencia)
        ecencia.text = "✮⋆˙ : ${GlobalData.ecencia_de_juego}"
    }
    fun apretar_para_cambiar_a_otro_album(view: View){
        if(rey_o_tropa == 1){
            rey_o_tropa = 0
            val miBoton = findViewById<ImageButton>(R.id.Cambiar_de_rey_a_tropa)
            miBoton.setImageResource(R.drawable.icono_tropa)
            aumento = 0
            cambiar_a_otro_album()
            return
        }
        if(rey_o_tropa == 0){
            rey_o_tropa = 1
            val miBoton = findViewById<ImageButton>(R.id.Cambiar_de_rey_a_tropa)
            miBoton.setImageResource(R.drawable.icono_rey)
            aumento = 0
            cambiar_a_otro_album()
            return
        }


    }
    fun cambiar_a_otro_album(){
        //figuras
        val botonesIds = listOf(
            R.id.Personaje0, R.id.Personaje1, R.id.Personaje2, R.id.Personaje3, R.id.Personaje4,
            R.id.Personaje5, R.id.Personaje6, R.id.Personaje7, R.id.Personaje8, R.id.Personaje9,
            R.id.Personaje10, R.id.Personaje11, R.id.Personaje12, R.id.Personaje13, R.id.Personaje14
        )

        if(rey_o_tropa == 1){
            val Reyes = GlobalData.Diccionario_Reyes.values.toList()
            val botones = botonesIds.map { findViewById<ImageButton>(it) }
            cargar_botones_imagen(botones, Reyes)
        }
        if(rey_o_tropa == 0){
            val tropas = GlobalData.Diccionario_Tropas.values.toList()
            val botones = botonesIds.map { findViewById<ImageButton>(it) }
            cargar_botones_imagen(botones, tropas)
        }


    }
    //apartado derecho inferior
    fun cargar_botones_imagen(botones: List<ImageButton>, tropas: List<Tropa>) {
        for (i in botones.indices) {
            val index = i + aumento
            if (index < tropas.size) {
                botones[i].setImageResource(tropas[index].rutaviva)
                botones[i].visibility = View.VISIBLE
            } else {
                botones[i].visibility = View.GONE
            }
        }

    }

    fun mostrarInfoPersonaje(view: View) {
        val botonesIds = listOf(
            R.id.Personaje0, R.id.Personaje1, R.id.Personaje2, R.id.Personaje3, R.id.Personaje4,
            R.id.Personaje5, R.id.Personaje6, R.id.Personaje7, R.id.Personaje8, R.id.Personaje9,
            R.id.Personaje10, R.id.Personaje11, R.id.Personaje12, R.id.Personaje13, R.id.Personaje14
        )

        val index = botonesIds.indexOf(view.id)
        if (index == -1) return

        val posicionReal = index + aumento

        if (rey_o_tropa == 0) {
            // Usar la listaTropas actual (ordenada o no)
            if (posicionReal < listaTropas.size) {
                val tropa = listaTropas[posicionReal]
                mostrarDatosDeTropa(tropa)
            }
        } else {
            //  Usar la listaReyes actual (ordenada o no)
            if (posicionReal < listaReyes.size) {
                val rey = listaReyes[posicionReal]
                mostrarDatosDeRey(rey)
            }
        }
    }


    fun mostrarDatosDeTropa(tropa: Tropa) {

        val Nombre_rey_tropa = findViewById<TextView>(R.id.Nombre_rey_tropa)
        Nombre_rey_tropa.text = "${tropa.nombre}"
        val imagen = findViewById<ImageView>(R.id.Imagen_grande)
        imagen.setImageResource(tropa.rutaviva)
        val Nivel_rey_tropa = findViewById<TextView>(R.id.Nivel_rey_tropa)
        Nivel_rey_tropa.text = "N : ${tropa.nivel}"
        val Vida_rey_tropa = findViewById<TextView>(R.id.Vida_rey_tropa)
        Vida_rey_tropa.text = "♡  : ${tropa.vida}"
        val Ataque_rey_tropa = findViewById<TextView>(R.id.Ataque_rey_tropa)
        Ataque_rey_tropa.text = "⚔ : ${tropa.ataque_base}"
        val Ataques_disponibles = findViewById<TextView>(R.id.Ataques_disponibles)

        var ataquess =obtener_Ataques(tropa)
        Ataques_disponibles.text = ataquess

        listaNombres = Obtener_Array_String(tropa.nombre).toMutableList()
        actualizarAtaques(listaNombres)

        val Nuevo_nivel_rey_tropa = findViewById<TextView>(R.id.Nuevo_nivel_rey_tropa)
        var Nuevo = (tropa.nivel + 1)
        Nuevo_nivel_rey_tropa.text = "N :$Nuevo"

        val Nueva_vida_rey_tropa = findViewById<TextView>(R.id.Nueva_vida_rey_tropa)
        var nuevoN = Tropa.calcularAtaque(tropa.vida,2)
        Nueva_vida_rey_tropa.text = "♡  : $nuevoN"

        val Nuevo_ataque_rey_tropa = findViewById<TextView>(R.id.Nuevo_ataque_rey_tropa)
        var proxima_ataque = Tropa.calcularAtaque(tropa.ataque_base,2)
        Nuevo_ataque_rey_tropa.text = "⚔ : $proxima_ataque"

        val probabilidad = (tropa.probabilidad_de_critico * 100).toInt()
        val dañoCritico = (tropa.daño_critico * 100).toInt()

        val Probabilidad_tropa_rey = findViewById<TextView>(R.id.Probabilidad_tropa_rey)
        Probabilidad_tropa_rey.text = "✢ : $probabilidad%"
        val Daño_critico_tropa_rey = findViewById<TextView>(R.id.Dano_critico_tropa_rey)
        Daño_critico_tropa_rey.text = "☠\uFE0E\uFE0E : +$dañoCritico%"

        pasarSiguienteAtaque()

    }

    fun mostrarDatosDeRey(tropa: Tropa) {
        val Nombre_rey_tropa = findViewById<TextView>(R.id.Nombre_rey_tropa)
        Nombre_rey_tropa.text = "${tropa.nombre}"
        val imagen = findViewById<ImageView>(R.id.Imagen_grande)
        imagen.setImageResource(tropa.rutaviva)
        val Nivel_rey_tropa = findViewById<TextView>(R.id.Nivel_rey_tropa)
        Nivel_rey_tropa.text = "N :${tropa.nivel}"
        val Vida_rey_tropa = findViewById<TextView>(R.id.Vida_rey_tropa)
        Vida_rey_tropa.text = "♡  : ${tropa.vida}"
        val Ataque_rey_tropa = findViewById<TextView>(R.id.Ataque_rey_tropa)
        Ataque_rey_tropa.text = "⚔ : ${tropa.ataque_base}"
        val Ataques_disponibles = findViewById<TextView>(R.id.Ataques_disponibles)


        val probabilidad = (tropa.probabilidad_de_critico * 100).toInt()
        val dañoCritico = (tropa.daño_critico * 100).toInt()

        val Probabilidad_tropa_rey = findViewById<TextView>(R.id.Probabilidad_tropa_rey)
        Probabilidad_tropa_rey.text = "✢ : $probabilidad%"
        val Daño_critico_tropa_rey = findViewById<TextView>(R.id.Dano_critico_tropa_rey)
        Daño_critico_tropa_rey.text = "☠\uFE0E\uFE0E : +$dañoCritico%"

        var ataquess =obtener_Ataques(tropa)
        Ataques_disponibles.text = ataquess
        listaNombres = Obtener_Array_String(tropa.nombre).toMutableList()
        actualizarAtaques(listaNombres)

        val Nuevo_nivel_rey_tropa = findViewById<TextView>(R.id.Nuevo_nivel_rey_tropa)
        var Nuevo = (tropa.nivel + 1)
        Nuevo_nivel_rey_tropa.text = "N :$Nuevo"

        val Nueva_vida_rey_tropa = findViewById<TextView>(R.id.Nueva_vida_rey_tropa)
        var nuevoN = Tropa.calcularAtaque(tropa.vida,2)
        Nueva_vida_rey_tropa.text = "♡  : $nuevoN"

        val Nuevo_ataque_rey_tropa = findViewById<TextView>(R.id.Nuevo_ataque_rey_tropa)
        var proxima_ataque = Tropa.calcularAtaque(tropa.ataque_base,2)
        Nuevo_ataque_rey_tropa.text = "⚔ : $proxima_ataque"

        pasarSiguienteAtaque()
    }

    fun pasar_siguiente_hoja(view: View) {
        val botonesIds = listOf(
            R.id.Personaje0, R.id.Personaje1, R.id.Personaje2, R.id.Personaje3, R.id.Personaje4,
            R.id.Personaje5, R.id.Personaje6, R.id.Personaje7, R.id.Personaje8, R.id.Personaje9,
            R.id.Personaje10, R.id.Personaje11, R.id.Personaje12, R.id.Personaje13, R.id.Personaje14
        )
        val botones = botonesIds.map { findViewById<ImageButton>(it) }
        val maxPorPagina = 15

        if (rey_o_tropa == 0) {
            val total = GlobalData.Diccionario_Tropas.size
            if (aumento + maxPorPagina < total) {
                aumento += maxPorPagina
                cargar_botones_imagen(botones, listaTropas)
            }
        }

        if (rey_o_tropa == 1) {
            val total = GlobalData.Diccionario_Reyes.size
            if (aumento + maxPorPagina < total) {
                aumento += maxPorPagina
                cargar_botones_imagen(botones, listaReyes)
            }
        }
    }
    fun pasar_hoja_anterior(view: View) {
        val botonesIds = listOf(
            R.id.Personaje0, R.id.Personaje1, R.id.Personaje2, R.id.Personaje3, R.id.Personaje4,
            R.id.Personaje5, R.id.Personaje6, R.id.Personaje7, R.id.Personaje8, R.id.Personaje9,
            R.id.Personaje10, R.id.Personaje11, R.id.Personaje12, R.id.Personaje13, R.id.Personaje14
        )
        val botones = botonesIds.map { findViewById<ImageButton>(it) }
        val maxPorPagina = 15

        // Evita que baje de 0
        if (aumento - maxPorPagina >= 0) {
            aumento -= maxPorPagina
        } else {
            aumento = 0
        }

        // Carga según el tipo de álbum actual
        if (rey_o_tropa == 0) {
            val listaTropas = GlobalData.Diccionario_Tropas.values.toList()
            cargar_botones_imagen(botones, listaTropas)
        }

        if (rey_o_tropa == 1) {
            val listaReyes = GlobalData.Diccionario_Reyes.values.toList()
            cargar_botones_imagen(botones, listaReyes)
        }
    }

    fun pasarSiguienteAtaque(view: View) {
        if (ataques.isEmpty()) return

        // Obtener nombre del ataque actual
        val nombreAtaque = ataques[posicionAtaque]
        val descripcion = GlobalData.Diccionario_Ataques[nombreAtaque] ?: "Descripción no encontrada"

        val Nombre_de_Ataque = findViewById<TextView>(R.id.Nombre_de_Ataque)
        Nombre_de_Ataque.text = nombreAtaque
        val Info_ataques = findViewById<TextView>(R.id.Info_ataques)
        Info_ataques.text = descripcion

        // Pasar al siguiente
        posicionAtaque++
        if (posicionAtaque >= ataques.size) {
            posicionAtaque = 0 // vuelve al inicio
        }
    }

    fun pasarSiguienteAtaque() {
        if (ataques.isEmpty()) return

        // Obtener nombre del ataque actual
        val nombreAtaque = ataques[posicionAtaque]
        val descripcion = GlobalData.Diccionario_Ataques[nombreAtaque] ?: "Descripción no encontrada"

        val Nombre_de_Ataque = findViewById<TextView>(R.id.Nombre_de_Ataque)
        Nombre_de_Ataque.text = nombreAtaque
        val Info_ataques = findViewById<TextView>(R.id.Info_ataques)
        Info_ataques.text = descripcion

        // Pasar al siguiente
        if (posicionAtaque >= ataques.size) {
            posicionAtaque = 0 // vuelve al inicio
        }
    }

    fun pasarAtaqueAnterior(view: View) {
        if (ataques.isEmpty()) return

        // Retroceder
        posicionAtaque--
        if (posicionAtaque < 0) {
            posicionAtaque = ataques.size - 1 // vuelve al último
        }

        val nombreAtaque = ataques[posicionAtaque]
        val descripcion = GlobalData.Diccionario_Ataques[nombreAtaque] ?: "Descripción no encontrada"

        val Nombre_de_Ataque = findViewById<TextView>(R.id.Nombre_de_Ataque)
        Nombre_de_Ataque.text = nombreAtaque
        val Info_ataques = findViewById<TextView>(R.id.Info_ataques)
        Info_ataques.text = descripcion
    }


    fun actualizarAtaques(nuevosAtaques: List<String>) {
        ataques.clear() // Borra todo
        for ((index, ataque) in nuevosAtaques.withIndex()) {
            ataques[index] = ataque
        }
    }


    fun obtener_Ataques(obj: Any): String {
        val metodos_excluidos = listOf(
            "toString", "equals", "hashCode",
            "copyValueOf", "transform", "formatted", "intern",
            "wait", "notify", "notifyAll", "getClass",
            "clonar", "copyBase", "Recivir_daño",
            "component1", "component2" ,"Ataque_Normal"," efectuardaño","Habilidad_Especial"
        )

        return obj::class.java.methods
            .filter { Modifier.isPublic(it.modifiers) }             //  solo métodos públicos
            .filter { !it.isSynthetic }                             //  descarta métodos del compilador
            .filter { !it.name.contains("$") }                      //  descarta los $r8$lambda
            .map { it.name }
            .filter { it !in metodos_excluidos }
            .filter { !it.startsWith("get") && !it.startsWith("set") }
            .distinct()
            .sorted()
            .joinToString("\n") { "- $it" } // formato limpio
    }


    //para impirmir

    fun Obtener_Array_String(nombreClase: String): List<String> {
        return try {
            val claseKotlin = GlobalData.Diccionario_Clases[nombreClase]
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
                            "clonar", "copyBase", "Recivir_daño",
                            "component1", "component2" ,"Ataque_Normal","efectuardaño","Habilidad_Especial"
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


    //para recorrer

    fun ordenar_por_nivel(view: View) {
        val botonesIds = listOf(
            R.id.Personaje0, R.id.Personaje1, R.id.Personaje2, R.id.Personaje3, R.id.Personaje4,
            R.id.Personaje5, R.id.Personaje6, R.id.Personaje7, R.id.Personaje8, R.id.Personaje9,
            R.id.Personaje10, R.id.Personaje11, R.id.Personaje12, R.id.Personaje13, R.id.Personaje14
        )
        if(rey_o_tropa == 1){
            listaReyes = quickSort(GlobalData.Diccionario_Reyes.values.toMutableList()) { it.nivel }
            val botones = botonesIds.map { findViewById<ImageButton>(it) }
            cargar_botones_imagen(botones, listaReyes)
        }
        if(rey_o_tropa == 0){
            listaTropas = quickSort(GlobalData.Diccionario_Tropas.values.toMutableList()) { it.nivel }
            val botones = botonesIds.map { findViewById<ImageButton>(it) }
            cargar_botones_imagen(botones, listaTropas)
        }
    }

    fun ordenar_por_vida(view: View) {
        val botonesIds = listOf(
            R.id.Personaje0, R.id.Personaje1, R.id.Personaje2, R.id.Personaje3, R.id.Personaje4,
            R.id.Personaje5, R.id.Personaje6, R.id.Personaje7, R.id.Personaje8, R.id.Personaje9,
            R.id.Personaje10, R.id.Personaje11, R.id.Personaje12, R.id.Personaje13, R.id.Personaje14
        )
        if(rey_o_tropa == 1){
            listaReyes = quickSort(GlobalData.Diccionario_Reyes.values.toMutableList()) { it.vida }
            val botones = botonesIds.map { findViewById<ImageButton>(it) }
            cargar_botones_imagen(botones, listaReyes)
        }
        if(rey_o_tropa == 0){
            listaTropas = quickSort(GlobalData.Diccionario_Tropas.values.toMutableList()) { it.vida }
            val botones = botonesIds.map { findViewById<ImageButton>(it) }
            cargar_botones_imagen(botones, listaTropas)
        }
    }

    fun ordenar_por_ataque(view: View) {
        val botonesIds = listOf(
            R.id.Personaje0, R.id.Personaje1, R.id.Personaje2, R.id.Personaje3, R.id.Personaje4,
            R.id.Personaje5, R.id.Personaje6, R.id.Personaje7, R.id.Personaje8, R.id.Personaje9,
            R.id.Personaje10, R.id.Personaje11, R.id.Personaje12, R.id.Personaje13, R.id.Personaje14
        )
        if(rey_o_tropa == 1){
            listaReyes = quickSort(GlobalData.Diccionario_Reyes.values.toMutableList()) { it.ataque_base }
            val botones = botonesIds.map { findViewById<ImageButton>(it) }
            cargar_botones_imagen(botones, listaReyes)
        }
        if(rey_o_tropa == 0){
            listaTropas = quickSort(GlobalData.Diccionario_Tropas.values.toMutableList()) { it.ataque_base }
            val botones = botonesIds.map { findViewById<ImageButton>(it) }
            cargar_botones_imagen(botones, listaTropas)
        }
    }

    // ==================== QUICK SORT GENÉRICO ====================
    fun <T> quickSort(lista: MutableList<T>, criterio: (T) -> Int): MutableList<T> {
        if (lista.size <= 1) return lista

        val pivote = lista[lista.size / 2]
        val valorPivote = criterio(pivote)

        val menores = mutableListOf<T>()
        val iguales = mutableListOf<T>()
        val mayores = mutableListOf<T>()

        for (elemento in lista) {
            val valor = criterio(elemento)
            when {
                valor > valorPivote -> mayores.add(elemento)   // descendente
                valor < valorPivote -> menores.add(elemento)
                else -> iguales.add(elemento)
            }
        }

        val resultado = mutableListOf<T>()
        resultado.addAll(quickSort(mayores, criterio))
        resultado.addAll(iguales)
        resultado.addAll(quickSort(menores, criterio))

        return resultado
    }

    fun subir_de_nivel(view: View) {
        val nombreActual = findViewById<TextView>(R.id.Nombre_rey_tropa).text.toString()
        if (nombreActual.isBlank()) return // nada seleccionado

        // Verificar si tiene monedas suficientes
        if (GlobalData.monedas < 100) {
            Toast.makeText(this, " No tienes suficientes monedas (100 necesarias)", Toast.LENGTH_SHORT).show()
            return
        }

        //  Descontar monedas
        GlobalData.monedas -= 100
        mostrar_datos_economicos() // refresca la esquina superior

        // Si estás viendo tropas
        if (rey_o_tropa == 0) {
            val tropaOriginal = GlobalData.Diccionario_Tropas.values.find { it.nombre == nombreActual }
            if (tropaOriginal != null) {
                val nuevoNivel = tropaOriginal.nivel + 1

                // Crear nuevo objeto del mismo tipo pero con nivel +1
                val claseTropa = GlobalData.Diccionario_Clases[tropaOriginal.nombre]
                if (claseTropa != null) {
                    val nuevoObjeto = claseTropa.constructors.first().call(nuevoNivel) as Tropa

                    // Actualizar el diccionario
                    val clave = GlobalData.Diccionario_Tropas.entries.find { it.value == tropaOriginal }?.key
                    if (clave != null) {
                        GlobalData.Diccionario_Tropas[clave] = nuevoObjeto
                    }

                    // Refrescar la lista y mostrar los nuevos datos
                    listaTropas = GlobalData.Diccionario_Tropas.values.toList()
                    mostrarDatosDeTropa(nuevoObjeto)

                    Toast.makeText(this, "${nuevoObjeto.nombre} subió a nivel ${nuevoObjeto.nivel}!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //Si estás viendo reyes
        if (rey_o_tropa == 1) {
            val reyOriginal = GlobalData.Diccionario_Reyes.values.find { it.nombre == nombreActual }
            if (reyOriginal != null) {
                val nuevoNivel = reyOriginal.nivel + 1

                val claseRey = GlobalData.Diccionario_Clases[reyOriginal.nombre]
                if (claseRey != null) {
                    val nuevoObjeto = claseRey.constructors.first().call(nuevoNivel) as Tropa

                    val clave = GlobalData.Diccionario_Reyes.entries.find { it.value == reyOriginal }?.key
                    if (clave != null) {
                        GlobalData.Diccionario_Reyes[clave] = nuevoObjeto
                    }

                    listaReyes = GlobalData.Diccionario_Reyes.values.toList()
                    mostrarDatosDeRey(nuevoObjeto)

                    Toast.makeText(this, " ${nuevoObjeto.nombre} subió a nivel ${nuevoObjeto.nivel}!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        DataManager.guardarDatos(this)
    }

    fun atras(view: View){
        val intent = Intent(this, Iniciar_Sesion::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    fun habilidad(view: View) {
        val vistaPopup = layoutInflater.inflate(R.layout.popup_layout, null)

        val textoInfo = vistaPopup.findViewById<TextView>(R.id.Info_Habilidad)

        //Cambias el texto
        textoInfo.text = "🔥 Habilidad activada: Golpe de energía"

        val ancho = (190 * resources.displayMetrics.density).toInt()
        val alto = (150 * resources.displayMetrics.density).toInt()
        val popup = PopupWindow(vistaPopup, ancho, alto, true)
        // Muestra el popup justo debajo del botón que lo activó
        popup.showAsDropDown(view, 0, 10)
    }



}
