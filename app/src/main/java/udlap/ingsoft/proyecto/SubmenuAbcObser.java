package udlap.ingsoft.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Clase que contiene la lógica del submenu que direcciona a ejercicios de letras de abecedario
 */

public class SubmenuAbcObser extends AppCompatActivity
{
    //Atributos

    //Metodos
    protected void onCreate(Bundle SavedInstnceState)
    {
        super.onCreate(SavedInstnceState);
        setContentView(R.layout.submenu_abc_obser);

    }//Fin metodo onCreate
    //----------------------------------------------------------------------------------------------
    //Metodo que llama a ir de regreso a menu principal si se oprime boton Home
    public void HomeClick(View vi)
    {

        //CREAR UN INTENT

        //Un intent es un objeto que permite un enlace en tiempo de ejecución entre 2 actividades
        //son generalemnte utilziadas para realizar varias tareas al mismo tiempo
        //El constructor de Intent recibe 2 parametros un Context y una Class
        //1) se puede usar this porque la clase Activity (que hereda la EjercicioSilabico) extiende de Context
        //2)La clase que se le debe asignar al intent
        Intent in = new Intent(this, MenuPrincipal.class);

        //Inicar nueva actividad creada en line anterior/ ir a menu principal
        startActivity(in);

    }//Fin metodo HomeClick
    //----------------------------------------------------------------------------------------------
    //Si se presiona el boton determinar que boton ha sido y de esta manera determinar en
    //que letra debe ser abierto al iniciar la siguiente pantalla
    public void SelectLetra(View vi)
    {
        //Inciiar actividad de ejercicioAbecedario
        Intent inte = new Intent(this, EjercicioAbecedario.class);
        startActivity(inte);

    }//Fin metodo SelectLetra

}//Fin clase SubmenuAbcObser
