package Pruebas

import io.github.jan.supabase.postgrest.postgrest


import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

fun main() = runBlocking {

    val supabase = createSupabaseClient(
        supabaseUrl = "https://bkrvukzsfyzbyoatpysw.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJrcnZ1a3pzZnl6YnlvYXRweXN3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjQwMDgyMzQsImV4cCI6MjA3OTU4NDIzNH0.It2XZzxNzPk0wYv-xW52mVkOOP3Wqpqacoefszu36ic"

    ) {
        install(Postgrest)
    }

    println("Probando UPSERT...")

    // No usar mapOf. Usar JsonObject SIEMPRE.
    val params = buildJsonObject {
        put("p_nombre", "xds")
    }

    val result = supabase.postgrest.rpc(
        function = "existe_jugador",
        parameters = params
    )

    println("Resultado:")
    println(result.data)
}

