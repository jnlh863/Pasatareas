export const RegistrarUser = {
    //getAllUsers : "SELECT * FROM Usuarios",
    InsertUser : "INSERT INTO Usuarios (usuario, correo, contrasena) values (@usuario, @correo, @password)",
    DeleteUser : "DELETE FROM Usuarios where usuario = @usuario",
    DeletemisTareas: "DELETE FROM misTareas where userName = @miusuario"
}

export const InicioSesion = {
    existeUser: "SELECT * FROM Usuarios where usuario = @usuario and correo = @correo and contrasena = @passW",
    micuenta: "SELECT usuario, correo, FP FROM Usuarios where usuario = @miuser",
    actualizarFoto: "UPDATE Usuarios SET FP = @miFoto WHERE correo = @correo"
}

export const guardarH = {
    
    buscar: "SELECT * FROM misTareas WHERE materiaT = @materia",
    verTareas : "SELECT * FROM misTareas",
    vermisTareas: "SELECT * FROM misTareas WHERE userName = @user" ,
    mitarea: "INSERT INTO misTareas (nameT, materiaT, DesH, urlT, userName) values (@nombreT, @materiaT, @descripT, @urlT, @userT)",
    eliminarT: "DELETE FROM misTareas where idUsuario = @ID",
    actualizar: "UPDATE misTareas SET nameT = @newnameT, materiaT = @newmateriaT, DesH = @newDesH, urlT = @newURL where idUsuario = @idH"
}