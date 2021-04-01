package app.client.modal;

import javafx.scene.control.Alert;

public class AlertWindows {
    public static void alert(String msg,String title){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);

        // alert.setHeaderText("Results:");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
