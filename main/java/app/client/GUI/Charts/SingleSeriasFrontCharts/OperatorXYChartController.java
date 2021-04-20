package app.client.GUI.Charts.SingleSeriasFrontCharts;

import app.DTO.Speach;
import javafx.scene.chart.XYChart;
@Deprecated
public class OperatorXYChartController extends XYChartController {
    public OperatorXYChartController(XYChart<String, Integer> barChart) {
        super(barChart);
    }

    @Override
    String getSerias(Speach data) {
        return data.getOperator();
    }
    @Override
    void test(Speach speach) {
            int rand=randomWithRange(1,5);
            speach.setOperator(speach.getOperator()+rand);
    }

    int randomWithRange(int min, int max){
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
