package app.client.GUI.Charts.SingleSeriasFrontCharts;

import app.DTO.Speach;
import javafx.scene.chart.BarChart;

public class CountryXYChartController extends XYChartController {
    public CountryXYChartController(BarChart<String, Integer> barChart) {
        super(barChart);
    }

    @Override
    String getSerias(Speach data) {
        return data.getStation();
    }

    @Override
    void test(Speach speach) {
            int rand=randomWithRange(1,5);
            speach.setStation(speach.getStation()+rand);
    }

    int randomWithRange(int min, int max){
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
