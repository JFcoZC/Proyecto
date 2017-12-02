//Programa que contiene la logica y funcionamiento de los ejercicios solabicos que son ejercicios
//de la clase SilabicEx

//Librerias
package udlap.ingsoft.proyecto;
//package android.util;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//Libreria para objetos clase log (para los mensajes de logcat del debug)
import android.util.Log;
//Libreria para objetos clase View
import android.view.View;
//Libreria para objetos clase CheckBox
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RatingBar;
//Libreria para reproducción mp3
import android.media.MediaPlayer;
//Libreria viewFlipper
import android.widget.ViewFlipper;
import android.widget.Button;
//Libreria para inciar nuevas acticidades
import android.content.Intent;

//Inicio del programa

//https://stackoverflow.com/questions/5869871/how-to-catch-a-click-event-on-a-button
//FUENTE UTILIZADA PARA ARREGLAR PROBLEMA QUE NO PERMITE CONVERTIR ID DE RECURSO RAW A MediaPlayer:
//https://stackoverflow.com/questions/11760069/error-unable-to-instantiate-activity


//Inicio Main Class
public class EjercicioSilabico extends AppCompatActivity implements View.OnClickListener
{
    //Declaracion de variables (globales)
    ImageButton next, prev;
    ImageButton home;
    ViewFlipper vflip;
    CheckBox uno,dos,tres,cuatro;
    //MediaPlayer para la reproduccion de sonidos
    MediaPlayer sound;
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

    //DETERMINA EL NUMERO DE EJERCICIOS SILABICOS DE 2 SILABAS (checar esta variable tambien en clase Usuario)
    int NUMEXCER = 28;

    //!!!!IMPPORTANTE: DECLARACION DE ARREGLO DE ejercicios Silabicos de dos silabas
    SilabicEx[] ejercicios;

    //Variable para determinar si la actividad ya ha sido inciada
    //boolean actexpl = false;

    //Indica la posicion del ejercicio silabico dentro del arreglo de ejercicios
    int excercise = 0;

    //Definir del mismo tamaño un arreglo para mantener la puntuacion de cada ejercicio
    float[] scoreSilEx = new float[NUMEXCER];

    //Arreglo de 4 variables para guardar las posiciones que fueron creadas automaticamente
    //del ejercicio actual
    int[] posactualex = {0,1,2,3};

    //Datos de entrada

    //Procesos

    //Llamar al archivo ejercicio_silabicoabico.xml
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ejercicio_silabico);

        //Incializacion botones es importante si no tira null exception
        //Objeto que permite pasar las fotos como slides
        vflip = (ViewFlipper) findViewById(R.id.ViewFlipper);

        //Botones
        next = (ImageButton) findViewById(R.id.siguiente);
        prev = (ImageButton) findViewById(R.id.previo);

        home = (ImageButton) findViewById(R.id.HomeButton);

        //!!!!IMPPORTANTE: Incializar el numero de ejercicios Silabicos de dos silabas
        ejercicios = SilabicExInitializer(NUMEXCER);
        //Importante ya que se esta pasando el contexto a cada constructor de un ejercicio silabico
        //para que genere el objeto y debido a que el  contexto se crea hasta que se entra al OnCreate
        //aqui es donde se deben incializar los ejercicios pero el arreglo de ejercicios debe
        //ser declarado de forma global
        //-----------------------------------------

        //----OBTENER POSICION DE EJERCICIO QUE DEBE SER DESPLEGADO Y MOSTRARLO---------------------
        inten = getIntent();
        //El 0 es un valor de dfault por si no se encuentra el valor que se buscaba, se asigna 0 por
        //default
        excercise = inten.getIntExtra("INDXEX",0);
        //------------------------------
        //Log.d("eeeinx:","CREADO");
        //Log.d("eeeinx:",""+excercise);
        //-------------------------------
        //MOSTRAR LA IMAGEN CORRESPONDIENTE
        vflip.setDisplayedChild(excercise);
        //Cambiar silabas de checkboxes y guardar posiciones actuales
        posactualex = ChangeTextBoxes(ejercicios[excercise]);
        //-------------- FIN MOSTRAR EJERCICIO CORRESPONDIENTE VIEW FLIPPER-------------------------

        //Objetos para reproducción audio tomar el MediaPlayer de forma global
        sound = new MediaPlayer();
        //Incializar con audio de palabra actual
        //Asignar y reproducir sonido
        sound = ejercicios[excercise].getPalabraValue();

        //Resetear rating bar y checkboxes de ejercicio
        Reset(ejercicios[excercise]);

        //Register a callback to be invoked when this view is clicked
        //Cuando una view suceda en estos botones ir a achecar a la logica del metodo onClick
        next.setOnClickListener(this);
        prev.setOnClickListener(this);

        //***Obtener Datos Actuales del uuario que esta usando la app obtenido de actividad danterior
        inten = getIntent();
        IDCURRENTUSER = inten.getIntExtra("IDUSER",0);

        INICIOTIME = System.currentTimeMillis();
        //**Fin del programa
        Log.d("Fin del programa", "v6");

    }//Fin metodo que llama archivo ejercicio_silabicoabico.xml
    //----------------------------------------------------------------------------------------------
    //Metodo que se llama cada vez que la Actividad recibe una nueva actividad
    //SI ESTE METODO NO ESTA DECLARADO, EL EJERCICIO CUANDO ES SELECCIONADO EN EL SUBMENU SOLO ES
    //CAMBIADO LA PRIMERA VEZ, PERO DESPUES YA NO PORQUE EL INTENT Y SUS DATOS NO SE ACTUALIZAN
    public void onNewIntent(Intent inten)
    {
        //Actualizar los datos deL intent de la actividad anterior, por dados del neuvo intent
        super.onNewIntent(inten);
        this.setIntent(inten);
    }
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
        Log.d("eeeinx:","REANUDADO");
        Log.d("eeeinx:",""+excercise);
        //MOSTRAR LA IMAGEN CORRESPONDIENTE
        vflip.setDisplayedChild(excercise);
        //Cambiar silabas de checkboxes y guardar posiciones actuales
        posactualex = ChangeTextBoxes(ejercicios[excercise]);
        //Restear valores checkobx y rbar
        Reset(ejercicios[excercise]);
        //-------------- FIN MOSTRAR EJERCICIO CORRESPONDIENTE VIEW FLIPPER-------------------------

    }//Fin metodo onResume
    //----------------------------------------------------------------------------------------------
    //Metodo que se incia cuando se mueve a otra actividad
    public void onStop()
    {
        super.onStop();
        //***Obtener Datos Actuales del usario desde la BDS
        inten = getIntent();
        IDCURRENTUSER = inten.getIntExtra("IDUSER",0);

        //OBTENER Y ACTUALIZAR DATOS ACTUALES CON LA BDS SOLO SU HAY CONEXION A LA BDS
        if(IDCURRENTUSER != -1)
        {
            CURRENTUSER = new Usuario(IDCURRENTUSER, this);

            //***Actualizar Datos de Usuario con puntuacion Actual
            //arregloDePuntos/Número de ejercicio 2(3) y Numero de ejercicicos creados
            CURRENTUSER.LevelProgress(scoreSilEx, 2, NUMEXCER);
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

        }//FIN IF 1

    }//Fin metodo onStop
    //----------------------------------------------------------------------------------------------
    //Metodo que se incia cuando se destruye completamente la actividad
    public void onDestroy()
    {
        super.onDestroy();

        //***Obtener Datos Actuales del usario desde la BDS
        Intent inten = getIntent();
        IDCURRENTUSER = inten.getIntExtra("IDUSER",0);

        //OBTENER DATOS DE BDS Y ACTUALZIARLOS SOLAMENTE SI HAY CONEXION A LA BDS
        if(IDCURRENTUSER != -1)
        {
            CURRENTUSER = new Usuario(IDCURRENTUSER, this);

            //***Actualizar Datos de Usuario con puntuacion Actual
            //arregloDePuntos/Número de ejercicio 2(3) y Numero de ejercicicos creados
            CURRENTUSER.LevelProgress(scoreSilEx, 2, NUMEXCER);
            //ACTUALIZAR EL RESTO DE LOS CALCULOS ESTADISTICOS
            FINTIME = System.currentTimeMillis();
            FULLTIME = FINTIME - INICIOTIME;
            CURRENTUSER.calcularUserData(FULLTIME);

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
            //1) se puede usar this porque la clase Activity (que hereda la EjercicioSilabico) extiende de Context
            //2)La clase que se le debe asignar al intent
            Intent in = new Intent(this, MenuPrincipal.class);

            //Mandar id a actividad de menu principal
            in.putExtra("IDUSER",IDCURRENTUSER);

            //Inicar nueva actividad creada en line anterior/ ir a menu principal
            startActivity(in);

            //Calcular info de scores actuales
            //Datos dat = new Datos(this);
            //Actualizar datos del segundo usuario "Pedro"
            //ERROR AQUI
            //dat.ActualizarDatos(1,12.5f,new float[0],scoreSilEx,new float[0]);

        }//Fin if 1

    }//Fin metodo HomeClick
    //----------------------------------------------------------------------------------------------
    //Acciones a realizar cuando se oprima el boton next o previous, no cambiarle el nombre del metodo
    //porque si no suceden problemas
    public void onClick(View v)
    {
        //Log.d("Excercise Actual:",""+excercise);
        //Log.d("state0:",""+ejercicios[excercise].estado[0]);
        //Log.d("state1:",""+ejercicios[excercise].estado[1]);
        //Log.d("state2:",""+ejercicios[excercise].estado[2]);
        //Log.d("state3:",""+ejercicios[excercise].estado[3]);

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
        //View objects occupes a rectangular area on the screen and is respoinsible
        //for drawing and event handling

        //---------- LIBERAR ESPACIO DE  MEDIA PLAYER CADA VEZ QUE SE LLAME ---------
        if(sound != null)
        {
            //sound.reset();
            //sound.release();
            //sound = new MediaPlayer();
        }
        //---------------------------------------------------------------------------

        //Esta el objeto View v marcado?
        //selected recive el valor booleano de acuerdo a la acción que reciba v (argumento de este
        //metodo)
        boolean selected = ( (CheckBox) v).isChecked();

        //Objetos para reproducción audio tomar el MediaPlayer de forma global
        //Usa varaibel global previamente definida

        //Asignar a objeto clase RaitingBar aquella raitingbar que tenga como valor de id:
        //'ratingBar'
        RatingBar bar = ( (RatingBar) findViewById(R.id.ratingBar) );

        //Checar que checkbox esta seleccionada con base en su Id
        switch( v.getId())
        {
            //Checkbox 1
            case R.id.pi:
                if(selected)
                {
                    //Ejercicios = arreglo de SilabicEx
                    //excercise = numero de SilabicEx actual
                    //posactualex = arreglo con posiciones aleatorias de las silabas [s1,s2,s3,s4]
                    //[0] = posicion s1, [1] = posicion s2, [2] = posicion s3 [3] = posicion s4

                    //Marcar en arreglo de estados que la silaba que esta asociada al boton 0, cual
                    //sea que sea esta silaba (porque se asigno de manera aleatoria) ha sido marcada
                    //como seleccionada por el checkbox
                    ejercicios[excercise].SilabaSelected(posactualex[0]);
                    //botones[0] = 1;

                    //Asignar y reproducir audio
                    try
                    {

                        sound = ejercicios[excercise].getResourceNum(posactualex[0]);
                        sound.start();

                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }//Fin if 1
                else
                {
                    //Marcar en arreglo de estados que la silaba que esta asociada al boton 0, cual
                    //sea que sea esta silaba (porque se asigno de manera aleatoria) ha sido marcada
                    //como deseleccionada por el checkbox
                    ejercicios[excercise].SilabaDeselected(posactualex[0]);
                    //botones[0] = 0;


                }//Fin else 1
                break;

            //Checkbox 2
            case R.id.no:
                if(selected)
                {
                    //Marcar en arreglo de estados que la silaba que esta asociada al boton 1, cual
                    //sea que sea esta silaba (porque se asigno de manera aleatoria) ha sido marcada
                    //como seleccionada por el checkbox
                    ejercicios[excercise].SilabaSelected(posactualex[1]);
                    //botones[1] = 1;

                    //Asignar y reproducir sonido
                    try
                    {
                        sound = ejercicios[excercise].getResourceNum(posactualex[1]);
                        sound.start();

                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }//Fin if 2
                else
                {
                    ejercicios[excercise].SilabaDeselected(posactualex[1]);
                    //botones[1] = 0;
                }//Fin else 2
                break;

            //Checkbox 3
            case R.id.pa:
                if(selected)
                {
                    ejercicios[excercise].SilabaSelected(posactualex[2]);
                    //botones[2] = 1;

                    //Asignar y reproducir audio
                    try
                    {
                        sound = ejercicios[excercise].getResourceNum(posactualex[2]);
                        sound.start();

                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }//Fin if 3
                else
                {
                    ejercicios[excercise].SilabaDeselected(posactualex[2]);
                    //botones[2] = 0;
                }//fin else 3
                break;

            //Checkbox 4
            case R.id.ne:
                if(selected)
                {
                    ejercicios[excercise].SilabaSelected(posactualex[3]);
                    //botones[3] = 1;

                    //Asignar y reproducir audio
                    try
                    {
                        sound = ejercicios[excercise].getResourceNum(posactualex[3]);
                        sound.start();

                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }//Fin if 4
                else
                {
                    ejercicios[excercise].SilabaDeselected(posactualex[3]);
                    //botones[3] = 0;
                }//Fin else 4
                break;

            default:
                break;
            //Log.d("botones:",""+botones[0]);

        }//Fin switch

        //Verificar que ha parado la reproducción de audio
        if(sound != null)
        {
            while (sound.isPlaying()) {
                //Esperar a que acabe de reproducir sonido
            }//Fin while
        }

        //------------------ LOGICA DE RESPUESTA CORRECTA ------------------------------------------
        //Verificar seleccion de checkboxes en orden inadecuado
        if(ejercicios[excercise].StateFirstRight() == 0 && ejercicios[excercise].StateSecondRight() == 1)
        {
            //Se completo la palabra pero en orden inverso
            //Poner como 2 el estado de la segunda silaba
            ejercicios[excercise].estado[1] = 2;

        }//Fin if 4.9

        //Ver si se seleccionaron las posibles respuestas correctas
        if(ejercicios[excercise].StateFirstRight() == 1 && ejercicios[excercise].StateSecondRight() != 0 && ejercicios[excercise].StateFirstWrong() == 0 && ejercicios[excercise].StateSecondWrong() == 0 )
        {
            //Verificar si se selcciono silabas en orden correcto
            if( ejercicios[excercise].StateSecondRight() == 1)
            {
                //Respuesta correcta
                Log.d("correcto", "c");

                //Colorear las estrellas
                bar.setRating(Float.parseFloat("3.0"));

                //Guardar score ejercicio actual (f es para indicar que es float)
                scoreSilEx[excercise] = 3.0f;

                //Asignar y reproducir sonido de la palabra correspondiente
                try
                {

                    sound = ejercicios[excercise].getPalabraValue();
                    sound.start();

                    //Verificar que ha parado la reproducción de audio
                    while(sound.isPlaying())
                    {
                        //Esperar a que acabe de reproducir sonido
                    }//Fin while

                }catch(Exception e)
                {
                    e.printStackTrace();
                }
                //Asignar y reproducir audio VERSION ANTERIOR
                //sound = MediaPlayer.create(EjercicioSilabico.this,  ejercicios[excercise].getPalabraValue() );
                //sound.start();

                /*/---Pasar automaticamente a siguiente ejercicio---

                //Verificar que ha parado la reproducción de audio
                while(sound.isPlaying())
                {
                    //Esperar a que acabe de reproducir sonido
                }//Fin while

                //Siguiente imagen
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
                //--- Fin pasar automaticamente a siguiente ejercicio ----*/

            }//Fin if 6

            //Verificar si se selecciono silabas en orden inverso
            if(ejercicios[excercise].estado[1] == 2)
            {
                //Dar 2 de 3 estrellas
                bar.setRating(Float.parseFloat("2.0"));
                //Guardar score ejercicio actual (f es para indicar que es float)
                scoreSilEx[excercise] = 2.0f;

            }//Fin if 7

        }//Fin if 5
        else
        {
            //Si es incorrecto borrar todoo
            bar.setRating(Float.parseFloat("0.0"));


            if(ejercicios[excercise].StateFirstRight() == 1 || ejercicios[excercise].StateSecondRight() != 0 )
            {
                //Mitad de respuesta correcta
                bar.setRating(Float.parseFloat("1.5"));
                //Guardar score ejercicio actual (f es para indicar que es float)
                scoreSilEx[excercise] = 1.5f;


            }//Fin if 8

        }//Fin else 5

        ///*******************
        //sound.stop();

    }//Fin metodo checkBox1click
    //----------------------------------------------------------------------------------------------
    //Metodo que incializa los Ejercicios de 2 silabas
    SilabicEx[] SilabicExInitializer(int numExcercises)
    {

        //Designación del numero de ejercicios que van a existir en el arreglo
        SilabicEx[] silexcercises = new SilabicEx[numExcercises];

        Log.d("fcoz","afuera");

        int[] recursos = {R.raw.pi,R.raw.no,R.raw.pa,R.raw.ne};
        silexcercises[0] = new SilabicEx("pi","no","pa","ne", R.raw.pino, recursos,0,EjercicioSilabico.this);

        int[] recursost = {R.raw.fo,R.raw.to,R.raw.fa,R.raw.ta};
        silexcercises[1] = new SilabicEx("fo","to","fa","ta", R.raw.foto, recursost,1,EjercicioSilabico.this);

        int[] recursodos = {R.raw.ja,R.raw.bon,R.raw.ju,R.raw.bin};
        silexcercises[2] = new SilabicEx("ja","bón","ju","bin",R.raw.jabon, recursodos,2,EjercicioSilabico.this);

        int[] recursotres = {R.raw.ma,R.raw.pa,R.raw.mo,R.raw.pi};
        silexcercises[3] = new SilabicEx("ma","pa","mo","pi",R.raw.mapa, recursotres, 3, EjercicioSilabico.this);

        int[] recursofour = {R.raw.to,R.raw.ro,R.raw.tu,R.raw.re};
        silexcercises[4] = new SilabicEx("to","ro","tu","re", R.raw.toro, recursofour, 4, EjercicioSilabico.this);

        int[] recursofive = {R.raw.tor,R.raw.ta,R.raw.tur,R.raw.ti};
        silexcercises[5] = new SilabicEx("tor","ta","tur","ti", R.raw.torta, recursofive, 5, EjercicioSilabico.this);

        int[] recursosix = {R.raw.bar,R.raw.co,R.raw.ber,R.raw.cu};
        silexcercises[6] = new SilabicEx("bar","co","ber","cu", R.raw.barco, recursosix, 6, EjercicioSilabico.this);

        int[] recursosev = {R.raw.bol,R.raw.so,R.raw.bal,R.raw.si};
        silexcercises[7] = new SilabicEx("bol","so","bal","si", R.raw.bolso, recursosev, 7, EjercicioSilabico.this);

        int[] recurso8 = {R.raw.bo,R.raw.tas,R.raw.tes,R.raw.ba};
        silexcercises[8] = new SilabicEx("bo","tas","tes","ba", R.raw.botas, recurso8, 8, EjercicioSilabico.this);

        int[] recurso9 = {R.raw.bo,R.raw.te,R.raw.ba,R.raw.ta};
        silexcercises[9] = new SilabicEx("bo","te","ba","ta", R.raw.bote, recurso9, 9, EjercicioSilabico.this);

        int[] recurso10 = {R.raw.ca,R.raw.fe,R.raw.ce,R.raw.fi};
        silexcercises[10] = new SilabicEx("ca","fé","ce","fi", R.raw.cafe, recurso10, 10, EjercicioSilabico.this);

        int[] recurso11 = {R.raw.co,R.raw.che,R.raw.chi,R.raw.ca};
        silexcercises[11] = new SilabicEx("co","che","chi","ca", R.raw.coche, recurso11, 11, EjercicioSilabico.this);

        int[] recurso12 = {R.raw.co,R.raw.co,R.raw.cu,R.raw.ce};
        silexcercises[12] = new SilabicEx("co","co","cu","ce", R.raw.coco, recurso12, 12, EjercicioSilabico.this);

        int[] recurso13 = {R.raw.co,R.raw.pa,R.raw.po,R.raw.ci};
        silexcercises[13] = new SilabicEx("co","pa","po","ci", R.raw.copa, recurso13, 13, EjercicioSilabico.this);

        int[] recurso14 = {R.raw.da, R.raw.doo, R.raw.de, R.raw.di};
        silexcercises[14] = new SilabicEx("da","do","de","di", R.raw.dado, recurso14, 14, EjercicioSilabico.this);

        int[] recurso15 = {R.raw.fal,R.raw.da,R.raw.ful,R.raw.di};
        silexcercises[15] = new SilabicEx("fal","da","ful","di", R.raw.falda, recurso15, 15, EjercicioSilabico.this);

        int[] recurso16 = {R.raw.fo,R.raw.co,R.raw.ca,R.raw.fe};
        silexcercises[16] = new SilabicEx("fo","co","ca","fe", R.raw.foco, recurso16, 16, EjercicioSilabico.this);

        int[] recurso17 = {R.raw.ga,R.raw.to,R.raw.ti,R.raw.gu};
        silexcercises[17] = new SilabicEx("ga","to","ti","gu", R.raw.gato, recurso17, 17, EjercicioSilabico.this);

        int[] recurso18 = {R.raw.lo,R.raw.bo,R.raw.la,R.raw.bu};
        silexcercises[18] = new SilabicEx("lo","bo","la","bu", R.raw.lobo, recurso18, 18, EjercicioSilabico.this);

        int[] recurso19 = {R.raw.lu,R.raw.na,R.raw.lo,R.raw.ni};
        silexcercises[19] = new SilabicEx("lu","na","lo","ni", R.raw.luna, recurso19, 19, EjercicioSilabico.this);

        int[] recurso20 = {R.raw.me,R.raw.sa,R.raw.mi,R.raw.su};
        silexcercises[20] = new SilabicEx("me","sa","mi","su", R.raw.mesa, recurso20, 20, EjercicioSilabico.this);

        int[] recurso21 = {R.raw.nu,R.raw.be,R.raw.ni,R.raw.bo};
        silexcercises[21] = new SilabicEx("nu","be","ni","bo", R.raw.nube, recurso21, 21, EjercicioSilabico.this);

        int[] recurso22 = {R.raw.po,R.raw.so,R.raw.pa,R.raw.sa};
        silexcercises[22] = new SilabicEx("po","zo","pa","za", R.raw.pozo, recurso22, 22, EjercicioSilabico.this);

        int[] recurso23 = {R.raw.ra,R.raw.ta,R.raw.ro,R.raw.to};
        silexcercises[23] = new SilabicEx("ra","ta","ro","to", R.raw.rata, recurso23, 23, EjercicioSilabico.this);

        int[] recurso24 = {R.raw.si,R.raw.lla,R.raw.so,R.raw.lle};
        silexcercises[24] = new SilabicEx("si","lla","llo","lle", R.raw.silla, recurso24, 24, EjercicioSilabico.this);

        int[] recurso25 = {R.raw.ta,R.raw.co,R.raw.te,R.raw.cu};
        silexcercises[25] = new SilabicEx("ta","co","te","cu", R.raw.taco, recurso25, 25, EjercicioSilabico.this);

        int[] recurso26 = {R.raw.ti,R.raw.na,R.raw.tu,R.raw.ne};
        silexcercises[26] = new SilabicEx("ti","na","tu","ne", R.raw.tina, recurso26, 26, EjercicioSilabico.this);

        int[] recurso27 = {R.raw.be,R.raw.la,R.raw.va,R.raw.lu};
        silexcercises[27] = new SilabicEx("ve","la","va","lu", R.raw.vela, recurso27, 27, EjercicioSilabico.this);

        return silexcercises;

    }//Fin metodo
    //----------------------------------------------------------------------------------------------
    //Metodo que cambia el texto de las checkBoxes de acuerdo a las silabas del ejercicio actual
    //El orden cambia cada vez que se invoca el metodo. Se regresa el orden de las silabas en un
    //arreglo de enteros
    int[] ChangeTextBoxes(SilabicEx ejercicio)
    {
       int[] silabas = ejercicio.RandomSilabas();

        //Asignarle a cada Checkboc el valor del CheckBox con su ID correspondiente y después
        //a ese boton cambiarle el texto por la silaba del ejercicio correspondiente
        uno = (CheckBox) findViewById(R.id.pi);
        //uno.setText(ejercicio.getFirstRight());
        uno.setText( ejercicio.getThisSilaba(silabas[0]) );

        dos = (CheckBox) findViewById(R.id.no);
        //dos.setText(ejercicio.getSecondRight());
        dos.setText( ejercicio.getThisSilaba(silabas[1]) );

        tres = (CheckBox) findViewById(R.id.pa);
        //tres.setText(ejercicio.getFirstWrong());
        tres.setText( ejercicio.getThisSilaba(silabas[2]) );

        cuatro = (CheckBox) findViewById(R.id.ne);
        //cuatro.setText(ejercicio.getSecondWrong());
        cuatro.setText( ejercicio.getThisSilaba(silabas[3]) );

        return silabas;

    }//Fin metodo ChangeTextBoxes
    //----------------------------------------------------------------------------------------------
    //Metodo que resetea valores de cehckbox a unselected y regresa rating bar a valroes originales
    public void Reset(SilabicEx ejercicio)
    {
        //Asociar a  objeto checkbox cada checkbox y poner valores como unselected
        CheckBox a = (CheckBox) findViewById(R.id.pi);
        a.setChecked(false);

        CheckBox b = (CheckBox) findViewById(R.id.no);
        b.setChecked(false);

        CheckBox c = (CheckBox) findViewById(R.id.ne);
        c.setChecked(false);

        CheckBox d = (CheckBox) findViewById(R.id.pa);
        d.setChecked(false);

        //RATING BAR
        //Adociar a objeto RatingBar la RatingBar definida para poner valores predeterminados
        RatingBar rb = (RatingBar) findViewById(R.id.ratingBar);
        rb.setRating(Float.parseFloat("0.0"));

        //Resetear estados del ejercicio
        ejercicio.ResetEstados();

    }//Fin metodo Reset
    //----------------------------------------------------------------------------------------------
    //Metodo que resetea el arreglo global que lleva el score de los ejercicios de 2 silabas
    float[] ResetScores(float scores[])
    {
        for(int i = 0; i < scores.length; i++)
        {
            scores[i] = 0.0f;
        }//Fin for 1

        return scores;

    }//Fin metodo ResetScores
    //----------------------------------------------------------------------------------------------
    //Datos de salida

    //Fin del programa**
    //no hay print pero se hace logcat al final del metodo onCreate

}//Fin main Class
