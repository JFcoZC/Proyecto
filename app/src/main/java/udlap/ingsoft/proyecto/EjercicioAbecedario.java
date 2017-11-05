package udlap.ingsoft.proyecto;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.view.View;
import android.media.MediaPlayer;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.net.URI;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by luisricardo on 19/10/2017.
 * Clase que inicializa los ejercicios de tipo abecedario
 */

public class EjercicioAbecedario extends AppCompatActivity
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
    int count = 0;

    MediaPlayer[] sounds = new MediaPlayer[6];

    String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    Random rm = new Random();
    EjercicioLetra[] ejercicios = {
            new EjercicioLetra("A",R.drawable.abeja, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("B",R.drawable.burro, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("C",R.drawable.carro, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("D",R.drawable.dado, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("E",R.drawable.estrella, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("F",R.drawable.flor, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("G",R.drawable.gato, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("H",R.drawable.helado, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("I",R.drawable.iguana, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("J",R.drawable.jirafa, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("K",R.drawable.koala, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("L",R.drawable.leon, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("M",R.drawable.mono, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("N",R.drawable.naranja, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("O",R.drawable.oso, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("P",R.drawable.perro, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("Q",R.drawable.queso, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("R",R.drawable.rana, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("S",R.drawable.sol, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("T",R.drawable.tortuga, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("U",R.drawable.uva, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("V",R.drawable.vaca, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("W",R.drawable.abeja, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("X",R.drawable.xilofono, new String[] {"A", "B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("Y",R.drawable.yoyo, new String[] {"Y","B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u}),
            new EjercicioLetra("Z",R.drawable.zorro, new String[] {"Z","B", "C", "D"}, 1,
                    new int[] {R.raw.a, R.raw.e, R.raw.i , R.raw.u})};


    int[] ejerciciosIndex = new int[27];
    EjercicioLetra exercise;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ejercicio_abecedario);


        sounds[0] = MediaPlayer.create(this, R.raw.a);
        sounds[1] = MediaPlayer.create(this, R.raw.e);
        sounds[2] = MediaPlayer.create(this, R.raw.i);
        sounds[3] = MediaPlayer.create(this, R.raw.u);
        sounds[4] = MediaPlayer.create(this, R.raw.abeja);



        //btnSonido = (ImageButton) findViewById(R.id.btnSonido);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPrev = (ImageButton) findViewById(R.id.btnPrev);
        vf = (ImageView) findViewById(R.id.imageView1);
        CB1 = (CheckBox) findViewById(R.id.check1);
        CB2 = (CheckBox) findViewById(R.id.check2);
        CB3 = (CheckBox) findViewById(R.id.check3);
        CB4 = (CheckBox) findViewById(R.id.check4);

        sounds[4].start();

    }

    //---------INICIALIZACION DE SEGUNDO TIPO DE EJERCICIO----
    public void newWindow()
    {
        String actionName = "android.intent.action.Eje2";
        Intent intent = new Intent(this, EjercicioAsociacionletr.class);
        startActivity(intent);
    }//FIN

    public void setProperties(EjercicioLetra exe){

        //setContentView(R.layout.activity_main);

        btnNext = (ImageButton) findViewById(R.id.btnNext);
        vf = (ImageView) findViewById(R.id.imageView1);
        btnPrev = (ImageButton) findViewById(R.id.btnPrev);
        CB1 = (CheckBox) findViewById(R.id.check1);
        CB2 = (CheckBox) findViewById(R.id.check2);
        CB3 = (CheckBox) findViewById(R.id.check3);
        CB4 = (CheckBox) findViewById(R.id.check4);

        vf.setImageResource(exe.dirImg);

        CB1.setText(exe.letters[0]);
        CB2.setText(exe.letters[1]);
        CB3.setText(exe.letters[2]);
        CB4.setText(exe.letters[3]);

        sounds[0] = MediaPlayer.create(this, exe.dirSounds[0]);
        sounds[1] = MediaPlayer.create(this, exe.dirSounds[1]);
        sounds[2] = MediaPlayer.create(this, exe.dirSounds[2]);
        sounds[3] = MediaPlayer.create(this, exe.dirSounds[3]);
        //sounds[4] = MediaPlayer.create(this, exe.dirSounds[4]);

        System.gc();
        sounds[4].start();

    }
    public void nextPrev(View v){
        if (v == btnNext){
            count++;
            if (count == 26) count =0;
            Log.d("eeeee",""+count);
            Log.d("eeeee",""+ejercicios.length);
            setProperties(ejercicios[count]);
            System.gc();
        }else {
            if (count == 0){
                count = 25;
                setProperties(ejercicios[count]);
                System.gc();

            }else{
                count--;
                setProperties(ejercicios[count]);
                System.gc();

            }
        }
    }

    public void OnClick(View v){

        if (v == btn){
            newWindow();
        }

        if (v == btnSonido){

            Sonido1 = MediaPlayer.create(this, R.raw.pajaros);
            Sonido1.start();
        }
        /*if (v == btnNext ){

            vf.showNext();
        }*/
        if (v == CB1){
            Sonido1 = sounds[0];
            Sonido1.start();
        }
        if (v == CB2){
            Sonido1 = sounds[1];
            Sonido1.start();
        }
        if (v == CB3){
            Sonido1 = sounds[2];
            Sonido1.start();
        }
        if (v == CB4){
            Sonido1 = sounds[3];
            Sonido1.start();
        }
        if(v == vf){
            Sonido1 = sounds[4];
            Sonido1.start();
        }

    }

}//Fin clase ejercicioAbecedario
