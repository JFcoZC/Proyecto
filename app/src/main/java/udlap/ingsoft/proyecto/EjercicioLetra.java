package udlap.ingsoft.proyecto;

/**
 * CLASE QUE DECLARA ATRIBUTOS Y METODOS DE UN EJERCICIO DE LOSE QUE CONFORMAN EL TIPO DE Ejercicio
 * Abecedario
 * Created by luisricardo on 01/11/2017.
 */

public class EjercicioLetra
{
    String keyLetter;
    int dirImg;
    int[] dirSounds;
    String[] letters;

    int keyIndex;

    public EjercicioLetra(String keyWord, int dirImg, String[] letrasOpc, int keyIndex, int[] dirSounds){
        keyLetter = keyWord;
        this.dirImg = dirImg;
        letters = letrasOpc;
        this.keyIndex = keyIndex;
        this.dirSounds = dirSounds;
    }

}//Fin clase EjercicioLetra
