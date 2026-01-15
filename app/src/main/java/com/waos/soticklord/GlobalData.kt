package com.waos.soticklord

import android.app.Activity
import Data.Tropa
import Data.Especiales.Rey_Cristian
import Data.Especiales.Rey_Fernando
import Data.Especiales.Rey_Heber
import Data.Especiales.Reyna_Darisce
import Data.Especiales.Reyna_Shantal
import Data.Personalizados.Rey_Aethelred
import Data.Personalizados.Rey_Borrego
import Data.Personalizados.Rey_Bufon_Negro
import Data.Personalizados.Rey_El_Pro
import Data.Personalizados.Rey_Han_Kong
import Data.Personalizados.Rey_Jerald
import Data.Personalizados.Rey_Kanox
import Data.Personalizados.Rey_Vikingo
import Data.Personalizados.Rey_Moises
import Data.Personalizados.Reyna_paranormal
import Data.Rey_Arquero
import Data.Rey_Espadachin
import Data.Rey_Lanzatonio
import Data.Rey_de_los_Gigantes
import Data.Tropa_Arquero
import Data.Tropa_Espadachin
import Data.Tropa_Gigante
import Data.Tropa_Lanzatonio
import Data.Tropas_personalizadas.Tropa_Curandera
import Data.Tropas_personalizadas.Tropa_Gato_amigo1
import Data.Tropas_personalizadas.Tropa_Gato_amigo2
import Data.Tropas_personalizadas.Tropa_Gigante_estelar
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.animation.AnimationUtils
import android.view.View
import android.content.Context
import android.media.SoundPool


object GlobalData {

    private var soundPool: SoundPool? = null
    private var sonidoInvocacion: Int = 0
    private var cargado = false

    fun initSonidos(context: Context) {
        if (soundPool != null) return

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .build()

        sonidoInvocacion = soundPool!!.load(context, R.raw.gigante_risa, 1)

        soundPool!!.setOnLoadCompleteListener { _, _, _ ->
            cargado = true
        }
    }

    fun reproducirInvocacion() {
        if (cargado) {
            soundPool?.play(
                sonidoInvocacion,
                1f, 1f,     // volumen L / R
                1,          // prioridad
                0,          // repetir
                1f          // velocidad
            )
        }
    }

    fun shake(activity: Activity) {
        val anim = AnimationUtils.loadAnimation(activity, R.anim.shake)
        val root = activity.findViewById<View>(android.R.id.content)
        root.startAnimation(anim)
    }

    fun vibrar(activity: Activity, tiempo: Long = 1500) {
        val vibrator = activity.getSystemService(Vibrator::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(tiempo, VibrationEffect.DEFAULT_AMPLITUDE)
            )
        } else {
            vibrator.vibrate(tiempo)
        }
    }

    fun oscurecer(activity: Activity) {
        val overlay = activity.findViewById<View>(R.id.overlay_negro)

        // 1. OSCURECER DE GOLPE (muy rápido)
        overlay.animate()
            .alpha(1f)
            .setDuration(80)  // casi instantáneo
            .withEndAction {
                // 2. ACLARAR POCO A POCO (3 segundos)
                overlay.animate()
                    .alpha(0f)
                    .setDuration(3000)  // lento
                    .start()
            }
            .start()
    }

    fun invocacion(activity: Activity) {
        shake(activity)
        vibrar(activity)
        oscurecer(activity)
    }

    var jugador11 = false
    var jugador22 = false



    var Jugador2 = ArrayList<Tropa?>().apply {
        add(null) // posición 0 (rey)
        add(null) // posición 1 (tropa)
        add(null) // posición 2 (tropa)
        add(null) // posición 3 (tropa)
        add(null) // posición 4 (tropa)
        add(null) // posición 5 (tropa)
    }
    var Jugador1 = ArrayList<Tropa?>().apply {
        add(null) // posición 0 (rey)
        add(null) // posición 1 (tropa)
        add(null) // posición 2 (tropa)
        add(null) // posición 3 (tropa)
        add(null) // posición 4 (tropa)
        add(null) // posición 5 (tropa)
    }

    var Diccionario_Reyes = hashMapOf<Int, Tropa>()
    var Diccionario_Tropas = hashMapOf<Int, Tropa>()

    var Diccionario_Reyes_enemigos = hashMapOf<Int,Tropa>(
        0 to Rey_Bufon_Negro(10,false),
        1 to Rey_Espadachin(10),
        2 to Rey_Arquero(10),
        3 to Reyna_Shantal(10),
        4 to Rey_Cristian(10),
        5 to Rey_Heber(10),
        6 to Reyna_Darisce(10),
        7 to Rey_Lanzatonio(10),
        8 to Rey_Han_Kong(10),
        9 to Rey_Jerald(10),
        10 to Rey_Vikingo(10),
        11 to Rey_Aethelred(10),
        12 to Rey_Moises(10),
        13 to Reyna_paranormal(10),
        14 to Rey_de_los_Gigantes(10),
        15 to Rey_Borrego(10),
        16 to Rey_Fernando(10),
        17 to Rey_El_Pro(10),
        18 to Rey_Kanox(10)
    )

    var Diccionario_Reyes_amigos = hashMapOf<Int,Tropa>(
        0 to Rey_Bufon_Negro(10,true),
        1 to Rey_Espadachin(10),
        2 to Rey_Arquero(10),
        3 to Reyna_Shantal(10),
        4 to Rey_Cristian(10),
        5 to Rey_Heber(10),
        6 to Reyna_Darisce(10),
        7 to Rey_Lanzatonio(10),
        8 to Rey_Han_Kong(10),
        9 to Rey_Jerald(10),
        10 to Rey_Vikingo(10),
        11 to Rey_Aethelred(10),
        12 to Rey_Moises(10),
        13 to Reyna_paranormal(10),
        14 to Rey_de_los_Gigantes(10),
        15 to Rey_Borrego(10),
        16 to Rey_Fernando(10),
        17 to Rey_El_Pro(10),
        18 to Rey_Kanox(10)
    )

    var Diccionario_Tropas_enemigos = hashMapOf<Int,Tropa>(
        0 to Tropa_Gigante(10),
        1 to Tropa_Curandera(10),
        2 to Tropa_Arquero(10),
        3 to Tropa_Gigante_estelar(10),
        4 to Tropa_Espadachin(10),
        5 to Tropa_Lanzatonio(10),
        6 to Tropa_Gato_amigo2(10),
        7 to Tropa_Gato_amigo1(10)

    )
    var batalla: Activity? = null

    val Diccionario_Clases = mapOf(
        //defaults
        "Rey_Arquero" to Data.Rey_Arquero::class,
        "Rey_Espadachin" to Data.Rey_Espadachin::class,
        "Rey_Lanzatonio" to Data.Rey_Lanzatonio::class,
        "Rey_de_los_Gigantes" to Data.Rey_de_los_Gigantes::class,
        "Rey_Gigante_Bufon_Negro" to Data.Especiales.Rey_Gigante_Bufon_Negro::class,

        "Tropa_Arquero" to Data.Tropa_Arquero::class,
        "Tropa_Espadachin" to Data.Tropa_Espadachin::class,
        "Tropa_Lanzatonio" to Data.Tropa_Lanzatonio::class,
        "Tropa_Gigante" to Data.Tropa_Gigante::class,

        //Especiales
        "Rey_Heber" to Data.Especiales.Rey_Heber::class,
        "Rey_Fernando" to Data.Especiales.Rey_Fernando::class,
        "Rey_Cristian" to Data.Especiales.Rey_Cristian::class,
        "Reyna_Darisce" to Data.Especiales.Reyna_Darisce::class,
        "Reyna_Shantal" to Data.Especiales.Reyna_Shantal::class,
        "Rey_Bufon_Negro" to Data.Personalizados.Rey_Bufon_Negro::class,
        "Rey_Bufon_Negro_Navideño" to Data.Especiales.Rey_Bufon_Negro_Navideño::class,

        //personalizados
        //creados

        "Rey_El_Pro" to Data.Personalizados.Rey_El_Pro::class,


        //torneo
        "Rey_Borrego" to Data.Personalizados.Rey_Borrego::class,
        "Reyna_paranormal" to Data.Personalizados.Reyna_paranormal::class,
        "Rey_Kanox" to Data.Personalizados.Rey_Kanox::class,
        "Rey_Vikingo" to Data.Personalizados.Rey_Vikingo::class,
        "Rey_Jerald" to Data.Personalizados.Rey_Jerald::class,
        "Rey_Moises" to Data.Personalizados.Rey_Moises::class,
        "Rey_Han_Kong" to Data.Personalizados.Rey_Han_Kong::class,
        "Rey_Aethelred" to Data.Personalizados.Rey_Aethelred::class,
        "Rey_Vago_de_Vagos" to Data.Especiales.Rey_Vago_de_Vagos::class,

        //tropas fichas
        "Tropa_Gigante_estelar" to Data.Tropas_personalizadas.Tropa_Gigante_estelar::class,
        "Tropa_Curandera" to Data.Tropas_personalizadas.Tropa_Curandera::class,
        "Tropa_Gato_amigo2" to Data.Tropas_personalizadas.Tropa_Gato_amigo2::class,
        "Tropa_Gato_amigo1" to Data.Tropas_personalizadas.Tropa_Gato_amigo1::class,
        "Tropa_Lanzatonio_Medieval" to Data.Tropas_personalizadas.Tropa_Lanzatonio_Medieval::class,
        "Tropa_Bufon" to Data.Tropas_personalizadas.Tropa_Bufon::class

    )

    var Desea_nimaciones: Boolean = false

    //Contrataque
    val Diccionario_Ataques = mapOf(
        "Estrendor" to
                """
        Una ves por partida ,bajas a todos
        un 12% de defensa e inglijes un daño
        igual a tu ataque base
                """.trimIndent() ,
        "Fuerza_elite" to
                """
        Invocas 3 gigantes y 2 
        lanzatonios medievales 
                """.trimIndent() ,
        "Contrataque" to
                """
        Devuelve el ultimo golpe 
        directo q le hicieron como
        si fuera su ataque base
                """.trimIndent() ,
        "Ultima_Invocacion" to
                """
        Una ves por partida y despues
        de aver realizado 10 ataques 
        previos,
        Invocas 3 reyes espadachines 
        y 2 Reyes Arqueros
                """.trimIndent() ,
        "Trompeta_del_Reino" to
                """
        Todos obtienen 20% de espinas y
        10% de vida
                """.trimIndent() ,
        "Mandato_del_Castillo" to
                """
        Todas las tropas obtienen 
        +10% de defensa y +10% de vida
                """.trimIndent() ,
        "Carga_real" to
                """
        Aumentas tu defensa en  un 15%
        si la defensa llega a ser mayor o igual
        a 150% ,tu defensa baja a 50% e 
        invocas 5 guardias medievales con 50%
        de defensa cada uno
                """.trimIndent() ,
        "Invocacion_antes_de_Tiempo" to
                """
        Q crees q sucedera?
                """.trimIndent() ,
        "Capsula_de_Locura" to
                """
        Inflijes daño directo reducido
        en un 50% a todos, si tu vida
        es menor q el 50% iflijes daño
        directo x2 a todos en su lugar
                """.trimIndent() ,
        "Mascara_Maldicta" to
                """
        El enemigo recive -20%
        de ataque y presicion,
        si su defensa es baja 
        recivira -50% en ambos 
                """.trimIndent() ,
        "Sombras_Traviesas" to
            """
        Dos sombras tuyas inflijen daño
        directo e ignorando defensas,
        obtienes +10% de vida y ataque
                """.trimIndent() ,
        "Risa_desgarradora" to
                """
        Infliges daño de ataque_base y 
        existe una probabilidad del 25% 
        de hacerle perder turno,
        obtienes +10% de vida
                """.trimIndent() ,
        "Invocacion_de_guardia" to
                """
        Invocas un Lanzatonio_Medieval
        y obtienes +5% de defensa
                """.trimIndent() ,
        "Oscuridad_Negra" to
                """
        Obtienes +30% de vida 
        y un 5% de defensa
                """.trimIndent() ,
        "Contrataque_Negro" to
                """
        Devuelves el ultimo golpe 
        directo recivido , pero multiplicado
        en un x2 (Si no recivistes algun
        golpe directo efectuaras daño normal)
                """.trimIndent() ,
        //----------------------------
        "Gran_puño" to
                """
        Inflijes daño directo al enemigo 
        multipicado *3 pero pierdes 50pv
                """.trimIndent() ,
        //----------------------------
        "Ataque_normal_curativo" to
                """
        Inflijes daño directo y curas a todos 
        llos aliados +130pv
                """.trimIndent() ,
        //----------------------------
        "uiiaiuiiiai" to
                """
        Inflijes un daño de 50% de tu ataque base
        +15% por cada nivel 
        a todos los enemigos
                """.trimIndent() ,
        //----------------------------
        "Rasguño" to
                """
        Inflijes daño normal al enemigo
                """.trimIndent() ,
        //----------------------------
        "Miasculu" to
                """
        Creas un daño aleatorio (entre 1 y tu ataque base) +15%
        por cada nivel ,y lo inflijes a todos
        los enemigos
                """.trimIndent() ,
        //----------------------------
        "Transformacion" to
                """
        Te conviertes en el rey enemigo +5 niveles
                """.trimIndent() ,
        //----------------------------
        "Porque_tan_solo" to
                """
        Inflijes daño normal al enemigo
        (este golpe no depende de presision)
                """.trimIndent() ,
        //---------------------------
        "Ataque_normall" to
                """
        Un Atque normal q inflije un daño respecto
        a tu ataque base
                """.trimIndent() ,
        //---------------------------

        "Ascensión_del_Rey_Inmortal" to
                """
        Una ves por partida (Situacional)
        inflijes un daño x1.5 al enemigo ,si este 
        muere se recarga la habilidad se puede
        volver a usar y tu ataque base aumenta 
        en un 50%
                """.trimIndent() ,
        //---------------------------
        "Risa_del_Caos_Eterno" to
                """
        Tu vida aumenta en un 30% , inlfijes daño
        al rival y el proximo golpe q recivas se
        incrementara a tu ataque base
                """.trimIndent() ,
        //---------------------------
        "Golpe_del_Sol_Primordial" to
                """
        Tu ataque base se incrementa en un 30% , 
        inflijes un daño al rival y recargaras tu vida
        un 60% del daño inflijido
                """.trimIndent() ,
        //---------------------------
        "Clones_de_Sombrasque" to
                """
        La probabilidad de poder esquivar aumenta 
        en un 45%, si se llega a esquivar un ataque
        la probabilidad de esquivar baja 20%
                """.trimIndent() ,
        //---------------------------
        "Grito_del_Dragón_Dorado" to
                """
        (ignora defensa y espinas)
        Inflijes daño al enemigo asimismo reduces 
        su taque base en un 20% y su defensa en 
        un 20% tambien
                """.trimIndent() ,
        //---------------------------
        "Nube_Voladora" to
                """
        Inflijes un daño x2 al enemigo
        ignoras defensa y espinas
        (presison no necesaria)
                """.trimIndent() ,
        //---------------------------
        "Bastón_del_Cielo_Expandido" to
                """
        Todos los enemigos reciven daño
        y ahi un 10% de probabilidad de
        hacerle perder turno
                """.trimIndent() ,
        //---------------------------
        "Égida_del_Eón" to
                """
        Todas tus tropas VIVAS moriran ,pero te 
        eredaran toda su vida, ademas recives
        +25% de defensa
                """.trimIndent() ,
        //---------------------------
        "Campo_de_Estasis" to
                """
        Creas un entorno el caul todos los enemigos
        receiben un daño igual al 8% de su ataque base
                """.trimIndent() ,
        //---------------------------
        "Sincronización_Forzada" to
                """
        Inflijes un daño  de tu ataque base
        multiplicado x2.5 y haces perder un turno,
        y existe una probabilidad del 20% de 
        realizar otro ataque extra
                """.trimIndent() ,
        //---------------------------
        "Lanza_de_Cristal_Puro" to
                """
        Inflijes un daño de tu ataque base
        multiplicado x2.5
                """.trimIndent() ,
        //---------------------------
        "Ataque_Refracción_Temporal" to
                """
        Un enemigo pierde turno, si tienes
        fragmento de geoda efectuas daño y lo 
        recuperas como salud
                """.trimIndent() ,
        //---------------------------
        "Geoda_Arcana" to
                """
        A la suerte:
        - Todos los aliados obtienen +250 pv
        - Todos los enemigo reciven un daño de 
        tu aatque base
        - Obtienes turno doble y un fragmento de Geoda
                """.trimIndent() ,
        //---------------------------
        "Muralla_de_Cuarzo" to
                """
        (Una ves por partida)
        aumentas la defensa de todos los 
        aliados en un 35% (incluyendote)
                """.trimIndent() ,
        //---------------------------
        "El_Último_Mandamiento" to
                """
       (Una ves por partida) , eliminas a un 
        enemigo de una ,si es el rey , le 
        bajas su vida en un 65%
                """.trimIndent() ,
        //---------------------------
        "Caminar_sobre_el_Vacío" to
                """
                    
        Te vuelves de tipo aereo y el enemigo de
        tipo terrestre, obtienes + 400pv y 
        efectuas el ultimo daño q recivistes
                """.trimIndent() ,
        //---------------------------
        "Mandamiento_del_Caos" to
                """
        (Una ves por partida)
        todos resiven un daño de 150 puntos 
        y su precicion se baja en un 10%
                """.trimIndent() ,
        //---------------------------
        "Plaga_de_Oscuridad" to
                """
        durante 4 turnos todos sufren un daño 
        de 150 por las plagas q estan en el 
        terreno
                """.trimIndent() ,
        //---------------------------
        "Separación_del_Mar" to
                """
        (una ves por partida)
        todos los enemigos perderan 25 puntos 
        de vida, 25 puntos de ataque base
        y 25% de daño critico
                """.trimIndent() ,
        //---------------------------
        "Mandamiento_del_Silencio" to
                """
        El ataque_base del enemigo se reduce 
        en un 25% ,su defensa baja en un 20%
        y pierde su siguiente turno
                """.trimIndent() ,
        //---------------------------
        "Mar_Rojo_del_Fin" to
                """
        Creas un entorno el cual bajara 5% de
        vida a todos los enenmigos por turno
        haci mismo tambien se les bajara un 1% 
        de precision
                """.trimIndent() ,
        //---------------------------

        "Atropello" to
                """
        una ves por partida inflijes un
        daño igual de tu ataque base a
        todos y al rey le hacer perder un 
        70% de defensa ,un 25%
        de vida y ataque y un daño extra
                """.trimIndent() ,
        //---------------------------
        "Sube_de_fase" to
                """
        Una ves por paartida tu ataque se 
        incrementa en un 150% y tus 
        enemigos se reduce su vida en 
        un 30%
       
                """.trimIndent() ,
        //---------------------------
        "invocacion_de_gatos" to
                """
        (Una ves por partida)
        invocas una manada de gatos, 
        si existen tropas vivas
        los gatos tomaran su lugar
       
                """.trimIndent() ,
        //---------------------------
        "Ballesta" to
                """
        Te equipas una ballesta q inglijira
        un daño igual a tu ataque base a todos
        los enemigos
                """.trimIndent() ,
        //---------------------------
        "Llamada_De_perro" to
                """
        Una ves por partida , invocas a un ser 
        superiro ,q les otorgara +500 de vida a
        todas tus tropas, y seguidamente su vida
        se duplicara
                """.trimIndent() ,
        //---------------------------
        "Modo_cautel" to
                """
        Tu vida se aumentara + 25% y tu ataque
        base se duplicara
                """.trimIndent() ,
        //---------------------------
        "Salto_de_soga" to
                """
        El enemigo sufre sangrado
        y perdera 100 de vida por cada
        cierto turno
                """.trimIndent() ,
        //---------------------------
        "Rompimiento_de_Fe" to
                """
        inflijes daño directo e 
        inflijes -25% de defensa
                """.trimIndent() ,
        //---------------------------
        "Golpe_Helado_del_Leviatán" to
                """
        Inflijes un daño de 130% a un enemigo,
        con una probabilidad del 10% de congelar 
        al rival (perder turno) asi mismo si 
        llegara a ser congelado obtienes +20%
        de presicion y 100 puntos de ataque
                """.trimIndent() ,
        //---------------------------
        "Torbellino_de_Njord" to
                """
        haces un daño igual del 120% de ataque
        base a todos los enemigos , y les 
        reduces un 15%de defensa,obtienes 
        +10% de defensa
                """.trimIndent() ,
        //---------------------------
        "Filo_del_Caos" to
                """
        Durante 2 turnos los enemigos sufren un 
        daño igual al 15% de su vida restante
                """.trimIndent() ,
        //---------------------------
        "Aplastamiento_de_Nemea" to
                """
        (si tienes mas de 800 pv)
        Todos los enemigos sufren daño pero 
        reducido en un 50%,y pierden 8% de 
        defensa y sufren un daño igual al 25%
        de su vida restante durnate 2 turnos,
        y pierdes 100 pv
                """.trimIndent() ,
        //---------------------------
        "Carga_de_raupnir" to
                """
        Un enemigo recive un daño x2 , y una 
        probabilidad del 18% de hacerle 
        perder turno y obtener +20% de presicion
                """.trimIndent() ,
        //---------------------------
        "Artillería_de_los_Antiguos" to
                """
        Todas las tropas obtiene -30% de defensa
        y sufren un daño aumentado en un 180%
                """.trimIndent() ,
        //---------------------------
        "Juicio_de_los_Dioses" to
                """
        Una ves por partida ,inglijes un daño x3 
        a todos los enemigos, con una probabilidad 
        del 25%de q pierdan turno y tu ganes un 
        25% de vida
                """.trimIndent() ,
        //---------------------------
        "bye_bye" to
                """
        El enemigo se transforma en un Rey espadachin
                """.trimIndent() ,
        //---------------------------
        "Angeles_Demoniacos" to
                """
        Dos Angeles inflijiran daño directo
        al enemigo seleccionado
                """.trimIndent() ,
        //---------------------------
        "Envenenamiento" to
                """
        Durante 5 turnos un enemigo pierde 100 de vida
                """.trimIndent() ,
        //---------------------------
        "Reflejo_Maldito" to
                """
        Copias la vida del rey enemigo 
        (varias veces por partida)
                """.trimIndent() ,
        //---------------------------
        "Control_Sombra" to
                """
        Invocas un entorno sombrio 
        enemigos : -10% de vida , -10% de Ataque 
        Base y precicion -1%
        tu: +20% precicion +10% escudo +40% de 
        daño critico y mas 2% de espinas
        aliados : + 10% de vida
                """.trimIndent() ,
        //---------------------------
        "Juguemos_un_juego" to
                """
        85% de probabilidad de inflijir un daño x2
        al enemigo, de lo contrario le bajas la 
        precision un 10%
                """.trimIndent() ,
        //---------------------------
        "Susto" to
                """
        Bajas la precision de acertar
        un ataque a todos en un 8% 
                """.trimIndent() ,
        //---------------------------
        "Puño_del_Dragón" to
                """
        Si el enemigo esta con  defensa baja 
        el siguiente golpe bajara un daño 
        multipplicado x5
                """.trimIndent() ,
        //---------------------------
        "Destrucción_Divina" to
                """
        Si no tienes ninguna tropa viva
        el enemigo pierde un 70% de su vida
        seguidamente haces un daño de tu ataque base
                """.trimIndent() ,
        //---------------------------
        "CORTE_DE_ESCALIBUR" to
                """
        Inflijees un daño igual a tu
        ataque base a todos los 
        enemigos y bajas su defensa en un 5%
                """.trimIndent() ,
        //---------------------------
        "Hakai" to
                """
        Ahi una probabilidad del 65% 
        de hacer perder turno a una
        tropa del rival e inflijirle
        daño elevado en un 35%
                """.trimIndent() ,
        //---------------------------
        //---------------------------
        "Aguja_Escarlata" to
                """
        Si el enemigo tiene menos o igual a 750
        de vida, morira instantaneamente
                """.trimIndent() ,
        //---------------------------
        "Ultra_Instinto" to
                """
        Existe una probabilidad del 50%
        de esquivar el siguiente ataque
        y recivir +10% de defensa
                """.trimIndent() ,
        //---------------------------
        "Ataque_normal" to
                """
        Ataque normal q inflije daño 
        igual al Ataque Base
                """.trimIndent() ,
        //---------------------------
        "Disparo_Real" to
                """
        La Probabilidad de hacertar un golpe
        x5 es la mitad de la probabilidad de 
        hacertar un golpe critico
                """.trimIndent() ,
        //---------------------------
        "Flecha_Explosiva" to
                """
        Ahi una probabilidada del %20 de fallar
        el ataque y sufrir un daño igual al 
        Ataque base, de lo contraio Inflijes 
        un daño x4 al enemigo
                """.trimIndent() ,
        //---------------------------
        "Furia_Del_Rey" to
                """
        Tu vida se incrementa en un 20%,
        Tu Ataque Base se incrementa en un 15%,
        La Probabilidad de hacertar un golpe critico
        aumenta un 10%,
        EL daño critico aumenta en un 15%
                """.trimIndent() ,
        //---------------------------
        "Invocacion_de_Gigantes" to
                """
        (Una ves por partida)
        Invocas A 5 gigantes respecto a tu nivel,
        los 5 gigantes vendran aplastando todo,
        haci q destruiran tus tropas vivas restantes
                """.trimIndent(),
        //---------------------------
        "Espadazo_Real" to
                """
        Sufres un daño al 10% de tu salud restante
        pero tu daño aumenta un x2 este turno 
                """.trimIndent(),
        //---------------------------
        "En_Guardia" to
                """
        Tu vida aumenta en un 15% de tu salud 
        restante
                """.trimIndent(),
        //---------------------------
        "Ataque_Final" to
                """
        Tu vida se reduce en un 10% de tu salud 
        restante pero tu daño aumenta x4 este 
        turno
                """.trimIndent(),
        //---------------------------
        "Lanza_Imperial" to
                """
        Realizar un Ataque x2 este turno 
                """.trimIndent(),
        //---------------------------
        "Bloqueo_real" to
                """
        Tu vida aumenta en un 25% de tu vida restante
                """.trimIndent(),
        //---------------------------
        "Tormenta_de_Lanzas" to
                """
        Tu Ataque Base se reduce a 10 durante 
        este turno, realizas 25 ataques 
        seguidos de ataque normal
        (La probabilidad de critico y daño critico
        ahun se conserva)
                """.trimIndent(),
        //---------------------------
        "Flecha_de_Sangre" to
                """
        Tu vida baja un 10% de tu vida restante,
        Inflijes un daño x3 durante este turno
        
                """.trimIndent(),
        //---------------------------
        "Flecha_penetrante" to
                """
        Probabilidad del 20% de hacertar un
        la flecha x5 de daño , de lo contrario
        inflijaras la mitad de tu Ataque Base
                """.trimIndent(),
        //---------------------------
        "Estocada_Veloz" to
                """
        Pierdes un 10% de tu vida restante,
        inflijes un daño x2 este turno
                """.trimIndent(),
        //---------------------------
        "Terremoto" to
                """
        Inflijes un daño de 25% de tu 
        ataque base + 15% extra por cada 
        nivel del gigante,a todas los
        tropas enemigas
                """.trimIndent(),
        //---------------------------
        "Estocada" to
                """
        Probabilidad del 30% de inflijer un daño x3 al enemigo
        de lo contrario se inflijira tu ataque base sin critido
                """.trimIndent(),
        //---------------------------
        "Bloqueo" to
                """
        Tu vida Aumenta en un 15% de tu vida restante
                """.trimIndent(),
        //---------------------------
        "Donde_corress" to
                """
        (Ignoras Espinas y escudo)
        La vida del enemigo se reduce a 1!
                """.trimIndent(),
        //---------------------------
        "Soy_inevitable" to
                """
        Tu vida aumenta + 10 000
                """.trimIndent(),
        //---------------------------
        "Jugamos_jaja" to
                """
        (Ignoras Espinas y escudo)
        Alteras las Stats del oponente por:
        Ataque base = 10
        Vida = 10
                """.trimIndent(),
        //---------------------------
        "Estaca_Feroz" to
                """
        Haces un daño x4 al enemigo,
        Pierdes un 5% de tuvida maxima
                """.trimIndent(),
        //---------------------------
        "Clavada_de_pico" to
                """
        Todos los enemigos reciven un daño igual
        a tu Ataque Base
                """.trimIndent(),
        //---------------------------
        "la_muerte_no_es_una_opcion" to
                """
        Aumentas tu vida en +500
                """.trimIndent(),
        //---------------------------
        "mejoramiento" to
                """
        Tu vida aumenta en +500
        tu Ataque base en +500
        Tu Daño critico +50%
        La Probabilidad de hacertar critico
        se incremetna en un 5% mas
                """.trimIndent(),
        //---------------------------
        "Tiro_Perfecto" to
                """
        Haces un daño del 1000% de tu Ataque Base
                """.trimIndent(),
        //---------------------------
        "Lluvia_de_Flechas_Magicas" to
                """
        (Ignora Espinas y Escudo)
        Inflijes la mitad de tu Ataque base a todos
        los enemigos
                """.trimIndent(),
        //---------------------------
        "Compañia_cercana" to
                """
        Todas tus tropas incluyendote 
        reciven + 25% de vida respecto
        a su vida restante
                """.trimIndent(),
        //---------------------------
        "Bendicion_del_cetro" to
                """
        Todos los enemigos reciven un 50% de tu ataque base,
        el 30% inflijido lo curas a todos tus aliados
                """.trimIndent(),
        //---------------------------
        "Golpe_del_Reyno" to
                """
        Pierdes el 50% de tu vida restante,
        El enemigo pierde el 50% de su vida restante
        + la cantidad de tu vida arrebatada
                """.trimIndent(),
        //---------------------------
        "Juicio_Celestial" to
                """
        (Una ves por partida)
        Todaas tus tropas q esten muertas
        reviven al 100% 
                """.trimIndent(),
        //---------------------------
        "Back_on_de_bit" to
                """
        Todas las tropas enemigas reducen
        un 15% de su vida y su ataque 
        restante
                """.trimIndent(),
        //---------------------------
        "king_Crimson" to
                """
        (Una ves por partida)
        invocas
        3 Gigantes estelares y 
        2 curanderas cosmicas
                """.trimIndent(),
        //---------------------------
        "TUSK" to
                """
        Si todas las tropas aliadas estan 
        muertas , las tropas restantes del 
        rival se pasan a tu bando
                """.trimIndent(),
        //---------------------------
        "Diamond" to
                """
        Probabilidad del 50% de revivir una
        tropa aliada aleaotrio muerta con un
        50% de vida y 5% con su 100% de vida
                """.trimIndent(),
        //---------------------------
        "Calamidad" to
                """
        Transformas a una tropa del rival 
        en un tropa de nivel 1
                """.trimIndent(),
        //---------------------------
        "Killer_Queen" to
                """
        Una tropa aleatoria del bando enemigo
        morira de forma instantanea, a cambio
        otra tropa se curara al 100% y perdera
        un 20% de punteria
                """.trimIndent(),
        //---------------------------
        "Golden" to
                """
        Una ves por partida, te volveras inmune 
        a buffs y efectos de combate, tus stats 
        no se podras modificar 
                """.trimIndent(),

    )

    val Diccionario_Habilidades = mapOf(
        "Tropa_Bufon" to
                """
        Existe una probabilidad
        del 50% de devolver un Ataque
        directo y no sufrir daño
                """.trimIndent() ,
        "Rey_Vago_de_Vagos" to
                """
        Comienzas el combate con 100%
        de defensa, al realizar algun
        ataque invocas a un Lnazatonio
        Medieval con 60% de defensa
                """.trimIndent() ,
        "Rey_Aethelred" to
                """
        Es imposible perder turno,
        obtiene + 50 pv y un 10% 
        de ataque cada turno q pase
                """.trimIndent() ,
        //----------------------------------
        "Rey_Bufon_Negro_Navideño" to
                """
        Al recivir un daño directo 
        existe una probabilidad del
        40% de devolver todo el daño
        y una probabiblidad del 75%
        de Recivir todo el daño
        "Piensas acaso bajarme nivel?"
                "Intentalo"
                """.trimIndent() ,

        "Rey_Bufon_Negro" to
                """
        Al recivir un daño directo 
        existe una probabilidad del
        40% de devolver todo el daño
        y una probabiblidad del 75%
        de Recivir todo el daño
        "Piensas acaso bajarme nivel?"
                "Intentalo"
                """.trimIndent() ,
        //----------------------------------
        "Rey_Arquero" to
                """
        Al recivir algun tipo
           de daño directo,
        devolved un golpe de 
                daño 
                """.trimIndent() ,
        //----------------------------------
        "Rey_Cristian" to
                """
        Si Alguna tropa muere,
            eliminad a una
        tropa enemiga aleatoria
                """.trimIndent() ,
        "Rey_Kratos" to
                """
        Una ves por partida, 
        Al recivir un daño
        directo q no haga morir,
        revivid con 100pv,
        30% de defensa y 
        +100 de ataque extra
                """.trimIndent() ,
        //----------------------------------
        "Rey_Kanox" to
                """
        Por cada turno q 
        pase, existe una 
        probabilidad del 
        45% de subir tu 
        vida y ataque base
        en un 8%
                """.trimIndent() ,
        //----------------------------------


        )

    val Diccionarios_textos = hashMapOf<Int,String>(
        //Común
        //Poco Común
        //Raro
        //Especial
        //Épico
        //Legendario
        //Mítico
        //Ancestral
        //Divino
        //Supremo
        0 to """
            Soticklord 
        """.trimIndent(),
        1 to """
                            Creyentes_Arquidonios
            Rareza: ⚡⚡
            - Poco Común
            Afinidad: 
            - Arco y flechas
            Estilo de combate:
            - A Ditancia 
            Habilidad Distintiva: 
            - Su presicion suele ser de 150% a mas
            Debilidad:
            - Muy expuestos y baja armadura
        """.trimIndent(),
        2 to """
                            Creyentes_Espadachines                       
            Rareza: ⚡⚡
             - Poco Común
            Afinidad: 
            - Espadas y Resistenica
            Estilo de combate:
            - Cuerpo a cuerpo
            Habilidad Distintiva: 
            - Criticos fuertes 
            Debilidad:
            - Debilitamientos 
        """.trimIndent(),
        3 to """
                            Creyentes_Lanzatonio                                      
            Rareza: ⚡⚡⚡
             - Raro <-> Especial
            Afinidad: 
            - Lanzas y Escudos
            Estilo de combate:
            - Cuerpo a cuerpo y en ocaciones a distancia
            Habilidad Distintiva: 
            - Suelen tender defensa elevada y Alta vida
            Debilidad:
            - Ataques magicos
        """.trimIndent(),
        4 to """
                            Creyentes_Magitasanos                                    
            Rareza: ⚡⚡⚡⚡
             - Especial
            Afinidad: 
            - Magia
            Estilo de combate:
            - Soporte, Defensivo ,Tacticos
            Habilidad Distintiva: 
            - Hechizos e invocaciones
            Debilidad:
            - Tiempistas
        """.trimIndent(),
        5 to """
                            Creyentes_de_Gigantes
            Rareza: ⚡⚡⚡⚡
             - Especial
            Afinidad: 
            - Destruccion
            Estilo de combate:
            - Ofensivo ,Cuerpo a cuerpo
            Habilidad Distintiva: 
            - Daño en area,ataque fuerte y vida elevada
            Debilidad:
            - Magia y Debilitamientos
        """.trimIndent(),
        6 to """
                            Seguidores_de_Fernando
            Rareza: ⚡⚡⚡⚡⚡⚡⚡
             - Mitico
            Afinidad: 
            - Destruccion Recuperativa
            Estilo de combate:
            - Ofensivo ,Cuerpo a cuerpo,A distancia
            Habilidad Distintiva: 
            - Daño en area,Ataques fuerte y Vida elevada
            Debilidad:
            - Seguidores de Heber
        """.trimIndent(),
        7 to """
                            Seguidores_de_Cristian
            Rareza: ⚡⚡⚡⚡⚡⚡⚡
             - Mitico
            Afinidad: 
            - Conbinatoria
            Estilo de combate:
            - Ofensivo y Defensivo
            Habilidad Distintiva: 
            - Inmunidad,Magia,Debilitamiento,etc
            Debilidad:
            - Estrategia
        """.trimIndent(),
        8 to """
                            Seguidores_de_Darisce
            Rareza: ⚡⚡⚡⚡⚡⚡⚡
             - Mitico
            Afinidad: 
            - Estrategia
            Estilo de combate:
            - Defensivo,Cuerpo a cuerpo
            Habilidad Distintiva: 
            - Estrategia
            Debilidad:
            - Estrategia
        """.trimIndent(),
        9 to """
                            Seguidores_de_Shantal
            Rareza: ⚡⚡⚡⚡⚡⚡⚡
             - Mitico
            Afinidad: 
            - Arco y Estrategia
            Estilo de combate:
            - Muy ofensivo ,Cuerpo a cuerpo y a Distancia
            Habilidad Distintiva: 
            - Daño exagerado
            Debilidad:
            - Debilitamiento 
           
        """.trimIndent(),
        10 to """
                         Seguidores_de_Heber
            Rareza: ⚡⚡⚡⚡⚡⚡⚡⚡⚡
             - Divino
            Afinidad: 
            - Alterar la Realiadad
            Estilo de combate:
            - Soporte
            Habilidad Distintiva: 
            - Alteración
            Debilidad:
            - No comenzar primero y poca vida
        """.trimIndent(),
        11 to """
                            Creyentes_Bufones
            Rareza: ⚡⚡⚡⚡⚡
             - Epico
            Afinidad: 
            - Caos Controlado
            Estilo de combate:
            - Defensivo Ofensivo
            Habilidad Distintiva: 
            - Esquivar y devolver golpes
            Debilidad:
            - Magia      
        """.trimIndent(),
        12 to """
                            Creyentes_Tiempistas
            Rareza: ⚡⚡⚡⚡⚡
             - Epico
            Afinidad: 
            - Relojes y tiempo
            Estilo de combate:
            - Soporte
            Habilidad Distintiva: 
            - Perdida y ganancia de Turnos
            Debilidad:
            - Muy poca vida
        """.trimIndent(),
        13 to """
                            Creyentes_Paranormales
            Rareza: ⚡⚡⚡⚡⚡⚡
             - Legendario
            Afinidad: 
            - Oscuridad
            Estilo de combate:
            - Ofensivos Agresivos
            Habilidad Distintiva: 
            - No necesitan Presicion
            Debilidad:
            - Quien sabe
        """.trimIndent(),
        14 to """
                            Creyentes_Judios
            Rareza: ⚡⚡⚡⚡⚡
             - Epico
            Afinidad: 
            - Resurrectivos
            Estilo de combate:
            - Soporte,Defensivo,Ofensivo
            Habilidad Distintiva: 
            - Poder para resucitar
            Debilidad:
            - Oscuridad 
        """.trimIndent(),
        15 to """
                            Creyentes_Iluminados
            Rareza: ⚡⚡⚡⚡⚡
             - Epico
            Afinidad: 
            - Variado
            Estilo de combate:
            - Variado
            Habilidad Distintiva: 
            - Variedad
            Debilidad:
            - Variado
        """.trimIndent(),
        16 to """
                            Creyentes_Constructores
            Rareza: ⚡⚡⚡⚡⚡⚡
             - Legendaria
            Afinidad: 
            - Contruccion
            Estilo de combate:
            - Cuerpo a cuerpo,a distancia, ofensivos
            Habilidad Distintiva: 
            - Creacion de Maquinas
            Debilidad:
            - Tiempistas
        """.trimIndent(),
        17 to """
                            Creyentes_Superheroes
            Rareza: ⚡⚡⚡⚡⚡⚡
             - Legendaria
            Afinidad: 
            - Fuerza Estrategica
            Estilo de combate:
            - Super Ofensivos
            Habilidad Distintiva: 
            - Estrategicos ofensivos
            Debilidad:
            - Debilitamientos  
        """.trimIndent(),
        18 to """
                            Creyentes_Transformicadores
            Rareza: ⚡⚡⚡⚡⚡⚡⚡⚡⚡
             - Ancestral <-> Divino
            Afinidad: 
            - Mutacion
            Estilo de combate:
            - Tactico Caotico
            Habilidad Distintiva: 
            - Transmutacion
            Debilidad:
            - Seguidores de Heber
        """.trimIndent(),
    )

    val Diccionario_miniInfo = hashMapOf<Int, String>(
        0 to """
            En un tiempo incierto —o quizá en todos al mismo tiempo— los mundos colapsaron hasta revelar una única verdad: el poder ya no provenía de dioses, sino de las propias armas.
            No eran objetos inertes, sino reliquias vivientes, conscientes, capaces de alterar la realidad, influir en el destino y moldear la existencia de quienes las encontraban.
            A medida que el multiverso se fragmentaba, civilizaciones, criaturas y fuerzas desconocidas comenzaron a venerar estas reliquias como su nueva fe. Cada arma otorgaba
            habilidades diferentes a sus seguidores, convirtiéndolos en guerreros, visionarios o seres irreconocibles.
            Así surgieron las religiones-armas: sistemas de creencias basados no en lo espiritual, sino en la voluntad de una reliquia que exigía devoción total.
            En este universo inestable, cada facción lucha por proteger su verdad, expandir su influencia o evitar que otra religión-arma reclame su realidad como territorio sagrado.
            Aquí, las armas no se usan. Se adoran.
        """.trimIndent(),
        //En un tiempo remoto —o quizá en todos los tiempos a la vez— los mundos colapsaron en una sola verdad universal: el poder ya no provenía de dioses, sino de las
        1 to """
            Los Arquidonios son seguidores del Ojo Celestial, una antigua arma–entidad que, según sus escrituras, nació del primer destello de luz que atravesó el vacío
            del mundo. Creen que cada flecha disparada es un “destino trazado”, y que el universo puede ser comprendido a través de líneas rectas, trayectorias perfectas
            y cálculos divinos.
            Desde temprana edad, los acólitos entrenan para ver más allá de lo visible: aprenden a detectar movimientos imperceptibles, a leer corrientes de viento y hasta
            a intuir la intención asesina del enemigo antes de que ocurra. Para ellos, fallar un disparo no es un error… es un pecado.
            Su mayor fortaleza es también su condena: viven para la distancia, para el espacio entre ellos y sus objetivos. Si un enemigo logra romper esa distancia sagrada,
            el arquidonio queda expuesto, frágil, casi desnudo. Por eso viajan ligeros, sin armaduras pesadas, confiando siempre en que su primera flecha será también 
            la última que el enemigo vea
        """.trimIndent(),
        2 to """
            Los Creyentes Espadachines siguen los mandatos de la Hoja Primigenia, una antigua arma-religión que consideran la primera espada creada por manos mortales
            pero bendecida por fuerzas desconocidas. Para ellos, la espada no es un instrumento: es un camino. Un filo que divide el miedo del valor, la vida de la 
            muerte y la duda de la decisión.
            Los iniciados pasan años templando su cuerpo y espíritu en rituales donde sostienen la espada durante horas, incluso días, hasta que sus brazos dejan de 
            sentir dolor y su mente aprende a ignorar el cansancio. Cuentan que un verdadero Espadachín puede mantener la guardia incluso dormido, pues su vínculo con
            su arma se vuelve instintivo, casi espiritual.
            En combate, su fe se manifiesta como golpes críticos devastadores, capaces de partir escudos, huesos y voluntades en un solo movimiento. Sin embargo, su
            devoción absoluta por el enfrentamiento directo deja un punto débil: los debilitamientos, maldiciones o rupturas espirituales los afectan más de lo normal.
            Una espada firme necesita un espíritu firme, y si este es quebrado… la Hoja Primigenia no responde.
            Aun así, nadie camina hacia un Espadachín sin sentir que un filo invisible ya apunta a su corazón.
        """.trimIndent(),
        3 to """
            Los Creyentes Lanzatonio rinden culto al Colmillo del Titán, una antigua lanza sagrada que según sus mitos fue forjada para perforar la corteza del mundo 
            mismo. Sus seguidores creen que la verdadera fuerza no proviene de la agresión, sino de la resistencia, y por eso dedican su vida a convertirse en murallas
            vivientes.
            Entrenados desde jóvenes en duelos rituales, aprenden a blandir la lanza con una precisión casi matemática y a portar escudos pesados como si no significaran
            más que una tela al viento. Su estilo de combate es directo, disciplinado, y capaz de adaptarse tanto a la cercanía mortal como a la distancia estratégica
            cuando la situación lo exige.
            En batalla, son conocidos por su defensa inquebrantable y su extraordinaria vitalidad. Su armadura no es solo metal: es una extensión de su fe. Sin embargo,
            esta misma fortaleza tiene una grieta espiritual… pues las artes mágicas logran atravesar lo que la fuerza física no puede.
            Los ancianos del culto murmuran historias que casi nadie se atreve a investigar: viejos relatos sobre una armadura imposible, supuestamente forjada de
            diamante puro, acompañada de una lanza capaz de resistir cualquier impacto. Pero, como insisten los Lanzatonio más prudentes… “toda leyenda se cuenta 
            por diversión, no por certeza”. Aun así, la manera en que sus voces bajan al decirlo siempre deja una pequeña duda en el aire.
        """.trimIndent(),
        4 to """
            Los Creyentes Magitasanos son los herederos del Grimorio Viviente, un antiguo libro-arma cuya existencia se remonta a épocas anteriores a la escritura misma.
            Sus páginas cambian, se reescriben y respiran, como si una mente ancestral latiera en su interior. Los Magitasanos consideran que todo conocimiento es poder,
            y que la magia es el lenguaje primordial del universo.
            A diferencia de otras religiones-armas, ellos no ven la batalla como un choque frontal, sino como un tablero de posibilidades. Sus estrategias se basan en 
            hechizos complejos, barreras que distorsionan el flujo de energía y tácticas que manipulan el campo de combate sin que el enemigo lo note hasta que ya es 
            demasiado tarde.
            Los acólitos entrenan durante años para dominar sus artes de soporte, invocación y defensa, pues su magia no se limita sólo a atacar: puede curar, proteger
            o incluso alterar la voluntad de criaturas menores. Sin embargo, aquellos que estudian demasiado profundo llegan a escuchar susurros en el grimorio… y no 
            todos regresan siendo los mismos.
            Su gran vulnerabilidad son los Tiempistas, ya que manipulan el único aspecto que la magia arcana no puede controlar por completo: el flujo del tiempo. 
            Frente a ellos, los hechizos de los Magitasanos se vuelven inestables, sus invocaciones pierden cohesión y sus estrategias se derrumban como un castillo
            de arena bajo la marea.
            Se dice que en las cámaras más ocultas del culto existen conjuros prohibidos, fórmulas que rozan la magia oscura y que pueden transformar al hechicero
            tanto como al enemigo. Nadie admite su existencia… pero a veces, cuando un Magitasano abre su grimorio, una sombra que no debería estar allí parece 
            moverse entre sus páginas.
        """.trimIndent(),
        5 to """
            
        """.trimIndent(),
        6 to """
            
        """.trimIndent(),
        7 to """
            
        """.trimIndent(),
        8 to """
            
        """.trimIndent(),
        9 to """
            
        """.trimIndent(),
        10 to """
            
        """.trimIndent(),
        11 to """
            
        """.trimIndent(),
        12 to """
            
        """.trimIndent(),
        13 to """
            
        """.trimIndent(),
        14 to """
            
        """.trimIndent(),
        15 to """
            
        """.trimIndent(),
        16 to """
            
        """.trimIndent(),
        17 to """
            
        """.trimIndent(),
        18 to """
            
        """.trimIndent(),

    )

    val diccionarioImagenes = hashMapOf<Int, Int>(
        0 to R.drawable.pantalla_carga,
        1 to R.drawable.pantalla_carga,
        2 to R.drawable.pantalla_carga,
        3 to R.drawable.pantalla_carga,
        4 to R.drawable.pantalla_carga,
        5 to R.drawable.pantalla_carga,
        6 to R.drawable.pantalla_carga,
        7 to R.drawable.pantalla_carga,
        8 to R.drawable.pantalla_carga,
        9 to R.drawable.pantalla_carga,
        10 to R.drawable.pantalla_carga,
        11 to R.drawable.pantalla_carga,
        12 to R.drawable.pantalla_carga,
        13 to R.drawable.pantalla_carga,
        14 to R.drawable.pantalla_carga,
        15 to R.drawable.pantalla_carga,
        16 to R.drawable.pantalla_carga,
        17 to R.drawable.pantalla_carga,
        18 to R.drawable.pantalla_carga,

    )

    var estado = false
    //datos jugador
    var datosJugador: String = ""
    var datos_evento: String = ""
    var datos_premio_evento = "waza"

    var id_usuario = 0 //id del usu// ario
    var Nombre = "default"
    var nivel_De_cuenta = 1 //depdned de experiencia
    var Moneda_Global = 0
    var monedas = 0 // monedas q tiene el
    var ecencia_de_juego = 0//ecencia para mejorar personajes
    var experiencia_de_juego = 0 //su experincia del nivel
    var contraseña_hash = "xxx"
    var nivel_de_progresion = 0 //progresion del mapa
    var Perfil_id = "perfil_0"

    //variables para un uso para versiones nuevas
    var experiencia_pasada = false

    @Volatile
    var disparador = false

    var puntaje = 0


    var se_cargo_datos_iniciales = false


    var Primer_inicio = true //para dar recompensa
    var Se_paso_el_evento = false //si esque se apso el evento
    var evento_activo = false


    var ReySeleccionado: Tropa? = null //xd
    var TropaSeleccionada: Tropa? = null //xd
    var decision = 0 //ni diea
    var tropa_seleccionada_posicion = 0 //para aniamcion
    var A_quien = true //true = turno de jugador1 , false = turno jugador 2
    //atributos q no me acuerdo :v
    var Atodos = false
    var nivel_actico = 0;
    var turno = true


    //nuevos atributos cuenta gaa
    var Diccionario_Perfiles = hashMapOf<String, Int>(
        "perfil_0" to R.drawable.perfil_0,
        "perfil_1" to R.drawable.perfil_1,
        "perfil_2" to R.drawable.perfil_2,
        "perfil_3" to R.drawable.perfil_3,
        "perfil_4" to R.drawable.perfil_4,

    )


}
