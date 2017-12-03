package udlap.ingsoft.proyecto;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.view.View;
import android.media.MediaPlayer;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by luisricardo on 19/10/2017.
 * Clase que inicializa los ejercicios de tipo abecedario
 */

public class EjercicioAbecedario extends AppCompatActivity
{
    //variable ID USUARIO ACTUAL
    int IDCURRENTUSER = 0;

    MediaPlayer mp;
    ImageView ibt;
    ImageButton btnNext;
    TextView txtView;

    String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "Ñ","O",
            "P", "Q", "R", "S", "T", "U", "V", "W" ,"X", "Y", "Z"};

    int[] images = {R.drawable.abeja,R.drawable.burro, R.drawable.carro, R.drawable.dado, R.drawable.estrella, R.drawable.flor,
            R.drawable.gato, R.drawable.helado, R.drawable.iguana, R.drawable.jirafa, R.drawable.koala, R.drawable.leon,
            R.drawable.mono, R.drawable.naranja, R.drawable.niu ,R.drawable.oso, R.drawable.perro, R.drawable.queso, R.drawable.rana,
            R.drawable.sol, R.drawable.tortuga, R.drawable.uva, R.drawable.vaca, R.drawable.waterpolo ,R.drawable.xilofono, R.drawable.yoyo,
            R.drawable.zorro };
    int[] sounds = {R.raw.ade, R.raw.bde, R.raw.cde, R.raw.dde, R.raw.ede, R.raw.fde, R.raw.gde, R.raw.hde,
            R.raw.ide, R.raw.jde, R.raw.kde, R.raw.lde, R.raw.mde, R.raw.nde, R.raw.eneniu,R.raw.ode, R.raw.pde, R.raw.qde,
            R.raw.rde, R.raw.sde, R.raw.tde, R.raw.ude, R.raw.vde, R.raw.wdwater ,R.raw.xde, R.raw.yde, R.raw.zde};
    float[] scores = new float[27];

    //Contador para indicar el numero de ejercicio actual
    int count = 0;
    //DECLAR VARIABLE GLOBAL DE INTENT para obtener datos provenientes de actividades anteriroes
    Intent inten;

    //---------------------------------------------------------------------------------------------
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ejercicio_abcedario);
        ibt = (ImageButton) findViewById(R.id.imageButton3);
        txtView = (TextView) findViewById(R.id.textView);
        btnNext = (ImageButton) findViewById(R.id.btnNext);

        //----OBTENER POSICION DE EJERCICIO QUE DEBE SER DESPLEGADO Y MOSTRARLO---------------------
        inten = getIntent();
        //El 0 es un valor de dfault por si no se encuentra el valor que se buscaba, se asigna 0 por
        //default
        count = inten.getIntExtra("INDXEX",0);

        //Todoo esto se hace en una sola linea:
        //Mostrar la imagen correspondiente
        //CAMBIAR POSICIONES DE PALABRAS DE CHECKBOXES Y GUARDAR POSICIONES ACTUALES
        setProperties(count);
        //-------------- FIN MOSTRAR EJERCICIO CORRESPONDIENTE EN VIEWFLIPPER-----------------------

        //obtener ID usuario actual
        IDCURRENTUSER = inten.getIntExtra("IDUSER",0);

    }//Fin metodo onCreate
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
        //INICIOTIME = System.currentTimeMillis();

        //----OBTENER POSICION DE EJERCICIO QUE DEBE SER DESPLEGADO Y MOSTRARLO---------------------
        inten = getIntent();
        //El 0 es un valor de dfault por si no se encuentra el valor que se buscaba, se asigna 0 por
        //default
        count = inten.getIntExtra("INDXEX",0);


        //Mostrar la imagen correspondiente
        //CAMBIAR POSICIONES DE PALABRAS DE CHECKBOXES Y GUARDAR POSICIONES ACTUALES
        setProperties(count);

        //-------------- FIN MOSTRAR EJERCICIO CORRESPONDIENTE EN VIEWFLIPPER-----------------------

    }//Fin metodo onResume
    //----------------------------------------------------------------------------------------------
    public void setProperties(int n)
    {
        ibt = (ImageView) findViewById(R.id.imageView);
        ibt.setImageResource(images[n]);
        mp = MediaPlayer.create(this, sounds[n]);
        txtView = (TextView) findViewById(R.id.textView);
        txtView.setText(alphabet[count]);

        mp.start();
    }//Fin metodo setProperties
    //----------------------------------------------------------------------------------------------
    public void nextPrev(View v){
        if (v == btnNext){
            count++;
            if (count == 27) count =0;
            Log.d("eeeeeSIG",""+count);
            Log.d("eeeee",""+alphabet.length);
            setProperties(count);
            System.gc();
        }else {
            if (count == 0){
                count = 27;
                Log.d("eeeee",""+count);
                Log.d("eeeee",""+alphabet.length);
                setProperties(count);
                System.gc();

            }else{
                count--;
                Log.d("eeeeePREV",""+count);
                Log.d("eeeee",""+alphabet.length);
                setProperties(count);
                System.gc();

            }
        }
    }
    //----------------------------------------------------------------------------------------------
    public void OnClick2(View v){

        mp.start();
    }
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



}//Fin clase ejercicioAbecedario
