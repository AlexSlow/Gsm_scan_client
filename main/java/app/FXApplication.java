package app;

import app.ServerConnection.ServerConnectionManager;
import app.client.Controller.MainController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URL;
@Component
@Log4j
@Data
public class FXApplication extends Application {
private Stage mainStage;
    @PostConstruct
    @Override
    public void init() throws Exception {
        super.init();

        log.info("Инициализация FX клиента");

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        log.info("Остановка FX клиента");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage=primaryStage;
        log.info("Запуск FX клиента");
        FXMLLoader loader = new FXMLLoader();
        MainController mainController=getController();
        loader.setController(mainController);



        URL xmlUrl = getClass().getResource("/fxml/Main.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        primaryStage.setTitle("СПО КБС");
        primaryStage.setScene(new Scene(root));


        primaryStage.show();
        mainController.setScrollHandler();
    }
    MainController getController(){
        MainController mainController=new MainController();
        return mainController;
    }
}
