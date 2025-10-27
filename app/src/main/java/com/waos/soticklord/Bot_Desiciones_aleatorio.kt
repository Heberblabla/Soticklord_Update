package com.waos.soticklord
import kotlin.system.exitProcess

import Data.Tropa
import android.widget.Toast
import android.content.Context
import android.view.View
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import android.widget.AdapterView
import android.widget.ImageView

class Bot_Desiciones_aleatorio (private val context: Context){

    fun Empezar_Analisis(Posicion : Int) {
        //sacamos el nombre de la tropa
        val nombre = GlobalData.Jugador2[Posicion]!!.nombre
        //sacamos todos los ataques q puede hacer
        var Ataques = Obtener_Array_String(nombre)
        //vemos q tropas podemos atacar
        val tropas_disponibles = Tropas_disponibles(Posicion)
        //seleccionamos la tropa a la q atacaremos (por ahora random)
        val Tropa_random_atacar = tropas_disponibles.random()
        //aca se decide q ataque efectuar
        var ataque = Seleccionar_Ataque(Ataques,Tropa_random_atacar,Posicion)
        Toast.makeText(context, "$ataque", Toast.LENGTH_SHORT).show()
        //Toast.makeText(context, "Posicion del enemigo: $Tropa_random_atacar", Toast.LENGTH_SHORT).show()
        Ejecutar_ataque(GlobalData.Jugador2, GlobalData.Jugador1,Posicion,Tropa_random_atacar,ataque)

    }

    fun Tropas_disponibles(posicion: Int): List<Int> {
        val Tropas_disponibles = mutableListOf<Int>()
        if(GlobalData.Jugador1[5]!!.estado_de_vida) {
            Tropas_disponibles.add(5)
        }
        if(GlobalData.Jugador1[4]!!.estado_de_vida) {
            Tropas_disponibles.add(4)
        }
        if(GlobalData.Jugador1[3]!!.estado_de_vida) {
            Tropas_disponibles.add(3)
        }
        if((GlobalData.Jugador1[2]!!.estado_de_vida && GlobalData.Jugador2[posicion]!!.aereo) ||(
            !GlobalData.Jugador1[3]!!.estado_de_vida &&
            !GlobalData.Jugador1[4]!!.estado_de_vida &&
            !GlobalData.Jugador1[5]!!.estado_de_vida )){
            Tropas_disponibles.add(2)
        }
        if((GlobalData.Jugador1[1]!!.estado_de_vida && GlobalData.Jugador1[posicion]!!.aereo) ||(
            !GlobalData.Jugador1[3]!!.estado_de_vida &&
            !GlobalData.Jugador1[4]!!.estado_de_vida &&
            !GlobalData.Jugador1[5]!!.estado_de_vida)) {
            Tropas_disponibles.add(1)
            }
        if((GlobalData.Jugador1[0]!!.estado_de_vida && GlobalData.Jugador1[posicion]!!.aereo) ||(
            !GlobalData.Jugador1[3]!!.estado_de_vida &&
            !GlobalData.Jugador1[4]!!.estado_de_vida &&
            !GlobalData.Jugador1[5]!!.estado_de_vida &&
            !GlobalData.Jugador1[1]!!.estado_de_vida &&
            !GlobalData.Jugador1[2]!!.estado_de_vida)){
            Tropas_disponibles.add(0)
        }

        return Tropas_disponibles
    }

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
                            "clonar", "copyBase", "reproducirVideoAtaque",
                            "Ataque_Normal", "Recivir_daño",
                            "component1", "component2"
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


    fun Seleccionar_Ataque(Ataques: List<String>, Posicion_enemiga: Int, posicion: Int): String {
        val probabilidad = (0..100).random() // número aleatorio entre 0 y 100

        if (true) {
            return Ataques.random() // 20% de azar
        }
        else {
            val Total_ataques = Ataques.size
            var vida_total = 0
            var ataque_seleccionado = 0

            // Calcular la vida total inicial del enemigo
            for (tropa in GlobalData.Jugador1) {
                if (tropa!!.vida > 0) {
                    vida_total += tropa.vida
                }
            }

            var copia1 = GlobalData.Jugador1.map { it?.clonar() }.toCollection(ArrayList())
            var copia2 = GlobalData.Jugador2.map { it?.clonar() }.toCollection(ArrayList())

            // Simular cada ataque
            for (i in 0 until Total_ataques) {
                copia1 = GlobalData.Jugador1.map { it?.clonar() }.toCollection(ArrayList())
                copia2 = GlobalData.Jugador2.map { it?.clonar() }.toCollection(ArrayList())
                var vida_total_emulada = 0

                // Clonar los ejércitos antes de cada simulación

                val ataque = Ataques[i]
                Ejecutar_ataque(copia2, copia1, posicion, Posicion_enemiga, ataque)

                // Sumar la vida total restante del enemigo después de simular
                for (tropa in copia1) {
                    if (tropa!!.vida > 0) {
                        vida_total_emulada += tropa.vida
                    }
                }

                // Ver si este ataque causa más daño que los anteriores
                if (vida_total_emulada < vida_total) {
                    ataque_seleccionado = i
                    vida_total = vida_total_emulada
                }


            }

            return Ataques[ataque_seleccionado]
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
                metodo.invoke(tropaAtacante, jugador2, posicion2,false)
            } else {
                println("No se encontró el método '$nombreMetodo' en la clase $nombreClase")
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
