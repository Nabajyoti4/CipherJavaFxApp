package sample;

import java.io.*;

public class VigenereCipher {


    public String decrypt(String key) throws IOException {

        String plainText = "";


        BufferedReader f1 = new BufferedReader(new FileReader("vigenere"));


        String texts = f1.readLine();


        String[] words = texts.split(" ");

        for (String word : words){

            //genearte key according to length of cipher text
            String keyPerWord = generateKey(word, key);


            // now subtract each asciivalue of plain text with key asciivalue
            //according to the sequence
            for (int i = 0 ; i< word.length(); i++){

                int asciiValue = (word.charAt(i)-97) - (keyPerWord.charAt(i)-97);

                //if asciivalue is less then 0 then add 26 with the value
                if(asciiValue < 0){
                    asciiValue += 26;
                }

                plainText = plainText + (char)(asciiValue + 97);
            }

            plainText = plainText + " ";


        }

        System.out.println(plainText);

        return plainText;
    }

    public String encrypt(String key,String text) throws IOException {

        File file = new File("vigenere");


        FileWriter f1 = new FileWriter(file);


        String cipherText = "";


        String[] words = text.split(" ");


        //Generate key according to length of word
        //loop for words
        for(String word : words){

            /*function to generate key for word
             to generate key according to its length
             return key
             */
            String keyPerWord = generateKey(word,key);


            System.out.println(keyPerWord);



            // now add each asciivalue of plain text with key asciivalue
            //according to the sequence

            for (int i = 0 ; i< word.length(); i++){

                int asciiValue = (word.charAt(i)-97) + (keyPerWord.charAt(i)-97);

                if(asciiValue > 25){
                    asciiValue %= 26;
                }

                cipherText = cipherText + (char)(asciiValue + 97);
            }

            cipherText = cipherText + " ";


        }//end of word loop


        System.out.println(cipherText);

        f1.write(cipherText);
        f1.close();



        return cipherText;
    }

    public String generateKey(String word, String key) {
        String keyPerWord = key;
        int wordLength = word.length();
        int keyLength = key.length();


        int k = 0;
        //if key lenght is less than plain text
        if(keyLength < wordLength){

            //loop thorugh the word length
            for (int i = 0;i < wordLength; i++){

                // check if i reach the length of word
                if(i >= keyLength){

                    //conacte the key with new to make it same length as word
                    // key => "hi"
                    // text => "hello"
                    // keyPerword => "hihih"
                    keyPerWord = keyPerWord + key.charAt(k);
                    k++;
                    if(k == keyLength){
                        k = 0;
                    }
                }

            }
        }
        else if(keyLength > wordLength){

            //initilize key to null
            keyPerWord = "";
            for (int j = 0;j < wordLength; j++ ){
                keyPerWord = keyPerWord + key.charAt(j);
            }
        }
        else{
            key = key;
        }


        return keyPerWord;
    }
}
