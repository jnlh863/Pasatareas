package com.jahir.pia_appsmov.usescases.user

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.jahir.pia_appsmov.R
import com.jahir.pia_appsmov.usescases.compartirTarea.urlT
import com.jahir.pia_appsmov.usescases.iniciosesion.NomU
import com.jahir.pia_appsmov.usescases.menu.Menu
import com.jahir.pia_appsmov.usescases.pantallaprincipal.MainActivity
import com.jahir.pia_appsmov.usescases.registro.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MiCuenta : AppCompatActivity(), MisDatosAd.OnItemClicked {

    lateinit var BtnCS: Button
    lateinit var MisDates: RecyclerView
    lateinit var MisDatos_U: MisDatosAd
    lateinit var Refresh: SwipeRefreshLayout

    lateinit var preferences: SharedPreferences
    lateinit var URL_IMG : SharedPreferences
    lateinit var progressDialog: ProgressDialog
    lateinit var storageReference: StorageReference
    lateinit private var img_url: Uri

    lateinit var Regresar: Button

    var misT = arrayListOf<MisDatos>()
    var siempre = true
    var FPU = SubirFP("","")
    var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_cuenta)

        BtnCS = findViewById(R.id.BtnCS)
        MisDates = findViewById(R.id.MISD)
        Refresh = findViewById(R.id.swipe)
        Regresar = findViewById(R.id.Regresar)


        MisDates.layoutManager = LinearLayoutManager(this)


        preferences = getSharedPreferences("Preferences", MODE_PRIVATE)
        URL_IMG = getSharedPreferences(urlT.DATOS, Context.MODE_PRIVATE)
        storageReference = FirebaseStorage.getInstance().getReference()


        val us = preferences.getString("user", null)

        obtener_misDatos(us)

        Regresar.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@MiCuenta,
                    Menu::class.java
                )
            )
            finish()
        })

        Refresh.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                Refresh.isRefreshing = false
                obtener_misDatos(us)
            },2000)
        }

        BtnCS.setOnClickListener(View.OnClickListener {
            AlertDialog.Builder(this@MiCuenta)
                .setMessage("Acepto eliminar mi cuenta, y todo lo asociado a ella")
                .setCancelable(false)
                .setPositiveButton("Si") { dialog, whichButton ->
                    progressDialog= ProgressDialog(this@MiCuenta)
                    progressDialog!!.show()
                    progressDialog!!.setContentView(R.layout.pantalla_de_carga)
                    progressDialog!!.window!!.setBackgroundDrawableResource(
                        android.R.color.transparent
                    )
                    Thread.sleep(2000)
                    finish()
                    Eliminar(us)
                }
                .setNegativeButton("No") { dialog, whichButton ->
                }
                .show()
        })
    }



    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(
            Intent(
                this@MiCuenta,
                Menu::class.java
            )
        )
    }

    fun obtener_misDatos(us: String?) {
        try{
            if(siempre){
                CoroutineScope(Dispatchers.IO).launch {
                    val call = RetrofitClient.webService.misdatos(us.toString())
                    runOnUiThread {
                        if (call.isSuccessful) {
                            misT = call.body()!!.misT
                            setupRecyclerView()
                        } else {
                            Toast.makeText(this@MiCuenta, call.body().toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }}catch(e: Exception){
            Toast.makeText(this@MiCuenta, "Algo salio mal", Toast.LENGTH_SHORT).show()
        }
    }


    private fun setupRecyclerView() {
        MisDatos_U =  MisDatosAd(this, misT)
        MisDatos_U.setOnClick(this@MiCuenta)
        MisDates.adapter = MisDatos_U

    }

    private fun Eliminar(us: String?){
        try{
            if(siempre){
                CoroutineScope(Dispatchers.IO).launch {
                    val call = RetrofitClient.webService.eliminarcuenta(us.toString())
                    runOnUiThread {
                        if (call.isSuccessful) {
                            mAuth = FirebaseAuth.getInstance()
                            mAuth!!.signOut()
                            startActivity(Intent(this@MiCuenta, MainActivity::class.java))
                            progressDialog!!.dismiss()
                            Toast.makeText(this@MiCuenta, call.body().toString(), Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MiCuenta, call.body().toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }}catch(e: Exception){
            Toast.makeText(this@MiCuenta, "Algo salio mal",Toast.LENGTH_SHORT).show()
        }
    }

    private fun openFile() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_PICK
        startActivityForResult(Intent.createChooser(i, "Seleccione su una imagen.."), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == 1 && data != null && data.data != null) {
            img_url = data.data!!
            subirArchivo(img_url)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun subirArchivo(img_url: Uri?) {
        val Correo_U = preferences.getString("correo", null)
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Cargando archivo seleccionado")
        progressDialog.show()
        val reference = storageReference.child("Fotos de perfil/*" + Correo_U.toString() + ".jpg")
        reference.putFile(img_url!!).addOnSuccessListener (OnSuccessListener{
            try{
                reference.downloadUrl.addOnSuccessListener(OnSuccessListener<Uri> { uri ->
                    Toast.makeText(this@MiCuenta, "Selección exitosa", Toast.LENGTH_SHORT)
                        .show()
                    URL_IMG.edit().putString(urlT.URLT, uri.toString()).commit()
                    progressDialog!!.dismiss()
                })
            }catch (e: Exception) {
                Toast.makeText(this@MiCuenta, "No se pudo seleccionar la imagen", Toast.LENGTH_SHORT)
                    .show()
                progressDialog!!.dismiss()
            }
        }).addOnFailureListener {
            Toast.makeText(
                this@MiCuenta,
                "Error al cargar",
                Toast.LENGTH_SHORT
            ).show()
        }.addOnProgressListener { snapshot ->
            val progress = 100.0 * snapshot.bytesTransferred / snapshot.totalByteCount
            progressDialog.setMessage("Cargando" + " " + progress.toInt() + "%")
        }
    }

    override fun FP() {
        openFile()
    }

    override fun CONf() {
        val Correo_U = preferences.getString("correo", null)
        val URLImg = URL_IMG.getString(urlT.URLT, "Algo salio mal")
        this.FPU.correo = Correo_U.toString()
        this.FPU.urlimg = URLImg.toString()

        progressDialog = ProgressDialog(this@MiCuenta)
        progressDialog!!.show()
        progressDialog!!.setContentView(R.layout.pantalla_de_carga)
        progressDialog!!.window!!.setBackgroundDrawableResource(
            android.R.color.transparent
        )

        if(siempre) {
            CoroutineScope(Dispatchers.IO).launch {
                val call = RetrofitClient.webService.miFotoP(FPU)
                runOnUiThread {
                    if (call.isSuccessful) {
                        Toast.makeText(
                            this@MiCuenta,
                            call.body().toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        progressDialog!!.dismiss()
                    } else {
                        Toast.makeText(
                            this@MiCuenta,
                            call.body().toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        progressDialog!!.dismiss()
                    }
                }
            }
        }
    }
}