package com.waos.soticklord

import android.app.Activity
import Data.Tropa
import Archivos_Extra.*

object GlobalData {
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
    //var batalla: Activity? = null
    val Diccionario_Clases = mapOf(
        //defaults
        "Rey_Arquero" to Data.Rey_Arquero::class,
        "Rey_Espadachin" to Data.Rey_Espadachin::class,
        "Rey_Lanzatonio" to Data.Rey_Lanzatonio::class,
        "Rey_de_los_Gigantes" to Data.Rey_de_los_Gigantes::class,

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


        //personalizados
        //creados
        "Rey_Freddy" to Data.Personalizados.Rey_Freddy::class,
        "Rey_Constructor" to Data.Personalizados.Rey_Constructor::class,
        "Rey_El_Pro" to Data.Personalizados.Rey_El_Pro::class,
        "Rey_Noob" to Data.Personalizados.Rey_Noob::class,
        "Rey_Goku" to  Data.Personalizados.Rey_Goku::class,
        "Rey_Piero" to Data.Personalizados.Rey_Piero::class,

        //torneo
        "Rey_Borrego" to Data.Personalizados.Rey_Borrego::class,
        "Reyna_paranormal" to Data.Personalizados.Reyna_paranormal::class,
        "Rey_Kanox" to Data.Personalizados.Rey_Kanox::class,
        "Rey_Kratos" to Data.Personalizados.Rey_Kratos::class,
        "Rey_Jerald" to Data.Personalizados.Rey_Jerald::class,
        "Rey_Moises" to Data.Personalizados.Rey_Moises::class,
        "Rey_Han_Kong" to Data.Personalizados.Rey_Han_Kong::class,
        "Rey_Aethelred" to Data.Personalizados.Rey_Aethelred::class,

        //tropas fichas
        "Tropa_Gigante_estelar" to Data.Tropas_personalizadas.Tropa_Gigante_estelar::class,
        "Tropa_Gurandera" to Data.Tropas_personalizadas.Tropa_Gurandera::class,
        "Tropa_Gato_amigo2" to Data.Tropas_personalizadas.Tropa_Gato_amigo2::class,
        "Tropa_Gato_amigo1" to Data.Tropas_personalizadas.Tropa_Gato_amigo1::class



    )

    val Diccionario_Ataques = mapOf(
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
        Inflijes un daño de 35 +15% por cada nivel 
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
        Creas un daño aleatorio (1-100) +15%
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
        Inflijes un daño de 50 
        + 15% extra por cada nivel del gigante,
        a todas los tropas enemigas
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
        "Rey_Arquero" to
                """
        Al recivir algun tipo de daño directo,
        devolved un golpe de daño 
                """.trimIndent() ,
        //----------------------------------
        "Rey_Cristian" to
                """
        Si Alguna tropa muere ,eliminad a una
        tropa enemiga aleatoria
                """.trimIndent() ,
        "Rey_Kratos" to
                """
        Una ves por partida, Al recivir un daño
        directo q no haga morir , revivid con 100pv,
        30% de defensa y +100 de ataque extra
                """.trimIndent() ,
        //----------------------------------
        "Rey_Kanox" to
                """
        Por cada turno q pase, existe una 
        probabilidad del 45% de subir tu vida 
        y ataque base en un 8%
                """.trimIndent() ,
        //----------------------------------


        )


    val listaEventos = mutableListOf<Evento>()
    var id_usuario = 0 //id del usuario
    var nivel_de_progresion = 0 //progresion del mapa
    var experiencia_de_juego = 0 //su experincia del nivel
    var nivel_De_cuenta = 0 //depdned de experiencia
    var monedas = 0 // monedas q tiene el jugador
    var ecencia_de_juego = 0//ecencia para mejorar personajes

    var ReySeleccionado: Tropa? = null
    var TropaSeleccionada: Tropa? = null
    var decision = 0
    var Primer_inicio = true


    var tropa_seleccionada_posicion = 0
    var A_quien = true
    var Atodos = false

    var nivel_actico = 0;

    var turno = true


}
