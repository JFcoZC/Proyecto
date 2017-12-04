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

    //VARIABLE DEL EJERCICIO AL QUE SE DESEA ENTRAR
    int NUMEX = 0;

    //ATRIBUTOS
    //----- Atributos de juego ORTOGRAFIA ----------------------
    float[] scoreslvlsix;
    //Arreglo con id de ratingbars
    int[] idrbsorto = {R.id.rb1astro1,R.id.rb2astro1,R.id.rb3astro1,R.id.rb4astro1,R.id.rb5astro1,
                        R.id.rb6astro1,R.id.rb7astro1,R.id.rb8astro1,R.id.rb9astro1,R.id.rb10astro1,
                        R.id.rb11astro1,R.id.rb12astro1,R.id.rb13astro1,R.id.rb14astro1,R.id.rb15astro1,
                        R.id.rb16astro1,R.id.rb17astro1,R.id.rb18astro1,R.id.rb19astro1,R.id.rb20astro1,
                        R.id.rb21astro1,R.id.rb22astro1,R.id.rb23astro1,R.id.rb24astro1,R.id.rb25astro1,
                        R.id.rb26astro1,R.id.rb27astro1,R.id.rb28astro1};
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

        //OBTENER LOS SCORES PAR ACTUALIZAR RATINGBR SOLO SI HAY CONEXION A LA BDS
        if(IDCURRENTUSER != -1)
        {
            //***Obtener los scores del usuario actual
            CURRENTUSER = new Usuario(IDCURRENTUSER, this);

            //Obtener los escores del lvlSIX=juego de ORTOGRAFIA
            scoreslvlsix = CURRENTUSER.getScoreslvlsix();

            //Crear objeto de la clase SubMenu utilizando la view actual
            SubMenu submsilabas = new SubMenu(scoreslvlsix, idrbsorto, findViewById(android.R.id.content));

            //Hacer actualizacion de los ratingbars antes de entrar al submenu
            submsilabas.updateAllRbs();
            //---- FIN ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-----
        }//FIN IF 1

    }//Fin metodo onCreate
    //----------------------------------------------------------------------------------------------
    //Cada que el usurio deja de ocupar esta activity y depues vuelve a ella en el momento en que
    //vuelve a la activity se ejecuta este metodo
    @Override
    public void onResume()
    {
        super.onResume();

        //ACTUALIZAR RATING BARS SOLO SI HAY CONEXION A LA BDS
        if(IDCURRENTUSER != -1)
        {
            //------ ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-------
            //Obtener los scores del usuario actual DESDE LA BDS
            CURRENTUSER = new Usuario(IDCURRENTUSER, this);
            scoreslvlsix = CURRENTUSER.getScoreslvlsix();

            //Crear objeto de la clase SubMenu utilizando la view actual
            SubMenu submsilabas = new SubMenu(scoreslvlsix, idrbsorto, findViewById(android.R.id.content));

            //Hacer actualizacion de los ratingbars antes de entrar al submenu
            submsilabas.updateAllRbs();
            //---- FIN ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-----

        }//FIN IF 1

    }//Fin on resume

    //-----------------------------------------------------
    //Si se presiona el boton determinar que boton ha sido y de esta manera determinar en
    //que ejercicio debe ser abierto al iniciar la siguiente pantalla
    public void SelectOrtografiaEx(View v)
    {
        //Determinar que boton se ha oprimido
        switch(v.getId())
        {
            case R.id.B1astro1:
                NUMEX = 0;
                break;

            case R.id.B2astro1:
                NUMEX = 1;
                break;

            case R.id.B3astro1:
                NUMEX = 2;
                break;

            case R.id.B4astro1:
                NUMEX = 3;
                break;

            case R.id.B5astro1:
                NUMEX = 4;
                break;

            case R.id.B6astro1:
                NUMEX = 5;
                break;

            case R.id.B7astro1:
                NUMEX = 6;
                break;

            case R.id.B8astro1:
                NUMEX = 7;
                break;

            case R.id.B9astro1:
                NUMEX = 8;
                break;

            case R.id.B10astro1:
                NUMEX = 9;
                break;

            case R.id.B11astro1:
                NUMEX = 10;
                break;

            case R.id.B12astro1:
                NUMEX = 11;
                break;

            case R.id.B13astro1:
                NUMEX = 12;
                break;

            case R.id.B14astro1:
                NUMEX = 13;
                break;

            case R.id.B15astro1:
                NUMEX = 14;
                break;

            case R.id.B16astro1:
                NUMEX = 15;
                break;

            case R.id.B17astro1:
                NUMEX = 16;
                break;

            case R.id.B18astro1:
                NUMEX = 17;
                break;

            case R.id.B19astro1:
                NUMEX = 18;
                break;

            case R.id.B20astro1:
                NUMEX = 19;
                break;

            case R.id.B21astro1:
                NUMEX = 20;
                break;

            case R.id.B22astro1:
                NUMEX = 21;
                break;

            case R.id.B23astro1:
                NUMEX = 22;
                break;

            case R.id.B24astro1:
                NUMEX = 23;
                break;

            case R.id.B25astro1:
                NUMEX = 24;
                break;

            case R.id.B26astro1:
                NUMEX = 25;
                break;

            case R.id.B27astro1:
                NUMEX = 26;
                break;

            case R.id.B28astro1:
                NUMEX = 27;
                break;

            default:
                break;

        }//Fin estructura switch

        //Un intent es un objeto que permite un enlace en tiempo de ejecución entre 2 actividades
        //son generalemnte utilziadas para realizar varias tareas al mismo tiempo
        Intent in = new Intent(this, EjercicioOrtografia.class);
        //MANDAR POSICION DE EJERCICIO SELECCIONADO POR USUARIO
        in.putExtra("INDXEX",NUMEX);
        //MANDAR ID DE USUARIO ACTUAL A ACTIVITY EjercicioOrtografico
        in.putExtra("IDUSER", IDCURRENTUSER);

        startActivity(in);
        finish();


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

            //Mandar id a actividad de menu principal
            in.putExtra("IDUSER",IDCURRENTUSER);

            //Inicar nueva actividad creada en line anterior/ ir a menu principal
            startActivity(in);
            finish();

        }//Fin if 1

    }//Fin metodo HomeClick

}//Fin clase SubmenuOrtoAstro
