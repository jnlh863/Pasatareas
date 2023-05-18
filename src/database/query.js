export const RegistrarUser = {
    //getAllUsers : "SELECT * FROM Usuarios",
    InsertUser : "INSERT INTO Usuarios (usuario, correo, contrasena) values (@usuario, @correo, @password)",
    DeleteUser : "DELETE FROM Usuarios where usuario = @usuario"
}

export const InicioSesion = {
    existeUser: "SELECT * FROM Usuarios where usuario = @usuario and correo = @correo and contrasena = @passW"
}