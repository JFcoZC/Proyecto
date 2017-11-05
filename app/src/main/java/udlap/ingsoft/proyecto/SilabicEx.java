package udlap.ingsoft.proyecto;

/**
 Clase que contiene todos los atributos necesarios para formar un ejercicio de formar palabras
 por medio de seleccionar 2 silabas. Este tipo de ejercicio es perteneciente al nivel 2
 José Francisco Zerón Cabrera
 */

//Librerias
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.Random;

public class SilabicEx extends Activity
{
    //Atributos
    //Por convencion [0] corresponde a la primera silaba correcta y [1] a la segunda correcta
    //silaba correcta
    String[] silabas = new String[4] ;
    //VALORES MediaPlayer DE RESOURCES (R) //SONIDOS silabas
    MediaPlayer[] valores = new MediaPlayer[4];
    //Valor palabra corecta
    MediaPlayer palabra;
    int posicion;
    //Arreglo que representa si las silabas estan siendo marcadas por el checkbox
    int[] estado = {0,0,0,0};

    //----------------------------------------------------------------------------------------------
    //Constructor
    public SilabicEx(String cone, String ctwo, String ctre, String cfou, int word, int[] resources, int id, Context c)
    {
        Log.d("fcoz","in");
        if(c==null)
        {
            Log.d("fcoz","null");
        }//Fin if 1
        else
        {
            Log.d("fcoz","nonull");
        }

       silabas[0] = cone;
       silabas[1] = ctwo;
       silabas[2] = ctre;
       silabas[3] = cfou;

        Log.d("fcoz","");

       palabra =  MediaPlayer.create(c,word);

        Log.d("fcozz","");

       valores = ToMediaPlayer(resources, c);

        Log.d("fcozzz","");
       //Posicion en el arreglo de SilabicEx
       posicion = id;

    }//Fin constructor SilabicEx
    //------------------------------------
    //Setters & Getters
    //Metodo que regresa el valor entero de un Resource asociado (usado para los audios)
    MediaPlayer getResourceNum(int indx) { return valores[indx]; }//Fin metodo getResourceNum

    //Metodo que regresa la cadena de la silaba en la posicion especificada
    String getThisSilaba(int i)
    {
        return silabas[i];
    }//Fin metodo getThisSilaba

    //Metodo que regresa estado de seleccion de la primer silaba correcta
    int StateFirstRight()
    {
        return estado[0];
    }//Metodo que regresa estado primera silaba correcta

    //Metodo que regresa estado de seleccion de la segunda silaba correcta
    int StateSecondRight()
    {
        return estado[1];
    }//Fin metodo StateSecondRight

    //Metodo que regresa estado de seleccion de la primer silaba incorrecta
    int StateFirstWrong() {return estado[2];}//Fin metodo StateFirstWrong

    //Metodo que regresa la segunda silaba incorrecta
    int StateSecondWrong() { return estado[3]; }//Metodo que regresa estado segunda silaba incorrecta

    //Metodo que regresa el valor entero del Resource de toda la palabra(usado para el audio)
    MediaPlayer getPalabraValue()
    {
        return palabra;
    }//Fin metodo getPalabraValue

    //Metodo que regresa el valor id/posicion en arreglod e SilabicEx
    int getPosicion()
    {
        return posicion;
    }//Fin metodo getPosicon

    //Metodo que dado el numero de silaba la marca como activada por el checkbox
    void SilabaSelected(int indx){ estado[indx] = 1; }

    //Metodo que dado el numero de silaba la marca como desactivada por el checkbox
    void SilabaDeselected(int indx){ estado[indx] = 0; }

    //--------------------------FIN GETTERS & SETTERS------------------------------------

    //Metodos
    //------------------------------------------------------------------------------------
    //Metodo que regresa las posiciones de las 4 slabas en un arreglo pero en un orden aleatorio
    int[] RandomSilabas()
    {
        //Crear un valor aleatorio entre 0 - 3
        Random random = new Random();
        int rand;
        int contador = 0;

        //Arreglo que va a contener silabas en orden aleatorio
       int[] silabasrandom = {-1,-1,-1,-1};

        //Arreglo para control de repeticion de numero random
        //false == no se ha colocado esa silaba / true == ya se coloco esa silaba
        boolean[] repetido = {false,false,false,false};

        //Mientras no se hallan colocado todas las silabas
        while(repetido[0]== false || repetido[1] == false || repetido[2] == false || repetido[3]== false)
        {
            rand  = random.nextInt(3 - 0 + 1) + 0;

            //El numero aleatorio ya paso
            if(repetido[rand] == false)
            {
                //No ha pasado ese nuemro

                //Agregar en posicion corespondiente de silabasrandom
                // la posicion rand
                silabasrandom[contador] = rand;

                //Marcar que ya pasao el numero
                repetido[rand] = true;
                //Aumentar en uno el contador
                contador++;

            }//Fin if 1
            else
            {
                //No hacer nada y que intente con otro numero aleatorio
            }//Fin else 1

        }//Fin while

        return silabasrandom;

    }//Fin metodo silabas
    //-------------------------------------------------------------------------------------
    //Metodo que resetea los estados de seleccion de las silabas
    public void ResetEstados()
    {
        estado[0] = 0;
        estado[1] = 0;
        estado[2] = 0;
        estado[3] = 0;

    }//Fin metodo ResetEstados
    //------------------------------------------------------------------------------------
    //Metodo que conveirte el valor entero de la R de cada sonido a un MediaPLayer para
    //que este sea pasado al MediaPlayer sin tener que andar creando un MediaPlayer
    //cada vez que se quiera reproducir un sonido
   MediaPlayer[] ToMediaPlayer(int[] sonidos, Context C)
    {
        //Crear un arreglo de asfd del mismo tamaño que el de sonidos(siempre son 5 sonidos)
        //4 silabas y palabra
        MediaPlayer[] posiciones = new MediaPlayer[sonidos.length];

        for(int i = 0; i < posiciones.length; i++)
        {

            posiciones[i] = MediaPlayer.create(C, sonidos[i]);

        }//Fin for 1

        return posiciones;

    }
    //------------------------------------------------------------------------------------
}//Fin clase SilabicEx
