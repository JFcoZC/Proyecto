package udlap.ingsoft.proyecto;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.util.Random;

/**
 * Clase quye inicializa los ejercicios de tipo de Asociacion de Letras
 *
 */

public class EjercicioAsociacionletr extends AppCompatActivity
{
    //Declracion de Atributos
    ImageButton btnSonido, btnNext, btnPrev;
    ImageView vf;
    MediaPlayer Sonido1;
    CheckBox CB1;
    CheckBox CB2;
    CheckBox CB3;
    CheckBox CB4;
    Button btn;
    //Contador para indicar el nuemro de ejercicio
    int count = 0;
    String keyChar;
    RatingBar ratingBar;

    MediaPlayer[] sounds = new MediaPlayer[6];

    Random rm = new Random();

    // Instanciación de objetos de la clase ejercicio para la creación de diferentes juegos
    EjercicioLetra[] ejercicios = {
            new EjercicioLetra("A",R.drawable.abeja, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.b, R.raw.c , R.raw.d, R.raw.ade}),
            new EjercicioLetra("B",R.drawable.burro, new String[] {"R", "P", "B", "U"}, 1,
                    new int[] {R.raw.r, R.raw.p, R.raw.b , R.raw.u, R.raw.bde}),
            new EjercicioLetra("C",R.drawable.carro, new String[] {"A", "C", "H", "I"}, 1,
                    new int[] {R.raw.a, R.raw.c, R.raw.h , R.raw.i, R.raw.cde}),
            new EjercicioLetra("D",R.drawable.dado, new String[] {"T", "J", "C", "D"}, 1,
                    new int[] {R.raw.t, R.raw.j, R.raw.c , R.raw.d, R.raw.dde}),
            new EjercicioLetra("E",R.drawable.estrella, new String[] {"R", "E", "O", "B"}, 1,
                    new int[] {R.raw.r, R.raw.e, R.raw.o , R.raw.b, R.raw.ede}),
            new EjercicioLetra("F",R.drawable.flor, new String[] {"F", "D", "L", "N"}, 1,
                    new int[] {R.raw.f, R.raw.d, R.raw.l , R.raw.n, R.raw.fde}),
            new EjercicioLetra("G",R.drawable.gato, new String[] {"Z", "G", "K", "Q"}, 1,
                    new int[] {R.raw.z, R.raw.g, R.raw.k , R.raw.q, R.raw.gde}),
            new EjercicioLetra("H",R.drawable.helado, new String[] {"T", "V", "H", "M"}, 1,
                    new int[] {R.raw.t, R.raw.v, R.raw.h , R.raw.m, R.raw.hde}),
            new EjercicioLetra("I",R.drawable.iguana, new String[] {"I", "Y", "X", "D"}, 1,
                    new int[] {R.raw.i, R.raw.y, R.raw.x , R.raw.d, R.raw.ide}),
            new EjercicioLetra("J",R.drawable.jirafa, new String[] {"E", "J", "Z", "B"}, 1,
                    new int[] {R.raw.e, R.raw.j, R.raw.z , R.raw.b, R.raw.jde}),
            new EjercicioLetra("K",R.drawable.koala, new String[] {"T", "C", "L", "K"}, 1,
                    new int[] {R.raw.t, R.raw.c, R.raw.l , R.raw.k, R.raw.kde}),
            new EjercicioLetra("L",R.drawable.leon, new String[] {"L", "D", "T", "A"}, 1,
                    new int[] {R.raw.l, R.raw.d, R.raw.t , R.raw.a, R.raw.lde}),
            new EjercicioLetra("M",R.drawable.mono, new String[] {"P", "B", "M", "S"}, 1,
                    new int[] {R.raw.p, R.raw.b, R.raw.m , R.raw.s, R.raw.mde}),
            new EjercicioLetra("N",R.drawable.naranja, new String[] {"M", "O", "R", "N"}, 1,
                    new int[] {R.raw.m, R.raw.o, R.raw.r , R.raw.n, R.raw.nde}),
            new EjercicioLetra("Ñ",R.drawable.niu, new String[] {"Ñ","N","X","L"}, 1,
                    new int[] {R.raw.enie,R.raw.n,R.raw.x,R.raw.l,R.raw.eneniu}),
            new EjercicioLetra("O",R.drawable.oso, new String[] {"F", "Q", "G", "O"}, 1,
                    new int[] {R.raw.f, R.raw.q, R.raw.g , R.raw.o, R.raw.ode}),
            new EjercicioLetra("P",R.drawable.perro, new String[] {"T", "P", "R", "H"}, 1,
                    new int[] {R.raw.t, R.raw.p, R.raw.r , R.raw.h, R.raw.pde}),
            new EjercicioLetra("Q",R.drawable.queso, new String[] {"Y", "Q", "O", "J"}, 1,
                    new int[] {R.raw.y, R.raw.q, R.raw.o , R.raw.j, R.raw.qde}),
            new EjercicioLetra("R",R.drawable.rana, new String[] {"R", "Q", "K", "V"}, 1,
                    new int[] {R.raw.r, R.raw.q, R.raw.k , R.raw.v, R.raw.rde}),
            new EjercicioLetra("S",R.drawable.sol, new String[] {"F", "B", "H", "S"}, 1,
                    new int[] {R.raw.f, R.raw.b, R.raw.h , R.raw.s, R.raw.sde}),
            new EjercicioLetra("T",R.drawable.tortuga, new String[] {"I", "U", "T", "X"}, 1,
                    new int[] {R.raw.i, R.raw.u, R.raw.t , R.raw.x, R.raw.tde}),
            new EjercicioLetra("U",R.drawable.uva, new String[] {"F", "U", "G", "A"}, 1,
                    new int[] {R.raw.f, R.raw.u, R.raw.g , R.raw.a, R.raw.ude}),
            new EjercicioLetra("V",R.drawable.vaca, new String[] {"A", "V", "Q", "F"}, 1,
                    new int[] {R.raw.a, R.raw.v, R.raw.q , R.raw.f, R.raw.vde}),
            new EjercicioLetra("W",R.drawable.waterpolo, new String[] {"W", "L", "S", "T"}, 1,
                    new int[] {R.raw.dobleu, R.raw.l, R.raw.s , R.raw.t, R.raw.wdwater}),
            new EjercicioLetra("X",R.drawable.xilofono, new String[] {"X", "V", "E", "S"}, 1,
                    new int[] {R.raw.x, R.raw.v, R.raw.e , R.raw.s, R.raw.xde}),
            new EjercicioLetra("Y",R.drawable.yoyo, new String[] {"Y","Q", "R", "T"}, 1,
                    new int[] {R.raw.y, R.raw.q, R.raw.r , R.raw.r, R.raw.yde}),
            new EjercicioLetra("Z",R.drawable.zorro, new String[] {"Z","S", "A", "T"}, 1,
                    new int[] {R.raw.z, R.raw.s, R.raw.a , R.raw.t, R.raw.zde})};

    float[] scores = new float[ejercicios.length];

    //Tiempo de juego
    long INICIOTIME = 0;
    long FINTIME = 0;
    long FULLTIME;

    //DECLAR VARIABLE GLOBAL DE INTENT para obtener datos provenientes de actividades anteriroes
    Intent inten;

    //DECLARAR VARIABLE GLOBAL DE ID USAURIO ACTUAL
    int IDCURRENTUSER = -1;

    //DECLARAR VARIABLE GLOBAL DE USUARIO ACTUAL
    Usuario CURRENTUSER;


    @Override
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ejercicio_asociacionletra);

        keyChar = "A";
        //sounds[0] = MediaPlayer.create(this, R.raw.u);
        //sounds[1] = MediaPlayer.create(this, R.raw.e);
        //sounds[2] = MediaPlayer.create(this, R.raw.a);
        //sounds[3] = MediaPlayer.create(this, R.raw.i);
        //sounds[4] = MediaPlayer.create(this, R.raw.ade);



        //btnSonido = (ImageButton) findViewById(R.id.btnSonido);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPrev = (ImageButton) findViewById(R.id.btnPrev);
        vf = (ImageView) findViewById(R.id.imageView1);
        CB1 = (CheckBox) findViewById(R.id.check1);
        CB2 = (CheckBox) findViewById(R.id.check2);
        CB3 = (CheckBox) findViewById(R.id.check3);
        CB4 = (CheckBox) findViewById(R.id.check4);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);


        //sounds[4].start();

        //----OBTENER POSICION DE EJERCICIO QUE DEBE SER DESPLEGADO Y MOSTRARLO---------------------
        inten = getIntent();
        //El 0 es un valor de dfault por si no se encuentra el valor que se buscaba, se asigna 0 por
        //default
        count = inten.getIntExtra("INDXEX",0);

        //Todoo esto se hace en una sola linea:
        //Mostrar la imagen correspondiente

        //CAMBIAR POSICIONES DE PALABRAS DE CHECKBOXES Y GUARDAR POSICIONES ACTUALES
        //ESTO SE REALIZA SOLO UNA VEZ EN EL ON RESUME PARA EVITAR AMONTONAMIENTOS DE AUDIO

        //-------------- FIN MOSTRAR EJERCICIO CORRESPONDIENTE EN VIEWFLIPPER-----------------------

        //*********obtener id usuario actual para pasarlo por medio de home ****************
        IDCURRENTUSER = inten.getIntExtra("IDUSER",0);

        INICIOTIME = System.currentTimeMillis();


    }//Fin on crate
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

        //----OBTENER POSICION DE EJERCICIO QUE DEBE SER DESPLEGADO Y MOSTRARLO---------------------
        inten = getIntent();
        //El 0 es un valor de dfault por si no se encuentra el valor que se buscaba, se asigna 0 por
        //default
        count = inten.getIntExtra("INDXEX",0);

        //Mostrar la imagen correspondiente
        //CAMBIAR POSICIONES DE PALABRAS DE CHECKBOXES Y GUARDAR POSICIONES ACTUALES
        setProperties(ejercicios[count]);

        //-------------- FIN MOSTRAR EJERCICIO CORRESPONDIENTE EN VIEWFLIPPER-----------------------

    }//Fin metodo onResume
    //----------------------------------------------------------------------------------------------
    //Metodo que se incia cuando se mueve a otra actividad
    public void onStop()
    {
        super.onStop();

        //***Obtener datos actuales del usuario desde la base de datos
        inten = getIntent();
        IDCURRENTUSER = inten.getIntExtra("IDUSER",0);

        //OBTENER DATOS DEL USUARIO SOLO SI HAY CONEXION A LA BDS
        if(IDCURRENTUSER != -1)
        {
            CURRENTUSER = new Usuario(IDCURRENTUSER, this);

            //***Actualizar Datos de Usuario con puntuacion Actual
            //arregloDePuntos/Número de ejercicio 2(1)Asociacion y Numero de ejercicicos creados
            CURRENTUSER.LevelProgress(scores, 1, ejercicios.length);
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

    }//Fin metodo on stop
    //----------------------------------------------------------------------------------------------
    //Metodo que se imicia cuando se destruye completamente la actividad
    public void onDestroy()
    {
        super.onDestroy();

        //***Obtener datos actuales del usuario desde la base de datos
        inten = getIntent();
        IDCURRENTUSER = inten.getIntExtra("IDUSER",0);

        //OBTENER DATOS DEL USUARIO SOLO SI HAY CONEXION A LA BDS
        if(IDCURRENTUSER != -1)
        {
            CURRENTUSER = new Usuario(IDCURRENTUSER, this);

            //***Actualizar Datos de Usuario con puntuacion Actual
            //arregloDePuntos/Número de ejercicio 2(1)Asociacion y Numero de ejercicicos creados
            CURRENTUSER.LevelProgress(scores, 1, ejercicios.length);
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

    }//Fin metodo onDestroy
    //----------------------------------------------------------------------------------------------
    //---------INICIALIZACION DE EJERCICIO----
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setProperties(EjercicioLetra exe){

        //Causa problemas de memoria
        //setContentView(R.layout.activity_main);


        btnNext = (ImageButton) findViewById(R.id.btnNext);
        vf = (ImageView) findViewById(R.id.imageView1);
        btnPrev = (ImageButton) findViewById(R.id.btnPrev);
        CB1 = (CheckBox) findViewById(R.id.check1);
        CB2 = (CheckBox) findViewById(R.id.check2);
        CB3 = (CheckBox) findViewById(R.id.check3);
        CB4 = (CheckBox) findViewById(R.id.check4);

        keyChar = exe.keyLetter;
        vf.setImageResource(exe.dirImg);

        CB1.setText(exe.letters[0]);
        CB2.setText(exe.letters[1]);
        CB3.setText(exe.letters[2]);
        CB4.setText(exe.letters[3]);

        CB1.setChecked(false);
        CB2.setChecked(false);
        CB3.setChecked(false);
        CB4.setChecked(false);

        ratingBar.setRating(0);

        sounds[0] = MediaPlayer.create(this, exe.dirSounds[0]);
        sounds[1] = MediaPlayer.create(this, exe.dirSounds[1]);
        sounds[2] = MediaPlayer.create(this, exe.dirSounds[2]);
        sounds[3] = MediaPlayer.create(this, exe.dirSounds[3]);
        sounds[4] = MediaPlayer.create(this, exe.dirSounds[4]);

        System.gc();
        sounds[4].start();



    }
    //----- AVANZAR AL SIGUIENTE EJERCICIO --------------------------
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void nextPrev(View v){

        if (v == btnNext){
            count++;
            if (count == 27) count =0;
            Log.d("eeeee",""+count);
            Log.d("eeeee",""+ejercicios.length);
            setProperties(ejercicios[count]);
            System.gc();
        }else {

            if (count == 0){
                Log.d("eeeee",""+count);
                Log.d("eeeee",""+ejercicios.length);
                count = 26;
                setProperties(ejercicios[count]);
                System.gc();

            }else{
                count--;
                Log.d("eeeee",""+count);
                Log.d("eeeee",""+ejercicios.length);
                setProperties(ejercicios[count]);
                System.gc();

            }
        }
    }
    //------------- Logica CheckBox y sonidos --------------------------

    public void CBclick(View cb){


        if (cb == CB1){
            CB2.setChecked(false);
            CB3.setChecked(false);
            CB4.setChecked(false);
            Sonido1 = sounds[0];
            Sonido1.start();

            if (CB1.getText().equals(ejercicios[count].keyLetter)){
                ratingBar.setRating(Float.parseFloat("3.0"));
                scores[count] = 3.0f;
            }else{
                ratingBar.setRating(0);

            }
        }
        if (cb == CB2){
            CB1.setChecked(false);
            CB3.setChecked(false);
            CB4.setChecked(false);
            Sonido1 = sounds[1];
            Sonido1.start();

            if (CB2.getText().equals(ejercicios[count].keyLetter)){
                ratingBar.setRating(Float.parseFloat("3.0"));
                scores[count] = 3.0f;
            }else{
                ratingBar.setRating(0);

            }
        }
        if (cb == CB3){
            CB1.setChecked(false);
            CB2.setChecked(false);
            CB4.setChecked(false);
            Sonido1 = sounds[2];
            Sonido1.start();

            if (CB3.getText().equals(ejercicios[count].keyLetter)){
                ratingBar.setRating(Float.parseFloat("3.0"));
                scores[count] = 3.0f;
            }else{
                ratingBar.setRating(0);

            }
        }
        if (cb == CB4){
            CB1.setChecked(false);
            CB2.setChecked(false);
            CB3.setChecked(false);
            Sonido1 = sounds[3];
            Sonido1.start();

            if (CB4.getText().equals(ejercicios[count].keyLetter)){
                ratingBar.setRating(Float.parseFloat("3.0"));
                scores[count] = 3.0f;
            }else{
                ratingBar.setRating(0);

            }
        }
        while (Sonido1.isPlaying()){}

    }

    public void OnClick(View v){

        //if (v == btn){
        //    newWindow();

        //}

        if (v == btnSonido){

            Sonido1.start();
            Sonido1.release();
        }

        if(v == vf){
            Sonido1 = sounds[4];
            Sonido1.start();
            Sonido1.release();
        }

    }//Fin metodo OnClick
    //----------------------------------------------------------------------------------------------
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

}
