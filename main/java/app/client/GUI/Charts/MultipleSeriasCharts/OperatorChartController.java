package app.client.GUI.Charts.MultipleSeriasCharts;

import app.DTO.Speach;
import javafx.scene.chart.XYChart;

public class OperatorChartController extends AbstractAnalizeChartController {
    public OperatorChartController(XYChart<String, Integer> Chart) {
        super(Chart);
    }

    @Override
    String getData(Speach speach) {
        return speach.getOperator()+" "+randomWithRange(1,5);
    }

    private int randomWithRange(int min, int max){
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
