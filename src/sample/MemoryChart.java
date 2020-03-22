package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class MemoryChart  {


    @FXML
    public Label per;
    public PieChart cpuPie;
    public BarChart<String, Double> memoryBar;
    public double hillMemory = 0,playMemory = 0,vigMemory = 0,verMemory = 0;
    public double hillCpu = 0,playCpu = 0,vigCpu = 0 ,verCpu = 0;


    /*
     * function to create bar chart for the memery usage
     */
    @FXML
    public void setChart(double hillMemory ,double playMemory, double vigMemory, double verMemory){


        cpuPie.setVisible(false);

        XYChart.Series<String, Double> cpu = new XYChart.Series<>();
        XYChart.Series<String, Double> cpu1 = new XYChart.Series<>();
        XYChart.Series<String, Double> cpu2 = new XYChart.Series<>();
        XYChart.Series<String, Double> cpu3 = new XYChart.Series<>();

        this.hillMemory = hillMemory;
        this.playMemory = playMemory;
        this.vigMemory = vigMemory;
        this.verMemory = verMemory;




        cpu.getData().add(new XYChart.Data("Hill", hillMemory));
        cpu1.getData().add(new XYChart.Data("Play", playMemory));
        cpu2.getData().add(new XYChart.Data("Vigenere", vigMemory));
        cpu3.getData().add(new XYChart.Data("Vernam", verMemory));

        memoryBar.getXAxis().setLabel("CIPHERS ");

        memoryBar.getYAxis().setLabel("MEMORY USAGE IN MB");

        memoryBar.getData().addAll(cpu,cpu1,cpu2,cpu3);



    }


    /*
     * to show usgae in percentage on click of mouse
     */
    public void handle(MouseEvent mouseEvent) {


        for (final PieChart.Data data : cpuPie.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                per.setLayoutX(mouseEvent.getSceneX());
                per.setLayoutY(mouseEvent.getSceneY());
                per.setText(String.format("%.2f",data.getPieValue()));
            });
        }
    }



    /*
     * chart for cpu usage
     * Pie chart is shown based on given values
     * cpu usage will be in percentage
     */
    public void setCpu(double hillCpu ,double playCpu, double vigCpu, double verCpu){

        memoryBar.setVisible(false);


        this.hillCpu = hillCpu;
        this.playCpu = playCpu;
        this.vigCpu = vigCpu;
        this.verCpu = verCpu;


        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Hill Cipher", this.hillCpu),
                        new PieChart.Data("Play Cipher", this.playCpu),
                        new PieChart.Data("Vigenere Cipher", this.vigCpu),
                        new PieChart.Data("Vernam Memory", this.verCpu));
        cpuPie.setData(pieChartData);
        cpuPie.setTitle("      Cpu Usage Chart \nClick on the area to see Percentage");





    }

}
