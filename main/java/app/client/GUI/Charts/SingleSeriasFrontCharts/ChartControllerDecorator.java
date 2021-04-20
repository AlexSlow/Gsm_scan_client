package app.client.GUI.Charts.SingleSeriasFrontCharts;

import app.DTO.Speach;
import javafx.application.Platform;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Создан для того, что бы не делать несколько итераций по списку ИП
 */
@Data
public class ChartControllerDecorator implements ChartControllerInterface {
    private List<ChartControllerInterface> chartControllerList=new ArrayList<>();

    @Override
    public void clear() {
        for (ChartControllerInterface chartController:chartControllerList)
            chartController.clear();

    }

    @Override
    public void setSpeachList(List<Speach> speaches) {
        speaches.forEach(speach -> {
            for (ChartControllerInterface chartController:chartControllerList)
                chartController.processSpeach(speach);
        });
        startAnimation();

    }

    @Override
    public void startAnimation() {
        for (ChartControllerInterface chartController:chartControllerList)
        Platform.runLater(()->{chartController.startAnimation();});
    }

    @Override
    public void processSpeach(Speach speach) {
    throw  new RuntimeException("Реализация не создана. Декоратор chartController");
    }
}
