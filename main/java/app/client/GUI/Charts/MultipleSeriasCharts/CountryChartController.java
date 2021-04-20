package app.client.GUI.Charts.MultipleSeriasCharts;

import app.DTO.Speach;
import javafx.scene.chart.XYChart;

public class CountryChartController extends AbstractAnalizeChartController {
    @Override
    String getData(Speach speach) {
      String contry=speach.getStation();
      return contry+" "+randomWithRange(1,5);

    }

    public CountryChartController(XYChart<String, Integer> Chart) {
        super(Chart);
    }
    private int randomWithRange(int min, int max){
            int range = (max - min) + 1;
            return (int)(Math.random() * range) + min;
        }

}
