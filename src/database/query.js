export const RegistrarUser = {
    //getAllUsers : "SELECT * FROM Usuarios",
    InsertUser : "INSERT INTO Usuarios (usuario, correo, contrasena) values (@usuario, @correo, @password)",
    DeleteUser : "DELETE FROM Usuarios where usuario = @usuario",
    DeletemisTareas: "DELETE FROM misTareas where userName = @miusuario"
}

export const InicioSesion = {
    existeUser: "SELECT * FROM Usuarios where usuario = @usuario and correo = @correo and contrasena = @passW",
    micuenta: "SELECT usuario, correo FROM Usuarios where contrasena = @mipassW"
}

export const guardarH = {
    
    buscar: "SELECT * FROM misTareas WHERE materiaT = @materia",
    verTareas : "SELECT * FROM misTareas",
    //vermisTareas: "SELECT * FROM misTareas where idUsuario = @user",
    mitarea: "INSERT INTO misTareas (nameT, materiaT, DesH, urlT, userName) values (@nombreT, @materiaT, @descripT, @urlT, @userT)",
    eliminarT: "DELETE FROM misTareas where idUsuario = @miusuario",
    actualizar: "UPDATE misTareas SET nameT = @newnameT, materiaT = @newmateriaT, DesH = @newDesH where idUsuario = @idH"
}