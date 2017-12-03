package udlap.ingsoft.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Clase que contiene la lógica del submenu que direcciona a ejercicios de letras de abecedario
 */

public class SubmenuAbcObser extends AppCompatActivity
{
    //Atributos

    //VARIABLE DEL EJERCICIO AL QUE SE DESEA ENTRAR
    int NUMEX = 0;

    //variable ID USUARIO ACTUAL
    int IDCURRENTUSER;

    //Metodos
    protected void onCreate(Bundle SavedInstnceState)
    {
        super.onCreate(SavedInstnceState);
        setContentView(R.layout.submenu_abc_obser);

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
    //Si se presiona el boton determinar que boton ha sido y de esta manera determinar en
    //que letra debe ser abierto al iniciar la siguiente pantalla
    public void SelectLetra(View vi)
    {
        //Determinar el boton que se ha oprimido
        switch(vi.getId())
        {
            case R.id.BA:
                NUMEX = 0;
                break;

            case R.id.BB:
                NUMEX = 1;
                break;

            case R.id.BC:
                NUMEX = 2;
                break;

            case R.id.BD:
                NUMEX  = 3;
                break;

            case R.id.BE:
                NUMEX = 4;
                break;

            case R.id.BF:
                NUMEX = 5;
                break;

            case R.id.BG:
                NUMEX = 6;
                break;

            case R.id.BH:
                NUMEX = 7;
                break;

            case R.id.BI:
                NUMEX = 8;
                break;

            case R.id.BJ:
                NUMEX = 9;
                break;

            case R.id.BK:
                NUMEX = 10;
                break;

            case R.id.BL:
                NUMEX = 11;
                break;

            case R.id.BM:
                NUMEX = 12;
                break;

            case R.id.BN:
                NUMEX = 13;
                break;

            case R.id.BÑ:
                NUMEX = 14;
                break;

            case R.id.BO:
                NUMEX = 15;
                break;

            case R.id.BP:
                NUMEX = 16;
                break;

            case R.id.BQ:
                NUMEX = 17;
                break;

            case R.id.BR:
                NUMEX = 18;
                break;

            case R.id.BS:
                NUMEX = 19;
                break;

            case R.id.BT:
                NUMEX = 20;
                break;

            case R.id.BU:
                NUMEX = 21;
                break;

            case R.id.BV:
                NUMEX = 22;
                break;

            case R.id.BW:
                NUMEX = 23;
                break;

            case R.id.BX:
                NUMEX = 24;
                break;

            case R.id.BY:
                NUMEX = 25;
                break;

            case R.id.BZ:
                NUMEX = 26;
                break;

        }//Fin switch 1

        //Inciiar actividad de ejercicioAbecedario
        Intent inte = new Intent(this, EjercicioAbecedario.class);

        //MANDAR POSICION DE EJERCICIO SILABICO EN EL QUE SE DEBE ABRIR LA SIGUIENTE PANTALLA
        inte.putExtra("INDXEX", NUMEX);

        //MANDAR ID DE USUARIO ACTUAL
        inte.putExtra("IDUSER",IDCURRENTUSER);

        startActivity(inte);
        finish();

    }//Fin metodo SelectLetra

}//Fin clase SubmenuAbcObser
