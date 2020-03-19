package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Controller {

    @FXML
    public TextField pltext,keytext;
    public TextArea enText,detext;
    public Button debtn;
    public RadioButton hill,play,vign,vern;
    public Button valikey;

    //hill cipher class instance
    private HillCipher hillCipher = new HillCipher();

    //play cipher class instance
    private PlayCipher playCipher = new PlayCipher();


    //Vernam cipher class
    private VernamCipher vernamCipher = new VernamCipher();


    //Vigenere cipher class
    private VigenereCipher vigenereCipher = new VigenereCipher();




    /* method to check which radio button is selected */
    public void cipher(ActionEvent actionEvent){

        //for hill cipher message box
        if(hill.isSelected()){
            System.out.println("hill");

            keytext.setEditable(true);
            JOptionPane.showMessageDialog(null, "Hill selected Enter A key of 2 X 2 matrix");

        }

        //for play cipher mesage box
        if(play.isSelected()){
            System.out.println("play");
            keytext.setEditable(true);
            valikey.setVisible(false);
            JOptionPane.showMessageDialog(null, "Play Selected Enter A key");

        }


        //for vigenere cipher message box
        if(vign.isSelected()){
            System.out.println("vigenere");
            keytext.setEditable(true);
            valikey.setVisible(false);
            JOptionPane.showMessageDialog(null, "Vigenere Selected Enter A key");

        }


        //for vernam cipher message box
        if(vern.isSelected()){
            System.out.println("Vernam");
            keytext.setEditable(true);
            valikey.setVisible(false);
            JOptionPane.showMessageDialog(null, "Vernam Selected Enter A key");

        }

    }


    /* validate key button */
    @FXML
    public void validate(ActionEvent actionEvent){
                String key = keytext.getText();

                //return true if key is accepted
                // returns false if key is not inversible or smaller
                if(hillCipher.keyGenrate(key)){
                    JOptionPane.showMessageDialog(null, "Key Succesfully Accepted Enter plain Text");

                    //enable plain text field to enter the plain text
                    pltext.setEditable(true);

                }else{
                    //if key is not accepted renter again
                    JOptionPane.showMessageDialog(null, "Key cannot be used re-enter");
                }

    }



    //Encrypt button function
    @FXML
    public void enbtn() throws IOException {

        //////////////////// ENCRYPTION FOR HILL CIPHER //////////////////////////////
        if(hill.isSelected()){

            //send the plain text and key to hill class
            String plainText = pltext.getText();


            //call encrypt method to encrypt plain text
            //returns cipher text
            String cipherText = hillCipher.encryptText(plainText);

            //store encrypt text in encrypt field
            enText.setText(cipherText);

            //show message
            JOptionPane.showMessageDialog(null, "Message succesfully encrypted");


        }


        /////////////////////// ENCRYPTION FORM PLAY CIPHER /////////////////////////////
        if(play.isSelected()){


            System.out.println("play");

            //take the key from text box
            String key = keytext.getText();

            //Send the key to the play cipher to genrate the play cipher table
            playCipher.keyGenerate(key);



            //Take the plain text from the plain text box
            String plainText = pltext.getText();


            //Send the plainText for encryption
            //returns the cipher text
            String cipherText = playCipher.encryption(plainText);

            //display the cipher text in encrypt box
            enText.setText(cipherText);

        }


        /////////////////////// ENCRYPTION FORM VIGENERE CIPHER/////////////////////////////
        if(vign.isSelected()){

            String key = keytext.getText();

            String text = pltext.getText();

            String cipherText = vigenereCipher.encrypt(key,text);

            enText.setText(cipherText);

        }


        /////////////////////// ENCRYPTION FORM VERNAM CIPHER /////////////////////////////
        if(vern.isSelected()){

            String key = keytext.getText();

            String plainText = pltext.getText();

            //if key length is not equal to word
            if (key.length() != plainText.length()){
                JOptionPane.showMessageDialog(null,"Key length be equal to Word");
            }
            else{

                String cipherText = vernamCipher.encrypt(plainText,key);

                enText.setText(cipherText);
            }



        }

    }



    //decrypt button function
    @FXML
    public void debtn() throws IOException {
        System.out.println("decrpt");

        /////////////////// FOR HILL CIPHER ////////////////////////////////
        if(hill.isSelected()){

            //Read the data from file
            BufferedReader f2 = new BufferedReader(new FileReader("hill"));


            //Store the data in a variable
            //store the text in a words string
            String words =  f2.readLine();


            //Send the data to hill decrypt method for decryption
            //returns normal text
            String decryptText  = hillCipher.decryptCipher(words);


            //Store the text in decrypt textfield
            detext.setText(decryptText);

        }


        ///////////////////////////// FOR PLAY CIPHER /////////////////////////////////
        if(play.isSelected()){
            //Read the data from file
            BufferedReader f2 = new BufferedReader(new FileReader("play"));


            //Store the data in a variable
            //store the text in a words string
            String words =  f2.readLine();


            //send msg for decryption
            //return normal text
            String decryptText = playCipher.decryption(words);


            //Display the text in field
            detext.setText(decryptText);
        }


        /////////////////////// DECRYPTION FORM VIGENERE CIPHER/////////////////////////////
        if(vign.isSelected()){

            String key = keytext.getText();

            String decryptText = vigenereCipher.decrypt(key);

            detext.setText(decryptText);

        }



        /////////////////////// DECRYPTION FORM VERNAM CIPHER /////////////////////////////
        if(vern.isSelected()){

            String key = keytext.getText();


                String decryptText = vernamCipher.decrypt(key);

               detext.setText(decryptText);


        }

    }




    // Function to clear all field
    @FXML
    public void clearAll(){
        enText.setText("");
        pltext.setText("");
        detext.setText("");


    }

}
