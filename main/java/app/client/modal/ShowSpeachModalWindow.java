package app.client.modal;

import app.DTO.Speach;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class ShowSpeachModalWindow {
    private Dialog dialog;
    public void open(Speach speach)
    {
        init(speach);
        dialog.show();
    }
    private void init(Speach speach)
    {
        dialog=new Dialog();
        dialog.setTitle("Информация о сеансе");


        VBox mainVbox=new VBox();
        mainVbox.setSpacing(5);
        mainVbox.setAlignment(Pos.CENTER_LEFT);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        //Id
        HBox hBoxFirst=new HBox();
        hBoxFirst.setSpacing(5);
        hBoxFirst.setAlignment(Pos.CENTER_LEFT);
        hBoxFirst.getChildren().add(new Label("Id:"));
        hBoxFirst.getChildren().add(new Label(speach.getId().toString()));
        hBoxFirst.getChildren().add(new Label("Дата:"));
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd.MM.yyyy");
        hBoxFirst.getChildren().add(new Label(simpleDateFormat.format(speach.getDate())));
        mainVbox.getChildren().add(hBoxFirst);

        HBox hBoxSecond=getHbox();
        hBoxSecond.getChildren().add(new Label("Событие: "));
        hBoxSecond.getChildren().add(new Label( speach.getEvent()));
        mainVbox.getChildren().add(hBoxSecond);

        HBox hBox3=getHbox();
        hBox3.getChildren().add(new Label("Станция: "));
        hBox3.getChildren().add(new Label( speach.getStation()));
        mainVbox.getChildren().add(hBox3);




        gridPane.add(new Label("IMEI:"),0,0);
        gridPane.add(new Label(speach.getIMEI()),1,0);

        gridPane.add(new Label("IMSI:"),0,1);
        gridPane.add(new Label(speach.getIMSI()),1,1);

        gridPane.add(new Label("LAC:"),0,2);
        gridPane.add(new Label(speach.getLAC()),1,2);

        gridPane.add(new Label("CID:"),0,3);
        gridPane.add(new Label(speach.getLAC()),1,3);

        gridPane.add(new Label("Оператор:"),0,4);
        gridPane.add(new Label(speach.getOperator()),1,4);

        gridPane.add(new Label("Частота:"),0,5);
        gridPane.add(new Label(speach.getS_FREQUENCY()),1,5);

        mainVbox.getChildren().add(gridPane);

        dialog.getDialogPane().setContent(mainVbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
    }
    HBox getHbox(){
        HBox hBox=new HBox();
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER_LEFT);
        return hBox;
    }
}
