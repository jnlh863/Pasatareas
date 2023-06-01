package com.jahir.pia_appsmov.usescases.MenuFragments.Buscar

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jahir.pia_appsmov.R
import com.jahir.pia_appsmov.usescases.MenuFragments.misTareas.HAdpater
import com.jahir.pia_appsmov.usescases.VerInfoTarea.VerInfoT
import com.jahir.pia_appsmov.usescases.compartirTarea.Tarea
import com.jahir.pia_appsmov.usescases.registro.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Buscar : Fragment(), TBusqueda.OnItemClicked {

    lateinit var TareasB: RecyclerView
    lateinit var TB: SearchView
    lateinit var TBAdapter: TBusqueda

    var TareasBuscadas = arrayListOf<Tarea>()

    private var mParam1: String? = null
    private var mParam2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_buscar, container, false)
        TareasB = root.findViewById(R.id.TareasBuscadas)
        TB = root.findViewById(R.id.searchView)

        TareasB.layoutManager = LinearLayoutManager(activity)
        setupRecyclerView()

        TB.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                buscarTarea(p0)
                return true
            }
        })
        return root
    }

    private fun buscarTarea(p0: String?) {
        try{
            if(p0 != null){
                CoroutineScope(Dispatchers.IO).launch {
                    val call = RetrofitClient.webService.buscarTareas(p0.toString())
                    activity?.runOnUiThread {
                        if (call.isSuccessful) {
                            TareasBuscadas = call.body()!!.TareasBuscadas
                            setupRecyclerView()
                        } else {
                            Toast.makeText(activity, call.body().toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }else{
                Toast.makeText(activity, "Sin resultados", Toast.LENGTH_SHORT).show()
            }
        }catch(e: Exception){
            Toast.makeText(activity, "Algo salio mal", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setupRecyclerView() {
        TBAdapter =  TBusqueda(this, TareasBuscadas)
        TBAdapter.setOnClick(this@Buscar)
        TareasB.adapter = TBAdapter
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        fun newInstance(param1: String?, param2: String?): Buscar {
            val fragment = Buscar()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    override fun verPDF2(urlT: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(urlT), "application/pdf")
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        try {
            requireActivity().startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                activity,
                "No existe una aplicación para abrir el PDF",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun verInfo2(tareaB: Tarea) {
        val intent = Intent(context, VerInfoT::class.java)
        intent.putExtra("Usuario", tareaB.userName)
        intent.putExtra("NombreT", tareaB.nameT)
        intent.putExtra("MateriaT",tareaB.materiaT)
        intent.putExtra("DesT", tareaB.DesH)
        startActivity(intent)
    }

}