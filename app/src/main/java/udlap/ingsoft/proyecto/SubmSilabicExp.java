package udlap.ingsoft.proyecto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

/**
 * Submenu de nivel explorador que contiene todos los ejerecicios y el progreso de cada nivel
 * de los ejercicios silabicos
 * https://developer.android.com/guide/components/activities/activity-lifecycle.html
 */

public class SubmSilabicExp extends AppCompatActivity
{
    //Variables globales
    ImageButton home;
    //DECLARAR VARIABLE GLOBAL DEL ID DEL USUARIO ACTUAL
    int IDCURRENTUSER = -1;
    //DECLARACION VARIABLE GLOBAL DEL USUARIO
    Usuario CURRENTUSER;

    //Variable de posicion de ejercicio al que se desea entrar
    int NUMEX = 0;

    //ATRIBUTOS
    //----- Atributos de juego silabico ----------------------
    int NUMSILABICGAMES = 6;
    float[] scoreslvltrh;
    //Arreglo con id de ratingbars
    int[] idrbs = {R.id.rb1exp1,R.id.rb2exp1,R.id.rb3exp1,R.id.rb4exp1,R.id.rb5exp1,R.id.rb6exp1};
    //----- FIN ATRIBUTOS JUEGO SILABICO

    //------- Atributos sopa de letras ------------------

    //------ FIN atributos sopa de letras----------------

    //METODOS
    //----------- METODO ON CREATE -------------
    protected void onCreate(Bundle SavedInstanceState)
    {
        super.onCreate(SavedInstanceState);
        //Mostrar contenido de xml correspondiente
        setContentView(R.layout.submenu_silabic_explor);

        home = (ImageButton) findViewById(R.id.HomeButton);

        //------ ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-------
        //***Obtener el ID DEL USUARIO ACTUAL
        Intent in = getIntent();
        //0 valor de default que rescibiria la variable si no hay nada en el Intent
        IDCURRENTUSER = in.getIntExtra("IDUSER",0);

        //***Obtener los scores del usuario actual
        CURRENTUSER = new Usuario(IDCURRENTUSER,this);
        //Datos dat = new Datos(this);
        //Usuario dos = dat.SearchName("Pedro");

        //Obtener los escores del lvltwo=juego de silabas
        //Usuario usr = new Usuario(60,this);
        scoreslvltrh = CURRENTUSER.getScoreslvltree();

        //Crear objeto de la clase SubMenu utilizando la view actual
        SubMenu submsilabas = new SubMenu(scoreslvltrh,idrbs,findViewById(android.R.id.content));

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
        //Usuario dos = dat.SearchName("Pedro");
        scoreslvltrh = CURRENTUSER.getScoreslvltree();

        //Crear objeto de la clase SubMenu utilizando la view actual
        SubMenu submsilabas = new SubMenu(scoreslvltrh,idrbs,findViewById(android.R.id.content));

        //Hacer actualizacion de los ratingbars antes de entrar al submenu
        submsilabas.updateAllRbs();
        //---- FIN ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-----

    }//Fin on resume

    //-----------------------------------------------------
    //Si se presiona el boton determinar que boton ha sido y de esta manera determinar en
    //que ejercicio debe ser abierto al iniciar la siguiente pantalla
    public void SelectSilabicEx(View v)
    {
        //Determinar que boton se ha oprimido
        switch(v.getId())
        {
            case R.id.B1exp1:
                NUMEX = 0;
                break;

            case R.id.B2exp1:
                NUMEX = 1;
                break;

            case R.id.B3exp1:
                NUMEX = 2;
                break;

            case R.id.B4exp1:
                NUMEX = 3;
                break;

            case R.id.B5exp1:
                NUMEX = 4;
                break;

            case R.id.B6exp1:
                NUMEX = 5;
                break;

            default:
                break;

        }//Fin estrucutra swutch

        //--------------------------------------------------------
        //Log.d("eeeinx","Num ejer mandado desde submenu:"+NUMEX);
        //--------------------------------------------------------

        //Un intent es un objeto que permite un enlace en tiempo de ejecución entre 2 actividades
        //son generalemnte utilziadas para realizar varias tareas al mismo tiempo
        Intent in = new Intent(this, EjercicioSilabico.class);
        //MANDAR POSICION DE EJERCICIO SILABICO EN EL QUE SE DEBE ABRIR LA SIGUIENTE PANTALLA
        in.putExtra("INDXEX", NUMEX);
        //MANDAR ID DE USUARIO ACTUAL A ACTIVITY EjercicioSilabico
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

}//Fin clase SubmSilabicExp
