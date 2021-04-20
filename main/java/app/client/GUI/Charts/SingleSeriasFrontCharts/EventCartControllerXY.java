package app.client.GUI.Charts.SingleSeriasFrontCharts;

import app.DTO.Speach;
import javafx.scene.chart.BarChart;
@Deprecated
public class EventCartControllerXY extends XYChartController {

    public EventCartControllerXY(BarChart<String, Integer> barChart) {
        super(barChart);
    }

    @Override
        String getSerias(Speach data) {
            return data.getEvent();
        }
        @Override
        void test(Speach speach) {
            int rand=randomWithRange(1,5);
         //   speach.setOperator(speach.getOperator()+rand);
        }

        int randomWithRange(int min, int max){
            int range = (max - min) + 1;
            return (int)(Math.random() * range) + min;
        }
}
