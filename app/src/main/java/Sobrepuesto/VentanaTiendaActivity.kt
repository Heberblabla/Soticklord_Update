package Sobrepuesto

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.waos.soticklord.R
import android.view.WindowManager
import android.view.Gravity


class VentanaTiendaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_tienda)

        val displayMetrics = resources.displayMetrics
        val width = (displayMetrics.widthPixels * 0.80).toInt() // 80% = 10% cada lado

        window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
    }




    fun waos(view: View){
        finish()
    }



}