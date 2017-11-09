package udlap.ingsoft.proyecto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.view.View;
import android.media.MediaPlayer;

/**
 * Created by luisricardo on 19/10/2017.
 * Clase que inicializa los ejercicios de tipo abecedario
 */

public class EjercicioAbecedario extends AppCompatActivity
{

    MediaPlayer mp;
    ImageButton ibt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ejercicio_abcedario);
        ibt = (ImageButton) findViewById(R.id.imageButton3);
    }

    public void OnClick2(View v){
        if (v == ibt){
            mp = MediaPlayer.create(this, R.raw.burro);
            mp.start();
        }
    }



}//Fin clase ejercicioAbecedario
