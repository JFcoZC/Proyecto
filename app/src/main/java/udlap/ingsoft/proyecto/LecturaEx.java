package udlap.ingsoft.proyecto;

import java.util.Random;

/**
 * Created by José on 30/10/2017.
 * Clase que contiene ejercicio de tipo Lectura animada
 */

public class LecturaEx
{
    //Atributos
    //Por convencion [0] corresponde a la primera palabra correcta y [1] a la segunda correcta
    String[] palabra = new String[3];
    //VALORES EN ENTREROS DE RESOURCES (R)
    int[] valores = new int[4];
    //Valor palabra corecta
    int posicion;
    //Arreglo que representa si las silabas estan siendo marcadas por el checkbox
    int[] estado = {0, 0, 0};

    //----------------------------------------------------------------------------------------------
    //Constructor
    public LecturaEx(String cone, String ctwo, String ctre, int[] resources, int id) {
        palabra[0] = cone;
        palabra[1] = ctwo;
        palabra[2] = ctre;
        //palabra = word;

        valores = resources;
        //Posicion
        posicion = id;
    }//Fin constructor SilabicEx

    //------------------------------------
    //Setters & Getters
    //Metodo que regresa el valor entero de un Resource asociado (usado para los audios)
    int getResourceNum(int indx) {
        return valores[indx];
    }//Fin metodo getResourceNum

    //Metodo que regresa la cadena de la silaba en la posicion especificada
    String getThisWord(int i) {
        return palabra[i];
    }//Fin metodo getThisSilaba

    //Metodo que regresa estado de seleccion de la primer silaba correcta
    int StateFirstRight() {
        return estado[0];
    }//Metodo que regresa estado primera silaba correcta

    //Metodo que regresa estado de seleccion de la segunda silaba correcta
    int StateFirstWrong() {
        return estado[1];
    }//Fin metodo StateSecondRight

    //Metodo que regresa estado de seleccion de la primer silaba incorrecta
    int StateSecondWrong() {
        return estado[2];
    }//Fin metodo StateFirstWrong


    //Metodo que regresa el valor id/posicion en arreglod e SilabicEx
    int getPosicion() {
        return posicion;
    }//Fin metodo getPosicon

    //Metodo que dado el numero de silaba la marca como activada por el checkbox
    void SilabaSelected(int indx) {
        estado[indx] = 1;
    }

    //Metodo que dado el numero de silaba la marca como desactivada por el checkbox
    void SilabaDeselected(int indx) {
        estado[indx] = 0;
    }

    //--------------------------FIN GETTERS & SETTERS------------------------------------

    //Metodos
    //------------------------------------------------------------------------------------
    //Metodo que regresa las posiciones de las 3 palabras en un arreglo pero en un orden aleatorio
    int[] randomWords()

    {
        //Crear un valor aleatorio entre 0 - 3
        Random random = new Random();
        int rand;
        int contador = 0;

        //Arreglo que va a contener palabras en orden aleatorio
        int[] silabasrandom = {-1, -1, -1};

        //Arreglo para control de repeticion de numero random
        //false == no se ha colocado esa silaba / true == ya se coloco esa silaba
        boolean[] repetido = {false, false, false};

        //Mientras no se hallan colocado todas las silabas
        while (repetido[0] == false || repetido[1] == false || repetido[2] == false) {
            rand = random.nextInt(2 - 0 + 1) + 0;

            //El numero aleatorio ya paso
            if (repetido[rand] == false) {
                //No ha pasado ese nuemro

                //Agregar en posicion corespondiente de silabasrandom
                // la posicion rand
                silabasrandom[contador] = rand;

                //Marcar que ya pasao el numero
                repetido[rand] = true;
                //Aumentar en uno el contador
                contador++;

            }//Fin if 1
            else {
                //No hacer nada y que intente con otro numero aleatorio
            }//Fin else 1

        }//Fin while

        return silabasrandom;

    }//Fin metodo silabas

    //-------------------------------------------------------------------------------------
    //Metodo que resetea los estados de seleccion de las silabas
    public void ResetEstados() {
        estado[0] = 0;
        estado[1] = 0;
        estado[2] = 0;

    }//Fin metodo ResetEstados
    //------------------------------------------------------------------------------------

}//Fin clase LecturaEx
