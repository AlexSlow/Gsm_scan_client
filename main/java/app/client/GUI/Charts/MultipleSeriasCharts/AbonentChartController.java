package app.client.GUI.Charts.MultipleSeriasCharts;

import app.DTO.Speach;
import javafx.scene.chart.XYChart;

import java.text.SimpleDateFormat;

public class AbonentChartController extends AbstractAnalizeChartController {
    //Длина по оси x
    private static final  int LENGS=10;
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd.MM.yy");
    @Override
    String getData(Speach speach) {
        return simpleDateFormat.format(speach.getDate());
    }

    public AbonentChartController(XYChart<String, Integer> Chart) {
        super(Chart);
    }
}
