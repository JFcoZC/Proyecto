package udlap.ingsoft.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

/**
 * Clase que hace la validacion del login
 */

//Inicio clase Login
public class Login extends AppCompatActivity
{
    //Atributos
    EditText etid;

    //Metodos
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        etid = (EditText) findViewById(R.id.Idlog);

        //**Fin de la actividad
        Log.d("Fin Act:Log", "v10");

    }//Fin metodo onCreate
    //----------------------------------------------------------------------------------------------
    //Metodo Para cuando se busca verificar el login del id especificado
    public void onLogin(View v)
    {
        //Guardar el Id ingresado en el campo
        String inputid = etid.getText().toString();

        //Instanciar objeto de la clase ValidarLoginBD pasandole el context de esta actividad(Login)
        ValidarLoginBD vlbd = new ValidarLoginBD(this);

        //Variable para el tipo de operacion que se quiere ejecutar
        String type = "Entrar";

        //Pasar multiples argumentos de la Activity actual para que sean ejecutados con los metodos
        //de la clase del objeto vlbd (Esto es posible ya que la clase ValidatLoginBD extiende de
        // Async Task)
        String resultado = "";
        int idfound = -1;
        try
        {
            //Guardar el resultado de la asyncronous task que se ha ejecutado
            resultado = vlbd.execute(type,inputid).get();
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
            //Mandar id de usuario a actividad de menu principal
            in.putExtra("IDUSER",idfound);
            //Mandar clave de primera vez a menu principal
            //Avisar que NOOO es la priemra vez que se accede al menu principal
            in.putExtra("primeraVez", -1);
            startActivity(in);
        }//Fin if 1

    }//Fin metodo onLogin
    //----------------------------------------------------------------------------------------------
    //Metodo que lleva a pantalla de Registro
    public void onRegister(View v)
    {
        //Iniciar actividad
        Intent in = new Intent(this, Registro.class);
        startActivity(in);
        finish();

    }//Fin metodo onRegister
    //----------------------------------------------------------------------------------------------
    //Metod que lleva a Menu principal avisando que no se consultara informacion de la bds
    public void noConnect(View v)
    {
        Intent in = new Intent(this, MenuPrincipal.class);
        //Mandar id -1 indicando que no hay connexion a la bds
        in.putExtra("IDUSER",-1);
        startActivity(in);
        finish();

    }//Fin metodo no connect
    //----------------------------------------------------------------------------------------------
}//Fin clase Login
