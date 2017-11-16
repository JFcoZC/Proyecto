package udlap.ingsoft.proyecto;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.RatingBar;
import android.widget.ViewFlipper;

/**
 * Created by José on 19/10/2017.
 * Clase que tiene la logica de Layout de EjercicioLectura
 */

public class EjercicioLectura extends AppCompatActivity implements View.OnClickListener
{
    private VideoView videoView;
    private VideoView videor;
    private int [] videoArray={R.raw.cohete,R.raw.videoluna,R.raw.marciano,R.raw.estrellavid,R.raw.astronauta};
    private String [] pregunta={"¿Qué hace el cohete?", "¿A donde lega el cohete?","¿Qué hace el marciano?","¿Qué hemos visto?","¿Qué somos?"};
    private int position = 0;

    //Tiempo de juego
    long INICIOTIME = 0;
    long FINTIME = 0;
    long FULLTIME;

    //DECLAR CARIABLE GLOBAL DE INTENT para obtener datos provenientes de actividad anterior
    Intent inten;

    //-------------------Inicio variables actualziar rating bars---------------------
    //DECLARAR VARIABLE GLOBAL DE ID USAURIO ACTUAL
    int IDCURRENTUSER = -1;

    //DECLARAR VARIABLE GLOBAL DE USUARIO ACTUAL
    Usuario CURRENTUSER;

    //DETERMINA EL NUMERO DE EJERCICIOS (checar esta variable tambien en clase Usuario)
    int NUMLECTURAS = 5; //numero de ejercicios disponibles
    //------------Fin actualizar variables rating bars----------

    Button siguiente, previo;
    ImageButton home,imgtrans;  //botones home y repeat
    ViewFlipper vflip; //instancia viewflipper
    CheckBox checkdespega,checkespera,checkdespeja;//checkboxes de logica
    TextView mensaje;

    //Indica el ejericicio actual en el que se trabaja empezando por el primero LecturaEX[numlectura];
    int numlectura = 0;

    //IMPORTANTE INCIALIZAR LOS EJERCICIOS
    LecturaEx[] ejercicios = LecturaExInitializer(NUMLECTURAS);

    //Definir del mismo tamaño un arreglo para mantener la puntuacion de cada LECUTA
    float[] scoreLecEx = new float[NUMLECTURAS];

    //Arreglo de 3 variables para guardar las posiciones que fueron creadas automaticamente
    //del ejercicio actual
    int[] posactualex = {0,1,2};
    //Botones



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ejercicio_lectura);

        //objeto que permite pasar de video
        vflip = (ViewFlipper) findViewById(R.id.ViewFlipper);

        //objeto que repretenta los botones de siguiente previo menu y repeticion respectivamente.
        siguiente = (Button) findViewById(R.id.siguiente);
        previo = (Button) findViewById(R.id.previo);
        home = (ImageButton) findViewById(R.id.HomeButton);
        imgtrans = (ImageButton) findViewById(R.id.repetir);

        //objeto de tipo videoview
        videor = (VideoView) findViewById(R.id.cohete);//objeto que será cargado con cada nuevo video

        //Cuando una view suceda en estos botones ir a achecar a la logica del metodo onClick
        siguiente.setOnClickListener(this);
        previo.setOnClickListener(this);

        //IMPORTANTE!! para que al girar el dispositivo no ocurra nada
        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        //-------- INICIAR PANTALLA CON VIDEO Y DATOS CORRESPONDIENTES AL BOTON SEÑALADO------------
        inten = getIntent();
        //El 0 es un valor de dfault por si no se encuentra el valor que se buscaba, se asigna 0 por
        //default
        numlectura = inten.getIntExtra("INDXEX",0);
        //preparamos el nuevo video .
        setUpVideoView();
        //cambiamos los checkboxes
        posactualex = cambiaRespuesta(ejercicios[numlectura]);
        //volvemos a resetear los estados de las cajas (por si acaso)
        Reset(ejercicios[numlectura]);
        //------------------------ FIN MOSTRAR LA LECTURA CORRESPONDIENTE --------------------------

        //Tomar tiempo de que se inicia el ejercicio
        INICIOTIME = System.currentTimeMillis();

    }//Fin metodo on Create


    //Metodo que llama a ir de regreso a menu principal si se oprime boton Home y repite el video si se presiona imgtrans
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

            //Inicar nueva actividad creada en line anterior/ ir a menu principal
            startActivity(in);

        }else if(vi==imgtrans){//si se repite el video
            videoView.stopPlayback();
            String uriPath = "android.resource://" + getPackageName()
                    + "/" +  videoArray[numlectura];
            Uri uri = Uri.parse(uriPath);
            try {
                // Asigna la URI del vídeo que será reproducido a la vista.
                videor.setVideoURI(uri);
                // Se asigna el foco a la VideoView.
                videor.requestFocus();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            videor.start();

        /*
         * Se asigna un listener que nos informa cuando el vídeo
         * está listo para ser reproducido.
         */
            videor.setOnPreparedListener(videoViewListener);;
        }//Fin if 1
    }//Fin metodo HomeClick
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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onResume()
    {
        super.onResume();

        //Incializar tiempo de uso de la aplicación
        INICIOTIME = System.currentTimeMillis();

        //-------- INICIAR PANTALLA CON VIDEO Y DATOS CORRESPONDIENTES AL BOTON SEÑALADO------------
        inten = getIntent();
        //El 0 es un valor de dfault por si no se encuentra el valor que se buscaba, se asigna 0 por
        //default
        numlectura = inten.getIntExtra("INDXEX",0);
        //preparamos el nuevo video que tambien usa valor de variable 'numlectura' .
        setUpVideoView();
        //cambiamos los checkboxes
        posactualex = cambiaRespuesta(ejercicios[numlectura]);
        //volvemos a resetear los estados de las cajas (por si acaso)
        Reset(ejercicios[numlectura]);
        //------------------------ FIN MOSTRAR LA LECTURA CORRESPONDIENTE --------------------------

    }//Fin metodo onResume
    //----------------------------------------------------------------------------------------------
    //Metodo que se incia cuando se mueve a otra actividad
    public void onStop()
    {
        super.onStop();
        //***Obtener Datos Actuales del usario desde la BDS
        Intent inten = getIntent();
        IDCURRENTUSER = inten.getIntExtra("IDUSER",0);
        CURRENTUSER = new Usuario(IDCURRENTUSER,this);

        //***Actualizar Datos de Usuario con puntuacion Actual
        //arregloDePuntos/Número de ejercicio 5(4) y Numero de ejercicicos creados
        CURRENTUSER.LevelProgress(scoreLecEx,4,NUMLECTURAS);
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

    }//Fin metodo onStop
    //----------------------------------------------------------------------------------------------
    //Metodo que se incia cuando se destruye completamente la actividad
    public void onDestroy()
    {
        super.onDestroy();

        //***Obtener Datos Actuales del usario desde la BDS
        Intent inten = getIntent();
        IDCURRENTUSER = inten.getIntExtra("IDUSER",0);
        CURRENTUSER = new Usuario(IDCURRENTUSER,this);

        //***Actualizar Datos de Usuario con puntuacion Actual
        //arregloDePuntos/Número de ejercicio 2(3) y Numero de ejercicicos creados
        CURRENTUSER.LevelProgress(scoreLecEx,4,NUMLECTURAS);
        //ACTUALIZAR EL RESTO DE LOS CALCULOS ESTADISTICOS
        FINTIME = System.currentTimeMillis();
        FULLTIME = FINTIME - INICIOTIME;
        CURRENTUSER.calcularUserData(FULLTIME);

        //***Realizar sobreedcritura informacion de usuario en BDs
        CURRENTUSER.updtadeDataDB(this);

    }//Fin metodo onDestroy
    //----------------------------------------------------------------------------------------------

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClick(View v)
    {
        //reseteamos los estados de el ejercicio en el que nos encontramos
        Reset(ejercicios[numlectura]);

        //Al accionar siguiente
        if(v == siguiente)
        {//Siguiente ejercicio
            numlectura++;

            //Si el numero del ejercicio, después del incremento es igual o mayor del length
            if(numlectura >= NUMLECTURAS ){//Regresarse al primero
                numlectura = 0;
            }
        }//Fin siguiente

        //Se presiona boton previous

        if(v == previo) {//Ejercicio previo
            numlectura--;


            if(numlectura < 0) {//Regresar a la  última  posicion valida del arreglo de ejercicios
                numlectura = ejercicios.length-1;
            }

        }//Fin Previo

        //preparamos el nuevo video .
        setUpVideoView();
        //cambiamos los checkboxes
        posactualex = cambiaRespuesta(ejercicios[numlectura]);
        //volvemos a resetear los estados de las cajas (por si acaso)
        Reset(ejercicios[numlectura]);
    }//dependiendo del ejercicio en el que estemos carga un video u otro

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setUpVideoView() {
        Log.d("dddd onPrepared",""+numlectura);
        // Prepara la URI del vídeo que será reproducido dependiendo del ejercicio en el que nos encontremos
        String uriPath = "android.resource://" + getPackageName() + "/" + videoArray[numlectura];
        Uri uri = Uri.parse(uriPath);
        // Inicializa la VideoView.
        videoView = (VideoView) findViewById(R.id.cohete);
        // Asigna el canvas a la VideoView.

        try {
            // Asigna la URI del vídeo que será reproducido a la vista.
            videor.setVideoURI(uri);
            // Se asigna el foco a la VideoView.
            videor.requestFocus();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        videor.setOnPreparedListener(videoViewListener);
    }
        /*
         * Se asigna un listener que nos informa cuando el vídeo
         * está listo para ser reproducido.
         */




    private MediaPlayer.OnPreparedListener videoViewListener =
            new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    //inicio del video aqui
                    Log.d("dddd onPrepared",""+numlectura);
                    Log.d("dddd:","Empezar video luna");
                    videor.start();
                }
            };

    //////////////////////////Hacer algo cuando el checkbox este presionado//////////////////

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void checkBoxclick(View v){
        //variable que almacena true si el checkbox esta presonado o false si no
        boolean selected = ( (CheckBox) v).isChecked();

        //Objetos para reproducción audio
        MediaPlayer sonido = MediaPlayer.create(EjercicioLectura.this, ejercicios[numlectura].getResourceNum(0) );

        //Asignar a objeto clase RaitingBar aquella raitingbar que tenga como valor de id:
        //'ratingBar'
        RatingBar bar = ( (RatingBar) findViewById(R.id.ratingBar) );

        //Checar que checkbox esta seleccionada con base en su Id
        switch( v.getId())
        {
            //Checkbox 1
            case R.id.despega:
                if(selected)
                {
                    //Ejercicios = arreglo de SilabicEx
                    //excercise = numero de SilabicEx actual
                    //posactualex = arreglo con posiciones aleatorias de las silabas [s1,s2,s3,s4]
                    //[0] = posicion s1, [1] = posicion s2, [2] = posicion s3 [3] = posicion s4

                    //Marcar en arreglo de estados que la silaba que esta asociada al boton 0, cual
                    //sea que sea esta silaba (porque se asigno de manera aleatoria) ha sido marcada
                    //como seleccionada por el checkbox
                    ejercicios[numlectura].SilabaSelected(posactualex[0]);
                    //botones[0] = 1;

                    //Asignar y reproducir audio
                    sonido= MediaPlayer.create(EjercicioLectura.this, ejercicios[numlectura].getResourceNum(posactualex[0]) );
                    sonido.start();

                }//Fin if 1
                else
                {
                    //Marcar en arreglo de estados que la silaba que esta asociada al boton 0, cual
                    //sea que sea esta silaba (porque se asigno de manera aleatoria) ha sido marcada
                    //como deseleccionada por el checkbox
                    ejercicios[numlectura].SilabaDeselected(posactualex[0]);
                    //botones[0] = 0;


                }//Fin else 1
                break;

            //Checkbox 2
            case R.id.despeja:
                if(selected)
                {
                    //Marcar en arreglo de estados que la silaba que esta asociada al boton 1, cual
                    //sea que sea esta silaba (porque se asigno de manera aleatoria) ha sido marcada
                    //como seleccionada por el checkbox
                    ejercicios[numlectura].SilabaSelected(posactualex[1]);
                    //botones[1] = 1;

                    //Asignar y reproducir sonido
                    sonido= MediaPlayer.create(EjercicioLectura.this, ejercicios[numlectura].getResourceNum(posactualex[1]) );
                    sonido.start();
                }//Fin if 2
                else
                {
                    ejercicios[numlectura].SilabaDeselected(posactualex[1]);
                    //botones[1] = 0;
                }//Fin else 2
                break;

            //Checkbox 3
            case R.id.espera:
                if(selected)
                {
                    ejercicios[numlectura].SilabaSelected(posactualex[2]);
                    //botones[2] = 1;

                    //Asignar y reproducir sonido
                    sonido = MediaPlayer.create(EjercicioLectura.this, ejercicios[numlectura].getResourceNum(posactualex[2]) );
                    sonido.start();

                }//Fin if 3
                else
                {
                    ejercicios[numlectura].SilabaDeselected(posactualex[2]);
                    //botones[2] = 0;
                }//fin else 3
                break;

            default:
                break;
            //Log.d("botones:",""+botones[0]);

        }//Fin switch

        //Verificar que ha parado la reproducción de audio
        while(sonido.isPlaying())
        {
            //Esperar a que acabe de reproducir sonido
        }//Fin while

        //------------------ LOGICA DE RESPUESTA CORRECTA ------------------------------------------
        //Se ecnontro respuesta correcta
        if(ejercicios[numlectura].StateFirstRight() == 1 && (ejercicios[numlectura].StateFirstWrong()==0) &&
                ejercicios[numlectura].StateSecondWrong() == 0)
        {
            //Se escogio la respuesta correcta

            //Colorear las estrellas
            bar.setRating(Float.parseFloat("3.0"));

            //Guardar score ejercicio actual (f es para indicar que es float)
            scoreLecEx[numlectura] = 3.0f;
            //0
            long inicio = System.currentTimeMillis();
            //while

            //onClick(siguiente);
        }//fIN IF 1

        //Si la correccta esta selecicionada pero con alguna incorrecta regresar 1 sola estrella
        if(ejercicios[numlectura].StateFirstRight() == 1 && ( (ejercicios[numlectura].StateFirstWrong()==1) ||
                ejercicios[numlectura].StateSecondWrong() == 1) )
        {
            //Colorear las estrellas
            bar.setRating(Float.parseFloat("1.0"));

            //Guardar score ejercicio actual (f es para indicar que es float)
            scoreLecEx[numlectura] = 1.0f;

        }//Fin if 2

        //NO HALLA SELECCIONADO LA OPCION CORRECTA; SIN IMPORTAR LAS OTRAS 2 OPCIONES
        if(ejercicios[numlectura].StateFirstRight() == 0)
        {
            //Colorear las estrellas
            bar.setRating(Float.parseFloat("0.0"));

            //Guardar score ejercicio actual (f es para indicar que es float)
            scoreLecEx[numlectura] = 0.0f;

        }//fIN IF 3

        //------------------------------------------------------------------------------------------

    }//Fin metodo checkBox1click
    int[] cambiaRespuesta(LecturaEx ejercicios) {
        int[] palabra = ejercicios.randomWords();

        //Asignarle a cada Checkbox el valor del CheckBox con su ID correspondiente y después
        //a ese boton cambiarle el texto por la palabra del ejercicio correspondiente
        checkdespega = (CheckBox) findViewById(R.id.despega);
        checkdespega.setText(ejercicios.getThisWord(palabra[0]));

        checkdespeja = (CheckBox) findViewById(R.id.despeja);
        checkdespeja.setText(ejercicios.getThisWord(palabra[1]));

        checkespera = (CheckBox) findViewById(R.id.espera);
        checkespera.setText(ejercicios.getThisWord(palabra[2]));

        mensaje = (TextView) findViewById(R.id.textView2);

        mensaje.setText(pregunta[numlectura]);

        return palabra;
    }
    public void Reset(LecturaEx ejercicios)
    {
        //Asociar a  objeto checkbox cada checkbox y poner valores como unselected
        CheckBox a = (CheckBox) findViewById(R.id.despega);
        a.setChecked(false);

        CheckBox b = (CheckBox) findViewById(R.id.despeja);
        b.setChecked(false);

        CheckBox c = (CheckBox) findViewById(R.id.espera);
        c.setChecked(false);

        //RATING BAR
        //Adociar a objeto RatingBar la RatingBar definida para poner valores predeterminados
        RatingBar rb = (RatingBar) findViewById(R.id.ratingBar);
        rb.setRating(Float.parseFloat("0.0"));

        //Resetear estados del ejercicio
        ejercicios.ResetEstados();

    }
    //----------------------------------------------------------------------------------------------
    //Dentro de este metodo se inicializan todos los ejercicios del tipo LecuraEx
    LecturaEx[] LecturaExInitializer(int numejercicios)
    {
        LecturaEx[] ejercicios = new LecturaEx[numejercicios];

        //Inicializar las lecturas en posicion corrspondiente dentro del ejercico

        int[] sonidos0 = {R.raw.despega,R.raw.despeja,R.raw.espera};
        ejercicios[0] = new LecturaEx("despega","despeja","espera", sonidos0,0);

        int[] sonidos1= {R.raw.luna,R.raw.marte,R.raw.venus};
        ejercicios[1]= new LecturaEx("luna","marte","venus",sonidos1,1);

        int[] sonidos2={R.raw.saluda,R.raw.despide,R.raw.seva};
        ejercicios[2]= new LecturaEx("saluda","se despide","se va",sonidos2,2);

        int[] sonidos3={R.raw.estrella,R.raw.cometa,R.raw.planeta};
        ejercicios[3]= new LecturaEx("una estrella","un cometa","un planeta",sonidos3,3);

        int[] sonidos4={R.raw.astronautas,R.raw.aventureros,R.raw.exploradores};
        ejercicios[4]= new LecturaEx("astronautas","aventureros","exploradores",sonidos4,4);


        return ejercicios;

    }//Fin metodo LecturaExInitializer

}//Fin clase EjercicioLectura
