package com.jahir.pia_appsmov.usescases.pantallaprincipal

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.FirebaseAuth
import com.jahir.pia_appsmov.usescases.menu.Menu
import com.jahir.pia_appsmov.R
import com.jahir.pia_appsmov.usescases.actualizarT.ActualizarT
import com.jahir.pia_appsmov.usescases.iniciosesion.InicioSesion
import com.jahir.pia_appsmov.usescases.registro.Registrarse

class MainActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        val screen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        screen.setKeepOnScreenCondition{false}
    }

    fun PInicioS(view: View?) {
        val i = Intent(this, InicioSesion::class.java)
        startActivity(i)
    }

    fun PRegistrarse(view: View?) {
        val i = Intent(this, Registrarse::class.java)
        startActivity(i)
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this@MainActivity)
            .setMessage("¿Desea salir de PasaTareas?")
            .setCancelable(false)
            .setPositiveButton("Si") { dialog, whichButton ->
                finishAffinity()
            }
            .setNegativeButton("No") { dialog, whichButton ->
            }
            .show()
    }

    override fun onStart() {
        super.onStart()
        val usuario = mAuth!!.currentUser

        if (usuario != null) {
            val intent = Intent(this@MainActivity, Menu::class.java)
            startActivity(intent)
            Thread.sleep(10000)
            finish()
        }
    }
}