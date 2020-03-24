package sample;

import javax.swing.*;
import java.io.*;
import java.math.BigInteger;

public class HillCipher {

    private int[][] keyMatrix = new int[2][2];



    // Function to generate key
    public boolean keyGenrate(String key) {


        // if key value length is 4 break the loop
        if (key.length() == 4) {

            //create a matrix of 2 x 2 with the unicode value of each char
            int k = 0;
            for (int  i = 0;i<2;i++){
                for (int j = 0;j<2;j++){
                    keyMatrix[i][j] = key.charAt(k) - 97;
                    k++;
                }
            }

            //dispaly keymatrix
            keyMatrixDisplay(keyMatrix);


            // check the determinant if it is greater then zero or not
            int determinant = ( keyMatrix[0][0] * keyMatrix[1][1] ) - ( keyMatrix[0][1] * keyMatrix[1][0] );
            System.out.println(determinant);



            //check for multiplicative inverse
            BigInteger biginteger1, biginteger2,result=null;

            // Initialize all BigInteger Objects
            biginteger1 = new BigInteger(String.valueOf(determinant));
            biginteger2 = new BigInteger(String.valueOf(26));

            // perform modInverse operation on biginteger1 using biginteger2.
            try {
                result = biginteger1.modInverse(biginteger2);
                System.out.println("Multiplicative inverse "+ result);
                keyMatrixDisplay(keyMatrix);
                return true;
            }catch (ArithmeticException e){
                System.out.println("Not invertable Enter key again");
                return false;
            }


        }//end of if length condition

        return false;
    }


    // function to encrypt text
    public String encryptText(String words) throws IOException {

        keyMatrixDisplay(keyMatrix);

        File file = new File("hill.txt");

        file.createNewFile();

        System.out.println(file.getAbsolutePath());

        FileWriter f1 = new FileWriter(file);


        String cipherText = "";


        // split the whole sentence in words
        String[] wordList = words.split(" ");



        // loop thorugh the words array
        for (String word : wordList) {


            //check if the plain text length is even or not
            if(word.length() % 2 != 0){
                word = word + "x";
            }


            //loop for crating cipher text
            for (int i = 0; i < word.length(); i = i + 2) {


                int x = i;

                for (int j = 0; j < 2; j++) {
                    int sum = 0;
                    int cipherAscii = 0;

                    for (int k = 0; k < 2; k++) {
                        //convert plain text to ascii
                        int ascii = word.charAt(x) - 97;

                        // generate the ascii value of cipher
                        sum = keyMatrix[j][k] * ascii;

                        //take the sum of first row with the plain text
                        cipherAscii = cipherAscii + sum;
                        x++;

                    }//end of k loop


                    // if value exceeds 25 then mod it with 26 to keep it in range 0 to 25
                    if (cipherAscii > 25) {
                        cipherAscii = cipherAscii % 26;
                    }

                    //concate the cipher text
                    cipherText = cipherText + (char) (cipherAscii + 97);
                    x = i;

                }//end j loop


            }//end of plain text loop

            System.out.println(cipherText);

            //add a space with the text
            cipherText = cipherText + " ";



        }

        //write the cipher in file
        f1.write(cipherText);


        //clean and  close the file
        f1.flush();
        f1.close();



        //return the cipher text
        return cipherText;
    }



    //Decryption of cipher text
    public String decryptCipher(String words) throws IOException {

        String plainText = "";


        // split the whole sentence in words
        String[] wordList = words.split(" ");


        //now we first find the inverse of key matrix

        // 1 . Adjoint of keymatrix

        //adjoint of matrix
        int temp = keyMatrix[0][0];
        keyMatrix[0][0] = keyMatrix[1][1];
        keyMatrix[1][1] = temp;
        keyMatrix[0][1] = -keyMatrix[0][1];
        keyMatrix[1][0] = -keyMatrix[1][0];



        // Multiplicative inverse of determinant
        int determinant = ( keyMatrix[0][0] * keyMatrix[1][1] ) - ( keyMatrix[0][1] * keyMatrix[1][0] );


        BigInteger biginteger1, biginteger2, result = null;


        // Initialize all BigInteger Objects
        biginteger1 = new BigInteger(String.valueOf(determinant));
        biginteger2 = new BigInteger(String.valueOf(26));


        // perform modInverse operation on biginteger1 using biginteger2.
        result = biginteger1.modInverse(biginteger2);


        // first add 26 with any number which is negative
        keyMatrix[0][1] = keyMatrix[0][1] + 26;
        keyMatrix[1][0] = keyMatrix[1][0] + 26;


        //now multiply the keymatrix with mod inverse
        for (int  i = 0;i<2;i++){
            for (int j = 0;j<2;j++){
                keyMatrix[i][j] = keyMatrix[i][j] * result.intValue();

                //now we mod 26 each number to keep them in range of 0 to 25
                if (keyMatrix[i][j] > 25){
                    keyMatrix[i][j] = keyMatrix[i][j] % 26;
                }
            }
        }

        keyMatrixDisplay(keyMatrix);


        //loop thorugh words
        for(String word : wordList) {

            // now multiple the keymatrix with cipher text to get the plain text back
            for (int i = 0; i < word.length(); i = i + 2) {

                int x = i;

                // row loop
                for (int j = 0; j < 2; j++) {
                    int sum = 0;
                    int cipherAscii = 0;

                    //column loop
                    for (int k = 0; k < 2; k++) {

                        //convert cipher text to ascii
                        int ascii = word.charAt(x) - 97;

                        // generate the ascii value of cipher
                        sum = keyMatrix[j][k] * ascii;

                        //take the sum of first row with the cipher text
                        cipherAscii = cipherAscii + sum;
                        x++;

                    }//end of column loop


                    // if value exceeds 25 then mod it with 26 to keep it in range 0 to 25
                    if (cipherAscii > 25) {
                        cipherAscii = cipherAscii % 26;
                    }

                    //concate the cipher text
                    plainText = plainText + (char) (cipherAscii + 97);

                    x = i;//again assign x to i to repeat from starting letter


                }//end row loop

            }//end of plain text loop
            //dding space between words
            plainText = plainText + " ";


        }//end of words loop

        System.out.println(plainText);


        return plainText;
    }


    // function to display keymatrix
    public static void keyMatrixDisplay(int[][] keyMatrix){
        for (int i = 0 ;i < 2;i++){
            for (int j = 0;j < 2;j++){
                System.out.print(keyMatrix[i][j]+"\t");
            }
            System.out.println("\n");
        }
    }
}