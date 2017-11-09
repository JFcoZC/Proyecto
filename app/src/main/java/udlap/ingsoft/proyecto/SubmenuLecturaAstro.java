package udlap.ingsoft.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Patty on 07/11/2017.
 * Submenu de nivel ASTRONAUTA que contiene todos los ejerecicios y el progreso de cada nivel
 * de las lecturas animadas
 */

public class SubmenuLecturaAstro extends AppCompatActivity
{
    //Variables globales
    ImageButton home;
    //DECLARAR VARIABLE GLOBAL DEL ID DEL USUARIO ACTUAL
    int IDCURRENTUSER = -1;
    //DECLARACION VARIABLE GLOBAL DEL USUARIO
    Usuario CURRENTUSER;

    //Atributos de LECTURAS ANIMADAS
    float[] scoreslvlfive;
    //Arreglo con id de rating bars de Lecturas; debe coincidir con el NUEXERCISE definifo en la
    //clase USUARIO
    int[] idrbslec = {R.id.rb1astroL,R.id.rb2astroL,R.id.rb3astroL};

    //Metodos
    //----------------------------------------------------------------------------------------------
    //-On create
    public void onCreate(Bundle SavedInstanceState)
    {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.submenu_lectura_astro);

        home = (ImageButton) findViewById(R.id.HomeButton);

        //------ ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-------
        //***Obtener el ID DEL USUARIO ACTUAL
        Intent in = getIntent();
        //0 valor de default que rescibiria la variable si no hay nada en el Intent
        IDCURRENTUSER = in.getIntExtra("IDUSER",0);

        //***Obtener los scores del usuario actual
        CURRENTUSER = new Usuario(IDCURRENTUSER,this);

        //Obtener los escores del lvlSIX=juego de ORTOGRAFIA
        scoreslvlfive = CURRENTUSER.getScoreslvlfive();

        //Crear objeto de la clase SubMenu utilizando la view actual
        SubMenu submsilabas = new SubMenu(scoreslvlfive,idrbslec,findViewById(android.R.id.content));

        //Hacer actualizacion de los ratingbars antes de entrar al submenu
        submsilabas.updateAllRbs();
        //---- FIN ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-----

    }//Fin metodo onCreate
    //----------------------------------------------------------------------------------------------
    //Cada que el usurio deja de ocupar esta activity y depues vuelve a ella en el momento en que
    //vuelve a la activity se ejecuta este metodo
    @Override
    public void onResume()
    {
        super.onResume();

        //------ ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-------
        //Obtener los scores del usuario actual DESDE LA BDS
        CURRENTUSER = new Usuario(IDCURRENTUSER,this);
        scoreslvlfive = CURRENTUSER.getScoreslvlfive();

        //Crear objeto de la clase SubMenu utilizando la view actual
        SubMenu submsilabas = new SubMenu(scoreslvlfive,idrbslec,findViewById(android.R.id.content));

        //Hacer actualizacion de los ratingbars antes de entrar al submenu
        submsilabas.updateAllRbs();
        //---- FIN ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-----

    }//Fin on resume
    //----------------------------------------------------------------------------------------------
    //Dado el boton que se preciona determinar que lectura debe ser mostrada en la pnatalla de la
    //actividad de lecturas
    public void selectLectura(View v)
    {
        //Inicar ejecución de Actividad de Lecturas animadas
        Intent in = new Intent(this,EjercicioLectura.class);
        //Mandar id usuario a actividad eLecturas animadas
        in.putExtra("IDUSER",IDCURRENTUSER);
        //Iniciar Actividad
        startActivity(in);
    }//Fin metodo selectLectura
    //----------------------------------------------------------------------------------------------
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

            //Inicar nueva actividad creada en line anterior/ ir a menu principal
            startActivity(in);

        }//Fin if 1

    }//Fin metodo HomeClick

}//Fin clase SubmenuLecturaAstro
