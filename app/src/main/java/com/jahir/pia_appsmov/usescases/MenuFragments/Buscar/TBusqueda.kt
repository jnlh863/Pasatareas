package com.jahir.pia_appsmov.usescases.MenuFragments.Buscar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jahir.pia_appsmov.R
import com.jahir.pia_appsmov.usescases.MenuFragments.TodasTareas.Inicio
import com.jahir.pia_appsmov.usescases.MenuFragments.misTareas.HAdpater
import com.jahir.pia_appsmov.usescases.MenuFragments.misTareas.MisTareas
import com.jahir.pia_appsmov.usescases.compartirTarea.Tarea

class TBusqueda(
    var context: Buscar,
    var TareasBuscadas: ArrayList<Tarea>
): RecyclerView.Adapter<TBusqueda.TBViewHolder>() {

    private var onClick: OnItemClicked? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TBViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.tareas_item, parent, false)
        return TBViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return TareasBuscadas.size
    }

    override fun onBindViewHolder(holder: TBViewHolder, position: Int) {
        val tareaB = TareasBuscadas.get(position)

            holder.tvIdUsuario.text = tareaB.userName
            holder.tvNombre.text = tareaB.nameT
            holder.tvMateria.text = tareaB.materiaT


        holder.btnVerPDF.setOnClickListener{
            onClick?.verPDF2(tareaB.urlT)
        }

        holder.btnVerInfo.setOnClickListener{
            onClick?.verInfo2(tareaB)
        }

    }

    inner class TBViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvIdUsuario = itemView.findViewById(R.id.tvIdUsuario) as TextView
        val tvNombre = itemView.findViewById(R.id.tvNombre) as TextView
        val tvMateria = itemView.findViewById(R.id.tvMateria) as TextView
        val btnVerPDF = itemView.findViewById(R.id.btnVerPDF) as Button
        val btnVerInfo = itemView.findViewById(R.id.btnVerInfo) as Button
    }

    interface OnItemClicked {
        fun verPDF2(urlT: String)
        fun verInfo2(tareaB: Tarea)
    }

    fun setOnClick(onClick: Buscar) {
        this.onClick = onClick
    }
}