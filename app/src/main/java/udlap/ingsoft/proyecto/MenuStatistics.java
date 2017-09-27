package udlap.ingsoft.proyecto;

//Librerias
import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Clase que contiene la lógica que se seguira para mostrar el avance del usuario
 * José Francisco Zerón Cabrera
 * Informacion sobre libreria Mpandroidchard para realizar gráficos, obtenida de:
 * http://happytutorialcode.blogspot.mx/2017/03/android-grouped-bar-chart-customized-x.html
 */

//Incio clase MenuStatistics
public class MenuStatistics extends Activity
{
    //Variables globales
    BarChart barchart;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_statistics);

        //Llamar metodo que construye la grafica con datos actuales
        CreateBarchart();
        //Cada que se entre al menuStatistics mostrar en pantalla los datos actuales del usuario
        Actualizar();

    }//Fin metodo que llama al menuStatistics del archivo .xml

    //Inicio metodos
    //----------------------------------------------------------------------------------------------
    //Metodo que crea la grafica de barras
    public void CreateBarchart()
    {
        //Declaracion de variables
        //Crear una ArrayList de BarEntry objects
        ArrayList<BarEntry> entradasgraf = new ArrayList<>();
        //Crear una ArrayList de Strings
        ArrayList<String> xlabels = new ArrayList<>();

        //Asignacion de valores
        //Asignar a barchart objeto de la Clase BarChart de nombre bargraph
        barchart = (BarChart) findViewById(R.id.bargraph) ;

        //Crear barras y asignarlas en la posicion correspondiente
        //Darles los valores del usuario 2
        Datos dat = new Datos(this);
        Usuario dos = dat.SearchName("Pedro");

        entradasgraf.add(new BarEntry(0f,40f));
        entradasgraf.add(new BarEntry(1f,dos.getProgresslvltwo()));
        entradasgraf.add(new BarEntry(2f,dos.getProgresslvltree()));


        BarDataSet datosDeBarras = new BarDataSet(entradasgraf,"Porcentaje de ejercicios completados");


        //CAMBIAR COLOR DE LAS 3 BARRAS
        int[] Colores = {Color.YELLOW,Color.BLUE,Color.RED};
        datosDeBarras.setColors(Colores);

        BarData datosfinales = new BarData(datosDeBarras);

        //Asignar BarData a objeto BarCHart
        barchart.setData(datosfinales);
        //Restringir movimientos sobre grafica
        barchart.setSaveEnabled(false);
        barchart.setPinchZoom(false);
        barchart.setDrawGridBackground(false);
        //Desabilita zoom en grafica
        barchart.setScaleEnabled(false);

        //EJE x
        xlabels.add("Observador");
        xlabels.add("Explorador");
        xlabels.add("Astronauta");

        XAxis ejex = barchart.getXAxis();
        ejex.setGranularity(1f);
        //ejex.setTextColor(Color.RED);
        ejex.setGranularityEnabled(true);
        ejex.setDrawGridLines(false);
        //Poner labels al fondo de la grafica
        ejex.setPosition(XAxis.XAxisPosition.BOTTOM);
        //Asignar valor de labels a cada barra
        ejex.setValueFormatter(new IndexAxisValueFormatter(xlabels));

    }//Fin metodo CreateBarchart
    //----------------------------------------------------------------------------------------------
    public void Actualizar()
    {
        //Ir a archivo y obtener los datos del usuario Pedro
        Datos dat = new Datos(this);
        Usuario dos = dat.SearchName("Pedro");

        //Text view
        TextView nombre = (TextView) findViewById(R.id.username);
        nombre.setText("Nombre Usuario: "+ dos.getName());

        //Text view horas
        TextView time = (TextView) findViewById(R.id.tiempo);
        time.setText("Tiempo de uso: "+dos.getTime());

        //Text vie maxScore
        TextView score = (TextView) findViewById(R.id.maxscore);
        score.setText("Puntaje máximo: "+dos.getScore());

        //Mostrar en texto el valor calculado para la grafica
        TextView lvltwo = (TextView) findViewById(R.id.porclvl2);
        lvltwo.setText("% en nivel 2: "+dos.getProgresslvltwo());

    }//Fin metodo Actualizar
    //----------------------------------------------------------------------------------------------

}//Fin Clase MenuStatistics
