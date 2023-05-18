import {Router} from 'express'
import { NEWUser, ExistUser, deleteUser, GuardarTarea, actualizarH, asesinarH, vermisTareas, buscarT, vermiCuenta, verTareas } from '../controllers/usuarios.controller'

const router = Router()

router.get('/usuarios/:passW', vermiCuenta); //Ruta para ver datos de mi cuenta

router.post('/usuarios/verificacion', ExistUser); //Ruta para el inicio de sesion

router.post('/usuarios/add', NEWUser); //Ruta para registar usuario

router.delete('/usuarios/:id', deleteUser); //Ruta para eliminar cuenta



router.get('/homework/:materia', buscarT); //Buscar tareas por nombre de la materia

router.get('/homework', verTareas); //Ver las primeras 15 tareas

router.get('/homework/:id', vermisTareas); //Ver las tareas que yo subi

router.post('/homework/add', GuardarTarea); //Ruta para guardar tarea

router.put('/homework/:id', actualizarH); //Ruta para actualizar una tarea

router.delete('/homework/:id', asesinarH); //Ruta para eliminar una tarea


export default router