package udlap.ingsoft.proyecto;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Patty on 30/11/2017.
 */

public class SubmSopaExp extends AppCompatActivity
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
    //----- Atributos de juego sopa letras ----------------------
    float[] scoreslvlfour;
    //Arreglo con id de ratingbars
    int[] idrbs = {R.id.rb1exp1,R.id.rb2exp1,R.id.rb3exp1,R.id.rb4exp1,R.id.rb5exp1,R.id.rb6exp1,
                   R.id.rb7exp1,R.id.rb8exp1,R.id.rb9exp1,R.id.rb10exp1,R.id.rb11exp1,R.id.rb12exp1,
                   R.id.rb13exp1,R.id.rb14exp1,R.id.rb15exp1,R.id.rb16exp1,R.id.rb17exp1,R.id.rb18exp1,
                   R.id.rb19exp1,R.id.rb20exp1};

    //METODOS
    //----------- METODO ON CREATE -------------
    protected void onCreate(Bundle SavedInstanceState)
    {
        super.onCreate(SavedInstanceState);
        //Mostrar contenido de xml correspondiente
        setContentView(R.layout.submenu_sopa_explor);

        home = (ImageButton) findViewById(R.id.HomeButton);

        //------ ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-------
        //***Obtener el ID DEL USUARIO ACTUAL
        Intent in = getIntent();
        //0 valor de default que rescibiria la variable si no hay nada en el Intent
        IDCURRENTUSER = in.getIntExtra("IDUSER",0);

        //ACTUALIZAR RBS DE SUBMENU SOLO SI HAY CONEXION A LA BDS
        if(IDCURRENTUSER != -1)
        {
            //***Obtener los scores del usuario actual
            CURRENTUSER = new Usuario(IDCURRENTUSER, this);
            //Datos dat = new Datos(this);
            //Usuario dos = dat.SearchName("Pedro");

            //Obtener los escores del lvltwo=juego de silabas
            //Usuario usr = new Usuario(60,this);
            scoreslvlfour = CURRENTUSER.getScoreslvlfour();

            //Crear objeto de la clase SubMenu utilizando la view actual
            SubMenu submsilabas = new SubMenu(scoreslvlfour, idrbs, findViewById(android.R.id.content));

            //Hacer actualizacion de los ratingbars antes de entrar al submenu
            submsilabas.updateAllRbs();
            //---- FIN ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-----
        }//Fin if 1

    }//Fin metodo onCreate
    //----------------------------------------------------------------------------------------------
    //Cada que el usurio deja de ocupar esta activity y depues vuelve a ella en el momento en que
    //vuelve a la activity se ejecuta este metodo
    @Override
    public void onResume()
    {
        super.onResume();

        //ACTUALIZAR RBS SOLAMENTE SI HAY CONEXION A LA BDS
        if(IDCURRENTUSER != -1)
        {
            //------ ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-------
            //Obtener los scores del usuario actual DESDE LA BDS
            CURRENTUSER = new Usuario(IDCURRENTUSER, this);
            //Usuario dos = dat.SearchName("Pedro");
            scoreslvlfour = CURRENTUSER.getScoreslvlfour();

            //Crear objeto de la clase SubMenu utilizando la view actual
            SubMenu submsilabas = new SubMenu(scoreslvlfour, idrbs, findViewById(android.R.id.content));

            //Hacer actualizacion de los ratingbars antes de entrar al submenu
            submsilabas.updateAllRbs();
            //---- FIN ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-----
        }//Fin  if 1

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

            case R.id.B7exp1:
                NUMEX = 6;
                break;

            case R.id.B8exp1:
                NUMEX = 7;
                break;

            case R.id.B9exp1:
                NUMEX = 8;
                break;

            case R.id.B10exp1:
                NUMEX = 9;
                break;

            case R.id.B11exp1:
                NUMEX = 10;
                break;

            case R.id.B12exp1:
                NUMEX = 11;
                break;

            case R.id.B13exp1:
                NUMEX = 12;
                break;

            case R.id.B14exp1:
                NUMEX = 13;
                break;

            case R.id.B15exp1:
                NUMEX = 14;
                break;

            case R.id.B16exp1:
                NUMEX = 15;
                break;

            case R.id.B17exp1:
                NUMEX = 16;
                break;

            case R.id.B18exp1:
                NUMEX = 17;
                break;

            case R.id.B19exp1:
                NUMEX = 18;
                break;

            case R.id.B20exp1:
                NUMEX = 19;
                break;

            case R.id.B21exp1:
                NUMEX = 20;
                break;

            case R.id.B22exp1:
                NUMEX = 21;
                break;

            case R.id.B23exp1:
                NUMEX = 22;
                break;

            case R.id.B24exp1:
                NUMEX = 23;
                break;

            case R.id.B25exp1:
                NUMEX = 24;
                break;

            case R.id.B26exp1:
                NUMEX = 25;
                break;

            case R.id.B27exp1:
                NUMEX = 26;
                break;

            case R.id.B28exp1:
                NUMEX = 27;
                break;

            default:
                break;

        }//Fin estrucutra swutch

        //--------------------------------------------------------
        //Log.d("eeeinx","Num ejer mandado desde submenu:"+NUMEX);
        //--------------------------------------------------------

        //Un intent es un objeto que permite un enlace en tiempo de ejecución entre 2 actividades
        //son generalemnte utilziadas para realizar varias tareas al mismo tiempo
        Intent in = new Intent(this, EjercicioSopa.class);
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

            //Mandar id a actividad de menu principal
            in.putExtra("IDUSER",IDCURRENTUSER);

            //Inicar nueva actividad creada en line anterior/ ir a menu principal
            startActivity(in);

        }//Fin if 1

    }//Fin metodo HomeClick

}//Fin clase SubmSopaExp
