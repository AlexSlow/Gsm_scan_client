package app.client.GUI;

import app.DTO.Speach;

import java.util.List;

public class OperatorChartController  extends  ChartController{
    @Override
    String getSerias(Speach data) {
        return data.getOperator();
    }
    @Override
    void test(List<Speach> speaches) {
        for(Speach speach:speaches){
            int rand=randomWithRange(1,5);
            speach.setOperator(speach.getOperator()+rand);
        }
    }

    int randomWithRange(int min, int max){
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
