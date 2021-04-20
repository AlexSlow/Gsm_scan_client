package app.client.GUI.Charts.MultipleSeriasCharts;

import app.DTO.Speach;
import javafx.scene.chart.XYChart;

public class EventChartController extends AbstractAnalizeChartController {
    @Override
    String getData(Speach speach) {
        return speach.getEvent();
    }

    public EventChartController(XYChart<String, Integer> Chart) {
        super(Chart);
    }
}
