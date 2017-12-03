package udlap.ingsoft.proyecto;

//Librerias

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.content.Intent;

import java.util.concurrent.ExecutionException;


/**
 * Clase que contiene la lógica que se seguira en el menu principal
 * José Francisco Zerón Cabrera
 */

//INICIO CLASE MENUPRINCIPAL
public class MenuPrincipal extends AppCompatActivity
{
    //VARIABLES GLOBALES
    AlertDialog alert;
    int idusuario = -1;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        Log.d("DDMENUPRINC","ONCREATE MENUPRINCIPAL");

        //Iniciar una ventana de alerta en la activity correspondiente al context de ctxt
        alert = new AlertDialog.Builder(this).create();
        alert.setTitle("Estado de Acceso");

        //Crear boton de cancel/dimiss en el alertdialog
        alert.setButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }//Fin metodo onCLick
        }); //Fin metodo setButton

        //Solo mostrar la ventana de alerta solo la primera vez que se accede, cuando el usaurio
        //se acaba de registrar
        //RECIBIR LA INFO MANDADA YA SEA QUE VENGA DE ACTIVIDAD LOGIN O REGISTRO (ambas mandan las dos variables)
        Intent inten = getIntent();
        //El 0 es el valor de default que va a recibir la variable si no hay nada en el intent
        int firsttime = inten.getIntExtra("primeraVez", 0);
        idusuario = inten.getIntExtra("IDUSER", 0);

        //Ver si es la priera vez que accede el usuario
        if(firsttime == 1)
        {
            //Si ES LA PRIMERA VEZ, mostrar cual es su id en ventana de alerta
            alert.setMessage("Registro exitoso! ID asignado: "+idusuario);
            alert.show();

        }//Fin if 1

        //++++++++++ INICIO DE CREAR USUARIO CON INFOR RECUPERADA DESDE LA BDs++++++++++++++++++++++
        //Crear un objeto de la clase Usuario a partir del id y contexto que le es pasado; el usuario
        //es automaticamnete generado con la info correspondiente al id que es recuperada desde la
        //base de datos
        //++++++++++ FIN DE INICIALIZACION DE USUARIO+++++++++++++++++++++++++++++++++++++++++++++++

        //**Fin de la actividad
        Log.d("Fin Act:MenuPrincipal", "v2");

    }//Fin metodo que llama archivo xml .menu_principal
    //----------------------------------------------------------------------------------------------
    //Cada vez que se vuelva a entrar a la app verificar el Id de usario que se le esta pasano
    public void onResume()
    {
        super.onResume();


    }//Fin metodo on  Resume
    //----------------------------------------------------------------------------------------------
    //Metodo que inicia actividades correspondientes al nivel observadores
    public void ClickObservadores(View v)
    {
        Intent in = new Intent(this, MenuObservadores.class);
        //Mandar id de usuario actual a actividad del MenuObservadores
        in.putExtra("IDUSER",idusuario);
        startActivity(in);
        finish();


    }//Fin metodo ClickObservadores
    //----------------------------------------------------------------------------------------------
    //Metodo que inicia actividades correspondientes al nivel exploradores
    public void ClickExploradores(View v)
    {
        //Un intent es un objeto que permite un enlace en tiempo de ejecución entre 2 actividades
        //son generalemnte utilziadas para realizar varias tareas al mismo tiempo
        Intent in = new Intent(this, MenuExploradores.class);
        //Mandar id de usuario actual a actividad del MenuExploradores
        in.putExtra("IDUSER",idusuario);
        startActivity(in);
        finish();


    }//Fin metodo ClickExplroadores
    //----------------------------------------------------------------------------------------------
    //Metodo que inica actividades correspondientes a  menu statistics
    public void ClickStatistics(View vi)
    {
        //Si no hay conexion a BDS (-1) no abrir estadisticas
        if(idusuario != -1)
        {
            Intent inte = new Intent(this, MenuStatistics.class);
            //Mandar id de usuario actual a actividad del MenuStatistics
            inte.putExtra("IDUSR",idusuario);
            startActivity(inte);
            finish();

        }//Fin if 1

    }//Fin metodo ClickSatistics
    //----------------------------------------------------------------------------------------------
    //Metodo que inicia actividades correspondientes a nivel astronautas
    public void ClickAstronautas(View vi)
    {
        //Inciiar actividad
        Intent inte = new Intent(this, MenuAstronautas.class);
        //Mandar id de usuario actual a actividad del MenuAstronautas
        inte.putExtra("IDUSER",idusuario);
        startActivity(inte);
        finish();


    }//Fin metodo ClickAstronautas
}//Fin clase MenuPrincipal
