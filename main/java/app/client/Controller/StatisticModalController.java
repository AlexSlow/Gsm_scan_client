package app.client.Controller;

import app.DTO.StantionDto;
import app.DTO.StantionSpeachDTO;
import app.ServerConnection.Exception.ServerNotResponseException;
import app.ServerConnection.RestConnection;
import app.ServerConnection.ServerConnectionManager;
import app.client.GUI.Charts.MultipleSeriasCharts.MultipleSeriasChartDecorator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import lombok.Data;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

@Log4j
@Controller
@Data
public class StatisticModalController {
    @FXML
    private DatePicker DatePickerStart;
    @FXML private DatePicker DatePickerEnd;

    @FXML private Button btClose;
    @FXML private Button btBuild;
    @FXML private ListView<StantionDto> ServerList;

    private MultipleSeriasChartDecorator multipleSeriasChartDecorator;
    private RestConnection restConnection;
    private ObservableList<StantionDto> stantionDtos;
    @FXML
    protected void initialize() {

    ServerList.setItems(stantionDtos);
        ServerList.getSelectionModel ().
                setSelectionMode (SelectionMode.MULTIPLE);
        btClose.setOnAction(event->{
            close();
        });
        btBuild.setOnAction(event -> {
            List<StantionSpeachDTO> stantionSpeachDTOS=getSpeaches();
            multipleSeriasChartDecorator.buildAll(stantionSpeachDTOS);
            close();
        });
    }
    private void close(){
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.close();
    }

    private List<StantionSpeachDTO> getSpeaches(){
        log.info("Вход в получение данных ");
        List<StantionSpeachDTO> stantionSpeachDTOList=new ArrayList<>();
        java.sql.Date gettedDatePickerStart = java.sql.Date.valueOf(DatePickerStart.getValue());
        java.sql.Date gettedDatePickerEnd = java.sql.Date.valueOf(DatePickerEnd.getValue());
        if ((gettedDatePickerStart==null)||(gettedDatePickerEnd==null)) throw new RuntimeException("Не заполнены даты");
        if (ServerList.getSelectionModel().getSelectedItem()==null) throw new RuntimeException("Не выбраны станции");

        Date start=new Date(gettedDatePickerStart.getTime());
        Date end=new Date(gettedDatePickerEnd.getTime());



      ObservableList<StantionDto> stantionDtos=
              ServerList.getSelectionModel().getSelectedItems();
      stantionDtos.forEach(stantionDto -> {
          try {
              stantionSpeachDTOList.add(
              restConnection.getAllByPeriod(start,end,stantionDto.getId())
              );
          } catch (ServerNotResponseException e) {
              e.printStackTrace();
          }
      });
      return stantionSpeachDTOList;
    }
}
