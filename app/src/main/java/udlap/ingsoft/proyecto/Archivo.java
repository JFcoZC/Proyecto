package udlap.ingsoft.proyecto;

/**
 * Clase para administracion de archivo de datos con la informacion de cada usario de la aplicacion.
 * La escritura se realiza al final del archivo y el borrado de los usarios se hace por medio de
 * borrado lógico
 *José Francisco Zerón Cabrera
 */

//Librerias
import android.util.Log;

import java.io.*;

//Incio clase Archivo
public class Archivo
{
    //Atributos
    RandomAccessFile raf = null;

    //Constructor
    public Archivo(RandomAccessFile raf)
    {
        this.raf = raf;
    }//Fin constructor

    //Metodos
    //----------------------------------------------------------------------------------------------
    //Insercion de usuario al final del archivo usando serializacion de Objetos de la clase Usuario
    void insertion(Usuario user) throws IOException
    {
        //---------------------------------
        Log.d("xsxBInsertion:","Archivo");
        Log.d("xsxEscrito: ", user.getName());
        Log.d("xsxLongitud: ", ""+user.length());   //1407
        Log.d("xsxFLOAT: ",""+Float.SIZE);
        Log.d("xsxPosInsertion: ", ""+raf.length());
        //----------------------------------

        //Posicionarse al final del archivo
        raf.seek(raf.length());

        //Insertar registro siempre en ultima posicion
        user.write(raf);

    }//Fin metodo insertion
    //----------------------------------------------------------------------------------------------
    //Metodo que modifica/sobreescribe el contenido de un usuario dada su posicion
    void modifyuser(int ipos, Usuario newuser) throws IOException
    {
        //Posicionarse en la posicion indicada y bajar/escribir los datos del usario
        raf.seek(ipos*newuser.length());
        newuser.write(raf);

    }//Fin metodo modifyuser
    //----------------------------------------------------------------------------------------------
    //Metodo que dado el nombre del usuario regresa la posicion en archivo donde se encuentra ubicado
    int searchuser(String nombre)throws IOException
    {
        //---------------------------------
        Log.d("xsxBSearchUser:","Archivo");
        //----------------------------------

        //Usuario temporal para hacer recorrido en archivo sin necesidad de encimar info
        Usuario tempuser = new Usuario();

        //POsicion del registro buscado
        int pos = -1;

        //Determinar el numero total de usarios que existen guardados dentro del archivo
        int totalusers = (int) ( (raf.length())/ tempuser.length());
        //---------------------------------
        Log.d("xsxTotalUS:",""+totalusers);
        //----------------------------------

        //Nota: Get de name regresa la cadena de bytes ya en forma de String
        //por lo que la comparacion de los nombres de usuarios puede ser
        //en formato de string (cuidando que esten escritas exactamente igual).
        //Lo unico hay que cuidar son los bytes de más que no son ocupados en el
        //campo name

        //searchedname es una cadena de caracteres de tamaño 30
        char[] searchedname = new char[tempuser.getName().length()];
        int i = 0;
        String nombrecomparado = "";

        //Pasar el nombre del usario que se esta buscando a una cadena del mismo tamaño que el maximo
        //permitido(30) caracteres

        //Guardar cada caracter en el arreglo de caracteres de tamaño 30
        while(i < nombre.length())
        {
            searchedname[i] = nombre.charAt(i);

            i++;
        }//Fin while 1

        //Pasar de arreglo de caracteres a string de 30 caracteres
        nombrecomparado = String.copyValueOf(searchedname);

        //Hacer el recorrido del archivo de cada usuario en busca de uno que no este borrado logicamente
        //y que tenga el mismo nombre que el que se busca. Regresar la posicion en archivo del primero
        // que se encuentre; si no se encuentra nada regresar -1
        for(int idx = 0; idx < totalusers; idx++)
        {
            //Leer usuario en posicion idx y cargarlo en Usuario temporal
            raf.seek(idx*tempuser.length());
            tempuser.read(raf);

            //El arreglo de caracteres ahora en la variable nombrecomparado considerarlo como sting
            //compararlo con cada nombre de usuario en el archivo siempre y cuando sea un Usuario que
            //no haya sido marcado como eliminado
            //---------------------------------
            Log.d("xsx1:",""+tempuser.getName().equals(nombrecomparado));
            Log.d("xsx2:",""+tempuser.getBorradol()+"== 48 ?");
            //----------------------------------
            if( ( tempuser.getName().equals(nombrecomparado) ) && tempuser.getBorradol() == 0)
            {
                //Conservar posicion del usuario actual
                pos = idx;

                //Forzar salida del for
                idx = totalusers;

            }//Fin if 1

        }//Fin for 1

        //---------------------------------
        Log.d("xsxPOSICION:",""+pos);
        //----------------------------------

        return pos;

    }//Fin metodo searchuser
    //----------------------------------------------------------------------------------------------
    //Metodo que regresa todoo el objeto Usuario dad su posicion
    //----------------------------------------------------------------------------------------------
    Usuario returnUser(int ipos) throws IOException
    {
        Usuario tempus = new Usuario();

        //Posicionarse en archivo y cargar en memoria el usuario en la posicion correspondiente
        raf.seek(ipos*tempus.length() );
        tempus.read(raf);

        return tempus;

    } //Fin metodo returnUser
    //----------------------------------------------------------------------------------------------
}//Fin clase Archivo
