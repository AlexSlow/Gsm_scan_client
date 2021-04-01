package app.client.modal;

import app.DTO.Client;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import lombok.Data;

import java.util.Optional;

@Data
public class ChooseCurrentClient {
    private Dialog<Client> dialog;
    Optional<Client> client;

    public void open(Client client)
    {
        init(client);
        this.client=dialog.showAndWait();
    }
    private void init(Client currentClient)
    {
        dialog=new Dialog();
        dialog.setTitle("Текущий пользователь");
        ButtonType saveButton = new ButtonType("Сохранить", ButtonBar.ButtonData.APPLY);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE,saveButton);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField userName = new TextField();
        userName.setText(currentClient.getName());
        userName.setPromptText("Имя пользователя");
        gridPane.add(new Label("Имя пользователя: "),0,0);
        gridPane.add(userName,1,0);

        TextField lon = new TextField();
        lon.setText(String.valueOf(currentClient.getLon()));
        lon.setPromptText("lon");
        gridPane.add(new Label("Долгота: "),0,1);
        gridPane.add(lon,1,1);

        TextField lat = new TextField();
        lat.setText(String.valueOf(currentClient.getLat()));
        lat.setPromptText("lat");
        gridPane.add(new Label("Широта: "),0,2);
        gridPane.add(lat,1,2);

        dialog.getDialogPane().setContent(gridPane);

        dialog.setResultConverter(dialogButton -> {

            //Вывод результатов в пару
            if (dialogButton == saveButton) {
                Client  clientRet=new Client();
                clientRet.setName(userName.getText());
                clientRet.setLat(Integer.parseInt(lat.getText()));
                clientRet.setLon(Integer.parseInt(lon.getText()));
                return clientRet;
            }
            return currentClient;
        });
    }


}
