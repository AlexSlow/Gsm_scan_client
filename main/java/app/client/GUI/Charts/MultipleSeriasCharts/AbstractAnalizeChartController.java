package app.client.GUI.Charts.MultipleSeriasCharts;

import app.DTO.Speach;
import app.DTO.StantionSpeachDTO;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Log4j
@Data
public abstract class AbstractAnalizeChartController  {
    private XYChart<String,Integer> xyChart;
    Map<XYChart.Series<String,Integer>,Map<String,Integer>> map=new HashMap<>();
    public AbstractAnalizeChartController(XYChart<String,Integer> Chart)
    {
        xyChart=Chart;
        xyChart.setAnimated(false);
    }
    public void initSeries (List<StantionSpeachDTO> stantionSpeachDTO){
        stantionSpeachDTO.forEach(dto->{
            XYChart.Series<String,Integer> series=new XYChart.Series<>();
            series.setName(dto.getStantionDto().getName());
            //seriesList.add(series);
            map.put(series,new HashMap<>());
        });
    }

    public void build(List<StantionSpeachDTO> stantionSpeachDTO) {
        clear();
        fillMap(stantionSpeachDTO);
        beforeDrow();
        drow();


    }
    protected void beforeDrow(){
    }
    protected void drow(){
        for (Map.Entry<XYChart.Series<String,Integer>,Map<String,Integer>> entry:map.entrySet()){
            for (Map.Entry<String,Integer> amoutEntety:entry.getValue().entrySet()){
            entry.getKey().getData().add(new XYChart.Data<>(amoutEntety.getKey(),amoutEntety.getValue()));
            }
        }
        Platform.runLater(()->{
            xyChart.setAnimated(false);
            xyChart.getData().addAll(map.keySet());
        });

    }
    protected void fillMap(List<StantionSpeachDTO> stantionSpeachDTO){


        stantionSpeachDTO.forEach(dto->{


            XYChart.Series<String,Integer> series=new XYChart.Series<>();
            series.setName(dto.getStantionDto().getName());
            map.put(series,new HashMap<>());

            dto.getSpeachList().forEach(speach -> {
                String param=cutData(getData(speach));
                incMap(series,param);
            });

        });
    }
    protected void incMap(XYChart.Series<String,Integer> series,String param){
        if (map.get(series).containsKey(param))
        map.get(series).put(param,map.get(series).get(param)+1);
        else  map.get(series).put(param,1);
    }
    abstract String getData(Speach speach);
    protected String cutData(String str){
        if (str.toCharArray().length<15) return str;
        else
        return str.substring(0,15)+"...";
    }
    public  void clear(){


        map.clear();
        Platform.runLater(()->{
            xyChart.getData().clear();

        });
    }
}
