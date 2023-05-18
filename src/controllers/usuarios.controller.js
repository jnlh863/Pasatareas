import { getC, sql } from "../database/conection";
import { RegistrarUser, InicioSesion } from "../database/query";

export const NEWUser = async (req, res) => {

    const { usuario, correo, password } = req.body

    if (usuario == null || correo == null || password == null) { 
        res.json('Llene los campos correspondientes')
    }

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
    
};

export const deleteUser = async (req, res) => {
    const { UserID } = req.body;
    const pool = await getC();
    const result = await pool.request().input("usuario", sql.VarChar, UserID)
    .query(RegistrarUser.DeleteUser);

    res.send(result);
}

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

/*export const getUser = async (req, res) => {try {
        const pool = await getC();
        const result = await pool.request().query(queries.getAllUsers);
        res.json(result.recordset);
    } catch (error) {
        res.status(500);
        res.send(error.message);
    }
};*/