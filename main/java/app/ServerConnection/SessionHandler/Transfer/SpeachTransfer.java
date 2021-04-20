package app.ServerConnection.SessionHandler.Transfer;
import app.DTO.Speach;
import app.DTO.StantionSpeachDTO;
import app.client.GUI.Charts.SingleSeriasFrontCharts.ChartControllerInterface;
import app.client.PageController;
import javafx.application.Platform;
import javafx.scene.control.TableView;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Data
public class SpeachTransfer implements StompSingleTransfer<StantionSpeachDTO> {
    private List<ChartControllerInterface> chartControllers=new ArrayList<>();
    private PageController pageController;
    private TableView<Speach> tableView;
    private boolean equalsFilter=false;
    @Override
    public synchronized void transfer(StantionSpeachDTO data) {
        if (!pageController.getStnationDtoId().equals(data.getStantionDto().getId()))
            return;

        if (equalsFilter){
           List<Speach> tableSpeach = tableView.getItems();
           List<Speach> deleteSpeach=new ArrayList<>();
           for(Speach speach:data.getSpeachList()){
               if (tableSpeach.contains(speach)) deleteSpeach.add(speach);
           }
           // Удалить все повторяющиеся записи
           data.getSpeachList().removeAll(deleteSpeach);

          // System.out.println("Удалено !"+ new Date());
          // System.out.println(deleteSpeach);
        }
        Platform.runLater(()->{
         //   System.out.println("------------------------------");
         //   System.out.println(data.getSpeachList());
            tableView.getItems().addAll(data.getSpeachList());
        });
        CompletableFuture.runAsync(()->{
            for (ChartControllerInterface c:chartControllers) c.setSpeachList(data.getSpeachList());
        });
    }
    public void clear(){
        tableView.getItems().clear();
        for (ChartControllerInterface c:chartControllers) c.clear();
    }
}
