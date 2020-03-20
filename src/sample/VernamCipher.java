package sample;

import java.io.*;

public class VernamCipher {

    // decryption function
    public String decrypt(String key) throws IOException {
        String plainText = "";


        BufferedReader f1 = new BufferedReader(new FileReader("vernam"));


        String text = f1.readLine().toLowerCase();


        for (int i = 0; i< text.length(); i++){
            int asciiValue = (text.charAt(i) - 97) - (key.charAt(i) - 97);

            if(asciiValue < 0){
                asciiValue += 26;
            }

            plainText = plainText + (char)(asciiValue + 97);
        }

        System.out.println(plainText);

        return plainText;
    }

    // encryption function
    public String encrypt(String text,String key) throws IOException {



        File file = new File("vernam");


        FileWriter f1 = new FileWriter(file);


        String cipherText = "";


        for (int i = 0; i< text.length(); i++){

            int asciiValue = (text.charAt(i) - 97) + (key.charAt(i) - 97);

            if(asciiValue > 25){
                asciiValue %= 26;
            }

            cipherText = cipherText + (char)(asciiValue + 97);
        }

        System.out.println(cipherText);

        f1.write(cipherText);
        f1.close();



        return cipherText;
    }
}
