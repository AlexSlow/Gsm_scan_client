package app.client.GUI.Charts.SingleSeriasFrontCharts;

import app.DTO.Speach;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;
import lombok.Data;
import lombok.extern.log4j.Log4j;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Log4j
@Data
/**
 * Предназначен для заполнения графиков
 */
public abstract class XYChartController implements ChartControllerInterface {
   protected Map<String,Integer> map=new HashMap<>();
    XYChart<String, Integer> xyChart;
    abstract String  getSerias(Speach data);

    public XYChartController(XYChart<String, Integer> xyChart) {
        this.xyChart = xyChart;
    }

    @Override
   public void clear(){
        xyChart.getData().clear();
        map.clear();
    }
    @Override
    public void startAnimation(){

      // System.out.println(map);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                for (XYChart.Series<String, Integer> series : xyChart.getData()) {
                    for (XYChart.Data<String, Integer> data : series.getData()) {
                        data.setYValue(map.get(series.getName()));
                    }
                }
            }
        }));
        timeline.play();
    }
    @Override
   public void setSpeachList(List<Speach> speaches){

       fillMap(speaches);
    }
    void test(Speach speach){}
    void fillMap(List<Speach> speaches){
        for (Speach speach:speaches)
        {
        processSpeach(speach);
        }
    }
    @Override
    public void processSpeach(Speach speach){
        /**
         * Убрать
         */
        test(speach);
        String param=getSerias(speach);
        if (map.containsKey(param)) map.put(param,map.get(param)+1);
        else
        {
            map.put(param,1);
            addSerias(param);

        }
    }
    public  void addSerias(String param){
        XYChart.Series<String,Integer> series = new XYChart.Series<> ();
        series.setName(param);
        series.getData().add(new XYChart.Data<>("",0));
        Platform.runLater(()-> xyChart.getData().add(series));
    }
}
