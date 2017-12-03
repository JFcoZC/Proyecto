package udlap.ingsoft.proyecto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Controla las acciones del menu de los ejercicios del nivel de observadores
 */

//Inicio main class
public class MenuExploradores extends AppCompatActivity
{
    //Declaracion de variables globales
    ImageButton home;
    //DECLARAR VARIABLE GLOBAL DEL ID DEL USUARIO ACTUAL
    int IDCURRENTUSER = -1;

    //ATRIBUTOS

    //INICIO METODOS

    //Lllama al XML para mostrar la pantalla del menu
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_exploradores);

        home = (ImageButton) findViewById(R.id.HomeButton);

        //RECIBIR ID CURRENT USER PROVENIENTE DE MNUPRINCIPAL
        Intent in = getIntent();
        //El 0 es el valor de default que va a recibir la variable si no hay nada en el intent
        IDCURRENTUSER = in.getIntExtra("IDUSER",0);

    }//Fin metodo OnCreate

    //Metodo que inicia acticidades de juegosilabicos
    public void ClickJuegoSilabico(View v)
    {
        //Un intent es un objeto que permite un enlace en tiempo de ejecución entre 2 actividades
        //son generalemnte utilziadas para realizar varias tareas al mismo tiempo
        Intent in = new Intent(this, SubmSilabicExp.class);

        //Mandar IDUSUARIO ACTUAL A ACTIVITY Submenu Juego Silabico
        in.putExtra("IDUSER", IDCURRENTUSER);
        startActivity(in);
        finish();



    }//Fin metodo ClickJuegoSilabico

    //Metodo que inicia actividades de juegosopadeletras
    public void ClickJuegoSopa(View v)
    {
        Intent in = new Intent(this, SubmSopaExp.class);

        //Mandar IDUSUARIO ACTUAL A ACTIVITY Submenu Juego Sopa de letras
        in.putExtra("IDUSER",IDCURRENTUSER);
        startActivity(in);
        finish();

    }//Fin metodo ClickJuegoSopa

    //Metodo que llama a ir de regreso a menu principal si se oprime boton Home
    public void HomeClick(View vi)
    {
        //Si se oprime el boton HOME
        if(vi == home)
        {
            //CREAR UN INTENT

            //Un intent es un objeto que permite un enlace en tiempo de ejecución entre 2 actividades
            //son generalemnte utilziadas para realizar varias tareas al mismo tiempo
            //El constructor de Intent recibe 2 parametros un Context y una Class
            //1) se puede usar this porque la clase Activity (que hereda la EjercicioSilabico) extiende de Context
            //2)La clase que se le debe asignar al intent
            Intent in = new Intent(this, MenuPrincipal.class);

            //Mandar id a actividad de menu principal
            in.putExtra("IDUSER",IDCURRENTUSER);

            //Inicar nueva actividad creada en line anterior/ ir a menu principal
            startActivity(in);
            finish();

        }//Fin if 1

    }//Fin metodo HomeClick


}//Fin clase MenuExploradores
