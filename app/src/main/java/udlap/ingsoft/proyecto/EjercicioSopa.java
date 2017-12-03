package udlap.ingsoft.proyecto;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Patty on 29/11/2017.
 */

public class EjercicioSopa extends AppCompatActivity
{
        int numEjercicios = 28; //Numero de ejercicios (20).
        int currentExercise = 0; //Contador utilizado para determinar la palabra, audio del ejercicio stepSize (suma de puntos por letra acertada) para el puntaje.
        String[] palabras = new String[numEjercicios]; //Arreglo de Strings/palabras (de cada ejercicio) del juego "Sopa de Letras".
        float[] puntaje = new float[numEjercicios]; //Arreglo de floats utilizado para guardar el punaje mÃ¡s alto en cada ejercicio.
        float puntosAcum; //Puntos acumulados por letra acertada de la palabra al momento de hacer el ejercicio nÃºmero "currentExcercise".
        int[] audios = new int[numEjercicios]; //Arreglo de integers (IDs de audios o grabaciones) de las palabras de cada ejercicio (en el respectivo orden de las palabras en el arreglo "palabras").
        SopaEx ejercicio = new SopaEx(); //Este objeto tiene un atributo de una matriz (llamado "matriz") de botones (Button[6][6]).
        String buttonName; //String que guarda el nombre del botÃ³n en el activity_main.xml para luego obtener su ID y asignar este a un objeto tipo botÃ³n en el MainActivity.java
        int matrixSize = ejercicio.matriz.length; //Variable para conocer el tamaÃ±o de renglÃ³n (igual al de las columnas) de la matriz "matriz"(6x6).
        int[] idClick = new int[matrixSize]; //Arreglo que guarda los IDs de los botones presionados (matrixSize = 6 es el tamaÃ±o mÃ¡ximo debido a que no se pueden preionar mÃ¡s de 6 botones en la matriz de botones por ejercicio (en caso de querer meter palabras de mÃ¡s de 6 letras, es necesario agregar mÃ¡s botones al activity_main.xml y cambiar el valor de las variables correspondientes dentro del MainActivity.java y Ejercicio.java)).
        int currentButton = -1; //Contador utilizado para indicar el nÃºmero de botones presionados (si currentButton == -1, no se tiene ningÃºn botÃ³n presionado | si currentButton == 0, se ha presionado un solo botÃ³n).
        String wordInput = ""; //String que almacena lo que el usuario ha tecleado por medio de la matriz de botones en el activity_main.xml.
        SpannableStringBuilder coloredText; //Objeto utilizado para colorear los caracteres de la palabra (del TextView "textViewWord") del ejercicio a modo que se presionan los botones de la matriz del activity_main.xml
        ForegroundColorSpan naranja = new ForegroundColorSpan(Color.rgb(255, 152, 35)); //(SIN UTILIZAR) Objeto utilizado para cambiar el color de una letra a NARANJA.
        ForegroundColorSpan morado = new ForegroundColorSpan(Color.rgb(179, 64, 250)); //Objeto utilizado para cambiar el color de una letra a MORADO.
        ForegroundColorSpan amarillo = new ForegroundColorSpan(Color.rgb(255, 215, 56)); //(SIN UTILIZAR) Objeto utilizado para cambiar el color de una letra a AMARILLO.
        ForegroundColorSpan verde = new ForegroundColorSpan(Color.rgb(124, 205, 2)); //Objeto utilizado para cambiar el color de una letra a VERDE.
        ForegroundColorSpan rojo = new ForegroundColorSpan(Color.rgb(255, 69, 69)); //Objeto utilizado para cambiar el color de una letra a ROJO.
        ForegroundColorSpan blanco = new ForegroundColorSpan(Color.rgb(255, 255, 255)); //(SIN UTILIZAR) Objeto utilizado para cambiar el color de una letra a BLANCO.
        int direccion; //Variable que especifica (definida por la interacciÃ³n del usuario con la aplicaciÃ³n) el sentido (y direcciÃ³n) en la que el usuario estÃ¡ "encontrando" la palabra dentro de la matriz de botones del activity_main.xml (si es 0, el sentido es vertical | si es 1, el sentido es horizontal).
        int a; //Ãndice al que se le asigna la posiciÃ³n en renglones del botÃ³n presionado en el activity_main.xml.
        int b; //Ãndice al que se le asigna la posiciÃ³n en columnas del botÃ³n presionado en el activity_main.xml.

        //Declarar elementos del layout
        TextView textViewWord;
        RatingBar estrellas;
        ImageButton btnNext;
        ImageButton btnPrev;
        ImageButton btnRetry;
        Button btnAudio;
        //Fin delcarar elementos del layout


        //---------------------
        //MediaPlayer para la reproduccion de sonidos
        MediaPlayer sound;
        //Tiempo de juego
        long INICIOTIME = 0;
        long FINTIME = 0;
        long FULLTIME;

        //DECLAR CARIABLE GLOBAL DE INTENT para obtener datos provenientes de actividades anteriroes
        Intent inten;

        //DECLARAR VARIABLE GLOBAL DE ID USAURIO ACTUAL
        int IDCURRENTUSER = -1;

        //DECLARAR VARIABLE GLOBAL DE USUARIO ACTUAL
        Usuario CURRENTUSER;

        //DETERMINA EL NUMERO DE EJERCICIOS sopa de letras (checar esta variable tambien en clase Usuario)
        int NUMEXCER = 0; //palabras.length

        //Media palyer
        MediaPlayer audio;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.ejercicio_sopa);



            textViewWord = (TextView) findViewById(R.id.textViewWord); //AsignaciÃ³n del "TextViewWord" (TextView) a un objeto en el MainActivity.java llamado "textViewWord".
            estrellas = (RatingBar) findViewById(R.id.pointsBar); //AsignaciÃ³n del "pointsBar" (RatingBar) a un objeto en el MainActivity.java llamado "estrellas".

            //final MediaPlayer audio = MediaPlayer.create(this, audios[currentExercise]);

            //---------- PALABRAS ---------- (DefiniciÃ³n de las palabras a utilizar en los ejercicios)//

            palabras[0] = "río";
            palabras[1] = "mar";
            palabras[2] = "sol"; //
            palabras[3] = "pez"; //
            palabras[4] = "eco"; //
            palabras[5] = "pie"; //
            palabras[6] = "oro"; //
            palabras[7] = "pino";
            palabras[8] = "pozo";
            palabras[9] = "lago";
            palabras[10] = "pato";
            palabras[11] = "león";
            palabras[12] = "bote";
            palabras[13] = "agua"; //
            palabras[14] = "perro";
            palabras[15] = "pasto";
            palabras[16] = "oveja";
            palabras[17] = "cielo";
            palabras[18] = "avión";
            palabras[19] = "cueva"; //
            palabras[20] = "fruta";
            palabras[21] = "piedra";
            palabras[22] = "conejo";
            palabras[23] = "planta";
            palabras[24] = "trueno";
            palabras[25] = "granja";
            palabras[26] = "volcán";
            palabras[27] = "jirafa";

            //---------- AUDIOS ---------- (Grabaciones de las palabras correspondientes para los ejercicios)

            audios[0] = R.raw.rio;
            audios[1] = R.raw.mar;
            audios[2] = R.raw.sol;
            audios[3] = R.raw.pez;
            audios[4] = R.raw.eco;
            audios[5] = R.raw.pie;
            audios[6] = R.raw.oro;
            audios[7] = R.raw.pino;
            audios[8] = R.raw.pozo;
            audios[9] = R.raw.lago;
            audios[10] = R.raw.pato;
            audios[11] = R.raw.leon;
            audios[12] = R.raw.bote;
            audios[13] = R.raw.agua;
            audios[14] = R.raw.perro;
            audios[15] = R.raw.pasto;
            audios[16] = R.raw.oveja;
            audios[17] = R.raw.cielo;
            audios[18] = R.raw.avion;
            audios[19] = R.raw.cueva;
            audios[20] = R.raw.fruta;
            audios[21] = R.raw.piedra;
            audios[22] = R.raw.conejo;
            audios[23] = R.raw.planta;
            audios[24] = R.raw.trueno;
            audios[25] = R.raw.granja;
            audios[26] = R.raw.volcan;
            audios[27] = R.raw.jirafa;

            //Numero de ejercicios en total!!!!!!!
            //NUMEXCER = palabras.length;

            //----OBTENER POSICION DE EJERCICIO QUE DEBE SER DESPLEGADO Y MOSTRARLO---------------------
            inten = getIntent();
            //El 0 es un valor de dfault por si no se encuentra el valor que se buscaba, se asigna 0 por
            //default
            currentExercise = inten.getIntExtra("INDXEX",0);
            //-------------- FIN MOSTRAR EJERCICIO CORRESPONDIENTE SOPA DE LETRAS-------------------------

            //***Obtener Datos Actuales del usario desde actividad anterior
            inten = getIntent();
            IDCURRENTUSER = inten.getIntExtra("IDUSER",0);

            INICIOTIME = System.currentTimeMillis();


            //---------- PALABRA DEL EJERCICIO ----------//

            textViewWord.setText(palabras[currentExercise]); //Se fija el texto del TextView "textViewWord" con la primera palabra del ejercicio con currentExcercise = 0.
            coloredText = new SpannableStringBuilder(palabras[currentExercise]); // Se crea un objeto utilizado para el cambio de colores con la palabra (String) del ejercicio actual (inicialmente se empieza con la palabra del currentExcercise = 0).

//----------AsignaciÃ³n de botones en el XML al JAVA----------

            //----------Arreglo de botones----------//

            //Nota: el procedimietno que sigue podrÃ­a resultar costoso en tÃ©rminos de eficiencia (dado el caso: asignar los botones de manera exhaustiva).
            for (int i = 0; i < matrixSize; i++) { //Asignar el valor del arreglo de botones en el activity_main.xml a los botones del atributo "matriz" de la clase Ejercicio.
                for (int j = 0; j < matrixSize; j++) { //Para lo anterior se recorre la matriz de botones "matriz" para dicha asignaciÃ³n.
                    buttonName = "btn" + i + j; //Se prepara el nombre del botÃ³n en el activity_main.xml para luego utilizarlo...
                    final int idButtonName = getResources().getIdentifier(buttonName, "id", getPackageName()); //...y generar el id a partir del nombre creado anteriormente.
                    ejercicio.matriz[i][j] = (Button) findViewById(idButtonName); //AsignaciÃ³n de los botones al arreglo de botones "matriz" de la clase "Ejercicio".

                    ejercicio.matriz[i][j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //--------------------InteracciÃ³n con los bototnes--------------------

                            //----------DefiniciÃ³n de "a" (posiciÃ³n en renglones) y de "b" (posiciÃ³n en columnas) del botÃ³n presionado----------
                            //(a y b son utilizados para el manejo del comportamiento de botones por posiciÃ³n en la matriz)
                            for (int i = 0; i <= 5; i++) {
                                for (int j = 0; j <= 5; j++) {
                                    if (ejercicio.matriz[i][j] == v) {
                                        a = i;
                                        b = j;
                                    }
                                }
                            }

//----------Caso 1: No hay ningÃºn botÃ³n presionado en la matriz----------

                            if ((currentButton == -1) && (idClick[currentButton + 1] == 0)) { //Si currentButton (contador) es cero y idClick[currentButton] (registro de botones presionados) es cero, quiere decir que no hay nada presionado en la matriz de botones.
                                currentButton++; //"currentButton" ahora es igual a 0 (quiere decir que se tiene un botÃ³n presionado en la matriz).
                                idClick[currentButton] = v.getId(); //Se guarda el ID del primer botÃ³n presionado en el arreglo idClick.
                                wordInput = wordInput + ejercicio.matriz[a][b].getText().toString(); //Se concatena lo que hay en wordInput (acutalmente vacÃ­o) mÃ¡s la letra del botÃ³n presionado...
                                for (int i = 0; i < matrixSize; i++) { //Con estos ciclos de for's anidados se deshabilitan todos los botones del arreglo (actualmente de color NARANJA).
                                    for (int j = 0; j < matrixSize; j++) {
                                        ejercicio.matriz[i][j].setEnabled(false); //Deshabilita el botÃ³n en la posiciÃ³n (i, j) de la matriz "matriz" de "ejercicio".
                                    }
                                }
                                if((a ==  matrixSize - 1) && (b == matrixSize - 1) && (wordInput.charAt(0) != (palabras[currentExercise].charAt(currentButton)))){ //Si el botÃ³n presionado es el encontrado en la esquina inferior izquierda y no contribuye a la construcciÃ³n de la palabra.
                                    ejercicio.setButtonState(ejercicio.matriz[a][b], true, 4); //Hablilitar y cambiar color del botÃ³n presionado a ROJO.
                                    coloredText.clearSpans();               //Se quita cualquier alteraciÃ³n de color al texto que se le asigna al TextView "textViewWord"...
                                    coloredText.setSpan(rojo, 0, 1, 0);     //...se cambia de color la primera letra de la palabra en el texto a ROJO...
                                    textViewWord.setText(coloredText);      //...y se le asigna el texto "coloredText" (tipo SpannableStringBuilder) al TextView "textViewWord".
                                } else {
                                    if((a ==  matrixSize - 1) && (b == matrixSize - 1) && (wordInput.charAt(0) == (palabras[currentExercise].charAt(currentButton)))){ //Si el botÃ³n presionado es el encontrado en la esquina inferior izquierda y contribuye a la construcciÃ³n de la palabra.
                                        ejercicio.setButtonState(ejercicio.matriz[a][b], true, 4); // Hablilitar y cambiar color del botÃ³n presionado a ROJO.
                                        coloredText.clearSpans();               //
                                        coloredText.setSpan(morado, 0, 1, 0);   //Cambio de color de la primera letra de la palabra (en el TextView "textViewWord") a MORADO.
                                        textViewWord.setText(coloredText);      //
                                    } else {
                                        if (ejercicio.matriz[a][b].getText().charAt(0) != palabras[currentExercise].charAt(currentButton)) { //El botÃ³n presionado (en cualquier parte de la matriz) no contribuye a la construcciÃ³n de la palabra.
                                            ejercicio.setButtonState(ejercicio.matriz[a][b], true, 4);
                                            coloredText.clearSpans();               //
                                            coloredText.setSpan(rojo, 0, 1, 0);     //Cambio de color de la primera letra de la palabra (en el TextView "textViewWord") a ROJO.
                                            textViewWord.setText(coloredText);      //
                                        } else {
                                            if (wordInput.equals(palabras[currentExercise])) { //Caso en el que la palabra completa solo tiene una letra y, por lo tanto, el ejercicio se resolviÃ³ correctamente ().
                                                ejercicio.setButtonState(ejercicio.matriz[a][b], false, 3); //Deshablilitar y cambiar color del botÃ³n presionado a VERDE.
                                                coloredText.clearSpans();               //
                                                coloredText.setSpan(verde, 0, 1, 0);    //Cambio de color de la primera letra de la palabra (en el TextView "textViewWord") a VERDE.
                                                textViewWord.setText(coloredText);      //
                                            } else {
                                                //El botÃ³n presionado contribuye a la construcciÃ³n de la palabra pero sigue sin construirse por completo.
                                                ejercicio.setButtonState(ejercicio.matriz[a][b], true, 1); //Hablilitar y cambiar color del botÃ³n presionado a MORADO.
                                                coloredText.clearSpans();               //
                                                coloredText.setSpan(morado, 0, 1, 0);   //Cambio de color de la primera letra de la palabra (en el TextView "textViewWord") a MORADO.
                                                textViewWord.setText(coloredText);      //

                                                //----------Hablilitar y cambiar color de los botones alrededor del presionado a AMARILLO.
                                                if (b + 1 < matrixSize) { //Caso en el que se trata el botÃ³n a la derecha del presionado.
                                                    ejercicio.setButtonState(ejercicio.matriz[a][b + 1], true, 2);
                                                }
                                                if (a + 1 < matrixSize) { //Caso en el que se trata el botÃ³n de abajo del presionado.
                                                    ejercicio.setButtonState(ejercicio.matriz[a + 1][b], true, 2);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {

//----------Caso 2: Se presiona otra vez el primer botÃ³n presionado previamente----------//

                                if ((currentButton == 0) && (v.getId() == idClick[currentButton])) { //Si currentButton (botÃ³n presionado recientemente) es 0 y el id del botÃ³n presionado anteriormente es el mismo que el presionado actualmente...
                                    for (int i = 0; i < matrixSize; i++) {              //
                                        for (int j = 0; j < matrixSize; j++) {          //
                                            ejercicio.matriz[i][j].setEnabled(true);    //Se habilitan todos los botones de la matriz.
                                        }                                               //
                                    }                                                   //
                                    ejercicio.setButtonColor(ejercicio.matriz[a][b], 0); //El botÃ³n presionado se cambia de color a NARANJA.
                                    if((a < matrixSize - 1) && (b < matrixSize -1)){ //Si el botÃ³n presionado se en cualquier parte de la matriz excepto en el lado inferior, el lado derecho y la esquina inferior derecha...
                                        ejercicio.setButtonColor(ejercicio.matriz[a + 1][b], 0); //El botÃ³n justo abajo del presionado se cambia de color a NARANJA.
                                        ejercicio.setButtonColor(ejercicio.matriz[a][b + 1], 0); //El botÃ³n justo a la derecha del presionado se cambia de color a NARANJA.
                                    } else{
                                        if(a != b) { //Si el botÃ³n presionado se encuentra o en el lado inferior o el lado derecho de la matriz (a excepciÃ³n del botÃ³n en la esquina inferior derecha).
                                            if (a == matrixSize - 1) { //Si el botÃ³n se encuentra en el lado inferior de la matriz...
                                                ejercicio.setButtonColor(ejercicio.matriz[a][b + 1], 0); //El botÃ³n justo a la derecha del presionado se cambia de color a NARANJA.
                                            } else {
                                                if (b == matrixSize - 1) { //Si el botÃ³n se encuentra en el lado derecho de la matriz...
                                                    ejercicio.setButtonColor(ejercicio.matriz[a + 1][b], 0); //El botÃ³n justo abajo del presionado se cambia de color a NARANJA.
                                                }
                                            }
                                        }
                                    }
                                    idClick[currentButton] = 0; //Se borra el ID del botÃ³n presionado al inicio del ejercicio del registro (arreglo) de botones presionados (idClick).
                                    wordInput = ""; //Se fija el texto wordInput a cadena vacÃ­a para luego.
                                    coloredText.clearSpans();               //Se quita cualquier alteraciÃ³n de color al texto que se le asigna al TextView "textViewWord"...
                                    textViewWord.setText(coloredText);      //se le asigna el texto "coloredText" (tipo SpannableStringBuilder) al TextView "textViewWord" (en otras palabras, el texto del TextView se pone de color blanco o sin color).
                                    currentButton--; //El contador se reinicia a "currentButton" = -1.
                                } else {

//----------Caso 3: Se presiona un botÃ³n cualquiera que no sea el mismo al primero presionado----------

                                    if ((currentButton >= 0) && (v.getId() != idClick[currentButton])) { //Si se presiona un botÃ³n de la matriz cualquira y no es el primero en presionarse, ni el mismo presionado al inicio.
                                        currentButton++; //El contador crece por 1 (ahora solo puede darse que currentButon > 0).
                                        idClick[currentButton] = v.getId(); //Se guarda el ID del botÃ³n presionado en el arreglo idClick.
                                        wordInput = wordInput + ejercicio.matriz[a][b].getText().toString(); //Se concatena la letra del botÃ³n presionado a lo que hay en el String "wordInput".

                                        //----------Se DEFINE el valor de "direccion" y se deshabilitan y cambian de color a naranja los botones que ya no estÃ¡n disponibles para interactuar.

                                        //DefiniciÃ³n de la variable "direccion" que especifica el sentido en el que el usuario estÃ¡ "encontrando" la palabra.
                                        if(currentButton == 1){ //Si el siguiente botÃ³n presionado (despuÃ©s del primero, cuando currentButton = 0)...
                                            if(a == 0){ //...se encuentra en el primer renglÃ³n de la matriz de botones...
                                                if(ejercicio.matriz[a][b - 1].getId() == idClick[currentButton - 1]){ //...y el botÃ³n a la izquierda del presionado es igual al presionado con anterioridad...
                                                    direccion = 1; //...el sentido con el que es usuario estÃ¡ "encontrando" la palabra es horizontal.
                                                } else{ //...y el botÃ³n que estÃ¡ arriba del presionado es igual al presionado con anterioridad...
                                                    direccion = 0; //...el sentido con el que es usuario estÃ¡ "encontrando" la palabra es vertical.
                                                }
                                            } else { //...se encuentra en cualquier parte de la matriz de botones que no sea en el primer renglÃ³n...
                                                if (ejercicio.matriz[a - 1][b].getId() == idClick[currentButton - 1]) { //...y el botÃ³n que estÃ¡ arriba del presionado es igual al presionado con anterioridad...
                                                    direccion = 0; //...el sentido con el que es usuario estÃ¡ "encontrando" la palabra es vertical.
                                                } else { //...y el botÃ³n a la izquierda del presionado es igual al presionado con anterioridad...
                                                    direccion = 1; //...el sentido con el que es usuario estÃ¡ "encontrando" la palabra es horizontal.
                                                }
                                            }

                                            //Se cambian de color a NARANJA y se deshabilitan los botones que ya no pueden ser presionados.
                                            if(direccion == 0){ //Si el sentido con el que es usuario estÃ¡ "encontrando" la palabra es vertical...
                                                if (b < matrixSize - 1) { //Si el botÃ³n presionado se encuentra en cualquier lugar que no sea sobre el lado derecho de la matriz de botones...
                                                    ejercicio.setButtonState(ejercicio.matriz[a - 1][b + 1], false, 0); //...se deshabilita y se cambia de color (a NARANJA) el botÃ³n justo arriba y a la derecha del presionado.
                                                }
                                            } else{ //Si el sentido con el que es usuario estÃ¡ "encontrando" la palabra es horizontal...
                                                if (a < matrixSize - 1) { //Si el botÃ³n presionado se encuentra en cualquier lugar que no sea sobre el lado inferior de la matriz de botones...
                                                    ejercicio.setButtonState(ejercicio.matriz[a + 1][b - 1], false, 0); //...se deshabilita y se cambia de color (a NARANJA) el botÃ³n justo abajo y a la izquierda del presionado.
                                                }
                                            }
                                        }

                                        // EL EJERCICIO SE RESOLVIÃ“ CORRECTAMENTE!!!
                                        if (wordInput.equals(palabras[currentExercise])) { //Si el botÃ³n presionado contribuye a la construcciÃ³n de la palabra y se construyÃ³ toda la palabra de forma correcta...
                                            for (int i = 0; i <= currentButton; i++) {
                                                ejercicio.setButtonStateById(idClick[i], false, 3); //...se deshabilitan y cambian el color de los botones a VERDES (por ID)...
                                                coloredText.clearSpans();                                               //
                                                coloredText.setSpan(verde, 0, palabras[currentExercise].length(), 0);   //...y se cambia de color (a VERDE) las letras de toda la palabra (dentro del TextView "textViewWord").
                                                textViewWord.setText(coloredText);                                      //
                                                //POSIBLE interacciÃ³n para pasar al siguiente ejercicio...
                                                audio.release();
                                                audio = MediaPlayer.create(EjercicioSopa.this, audios[currentExercise]);
                                                audio.start();
                                            }
                                        } else { //Si la palabra sigue sin construirse por completo...
                                            for (int i = 0; i < currentButton; i++) {
                                                ejercicio.setButtonStateById(idClick[i], false, 1); //...se deshabilitan y se cambian de color todos los botones previamente (excepto el actualmente presionado) oprimidos a MORADO (proceso hecho por medio de IDs).
                                            }
                                            if (ejercicio.matriz[a][b].getText().charAt(0) != palabras[currentExercise].charAt(currentButton)) { //Si el botÃ³n presionado no contribuye a la construcciÃ³n de la palabra...
                                                ejercicio.setButtonState(ejercicio.matriz[a][b], true, 4); //...el botÃ³n se habilita y cambia de color a ROJO.
                                                coloredText.clearSpans();                                       //
                                                coloredText.setSpan(morado, 0, currentButton, 0);               //Cambio de todas las letras (previa y correctamente encontradas) de la palabra (en el TextView "textViewWord") a MORADO.
                                                coloredText.setSpan(rojo, currentButton, currentButton + 1, 0); //Cambio de color de la letra (reciente e incorrectamente encontrada) de la palabra (en el TextView "textViewWord") a ROJO.
                                                textViewWord.setText(coloredText);                              //
                                            } else {
                                                if((ejercicio.matriz[a][b].getText().charAt(0) == palabras[currentExercise].charAt(currentButton)) && (((a == matrixSize - 1) && (direccion == 0)) || ((b == matrixSize - 1) && (direccion == 1)))){ //Si la palabra se estÃ¡ construyendo correctamente, pero se llega a los lÃ­mites de la matriz de botones (lado inferior o derecho)...
                                                    ejercicio.setButtonState(ejercicio.matriz[a][b], true, 4); //...el botÃ³n se habilita y cambia de color a ROJO.
                                                    coloredText.clearSpans();                               //
                                                    coloredText.setSpan(morado, 0, currentButton + 1, 0);   //Cambio de todas las letras de la palabra que coincidan con los botones presionados (en el TextView "textViewWord") a MORADO.
                                                    textViewWord.setText(coloredText);                      //
                                                } else{ //Si no se da ninguno de los casos anteriores, quiere decir que la interacciÃ³n con los botones sucede en cualquier parte de la matriz (que no sea en los lados derecho y/o inferior) y la palabra se estÃ¡ constuyendo de manera correcta.
                                                    ejercicio.setButtonState(ejercicio.matriz[a][b], true, 1); //El botÃ³n se habilita y se cambia de color a MORADO.
                                                    coloredText.clearSpans();                               //
                                                    coloredText.setSpan(morado, 0, currentButton + 1, 0);   //Cambio de todas las letras de la palabra que coincidan con los botones presionados (en el TextView "textViewWord") a MORADO.
                                                    textViewWord.setText(coloredText);                      //

                                                    //----------Hablilitar y cambiar color de los botones alrededor del presionado a AMARILLO.
                                                    if (direccion == 0) { //Si el sentido con el que es usuario estÃ¡ "encontrando" la palabra es vertical...
                                                        if (a + 1 < matrixSize) { //Si el botÃ³n presionado se encuentra en cualquier lugar que no sea sobre el lado inferior de la matriz de botones...
                                                            ejercicio.setButtonState(ejercicio.matriz[a + 1][b], true, 2); //...se habilita y cambia el color (a AMARILLO) del botÃ³n de abajo del presionado.
                                                        }
                                                    } else { //Si el sentido con el que es usuario estÃ¡ "encontrando" la palabra es horizontal...
                                                        if (b + 1 < matrixSize) { //Si el botÃ³n presionado se encuentra en cualquier lugar que no sea sobre el lado derecho de la matriz de botones...
                                                            ejercicio.setButtonState(ejercicio.matriz[a][b + 1], true, 2); //...se habilita y cambia el color (a AMARILLO) del botÃ³n de la derecha del presionado.
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else{

//----------Caso 4: Se presiona el mismo botÃ³n presionado con anterioridad (diferente del primero)----------

                                        if ((currentButton >= 1) && (v.getId() == idClick[currentButton])) { //Si se presiona el mismo botÃ³n presionado con anterioridad y este no es el primero que se presionÃ³ (Caso 2).
                                            ejercicio.setButtonColor(ejercicio.matriz[a][b], 2); //El botÃ³n presionado cambia de color a amarillo (y se encuentra habilitado).
                                            if(currentButton == 1){ //Si en la matriz de  botones hay exactamente 2 presionados...
                                                if(direccion == 0){ //Si la direccion en la que se encontraba "buscando" la palabra es vertical...
                                                    ejercicio.matriz[a - 1][b].setEnabled(true); //Se habilita el botÃ³n presionado anteriormente (que es color MORADO).
                                                    if(b < matrixSize - 1){ //Si el botÃ³n presionado se en encuentra en cualquier lugar que no sea el borde derecho de la matriz de botones...
                                                        ejercicio.setButtonState(ejercicio.matriz[a - 1][b + 1], true, 2); //Se habilita y se cambia de color a amarillo el botÃ³n un espacio hacia arriba y uno a la derecha del presionado.
                                                    }
                                                    if(a < matrixSize - 1){ //Si el botÃ³n presionado se en encuentra en cualquier lugar que no sea el borde inferior de la matriz de botones...
                                                        ejercicio.setButtonState(ejercicio.matriz[a + 1][b], false, 0); //Se deshabilita y cambia de color (a naranja) el botÃ³n que sigue en horizontal del preionado.
                                                    }
                                                } else{ //Si la direccion en la que se encontraba "buscando" la palabra es horizontal...
                                                    ejercicio.matriz[a][b - 1].setEnabled(true); //Se habilita el botÃ³n presionado anteriormente (color morado).
                                                    if(a < matrixSize - 1){ //Si el botÃ³n presionado se en encuentra en cualquier lugar que no sea el borde inferior de la matriz de botones...
                                                        ejercicio.setButtonState(ejercicio.matriz[a + 1][b - 1], true, 2); //Se habilita y se cambia de color a amarillo el botÃ³n un espacio hacia abajo y uno a la izquierda del presionado.
                                                    }
                                                    if(b < matrixSize - 1) { //Si el botÃ³n presionado se en encuentra en cualquier lugar que no sea el borde derecho de la matriz de botones...
                                                        ejercicio.setButtonState(ejercicio.matriz[a][b + 1], false, 0); //Se deshabilita y cambia de color (a naranja) el botÃ³n que sigue en vertical del preionado.
                                                    }
                                                }
                                            } else{ //Si en la matriz de botones hay mÃ¡s de 2 presionados...
                                                if(direccion == 0){ //Si la direccion en la que se encontraba "buscando" la palabra es vertical...
                                                    ejercicio.matriz[a - 1][b].setEnabled(true); //Se habilita el botÃ³n presionado anteriormente (color morado).
                                                    if(a < matrixSize - 1){ //Si el botÃ³n presionado se en encuentra en cualquier lugar que no sea el borde inferior de la matriz de botones...
                                                        ejercicio.setButtonState(ejercicio.matriz[a + 1][b], false, 0); //Se deshabilita y cambia de color (a naranja) el botÃ³n que sigue en vertical del preionado.
                                                    }
                                                } else{ //Si la direccion en la que se encontraba "buscando" la palabra es horizontal...
                                                    ejercicio.matriz[a][b - 1].setEnabled(true); //Se habilita el botÃ³n presionado anteriormente (color morado).
                                                    if(b < matrixSize - 1){ //Si el botÃ³n presionado se en encuentra en cualquier lugar que no sea el borde derecho de la matriz de botones...
                                                        ejercicio.setButtonState(ejercicio.matriz[a][b + 1], false, 0); //Se deshabilita y cambia de color (a naranja) el botÃ³n que sigue en horizontal del preionado.
                                                    }
                                                }
                                            }
                                            coloredText.clearSpans();                           //
                                            coloredText.setSpan(morado, 0, currentButton, 0);   //Cambio de todas las letras de la palabra que coincidan con los botones (a excepciÃ³n del mÃ¡s reciente oprimido) presionados con anterioridad (en el TextView "textViewWord") a MORADO.
                                            textViewWord.setText(coloredText);                  //
                                            idClick[currentButton] = 0; //Se borra el ID del del botÃ³n recientemente presionado del arreglo "IdClick".
                                            wordInput = wordInput.substring(0, currentButton); //Se cambia el valor de wordInput asignandonle una subcadena (menos un caracter) de su propio valor.
                                            currentButton--; //Se le resta 1 al contador (regreso al estado anterior).
                                        } //Fin de if del Caso 4
                                    } //Fin del else correspondiente al if del Caso 3
                                } //Fin del else correspondiente al if del Caso 2
                            } //Fin del else correspondiente al if del Caso 1

                            //----------ActualizaciÃ³n del estado del RatingBar (puntaje) llamado "estrellas"----------

                            puntosAcum = 0f;
                            for(int i = 0; i < wordInput.length(); i++){
                                if(wordInput.charAt(i) == palabras[currentExercise].charAt(i)){
                                    puntosAcum = puntosAcum + 3f/palabras[currentExercise].length();
                                }
                            }
                            estrellas.setStepSize(3f/palabras[currentExercise].length());
                            estrellas.setRating(puntosAcum);
                        } //Fin del onClick
                    }); //Fin del .setonClickListener
                } //Fin del segundo for (principal) en el que se comienza asignando los botones de la matriz en el activity_main.xml a la matriz de botones "matriz" del objeto "ejercicio" (de la clase "Ejercicio") en el MainActivity.java.
            } //Fin del primer for (principal) en el que se comienza asignando los botones de la matriz en el activity_main.xml a la matriz de botones "matriz" del objeto "ejercicio" (de la clase "Ejercicio") en el MainActivity.java.

            //----------Primer ejercicio creado aleatoriamente (con el contador del ejercicio "currentExcercise" = 0)----------//

            //*************************
            ejercicio.setMatrix(palabras[currentExercise]);
            audio = MediaPlayer.create(EjercicioSopa.this, audios[currentExercise]);
            audio.start();

            //---------- Botones NEXT, PREV, RETRY y AUDIO ----------//
            btnNext = (ImageButton) findViewById(R.id.btnNext);
            btnPrev= (ImageButton) findViewById(R.id.btnPrev);
            btnRetry = (ImageButton) findViewById(R.id.btnRetry);
            btnAudio = (Button) findViewById(R.id.btnAudio);

            //----------Caso en el que se llega al primer o Ãºltimo ejercicio----------//
            //-----("Desaparecen" los botones de NEXT o PREV)-----//
            if(currentExercise == 0){
                btnPrev.setEnabled(false);
                btnPrev.setVisibility(View.GONE);
            } else{
                if(currentExercise == numEjercicios){
                    btnNext.setEnabled(false);
                    btnNext.setVisibility(View.GONE);
                }
            }

            //----------NEXT----------(InteracciÃ³n)//
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(puntaje[currentExercise] < puntosAcum){
                        puntaje[currentExercise] = puntosAcum;
                    } else {
                        puntaje[currentExercise] = puntaje[currentExercise];
                    }
                    puntosAcum = 0f;
                    currentExercise++;
                    if(currentExercise == palabras.length - 1){
                        btnNext.setEnabled(false);
                        btnNext.setVisibility(View.GONE);
                    }
                    wordInput = "";
                    for(int i = 0; i <= currentButton; i++){
                        idClick[i] = 0;
                    }
                    currentButton = -1;
                    coloredText.clear();
                    coloredText.append(palabras[currentExercise]);
                    textViewWord.setText(coloredText);
                    audio.release();
                    audio = MediaPlayer.create(EjercicioSopa.this, audios[currentExercise]);
                    audio.start();
                    //audio = MediaPlayer.create(this, audios[currentExercise]);
                    ejercicio.setMatrix(palabras[currentExercise]);
                    btnPrev.setEnabled(true);
                    btnPrev.setVisibility(View.VISIBLE);
                    textViewWord.setText(palabras[currentExercise]);
                    estrellas.setRating(0f);
                }
            });

            //----------PREV----------(InteracciÃ³n)//
            btnPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(puntaje[currentExercise] < puntosAcum){
                        puntaje[currentExercise] = puntosAcum;
                    } else {
                        puntaje[currentExercise] = puntaje[currentExercise];
                    }
                    puntosAcum = 0f;
                    currentExercise--;
                    if(currentExercise == 0){
                        btnPrev.setEnabled(false);
                        btnPrev.setVisibility(View.GONE);
                    }
                    wordInput = "";
                    for(int i = 0; i <= currentButton; i++){
                        idClick[i] = 0;
                    }
                    currentButton = -1;
                    coloredText.clear();
                    coloredText.append(palabras[currentExercise]);
                    textViewWord.setText(coloredText);
                    audio.release();
                    audio = MediaPlayer.create(EjercicioSopa.this, audios[currentExercise]);
                    audio.start();
                    ejercicio.setMatrix(palabras[currentExercise]);
                    btnNext.setEnabled(true);
                    btnNext.setVisibility(View.VISIBLE);
                    textViewWord.setText(palabras[currentExercise]);
                    estrellas.setRating(0f);
                }
            });

            //----------RETRY----------(InteracciÃ³n)//
            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    puntosAcum = 0f;
                    wordInput = "";
                    for(int i = 0; i <= currentButton; i++){
                        idClick[i] = 0;
                    }
                    currentButton = -1;
                    coloredText.clearSpans();
                    textViewWord.setText(coloredText);
                    ejercicio.setMatrix(palabras[currentExercise]);
                    estrellas.setRating(0f);
                }
            });

            //----------Audio----------(InteracciÃ³n)//

        btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio.release();
                audio = MediaPlayer.create(EjercicioSopa.this, audios[currentExercise]);
                audio.start();
            }
        });

            //--------------------------(CORREGIR InteracciÃ³n del Audio)

            //----------Prueba del guardado de puntos por ejercicio (BORRAR)----------//
            //puntaje[currentExcercise]


        } //Fin del onCreate
        //-------------------------------------------------------------------------------------------
        //Metodo que se llama cada vez que la Actividad recibe una nueva actividad
        //SI ESTE METODO NO ESTA DECLARADO, EL EJERCICIO CUANDO ES SELECCIONADO EN EL SUBMENU SOLO ES
        //CAMBIADO LA PRIMERA VEZ, PERO DESPUES YA NO PORQUE EL INTENT Y SUS DATOS NO SE ACTUALIZAN
        public void onNewIntent(Intent inten)
        {
            //Actualizar los datos deL intent de la actividad anterior, por dados del neuvo intent
            super.onNewIntent(inten);
            this.setIntent(inten);
        }
        //----------------------------------------------------------------------------------------------
        //Metodo que ejecuta las acciones cuando una vez que el usuario a dejado de usar una activity
        //pero la vuelve a abrir, entonces entra en ejecucion este metodo
        public void onResume()
        {
            super.onResume();

            //Incializar tiempo de uso de la aplicación
            INICIOTIME = System.currentTimeMillis();

            //----OBTENER POSICION DE EJERCICIO QUE DEBE SER DESPLEGADO Y MOSTRARLO---------------------
            inten = getIntent();
            //El 0 es un valor de dfault por si no se encuentra el valor que se buscaba, se asigna 0 por
            //default
            currentExercise = inten.getIntExtra("INDXEX",0);
            Log.d("eeeinx:","REANUDADO");
            Log.d("eeeinx:",""+currentExercise);

            //HACER TODOO EL PROCESO PARA CAMBIAR AL EJERCICIO ACTUAL
            //Parche para ultimo ejercicio sopa de letras
            if(currentExercise == palabras.length - 1){
                btnNext.setEnabled(false);
                btnNext.setVisibility(View.GONE);
            }
            //--------------------------------------------------------------------------
            puntosAcum = 0f;
            wordInput = "";
            for(int i = 0; i <= currentButton; i++){
                idClick[i] = 0;
            }
            currentButton = -1;
            coloredText.clearSpans();
            textViewWord.setText(coloredText);
            audio.release();
            audio = MediaPlayer.create(EjercicioSopa.this, audios[currentExercise]);
            audio.start();
            ejercicio.setMatrix(palabras[currentExercise]);
            estrellas.setRating(0f);

            //-------------- FIN MOSTRAR EJERCICIO CORRESPONDIENTE SOPA DE LETRAS-------------------------

        }//Fin metodo onResume
        //----------------------------------------------------------------------------------------------
        //Metodo que se incia cuando se mueve a otra actividad
        public void onStop()
        {
            super.onStop();
            //***Obtener Datos Actuales del usario desde la BDS
            inten = getIntent();
            IDCURRENTUSER = inten.getIntExtra("IDUSER",0);

            //OBTENER Y ACTUALIZAR DATOS ACTUALES CON LA BDS SOLO SU HAY CONEXION A LA BDS
            if(IDCURRENTUSER != -1)
            {
                CURRENTUSER = new Usuario(IDCURRENTUSER, this);

                //***Actualizar Datos de Usuario con puntuacion Actual
                //arregloDePuntos/Número de ejercicio 2(3) y Numero de ejercicicos creados
                CURRENTUSER.LevelProgress(puntaje, 3, NUMEXCER);
                //ACTUALIZAR EL RESTO DE LOS CALCULOS ESTADISTICOS
                FINTIME = System.currentTimeMillis();
                FULLTIME = FINTIME - INICIOTIME;
                CURRENTUSER.calcularUserData(FULLTIME);
                //RESETEAR RELOJES
                FULLTIME = 0;
                INICIOTIME = 0;
                FINTIME = 0;

                //***Realizar sobre escritura informacion de usuario en BDs
                CURRENTUSER.updtadeDataDB(this);

            }//FIN IF 1

        }//Fin metodo onStop
        //----------------------------------------------------------------------------------------------
        //Metodo que se incia cuando se destruye completamente la actividad
        public void onDestroy()
        {
            super.onDestroy();

            //***Obtener Datos Actuales del usario desde la BDS
            Intent inten = getIntent();
            IDCURRENTUSER = inten.getIntExtra("IDUSER",0);

            //OBTENER DATOS DE BDS Y ACTUALZIARLOS SOLAMENTE SI HAY CONEXION A LA BDS
            if(IDCURRENTUSER != -1)
            {
                CURRENTUSER = new Usuario(IDCURRENTUSER, this);

                //***Actualizar Datos de Usuario con puntuacion Actual
                //arregloDePuntos/Número de ejercicio 2(3) y Numero de ejercicicos creados
                CURRENTUSER.LevelProgress(puntaje, 3, NUMEXCER);
                //ACTUALIZAR EL RESTO DE LOS CALCULOS ESTADISTICOS
                FINTIME = System.currentTimeMillis();
                FULLTIME = FINTIME - INICIOTIME;
                CURRENTUSER.calcularUserData(FULLTIME);

                //***Realizar sobreedcritura informacion de usuario en BDs
                CURRENTUSER.updtadeDataDB(this);

            }//FIN IF 1

        }//Fin metodo onDestroy
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



}//FIn metodo ejercicio sopa
