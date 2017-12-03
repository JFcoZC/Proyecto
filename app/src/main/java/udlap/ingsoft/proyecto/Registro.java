package udlap.ingsoft.proyecto;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

/**
 * Created by Patty on 26/10/2017.
 */

//Inicio clase Registro
public class Registro extends AppCompatActivity
{
    //Atributos
    EditText name,email,age;
    String nombre,correo,edad;

    //Metodos
    protected void onCreate(Bundle savedInstance)
    {
        //Mostrar pantalla de xml correspondiente
        super.onCreate(savedInstance);
        setContentView(R.layout.registro);

        name = (EditText) findViewById(R.id.NombreReg);

        //Fin de la actividad
        Log.d("DDD","Fin actividad v1");
    }//Fin metodo onCreate
    //----------------------------------------------------------------------------------------------
    //Metodo que hace la alta en la base de datos
    public void Registrobd(View v)
    {
        //!!!NOTA: FALTA HACER AQUI UNA VALIDACAION DE QUE nombre o cualquier otra cadena que se reciba
        //no contenga caracteres como "|" o "$"
        nombre = name.getText().toString();
        String type = "Registro";

        //Instanciar objeto de la clase ValidarLoginBD pasandole el context de esta actividad(Login)
        ValidarLoginBD vlbd = new ValidarLoginBD(this);
        //Pasar multiples argumentos de TIPO STRING de la Activity actual para que sean ejecutados con los metodos
        //de la clase del objeto vlbd (Esto es posible ya que la clase ValidatLoginBD extiende de
        // Async Task)

        String resultado = "";
        int idfound = -1;
        try
        {
            //Guardar el resultado de la asyncronous task que se ha ejecutado
            resultado = vlbd.execute(type,nombre).get();
        }catch (InterruptedException e)
        {
            e.printStackTrace();
            //Fin catch 1
        }catch(ExecutionException ee)
        {
            ee.printStackTrace();
        }//Fin catch 2

        //Se econtro un usario si la ultima parte de la cadena resultado es un entero mayor a 0
        //Regresa posicion donde esta el :
        int startpos = resultado.indexOf(": ");
        //Se obtiene la última parte de la cadena resultado apartir del indice anterior
        idfound = Integer.parseInt(resultado.substring(startpos+2));

        //Si hay un usuaruo valido ir al menu principal
        if(idfound > 0)
        {
            Intent in = new Intent(this, MenuPrincipal.class);
            //Mandar id a actividad de menu principal
            in.putExtra("IDUSER",idfound);
            //Avisar que es la priemra vez que se accede al menu principal para que se mustre
            //el id que se le asigno
            in.putExtra("primeraVez", 1);
            startActivity(in);
            finish();
        }//Fin if 1

    }//Fin metodo Registrobd
    //----------------------------------------------------------------------------------------------

}//Fin clase Registro
