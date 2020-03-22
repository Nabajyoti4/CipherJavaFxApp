package sample;

import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;

/*
 * class conatins all Alert methods
 */
public class Alerts {

    /*
     * Method for simple notification
     */
    public void alert1(String title, String info){
        Alert a1 = new Alert(Alert.AlertType.INFORMATION);
        a1.setTitle(title);
        a1.setContentText(info);
        a1.setHeaderText(null);
        a1.initStyle(StageStyle.UTILITY);
        a1.showAndWait();
    }

    /*
     * Method for warnings
     */
    public void warning(String title, String error){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait();
    }
}
