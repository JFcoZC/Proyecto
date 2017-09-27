package udlap.ingsoft.proyecto;

/**
 * Clase para manejar la creacion y operacion del archivo de datos local con la informacion de cada
 * usuario.
 * Referencias clase Context: https://stackoverflow.com/questions/14948362/how-to-initialize-context
 * Referencia Guardar un archivo en almacenamiento interno: https://developer.android.com/training/basics/data-storage/files.html
 *
 * La info del almacenamiento interno es borrada cuando se desinstala el app. Solo la app en el dispositivo
 * puede acceder al archivo de almaecenamiento interno, ideal cuando se desea que ningun otro usuario o
 * app acceda a la informacion
 */

//Librerias
import android.content.Context;
import android.util.Log;

import java.io.*;

//Inicio clase Datos
public class Datos
{
    //Atributos

    // Un objeto de la clase Context no se pude inicializar, esto es controlado por el sistema. Sin
    //embargo cada aplicacion tiene Contexto y también cada clase que extiende de la clase Activity.
    //Formas de obtener el context:
    //1)Asignar un puntero a un objeto de la clase Activity y este asignarlo al puntero del objeto de clase Context
    //2)En una clase que extienda de Activity se pude llamar al metodo getApplicationContext() que regresa el
    //apuntador del context para asignarlo al objeto de clase Context

    Context context;

    //Constructores
    public Datos(Context c) {context = c; }

    //Metodos
    //----------------------------------------------------------------------------------------------
    //Metodo de Alta de usuario
    public void Alta(int id, String name)
    {
        //Crear usuario
        Usuario newuser = new Usuario(id,name);

        try
        {
            //Abrir archivo
            //Metodo getFilesDir() regresa el path donde se enceuntra ubicado el archivo con la info
            //No se necesitanpermisos adicionales para poder hacer que la app lea o escriba archivos
            //del path
            File doc = new File(context.getFilesDir(),"Usuarios.data");
            //"rw" = crear archivo si no existe, si ya existe sobreescribirlo
            RandomAccessFile raf = new RandomAccessFile(doc,"rw");

            Archivo arch = new Archivo(raf);

            //Realizar alta
            arch.insertion(newuser);

            //Cerrar Archivo
            raf.close();

        }catch (IOException ioe)
        {
            //Mostrar donde ocurrio la exception
            ioe.printStackTrace();

        }//Fin catch

    }//Fin metodo Alta
    //----------------------------------------------------------------------------------------------
    //Metodo de Consulta de info actual de Usuario dado el nombre del Usuario; regresa objeto de clase
    //Usuario con datos validos si se encuentra; si no se encuentra se regresa objeto de clase USuario
    //con id=-1 y name:"Null"
    Usuario SearchName(String name)
    {
        Usuario tempuser = new Usuario(-1,"Null");
        int posuser = -1;

        //Abrir Archivo
        try
        {
            //Abrir archivo
            File doc = new File(context.getFilesDir(),"Usuarios.data");
            //"rw" = crear archivo si no existe, si ya existe sobreescribirlo
            RandomAccessFile raf = new RandomAccessFile(doc,"rw");

            Archivo arch = new Archivo(raf);

            //Realizar busqueda
            posuser = arch.searchuser(name);

            if(posuser != -1)
            {
                //Se encontro al usuario cambiarle los valores
                tempuser = arch.returnUser(posuser);
                //Log.d("xsxEncontrado: ",name);

            }//Fin if 1

            //Log.d("xsxEncontrado: ",""+posuser);

            //Cerrar archivo
            raf.close();

        }catch(IOException ioe)
        {
            ioe.printStackTrace();
        }//Fin catch

        return tempuser;

    }//Fin metodo SearchName
    //----------------------------------------------------------------------------------------------
    //Metodo de Sobreescritura de Usuario en posicion p con base en los scores actuales en cada nivel y el tiempo
    //de uso de la aplicación
    public void ActualizarDatos(int p, float time, float[] scorelvl1, float[] scorelvl2, float[] scorelvl3)
    {
        Usuario tempuser = new Usuario();

        //Abrir archivo
        try
        {
            //Abrir archivo
            File doc = new File(context.getFilesDir(),"Usuarios.data");
            //"rw" = crear archivo si no existe, si ya existe sobreescribirlo
            RandomAccessFile raf = new RandomAccessFile(doc,"rw");

            Archivo arch = new Archivo(raf);

            //Cargar datos actuales usuario en posicion p
            tempuser = arch.returnUser(p);

            //Realizar cambios de usuario
            tempuser.UserData(time, scorelvl1,scorelvl2,scorelvl3);

            //Bajra los nuevos cambios a Archivo
            arch.modifyuser(p,tempuser);

            //Cerrar archivo
            raf.close();

        }catch(IOException ioe)
        {
            ioe.printStackTrace();
        }//Fin metodo catch

    }//Fin metodo ActualizarDatos
    //----------------------------------------------------------------------------------------------
}//Fin clase Datos
