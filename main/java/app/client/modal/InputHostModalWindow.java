package app.client.modal;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import lombok.Data;

import java.util.Optional;

@Data
public class InputHostModalWindow {
    private Dialog<String> dialog;
    Optional<String> host;
    public void open(String serverHost)
    {
        init(serverHost);
       host=dialog.showAndWait();
    }
    private void init(String serverHost)
    {
        dialog=new Dialog();
        dialog.setTitle("Настройка сервера");
        ButtonType saveButton = new ButtonType("Сохранить", ButtonBar.ButtonData.APPLY);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE,saveButton);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField hostTextFaild = new TextField();
        hostTextFaild.setText(serverHost);
        hostTextFaild.setPromptText("Сервер");
        Label label=new Label("URL и порт работы сервера: ");
        gridPane.add(label,0,0);
        gridPane.add(hostTextFaild,1,0);
        dialog.getDialogPane().setContent(gridPane);

        dialog.setResultConverter(dialogButton -> {

            //Вывод результатов в пару
            if (dialogButton == saveButton) {
                return hostTextFaild.getText();
            }
            return host.get();
        });
    }

}
