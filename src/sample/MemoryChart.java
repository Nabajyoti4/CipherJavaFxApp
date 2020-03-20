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

    @FXML
    public void setChart(double hillMemory ,double playMemory, double vigMemory, double verMemory){

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


    public void handle(MouseEvent mouseEvent) {

        System.out.println("mouse clicked");


        for (final PieChart.Data data : cpuPie.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                per.setLayoutX(mouseEvent.getSceneX());
                per.setLayoutY(mouseEvent.getSceneY());
                System.out.println(data.getPieValue());
                per.setText(String.valueOf(data.getPieValue()) + "%.2fMB");
            });
        }
    }



    public void setCpu(double hillCpu ,double playCpu, double vigCpu, double verCpu){

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
        cpuPie.setTitle("Cpu Usage Chart");





    }

}
