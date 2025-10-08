package com.waos.soticklord
import Data.*
import java.util.ArrayList

fun main(){
    val myList = ArrayList<Tropa>()
    val arquero = Rey_Arquero()
    println(arquero.vida)
    myList.add(arquero)
    val lanzatonio = Rey_Lanzatonio()
    println("vida incial = ${lanzatonio.vida} ")
    arquero.activar_Reflejo()
    lanzatonio.ataqueNormal(myList,0)
    println(arquero.vida)
    
    println("vida despues = ${lanzatonio.vida} ")

}