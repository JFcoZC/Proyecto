package udlap.ingsoft.proyecto;

//Librerias

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;


/**
 * Clase que contiene la lógica que se seguira en el menu principal
 * José Francisco Zerón Cabrera
 */

//INICIO CLASE MENUPRINCIPAL
public class MenuPrincipal extends Activity
{
    //VARIABLES GLOBALES

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        //**Fin de la actividad
        Log.d("Fin Act:MenuPrincipal", "v1");

    }//Fin metodo que llama archivo xml .menu_principal
    //----------------------------------------------------------------------------------------------
    //Metodo que inicia actividades correspondientes al nivel exploradores
    public void ClickExploradores(View v)
    {
        //Un intent es un objeto que permite un enlace en tiempo de ejecución entre 2 actividades
        //son generalemnte utilziadas para realizar varias tareas al mismo tiempo
        Intent in = new Intent(this, MainActivity.class);
        startActivity(in);

    }//Fin metodo ClickExplroadores
    //----------------------------------------------------------------------------------------------
    //Metodo que inica actividades correspondientes a  menu statistics
    public void ClickStatistics(View vi)
    {
        Intent inte = new Intent(this, MenuStatistics.class);
        startActivity(inte);
    }//Fin metodo ClickSatistics
    //----------------------------------------------------------------------------------------------
    //Metodo de prueba que crea un Usuario
    public void CrearUsuario(View v)
    {
        Datos datos = new Datos(this);

        datos.Alta(1,"Paco");
        datos.Alta(2,"Pedro");

    }//Fin metodo CrearUsuario
    //----------------------------------------------------------------------------------------------
}//Fin clase MenuPrincipal
