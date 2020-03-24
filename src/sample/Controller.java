/*
* controller takes event action from the fxml file
* used for sample.fxml file
 */
package sample;

/*
 * import packages for scene builder
 */
import com.sun.management.OperatingSystemMXBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.*;

/*
 * import packages for File operations
 */
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;


public class Controller {

    /*
     * class variables
     */
    @FXML
    public TextField pltext,keytext;
    public TextArea enText,detext;
    public Button debtn;
    public RadioButton hill,play,vign,vern;
    public Button valikey;
    public ToggleGroup ciphers1;

    /*
     * Cpu usage variables
     * Memory usage variables
     */
    public double hillMemory = 0,playMemory = 0,vigMemory = 0,verMemory = 0;
    public double hillCpu = 0,playCpu = 0,vigCpu = 0 ,verCpu = 0;

    /*
     * key validate boolean
     */
    boolean hillKeyValidation  = false;


    /*
     * Instance of all the classes to be used
     */

    //hill cipher class instance
    private HillCipher hillCipher = new HillCipher();

    //play cipher class instance
    private PlayCipher playCipher = new PlayCipher();


    //Vernam cipher class
    private VernamCipher vernamCipher = new VernamCipher();


    //Vigenere cipher class
    private VigenereCipher vigenereCipher = new VigenereCipher();


    //Performance class
    private Performance performance = new Performance();


    //Alert class
    private Alerts alerts = new Alerts();

    /* method to check which radio button is selected */
    public void cipher(ActionEvent actionEvent){

        //for hill cipher message box
        if(hill.isSelected()){
            System.out.println("hill");
            valikey.setVisible(true);
            keytext.setEditable(true);
            alerts.alert1("Hill Cipher","Give a key of matrix 2 x 2 and validate the key");

        }

        //for play cipher mesage box
        if(play.isSelected()){
            System.out.println("play");
            valikey.setVisible(false);
            keytext.setEditable(true);
            alerts.alert1("Play Cipher","Enter key and plaintext");

        }


        //for vigenere cipher message box
        if(vign.isSelected()){
            System.out.println("vigenere");
            valikey.setVisible(false);
            keytext.setEditable(true);
            alerts.alert1("Vigenere Cipher","Enter key and plain text");

        }


        //for vernam cipher message box
        if(vern.isSelected()){
            System.out.println("Vernam");
            valikey.setVisible(false);
            keytext.setEditable(true);
            alerts.alert1("Vernam Cipher","Enter key and plain text of same length");

        }

    }


    /* validate key button */
    @FXML
    public void validate(ActionEvent actionEvent){


                String key = keytext.getText().toLowerCase();


                if(checkKey()){
                    //return true if key is accepted
                    // returns false if key is not inversible or smaller
                    if(hillCipher.keyGenrate(key)){
                        hillMemory = 0;
                        alerts.alert1("Key Accepted", "Key Succesfully Accepted Enter plain Text");

                        //enable plain text field to enter the plain text
                        pltext.setEditable(true);
                        hillKeyValidation = true;

                    }else{
                        //if key is not accepted renter again
                        alerts.warning("Key Error", "Key cannot be used re-enter");
                        hillKeyValidation = false;
                    }

                    /*
                     * return total memory usage in MB
                     */
                    hillMemory = hillMemory + performance.checkMemory();
                    System.out.println(hillMemory);



                    hillCpu =  hillCpu + performance.cpuUsage();
                }





    }



    //Encrypt button function
    @FXML
    public void enbtn() throws IOException {

        //if no plain text found in field
        // error message
        if (pltext.getText().isEmpty()){
            alerts.warning("Plain Text Not found", "Enter Plain text To Encrypt");
        }

        // else start encryption
        else{
            //////////////////// ENCRYPTION FOR HILL CIPHER //////////////////////////////
            if(hill.isSelected()){
                if(hillKeyValidation){
                    //send the plain text and key to hill class
                    String plainText = pltext.getText().toLowerCase();

                    //check for wrong input in plain text
                    if(checkPlainText(plainText)){

                        //call encrypt method to encrypt plain text
                        //returns cipher text
                        String cipherText = hillCipher.encryptText(plainText);

                        //store encrypt text in encrypt field
                        enText.setText(cipherText);

                        //show message
                        alerts.alert1("Encryption Done", "Message Succesfully encrypted and stored in file hill.txt");

                        hillMemory = hillMemory + performance.checkMemory();
                        System.out.println(hillMemory);



                        hillCpu =  hillCpu + performance.cpuUsage();

                        debtn.setDisable(false);
                    }

                }else{
                    alerts.warning("Key Validation Error", "Validate Key First to Encrypt Text");
                }


            }


            /////////////////////// ENCRYPTION FORM PLAY CIPHER /////////////////////////////
            if(play.isSelected()){

                // check for wrong input in key
                if(checkKey()){

                    String plainText = pltext.getText().toLowerCase();

                    if (checkPlainText(plainText)){

                        System.out.println("play");

                        //take the key from text box
                        String key = keytext.getText().toLowerCase();


                        //Send the key to the play cipher to genrate the play cipher table
                        playCipher.keyGenerate(key);



                        //Take the plain text from the plain text box



                        //Send the plainText for encryption
                        //returns the cipher text
                        String cipherText = playCipher.encryption(plainText);

                        //display the cipher text in encrypt box
                        enText.setText(cipherText);



                        debtn.setDisable(false);


                        playMemory = playMemory +  performance.checkMemory();



                        playCpu =  playCpu + performance.cpuUsage();

                        //show message
                        alerts.alert1("Encryption Done", "Message Succesfully encrypted and stored in file play.txt");
                    }


                }



            }


            /////////////////////// ENCRYPTION FORM VIGENERE CIPHER/////////////////////////////
            if(vign.isSelected()){

                if(checkKey()){

                    String key = keytext.getText().toLowerCase();

                    String text = pltext.getText().toLowerCase();

                    if(checkPlainText(text)){
                        String cipherText = vigenereCipher.encrypt(key,text);

                        enText.setText(cipherText);

                        //show message
                        alerts.alert1("Encryption Done", "Message Succesfully encrypted and stored in file vig.txt");



                        debtn.setDisable(false);


                        vigMemory = vigMemory + performance.checkMemory();


                        vigCpu =  vigCpu + performance.cpuUsage();
                    }


                }



            }


            /////////////////////// ENCRYPTION FORM VERNAM CIPHER /////////////////////////////
            if(vern.isSelected()){

                 if(checkKey()){


                     String plainText = pltext.getText().toLowerCase();

                     if (checkPlainText(plainText)){

                         String key = keytext.getText().toLowerCase();
                         //if key length is not equal to word
                         if (key.length() != plainText.length()){
                             //show message
                             alerts.warning("Key length not match", "key Length Must be equal to text");
                         }
                         else{

                             String cipherText = vernamCipher.encrypt(plainText,key);

                             enText.setText(cipherText);

                             //show message
                             alerts.alert1("Encryption Done", "Message Succesfully encrypted and stored in file ver.txt");


                             debtn.setDisable(false);

                             verMemory = verMemory + performance.checkMemory();



                             verCpu =  verCpu + performance.cpuUsage();
                         }
                     }


                 }


            }

        }


    }

    /*
     * check for speacial characters and numbers in the plain text
     * return true if not found any
     * return false if found
     */
    private boolean checkPlainText(String text) {

        String[] wordList = text.split(" ");

        if(vern.isSelected()){ // check if the plain text have more than one word in vernam
            if(wordList.length > 1){
                alerts.warning("Plain Text Too Long", "Plain text For vernam must be only one word for now");
                return false;
            }
            return true;
        }else{
            for (String word : wordList){

                Pattern p = Pattern.compile("[^A-Za-z]");
                Matcher m = p.matcher(word);

                if(m.find()){
                    alerts.warning("Plain Text Error", "Plain text with numbers and special characters are not allowed Re enter");
                    return false;
                }
                return true;
            }
        }

        return false;
    }


    //decrypt button function
    @FXML
    public void debtn() throws IOException {
        System.out.println("decrpt");

        /////////////////// FOR HILL CIPHER ////////////////////////////////
        if(hill.isSelected()){
            if (hillKeyValidation){
                //Read the data from file
                BufferedReader f2 = new BufferedReader(new FileReader("hill.txt"));


                //Store the data in a variable
                //store the text in a words string
                String words =  f2.readLine().toLowerCase();


                //Send the data to hill decrypt method for decryption
                //returns normal text
                String decryptText  = hillCipher.decryptCipher(words);


                //Store the text in decrypt textfield
                detext.setText(decryptText);

                // get memory usage
                hillMemory = hillMemory + performance.checkMemory();
                System.out.println(hillMemory);



                hillCpu =  hillCpu + performance.cpuUsage();
            }else{
                alerts.warning("Key Validation Error", "Validate Key First to Encrypt Text");
            }




        }


        ///////////////////////////// FOR PLAY CIPHER /////////////////////////////////
        if(play.isSelected()){




            //Read the data from file
            BufferedReader f2 = new BufferedReader(new FileReader("play.txt"));


            //Store the data in a variable
            //store the text in a words string
            String words =  f2.readLine().toLowerCase();


            //send msg for decryption
            //return normal text
            String decryptText = playCipher.decryption(words);


            //Display the text in field
            detext.setText(decryptText);

            playMemory = playMemory +  performance.checkMemory();



            playCpu =  playCpu + performance.cpuUsage();
        }


        /////////////////////// DECRYPTION FORM VIGENERE CIPHER/////////////////////////////
        if(vign.isSelected()){




            String key = keytext.getText().toLowerCase();

            String decryptText = vigenereCipher.decrypt(key);

            detext.setText(decryptText);


            vigMemory = vigMemory + performance.checkMemory();


            vigCpu =  vigCpu + performance.cpuUsage();

        }



        /////////////////////// DECRYPTION FORM VERNAM CIPHER /////////////////////////////
        if(vern.isSelected()){



              String key = keytext.getText().toLowerCase();


                String decryptText = vernamCipher.decrypt(key);

                detext.setText(decryptText);

                verMemory = verMemory + performance.checkMemory();



            verCpu =  verCpu + performance.cpuUsage();


        }

    }

    /*
     * Check for mouse event to restrict input of plain text
     * if no key is given in key box
     * if empty not plain tet can be entered
     */
    public void emptyKey(MouseEvent mouseEvent) {
        if(keytext.getText().isEmpty()){
            alerts.warning("Key Not Found", " Enter Key Or choose Cipher to proceed with encryption and decryption");
        }else{
            pltext.setEditable(true);
        }
    }



    // Function to clear all field
    @FXML
    public void clearAll(){
        enText.setText("");
        pltext.setText("");
        detext.setText("");
        keytext.setText("");
        hill.setSelected(false);
        play.setSelected(false);
        vern.setSelected(false);
        vign.setSelected(false);


    }


    //Diasplay Memory Chart
    @FXML
    public void memoryChart(){

        try {
            //open new parent pane
            Stage primaryStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("memorychart.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            primaryStage.setTitle("Memory Chart");
            primaryStage.setScene(new Scene(root1, 1139, 708));

            //Send dat from controller to chart controller
            MemoryChart memoryChart = (MemoryChart) fxmlLoader.getController() ;
            memoryChart.setChart(hillMemory,playMemory,vigMemory,verMemory);

            primaryStage.show();
        }catch (IOException io){
            System.out.println("IO ex : " + io.getMessage());
        }
    }


    @FXML
    public void cpuChart(){
        try {
            //open new parent pane
            Stage primaryStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("memorychart.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            primaryStage.setTitle("CPU Chart");
            primaryStage.setScene(new Scene(root1, 1139, 708));

            //Send dat from controller to chart controller
            MemoryChart memoryChart = (MemoryChart) fxmlLoader.getController() ;
            memoryChart.setCpu(hillCpu,playCpu,vigCpu,verCpu);

            primaryStage.show();
        }catch (IOException io){
            System.out.println("IO ex : " + io.getMessage());
        }
    }


    /*
     * check if there is any speacial charters or numbers are there in key
     */
    public boolean checkKey(){
        String key = keytext.getText();

        Pattern p = Pattern.compile("[^A-Za-z]");
        Matcher m = p.matcher(key);

        if(m.find()){
            alerts.warning("Key error", "Key with numbers and special characters are not allowed");
            return false;
        }
        return true;


    }



}
