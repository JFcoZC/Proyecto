package udlap.ingsoft.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Submenu de nivel ASTRONAUTA que contiene todos los ejerecicios y el progreso de cada nivel
 * de los ejercicios ORTOGRAFICOS
 * https://developer.android.com/guide/components/activities/activity-lifecycle.html
 */

public class SubmenuOrtoAstro extends AppCompatActivity
{
    //Variables globales
    ImageButton home;
    //DECLARAR VARIABLE GLOBAL DEL ID DEL USUARIO ACTUAL
    int IDCURRENTUSER = -1;
    //DECLARACION VARIABLE GLOBAL DEL USUARIO
    Usuario CURRENTUSER;

    //ATRIBUTOS
    //----- Atributos de juego ORTOGRAFIA ----------------------
    float[] scoreslvlsix;
    //Arreglo con id de ratingbars
    int[] idrbsorto = {R.id.rb1astro1,R.id.rb2astro1,R.id.rb3astro1};
    //----- FIN ATRIBUTOS JUEGO ORTOGRAFICO

    //------- Atributos sopa de letras ------------------

    //------ FIN atributos sopa de letras----------------

    //METODOS
    //----------- METODO ON CREATE -------------
    protected void onCreate(Bundle SavedInstanceState)
    {
        super.onCreate(SavedInstanceState);
        //Mostrar contenido de xml correspondiente
        setContentView(R.layout.submenu_orto_astro);

        home = (ImageButton) findViewById(R.id.HomeButton);

        //------ ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-------
        //***Obtener el ID DEL USUARIO ACTUAL
        Intent in = getIntent();
        //0 valor de default que rescibiria la variable si no hay nada en el Intent
        IDCURRENTUSER = in.getIntExtra("IDUSER",0);

        //***Obtener los scores del usuario actual
        CURRENTUSER = new Usuario(IDCURRENTUSER,this);

        //Obtener los escores del lvlSIX=juego de ORTOGRAFIA
        scoreslvlsix = CURRENTUSER.getScoreslvlsix();

        //Crear objeto de la clase SubMenu utilizando la view actual
        SubMenu submsilabas = new SubMenu(scoreslvlsix,idrbsorto,findViewById(android.R.id.content));

        //Hacer actualizacion de los ratingbars antes de entrar al submenu
        submsilabas.updateAllRbs();
        //---- FIN ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-----


    }//Fin metodo onCreate

    //Cada que el usurio deja de ocupar esta activity y depues vuelve a ella en el momento en que
    //vuelve a la activity se ejecuta este metodo
    @Override
    public void onResume()
    {
        super.onResume();

        //------ ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-------
        //Obtener los scores del usuario actual DESDE LA BDS
        CURRENTUSER = new Usuario(IDCURRENTUSER,this);
        scoreslvlsix = CURRENTUSER.getScoreslvlsix();

        //Crear objeto de la clase SubMenu utilizando la view actual
        SubMenu submsilabas = new SubMenu(scoreslvlsix,idrbsorto,findViewById(android.R.id.content));

        //Hacer actualizacion de los ratingbars antes de entrar al submenu
        submsilabas.updateAllRbs();
        //---- FIN ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-----

    }//Fin on resume

    //-----------------------------------------------------
    //Si se presiona el boton determinar que boton ha sido y de esta manera determinar en
    //que ejercicio debe ser abierto al iniciar la siguiente pantalla
    public void SelectOrtografiaEx(View v)
    {
        //Un intent es un objeto que permite un enlace en tiempo de ejecución entre 2 actividades
        //son generalemnte utilziadas para realizar varias tareas al mismo tiempo
        Intent in = new Intent(this, EjercicioOrtografia.class);
        //MANDAR ID DE USUARIO ACTUAL A ACTIVITY EjercicioOrtografico
        in.putExtra("IDUSER", IDCURRENTUSER);

        startActivity(in);


    }//Fin metodo view v

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

}//Fin clase SubmenuOrtoAstro
