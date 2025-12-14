package Pruebas

import Data.Especiales.Rey_Cristian
import Data.Rey_Lanzatonio
import Data.Tropa
import Data.Tropa_Arquero
import Data.Tropa_Espadachin
import Data.Tropa_Gigante
import Data.Tropa_Lanzatonio
import com.waos.soticklord.Bot_Desiciones_aleatorio
import com.waos.soticklord.GlobalData
import java.lang.reflect.Method


fun main() {
    prueba02()
    println("------------- Jugador 1 ------------")
    println(" tropa 0 : ${GlobalData.Jugador1[0]!!.nombre}")
    println(" tropa 1 : ${GlobalData.Jugador1[1]!!.nombre}")
    println(" tropa 2 : ${GlobalData.Jugador1[2]!!.nombre}")
    println(" tropa 3 : ${GlobalData.Jugador1[3]!!.nombre}")
    println(" tropa 4 : ${GlobalData.Jugador1[4]!!.nombre}")
    println(" tropa 5 : ${GlobalData.Jugador1[5]!!.nombre}")
    println("------------- Jugador 2 ------------")
    println(" tropa 0 : ${GlobalData.Jugador2[0]!!.nombre}")
    println(" tropa 1 : ${GlobalData.Jugador2[1]!!.nombre}")
    println(" tropa 2 : ${GlobalData.Jugador2[2]!!.nombre}")
    println(" tropa 3 : ${GlobalData.Jugador2[3]!!.nombre}")
    println(" tropa 4 : ${GlobalData.Jugador2[4]!!.nombre}")
    println(" tropa 5 : ${GlobalData.Jugador2[5]!!.nombre}")
    println("-----------------------------------")
    println("-----------------------------------")

    GlobalData.Jugador1[5]!!.estado_de_vida = false
    GlobalData.Jugador1[4]!!.estado_de_vida = false
    GlobalData.Jugador1[3]!!.estado_de_vida = false
    GlobalData.Jugador1[2]!!.estado_de_vida = false
    GlobalData.Jugador1[1]!!.estado_de_vida = false
    GlobalData.Jugador1[0]!!.estado_de_vida = false

    val bot = Bot_waos()
    bot.Empezar_Analisis(1)

}

fun escudo(){
    prueba02()
    print("Rey cristian :")
    println("Vida ${GlobalData.Jugador2[0]!!.vida}")
    //GlobalData.Jugador2[0]!!.vida -= 200
    println("Ejecutando ataque")
    Ejecutar_ataque(GlobalData.Jugador1, GlobalData.Jugador2,0,0,"Lanza_Imperial",true)
    print("Rey cristian :")
    println("Vida ${GlobalData.Jugador2[0]!!.vida}")
    //println("Eejcutando ataque golden")
    //Ejecutar_ataque2(GlobalData.Jugador2,GlobalData.Jugador1,0,0,"Golden")
    println("volviendo A ejecutar  ataque")
    Ejecutar_ataque(GlobalData.Jugador1, GlobalData.Jugador2,0,0,"Lanza_Imperial",true)
    print("Rey cristian :")
    println("Vida ${GlobalData.Jugador2[0]!!.vida}")
    println("volviendo A ejecutar  ataque magico")
    Ejecutar_ataque(GlobalData.Jugador1, GlobalData.Jugador2,1,0,"Jugamos_jaja",true)
    print("Rey cristian :")
    println("Vida ${GlobalData.Jugador2[0]!!.vida}")
}

fun prueba02() {
    GlobalData.Jugador1[0] = Rey_Lanzatonio(1)
    GlobalData.Jugador1[1] = Tropa_Arquero(1)
    GlobalData.Jugador1[2] = Tropa_Arquero(1)
    GlobalData.Jugador1[3] = Tropa_Gigante(1)
    GlobalData.Jugador1[4] = Tropa_Gigante(1)
    GlobalData.Jugador1[5] = Tropa_Gigante(1)

    GlobalData.Jugador2[0] = Rey_Cristian(1)
    GlobalData.Jugador2[1] = Tropa_Lanzatonio(1)
    GlobalData.Jugador2[2] = Tropa_Lanzatonio(1)
    GlobalData.Jugador2[3] = Tropa_Espadachin(1)
    GlobalData.Jugador2[4] = Tropa_Espadachin(1)
    GlobalData.Jugador2[5] = Tropa_Espadachin(1)


}

fun Ejecutar_ataque(
    jugador1: ArrayList<Tropa?>,
    jugador2: ArrayList<Tropa?>,
    posicion1: Int,
    posicion2: Int,
    nombreMetodo: String,
    waos : Boolean
) {
    try {
        // Obtener la tropa atacante y la clase de esa tropa
        val tropaAtacante = jugador1[posicion1] ?: return
        val clase = tropaAtacante::class.java
        val nombreClase = clase.simpleName


        // Buscar el método con el nombre exacto
        val metodo: Method? = clase.declaredMethods.find { m ->
            m.name == nombreMetodo
        }

        if (metodo != null) {


            // Invocar el método sobre la instancia de la tropa atacante
            // Le pasamos jugador2 y la posición del enemigo
            metodo.invoke(tropaAtacante, jugador2, posicion2,waos)
        } else {

        }

    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Ejecutar_ataque2(
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

        // Buscar el método con el nombre exacto
        val metodo: Method? = clase.declaredMethods.find { m ->
            m.name == nombreMetodo
        }
        if (metodo != null) {

            // Invocar el método sobre la instancia de la tropa atacante
            // Le pasamos jugador2 y la posición del enemigo
            metodo.invoke(tropaAtacante, jugador2, posicion2,false)
        } else {

        }

    } catch (e: Exception) {
        e.printStackTrace()
    }
}



