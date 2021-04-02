package app.client.GUI;

import app.DTO.Speach;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;
import lombok.Data;
import lombok.extern.log4j.Log4j;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Log4j
@Data
public abstract class ChartController {
    Map<String,Integer> map=new HashMap<>();
    BarChart<String, Integer> barChart;
    abstract String  getSerias(Speach data);

   public void clear(){
        barChart.getData().clear();
        map.clear();
    }
    public void startAnimation(){

       System.out.println(map);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                for (XYChart.Series<String, Integer> series : barChart.getData()) {
                    for (XYChart.Data<String, Integer> data : series.getData()) {
                        data.setYValue(map.get(series.getName()));
                    }
                }
                /*
               // barChart.getData().clear();
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    XYChart.Series<String,Integer> series=new XYChart.Series<>();
                    series.setName(entry.getKey());
                    series.getData().add(new XYChart.Data("",entry.getValue()));
                    barChart.getData().add(series);

                }

                 */
            }
        }));
        // Repeat indefinitely until stop() method is called.
      //  timeline.setCycleCount(Animation.INDEFINITE);
      //  timeline.setAutoReverse(true);
        timeline.play();
    }

   public void setSpeach(List<Speach> speaches){
       test(speaches);

    fillMap(speaches);
    Platform.runLater(()->{startAnimation();});
    }
    void test(List<Speach> speaches){}
    void fillMap(List<Speach> speaches){
        for (Speach speach:speaches)
        {
            String param=getSerias(speach);
            //setSeries(param);
            if (map.containsKey(param)) map.put(param,map.get(param)+1);
            else
            {
                map.put(param,0);
                XYChart.Series<String,Integer> series = new XYChart.Series<> ();
                series.setName(param);
                series.getData().add(new XYChart.Data<>("",0));
                Platform.runLater(()-> barChart.getData().add(series));
            }

        }


    }
    /*
    void setSeries(String series){
        ObservableList<XYChart.Series<String, Integer>>seriesList= barChart.getData();
        for (XYChart.Series<String, Integer> s:seriesList)
        {
            if (s.getName().equals(series)) {
                if (s.getName().equals(series)) {
                    XYChart.Data<String, Integer> data = s.getData().get(0);
                    data.setYValue(data.getYValue() + 1);
                    return;
                }
            }
        }
        XYChart.Series seriesNew = new XYChart.Series ();
        seriesNew.setName(series);
        Platform.runLater(()->barChart.getData().add(seriesNew));

    }

     */



}
