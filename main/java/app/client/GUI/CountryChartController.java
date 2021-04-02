package app.client.GUI;

import app.DTO.Speach;
import javafx.scene.chart.Chart;

import java.util.List;

public class CountryChartController extends ChartController {
    @Override
    String getSerias(Speach data) {
        return data.getStation();
    }

    @Override
    void test(List<Speach> speaches) {
        for(Speach speach:speaches){
            int rand=randomWithRange(1,5);
            speach.setStation(speach.getStation()+rand);
        }
    }

    int randomWithRange(int min, int max){
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
