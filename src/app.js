import express from 'express';
import config from './config';
import  URoutes from './routes/usuarios.routes';

const bodyParser = require('body-parser')

const app = express();

app.set('port', config.port);

app.use(bodyParser.json());
//app.use(express.urlencoded({ extended: false }));
app.use(URoutes);

export default app;