import { getC, sql } from "../database/conection";
import { RegistrarUser, InicioSesion, guardarH } from "../database/query";


//VER DATOS DE MI CUENTA
export const vermiCuenta = async (req, res) => {
    const obj = { }
    const user  = req.params.user;

    try {
        const pool = await getC();
        const result = await pool.request()
        .input("miuser", user)
        .query(InicioSesion.micuenta);
        obj.misT = result.recordset
    res.json(obj);
    } catch (error) {
        res.status(500);
        res.send(error.message);
    }
}




//COLOCAR UNA FOTO DE PERFIL
export const miFotoPerfil = async(req, res) => {

    const { correo, urlimg } = req.body;

    if(correo == null || urlimg == null){
        res.json("Algo salio mal, vuelva a intentarlo")
    }
    
    try{   

    const pool = await getC();
    await pool.request()
    .input("correo", sql.VarChar, correo)
    .input("miFoto", sql.VarChar, urlimg)
    .query(InicioSesion.actualizarFoto);
    res.json('Actualizacion completada');

    }catch(error){
        res.status(500);
        res.send(error.message);
    }
}


  

//INICIAR SESION
export const ExistUser = async (req, res) => {
    const { user, email, passW } = req.body;

    if(user == null || email == null){
        res.json('Llene todos los campos')
    }

    if(passW == null){
       res.json('Contraseña Obligatoria')
    }

    try{
        const pool = await getC();
        const result = await pool.request().input("usuario", sql.VarChar, user).
        input("correo", sql.VarChar, email).input("passW", sql.VarChar, passW)
        .query(InicioSesion.existeUser);

        if(result.recordset.length > 0){
            res.json('SI')
        }else{
            res.json('El usuarios no existe, ingrese correctamente sus datos')
        }
    }catch(error){ 
        res.status(500);
        res.send(error.message);
    }
}




//REGISTRAR NUEVOS USUARIOS
export const NEWUser = async (req, res) => {

    const { usuario, correo, password } = req.body

    if(usuario == null || correo == null){
        res.json('Llene todos los campos')
    }

    if(password == null){
       res.json('Contraseña Obligatoria')
    }
   
    try {
        const pool = await getC();
        await pool.request().input("usuario", sql.VarChar, usuario)
            .input("correo", sql.VarChar, correo)
            .input("password", sql.VarChar, password)
            .query(RegistrarUser.InsertUser);
        res.json('Registro exitoso');
    }catch(error){
        res.json('Ya existe una cuenta con estos datos')
        res.status(500);
        res.send(error.message);
    }  
};




//ELIMINAR UNA CUENTA 
export const deleteUser = async (req, res) => {

    const UserID  = req.params.UserID;

    try{
    const pool = await getC();
    await pool.request().input("usuario", UserID)
    .query(RegistrarUser.DeleteUser);

    await pool.request().input("miusuario", UserID)
    .query(RegistrarUser.DeletemisTareas);

    res.json('Su cuenta se ha eliminado')
    }catch(error){
        res.status(500);
        res.send(error.message);
    }
}

////////////////////////////////////////////////////////////////////////////////////////




//VER MIS TAREAS
export const vermisTareas = async (req, res) => {
    const obj = { }
    const user = req.params.user;

    try{
    const pool = await getC();
    const result = await pool.request().input("user", user)
        .query(guardarH.vermisTareas);
    obj.misT = result.recordset
    res.json(obj);
    }catch(error){
        res.status(500);
        res.send(error.message);
    }
}




//VER TODAS LAS TAREAS
export const verTareas = async (req, res) => {
    const obj = { }
    try {
        const pool = await getC();
        const result = await pool.request()
        .query(guardarH.verTareas);
        obj.misT = result.recordset
        res.json(obj);
    } catch (error) {
        res.status(500);
        res.send(error.message);
    }
}




//GUARDAR UNA TAREA 
export const GuardarTarea = async (req, res) => {
    const { nameT, materiaT, DesH, urlT, userName } = req.body;

    if(nameT == null || materiaT == null || DesH == null || urlT == null || userName == null){
        res.json('Llene todos los campos')
    }

    try{
        const pool = await getC();
        await pool.request()
        .input("nombreT", sql.NChar, nameT).input("materiaT", sql.NChar, materiaT)
        .input("descripT", sql.VarChar, DesH)
        .input("urlT", sql.VarChar, urlT)
        .input("userT", sql.VarChar, userName)
        .query(guardarH.mitarea);
        res.json('Tarea subida exitosamente');
    }catch(error){ 
        res.status(500);
        res.send(error.message);
    }
}




//ACTUALIZAR UNA TAREA
export const actualizarH = async(req, res) => {
    const id  = req.params.id;
    const { newN, newM, newD , newUrl} = req.body;

    if(newN == null || newM == null || newD == null || newUrl == null){
        res.json('Llene todos los campos')
    }


    try{
    const pool = await getC();
    await pool.request()
    .input("idH", sql.Int, id)
    .input("newnameT", sql.NChar, newN)
    .input("newmateriaT", sql.NChar, newM)
    .input("newDesH", sql.VarChar, newD)
    .input("newURL", sql.VarChar, newUrl)
    .query(guardarH.actualizar);
    res.json('Actualizacion completada');
    }catch(error){
        res.status(500);
        res.send(error.message);
    }
}




//ELIMINAR UNA TAREA
export const asesinarH = async (req, res) => {

    const id = req.params.id;
    try{
        const pool = await getC();
        await pool.request().input("ID", id)
        .query(guardarH.eliminarT);
        res.json("Tarea eliminada");
    }catch(error){
        res.status(500);
        res.send(error.message);
    }
};




//BUSCAR TAREAS POR NOMBRE DE LA MATERIA
export const buscarT = async (req, res) => {
    
    const obj = { }
    const materia  = req.params.materia;
    
    try {
        const pool = await getC();
        const result = await pool.request()
        .input("materia", materia)
        .query(guardarH.buscar);
        obj.misT = result.recordset
        res.json(obj);
    } catch (error) {
        res.status(500);
        res.send(error.message);
    }

}