//Librerias
package udlap.ingsoft.proyecto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Controla las acciones dentro del menu de los ejercicios del nivel de astronautas
 */

//Inicio clase MenuObservadores
public class MenuObservadores extends AppCompatActivity
{
    //Declaracion de elementos interfaz
    ImageButton bhome;

    //DECLARAR VARIABLE GLOBAL DEL ID DEL USUARIO ACTUAL
    int IDCURRENTUSER = -1;

    //METODOS
    //Llamar a XML para mostrar la pantalla del Menu
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_observadores);

        bhome = (ImageButton) findViewById(R.id.HomeButton);

        //Recibir el Id del usuario actual
        Intent in = getIntent();
        //El 0 es un valor que se asignara en caso de que no se encuentre ningun valor que halla
        //sido pasado a esta activity
        IDCURRENTUSER = in.getIntExtra("IDUSER",0);

    }//Fin metodo onCreate
    //----------------------------------------------------------------------------------------------
    //Metodo que llama a ir de regreso a menu principal si se oprime boton Home
    public void HomeClick(View vi)
    {
        //Si se oprime el boton HOME
        if(vi == bhome)
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

        }//Fin if 1

    }//Fin metodo HomeClick
    //----------------------------------------------------------------------------------------------
    //Metodo que llama a submenu de ejercicio de abecedario
    public void ClickAbecedario(View v)
    {
        //Llamara actividad que lleve a SubmenuAbecedario
        Intent in = new Intent(this,SubmenuAbcObser.class);
        //Mandar ID del usuario actual a siguiente pantalla de submenu de ejercicio Asocialetras
        in.putExtra("IDUSER",IDCURRENTUSER);

        startActivity(in);

    }//Fin metodo ClickAbecedario
    //----------------------------------------------------------------------------------------------
    //Metodo que llama a submenu de ejercicio de asociacion de letras
    public void ClickAsociaLetras(View v)
    {

        Intent in = new Intent(this,SubmenuAsocialObser.class);
        //Mandar ID del usuario actual a siguiente pantalla de submenu de ejercicio Asocialetras
        in.putExtra("IDUSER",IDCURRENTUSER);
        //Iniciar actividad submenu ascoiar letras
        startActivity(in);

    }//Fin metodo AsociarLetras
    //----------------------------------------------------------------------------------------------

}//Fin clase MenuObservadores
