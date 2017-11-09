package udlap.ingsoft.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Clase que maneja la lógica del ejercicio de tipo Asociacion de letras que corresponde al seguno
 * tipo de ejrcicios del nive Observadores
 */

public class SubmenuAsocialObser extends AppCompatActivity
{
    //Atributos

    //Declarar variable del ID del usuario actual
    int IDCURRENTUSER = -1;
    //Declaracion de variable global de USUQRIO
    Usuario CURRENTUSER;

    //Atributos jeugo orttografia
    float[] scoreslvltwo;
    //Arreglo con rating bars Asociacion de letras
    int[] idrbsasol = {R.id.rb1astro2,R.id.rb2astro2,R.id.rb3astro2,R.id.rb4astro2};

    //Metodos
    public void onCreate(Bundle SavedInstanceState)
    {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.submenu_asocial_obser);

        /*/------ ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-------
        //***Obtener el ID DEL USUARIO ACTUAL
        Intent in = getIntent();
        //0 valor de default que rescibiria la variable si no hay nada en el Intent
        IDCURRENTUSER = in.getIntExtra("IDUSER",0);

        //***Obtener los scores del usuario actual
        CURRENTUSER = new Usuario(IDCURRENTUSER,this);

        //Obtener los escores del lvltwojuego de Asociacion de letras
        scoreslvltwo = CURRENTUSER.getScoreslvltwo();

        //Crear objeto de la clase SubMenu utilizando la view actual
        SubMenu submsilabas = new SubMenu(scoreslvltwo,idrbsasol,findViewById(android.R.id.content));

        //Hacer actualizacion de los ratingbars antes de entrar al submenu
        submsilabas.updateAllRbs();
        //---- FIN ACTUALIZAR RATINGBARS DE EJERCICIOS SUBMENU-----*/


    }//Fin metodo on Create
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
    //----------------------------------------------------------------------------------------------
    //Si se presiona el boton determinar que boton ha sido y de esta manera determinar en
    //que EJERCICIO debe ser abierto al iniciar la siguiente pantalla
    public void SelectNivel(View v)
    {
        //Iniciar actividad en pantalla de Ejerciio DE ASOCIAR LETRAS
        Intent in = new Intent(this, EjercicioAsociacionletr.class);
        startActivity(in);

    }//Fin metodo select

}//Fin clase SubmenuAsocialObser
