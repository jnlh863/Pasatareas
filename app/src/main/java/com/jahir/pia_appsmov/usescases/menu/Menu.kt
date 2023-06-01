package com.jahir.pia_appsmov.usescases.menu

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.jahir.pia_appsmov.usescases.iniciosesion.InicioSesion
import com.jahir.pia_appsmov.R
import com.jahir.pia_appsmov.usescases.MenuFragments.Buscar.Buscar
import com.jahir.pia_appsmov.usescases.MenuFragments.TodasTareas.Inicio
import com.jahir.pia_appsmov.usescases.MenuFragments.misTareas.MisTareas
import com.jahir.pia_appsmov.usescases.pantallaprincipal.MainActivity
import com.jahir.pia_appsmov.usescases.user.MiCuenta

class Menu : AppCompatActivity() {
    lateinit private var Toolbar1: Toolbar
    lateinit private var Navegacion: BottomNavigationView

    //Button BtnCS;
    var mAuth: FirebaseAuth? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        Toolbar1 = findViewById(R.id.Toolbar1)
        Navegacion = findViewById(R.id.Navegacion)
        Toolbar1.setTitle("Mis Tareas")
        setSupportActionBar(Toolbar1)
        supportFragmentManager.beginTransaction().add(R.id.Frame1, MisTareas()).commit()

        Navegacion.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuUsuario -> {
                    supportFragmentManager.beginTransaction().replace(R.id.Frame1, MisTareas())
                        .commit()
                    Toolbar1.setTitle("Comparte tu tarea")
                    return@OnNavigationItemSelectedListener true
                }

                R.id.menuUsuarios -> {
                    supportFragmentManager.beginTransaction().replace(R.id.Frame1, Buscar())
                        .commit()
                    Toolbar1.setTitle("Buscar Tareas")
                    return@OnNavigationItemSelectedListener true
                }

                R.id.menuTareas -> {
                    supportFragmentManager.beginTransaction().replace(R.id.Frame1, Inicio())
                        .commit()
                    Toolbar1.setTitle("Tareas subidas por otros usuarios")
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

    }
    override fun onBackPressed() {
        AlertDialog.Builder(this@Menu)
            .setMessage("¿Desea salir de PasaTareas?")
            .setCancelable(false)
            .setPositiveButton("Si") { dialog, whichButton ->
                finishAffinity()
            }
            .setNegativeButton("No") { dialog, whichButton ->
            }
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menuopciones, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val nro = item.itemId
        return when (nro) {
            R.id.Cuenta -> {
                startActivity(Intent(this@Menu, MiCuenta::class.java))
                true
            }

            R.id.CerrarSesion -> {
                mAuth = FirebaseAuth.getInstance()
                mAuth!!.signOut()
                finish()
                startActivity(Intent(this@Menu, MainActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}