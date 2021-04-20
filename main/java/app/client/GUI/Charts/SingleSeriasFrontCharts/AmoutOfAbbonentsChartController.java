package app.client.GUI.Charts.SingleSeriasFrontCharts;

import app.DTO.Speach;
import javafx.scene.chart.XYChart;

import java.text.SimpleDateFormat;
import java.util.Map;

@Deprecated
public class AmoutOfAbbonentsChartController extends XYChartController {
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd.MM.yy");


    public AmoutOfAbbonentsChartController(XYChart<String, Integer> chart) {
        super(chart);


       // this.xyChart.getData().add(series);

    }

    @Override
    String getSerias(Speach data) {
        return simpleDateFormat.format(data.getDate());
    }
    @Override
    void test(Speach speach) {

    }

    @Override
    public void startAnimation() {
          xyChart.getData().clear();
          xyChart.setAnimated(false);
          XYChart.Series<String,Integer> series =new XYChart.Series<>();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
           series.getData().add(new XYChart.Data<>(entry.getKey(),entry.getValue()));
        }


        xyChart.getData().add(series);

    }

    @Override
    public void addSerias(String param) {
        // series.getData().add(new XYChart.Data<String,Integer>(param,0));
    }
}
