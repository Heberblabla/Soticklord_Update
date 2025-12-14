package Multijugador

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.waos.soticklord.GlobalData
import com.waos.soticklord.Iniciar_Sesion
import com.waos.soticklord.R

class Waos : AppCompatActivity() {
    var indice = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_waos)
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
    }


    fun siguiente_hoja(view: View) {
        indice += 1
        if (indice >= 19) {
            indice = 0
        }
        Mostrar_datos()
    }

    fun anterior_hoja(view: View){
        indice -= 1
        if(indice <= -1){
            indice = 18
        }
        Mostrar_datos()

    }

    fun Mostrar_datos(){
        var nombreu = GlobalData.Diccionarios_textos[indice]
        val mini_info = findViewById<TextView>(R.id.Mini_info)
        mini_info.text = "$nombreu"

        var waos2 = GlobalData.Diccionario_miniInfo[indice]
        val mini_info2 = findViewById<TextView>(R.id.Gran_info)
        mini_info2.text = "$waos2"
    }


    fun atras(view: View){
        val intent = Intent(this, Iniciar_Sesion::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }



}