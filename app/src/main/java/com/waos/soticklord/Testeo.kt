package com.waos.soticklord
import Data.*
import java.security.MessageDigest
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


    val nombre = crearHash("heber2006")
    println(nombre)
}

private fun crearHash(password: String): String {
    val bytes = MessageDigest
        .getInstance("SHA-256")
        .digest(password.toByteArray(Charsets.UTF_8))

    return bytes.joinToString("") { "%02x".format(it) }
}