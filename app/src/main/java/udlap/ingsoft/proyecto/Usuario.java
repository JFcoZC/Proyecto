package udlap.ingsoft.proyecto;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ExecutionException;

/**
 * Clase que contiene los atributos que conforman a un Usuario; contiene la información
 * que se mostrara en el menu de estadisticas para mostrar su progreso
 * José Francisco Zerón Cabrera
 */

//Inicio clase Usuaqrio
public class Usuario
{
    //Variables globales
    //Maximo de campos para metodo separar()
    //REVISAR
    int MAXFIELDS = 300;

    //Maximo de niveles por ejercicio. Ningun ejercicio tendra mas de 30 niveles
    int MAXEXER = 30;

    //Numero de ejercicios por nivel/tipos de ejercicios
    int NUMEXLONE = 0;
    int NUMEXLTWO = 0;
    int NUMEXLTREE = 6;
    int NUMEXLFOUR = 0;
    int NUMEXLFIVE = 3;
    int NUMEXLSIX = 3;

    //Atributos
    //byte[] borradol = new byte[1];      //1 byte
    String name;         //30bytes
    int id;                             //4 bytes
    float time;                         //4 bytes
    float GeneralScore;                 //4 bytes
    float[] levelsProgress = {0,0,0};   //12 bytes

    //Contador de accesos usuario
    int countaccess = 0;                //4 bytes
                                        //4 bytes*NUMACCESS*3 = 120BYTES
                                        //4*NUMEX1 + 4*NUMEX2 + 4*NUMEX3

    //Arreglo de arreglos de tipo float para indicar el progreso en los 6 tipos de ejercicios de la app (refiriendose
    //al progreso como la obtención del puntaje maximo/realizacion corrrecta de un nivel en particular)
    float[][] exercisesprogress = {new float[MAXEXER], new float[MAXEXER], new float[MAXEXER], new float[MAXEXER], new float[MAXEXER], new float[MAXEXER]};

    //++++++++++Constructores++++++++++++++++
    public Usuario() {}//Fin costructor utilizado para la escritura en BDs

    public Usuario(int id, Context c)
    {
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //++++Invocar la lectura de la base de datos para el usuario actual y recuperarla en una cadena++++
        //Instanciar objeto de la clase ValidarLoginBD pasandole el context de esta actividad(Login)
        ValidarLoginBD vlbd = new ValidarLoginBD(c);

        //Variable para el tipo de operacion que se quiere ejecutar
        String type = "LeerBD";

        //Pasar multiples argumentos de la Activity actual para que sean ejecutados con los metodos
        //de la clase del objeto vlbd (Esto es posible ya que la clase ValidatLoginBD extiende de
        // Async Task)
        String datosuser = "";
        try
        {
            //Guardar el resultado de la lectura de la bds del usuario con id obtenido del login
            //el id debe pasarse como cadena
            datosuser = vlbd.execute(type,id+"").get();
        }catch (InterruptedException e)
        {
            e.printStackTrace();
            //Fin catch 1
        }catch(ExecutionException ee)
        {
            ee.printStackTrace();
        }//Fin catch 2
        //++++++++++++++++ FIN LECTURA DE BDs+++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //Inicalizar datos con base en la infor recuperada de la base de datos que se encuentra
        //en datosuser en forma de cadena/frame
        setinfoFromDb(datosuser);

    }
    //++++++++Fin constructor Usuario++++++

    //Getters
    public String getName() { return name; }
    public int getId(){return id;}
    public int getNumAccs() {return countaccess;}
    public float getTime() {return time; }
    public float getScore() {return GeneralScore; }

    public float getProgresslvlone() {return levelsProgress[0]; }
    public float getProgresslvltwo() {return levelsProgress[1]; }
    public float getProgresslvltree() {return levelsProgress[2]; }

    //--
    public float[] getScoreslvltwo()
    {
        float[] scoresEx = new float[NUMEXLTWO];

        for(int i = 0; i < NUMEXLTWO; i++)
        {
            scoresEx[i] = exercisesprogress[1][i];
        }//Fin for 1

        return scoresEx;
    }//Fin getScoreslvltwo
    //--
    //Regresar la puntuacion actual de cada ejercicio del nivel (ASOCIACION SILABICA)
    public float[] getScoreslvltree()
    {
        float[] scoresEejrcicios = new float[NUMEXLTREE];

        for(int i = 0; i < NUMEXLTREE; i++)
        {
            scoresEejrcicios[i] = exercisesprogress[2][i];
        }//Fin for 1

        return scoresEejrcicios;
    }//Fin getScoreslvltree
    //--
    //Regresar la puntuacion actual de cada ejercicio del nivel CINCO(LECTURAS)
    public float[] getScoreslvlfive()
    {
        float[] scoresex = new float[NUMEXLFIVE];

        for(int i = 0; i < NUMEXLFIVE; i++)
        {
            scoresex[i] = exercisesprogress[4][i];
        }//Fin for 1

        return scoresex;

    }//Fin metodo getScoreslvlFive
    //--
    //Regresar la puntuacion actual de cada ejercicio del nivel SEIS (ORTOGRAFIA)
    public float[] getScoreslvlsix()
    {
        float[] scoresex = new float[NUMEXLSIX];

        for(int i = 0; i < NUMEXLSIX; i++)
        {
            scoresex[i] = exercisesprogress[5][i];
        }//Fin for 1

        return scoresex;

    }//Fin getScoreslvlFive

    //INICIO DE LOS METODOS
    //----------------------------------------------------------------------------------------------
    //METODO QUE SOBREESCRIBE INFRORMACION ACTUAL DEL USUARIO EN LA BDs
    void updtadeDataDB(Context c)
    {
        String ident = ""+id;
        String tiemp = String.valueOf(time);
        String gs = String.valueOf(GeneralScore);
        String ca = ""+countaccess;
        //Atributo NumAcccess quedo vacio

        //EJERCICIOS
        //Arreglo de floats y caracter de separacion de cada campo
        String euno = toStringSequence("|",exercisesprogress[0]);
        String edos = toStringSequence("|",exercisesprogress[1]);
        String etres = toStringSequence("|",exercisesprogress[2]);
        String efour = toStringSequence("|",exercisesprogress[3]);
        String efive = toStringSequence("|",exercisesprogress[4]);
        String esix = toStringSequence("|",exercisesprogress[5]);

        //Tipo de operacion que se desea ejecutar
        String type = "EscribirBD";

        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //Invocar la escritura en la BDs
        //Instanciar objeto tipo ValidarLoginBD dandole el context de la activity donde se ejecuta
        ValidarLoginBD vlbd = new ValidarLoginBD(c);

        //Pasar multiples argumentos de la Activity actual para que sean ejecutados con los metodos
        //de la clase del objeto vlbd (Esto es posible ya que la clase ValidatLoginBD extiende de
        // Async Task)
        vlbd.execute(type,ident,tiemp,gs,ca,euno,edos,etres,efour,efive,esix);


        Log.d("eeeeuno:",euno);

    }//Fin metodo updateDataDB
    //---------------------------------------------------------------
    //Calcular el score actual en los ejercicios de 1 nivel especifico
    float ScoreExcercise( float scores[])
    {
        float actualscore = 0.0f;

        //Calcular Score actual
        //Score actual
        for(int i = 0; i < scores.length; i++)
        {
            actualscore = actualscore+scores[i];

        }//Fin for 1

        return actualscore;

    }//Fin metodo Actual score
    //-----------------------------------------------------------------------------------
    //Calcular el promedio de aprovechamiento acutal en los ejercicios de un nivel
    //Dado el arreglo de ejercicios y el numero total de ejercicios que hay creados para ese tipo
    //de ejercicio
    float PromSkillsEx(float scores[], int totalex)
    {


        //Calcular el score actual del usuario
        float actualscore = ScoreExcercise(scores);
        float promedio = 0.0f;

        //Maximo score que se puede alcanzar en el nivel (si se obtiene maxima puntuacion en todos
        // los ejercicios del nivel)
        float maxscore = 3*totalex;

        //Si un tipo de ejercicio no posee ningun nivel regresar promedio de 0 automatico porque
        //si no el maxscore queda como 0 y al ponerlo como denominador hace una operacion que no
        //es posible

        if(totalex > 0)
        {
            //Hacer regla de 3 con 50% ya que cada nivel tiene 2 ejercicios
            promedio =  (50.0f* actualscore)/maxscore;

        }//Fin if 1

        //Si el numero de ejrcicios es 0 o menor(cosa que no debe pasar pero se prevee por algun
        // error de dedo) regresa pormedio de 0 como cuando se declaro la variable

        return promedio;

    }//Fin metodo scoreSilabicEx
    //---------------------------------------------------------------
    //Obtener el score general del usuario de los tres niveles actual; y si este es mayor que el
    //que tiene el usario entonces guardarlo en la variable del usuario
    public void MaxScore()
    {
        //Obtener el score individual en cada Tipo de ejercicio
        float score1lv = ScoreExcercise(exercisesprogress[0]);
        float score2lv = ScoreExcercise(exercisesprogress[1]);
        float score3lv = ScoreExcercise(exercisesprogress[2]);
        float score4lv = ScoreExcercise(exercisesprogress[3]);
        float score5lv = ScoreExcercise(exercisesprogress[4]);
        float score6lv = ScoreExcercise(exercisesprogress[5]);

        //Obtener el score de los 6 tipos de ejercicios
        float totalscore = score1lv + score2lv + score3lv+ score4lv + score5lv + score6lv;

        //Si el score calculado es mayor entonces guardarlo como el nuevo score del usuario
        if(totalscore > GeneralScore)
        {
            GeneralScore = totalscore;
        }//Fin if 1

    }//Fin metodo MaxScore
    //---------------------------------------------------------------
    //Calcular el progreso actual en un nivel especifico(progreso: si para un ejercicio se alcanza
    //la puntuacion máxima marcarlo como completado; si no quedarse con lo maximo de puntuacion al
    // canzada) Parametros: progreso de nivel actual, numero de nivel y numero de ejrcicios creados para ese nivel
    public float LevelProgress(float[] currentproglvl, int lvlnumber, int numberex)
    {
        //Verificar que el arreglo de scores a insertar no sea superior a 30
        if(currentproglvl.length < MAXEXER)
        {
            //El numero de ejercicios del arreglo del que se vana a actualizar posee un nuemro
            //de eejrcicios que no supera la capacidad del arrreglo exerciseprogress

            //Hacer checado de progreso para cada ejercicio del nivel
            for (int i = 0; i < currentproglvl.length; i++) {
                //Quedarse solamente con la puntuacion maxima obtenida en el nivel correspondiente
                if (currentproglvl[i] > exercisesprogress[lvlnumber][i]) {
                    //Guardar el progreso actual
                    exercisesprogress[lvlnumber][i] = currentproglvl[i];
                }//Fin if  E

            }//Fin for 1

        }//FIN IF 1
        else
        {
            Log.d("eee:","ERROR: Arreglo de scores a actualizar tiene un tamaño no valido");
        }//Fin else 1

        //Calcular el nivel de aprovechamiento en % del progreso en el nivel
        return PromSkillsEx(exercisesprogress[lvlnumber],numberex);

    }//Fin metodo LevelProgress
    //---------------------------------------------------------------
    //Obtener el nivel de progreso de los tres niveles con base en los datos actuales de los 6 ejericios
    public void FinalLevelsProgress()
    {
        //Obtener el progreso en % del nivel 1 50% ABECEDARIO 50% ASOCIACION DE LETRAS
        levelsProgress[0] = ( PromSkillsEx(exercisesprogress[0],NUMEXLONE) ) + (PromSkillsEx(exercisesprogress[1],NUMEXLTWO) );

        //Obtener el progreso en % del nivel 2 50% JUEO SILABAS 50% SOPA DE LETRAS
        levelsProgress[1] = ( PromSkillsEx(exercisesprogress[2],NUMEXLTREE) ) + ( PromSkillsEx(exercisesprogress[3],NUMEXLFOUR) ) ;

        //Obtener el progreso en % del nivel 3 50% LECTURA ANIMADA 50% JUEGO ORTOGRAFIA
        levelsProgress[2] = ( PromSkillsEx(exercisesprogress[4],NUMEXLFIVE) )+(PromSkillsEx(exercisesprogress[5],NUMEXLSIX) );

        //---------------------
        //Log.d("eeee:","Promedio ejercicio 3 "+PromSkillsEx(exercisesprogress[2],NUMEXLTREE));
        //Log.d("eeee:","Promedio ejercicio 4 "+PromSkillsEx(exercisesprogress[3],NUMEXLFOUR));
        //Log.d("eeee:","Promedio nivel explorador "+levelsProgress[1]);
        //---------------------

    }//Fin metodo FinalLevelsProgress
    //---------------------------------------------------------------------------------------------
    //Metodo que actualiza toda la estadistica con base en los datos actuales del usuario guardados
    //en sus atributos; el único dato que recibe es el tiempo actual de uso del juego

    //Hacer el calculo de todos los datos de uso del usuario (%Completado de nivel,TiempoUso,
    // MaxScore, %progreso de habilidades en ultimos 10 accesos)
    public void calcularUserData(long curtime)
    {
        //%Completado de niveles(ADAPTARLO A CONIDERAR 6 EJERCICIOS 2 POR NIVEL)
        FinalLevelsProgress();

        //Calcular tiempo de uso convertido a minutos
        time = time+(curtime/60000);

        //MaxScore considerando la puntuacion de todos los niveles de los 6 tipos de ejercicios
        MaxScore();

        //Incremento el contador de accesos del usuario para el siguiente acceso se hace en login.php


    }//Fin metodo UserData
    //----------------------------------------------------------------------------------------------
    //Metodo que asigna en usuario actual los datos que estan en una string que fueron recuperados
    //de la base de datos
    public void setinfoFromDb(String datos)
    {
        //Limite de la primera parte
        int ifirst = datos.indexOf("->"); //Regresa pos donde esta "-"
        //Limite parte dos
        int isecond = datos.indexOf("|<fin>"); //Regresa parte donde esta "|"

        //Separar frame en dos partes datos usuario y datos ejercicio
        String suser = datos.substring(0,ifirst); //Cadenas que incluye ultimo $
        String sexer = datos.substring(ifirst+2, isecond); //Cadena antes de ultimo | (ya no lo cuenta)

        Log.d("eee:",suser);
        Log.d("eee:",sexer);

        //Poner datos de frame en atributos corresponidentes de objeto de clase Usuario
        setDataUser(suser);
        setDataExcercises(sexer);

    }//Fin metodo
    //----------------------------------------------------------------------------------------------
    //Metodo que dado una cadena y un caracter de separacion regresa un arreglo de Strings en donde
    //en cada campo esta una parte de la subcadena antes de encontrar un caracter de separacion
    String[] separar(String cadena, String csepar)
    {
        String[] resultado = new String[MAXFIELDS]; //MAXFIELDS = aALMACENAR HASTA 300 CAMPOS
        int ipos = 0;
        //Indice para ir guardando resultado en la posicion del arreglo rsultante ya que este
        //avanza de forma diferente al indice ipos que bussca la posicion del proximo caracter
        //de separacion
        int inp = 0;

        //Mientras la cadena no este vacia
        while(cadena.length() != 0)
        {
            //POSICION DEL CARACTER DE SEPARCAION
            ipos = cadena.indexOf(csepar);


            //----------------------------------------------------
            Log.d("eee:","-------");
            Log.d("eee","posicion de "+csepar+":"+ipos);
            Log.d("eee:","length cadena:"+cadena.length());
            Log.d("eee","cadena: "+cadena);
            Log.d("eee:","-------");
            //----------------------------------------------------

            //Si la posicion del caracter de separacion es mayor al length de la cadena o regresa -1
            //(porque eso regresa cuando no encuentra el caracter deseado)
            if(ipos > cadena.length() || ipos == -1 )
            {
                //CASO ESPECIAL FIN CADENA
                //Guardar valor actual de la cadena en arreglo resultante
                resultado[inp++] = cadena;
                //Si es una posicion ya no validea en la cadena poner la cadena de tamaño 0
                //para que la siguiente iteracion ya no se guarde nada FORZAR FIN WHILE
                cadena = "";

            }//Fin if 1
            else
            {
                //La posicion donde paraece el caracter de separacion no es superior al length
                //de la cadena (Caso donde tofdavia pueden quedar mas caracteres de separacion
                // en la cadena)
                resultado[inp++] = cadena.substring(0,ipos);

                //Actualizar contenido cadena(cortar) con la subcadena que empieza con el caracter
                //posterior a la separacion (PROCURAR QUE EL LENGTH DE caracter de SEPARACION SEA 1)
                //si no va a haber problemas
                cadena = cadena.substring(ipos+1, cadena.length());

            }//Fin else 1
        }//Fin while

        return resultado;

    }//Fin metodo separar
    //----------------------------------------------------------------------------------------------
    //Metodo que dada la priemra parte del frame guarda los datos de usuario en los atributos corres
    //podndientes del objeto Usuario
    void setDataUser(String firspartfr)
    {
        String[] datos = separar(firspartfr,"$");

        //Id
        id = Integer.parseInt(datos[0]);
        //Nombre
        name = datos[2];
        //Tiempo
        time = Float.parseFloat(datos[3]);
        //GeneralScore
        GeneralScore = Float.parseFloat(datos[4]);
        //Numero de accessos
        countaccess = Integer.parseInt(datos[5]);

    }//Fin metodo setDataUser
    //----------------------------------------------------------------------------------------------
    //Metodo que pone la puntuacion de todos los ejercicios del usuario en la matriz de excercise
    //porgress
    void setDataExcercises(String secondpart)
    {
        String datos[] = separar(secondpart,"|");

        int ilvl1 = 3;
        int ilvl2 = 36;
        int ilvl3 = 69;
        int ilvl4 = 102;
        int ilvl5 = 135;
        int ilvl6 = 168;

        //30 ejercicios
        for(int i = 0; i < MAXEXER; i++)
        {
            //Puntuacion ejercicios de Primer tipo [3-32]
            exercisesprogress[0][i] = Float.parseFloat(datos[ilvl1]);

            //Puntuacion ejercicios de Segundo tipo [36-65]
            exercisesprogress[1][i] = Float.parseFloat(datos[ilvl2]);

            //Puntuacion ejercicios de Tercer tipo [69-98]
            exercisesprogress[2][i] = Float.parseFloat(datos[ilvl3]);

            //Puntuacion ejercicios de Cuarto tipo [102-131]
            exercisesprogress[3][i] = Float.parseFloat(datos[ilvl4]);

            //Puntuacion ejercicios de Quinto tipo [135-164]
            exercisesprogress[4][i] = Float.parseFloat(datos[ilvl5]);

            //Puntuacion ejercicios de Sexto tipo [168-197]
            exercisesprogress[5][i] = Float.parseFloat(datos[ilvl6]);

            //Aumentar contadores
            ilvl1++;
            ilvl2++;
            ilvl3++;
            ilvl4++;
            ilvl5++;
            ilvl6++;

        }//Fin for 1
    }//Fin metodo setDataExcercises
    //----------------------------------------------------------------------------------------------
    //Metodo que dado un arreglo de Floats los convierte en una String donde cada campo del arreglo
    //Es separado por el caracter que es especificado
    String toStringSequence(String character, float[] datos)
    {
        String resultado = "";

        for(int i = 0; i < datos.length; i++)
        {
            //Concatenar lo que va en la cadena mas elproximo caracter seguido del elemento
            //de separacion
            resultado = resultado+String.valueOf(datos[i])+character;
        }//Fin for 1

        return resultado;

    }//FIn metodo toStringSequence
}//Fin clase Usuario
