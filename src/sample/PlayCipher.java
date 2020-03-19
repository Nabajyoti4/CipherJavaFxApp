package sample;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;

public class PlayCipher {


    //5*5 matrix to create the playfiar table
    private char playTable[][] = new char[5][5];


    //Function for encryption
    public String encryption(String text) throws IOException {

        File file = new File("play");

        FileWriter f1 = new FileWriter(file,false);


        String[] wordList = text.split(" ");

        String cipherText = "";

        for(int words = 0;words < wordList.length;words++) {
            int lengthText = wordList[words].length();

            //If length is not even
            if (lengthText % 2 == 0) {
                wordList[words] = wordList[words];
            } else {
                wordList[words] = wordList[words]+  "x";
                lengthText++;
            }



            for (int i = 0; i < lengthText; i = i + 2) {
                char first = wordList[words].charAt(i);
                char second = wordList[words].charAt(i + 1);

                //get the index of two letters
                int firstRowIndex = 0, firstColIndex = 0, secondRowIndex = 0, secondColIndex = 0;
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < 5; k++) {
                        if (playTable[j][k] == first) {
                            firstRowIndex = j;//first index postion of first letter
                            firstColIndex = k;//second index postion of first letter
                            //System.out.println("Index of " + first + " j : " + j + " k : " + k);
                        }
                        if (playTable[j][k] == second) {
                            secondRowIndex = j;
                            secondColIndex = k;
                            //System.out.println("Index of " + second + " j : " + j + " k : " + k);
                        }
                    }//end of k loop
                }//end of getting index position loop for j


                //check whether the two letters are in same row
                //j gives row
                //k gives coloumn
                if (firstRowIndex == secondRowIndex) {//if the row index of both the letters are same
                    // both the letters are in same row
                    if (firstColIndex == 4 && secondColIndex == 4) {
                        firstColIndex = 0;
                        secondColIndex = 0;
                    }else if(secondColIndex == 4) {
                        firstColIndex++;
                        secondColIndex = 0;
                    } else if (firstColIndex == 4) { //if column index is last
                        firstColIndex = 0;
                        secondColIndex++;
                    }   else {
                        firstColIndex++;
                        secondColIndex++;
                    }

                    cipherText = cipherText + playTable[firstRowIndex][firstColIndex];
                    cipherText = cipherText + playTable[secondRowIndex][secondColIndex];


                } else if (firstColIndex == secondColIndex) {//if the column index is same for both
                    //both the letters are in same column
                    if (firstRowIndex == 4 && secondRowIndex == 4) {
                        firstRowIndex = 0;
                        secondRowIndex = 0;
                    }else if (secondRowIndex == 4) {
                        firstRowIndex++;
                        secondRowIndex = 0;
                    } else if (firstRowIndex == 4) {//if column index is last
                        firstRowIndex = 0;
                        secondRowIndex++;
                    } else {
                        firstRowIndex++;
                        secondRowIndex++;
                    }

                    //Add the char to cipher text
                    cipherText = cipherText + playTable[firstRowIndex][firstColIndex];
                    cipherText = cipherText + playTable[secondRowIndex][secondColIndex];

                } else {// else they are in rectangular form

                    //if first letter is on right
                    if (firstColIndex > secondColIndex) {
                        cipherText = cipherText + playTable[firstRowIndex][secondColIndex];
                        cipherText = cipherText + playTable[secondRowIndex][firstColIndex];
                    }
                    //else first letter is on left
                    else {
                        cipherText = cipherText + playTable[firstRowIndex][secondColIndex];
                        cipherText = cipherText + playTable[secondRowIndex][firstColIndex];
                    }

                }


            }//end of word iterate loop


            cipherText = cipherText + " ";
        }//Word list loop end

        System.out.println(cipherText);

        f1.write(cipherText);
        f1.flush();
        f1.close();


        return cipherText;


    }



    //function to decrypt the cipher text
    public String decryption(String cipherText) {

        String[] wordList = cipherText.split(" ");

        String plainText = "";

        for(int words = 0;words < wordList.length;words++) {
            int lengthText = wordList[words].length();


            for (int i = 0; i < lengthText; i = i + 2) {
                char first = wordList[words].charAt(i);
                char second = wordList[words].charAt(i + 1);

                //get the index of two letters
                int firstRowIndex = 0, firstColIndex = 0, secondRowIndex = 0, secondColIndex = 0;
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < 5; k++) {
                        if (playTable[j][k] == first) {
                            firstRowIndex = j;//first index postion of first letter
                            firstColIndex = k;//second index postion of first letter
                        }
                        if (playTable[j][k] == second) {
                            secondRowIndex = j;
                            secondColIndex = k;
                        }
                    }//end of k loop
                }//end of getting index position loop for j


                //check whether the two letters are in same row
                //j gives row
                //k gives coloumn
                if (firstRowIndex == secondRowIndex) {//if the row index of both the letters are same
                    // both the letters are in same row
                    if (firstColIndex == 0 && secondColIndex == 0) {
                        firstColIndex = 4;
                        secondColIndex = 4;
                    }else if (secondColIndex == 0) {
                        firstColIndex--;
                        secondColIndex = 4;
                    }  else if (firstColIndex == 0) { //if column index is last
                        firstColIndex = 4;
                        secondColIndex--;
                    } else {
                        firstColIndex--;
                        secondColIndex--;
                    }

                    plainText = plainText + playTable[firstRowIndex][firstColIndex];
                    plainText = plainText + playTable[secondRowIndex][secondColIndex];


                } else if (firstColIndex == secondColIndex) {//if the column index is same for both
                    //both the letters are in same column
                    if (firstRowIndex == 0 && secondRowIndex == 0) {
                        firstRowIndex = 4;
                        secondRowIndex = 4;
                    }else if (secondRowIndex == 0) {
                        firstRowIndex--;
                        secondRowIndex = 4;
                    } else if (firstRowIndex == 0) { //if column index is last
                        firstRowIndex = 4;
                        secondRowIndex--;
                    } else {
                        firstRowIndex--;
                        secondRowIndex--;
                    }

                    //Add the char to cipher text
                    plainText = plainText + playTable[firstRowIndex][firstColIndex];
                    plainText = plainText + playTable[secondRowIndex][secondColIndex];

                } else {// else they are in rectangular form

                    //if first letter is on right
                    if (firstColIndex > secondColIndex) {
                        plainText = plainText + playTable[firstRowIndex][secondColIndex];
                        plainText = plainText + playTable[secondRowIndex][firstColIndex];
                    }
                    //else first letter is on left
                    else {
                        plainText = plainText + playTable[firstRowIndex][secondColIndex];
                        plainText = plainText + playTable[secondRowIndex][firstColIndex];
                    }

                }



            }//end of word iterate loop


            plainText = plainText + " ";
        }//Word list loop end



        return plainText;
    }



    //Function to genarate the playfair table
    public void keyGenerate(String key) {

        // regex to remove duplicate char from the key
        String reverse = new StringBuilder(key).reverse().toString();
        String dupKey = reverse.replaceAll("(.)(?=.*\\1)","");
        String singleKey = new StringBuilder(dupKey).reverse().toString();



        //Hashmap to insert the key char and remaining alpha
        HashSet alpha = new HashSet();


        //loop to insert the chars in hashmap
        for (int i = 97;i<122;i++){
            int flag = 0;
            for (int j = 0;j<singleKey.length();j++){
                char value = singleKey.charAt(j);
                char keyAlpha = (char)i;

                //check if the alpha is in the key or not
                //if true increse flag
                if(keyAlpha == value){
                    flag++;
                    break;
                }
            }

            if(flag < 1){
                alpha.add((char)i);
            }
        }


        //insert the hashmap char in matrix
        Iterator q = alpha.iterator();
        int s = 0;
        for (int i = 0;i<5;i++){
            for (int j = 0; j< 5;j++){
                if(s < singleKey.length()){
                    playTable[i][j] = singleKey.charAt(s);
                    s++;
                }else{
                    if(q.hasNext()){
                        playTable[i][j] = (char)q.next();
                    }
                }
            }
        }



    }
}
