
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

//Inicio clases
//public class Accion
//{

//https://stackoverflow.com/questions/5869871/how-to-catch-a-click-event-on-a-button

//}//Fin clase Boton

//Inicio Main Class
public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    //Declaracion de variables (globales)
    Button next, prev;
    ImageButton home;
    ViewFlipper vflip;
    CheckBox uno,dos,tres,cuatro;
    //DETERMINA EL NUMERO DE EJERCICIOS SILABICOS DE 2 SILABAS
    int NUMEXCER = 6;

    //Variable para determinar si la actividad ya ha sido inciada
    //boolean actexpl = false;

    //Indica la posicion del ejercicio silabico dentro del arreglo de ejercicios
    int excercise = 0;

    //!!!!IMPPORTANTE: Incializar el numero de ejercicios Silabicos de dos silabas
    SilabicEx[] ejercicios = SilabicExInitializer(NUMEXCER);

    //Definir del mismo tamaño un arreglo para mantener la puntuacion de cada ejercicio
    float[] scoreSilEx = new float[NUMEXCER];

    //Arreglo de 4 variables para guardar las posiciones que fueron creadas automaticamente
    //del ejercicio actual
    int[] posactualex = {0,1,2,3};

    //Datos de entrada

    //Procesos

    //Llamar al archivo activity_main.xml
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Incializacion botones es importante si no tira null exception
        //Objeto que permite pasar las fotos como slides
        vflip = (ViewFlipper) findViewById(R.id.ViewFlipper);

        //Botones
        next = (Button) findViewById(R.id.siguiente);
        prev = (Button) findViewById(R.id.previo);

        home = (ImageButton) findViewById(R.id.HomeButton);

        //Register a callback to be invoked when this view is clicked
        //Cuando una view suceda en estos botones ir a achecar a la logica del metodo onClick
        next.setOnClickListener(this);
        prev.setOnClickListener(this);


        //**Fin del programa
        Log.d("Fin del programa", "v6");

    }//Fin metodo que llama archivo activity_main.xml
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

            //Inicar nueva actividad creada en line anterior/ ir a menu principal
            startActivity(in);

            //Calcular info de scores actuales
            Datos dat = new Datos(this);
            //Actualizar datos del segundo usuario "Pedro"
            //ERROR AQUI
            dat.ActualizarDatos(1,12.5f,new float[0],scoreSilEx,new float[0]);

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

        //Esta el objeto View v marcado?
        //selected recive el valor booleano de acuerdo a la acción que reciba v (argumento de este
        //metodo)
        boolean selected = ( (CheckBox) v).isChecked();

        //Objetos para reproducción audio
        MediaPlayer sound = MediaPlayer.create(MainActivity.this, ejercicios[excercise].getPalabraValue() );

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
                    sound = MediaPlayer.create(MainActivity.this, ejercicios[excercise].getResourceNum(posactualex[0]) );
                    sound.start();

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
                    sound = MediaPlayer.create(MainActivity.this, ejercicios[excercise].getResourceNum(posactualex[1]) );
                    sound.start();
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

                    //Asignar y reproducir sonido
                    sound = MediaPlayer.create(MainActivity.this, ejercicios[excercise].getResourceNum(posactualex[2]) );
                    sound.start();

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

                    //Asignar y reproducir sonido
                    sound = MediaPlayer.create(MainActivity.this, ejercicios[excercise].getResourceNum(posactualex[3]) );
                    sound.start();

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
        while(sound.isPlaying())
        {
            //Esperar a que acabe de reproducir sonido
        }//Fin while

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

                //Asignar y reproducir sonido
                sound = MediaPlayer.create(MainActivity.this,  ejercicios[excercise].getPalabraValue() );
                sound.start();

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

    }//Fin metodo checkBox1click
    //----------------------------------------------------------------------------------------------
    //Metodo que incializa los Ejercicios de 2 silabas
    SilabicEx[] SilabicExInitializer(int numExcercises)
    {

        //Designación del numero de ejercicios que van a existir en el arreglo
        SilabicEx[] silexcercises = new SilabicEx[numExcercises];


        int[] recursos = {R.raw.pi,R.raw.no,R.raw.pa,R.raw.ne};
        silexcercises[0] = new SilabicEx("pi","no","pa","ne", R.raw.pino, recursos,0);

        int[] recursost = {R.raw.fo,R.raw.to,R.raw.fa,R.raw.ta};
        silexcercises[1] = new SilabicEx("fo","to","fa","ta", R.raw.foto, recursost,1);

        int[] recursodos = {R.raw.ja,R.raw.bon,R.raw.ju,R.raw.bin};
        silexcercises[2] = new SilabicEx("ja","bón","ju","bin",R.raw.jabon, recursodos,2);

        int[] recursotres = {R.raw.ma,R.raw.pa,R.raw.mo,R.raw.pi};
        silexcercises[3] = new SilabicEx("ma","pa","mo","pi",R.raw.mapa, recursotres, 3);

        int[] recursofour = {R.raw.to,R.raw.ro,R.raw.tu,R.raw.re};
        silexcercises[4] = new SilabicEx("to","ro","tu","re", R.raw.toro, recursofour, 4);

        int[] recursofive = {R.raw.tor,R.raw.ta,R.raw.tur,R.raw.ti};
        silexcercises[5] = new SilabicEx("tor","ta","tur","ti", R.raw.torta, recursofive, 5);

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
