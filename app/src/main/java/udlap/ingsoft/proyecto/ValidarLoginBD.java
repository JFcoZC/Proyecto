package udlap.ingsoft.proyecto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
    Clase que se encarga de la conexion a la BD para validar que el id ingresado en la clase Login
    sea un id que exista dentro de la BD

    AsyncTask enables proper and easy use of the UI thread. This class allows you to perform background
    operations and publish results on the UI thread without having to manipulate threads and/or handlers

    Referencias: https://developer.android.com/reference/android/os/AsyncTask.html
 */
//Inicio clase ValidarLoginBD <Tipo-parametros-.execute(),?,Tipo-doInBackground>
//significado de <>: https://stackoverflow.com/questions/9900834/how-to-pass-variables-in-and-out-of-asynctasks
public class ValidarLoginBD extends AsyncTask<String,Void,String>
{
    //Atributos
    Context ctxt;
    AlertDialog alert;

    //Constructor
    //Pasar contexto de la clase donde se crea el objeto para que pueda ser utilizada en otras clases
    //de Actividades; pasandole asi valores provenientes de otras actividades
    ValidarLoginBD(Context context)
    {
        //Pasarle al context de la clase donde fue generado el objeto y guardarlo en atributo
        ctxt = context;
    }//Fin constructor

    //Metodos
    //----------------------------------------------------------------------------------------------
    //2) Meotod invocado despues de onPreExecute usado para realizar calculos en el background ya
    //que estos calculos pueden tardar. Los pasos de la tarea Asyncrona son ejecutados aqui
    @Override
    protected String doInBackground(String... params)
    {
        //Obtener los valores que se mandaron como parametro en el execute recordando el orden
        //en que fueron pasados como parametro
        String type = params[0];
        String iduser = params[1];
        //Direccion ip del servidor+php file con login
        String login_url ="http://192.168.1.78/login.php";
        String regis_url ="http://192.168.1.78/register.php";
        String read_url = "http://192.168.1.78/read.php";
        String write_url = "http://192.168.1.78/write.php";

        if(type.equals("Entrar"))
        {
            //Crear objeto tipo URL preveniendo el uso de una URL mal formada
            try
            {
                //Crear objeto URL
                URL url = new URL(login_url);

                //Abrir la conexion http
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                //Nota: usar .getResponeseCode() solo para debug ya qye si se deja
                //sin comentar aparece excepcion de que la conexion ya fue establecida.
                //Log.d("DDMENSAJE1",""+httpURLConnection.getResponseCode());

                //Definir el tipo de request en este caso de tipo POST
                httpURLConnection.setRequestMethod("POST");
                //Para tipo POST usar setDoOutput & setDoInput
                //Poner setDoOutput en true  si se quieren enviar informacion
                httpURLConnection.setDoOutput(true);
                //Poner setDoInput en true  si se quieren recibir informacion
                httpURLConnection.setDoInput(true);

                //Asignar salida de datos para el canal del tipo de la conexion http
                //Objeto necesario para el bufferWritter
                OutputStream outputStream = httpURLConnection.getOutputStream();

                //Escribir en bufer utilizando conexion http definida anteriormente usando el tipo de datos
                //UTF-8
                //Buffer de escritura para mandar datos por la conecion http
                BufferedWriter buffwr = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                //Codificar una  URL a cadena junto con el id ingresado por el usuario como string
                //URLEncoder.encode(Cadena a hacer encide, String con tipo de encoding(UTF-8))
                String post_data = URLEncoder.encode("idusr","UTF-8")+"="+URLEncoder.encode(iduser,"UTF-8");

                //Escribir todos los datos de post en el bufferWriter
                buffwr.write(post_data);
                //Hacer flush(limpiarlo toodo lo que hay) del buffer(canal) y luego cerrarlo
                buffwr.flush();
                buffwr.close();
                //Cerrar el canal de salida tambien
                outputStream.close();

                //Nota: usar .getResponeseCode() solo para debug ya qye si se deja
                //sin comentar aparece excepcion de que la conexion ya fue establecida.
                //Log.d("DDMENSAJE2",""+httpURLConnection.getResponseCode());

                //OBTENER(GUARDAR) LA RESPUESTA DE LO QUE SE HALLA MANDADO Y ESCRITO CON LO ANTERIOR
                //El tipo de datos que se espera obtener es del tipo "iso-8859-1"

                //Preparar el buffer con el que se leera lo que arroje la conexion
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader buffre = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                //Hacer la lectura linea por linea
                //String que contiene todas las lineas guardadas
                String result = "";
                //Guarda el contenido de cada linea
                String line = "";

                //Mientras no se llegue al final del archivo delimitado por null
                while( (line = buffre.readLine()) != null)
                {
                    Log.d("DDMENSAJE",line);
                    //Ir concatenando cada linea no vacia
                    result+=line;
                }//Fin while 1

                //Cerrar el buffer de lectura una vez finalizada la lectura
                buffre.close();
                //Cerrar el canal de entrada
                inputStream.close();

                //Desconectar la conexion HTTP
                httpURLConnection.disconnect();

                //Regeresar la cadena obtenida
                return result;

            }//Fin try 1
            catch(MalformedURLException mue)
            {
                mue.printStackTrace();
                //Fin catch 1 para crear objeto URL
            }catch (IOException e)
            {
                e.printStackTrace();
            }//Fin catch 2 para abrir conexion http


        }//Fin if 1
        else
        {
            if( type.equals("Registro"))
            {

                //Crear objeto tipo URL preveniendo el uso de una URL mal formada
                try
                {
                    //Ya que el indice 0 tiene el type
                    String nombre = params[1];
                    Log.d("eee:", nombre);
                    //------

                    //Crear objeto URL con php de registro
                    URL url = new URL(regis_url);

                    //Abrir la conexion http
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                    //Nota: usar .getResponeseCode() solo para debug ya qye si se deja
                    //sin comentar aparece excepcion de que la conexion ya fue establecida.
                    //Log.d("DDMENSAJE1",""+httpURLConnection.getResponseCode());

                    //Definir el tipo de request en este caso de tipo POST
                    httpURLConnection.setRequestMethod("POST");
                    //Para tipo POST usar setDoOutput & setDoInput
                    //Poner setDoOutput en true  si se quieren enviar informacion
                    httpURLConnection.setDoOutput(true);
                    //Poner setDoInput en true  si se quieren recibir informacion
                    httpURLConnection.setDoInput(true);

                    //Asignar salida de datos para el canal del tipo de la conexion http
                    //Objeto necesario para el bufferWritter
                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    //Escribir en bufer utilizando conexion http definida anteriormente usando el tipo de datos
                    //UTF-8
                    //Buffer de escritura para mandar datos por la conecion http
                    BufferedWriter buffwr = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                    //Codificar una  URL a cadena junto con el id ingresado por el usuario como string
                    //URLEncoder.encode(Cadena a hacer encide, String con tipo de encoding(UTF-8))
                    String post_data = URLEncoder.encode("NOMBRE","UTF-8")+"="+URLEncoder.encode(nombre,"UTF-8");

                    //Escribir todos los datos de post en el bufferWriter
                    buffwr.write(post_data);
                    //Hacer flush(limpiarlo toodo lo que hay) del buffer(canal) y luego cerrarlo
                    buffwr.flush();
                    buffwr.close();
                    //Cerrar el canal de salida tambien
                    outputStream.close();

                    //Nota: usar .getResponeseCode() solo para debug ya qye si se deja
                    //sin comentar aparece excepcion de que la conexion ya fue establecida.
                    //Log.d("DDMENSAJE2",""+httpURLConnection.getResponseCode());

                    //OBTENER(GUARDAR) LA RESPUESTA DE LO QUE SE HALLA MANDADO Y ESCRITO CON LO ANTERIOR
                    //El tipo de datos que se espera obtener es del tipo "iso-8859-1"

                    //Preparar el buffer con el que se leera lo que arroje la conexion
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader buffre = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                    //Hacer la lectura linea por linea
                    //String que contiene todas las lineas guardadas
                    String result = "";
                    //Guarda el contenido de cada linea
                    String line = "";

                    //Mientras no se llegue al final del archivo delimitado por null
                    while( (line = buffre.readLine()) != null)
                    {
                        Log.d("DDMENSAJE",line);
                        //Ir concatenando cada linea no vacia
                        result+=line;
                    }//Fin while 1

                    //Cerrar el buffer de lectura una vez finalizada la lectura
                    buffre.close();
                    //Cerrar el canal de entrada
                    inputStream.close();

                    //Desconectar la conexion HTTP
                    httpURLConnection.disconnect();

                    //Regeresar la cadena obtenida
                    return result;

                }//Fin try 1
                catch(MalformedURLException mue)
                {
                    mue.printStackTrace();
                    //Fin catch 1 para crear objeto URL
                }catch (IOException e)
                {
                    e.printStackTrace();
                }//Fin catch 2 para abrir conexion http

            }//Fin if 2
            else if(type.equals("LeerBD"))
            {
                //Crear objeto tipo URL preveniendo el uso de una URL mal formada
                try
                {
                    //Ya que el indice 0 tiene el type
                    //Indice 1 contiene el id del usuario que se busca leer la info de la base de
                    //datos
                    String idtosearch = params[1];
                    //------

                    //Crear objeto URL con php de registro
                    URL url = new URL(read_url);

                    //Abrir la conexion http
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                    //Nota: usar .getResponeseCode() solo para debug ya qye si se deja
                    //sin comentar aparece excepcion de que la conexion ya fue establecida.
                    //Log.d("DDMENSAJE1",""+httpURLConnection.getResponseCode());

                    //Definir el tipo de request en este caso de tipo POST
                    httpURLConnection.setRequestMethod("POST");
                    //Para tipo POST usar setDoOutput & setDoInput
                    //Poner setDoOutput en true  si se quieren enviar informacion
                    httpURLConnection.setDoOutput(true);
                    //Poner setDoInput en true  si se quieren recibir informacion
                    httpURLConnection.setDoInput(true);

                    //Asignar salida de datos para el canal del tipo de la conexion http
                    //Objeto necesario para el bufferWritter
                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    //Escribir en bufer utilizando conexion http definida anteriormente usando el tipo de datos
                    //UTF-8
                    //Buffer de escritura para mandar datos por la conecion http
                    BufferedWriter buffwr = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                    //Codificar una  URL a cadena junto con el id ingresado por el usuario como string
                    //URLEncoder.encode(Cadena a hacer encide, String con tipo de encoding(UTF-8))
                    String post_data = URLEncoder.encode("IDTOSEARCH","UTF-8")+"="+URLEncoder.encode(idtosearch,"UTF-8");

                    //Escribir todos los datos de post en el bufferWriter
                    buffwr.write(post_data);
                    //Hacer flush(limpiarlo toodo lo que hay) del buffer(canal) y luego cerrarlo
                    buffwr.flush();
                    buffwr.close();
                    //Cerrar el canal de salida tambien
                    outputStream.close();

                    //Nota: usar .getResponeseCode() solo para debug ya qye si se deja
                    //sin comentar aparece excepcion de que la conexion ya fue establecida.
                    //Log.d("DDMENSAJE2",""+httpURLConnection.getResponseCode());

                    //OBTENER(GUARDAR) LA RESPUESTA DE LO QUE SE HALLA MANDADO Y ESCRITO CON LO ANTERIOR
                    //El tipo de datos que se espera obtener es del tipo "iso-8859-1"

                    //Preparar el buffer con el que se leera lo que arroje la conexion
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader buffre = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                    //Hacer la lectura linea por linea
                    //String que contiene todas las lineas guardadas
                    String result = "";
                    //Guarda el contenido de cada linea
                    String line = "";

                    //Mientras no se llegue al final del archivo delimitado por null
                    while( (line = buffre.readLine()) != null)
                    {
                        Log.d("DDMENSAJE",line);
                        //Ir concatenando cada linea no vacia
                        result+=line;
                    }//Fin while 1

                    //Cerrar el buffer de lectura una vez finalizada la lectura
                    buffre.close();
                    //Cerrar el canal de entrada
                    inputStream.close();

                    //Desconectar la conexion HTTP
                    httpURLConnection.disconnect();

                    //Regeresar la cadena obtenida
                    return result;

                }//Fin try 1
                catch(MalformedURLException mue)
                {
                    mue.printStackTrace();
                    //Fin catch 1 para crear objeto URL
                }catch (IOException e)
                {
                    e.printStackTrace();
                }//Fin catch 2 para abrir conexion http

            }//Else 2 if 3
            else if (type.equals("EscribirBD"))
            {
                //Usuario temporal para poder llamar metodo de separacion de cadenas en un arreglo
                //de cadenas
                Usuario temp = new Usuario();

                //Parametros/cadenas recibidas
                String id = params[1];
                String tim = params[2];
                String gs = params[3];
                String ca = params[4];
                //Ejercicios
                String[] e1 = temp.separar(params[5],"|");
                String[] e2 = temp.separar(params[6],"|");
                String[] e3 = temp.separar(params[7],"|");
                String[] e4 = temp.separar(params[8],"|");
                String[] e5 = temp.separar(params[9],"|");
                String[] e6 = temp.separar(params[10],"|");

                //Crear objeto tipo URL preveniendo el uso de una URL mal formada
                try
                {

                    //Crear objeto URL con php de registro
                    URL url = new URL(write_url);

                    //Abrir la conexion http
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                    //Nota: usar .getResponeseCode() solo para debug ya qye si se deja
                    //sin comentar aparece excepcion de que la conexion ya fue establecida.
                    //Log.d("DDMENSAJE1",""+httpURLConnection.getResponseCode());

                    //Definir el tipo de request en este caso de tipo POST
                    httpURLConnection.setRequestMethod("POST");
                    //Para tipo POST usar setDoOutput & setDoInput
                    //Poner setDoOutput en true  si se quieren enviar informacion
                    httpURLConnection.setDoOutput(true);
                    //Poner setDoInput en true  si se quieren recibir informacion
                    httpURLConnection.setDoInput(true);

                    //Asignar salida de datos para el canal del tipo de la conexion http
                    //Objeto necesario para el bufferWritter
                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    //Escribir en bufer utilizando conexion http definida anteriormente usando el tipo de datos
                    //UTF-8
                    //Buffer de escritura para mandar datos por la conecion http
                    BufferedWriter buffwr = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                    //Codificar una  URL a cadena junto con el id ingresado por el usuario como string
                    //URLEncoder.encode(Cadena a hacer encide, String con tipo de encoding(UTF-8))
                    String post_data = URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"
                                       +URLEncoder.encode("TIME","UTF-8")+"="+URLEncoder.encode(tim,"UTF-8")+"&"
                                       +URLEncoder.encode("GS","UTF-8")+"="+URLEncoder.encode(gs,"UTF-8")+"&"
                                       +URLEncoder.encode("CA","UTF-8")+"="+URLEncoder.encode(ca,"UTF-8");

                    //***CODIFICACION EJERCICIOS CON CLAVES 1-6 porque asi estan los $_post del PHP***
                    //Codificacion ejercicio 1
                    String ejeruno = URLArrayEncoder(e1,1);
                    //Codificacion ejercicio 2
                    String ejerdos = URLArrayEncoder(e2,2);
                    //Codificacion ejercicio 3
                    String ejertres = URLArrayEncoder(e3,3);
                    //Codificacion ejercicio 4
                    String ejercuatro = URLArrayEncoder(e4,4);
                    //Codificacion ejercicio 5
                    String ejercinco = URLArrayEncoder(e5,5);
                    //Codificacion ejercicio 6
                    String ejerseis = URLArrayEncoder(e6,6);

                    //FALTA CONCATENAR TODOS LOS EJRCICIOS Y ACABAR EL PHP
                    post_data = post_data+ejeruno+ejerdos+ejertres+ejercuatro+ejercinco+ejerseis;

                    //Escribir todos los datos de post en el bufferWriter
                    buffwr.write(post_data);
                    //Hacer flush(limpiarlo toodo lo que hay) del buffer(canal) y luego cerrarlo
                    buffwr.flush();
                    buffwr.close();
                    //Cerrar el canal de salida tambien
                    outputStream.close();

                    //Nota: usar .getResponeseCode() solo para debug ya qye si se deja
                    //sin comentar aparece excepcion de que la conexion ya fue establecida.
                    //Log.d("DDMENSAJE2",""+httpURLConnection.getResponseCode());

                    //OBTENER(GUARDAR) LA RESPUESTA DE LO QUE SE HALLA MANDADO Y ESCRITO CON LO ANTERIOR
                    //El tipo de datos que se espera obtener es del tipo "iso-8859-1"

                    //Preparar el buffer con el que se leera lo que arroje la conexion
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader buffre = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                    //Hacer la lectura linea por linea
                    //String que contiene todas las lineas guardadas
                    String result = "";
                    //Guarda el contenido de cada linea
                    String line = "";

                    //Mientras no se llegue al final del archivo delimitado por null
                    while( (line = buffre.readLine()) != null)
                    {
                        Log.d("DDMENSAJE",line);
                        //Ir concatenando cada linea no vacia
                        result+=line;
                    }//Fin while 1

                    //Cerrar el buffer de lectura una vez finalizada la lectura
                    buffre.close();
                    //Cerrar el canal de entrada
                    inputStream.close();

                    //Desconectar la conexion HTTP
                    httpURLConnection.disconnect();

                    //Regeresar la cadena obtenida
                    return result;

                }//Fin try 1
                catch(MalformedURLException mue)
                {
                    mue.printStackTrace();
                    //Fin catch 1 para crear objeto URL
                }catch (IOException e)
                {
                    e.printStackTrace();
                }//Fin catch 2 para abrir conexion http


            }//Fin ELse 3 if 4 (else if)

        }//Fin else 1


        return null;
    }//Fin metodo doInBackgorund
    //----------------------------------------------------------------------------------------------
    //1) Metodo invocado en el thread de la UI antes de que la tarea sea ejecutada. Metodo comunmente
    //usado para hacer el setup(preparacion) de la tarea
    @Override
    protected void onPreExecute()
    {
        //Crear una ventana de alerta en la activity correspondiente al context de ctxt
        alert = new AlertDialog.Builder(ctxt).create();
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
    }//Fin metodo onPreExecute
    //----------------------------------------------------------------------------------------------
    //4) Invocado en la UI despues de que los calculos en el backgorund finalizan. Los resultados obtenidos
    //de los calculos son pasados como parametros a este paso/metodo
    @Override
    protected void onPostExecute(String result)
    {
        //verificar que no se regrese un "frame" de datos ya que se sabe que el ultimo caracter
        //es un >
        //Concatenar el ultimo caracter
        if( ">".equals( ""+result.charAt(result.length()-1) ) )
        {
            //Decidir si hay que poner la lectura y escritura en esta AsyncTask o en otra clase!!

        }//Fin if 1
        else
        {
            //Se econtro un usario si la ultima parte de la cadena resultado es un entero mayor a 0
            //Regresa posicion donde esta el :
            int startpos = result.indexOf(": ");
            //Se obtiene la última parte de la cadena resultado apartir del indice anterior
            int idencontrado = Integer.parseInt(result.substring(startpos+2));

            //Si es un id valido (mayor que 0 ) no enseñar en pantalla de login el mesnaje de alerta.
            //Solo eneseñar mesnaje de alertas cuando se trate de que no se ecnontro
            if(idencontrado <= 0)
            {
                //Mostrar como mensaje lo que se haya leido al final de doInBackground
                alert.setMessage(result);
                alert.show();
            }//Fin if 1

        }//Fin else 1

    }//Fin metodo onPostExecute
    //----------------------------------------------------------------------------------------------
    //3) Metodo usado para  desplegar cualquier forma de progreso en l Interfaz mientras los calculos
    //se siguen realizando. Usado para animar el progreso de un text field
    @Override
    protected void onProgressUpdate(Void... values)
    {
        super.onProgressUpdate(values);
    }//Fin metodo onProgressUpdate
    //----------------------------------------------------------------------------------------------
    //Metodo que dado un arreglo y el numero de ejrcicio[1-6] de strings codifica un URL a cadena
    // para que esa cadena pueda ser
    //escrita en el php para los queries de las consultas y comandos de la base de datos en PHP
    String URLArrayEncoder(String[] arr, int cveEx)
    {
        Log.d("eeeefu length",arr.length+"");
        String codificado = "";
        //Variable usuario para determinar elmaximo de niveles por ejercicio
        Usuario temp = new Usuario();

        try
        {
            //El primer elemento se hace de forma manual para que en el ultimo recorrido no quede una '&' sobrando
            //Inicar con union a lo que se va apegar
            codificado = "&"+URLEncoder.encode("E"+cveEx+"_"+0+"", "UTF-8") + "=" + URLEncoder.encode(arr[0], "UTF-8");

            for (int i = 1; i < temp.MAXEXER; i++)
            {
                Log.d("eeeefu","E"+cveEx+"_"+i+"");
                                                                //E1_1,...
                codificado = codificado+"&"+URLEncoder.encode("E"+cveEx+"_"+i+"", "UTF-8") + "=" + URLEncoder.encode(arr[i], "UTF-8");
            }//Fin for 1

        }//Fin catch 1
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }//Fin catch 2


        return codificado;
    }
    //----------------------------------------------------------------------------------------------

}//Fin clase ValidarLoginBD
