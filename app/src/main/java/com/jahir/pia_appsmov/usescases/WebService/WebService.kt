package com.jahir.pia_appsmov.usescases.registro

import com.google.gson.GsonBuilder
import com.jahir.pia_appsmov.usescases.MenuFragments.Buscar.BuscarTareas
import com.jahir.pia_appsmov.usescases.MenuFragments.TodasTareas.TareasGlobales
import com.jahir.pia_appsmov.usescases.MenuFragments.misTareas.dt_MisTareas
import com.jahir.pia_appsmov.usescases.UpdateTarea
import com.jahir.pia_appsmov.usescases.compartirTarea.Tarea
import com.jahir.pia_appsmov.usescases.iniciosesion.NomU
import com.jahir.pia_appsmov.usescases.user.MisDatos
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

object AppConstantes{
    const val Base_Url = "https://pasatareasappservicepia.azurewebsites.net"
}

interface WebService {

    //Parte del USUARIO

    @GET("/usuarios/{passW}") //Ver mis datos de la cuenta
    suspend fun misdatos(
        @Path ("passW") passW : String
    ): Response<MisDatos>

    @POST("/usuarios/verificacion") //Iniciar sesion (ESTE SI FUCIONA) (falta pulir cosas) <------- 5.-
    suspend fun INUser(
        @Body consulta: NomU
    ): Response<String>

    @POST("/usuarios/add") //Registrar Usuario (ESTE SI FUNCIONA) (falta pulir cosas) <------- 6.-
    suspend fun registrarUser(
        @Body user: Usuario
    ): Response<String>

    @DELETE("/usuarios/{id}") //Eliminar Cuenta
    suspend fun eliminarcuenta(
        @Path ("id") id : String
    ): Response <String>

///////////////////////////////////////////////////////////////////////////////////////////////////////

    //Parte de las TAREAS

    @GET("/homework/{materia}") //Buscar taarea mediante el searchview
    suspend fun buscarTareas(
        @Path ("materia") materia : String
    ): Response <BuscarTareas>


    @GET("/homework") //Todas las tareas
    suspend fun tareasGlobales(): Response<TareasGlobales>


    @GET("/homework/{id}") //Las tareas que yo subi <--------------- 2.-
    suspend fun misTareas(
        @Path ("id") id : String
    ): Response<dt_MisTareas>


    @POST("/homework/add") //Guardar Tarea  (ESTE FUNCIONA) (Pulir cosas menores)<--------------------------1.-
    suspend fun guardarTarea (
        @Body homework: Tarea
    ): Response<String>


    @PUT("/homework/{id}") //Actualizar Tarea <-------------------------------------3.-
    suspend fun actualizarTarea(
        @Path ("id") id: String,
        @Body updateH: UpdateTarea
    ): Response<String>

    @DELETE("/usuarios/{id}") //Eliminar Tarea <-----------------------------------4.-
    suspend fun eliminartarea(
        @Path ("id") id : Int
    ): Response <String>

}

object RetrofitClient{
    val webService: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstantes.Base_Url)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}