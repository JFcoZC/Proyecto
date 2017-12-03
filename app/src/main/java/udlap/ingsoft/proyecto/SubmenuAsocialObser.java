package udlap.ingsoft.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Clase que maneja la lógica del ejercicio de tipo Asociacion de letras que corresponde al seguno
 * tipo de ejrcicios del nive Observadores
 */

public class SubmenuAsocialObser extends AppCompatActivity
{
    //Atributos

    //Declarar variable del ID del usuario actual
    int IDCURRENTUSER = -1;
    //Declaracion de variable global de USUQRIO
    Usuario CURRENTUSER;

    //VARIABLE DEL EJERCICIO AL QUE SE DESEA ENTRAR
    int NUMEX = 0;


    //Atributos jeugo orttografia
    float[] scoreslvltwo;
    //Arreglo con rating bars Asociacion de letras
    int[] idrbsasol = {R.id.rb1astro2,R.id.rb2astro2,R.id.rb3astro2,R.id.rb4astro2,R.id.rb5astro2,
                       R.id.rb6astro2,R.id.rb7astro2,R.id.rb8astro2,R.id.rb9astro2,R.id.rb10astro2,
                       R.id.rb11astro2,R.id.rb12astro2,R.id.rb13astro2,R.id.rb14astro2,R.id.rb15astro2,
                       R.id.rb16astro2,R.id.rb17astro2,R.id.rb18astro2,R.id.rb19astro2,R.id.rb20astro2,
                       R.id.rb21astro2,R.id.rb22astro2,R.id.rb23astro2,R.id.rb24astro2,R.id.rb25astro2,
                       R.id.rb26astro2,R.id.rb27astro2};

    //Metodos
    public void onCreate(Bundle SavedInstanceState)
    {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.submenu_asocial_obser);

        //------ ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-------
        //***Obtener el ID DEL USUARIO ACTUAL
        Intent in = getIntent();
        //0 valor de default que rescibiria la variable si no hay nada en el Intent
        IDCURRENTUSER = in.getIntExtra("IDUSER",0);

        //Solo hacer actualizacion de RBS si hay conexion a BDS
        if(IDCURRENTUSER != -1)
        {
            //***Obtener los scores del usuario actual
            CURRENTUSER = new Usuario(IDCURRENTUSER, this);

            //Obtener los escores del lvltwojuego de Asociacion de letras
            scoreslvltwo = CURRENTUSER.getScoreslvltwo();

            //Crear objeto de la clase SubMenu utilizando la view actual
            SubMenu submsilabas = new SubMenu(scoreslvltwo, idrbsasol, findViewById(android.R.id.content));

            //Hacer actualizacion de los ratingbars antes de entrar al submenu
            submsilabas.updateAllRbs();
            //---- FIN ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-----

        }//FIN IF 1

    }//Fin metodo on Create
    //----------------------------------------------------------------------------------------------
    //Cada que el usurio deja de ocupar esta activity y depues vuelve a ella en el momento en que
    //vuelve a la activity se ejecuta este metodo
    @Override
    public void onResume()
    {
        super.onResume();

        //Solo hacer la actualizacion de la Rating bars si hay conexion a BDs
        if(IDCURRENTUSER != -1)
        {
            //---------ACTUALIZAR RB DE EJERCICIOS SUBMENU -----------
            //Obtener los Scores del usuario actual desde la BDs
            CURRENTUSER = new Usuario(IDCURRENTUSER, this);
            scoreslvltwo = CURRENTUSER.getScoreslvltwo();

            //Crear objeto de la clase SubMenu utilizando la view actual
            SubMenu submsilabas = new SubMenu(scoreslvltwo, idrbsasol, findViewById(android.R.id.content));

            //Hacer actualizacion de los ratingbars antes de entrar al submenu
            submsilabas.updateAllRbs();

        }//FIN IF 1

    }//Fin metodo onResume
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

        //Mandar id a actividad de menu principal
        in.putExtra("IDUSER",IDCURRENTUSER);

        //Inicar nueva actividad creada en line anterior/ ir a menu principal
        startActivity(in);
        finish();

    }//Fin metodo HomeClick
    //----------------------------------------------------------------------------------------------
    //Si se presiona el boton determinar que boton ha sido y de esta manera determinar en
    //que EJERCICIO debe ser abierto al iniciar la siguiente pantalla
    public void SelectNivel(View v)
    {
        //Determinar el boton que se ha oprimido
        switch(v.getId())
        {
            case R.id.B1astro2:
                NUMEX = 0;
                break;

            case R.id.B2astro2:
                NUMEX = 1;
                break;

            case R.id.B3astro2:
                NUMEX = 2;
                break;

            case R.id.B4astro2:
                NUMEX  = 3;
                break;

            case R.id.B5astro2:
                NUMEX = 4;
                break;

            case R.id.B6astro2:
                NUMEX = 5;
                break;

            case R.id.B7astro2:
                NUMEX = 6;
                break;

            case R.id.B8astro2:
                NUMEX = 7;
                break;

            case R.id.B9astro2:
                NUMEX = 8;
                break;

            case R.id.B10astro2:
                NUMEX = 9;
                break;

            case R.id.B11astro2:
                NUMEX = 10;
                break;

            case R.id.B12astro2:
                NUMEX = 11;
                break;

            case R.id.B13astro2:
                NUMEX = 12;
                break;

            case R.id.B14astro2:
                NUMEX = 13;
                break;

            case R.id.B15astro2:
                NUMEX = 14;
                break;

            case R.id.B16astro2:
                NUMEX = 15;
                break;

            case R.id.B17astro2:
                NUMEX = 16;
                break;

            case R.id.B18astro2:
                NUMEX = 17;
                break;

            case R.id.B19astro2:
                NUMEX = 18;
                break;

            case R.id.B20astro2:
                NUMEX = 19;
                break;

            case R.id.B21astro2:
                NUMEX = 20;
                break;

            case R.id.B22astro2:
                NUMEX = 21;
                break;

            case R.id.B23astro2:
                NUMEX = 22;
                break;

            case R.id.B24astro2:
                NUMEX = 23;
                break;

            case R.id.B25astro2:
                NUMEX = 24;
                break;

            case R.id.B26astro2:
                NUMEX = 25;
                break;

            case R.id.B27astro2:
                NUMEX = 26;
                break;

        }//Fin switch 1

        //Iniciar actividad en pantalla de Ejerciio DE ASOCIAR LETRAS
        Intent in = new Intent(this, EjercicioAsociacionletr.class);
        //MANDAR POSICION DE EJERCICIO SILABICO EN EL QUE SE DEBE ABRIR LA SIGUIENTE PANTALLA
        in.putExtra("INDXEX", NUMEX);
        //MANDAR ID DE USUARIO ACTUAL A ACTIVITY EjercicioAsociacionletr
        in.putExtra("IDUSER", IDCURRENTUSER);

        startActivity(in);
        finish();

    }//Fin metodo select

}//Fin clase SubmenuAsocialObser
