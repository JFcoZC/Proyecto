package udlap.ingsoft.proyecto;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.widget.Button;

import java.util.Random;

/**
 * Created by Patty on 29/11/2017.
 */

public class SopaEx
 {
    Button[][] matriz = new Button[6][6]; //Atributo de una matriz de botones utilizada para el manejo de la interacciÃ³n los botones (en matriz)en el activity_main.xml (XML/Layout).
    String alfabeto = "aábcdeéfghiíjklmnñoópqrstuúvwxyz"; //Alfabeto utilizado para fijar, aleatoriamente, el texto de la matriz de botones "matriz".
    Random rdm = new Random(); //Objeto utilizado para escoger una posiciÃ³n, direcciÃ³n y sentido especÃ­ficos para situar la palabra (por caracter) en la matriz de botones "matriz".
    int renPos; //Se utiliza para definir aleatoriamente la posiciÃ³n en y (renglones) en donde se empezarÃ¡ la palabra dentro de la matrÃ­z de botones.
    int colPos; //Se utiliza para definir aleatoriamente la posiciÃ³n en x (columnas) en donde se empezarÃ¡ la palabra dentro de la matrÃ­z de botones.
    int direccion; //Variable que defina en quÃ© direcciÃ³n y sentido aleatorio se escribe la palabra (horizontal y a la derecha o vertical y hacia abajo).

    //Maneja el color del botÃ³n btn.
    public void setButtonColor(Button btn, int buttonCase){
        if (buttonCase == 0) { //Caso en el que se cambia el color del botÃ³n al original/inicial (a NARANJA).
            ViewCompat.setBackgroundTintList(btn, ColorStateList.valueOf(Color.rgb(255, 152, 35))); //Cambio de color a NARANJA
        } else if (buttonCase == 1) { //Caso en el que se cambia el color del botÃ³n presionado (a MORADO).
            ViewCompat.setBackgroundTintList(btn, ColorStateList.valueOf(Color.rgb(179, 64, 250))); //Cambio de color a MORADO
        } else if (buttonCase == 2) { //Caso en el que se cambia el color los botones alrededor del botÃ³n presionado (a AMARILLO).
            ViewCompat.setBackgroundTintList(btn, ColorStateList.valueOf(Color.rgb(255, 215, 56))); //Cambio de color a AMARILLO
        } else if (buttonCase == 3) { //Caso en el que se cambia el color (a VERDE) del botÃ³n al final (cuando se encontrÃ³ la palabra).
            ViewCompat.setBackgroundTintList(btn, ColorStateList.valueOf(Color.rgb(124, 205, 2))); //Cambio de color a VERDE
        } else if (buttonCase == 4) { //Caso en el que se cambia el color del botÃ³n presionado y este no contribuye a la construcciÃ³n de la palabra.
            ViewCompat.setBackgroundTintList(btn, ColorStateList.valueOf(Color.rgb(255, 69, 69))); //Cambio de color a ROJO
        }
    }

    //Maneja el color y habilitado del botÃ³n btn.
    public void setButtonState(Button btn, Boolean enabling, int buttonCase) {
        btn.setEnabled(enabling); //Habilita o deshabilita (utilizando el valor de "enabling") el botÃ³n recibido por el mÃ©todo.
        if (buttonCase == 0) { //Caso en el que se cambia el color del botÃ³n al original/inicial (a NARANJA).
            ViewCompat.setBackgroundTintList(btn, ColorStateList.valueOf(Color.rgb(255, 152, 35))); //Cambio de color a NARANJA
        } else if (buttonCase == 1) { //Caso en el que se cambia el color del botÃ³n presionado (a MORADO).
            ViewCompat.setBackgroundTintList(btn, ColorStateList.valueOf(Color.rgb(179, 64, 250))); //Cambio de color a MORADO
        } else if (buttonCase == 2) { //Caso en el que se cambia el color los botones alrededor del botÃ³n presionado (a AMARILLO).
            ViewCompat.setBackgroundTintList(btn, ColorStateList.valueOf(Color.rgb(255, 215, 56))); //Cambio de color a AMARILLO
        } else if (buttonCase == 3) { //Caso en el que se cambia el color (a VERDE) del botÃ³n al final (cuando se encontrÃ³ la palabra).
            ViewCompat.setBackgroundTintList(btn, ColorStateList.valueOf(Color.rgb(124, 205, 2))); //Cambio de color a VERDE
        } else if (buttonCase == 4) { //Caso en el que se cambia el color del botÃ³n presionado y este no contribuye a la construcciÃ³n de la palabra (a ROJO).
            ViewCompat.setBackgroundTintList(btn, ColorStateList.valueOf(Color.rgb(255, 69, 69))); //Cambio de color a ROJO
        }
    }

    //Maneja el color y habilitado del botÃ³n btn mediante su posiciÃ³n en la matriz de botones "matriz".
    public void setButtonStateById(int buttonId, Boolean enabling, int buttonCase) {
        for (int i = 0; i < matriz.length; i++) { //Recorre la matriz por renglones...
            for (int j = 0; j < matriz[0].length; j++) { //Recorre la matriz por columnas...
                if (matriz[i][j].getId() == buttonId) { //Si el id del botÃ³n en la posiciÃ³n (i, j) de la matriz "matriz" es igual al id "buttonId" entregado al mÃ©todo...
                    matriz[i][j].setEnabled(enabling); //Habilita o deshabilita (utilizando el valor de "enabling") el botÃ³n recibido por el mÃ©todo.
                    if (buttonCase == 0) { //Caso en el que se cambia el color del botÃ³n al original/inicial (a NARANJA).
                        ViewCompat.setBackgroundTintList(matriz[i][j], ColorStateList.valueOf(Color.rgb(255, 152, 35))); //Cambio de color a NARANJA
                    } else if (buttonCase == 1) { //Caso en el que se cambia el color del botÃ³n presionado (a MORADO).
                        ViewCompat.setBackgroundTintList(matriz[i][j], ColorStateList.valueOf(Color.rgb(179, 64, 250))); //Cambio de color a MORADO
                    } else if (buttonCase == 2) { //Caso en el que se cambia el color los botones alrededor del botÃ³n presionado (a AMARILLO).
                        ViewCompat.setBackgroundTintList(matriz[i][j], ColorStateList.valueOf(Color.rgb(255, 215, 56))); //Cambio de color a AMARILLO
                    } else if (buttonCase == 3) { //Caso en el que se cambia el color (a VERDE) del botÃ³n al final (cuando se encontrÃ³ la palabra).
                        ViewCompat.setBackgroundTintList(matriz[i][j], ColorStateList.valueOf(Color.rgb(124, 205, 2))); //Cambio de color a VERDE
                    } else if (buttonCase == 4) { //Caso en el que se cambia el color del botÃ³n presionado y este no contribuye a la construcciÃ³n de la palabra (a ROJO).
                        ViewCompat.setBackgroundTintList(matriz[i][j], ColorStateList.valueOf(Color.rgb(255, 69, 69))); //Cambio de color a ROJO
                    }
                }
            }
        }
    }

    //Maneja el color, habilitado y texto del botÃ³n btn.
    public void setButton(Button btn, Boolean enabling, String letter, int buttonCase) {
        btn.setText(letter);
        btn.setEnabled(enabling); //Habilita o deshabilita el botÃ³n recibido por el mÃ©todo.
        if (buttonCase == 0) { //Caso en el que se cambia el color del botÃ³n al original/inicial.
            ViewCompat.setBackgroundTintList(btn, ColorStateList.valueOf(Color.rgb(255, 152, 35))); //Cambio de color a NARANJA
        } else if (buttonCase == 1) { //Caso en el que se cambia el color del botÃ³n presionado.
            ViewCompat.setBackgroundTintList(btn, ColorStateList.valueOf(Color.rgb(179, 64, 250))); //Cambio de color a MORADO
        } else if (buttonCase == 2) { //Caso en el que se cambia el color los botones alrededor del botÃ³n presionado.
            ViewCompat.setBackgroundTintList(btn, ColorStateList.valueOf(Color.rgb(255, 215, 56))); //Cambio de color a AMARILLO
        } else if (buttonCase == 3) { //Caso en el que se cambia el color de botÃ³n al final (cuando se encontrÃ³ la palabra).
            ViewCompat.setBackgroundTintList(btn, ColorStateList.valueOf(Color.rgb(124, 205, 2))); //Cambio de color a VERDE
        } else if (buttonCase == 4) { //Caso en el que se cambia el color del botÃ³n presionado y este no contribuye a la construcciÃ³n de la palabra (a ROJO).
            ViewCompat.setBackgroundTintList(btn, ColorStateList.valueOf(Color.rgb(255, 69, 69))); //Cambio de color a ROJO
        }
    }

    public void setMatrix(String word){ //MÃ©todo que se usarÃ¡ para "reorganizar" la matriz de botones (del XML activity_main.xml, desde el MainActivity.java) con la palabra "word" escondida, letras aleatorias y un color definido.
        //Se llena la matrÃ­z de valores (letras) aleatorios (del String "alfabeto").
        for(int i = 0; i < matriz.length; i++){ //Se recorre la matriz "matris" por renglÃ³n...
            for(int j = 0; j < matriz[0].length; j++){ //Se recorre la matriz "matriz" por columna...
                setButton(matriz[i][j], true, String.valueOf(alfabeto.charAt(rdm.nextInt(alfabeto.length()))),0); //Cambia el texto del botÃ³n matriz[i][j] (en la posiciÃ³n (i, j) de la matriz "matriz") con un valor aleatorio de obtenido del String "alfabeto".
            }
        }
        renPos = rdm.nextInt(matriz.length); //Se definide aleatoriamente la posiciÃ³n (en renglÃ³n/en y) inical de dÃ³nde se empezarÃ¡ a "escribir" o situar la palabra dentro de la matriz "matriz".
        colPos = rdm.nextInt(matriz[0].length); //Se definide aleatoriamente la posiciÃ³n (en columnas/en x) inical de dÃ³nde se empezarÃ¡ a "escribir" o situar la palabra dentro de la matriz "matriz".
        direccion = rdm.nextInt(2); //Se escoge una direcciÃ³n y sentido aleatorios para escribir la palabra dentro de la matriz de botones (el valor de 0 es para vertical y hacia abajo; y el valor de 1, para horizontal y hacia la derecha).
        if(direccion == 0){ //Si se escoge escribir la palabra verticalmente (direccion = 0) en la matriz...
            while(word.length() > matriz.length - renPos){ //Mientras que la palabra no quepa en la matriz debido a la posiciÃ³n en y (renglones) que se escogiÃ³...
                renPos = rdm.nextInt(matriz.length); //...se vuelve a escoger otra posiciÃ³n (en renglones) aleatoriamente.
            }
            for(int i = 0; i < word.length(); i++){ //Se cambia el texto de los botones en base a la direcciÃ³n, sentido y botÃ³n inicial sobre la cual se situarÃ¡ la palabra.
                matriz[renPos + i][colPos].setText(String.valueOf(word.charAt(i))); //Se "escribe" la palabra en sentido vertical (de arriba hacia abajo) cambiando el texto de los botones, comenzando por el botÃ³n en la posiciÃ³n inicial (renPos, colPos) de la matriz "matriz".
            }
        } else { //Si se selecciona escribir la palabra en la matriz horizontalmente (direccion = 1) en la matriz...
            while(word.length() > matriz[0].length - colPos){ //Mientras que la palabra no quepa en la matriz debido a la posiciÃ³n en x (columnas) que se escogiÃ³...
                colPos = rdm.nextInt(matriz[0].length); //...se vuelce a escoger otra posiciÃ³n (en columnas) aleatoriamente.
            }
            for(int i = 0; i < word.length(); i++){ //Se cambia el texto de los botones en base a la direcciÃ³n, sentido y botÃ³n inicial sobre la cual se situarÃ¡ la palabra.
                matriz[renPos][colPos + i].setText(String.valueOf(word.charAt(i))); //Se "escribe" la palabra en sentido horizontal (de izquierda a derecha) cambiando el texto de los botones, comenzando por el botÃ³n en la posiciÃ³n inicial (renPos, colPos) de la matriz "matriz".
            }
        }
    }
}

