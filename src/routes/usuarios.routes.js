import {Router} from 'express'
import { NEWUser, ExistUser, deleteUser, GuardarTarea, vermisTareas, actualizarH, asesinarH, buscarT, vermiCuenta, verTareas, miFotoPerfil } from '../controllers/usuarios.controller'

const router = Router()
 
router.get('/usuarios/:user', vermiCuenta); //Ruta para ver datos de mi cuenta

router.post('/usuarios/verificacion', ExistUser); //Ruta para el inicio de sesion

router.post('/usuarios/add', NEWUser); //Ruta para registar usuario

router.delete('/usuarios/:UserID', deleteUser); //Ruta para eliminar cuenta

router.put('/usuarios/foto', miFotoPerfil) //Ruta para agregar o actualizar una foto de perfil
   

router.get('/homework/:user', vermisTareas); //Ver mis Tareas

router.get('/homework', verTareas); //Ver TODAS las tareas

router.post('/homework/add', GuardarTarea); //Ruta para guardar tarea

router.put('/homework/update/:id', actualizarH); //Ruta para actualizar una tarea

router.delete('/homework/delete/:id', asesinarH); //Ruta para eliminar una tarea

router.get('/homework/ver/:materia', buscarT); //Buscar tareas por nombre de la materia


export default router 