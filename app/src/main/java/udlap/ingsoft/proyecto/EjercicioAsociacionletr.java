package udlap.ingsoft.proyecto;

import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Clase quye inicializa los ejercicios de tipo de Asociacion de Letras
 *
 */

public class EjercicioAsociacionletr extends AppCompatActivity
{
    MediaPlayer mp;
    ImageButton ibt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ejercicio_asociacionletr);
        ibt = (ImageButton) findViewById(R.id.imageButton3);
    }

    public void OnClick2(View v){
        if (v == ibt){
            mp = MediaPlayer.create(this, R.raw.burro);
            mp.start();
        }
    }

}
