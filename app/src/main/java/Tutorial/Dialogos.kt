package Tutorial

import Data.Especiales.Rey_Heber
import Data.Rey_Arquero
import Data.Rey_Espadachin
import Data.Tropa_Arquero
import Data.Tropa_Espadachin
import Data.Tropa_Gigante
import Data.Tropa_Lanzatonio
import Data.Tropas_personalizadas.Tropa_Curandera
import Data.Tropas_personalizadas.Tropa_Lanzatonio_Medieval
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.gms.ads.AdView
import com.waos.soticklord.GlobalData
import com.waos.soticklord.Iniciar_Sesion
import com.waos.soticklord.R

class Dialogos : AppCompatActivity() {
    var indice_dialog = 0
    val Diccionarios_textos = hashMapOf<Int, String>(
        // Diálogos de Darisce – Entrenadora

        // Introducción al juego
        1 to """
            Bien, estamos aquí.
            Hoy aprenderás cómo funciona una batalla en Soticklord.
            Mucho gusto, Usuario. Mi nombre aún no es importante, pero cuando terminemos,
            te aseguro que saldrás bien entrenado bajo mi guía.
        """.trimIndent(),
        // Introducción a la interfaz y ataques
        2 to """
            Lo primero que debes conocer es la interfaz.
            Como puedes ver, aquí tenemos el diccionario de ataques.
            Cuando sea tu turno y selecciones un ataque,
            su información aparecerá automáticamente en el cuadro inferior.
        """.trimIndent(),
        // Introducción a la variedad de ataques
        3 to """
            Existen tres variedades de ataques: uso múltiple, uso único y uso condicionado.
            Cada tropa o rey posee ataques distintos,
            pero puedes llevar más de una tropa a la batalla
            si deseas repetir o combinar diferentes ataques.
        """.trimIndent(),
        // Variedad de uso múltiple
        4 to """
            Variedad de uso múltiple:
            Estos ataques se caracterizan porque pueden ejecutarse
            más de una vez por partida.
            Pueden ser ofensivos o defensivos, y son la base de muchas estrategias.
        """.trimIndent(),

        // Variedad de uso condicionado
        5 to """
            Variedad de uso condicionado:
            Como su nombre lo indica, estos ataques poseen ciertas restricciones.
            Si intentas ejecutarlos sin cumplir la condición requerida,
            perderás automáticamente tu turno. Un ejemplo seria "Muralla_de_cuarzo"
        """.trimIndent(),

        // Variedad de uso único
        6 to """
            Variedad de uso único:
            Estos ataques solo pueden utilizarse una vez por partida.
            Si intentas ejecutarlos nuevamente después de haberlos usado,
            perderás el turno sin causar ningún efecto.Un ejemplo seria "Puño de Dragon"
        """.trimIndent(),

        // Introducción a los tipos de tropas (aéreas y terrestres)
        7 to """
        A lo largo de tu travesía te encontrarás con dos tipos de tropas:
        tropas aéreas y tropas terrestres.
        Si somos sinceros, las aéreas suelen tener ventaja,
        aunque las terrestres no se quedan atrás y cumplen un rol clave en batalla.
    """.trimIndent(),

// Tropas aéreas
        8 to """
        Tropas o Reyes de tipo Aéreo:
        Estas tropas o reyes se caracterizan por poder atacar directamente
        al rey enemigo o a unidades que se encuentren en la retaguardia.
        Sin embargo, lo habitual es que tengan una salud y daño
        ligeramente reducidos para mantener el equilibrio.
    """.trimIndent(),

// Tropas terrestres
        9 to """
        Tropas o Reyes de tipo Terrestre:
        Estas tropas o reyes solo pueden atacar a los enemigos que están al frente.
        Deben derrotarlos primero para poder avanzar y atacar a los de atrás.
        Generalmente poseen una salud muy alta y un daño superior,
        a diferencia de las tropas aéreas.
    """.trimIndent(),
        // Identificar si una tropa es aérea o terrestre
        10 to """
        Para que puedas identificar si una tropa es aérea,
        intenta seleccionar a un enemigo que se encuentre en la retaguardia.
        Si puedes hacerlo, entonces se trata de una unidad aérea.
        De lo contrario, si solo puedes atacar a los enemigos del frente,
        significa que la tropa es de tipo terrestre.
    """.trimIndent(),
        // Notas importantes
        11 to """
    Notas importantes: Algunas tropas, incluso siendo terrestres,
    pueden realizar ataques con daño en área. Aunque solo
    selecciones a un enemigo del frente, el daño puede afectar a
    varias unidades. Todo dependerá del efecto del ataque utilizado.
    Un ejemplo sería algunos gigantes con el ataque de terremoto.
""".trimIndent(),


// Mensaje importante (interrumpido)
        12 to """
    Ah, y casi se me olvida decirte algo. Lo que realmente ayuda a
    ganar partidas y es fundamental para asegurar la victoria casi
    siempre… es—
""".trimIndent(),

        13 to """
    *Silencio*
""".trimIndent(),

        14 to """
    Ah… hola, jugador. Al parecer recibiste una buena capacitación.
    Veremos qué tan bien te desenvuelves en batalla. Prepara a tu rey
    y a tus tropas, porque pelearás contra mí.
""".trimIndent(),

        15 to """
    Bueno… al parecer te enfrentarás al Rey Heber. No estoy segura de
    que puedas vencerlo, ni siquiera yo lo logré alguna vez. Pero
    confiaré en ti. Creo que tienes lo necesario para intentarlo.
""".trimIndent(),

        16 to """
    Bien, aquí van algunos consejos rápidos para que tengas una
    oportunidad contra el Rey Heber… el Rey del Castillo, hasta donde sé.
""".trimIndent(),

        17 to """
    Primero debes saber esto: el Rey Heber juega algo sucio. Puede que
    lo veas con solo una hoja y un lápiz, pero con eso basta. Sus
    habilidades se basan en manipular el entorno y las estadísticas
    del oponente.
""".trimIndent(),

        18 to """
    Escuché que una vez convirtió a un rey legendario al máximo nivel,
    en un solo turno, en un simple espadachín sin armadura. La derrota
    fue inmediata.
""".trimIndent(),

        19 to """
    Según mis conocimientos, debes derrotar primero al Rey Heber.
    Después preocúpate por sus tropas. Si lo ignoras, existe la
    posibilidad de que altere valores como la vida de forma absurda
    solo para asegurar la partida… y luego jugar contigo.
""".trimIndent(),

        20 to """
    Lo bueno es que, al ser él el retador, tú y tus tropas comienzan
    primero. Una vez selecciones un ataque, presiona el botón “Atacar”
    para ejecutarlo. Si atacas sin seleccionar objetivo ni ataque, se
    realizará un ataque normal a la tropa del frente superior, incluso
    si ya está derrotada.
""".trimIndent(),

        21 to """
    Te prestaré algunas tropas y un rey. Después de una larga
    investigación, creo que con esto podrías vencerlo… o tal vez no.
    Pero no te desanimes. Confío en ti.
""".trimIndent(),


        )

    var ataqueSeleccionado: String = " "

    lateinit var espiner: Spinner
    lateinit var Info: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dialogos)

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

        espiner = findViewById<Spinner>(R.id.Ataques)
        Info = findViewById<TextView>(R.id.info)

        espiner.visibility = View.GONE
        Info.visibility = View.GONE

        siguiente_fialogo(findViewById(R.id.Siguiente_dialogo))


    }


    fun cambiarFuenteConFondo(items: List<String>) {
        val spinner = findViewById<Spinner>(R.id.Ataques)

        val adapter = ArrayAdapter(this, R.layout.spinner_item_madera, R.id.texto_item, items)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_madera)
        spinner.setPopupBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        spinner.adapter = adapter
    }


    fun mostrar_ataques() {
        val spinnerAtaques: Spinner = findViewById(R.id.Ataques)

        val ataquesPorDefecto = listOf(
            "Ataque_normal",
            "Muralla_de_Cuarzo",
            "Sube_de_fase",
            "Aplastamiento_de_Nemea",
            "Puño_del_Dragón"
        )


        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            ataquesPorDefecto
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAtaques.adapter = adapter

        spinnerAtaques.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                ataqueSeleccionado = parent?.getItemAtPosition(position).toString()
                cargarinfo(ataqueSeleccionado)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                ataqueSeleccionado = "Ataque_normal"
                cargarinfo(ataqueSeleccionado)
            }
        }

        cambiarFuenteConFondo(ataquesPorDefecto)
    }


    fun cargarinfo(nombre: String) {
        val descripcion = GlobalData.Diccionario_Ataques[nombre] ?: "Descripción no encontrada"
        val Info_ataques = findViewById<TextView>(R.id.info)
        Info_ataques.text = descripcion
    }

    fun siguiente_fialogo(view: View) {
        indice_dialog++
        if (indice_dialog == 0) {
            return
        }
        if (indice_dialog == 1) {
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_1_imagen)
            return
        }
        if (indice_dialog == 2) {
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_3_imagen)
            return
        }
        if (indice_dialog == 3) {
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_2_imagen)

            mostrar_ataques()

            espiner.visibility = View.VISIBLE
            Info.visibility = View.VISIBLE

            return
        }
        if (indice_dialog == 4) {
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_2_imagen)
            return
        }
        if (indice_dialog == 5) {
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_2_imagen)
            return
        }
        if (indice_dialog == 6) {
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_2_imagen)
            return
        }
        if (indice_dialog == 7) {
            espiner.visibility = View.GONE
            Info.visibility = View.GONE
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_4_imagen)
            return
        }
        if (indice_dialog == 8) {
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_5_imagen)
            return
        }
        if (indice_dialog == 9) {
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_6_imagen)
            return
        }
        if (indice_dialog == 10) {
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_1_imagen)
            return
        }
        if (indice_dialog == 11) {
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_7_imagen)
            return
        }
        if (indice_dialog == 12) {
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_1_imagen)
            return
        }
        if (indice_dialog == 13) {
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_8_imagen)
            return
        }
        if (indice_dialog == 14) {
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_9_imagen)
            return
        }
        if (indice_dialog == 15) {
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_1_imagen)
            return
        }
        if (indice_dialog == 16) {
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_1_imagen)
            return
        }
        if (indice_dialog == 17) {
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_1_imagen)
            return
        }
        if (indice_dialog == 18) {
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_1_imagen)
            return
        }
        if (indice_dialog == 19) {
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_9_imagen)
            return
        }
        if (indice_dialog == 20) {
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_1_imagen)
            return
        }
        if (indice_dialog == 21) {
            val dialogo = findViewById<TextView>(R.id.Dialogo_texto)
            dialogo.text = Diccionarios_textos[indice_dialog]
            val main = findViewById<ConstraintLayout>(R.id.main)
            // Cambiar el fondo en runtime
            main.setBackgroundResource(R.drawable.tutorial_1_imagen)
            return
        }
        if (indice_dialog == 22) {
            GlobalData.Desea_nimaciones = true
            GlobalData.Jugador1[0] = Rey_Espadachin(50)
            GlobalData.Jugador1[1] = Tropa_Arquero(25)
            GlobalData.Jugador1[2] = Tropa_Arquero(30)
            GlobalData.Jugador1[3] = Tropa_Lanzatonio(22)
            GlobalData.Jugador1[4] = Tropa_Gigante(25)
            GlobalData.Jugador1[5] = Tropa_Lanzatonio(22)

            GlobalData.Jugador2[0] = Rey_Heber(50)
            GlobalData.Jugador2[1] = Tropa_Curandera(35)
            GlobalData.Jugador2[2] = Tropa_Curandera(35)
            GlobalData.Jugador2[3] = Tropa_Espadachin(35)
            GlobalData.Jugador2[4] = Tropa_Lanzatonio_Medieval(30,0.2)
            GlobalData.Jugador2[5] = Tropa_Espadachin(35)

            val intent = Intent(this, Tutorial_Batalla::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            return
        }
    }
    fun atras(view: View){
        val intent = Intent(this, Iniciar_Sesion::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

}