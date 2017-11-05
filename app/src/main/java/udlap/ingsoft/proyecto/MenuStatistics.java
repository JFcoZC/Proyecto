package udlap.ingsoft.proyecto;

//Librerias
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ProgressBar;
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
import java.util.concurrent.ExecutionException;

/**
 * Clase que contiene la lógica que se seguira para mostrar el avance del usuario
 * José Francisco Zerón Cabrera
 * Informacion sobre libreria Mpandroidchard para realizar gráficos, obtenida de:
 * http://happytutorialcode.blogspot.mx/2017/03/android-grouped-bar-chart-customized-x.html
 *
 * Informacion sobre progress bar:
 * https://android--code.blogspot.mx/2015/08/android-progressbar-color_31.html
 * https://xinyustudio.wordpress.com/2014/08/19/android-change-progressbar-height/
 */

//Incio clase MenuStatistics
public class MenuStatistics extends AppCompatActivity
{
    //Variables globales
    BarChart barchart;
    int iduser = 0;

    //OBJETOS LAYOUT
    ProgressBar pbdos, pbtres, pbcuatro, pbcinco, pbseis;
    TextView nombre,time,score,access, lvl2, lvl3, lvl4, lvl5, lvl6;
    //Enteros que guardan porgreso de cada ejercicio
    int prg2,prg3,prg4,prg5, prg6;

    //EJECUTAR ESTO AL CREAR POR PRIMERA VEZ LA ACTIVIDAD
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_statistics);

        //Recibir informacion mandada de MENUPRINCIPAL
        Intent in = getIntent();
        //El 0 es el valor de default que va a recibir la variable si no hay nada en el intent
        iduser = in.getIntExtra("IDUSR",0);

        //Cada que se entre al menuStatistics mostrar en pantalla los datos actuales del usuario
        Actualizar(iduser,this);

        //Llamar metodo que construye la grafica con datos actuales
        CreateBarchart(iduser,this);

    }//Fin metodo que llama al menuStatistics del archivo .xml

    //EJECUTAR ESTO CADA QUE SE VUELVE A ENTRAR A LA ACTIVIDAD
    public void onResume()
    {
        super.onResume();

        Actualizar(iduser,this);
        CreateBarchart(iduser,this);

    }//Fin metodo onResume que se eejcuta cada vez que se vuelve a entrar a esta pantalla

    //Inicio metodos
    //----------------------------------------------------------------------------------------------
    //Metodo que crea la grafica de barras
    public void CreateBarchart(int iduser, Context c)
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
        //Darles los valores del usuario que esta en la base de datos con el id en la variable id
        Usuario usera = new Usuario(iduser,c);
        //CALCULAR DATOS PASANDOLOE COMO TIEMPO 0 YA QUE SOLO SE CUENTA EL TIEMPO EN JUEGOS
        usera.calcularUserData(0);

        entradasgraf.add(new BarEntry(0f,40f));
        entradasgraf.add(new BarEntry(1f,usera.getProgresslvltwo()));
        entradasgraf.add(new BarEntry(2f,usera.getProgresslvltree()));


        BarDataSet datosDeBarras = new BarDataSet(entradasgraf,"Porcentaje de niveles completados");


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
    public void Actualizar(int id, Context c)
    {
        //Crear un objeto de la clase Usuario a partir del id y contexto que le es pasado; el usuario
        //es automaticamnete generado con la info correspondiente al id que es recuperada desde la
        //base de datos
        //----- Checar porque este usuario no tiene ni clave ni nombre es meramente ilustrativo
        Usuario curusr = new Usuario(id,c);
        //CALCULAR DATOS PASANDOLOE COMO TIEMPO 0 YA QUE SOLO SE CUENTA EL TIEMPO EN JUEGOS
        curusr.calcularUserData(0);

        //Text view
        nombre = (TextView) findViewById(R.id.username);
        nombre.setText("Nombre Usuario: "+ curusr.getName());

        //Text view horas
        time = (TextView) findViewById(R.id.tiempo);
        time.setText("Tiempo de uso: "+curusr.getTime()  +" minutos");

        //Text vie maxScore
        score = (TextView) findViewById(R.id.maxscore);
        score.setText("Puntaje máximo: "+curusr.getScore());

        //TextTime numero de accesos
        access = (TextView) findViewById(R.id.numaccess);
        access.setText( "Número de accesos: "+ curusr.getNumAccs() );

        //++++Asignar barras de progreso y valor de progreso correspondiente++++

        //Ejericio 2 Asociacion de letras
        pbdos = (ProgressBar) findViewById(R.id.pBarE2);
        //Asignar porcentaje de ejercicio correspondiente multiplicado * 2 ya que este emtodo lo
        //toma como el 50 % pero aqui representa el avance del 100% en ese ejercicio
        prg2 = (int) (curusr.PromSkillsEx(curusr.exercisesprogress[1], curusr.NUMEXLTWO) *2);
        pbdos.setProgress(prg2);
        //Cambiar text viw agrgando el porcentaje avanzado
        lvl2 = (TextView) findViewById(R.id.tvlvl2);
        lvl2.setText("Asociacion del letras: "+prg2+"% ");

        //Ejercicio 3 Asociacion silabica
        pbtres = (ProgressBar) findViewById(R.id.pBarE3);
        //-------------------------------------------------------------------------------------------------
        //Cambiar color de la barra cuando es de style @style/Widget.AppCompat.ProgressBar.Horizontal"
        //pbetres.getProgressDrawable().setColorFilter(Color.parseColor("#00A300"), PorterDuff.Mode.SRC_IN);
        //--------------------------------------------------------------------------------------------------
        prg3 = (int) (curusr.PromSkillsEx(curusr.exercisesprogress[2], curusr.NUMEXLTREE) * 2 );
        pbtres.setProgress(prg3);
        //Text view
        lvl3 = (TextView) findViewById(R.id.tvlvl3);
        lvl3.setText("Formación de silabas: "+prg3+"% ");

        //Ejercicio 4 Sopa de letras
        pbcuatro = (ProgressBar) findViewById(R.id.pBarE4);
        //Calcular porcentaje de avance multiplicado por 2 para el 100%
        prg4 = (int) (curusr.PromSkillsEx(curusr.exercisesprogress[3], curusr.NUMEXLFOUR) * 2 );
        pbcuatro.setProgress(prg4);
        //Text view
        lvl4 = (TextView) findViewById(R.id.tvlvl4);
        lvl4.setText("Sopa de letras: "+prg4+"% ");

        //Ejercicio 5 Lecturas animadas
        pbcinco = (ProgressBar) findViewById(R.id.pBarE5);
        //Calcular porcentaje de avance multiplicado por 2 para el 100%
        prg5 = (int) (curusr.PromSkillsEx(curusr.exercisesprogress[4], curusr.NUMEXLFIVE) * 2 );
        pbcinco.setProgress(prg5);
        //Text view
        lvl5 = (TextView) findViewById(R.id.tvlvl5);
        lvl5.setText("Lecturas animadas: "+prg5+"% ");

        //Ejercicio 6 Juego ortografia
        pbseis = (ProgressBar) findViewById(R.id.pBarE6);
        //Calcular porcentaje de avance multiplicado por 2 para el 100%
        prg6 = (int) (curusr.PromSkillsEx(curusr.exercisesprogress[5], curusr.NUMEXLSIX) * 2 );
        pbseis.setProgress(prg6);
        //Text view
        lvl6 = (TextView) findViewById(R.id.tvlvl6);
        lvl6.setText("Juego ortografía: "+prg6+"% ");

    }//Fin metodo Actualizar
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

        //Inicar nueva actividad creada en line anterior/ ir a menu principal
        startActivity(in);

    }//Fin metodo HomeClick

}//Fin Clase MenuStatistics
