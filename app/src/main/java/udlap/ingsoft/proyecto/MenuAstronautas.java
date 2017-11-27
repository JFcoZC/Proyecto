package udlap.ingsoft.proyecto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Controla las acciones del menu de los ejercicios del nivel de astronautas
 */

//Inicio clase
public class MenuAstronautas extends AppCompatActivity
{
    //Declaracion de elementos interfaz
    ImageButton bhome;
    //DECLARAR VARIABLE GLOBAL DEL ID DEL USUARIO ACTUAL
    int IDCURRENTUSER = -1;

    //METODOS

    //Lllama al XML para mostrar la pantalla del menu
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_astronautas);

        bhome = (ImageButton) findViewById(R.id.HomeButton);

        //RECIBIR ID CURRENT USER PROVENIENTE DE MENUPRINCIPAL
        Intent in = getIntent();
        //El 0 es una variable de default en caso de que atraves del intent no se obtuviera nada
        IDCURRENTUSER = in.getIntExtra("IDUSER",0);

    }//Fin metodo OnCreate
    //----------------------------------------------------------------------------------------------
    //Metodo que llama a ir de regreso a menu principal si se oprime boton Home
    public void HomeClick(View vi)
    {
        //Si se oprime el boton HOME
        if(vi == bhome)
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

        }//Fin if 1

    }//Fin metodo HomeClick
    //----------------------------------------------------------------------------------------------
    //Metodo que inicia acticividad de SUBMENU ejercicio ortografia
    public void OrtografiaClick(View v)
    {
        Intent in = new Intent(this, SubmenuOrtoAstro.class);
        //Mandar ID usuario actual a siguiente pantalla de submenu de ejercicios ortograficos
        in.putExtra("IDUSER",IDCURRENTUSER);
        //Iniciar nueva actividad
        startActivity(in);

    }//Fin metodo OrtografiaClick
    //----------------------------------------------------------------------------------------------
    //Metodo que inicia actividad de Submenu lecturas animadas
    public void LecturasClick(View v)
    {
        Intent in = new Intent(this, SubmenuLecturaAstro.class);
        //Mandar ID usuario actual a siguiente pantalla de submenu de lecturas animadas
        in.putExtra("IDUSER",IDCURRENTUSER);
        //Iniciar nueva actividad
        startActivity(in);

    }//Fin metodo LecturasClick

}//Fin clase MenuAstronautas
