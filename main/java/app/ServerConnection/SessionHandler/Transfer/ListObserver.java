package app.ServerConnection.SessionHandler.Transfer;

import app.ServerConnection.SessionHandler.Transfer.StompMultipleTransfer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;

public class ListObserver<T> implements StompMultipleTransfer<T> {
    List<ObservableList<T>> listViews=new ArrayList<>();
    public void subscribe(ObservableList<T> observableList){
        listViews.add(observableList);
    }
    public void unsubscribe(ObservableList<T> observableList){
        listViews.remove(observableList);
    }
    @Override
    public void transfer(List<T> data) {
        Platform.runLater(()->{
            for (ObservableList<T> ol:listViews){
                ol.clear();
                ol.addAll(data);
            }
        });
    }

}
