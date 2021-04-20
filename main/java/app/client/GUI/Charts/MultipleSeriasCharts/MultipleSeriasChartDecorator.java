package app.client.GUI.Charts.MultipleSeriasCharts;

import app.DTO.StantionSpeachDTO;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
@Log4j
@Data
public class MultipleSeriasChartDecorator {
    private List<AbstractAnalizeChartController> chartControllers=new ArrayList();

    public void buildAll(List<StantionSpeachDTO> stantionSpeachDTOS){

        if (stantionSpeachDTOS!=null)
        CompletableFuture.runAsync(()->{
            chartControllers.forEach(chart->{

                chart.build(stantionSpeachDTOS);
            });
        });
        else throw new RuntimeException();
    }
    public void clear(){
        chartControllers.clear();
    }
}
