import sql from 'mssql'

const dbSettings = {
    user:  'AppsMoviles032PIA',
    password:  'AppsMovilesPIAgpo032',
    server: 'pasatareasdb.database.windows.net',
    database: 'BD_PIA',
    options: {
        encrypt: true,
        trustServerCertificate: true,
    },
};

export async function getC(){
    try{
        const pool = await sql.connect(dbSettings);
        return pool;
    }catch(error){
        console.log(error);
    }
}

export {sql};





