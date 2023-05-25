import { getC, sql } from "../database/conection";
import { RegistrarUser, InicioSesion, guardarH } from "../database/query";




//REGISTRAR NUEVOS USUARIOS
export const NEWUser = async (req, res) => {

    const { usuario, correo, password } = req.body

    if (usuario == null || correo == null || password == null) { 
        res.json('Llene los campos correspondientes')
    }else{

    try {
        const pool = await getC();
        await pool.request().input("usuario", sql.VarChar, usuario)
            .input("correo", sql.VarChar, correo)
            .input("password", sql.VarChar, password)
            .query(RegistrarUser.InsertUser);
        res.json('Registro exitoso');
    }catch(error){
        res.status(500);
        res.send(error.message);
    }
}
    
};




//ELIMINAR UNA CUENTA (EN PROCESO)
export const deleteUser = async (req, res) => {
    const { UserID } = req.params;
    const pool = await getC();
    await pool.request().input("usuario", sql.VarChar, UserID)
    .query(RegistrarUser.DeleteUser);

    await pool.request().input("miusuario", sql.VarChar, UserID)
    .query(RegistrarUser.DeletemisTareas);

    res.send(result);
    res.json('Su cuenta se ha eliminado')
}




//INICIAR SESION
export const ExistUser = async (req, res) => {
    const { user, email, passW } = req.body;

    try{
        const pool = await getC();
        const result = await pool.request().input("usuario", sql.VarChar, user).
        input("correo", sql.VarChar, email).input("passW", sql.VarChar, passW)
        .query(InicioSesion.existeUser);

        if(result.recordset.length > 0){
            res.json('SI')
        }else{
            res.json('El usuarios no existe, ingrese correctamente su datos')
        }
    }catch(error){ 
        res.status(500);
        res.send(error.message);
    }
}





//VER DATOS DE MI CUENTA
export const vermiCuenta = async (req, res) => {
    const { contraW } = req.params;
    try {
        const pool = await getC();
        const result = await pool.request()
        .input("mipassW", sql.VarChar, contraW)
        .query(InicioSesion.micuenta);
        res.json(result.recordset);
    } catch (error) {
        res.status(500);
        res.send(error.message);
    }
}






//VER LAS PRIMERAS 15 TAREAS
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
    const { nameH, materiaH, DH, url, userN } = req.body;

    try{
        const pool = await getC();
        await pool.request()
        .input("nombreT", sql.NChar, nameH).input("materiaT", sql.NChar, materiaH)
        .input("descripT", sql.VarChar, DH)
        .input("urlT", sql.VarChar, url)
        .input("userT", sql.VarChar, userN)
        .query(guardarH.mitarea);
        res.json('Tarea subida exitosamente');
    }catch(error){ 
        res.status(500);
        res.send(error.message);
    }
}




//ACTUALIZAR UNA TAREA
export const actualizarH = async(req, res) => {
    const { id } = req.params;
    const { newN, newM, newD } = req.body;

    try{
    const pool = await getC();
    await pool.request()
    .input("idH", sql.VarChar, id)
    .input("newnameT", sql.NChar, newN)
    .input("newmateriaT", sql.NChar, newM)
    .input("newDesH", sql.VarChar, newD)
    .query(guardarH.actualizar);
    res.json('Actulizacion completada');
    }catch(error){
        res.status(500);
        res.send(error.message);
    }
}


//ELIMINAR UNA TAREA
export const asesinarH = async (req, res) => {

    const { UserID } = req.params;
    const pool = await getC();
    
    const result = await pool.request().input("ID", UserID)
    .query(guardarH.eliminarT);
    res.send(result);
   
};



//BUSCAR TAREAS POR NOMBRE DE LA MATERIA
export const buscarT = async (req, res) => {
    const { materia } = req.params;
    try {
        const pool = await getC();
        const result = await pool.request()
        .input("materia", sql.NChar, materia)
        .query(guardarH.buscar);
        res.json(result.recordset);
    } catch (error) {
        res.status(500);
        res.send(error.message);
    }

}


/*export const getUser = async (req, res) => {
    try {
        const pool = await getC();
        const result = await pool.request().query(queries.getAllUsers);
        res.json(result.recordset);
    } catch (error) {
        res.status(500);
        res.send(error.message);
    }
};*/