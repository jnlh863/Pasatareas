import {Router} from 'express'
import { NEWUser, ExistUser, deleteUser } from '../controllers/usuarios.controller'

const router = Router()

router.post('/usuarios/verificacion', ExistUser); //Ruta para el inicio de sesion

router.post('/usuarios/add', NEWUser); //Ruta para registar usuario

router.delete('/usuarios/:id', deleteUser); //Ruta para eliminar cuenta


export default router