package udlap.ingsoft.proyecto;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.ViewFlipper;

/**
 * Created by Patty on 18/10/2017.
 */

//Inicio clase EjercicioOrtografia
public class EjercicioOrtografia extends AppCompatActivity implements View.OnClickListener
{
    //variables globales
    ImageButton next, prev;
    ImageButton home;
    ViewFlipper vflip;
    CheckBox uno,dos;

    //Tiempo de juego
    long INICIOTIME = 0;
    long FINTIME = 0;
    long FULLTIME;

    //DECLAR CARIABLE GLOBAL DE INTENT para obtener datos provenientes de actividades anteriroes
    Intent inten;

    //DECLARAR VARIABLE GLOBAL DE ID USAURIO ACTUAL
    int IDCURRENTUSER = -1;

    //DECLARAR VARIABLE GLOBAL DE USUARIO ACTUAL
    Usuario CURRENTUSER;

    //num ejercicios juego ortografia (checar esta variable tambien en clase Usuario)
    int NUMEXCER = 3;

    //contador para indicar el numero de ejercicio
    int excercise = 0;

    //!!!!IMPPORTANTE: DECLARACION DE ARREGLO DE ejercicios ortograficos
    OrtoEx[] ejercicios;

    //array para guardar resultados
    float[] scoreOrtoEx = new float[NUMEXCER];

    int[] posactualex = {0,1};

    //MediaPlayer para la reproduccion de sonidos
    MediaPlayer sound;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ejercicio_ortografia);

        //!!!!IMPPORTANTE: Incializar el numero de ejercicios Silabicos de dos silabas
        ejercicios = OrtoExInitializer(NUMEXCER);
        //Importante ya que se esta pasando el contexto a cada constructor de un ejercicio silabico
        //para que genere el objeto y debido a que el  contexto se crea hasta que se entra al OnCreate
        //aqui es donde se deben incializar los ejercicios pero el arreglo de ejercicios debe
        //ser declarado de forma global
        //-----------------------------------------

        //Objeto que permite pasar las fotos como slides
        vflip = (ViewFlipper) findViewById(R.id.ViewFlipper);

        //Botones
        next = (ImageButton) findViewById(R.id.siguiente);
        prev = (ImageButton) findViewById(R.id.previo);

        home = (ImageButton) findViewById(R.id.HomeButton);

        //Cuando una view suceda en estos botones ir a achecar a la logica del metodo onClick
        next.setOnClickListener(this);
        prev.setOnClickListener(this);

        //----OBTENER POSICION DE EJERCICIO QUE DEBE SER DESPLEGADO Y MOSTRARLO---------------------
        inten = getIntent();
        //El 0 es un valor de dfault por si no se encuentra el valor que se buscaba, se asigna 0 por
        //default
        excercise = inten.getIntExtra("INDXEX",0);
        //Mostrar la imagen correspondiente
        vflip.setDisplayedChild(excercise);
        //CAMBIAR POSICIONES DE PALABRAS DE CHECKBOXES Y GUARDAR POSICIONES ACTUALES
        posactualex = ChangeTextBoxes(ejercicios[excercise]);
        //-------------- FIN MOSTRAR EJERCICIO CORRESPONDIENTE EN VIEWFLIPPER-----------------------

        //******* IMPORTANTE NECESARIO INICAR AQUI PARA QUE CON CAMBIO DE PANTALLAS CON HOME NO SE
        //PIERDA EL IDUSER**
        //***Obtener Datos Actuales del usario desde la BDS
        Intent inten = getIntent();
        IDCURRENTUSER = inten.getIntExtra("IDUSER",0);

        INICIOTIME = System.currentTimeMillis();
    }//Fin metodo que llama al xml
    //----------------------------------------------------------------------------------------------
    //--------Metodo que se llama cada vez que la Actividad recibe una nueva actividad--------------
    //SI ESTE METODO NO ESTA DECLARADO, EL EJERCICIO CUANDO ES SELECCIONADO EN EL SUBMENU SOLO ES
    //CAMBIADO LA PRIMERA VEZ, PERO DESPUES YA NO PORQUE EL INTENT Y SUS DATOS NO SE ACTUALIZAN
    public void onNewIntent(Intent in)
    {
        //Actualizar los datos deL intent de la actividad anterior, por dados del neuvo intent
        super.onNewIntent(in);
        this.setIntent(in);
    }//Fin metodo onNewIntent
    //----------------------------------------------------------------------------------------------
    //Metodo que ejecuta las acciones cuando una vez que el usuario a dejado de usar una activity
    //pero la vuelve a abrir, entonces entra en ejecucion este metodo
    public void onResume()
    {
        super.onResume();

        //Incializar tiempo de uso de la aplicación
        INICIOTIME = System.currentTimeMillis();

        //----OBTENER POSICION DE EJERCICIO QUE DEBE SER DESPLEGADO Y MOSTRARLO---------------------
        inten = getIntent();
        //El 0 es un valor de dfault por si no se encuentra el valor que se buscaba, se asigna 0 por
        //default
        excercise = inten.getIntExtra("INDXEX",0);
        //Mostrar la imagen correspondiente
        vflip.setDisplayedChild(excercise);
        //CAMBIAR POSICIONES DE PALABRAS DE CHECKBOXES Y GUARDAR POSICIONES ACTUALES
        posactualex = ChangeTextBoxes(ejercicios[excercise]);
        //-------------- FIN MOSTRAR EJERCICIO CORRESPONDIENTE EN VIEWFLIPPER-----------------------

    }//Fin metodo onResume
    //----------------------------------------------------------------------------------------------
    //Metodo que se incia cuando se mueve a otra actividad
    public void onStop()
    {
        super.onStop();
        //***Obtener Datos Actuales del usario desde la BDS
        inten = getIntent();
        IDCURRENTUSER = inten.getIntExtra("IDUSER",0);

        //OBTENER DATOS DEL USUARIO  DE LA BDS Y SOBREESCRIBIRLOS SOLO SI HAY CNEXION
        if(IDCURRENTUSER != -1)
        {
            CURRENTUSER = new Usuario(IDCURRENTUSER, this);

            //***Actualizar Datos de Usuario con puntuacion Actual
            //arregloDePuntos/Número de ejercicio 5(6) y Numero de ejercicicos creados
            CURRENTUSER.LevelProgress(scoreOrtoEx, 5, NUMEXCER);
            //ACTUALIZAR EL RESTO DE LOS CALCULOS ESTADISTICOS
            FINTIME = System.currentTimeMillis();
            FULLTIME = FINTIME - INICIOTIME;
            CURRENTUSER.calcularUserData(FULLTIME);
            //RESETEAR RELOJES
            FULLTIME = 0;
            INICIOTIME = 0;
            FINTIME = 0;

            //***Realizar sobre escritura informacion de usuario en BDs
            CURRENTUSER.updtadeDataDB(this);

        }//Fin if 1

    }//Fin metodo onStop
    //----------------------------------------------------------------------------------------------
    //Metodo que se incia cuando se destruye completamente la actividad
    public void onDestroy()
    {
        super.onDestroy();

        //***Obtener Datos Actuales del usario desde la BDS
        inten = getIntent();
        IDCURRENTUSER = inten.getIntExtra("IDUSER",0);

        //OBTENER DATOS ACTUALES DE BDS Y SOBREESCRIBILOS SOLO SI HAY CONEXION
        if(IDCURRENTUSER != -1)
        {
            CURRENTUSER = new Usuario(IDCURRENTUSER, this);

            //***Actualizar Datos de Usuario con puntuacion Actual
            //arregloDePuntos/Número de ejercicio 5(6) y Numero de ejercicicos creados
            CURRENTUSER.LevelProgress(scoreOrtoEx, 5, NUMEXCER);
            //ACTUALIZAR EL RESTO DE LOS CALCULOS ESTADISTICOS
            FINTIME = System.currentTimeMillis();
            FULLTIME = FINTIME - INICIOTIME;
            CURRENTUSER.calcularUserData(FULLTIME);
            //RESETEAR RELOJES
            FULLTIME = 0;
            INICIOTIME = 0;
            FINTIME = 0;

            //***Realizar sobreedcritura informacion de usuario en BDs
            CURRENTUSER.updtadeDataDB(this);

        }//FIN IF 1

    }//Fin metodo onDestroy
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
            //1) se puede usar this porque la clase Activity (que hereda la MainActivity) extiende de Context
            //2)La clase que se le debe asignar al intent
            Intent in = new Intent(this, MenuPrincipal.class);

            //Mandar id a actividad de menu principal
            in.putExtra("IDUSER",IDCURRENTUSER);

            //Inicar nueva actividad creada en line anterior/ ir a menu principal
            startActivity(in);

        }//Fin if 1

    }//Fin metodo HomeClick
    //----------------------------------------------------------------------------------------------
    //Acciones a realizar cuando se oprima el boton next o previous, no cambiarle el nombre del metodo
    //porque si no suceden problemas
    public void onClick(View v)
    {
        //Se presiona boton next
        if(v == next)
        {
            vflip.showNext();

            //Siguiente ejercicio
            excercise = excercise+1;

            //Si el numero del ejercicio, después del incremento es igual o mayor del length
            if(excercise >= ejercicios.length )
            {
                //Regresarse al primero
                excercise = 0;
            }//Fin if 2

            //Cambiar silabas de checkboxes y guardar posiciones actuales
            posactualex = ChangeTextBoxes(ejercicios[excercise]);

            //Resetear estados de seleccion de los checkboxes de las silabas de ejercicio actual
            //Restear botones y Barra de estrellas
            Reset(ejercicios[excercise]);

        }//Fin if 1
        else
        {
            //Se presiona boton previous
            if(v == prev)
            {
                vflip.showPrevious();

                //Ejercicio previo
                excercise = excercise-1;

                //Si el numero del ejerciico, después del decremento es menor que cero
                if(excercise < 0)
                {
                    //Regresar a la  última  posicion valida del arreglo de ejercicios
                    excercise = ejercicios.length-1;

                }//Fin if 4

                //Cambiar silabas de checkboxes y guardar posiciones actuales
                posactualex = ChangeTextBoxes(ejercicios[excercise]);

                //Resetear estados de seleccion de los checkboxes de las silabas de ejercicio actual
                //Restear botones y Barra de estrellas
                Reset(ejercicios[excercise]);

            }//Fin if 3

        }//Fin else 1

    }//Fin metodo onClick
    //----------------------------------------------------------------------------------------------
    //Hacer algo cuando el checkbox este precionado
    public void checkBoxclick(View v)
    {
        boolean selected = ( (CheckBox) v).isChecked();

        //Objetos para reproducción audio
        sound = ejercicios[excercise].getPalabraValue();

        //Asignar a objeto clase RaitingBar aquella raitingbar que tenga como valor de id:
        //'ratingBar'
        RatingBar bar = ( (RatingBar) findViewById(R.id.ratingBar) );

        //Checar que checkbox esta seleccionada con base en su Id
        switch( v.getId())
        {
            //Checkbox 1
            case R.id.burro:
                if(selected)
                {
                    ejercicios[excercise].OpcionSelected(posactualex[0]);

                    //Asignar y reproducir audio
                    sound = ejercicios[excercise].getResourceNum(posactualex[0]);
                    sound.start();

                }
                else
                {
                    ejercicios[excercise].OpcionDeselected(posactualex[0]);

                }
                break;

            //Checkbox 2
            case R.id.vurro:
                if(selected)
                {

                    ejercicios[excercise].OpcionSelected(posactualex[1]);

                    //Asignar y reproducir sonido
                    sound = ejercicios[excercise].getResourceNum(posactualex[1]);
                    sound.start();

                }
                else
                {
                    ejercicios[excercise].OpcionDeselected(posactualex[1]);

                }
                break;

            default:
                break;


        }


        //LOGICA DE RESPUESTA CORRECTA

        if(ejercicios[excercise].StateFirstRight() == 1 && ejercicios[excercise].StateFirstWrong() == 0 )
        {

            bar.setRating(Float.parseFloat("3.0"));

            scoreOrtoEx[excercise] = 3.0f;

        }else
        {

            //Si es incorrecto borrar todoo
            bar.setRating(Float.parseFloat("0.0"));

            scoreOrtoEx[excercise] = 0.0f;

        }

    }//Fin metodo checkBox1click
    //----------------------------------------------------------------------------------------------
    //Metodo que incializa los Ejercicios
    OrtoEx[] OrtoExInitializer(int numExcercises)
    {

        //Designación del numero de ejercicios que van a existir en el arreglo
        OrtoEx[] ortoexcercises = new OrtoEx[numExcercises];


        int[] recursos = {R.raw.good,R.raw.bad};
        ortoexcercises[0] = new OrtoEx("burro","vurro", R.raw.burro, recursos,0,EjercicioOrtografia.this);

        int[] recursos1 = {R.raw.good,R.raw.bad};
        ortoexcercises[1] = new OrtoEx("vaca","baca", R.raw.vaca, recursos1,1,EjercicioOrtografia.this);

        int[] recursos2 = {R.raw.good,R.raw.bad};
        ortoexcercises[2] = new OrtoEx("jirafa","girafa", R.raw.jirafa, recursos2,2,EjercicioOrtografia.this);

        return ortoexcercises;

    }
    //----------------------------------------------------------------------------------------------
    //Metodo que cambia el texto de las checkBoxes
    int[] ChangeTextBoxes(OrtoEx ejercicio)
    {
        int[] opciones = ejercicio.RandomOpciones();

        uno = (CheckBox) findViewById(R.id.burro);
        uno.setText( ejercicio.getThisOpcion(opciones[0]) );

        dos = (CheckBox) findViewById(R.id.vurro);
        dos.setText( ejercicio.getThisOpcion(opciones[1]) );

        return opciones;

    }
    //----------------------------------------------------------------------------------------------

    public void imagenClick(View vi)
    {
        boolean selected = vflip.isClickable();

        sound = ejercicios[excercise].getPalabraValue();

        if(selected){
            sound = ejercicios[excercise].getPalabraValue();
            sound.start();
        }
    }

    //Metodo que resetea valores de cehckbox a unselected y regresa rating bar a valroes originales
    public void Reset(OrtoEx ejercicio)
    {
        //Asociar a  objeto checkbox cada checkbox y poner valores como unselected
        CheckBox a = (CheckBox) findViewById(R.id.burro);
        a.setChecked(false);

        CheckBox b = (CheckBox) findViewById(R.id.vurro);
        b.setChecked(false);

        RatingBar rb = (RatingBar) findViewById(R.id.ratingBar);
        rb.setRating(Float.parseFloat("0.0"));

        //Resetear estados del ejercicio
        ejercicio.ResetEstados();

    }
    //----------------------------------------------------------------------------------------------
    //Metodo que resetea el score
    float[] ResetScores(float scores[])
    {
        for(int i = 0; i < scores.length; i++)
        {
            scores[i] = 0.0f;
        }//Fin for 1

        return scores;

    }
    //----------------------------------------------------------------------------------------------


}//Fin clase Ejercicio Ortografia
