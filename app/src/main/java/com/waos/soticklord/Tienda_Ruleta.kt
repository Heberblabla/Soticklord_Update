package com.waos.soticklord

import Data.Especiales.Rey_Cristian
import Data.Especiales.Rey_Fernando
import Data.Especiales.Rey_Heber
import Data.Especiales.Reyna_Darisce
import Data.Especiales.Reyna_Shantal
import Data.Personalizados.Rey_Aethelred
import Data.Personalizados.Rey_Han_Kong
import Data.Personalizados.Rey_Kratos
import Data.Personalizados.Rey_Moises
import Data.Tropa
import Data.Tropa_Arquero
import Data.Tropas_personalizadas.Tropa_Curandera
import Data.Tropas_personalizadas.Tropa_Gato_amigo1
import Data.Tropas_personalizadas.Tropa_Gato_amigo2
import Data.Tropas_personalizadas.Tropa_Gigante_estelar
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
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

class Tienda_Ruleta : AppCompatActivity() {

    private lateinit var ruletaView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tienda_ruleta)

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

        ruletaView = findViewById(R.id.ruletaone)

        cargar_info()

    }


    fun girarRuleta1() {

        val rangos = listOf(
            7..38,    // Premio 1
            52..83,   // Premio 2
            97..128,  // Premio 3
            142..173, // Premio 4
            187..218, // Premio 5
            232..263, // Premio 6
            277..308, // Premio 7
            322..353  // Premio 8
        )

        val ruletaView = findViewById<ImageView>(R.id.ruletaone)
        ruletaView.setImageResource(R.drawable.primera_ruleta)

        // 1. Elegir premio aleatorio (1–8)
        val premio = (1..8).random()

        val Premios_nombre = mapOf(
            1 to "+ 3 niveles Arquero tropa",
            2 to "+ 100 monedas",
            3 to "+ 100 monedas",
            4 to "Rey_Han_Kong",
            5 to "+ 100 monedas",
            6 to "+ 100 monedas",
            7 to "+ Tropa Gato",
            8 to "+ 100 monedas",
        )


        // 2. Elegir ángulo dentro del rango correspondiente
        val rango = rangos[premio - 1]
        val anguloElegido = rango.random()

        // 3. Hacer muchas vueltas + ángulo final
        val anguloFinal = 3600 + anguloElegido   // 10 vueltas + ángulo ganador

        // 4. Animación
        val rotation = RotateAnimation(
            0f,
            anguloFinal.toFloat(),
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )

        rotation.duration = 3000
        rotation.fillAfter = true
        rotation.interpolator = DecelerateInterpolator()

        rotation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                Buscar_premio(1,premio)
                cargar_info()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        ruletaView.startAnimation(rotation)


    }

    fun girarRuleta2() {

        val rangos = listOf(
            7..38,    // Premio 1
            52..83,   // Premio 2
            97..128,  // Premio 3
            142..173, // Premio 4
            187..218, // Premio 5
            232..263, // Premio 6
            277..308, // Premio 7
            322..353  // Premio 8
        )

        val ruletaView = findViewById<ImageView>(R.id.ruletaoneone)
        ruletaView.setImageResource(R.drawable.segunda_ruleta)

        // 1. Elegir premio aleatorio (1–8)
        val premio = (1..8).random()

        val Premios_nombre = mapOf(
            1 to "+ Rey_Aethelred",
            2 to "+ Rey_Moises",
            3 to "+ 100 monedas",
            4 to "+ Tropa Curandera",
            5 to "+ Rey Kratos",
            6 to "+ 100 monedas",
            7 to "+ Tropa Gato",
            8 to "+ 100 monedas",
        )


        // 2. Elegir ángulo dentro del rango correspondiente
        val rango = rangos[premio - 1]
        val anguloElegido = rango.random()

        // 3. Hacer muchas vueltas + ángulo final
        val anguloFinal = 3600 + anguloElegido   // 10 vueltas + ángulo ganador

        // 4. Animación
        val rotation = RotateAnimation(
            0f,
            anguloFinal.toFloat(),
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )

        rotation.duration = 3000
        rotation.fillAfter = true
        rotation.interpolator = DecelerateInterpolator()

        rotation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                Buscar_premio(2,premio)
                cargar_info()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        ruletaView.startAnimation(rotation)

    }

    fun girarRuleta3() {

        val rangos = listOf(
            7..38,    // Premio 1
            52..83,   // Premio 2
            97..128,  // Premio 3
            145..173, // Premio 4
            187..218, // Premio 5
            232..263, // Premio 6
            277..308, // Premio 7
            322..353  // Premio 8
        )

        val ruletaView = findViewById<ImageView>(R.id.ruletaoneoneone)
        ruletaView.setImageResource(R.drawable.ruleta_tercera)

        // 1. Elegir premio aleatorio (1–8)
        val premio = (1..8).random()

        val Premios_nombre = mapOf(
            1 to "+ Reyna Shantal",
            2 to "+ Rey Fernando",
            3 to "+ 500 monedas",
            4 to "+ Reyna Darisce",
            5 to "+ Rey Cristian",
            6 to "+ 700 monedas",
            7 to "+ Rey Heber",
            8 to "+ S/. 1.0 ",
        )


        // 2. Elegir ángulo dentro del rango correspondiente
        val rango = rangos[premio - 1]
        val anguloElegido = rango.random()

        // 3. Hacer muchas vueltas + ángulo final
        val anguloFinal = 3600 + anguloElegido   // 10 vueltas + ángulo ganador

        // 4. Animación
        val rotation = RotateAnimation(
            0f,
            anguloFinal.toFloat(),
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )

        rotation.duration = 3000
        rotation.fillAfter = true
        rotation.interpolator = DecelerateInterpolator()

        rotation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                Buscar_premio(3,premio)
                cargar_info()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        ruletaView.startAnimation(rotation)



    }


    fun Comprar_ruleta1(view: View) {
        val inflater = layoutInflater
        val vista = inflater.inflate(R.layout.popup_confirmar, null)
        val texto = vista.findViewById<TextView>(R.id.textoConfirmar)
        val botonSi = vista.findViewById<Button>(R.id.botonSi)
        val botonNo = vista.findViewById<Button>(R.id.botonNo)
        // Por si quieres cambiar el texto dinámicamente:
        texto.text = """
            
            ------¿   Deseas comprar Un giro en esta ruleta  ?------
            
            -                            Costo : 250 🪙                              -
                           
            """.trimIndent()

        val dialogo = AlertDialog.Builder(this)
            .setView(vista)
            .setCancelable(false)
            .create()

        botonSi.setOnClickListener {
            if (GlobalData.monedas >= 250) {
                    GlobalData.monedas -= 250
                    cargar_info()
                    //mostrar_datos_economicos()
                    girarRuleta1()
                    DataManager.guardarDatos(this)
                    dialogo.dismiss()
                } else {
                    Toast.makeText(this, "No tiene suficiente Monedas", Toast.LENGTH_SHORT).show()
                    dialogo.dismiss()
                }
            }


        botonNo.setOnClickListener {
            dialogo.dismiss()
        }
        dialogo.show()
    }

    fun Comprar_ruleta2(view: View) {
        val inflater = layoutInflater
        val vista = inflater.inflate(R.layout.popup_confirmar, null)
        val texto = vista.findViewById<TextView>(R.id.textoConfirmar)
        val botonSi = vista.findViewById<Button>(R.id.botonSi)
        val botonNo = vista.findViewById<Button>(R.id.botonNo)
        // Por si quieres cambiar el texto dinámicamente:
        texto.text = """
            
            ------¿   Deseas comprar Un giro en esta ruleta  ?------
            
            -                            Costo : 500 🪙                              -
                           
            """.trimIndent()

        val dialogo = AlertDialog.Builder(this)
            .setView(vista)
            .setCancelable(false)
            .create()

        botonSi.setOnClickListener {
            if (GlobalData.monedas >= 500) {
                GlobalData.monedas -= 500
                cargar_info()
                //mostrar_datos_economicos()
                girarRuleta2()
                DataManager.guardarDatos(this)
                dialogo.dismiss()
            } else {
                Toast.makeText(this, "No tiene suficiente Monedas", Toast.LENGTH_SHORT).show()
                dialogo.dismiss()
            }
        }


        botonNo.setOnClickListener {
            dialogo.dismiss()
        }

        dialogo.show()
    }

    fun Comprar_ruleta3(view: View) {
        val inflater = layoutInflater
        val vista = inflater.inflate(R.layout.popup_confirmar, null)
        val texto = vista.findViewById<TextView>(R.id.textoConfirmar)
        val botonSi = vista.findViewById<Button>(R.id.botonSi)
        val botonNo = vista.findViewById<Button>(R.id.botonNo)
        // Por si quieres cambiar el texto dinámicamente:
        texto.text = """
            
            ------¿   Deseas comprar Un giro en esta ruleta  ?------
            
            -                            Costo : 1000 🪙                              -
                           
            """.trimIndent()

        val dialogo = AlertDialog.Builder(this)
            .setView(vista)
            .setCancelable(false)
            .create()

        botonSi.setOnClickListener {
            if (GlobalData.monedas >= 1000) {
                GlobalData.monedas -= 1000
                cargar_info()
                //mostrar_datos_economicos()
                girarRuleta3()
                DataManager.guardarDatos(this)
                dialogo.dismiss()
            } else {
                Toast.makeText(this, "No tiene suficiente Monedas", Toast.LENGTH_SHORT).show()
                dialogo.dismiss()
            }
        }


        botonNo.setOnClickListener {
            dialogo.dismiss()
        }

        dialogo.show()
    }


    fun cargar_info(){
        val Info_ruleta1 = findViewById<TextView>(R.id.Info_ruleta1)
        Info_ruleta1.text = """
                 Ruleta para Pobres
            costo : 250 🪙
            nota "La probabilidad es la misma para todo"
        """.trimIndent()

        val Info_ruleta2 = findViewById<TextView>(R.id.Info_ruleta2)
        Info_ruleta2.text = """
                 Ruleta para Gente común y 
                    corriente como tu
            costo : 500 🪙 
            nota "La probabilidad es la misma para todo"
        """.trimIndent()

        val Info_ruleta3 = findViewById<TextView>(R.id.Info_ruleta3)
        Info_ruleta3.text = """
                 Ruleta De los Cracks
            costo : 1000 🪙  
            nota "La probabilidad es la misma para todo"
        """.trimIndent()

        val Info_monedas = findViewById<TextView>(R.id.Info_monedas)
        Info_monedas.text = "\uD83E\uDE99 : ${GlobalData.monedas}"

        val Info_monedas_globales = findViewById<TextView>(R.id.Info_monedas_globales)
        Info_monedas_globales.text = "S/. : ${GlobalData.Moneda_Global}"

    }

    fun Buscar_premio(ruleta : Int,premio : Int){

        if(ruleta == 1){
            when (premio) {
                1 -> {
                    var waos = devolver_clave("Tropa_Arquero")
                    var nuevo_nivel= GlobalData.Diccionario_Tropas[waos]!!.nivel + 3
                    GlobalData.Diccionario_Tropas[waos] = Tropa_Arquero(nuevo_nivel)
                    DataManager.guardarDatos(this)
                    Toast.makeText(this, "se aumento + 3 niveles a tu Tropa Arquero", Toast.LENGTH_SHORT).show()
                }
                2 -> {
                    GlobalData.monedas += 100
                    Toast.makeText(this, "Obtuvites + 100 monedas", Toast.LENGTH_SHORT).show()
                }
                3 -> {
                    GlobalData.monedas += 100
                    Toast.makeText(this, "Obtuvites + 100 monedas", Toast.LENGTH_SHORT).show()
                }
                4 -> {
                    var waos = devolver_clave("Rey_Han_Kong")
                    if (waos == -1){
                       var xd = obtenerUltimoIDRey() + 1
                       GlobalData.Diccionario_Reyes[xd] = Rey_Han_Kong(5)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "Obtuvistes A un nuevo rey : Rey_Han_Kong", Toast.LENGTH_SHORT).show()
                    }else{
                        var nuevo_nivel= GlobalData.Diccionario_Reyes[waos]!!.nivel + 5
                        GlobalData.Diccionario_Reyes[waos] = Rey_Han_Kong(nuevo_nivel)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "se aumento + 5 niveles a tu Rey_Han_Kong", Toast.LENGTH_SHORT).show()
                    }
                }
                5 -> {
                    GlobalData.monedas += 100
                    Toast.makeText(this, "Obtuvites + 100 monedas", Toast.LENGTH_SHORT).show()
                }
                6 -> {
                    GlobalData.monedas += 100
                    Toast.makeText(this, "Obtuvites + 100 monedas", Toast.LENGTH_SHORT).show()
                }
                7 -> {
                    var waos = devolver_clave2("Tropa_Gato_amigo1")
                    if (waos == -1){
                        var xd = obtenerUltimoIDTropa() + 1
                        println(" xd $xd")
                        GlobalData.Diccionario_Tropas[xd] = Tropa_Gato_amigo1(10)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "Obtuvistes A una nueva Tropa : Tropa_Gato_amigo1", Toast.LENGTH_SHORT).show()
                    }else{
                        var nuevo_nivel= GlobalData.Diccionario_Tropas[waos]!!.nivel + 5
                        GlobalData.Diccionario_Tropas[waos] = Tropa_Gato_amigo1(nuevo_nivel)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "se aumento + 5 niveles a tu Tropa_Gato_amigo1", Toast.LENGTH_SHORT).show()
                    }
                }
                8 -> {
                    GlobalData.monedas += 100
                    Toast.makeText(this, "Obtuvites + 100 monedas", Toast.LENGTH_SHORT).show()
                }
            }


        }

        if(ruleta == 2){
            when (premio) {
                1 -> {
                    var waos = devolver_clave("Rey_Aethelred")
                    if (waos == -1){
                        var xd = obtenerUltimoIDRey() + 1
                        GlobalData.Diccionario_Reyes[xd] = Rey_Aethelred(5)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "Obtuvistes A un nuevo rey : Rey_Aethelred", Toast.LENGTH_SHORT).show()
                    }else{
                        var nuevo_nivel= GlobalData.Diccionario_Reyes[waos]!!.nivel + 5
                        GlobalData.Diccionario_Reyes[waos] = Rey_Aethelred(nuevo_nivel)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "se aumento + 5 niveles a tu Rey_Aethelred", Toast.LENGTH_SHORT).show()
                    }
                }
                2 ->{
                    var waos = devolver_clave("Rey_Moises")
                    if (waos == -1){
                        var xd = obtenerUltimoIDRey() + 1
                        GlobalData.Diccionario_Reyes[xd] = Rey_Moises(5)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "Obtuvistes A un nuevo rey : Rey_Moises", Toast.LENGTH_SHORT).show()
                    }else{
                        var nuevo_nivel= GlobalData.Diccionario_Reyes[waos]!!.nivel + 5
                        GlobalData.Diccionario_Reyes[waos] = Rey_Moises(nuevo_nivel)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "se aumento + 5 niveles a tu Rey_Moises", Toast.LENGTH_SHORT).show()
                    }
                }

                3 ->{
                    GlobalData.monedas += 100
                    Toast.makeText(this, "Obtuvites + 100 monedas", Toast.LENGTH_SHORT).show()
                }

                4 ->{
                    var waos = devolver_clave2("Tropa_Curandera")
                    if (waos == -1){
                        var xd = obtenerUltimoIDTropa() + 1
                        GlobalData.Diccionario_Tropas[xd] = Tropa_Curandera(10)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "Obtuvistes A una nueva Tropa : Tropa_Curandera", Toast.LENGTH_SHORT).show()
                    }else{
                        var nuevo_nivel= GlobalData.Diccionario_Tropas[waos]!!.nivel + 5
                        GlobalData.Diccionario_Tropas[waos] = Tropa_Curandera(nuevo_nivel)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "se aumento + 5 niveles a tu Tropa_Curandera", Toast.LENGTH_SHORT).show()
                    }
                }
                5 ->{
                    var waos = devolver_clave("Rey_Kratos")
                    if (waos == -1){
                        var xd = obtenerUltimoIDRey() + 1
                        GlobalData.Diccionario_Reyes[xd] = Rey_Kratos(5)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "Obtuvistes A un nuevo rey : Rey_Kratos", Toast.LENGTH_SHORT).show()
                    }else{
                        var nuevo_nivel= GlobalData.Diccionario_Reyes[waos]!!.nivel + 5
                        GlobalData.Diccionario_Reyes[waos] = Rey_Kratos(nuevo_nivel)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "se aumento + 5 niveles a tu Rey_Kratos", Toast.LENGTH_SHORT).show()
                    }
                }
                6 ->{
                    GlobalData.monedas += 100
                    Toast.makeText(this, "Obtuvites + 100 monedas", Toast.LENGTH_SHORT).show()
                }
                7 ->{
                    var waos = devolver_clave2("Tropa_Gato_amigo2")
                    if (waos == -1){
                        var xd = obtenerUltimoIDTropa() + 1
                        GlobalData.Diccionario_Tropas[xd] = Tropa_Gato_amigo2(10)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "Obtuvistes A una nueva Tropa : Tropa_Gato_amigo2", Toast.LENGTH_SHORT).show()
                    }else{
                        var nuevo_nivel= GlobalData.Diccionario_Tropas[waos]!!.nivel + 5
                        GlobalData.Diccionario_Tropas[waos] = Tropa_Gato_amigo2(nuevo_nivel)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "se aumento + 5 niveles a tu Tropa_Gato_amigo2", Toast.LENGTH_SHORT).show()
                    }
                }
                8 ->{
                    GlobalData.monedas += 300
                    Toast.makeText(this, "Obtuvites + 100 monedas", Toast.LENGTH_SHORT).show()
                }


            }


        }

        if(ruleta == 3){
            when (premio) {
                1 -> {
                    var waos = devolver_clave("Reyna_Shantal")
                    if (waos == -1){
                        var xd = obtenerUltimoIDRey() + 1
                        GlobalData.Diccionario_Reyes[xd] = Reyna_Shantal(5)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "Obtuvistes A un nuevo rey : Reyna_Shantal", Toast.LENGTH_SHORT).show()
                    }else{
                        var nuevo_nivel= GlobalData.Diccionario_Reyes[waos]!!.nivel + 5
                        GlobalData.Diccionario_Reyes[waos] = Reyna_Shantal(nuevo_nivel)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "se aumento + 5 niveles a tu Reyna_Shantal", Toast.LENGTH_SHORT).show()
                    }
                }
                2 ->{
                    var waos = devolver_clave("Rey_Fernando")
                    if (waos == -1){
                        var xd = obtenerUltimoIDRey() + 1
                        GlobalData.Diccionario_Reyes[xd] = Rey_Fernando(5)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "Obtuvistes A un nuevo rey : Rey_Fernando", Toast.LENGTH_SHORT).show()
                    }else{
                        var nuevo_nivel= GlobalData.Diccionario_Reyes[waos]!!.nivel + 5
                        GlobalData.Diccionario_Reyes[waos] = Rey_Fernando(nuevo_nivel)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "se aumento + 5 niveles a tu Rey_Fernando", Toast.LENGTH_SHORT).show()
                    }
                }

                3 ->{
                    GlobalData.monedas += 500
                    Toast.makeText(this, "Obtuvites + 500 monedas", Toast.LENGTH_SHORT).show()
                }

                4 ->{
                    var waos = devolver_clave("Reyna_Darisce")
                    if (waos == -1){
                        var xd = obtenerUltimoIDRey() + 1
                        GlobalData.Diccionario_Reyes[xd] = Reyna_Darisce(5)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "Obtuvistes A un nuevo rey : Reyna_Darisce", Toast.LENGTH_SHORT).show()
                    }else{
                        var nuevo_nivel= GlobalData.Diccionario_Reyes[waos]!!.nivel + 5
                        GlobalData.Diccionario_Reyes[waos] = Reyna_Darisce(nuevo_nivel)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "se aumento + 5 niveles a tu Reyna_Darisce", Toast.LENGTH_SHORT).show()
                    }
                }
                5 ->{
                    var waos = devolver_clave("Rey_Cristian")
                    if (waos == -1){
                        var xd = obtenerUltimoIDRey() + 1
                        GlobalData.Diccionario_Reyes[xd] = Rey_Cristian(5)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "Obtuvistes A un nuevo rey : Rey_Cristian", Toast.LENGTH_SHORT).show()
                    }else{
                        var nuevo_nivel= GlobalData.Diccionario_Reyes[waos]!!.nivel + 5
                        GlobalData.Diccionario_Reyes[waos] = Rey_Cristian(nuevo_nivel)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "se aumento + 5 niveles a tu Rey_Cristian", Toast.LENGTH_SHORT).show()
                    }
                }
                6 ->{
                    GlobalData.monedas += 700
                    Toast.makeText(this, "Obtuvites + 700 monedas", Toast.LENGTH_SHORT).show()
                }
                7 ->{
                    var waos = devolver_clave("Rey_Heber")
                    if (waos == -1){
                        var xd = obtenerUltimoIDRey() + 1
                        GlobalData.Diccionario_Reyes[xd] = Rey_Heber(10)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "Obtuvistes A un nuevo rey : Rey_Heber", Toast.LENGTH_SHORT).show()
                    }else{
                        var nuevo_nivel= GlobalData.Diccionario_Reyes[waos]!!.nivel + 5
                        GlobalData.Diccionario_Reyes[waos] = Rey_Heber(nuevo_nivel)
                        DataManager.guardarDatos(this)
                        Toast.makeText(this, "se aumento + 5 niveles a tu Rey_Heber", Toast.LENGTH_SHORT).show()
                    }
                }
                8 ->{
                    GlobalData.Moneda_Global += 1
                    DataManager.guardarDatos(this)
                    Toast.makeText(this, "Obtuvistes + S/. 1.0", Toast.LENGTH_SHORT).show()
                }


            }

        }

    }

    fun devolver_clave(nombre : String) : Int{
        val clave = GlobalData.Diccionario_Reyes.entries
            .find { it.value.nombre == nombre }
            ?.key

        return clave?: -1
    }

    fun devolver_clave2(nombre : String) : Int{
        val clave = GlobalData.Diccionario_Tropas.entries
            .find { it.value.nombre == nombre }
            ?.key

        return clave?: -1
    }


    fun cargar_gato(view: View){
        Buscar_premio(1,7)
    }

    fun obtenerUltimoIDTropa(): Int {
        return if (GlobalData.Diccionario_Tropas.isEmpty()) {
            -1   // o 0, según lo que necesites
        } else {
            GlobalData.Diccionario_Tropas.keys.max()
        }
    }

    fun obtenerUltimoIDRey(): Int {
        return if (GlobalData.Diccionario_Reyes.isEmpty()) {
            -1   // o 0, según lo que necesites
        } else {
            GlobalData.Diccionario_Reyes.keys.max()
        }
    }

    fun atras(view: View){
        finish()
    }


}