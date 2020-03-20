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

    /*
     * Cpu usage variables
     * Memory usage variables
     */
    public double hillMemory = 0,playMemory = 0,vigMemory = 0,verMemory = 0;
    public double hillCpu = 0,playCpu = 0,vigCpu = 0 ,verCpu = 0;



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



    /* method to check which radio button is selected */
    public void cipher(ActionEvent actionEvent){

        //for hill cipher message box
        if(hill.isSelected()){
            System.out.println("hill");
            valikey.setVisible(true);
            keytext.setEditable(true);
            JOptionPane.showMessageDialog(null, "Hill selected Enter A key of 2 X 2 matrix");

        }

        //for play cipher mesage box
        if(play.isSelected()){
            System.out.println("play");
            valikey.setVisible(false);
            keytext.setEditable(true);
            JOptionPane.showMessageDialog(null, "Play Selected Enter A key");

        }


        //for vigenere cipher message box
        if(vign.isSelected()){
            System.out.println("vigenere");
            valikey.setVisible(false);
            keytext.setEditable(true);
            JOptionPane.showMessageDialog(null, "Vigenere Selected Enter A key");

        }


        //for vernam cipher message box
        if(vern.isSelected()){
            System.out.println("Vernam");
            valikey.setVisible(false);
            keytext.setEditable(true);
            JOptionPane.showMessageDialog(null, "Vernam Selected Enter A key");

        }

    }


    /* validate key button */
    @FXML
    public void validate(ActionEvent actionEvent){
                String key = keytext.getText().toLowerCase();


                //return true if key is accepted
                // returns false if key is not inversible or smaller
                if(hillCipher.keyGenrate(key)){
                    hillMemory = 0;
                    JOptionPane.showMessageDialog(null, "Key Succesfully Accepted Enter plain Text");

                    //enable plain text field to enter the plain text
                    pltext.setEditable(true);

                }else{
                    //if key is not accepted renter again
                    JOptionPane.showMessageDialog(null, "Key cannot be used re-enter");
                }

                /*
                 * return total memory usage in MB
                 */
                 hillMemory = hillMemory + performance.checkMemory();
                 System.out.println(hillMemory);




                hillCpu =  hillCpu + performance.cpuUsage();

    }



    //Encrypt button function
    @FXML
    public void enbtn() throws IOException {

        //////////////////// ENCRYPTION FOR HILL CIPHER //////////////////////////////
        if(hill.isSelected()){



            //send the plain text and key to hill class
            String plainText = pltext.getText().toLowerCase();


            //call encrypt method to encrypt plain text
            //returns cipher text
            String cipherText = hillCipher.encryptText(plainText);

            //store encrypt text in encrypt field
            enText.setText(cipherText);

            //show message
            JOptionPane.showMessageDialog(null, "Message succesfully encrypted");

            hillMemory = hillMemory + performance.checkMemory();
            System.out.println(hillMemory);




            hillCpu =  hillCpu + performance.cpuUsage();


        }


        /////////////////////// ENCRYPTION FORM PLAY CIPHER /////////////////////////////
        if(play.isSelected()){




            System.out.println("play");

            //take the key from text box
            String key = keytext.getText().toLowerCase();

            //Send the key to the play cipher to genrate the play cipher table
            playCipher.keyGenerate(key);



            //Take the plain text from the plain text box
            String plainText = pltext.getText().toLowerCase();


            //Send the plainText for encryption
            //returns the cipher text
            String cipherText = playCipher.encryption(plainText);

            //display the cipher text in encrypt box
            enText.setText(cipherText);


            playMemory = playMemory +  performance.checkMemory();



            playCpu =  playCpu + performance.cpuUsage();


        }


        /////////////////////// ENCRYPTION FORM VIGENERE CIPHER/////////////////////////////
        if(vign.isSelected()){



            String key = keytext.getText().toLowerCase();

            String text = pltext.getText().toLowerCase();

            String cipherText = vigenereCipher.encrypt(key,text);

            enText.setText(cipherText);


            vigMemory = vigMemory + performance.checkMemory();


            vigCpu =  vigCpu + performance.cpuUsage();

        }


        /////////////////////// ENCRYPTION FORM VERNAM CIPHER /////////////////////////////
        if(vern.isSelected()){



            String key = keytext.getText().toLowerCase();

            String plainText = pltext.getText().toLowerCase();

            //if key length is not equal to word
            if (key.length() != plainText.length()){
                JOptionPane.showMessageDialog(null,"Key length be equal to Word");
            }
            else{

                String cipherText = vernamCipher.encrypt(plainText,key);

                enText.setText(cipherText);
                verMemory = verMemory + performance.checkMemory();



                verCpu =  verCpu + performance.cpuUsage();
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

        }


        ///////////////////////////// FOR PLAY CIPHER /////////////////////////////////
        if(play.isSelected()){




            //Read the data from file
            BufferedReader f2 = new BufferedReader(new FileReader("play"));


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



    // Function to clear all field
    @FXML
    public void clearAll(){
        enText.setText("");
        pltext.setText("");
        detext.setText("");


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

}
