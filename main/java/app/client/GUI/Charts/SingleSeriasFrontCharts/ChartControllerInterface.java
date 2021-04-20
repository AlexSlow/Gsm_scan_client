package app.client.GUI.Charts.SingleSeriasFrontCharts;

import app.DTO.Speach;

import java.util.List;

public interface ChartControllerInterface {
    void clear();
    void setSpeachList(List<Speach> speaches);
    public void startAnimation();
    public void processSpeach(Speach speach);
}
