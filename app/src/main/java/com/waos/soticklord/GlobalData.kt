package com.waos.soticklord

import android.app.Activity
import Data.Tropa

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

    var batalla: Activity? = null

    val Diccionario_Clases = mapOf(
        //defaults
        "Rey_Arquero" to Data.Rey_Arquero::class,
        "Rey_Espadachin" to Data.Rey_Espadachin::class,
        "Rey_Lanzatonio" to Data.Rey_Lanzatonio::class,
        "Rey_de_los_Gigantes" to Data.Rey_de_los_Gigantes::class,

        "Tropa_Arquero" to Data.Tropa_Arquero::class,
        "Tropa_Espadachin" to Data.Tropa_Espadachin::class,
        "Tropa_Lanzatonio" to Data.Rey_Lanzatonio::class,
        "Tropa_Gigante" to Data.Tropa_Gigante::class,
        //Especiales
        "Rey_Heber" to Data.Especiales.Rey_Heber::class,
        "Rey_Fernando" to Data.Especiales.Rey_Fernando::class,
        "Rey_Cristian" to Data.Especiales.Rey_Cristian::class,
        "Reyna_Darisce" to Data.Especiales.Reyna_Darisce::class,
        "Reyna_Shantal" to Data.Especiales.Reyna_Shantal::class,

        //personalizados
        "Rey_Noob" to Data.Personalizados.Rey_Noob::class,
        "Rey_Goku" to  Data.Personalizados.Rey_Goku::class,
        "Rey_Piero" to Data.Personalizados.Rey_Piero::class,
        "Rey_Borrego" to Data.Personalizados.Rey_Borrego::class,
        "Reyna_paranormal" to Data.Personalizados.Reyna_paranormal::class,
        "Rey_Freddy" to Data.Personalizados.Rey_Freddy::class,
        "Rey_Constructor" to Data.Personalizados.Rey_Constructor::class


    )

    val Diccionario_Ataques = mapOf(
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
        Invocas A 5 gigantes respecto a tu nivel
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
                """"
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
        "Baje_de_vida_estelar" to
                """
        (Ignora espinas y escudo)
        Todos los enemigos bajan su vida en un 30%
        de su vida restante
                """.trimIndent(),
        //---------------------------
        "Empeoramiento_estelar" to
                """
        El ataque base de todos los enemigos
        baja en un 50%
                """.trimIndent(),
        //---------------------------
        "Se_te_acabo_el_tiempo" to
                """
        Todas las Tropas enemigas reciven
        un daño igual a tu ataque base
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

    )

    var id_usuario = 0 //id del usuario
    var nivel_de_progresion = 0 //progresion del mapa
    var experiencia_de_juego = 0 //su experincia del nivel
    var nivel_De_cuenta = 0 //depdned de experiencia
    var monedas = 0 // monedas q tiene el jugador
    var ecencia_de_juego = 0//ecencia para mejorar personajes

    var ReySeleccionado: Tropa? = null
    var TropaSeleccionada: Tropa? = null
    var decision = 0
}
