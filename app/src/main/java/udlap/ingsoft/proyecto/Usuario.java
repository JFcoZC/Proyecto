package udlap.ingsoft.proyecto;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Clase que contiene los atributos que conforman a un Usuario; contiene la información
 * que se mostrara en el menu de estadisticas para mostrar su progreso
 * José Francisco Zerón Cabrera
 */

//Inicio clase Usuaqrio
public class Usuario
{
    //Variables globales
    //Numero de accesos que se van a tomar en cuenta para sacar el promedio del aprovechamiento de
    //las habilidades del usuario
    int NUMACCESS  = 10;
    //Numero de ejercicios por nivel/tipos de ejercicios
    int NUMEXLONE = 0;
    int NUMEXLTWO = 6;
    int NUMEXLTREE = 0;

    //Atributos
    byte[] borradol = new byte[1];      //1 byte
    byte[] name = new byte[30];         //30bytes
    int id;                             //4 bytes
    float time;                         //4 bytes
    float GeneralScore;                 //4 bytes
    float[] levelsProgress = {0,0,0};   //12 bytes

    //Contador de accesos usuario
    int countaccess = 0;                //4 bytes
                                        //4 bytes*NUMACCESS*3 = 120BYTES
                                        //4*NUMEX1 + 4*NUMEX2 + 4*NUMEX3

    //Arreglo de arreglos de tipo float para indicar el aprovechamiento de las 3 habilidades a lo largo de
    //10 accesos a la app
    float[][] skills = {new float[NUMACCESS] , new float[NUMACCESS], new float[NUMACCESS]};
    //Arreglo de arreglos de tipo float para indicar el progreso en los 3 niveles de la app (refiriendose
    //al progreso como la obtención del puntaje maximo/realizacion corrrecta de un ejercicio)
    float[][] progreso = {new float[NUMEXLONE], new float[NUMEXLTWO], new float[NUMEXLTREE]};

    //++++++++++Constructores++++++++++++++++
    public Usuario() {}

    public Usuario(int cve, String nombre)
    {
        id = cve;

        //Si el nombre del usuario es mayor a 30 caracteres este aparecera cortado
        for( int i = 0; i < 30 && i < nombre.getBytes().length; i++ )
            name[i] = nombre.getBytes()[i];

        //Tiempo inicial  = 0
        time = 0;
        //Score inical = 0
        GeneralScore = 0;
    }
    //++++++++Fin constructor Usuario++++++

    //Getters
    public String getName() { return new String(name); }
    public int getBorradol(){ return borradol[0]; }
    public int getId(){return id;}
    public float getTime() {return time; }
    public float getScore() {return GeneralScore; }

    public float getProgresslvlone() {return levelsProgress[0]; }
    public float getProgresslvltwo() {return levelsProgress[1]; }
    public float getProgresslvltree() {return levelsProgress[2]; }

    //Metodos
    //---------------------------------------------------------------
    //Longitud en bytes de un usuario
    public int length()
    {
        return borradol.length+
               name.length+
                (Integer.SIZE/8)+
                (Float.SIZE/8)+
                (Float.SIZE/8)+
                ((Float.SIZE/8)*3)+
                (Integer.SIZE/8)+
                ((Float.SIZE/8)*NUMACCESS*3)+
                ((Float.SIZE/8)*NUMEXLONE)+((Float.SIZE/8)*NUMEXLTWO)+((Float.SIZE/8)*NUMEXLTREE);

    }//Fin metodo length
    //----------------------------------------------------------------------------------------------
    //Metodo para escribir en RAF un objeto de la clase Usuario
    public void write(RandomAccessFile raf) throws IOException
    {
        raf.write(borradol);
        raf.write(name);
        raf.writeInt(id);
        raf.writeFloat(time);
        raf.writeFloat(GeneralScore);
        //Escribir Progreso de los niveles
        raf.writeFloat(levelsProgress[0]);
        raf.writeFloat(levelsProgress[1]);
        raf.writeFloat(levelsProgress[2]);
        //Contador de accesos usuario
        raf.writeInt(countaccess);
        //Aprovechamiento de n numero de habilidades con i numero de accesos
        for(int n = 0; n < 3; n++)
        {
            for(int i = 0; i < NUMACCESS; i++)
            {
                raf.writeFloat(skills[n][i]);
            }//Fin for 2

        }//Fin for 1
        //Progreso de los 3 niveles: cada uno con diferente numero de ejercicios
        //1)
        for(int i = 0; i < NUMEXLONE; i++)
        {
            raf.writeFloat(progreso[0][i]);
        }//Fin for 3
        //2)
        for(int i = 0; i < NUMEXLTWO; i++)
        {
            raf.writeFloat(progreso[1][i]);
        }//Fin for 3
        //3)
        for(int i = 0; i < NUMEXLTREE; i++)
        {
            raf.writeFloat(progreso[2][i]);
        }//Fin for 3

    }//Fin metodo write
    //---------------------------------------------------------------
    //Metodo para leer del RAF un objeto de la clase USUARIO
    public void read(RandomAccessFile raf) throws IOException
    {
        //Recordando que cada vez que se lee un byte avanza seek de manera automatica
        raf.read(borradol);
        raf.read(name);
        id = raf.readInt();
        time = raf.readFloat();
        GeneralScore = raf.readFloat();
        //Leer progreso de los niveles
        levelsProgress[0] = raf.readFloat();
        levelsProgress[1] = raf.readFloat();
        levelsProgress[2] = raf.readFloat();
        //Leer contador de accesos usario
        countaccess = raf.readInt();
        // lEER Aprovechamiento de n numero de habilidades con i numero de accesos
        for(int n = 0; n < 3; n++)
        {
            for(int i = 0; i < NUMACCESS; i++)
            {
                skills[n][i] = raf.readFloat();
            }//Fin for 2

        }//Fin for 1
        //Leer progreso en los tres niveles/tipos de ejercicios cada uno con diferente numero de
        //ejercicios
        //1)
        for(int i = 0; i < NUMEXLONE; i++)
        {
            progreso[0][i] = raf.readFloat();
        }//Fin for 3
        //2)
        for(int i = 0; i < NUMEXLTWO; i++)
        {
            progreso[1][i] = raf.readFloat();
        }//Fin for 3
        //3)
        for(int i = 0; i < NUMEXLTREE; i++)
        {
            progreso[2][i] = raf.readFloat();
        }//Fin for 3

    }//Fin metodo clase read
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
    float PromSkillsEx(float scores[])
    {
        //Calcular el score actual del usuario
        float actualscore = ScoreExcercise(scores);
        float promedio = 0.0f;

        //Maximo score que se puede alcanzar en el nivel (si se obtiene maxima puntuacion en todos
        // los ejercicios del nivel)
        float maxscore = 3*scores.length;

        //Hacer regla de 3
        promedio =  (100.0f* actualscore)/maxscore;

        return promedio;

    }//Fin metodo scoreSilabicEx
    //---------------------------------------------------------------
    //Obtener el score general del usuario de los tres niveles actual; y si este es mayor que el
    //que tiene el usario entonces guardarlo en la variable del usuario
    public void MaxScore(float[] scorelone, float[] scoreltwo, float[] scoreltree)
    {
        //Obtener el score individual en cada nivel
        float score1lv = ScoreExcercise(scorelone);
        float score2lv = ScoreExcercise(scoreltwo);
        float score3lv = ScoreExcercise(scoreltree);

        //Obtener el score de los 3 niveles
        float totalscore = score1lv + score2lv + score3lv;

        //Si el score calculado es mayor entonces guardarlo como el nuevo score del usuario
        if(totalscore > GeneralScore)
        {
            GeneralScore = totalscore;
        }//Fin if 1

    }//Fin metodo MaxScore
    //---------------------------------------------------------------
    //Obtener el nivel de aprovechamiento de las habilidades en los ultimos 10 accessos a la app
    public void UserSkillsAproach(int numAcc, float[] score1lv, float[] score2lv, float[] score3lv)
    {
        //Si el contador de acceso actual es menor que el numero de accessos tomados en cuenta para
        //calcular el aprovechamiento del usuario guardar el aprovechamiento de cada habilidad
        //en la posicion correspondiente al acceso
        if( numAcc < NUMACCESS )
        {
            //Calcular el aprovechamiento de la habilidad 1 correspondiente al acceso actual
            skills[0][numAcc] = PromSkillsEx(score1lv);

            //Calcular el aprovechamiento de la habilidad 2 correspondiente al acceso actual
            skills[1][numAcc] = PromSkillsEx(score2lv);

            //Calcular el aprovechamiento de la habilidad 3 correspondiente al acceso actual
            skills[2][numAcc] = PromSkillsEx(score3lv);

        }//Fin if 1

    }//Fin metodo UserSkillsAproach
    //---------------------------------------------------------------
    //Calcular el progreso actual en un nivel especifico(progreso: si para un ejercicio se alcanza
    //la puntuacion máxima marcarlo como completado; si no quedarse con lo maximo de puntuacion al
    // canzada) Parametros: progreso de nivel actual, numero de nivel
    public float LevelProgress(float[] currentproglvl, int lvlnumber)
    {
        //Hacer checado de progreso para cada ejercicio del nivel
        for(int i = 0; i < currentproglvl.length; i++)
        {
            //Quedarse solamente con la puntuacion maxima obtenida en el nivel correspondiente
            if(currentproglvl[i] > progreso[lvlnumber][i])
            {
                //Guardar el progreso actual
                progreso[lvlnumber][i] = currentproglvl[i];
            }//Fin if  1

        }//Fin for 1

        //Calcular el nivel de aprovechamiento en % del progreso en el nivel
        return PromSkillsEx(progreso[lvlnumber]);

    }//Fin metodo LevelProgress
    //---------------------------------------------------------------
    //Obtener el nivel de progreso de los tres niveles de ejercicios
    public void FinalLevelsProgress(float[] proglvlone, float[] proglvltwo, float[] proglvltree)
    {
        //Obtener el progreso en % del nivel 1
        levelsProgress[0] = LevelProgress(proglvlone, 0);

        //Obtener el progreso en % del nivel 2
        levelsProgress[1] = LevelProgress(proglvltwo, 1);

        //Obtener el progreso en % del nivel 3
        levelsProgress[2] = LevelProgress(proglvltree, 2);

    }//Fin metodo FinalLevelsProgress
    //---------------------------------------------------------------------------------------------
    //Hacer el calculo de todos los datos de uso del usuario (%Completado de nivel,TiempoUso,
    // MaxScore, %progreso de habilidades en ultimos 10 accesos)
    public void UserData(float curtime, float[] scorelvl1, float[] scorelvl2, float[] scorelvl3)
    {
        //%Completado de niveles
        FinalLevelsProgress(scorelvl1, scorelvl2, scorelvl3);

        //Calcular tiempo de uso
        time = time+curtime;

        //MaxScore
        MaxScore(scorelvl1, scorelvl2, scorelvl3);

        //%Progreso habilidades lectoescritoras del usuario en los ultimos 10 accesos
        //Si el numero de accesos es mayor a el maximo; sobrescrivir datos en la primera posicion
        if(countaccess < NUMACCESS)
        {
            UserSkillsAproach(countaccess, scorelvl1, scorelvl2, scorelvl3);
        }
        else
        {
            //Reinciar el contador de accessos y realizar el calculo de Progreso de habilidades lectoescritoras
            countaccess = 0;

            UserSkillsAproach(countaccess, scorelvl1,scorelvl2,scorelvl3);
        }//Fin else

        //Incremento el contador de accesos del usuario para el siguiente acceso
        countaccess++;


    }//Fin metodo UserData

    //----------------------------------------------------------------------------------------------
}//Fin clase Usuario
