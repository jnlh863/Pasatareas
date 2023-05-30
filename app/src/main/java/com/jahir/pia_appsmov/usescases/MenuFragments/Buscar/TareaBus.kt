package com.jahir.pia_appsmov.usescases.MenuFragments.Buscar

import com.google.gson.annotations.SerializedName
import com.jahir.pia_appsmov.usescases.compartirTarea.Tarea

data class TareaBus(
    @SerializedName("misT") var TareasBuscadas: ArrayList<Tarea>
)
