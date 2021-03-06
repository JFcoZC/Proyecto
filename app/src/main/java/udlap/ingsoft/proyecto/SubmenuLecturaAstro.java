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

    //VARIABLE DE LECTURA A LA QUE SE DESEA ENTRAR
    int NUMEX = 0;

    //Atributos de LECTURAS ANIMADAS
    float[] scoreslvlfive;
    //Arreglo con id de rating bars de Lecturas; debe coincidir con el NUEXERCISE definifo en la
    //clase USUARIO
    int[] idrbslec = {R.id.rb1astroL,R.id.rb2astroL,R.id.rb3astroL,R.id.rb4astroL,R.id.rb5astroL,
            R.id.rb6astroL,R.id.rb7astroL,R.id.rb8astroL,R.id.rb9astroL,R.id.rb10astroL,R.id.rb11astroL
            ,R.id.rb12astroL,R.id.rb13astroL,R.id.rb14astroL,R.id.rb15astroL,R.id.rb16astroL,R.id.rb17astroL
            ,R.id.rb18astroL,R.id.rb19astroL,R.id.rb20astroL,R.id.rb21astroL,R.id.rb22astroL,R.id.rb23astroL
            ,R.id.rb24astroL,R.id.rb25astroL,R.id.rb26astroL,R.id.rb27astroL,R.id.rb28astroL};

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

        //OBTENER SOLO LOS SCORES DEL USUARIO PAR ACTUALIZAR RBS SOLO SI HAY CONEXION A  LA BDs
        if(IDCURRENTUSER != -1)
        {
            //***Obtener los scores del usuario actual
            CURRENTUSER = new Usuario(IDCURRENTUSER, this);

            //Obtener los escores del lvlSIX=juego de ORTOGRAFIA
            scoreslvlfive = CURRENTUSER.getScoreslvlfive();

            //Crear objeto de la clase SubMenu utilizando la view actual
            SubMenu submsilabas = new SubMenu(scoreslvlfive, idrbslec, findViewById(android.R.id.content));

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

        //ACTUALIZAR RBS SOLO SI HAY CONEXION A LA BDS
        if(IDCURRENTUSER != -1)
        {
            //------ ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-------
            //Obtener los scores del usuario actual DESDE LA BDS
            CURRENTUSER = new Usuario(IDCURRENTUSER, this);
            scoreslvlfive = CURRENTUSER.getScoreslvlfive();

            //Crear objeto de la clase SubMenu utilizando la view actual
            SubMenu submsilabas = new SubMenu(scoreslvlfive, idrbslec, findViewById(android.R.id.content));

            //Hacer actualizacion de los ratingbars antes de entrar al submenu
            submsilabas.updateAllRbs();
            //---- FIN ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-----

        } //FIN IF 1

    }//Fin on resume
    //----------------------------------------------------------------------------------------------
    //Dado el boton que se preciona determinar que lectura debe ser mostrada en la pnatalla de la
    //actividad de lecturas
    public void selectLectura(View v)
    {
        //Determinar que boton se a oprimido
        switch(v.getId())
        {
            case R.id.B1astroL:
                NUMEX = 0;
                break;

            case R.id.B2astroL:
                NUMEX = 1;
                break;

            case R.id.B3astroL:
                NUMEX = 2;
                break;
            case R.id.B4astroL:
                NUMEX = 3;
                break;
            case R.id.B5astroL:
                NUMEX = 4;
                break;
            case R.id.B6astroL:
                NUMEX = 5;
                break;
            case R.id.B7astroL:
                NUMEX = 6;
                break;
            case R.id.B8astroL:
                NUMEX = 7;
                break;
            case R.id.B9astroL:
                NUMEX = 8;
                break;
            case R.id.B10astroL:
                NUMEX = 9;
                break;
            case R.id.B11astroL:
                NUMEX = 10;
                break;
            case R.id.B12astroL:
                NUMEX = 11;
                break;
            case R.id.B13astroL:
                NUMEX = 12;
                break;
            case R.id.B14astroL:
                NUMEX = 13;
                break;
            case R.id.B15astroL:
                NUMEX = 14;
                break;
            case R.id.B16astroL:
                NUMEX = 15;
                break;
            case R.id.B17astroL:
                NUMEX = 16;
                break;
            case R.id.B18astroL:
                NUMEX = 17;
                break;
            case R.id.B19astroL:
                NUMEX = 18;
                break;
            case R.id.B20astroL:
                NUMEX = 19;
                break;
            case R.id.B21astroL:
                NUMEX = 20;
                break;
            case R.id.B22astroL:
                NUMEX = 21;
                break;
            case R.id.B23astroL:
                NUMEX = 22;
                break;
            case R.id.B24astroL:
                NUMEX = 23;
                break;
            case R.id.B25astroL:
                NUMEX = 24;
                break;
            case R.id.B26astroL:
                NUMEX = 25;
                break;
            case R.id.B27astroL:
                NUMEX = 26;
                break;
            case R.id.B28astroL:
                NUMEX = 27;
                break;
            default:
                break;
        }//Fin estructura switch

        //Inicar ejecución de Actividad de Lecturas animadas
        Intent in = new Intent(this,EjercicioLectura.class);
        //MANDAR POSICION DE LECTURA SELECCIONADA POR USUARIO
        in.putExtra("INDXEX",NUMEX);
        //Mandar id usuario a actividad eLecturas animadas
        in.putExtra("IDUSER",IDCURRENTUSER);
        //Iniciar Actividad
        startActivity(in);
        finish();
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

            //Mandar id a actividad de menu principal
            in.putExtra("IDUSER",IDCURRENTUSER);

            //Inicar nueva actividad creada en line anterior/ ir a menu principal
            startActivity(in);
            finish();

        }//Fin if 1

    }//Fin metodo HomeClick

}//Fin clase SubmenuLecturaAstro
