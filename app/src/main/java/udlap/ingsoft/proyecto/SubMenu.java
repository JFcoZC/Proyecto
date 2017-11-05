package udlap.ingsoft.proyecto;

import android.util.Log;
import android.view.View;
import android.widget.RatingBar;

/**
 * Clase que se va a encargar del control logico del submenu de cada tipo de ejercicio. En cada
 * submenu el usuario sera capaz de ver la puntuacion realizada en cada ejercicio de forma grafica
 * con rating bars; ademas si el usuario da click en un ejercicio particular el usuario tiene
 * que ser direccionado a la pantalla en ese ejercicio especifico
 */

public class SubMenu
{

    //Atributos

    //Arreglo con las puntuaciones de cada ejerrcicio del tipo de ejercicios correspondiente
    float[] scores;
    View view;
    //Arreglo con los R.id de cada rating bar
    int ratbars[];

    //Constructor
    public SubMenu(float[] puntiaciones, int[] bars , View v)
    {
        scores = puntiaciones;
        view = v;
        ratbars = bars;

    }//Fin contructor de la clase

    //Metodos

    //----------------------------------------------------------------------------------------------
    //Dada una rating bar cambiar la puntuacion
    public void updateRatingBar(int id, float score)
    {
        RatingBar rbtemp = ( (RatingBar) view.findViewById(id) );

        rbtemp.setRating(score);

    }//Fin metodo RatingBar
    //----------------------------------------------------------------------------------------------
    //Metodo que hace la actualizacion de todos los rating bars dado un arreglo con el id de los
    //rating bars y sus puntuaciones correspondientes
    public void updateAllRbs()
    {
        Log.d("ERROR:","entro update all");
        Log.d("ERROR:","RATBARS[]"+ratbars.length+"=="+"SCORES[]"+scores.length);
        //Primero asegurarse que el numero de scores por nivel corresponda al numero de ratingbars
        if(ratbars.length == scores.length)
        {
            Log.d("ERROR:","mismo tamaño");
            for (int i = 0; i < ratbars.length; i++)
            {

                Log.d("ERROR:","[i]:"+i);
                Log.d("ERROR:","ratbar:"+ratbars[i]);
                Log.d("ERROR:","scores:"+scores[i]);
                //Actualizar el ratingbar
                updateRatingBar(ratbars[i], scores[i]);

            }//Fin for 1
        }//FIn if 1
        else
        {
            //HAY un mismatch en el tamaño de los arreglos
            Log.d("ERROR:"," #DE SCORES Y RBS NO COCNUENRDAN");
        }//Fin else

    }//Fin metodo updateAllRbs

}//Fin clase SubMenu
